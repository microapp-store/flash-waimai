
import {mapState, mapMutations} from 'vuex'
import headTop from 'src/components/header/head'
import alertTip from 'src/components/common/alertTip'
import loading from 'src/components/common/loading'
import {checkout, getAddress, placeOrders, getAddressList} from 'src/service/getData'
import {imgBaseUrl} from 'src/config/env'

export default {
  data(){
    return {
      geohash: '', //geohash位置信息
      shopId: null, //商店id值
      showLoading: true, //显示加载动画
      checkoutData: null,//数据返回值
      shopCart: null,//购物车数据
      imgBaseUrl, //图片域名
      showPayWay: false,//显示付款方式
      payWayId: 1, //付款方式
      showAlert: false, //弹出框
      alertText: null, //弹出框内容
    }
  },
  created(){
    //获取上个页面传递过来的geohash值
    this.geohash = this.$route.query.geohash;
    //获取上个页面传递过来的shopid值
    this.shopId = this.$route.query.shopId;
    console.log('shopId',this.shopId);
    this.INIT_BUYCART();
    this.SAVE_SHOPID(this.shopId);
    //获取当前商铺购物车信息
    this.shopCart = this.cartList[this.shopId];
    console.log('shopCart',this.shopCart)
  },
  mounted(){
    if (this.geohash) {
      this.initData();
      this.SAVE_GEOHASH(this.geohash);
    }
    if (!(this.userInfo && this.userInfo.user_id)) {
      // this.showAlert = true;
      // this.alertText = '您还没有登录';
    }
  },
  components: {
    headTop,
    alertTip,
    loading,
  },
  computed: {
    ...mapState([
      'cartList', 'remarkText', 'inputText', 'invoice', 'choosedAddress', 'userInfo'
    ]),
    //备注页返回的信息进行处理
    remarklist: function (){
      let str = new String;
      if (this.remarkText) {
        Object.values(this.remarkText).forEach(item => {
          str += item[1] + '，';
        })
      }
      //是否有自定义备注，分开处理
      if (this.inputText) {
        return str + this.inputText;
      }else{
        return str.substr(0, str.lastIndexOf('，')) ;
      }
    },
  },
  methods: {
    ...mapMutations([
      'INIT_BUYCART', 'SAVE_GEOHASH', 'CHOOSE_ADDRESS', 'NEED_VALIDATION', 'SAVE_CART_ID_SIG', 'SAVE_ORDER_PARAM', 'ORDER_SUCCESS', 'SAVE_SHOPID'
    ]),
    //初始化数据
    async initData(){
      let newArr = new Array;
      console.log('shopCart',this.shopCart)
      Object.values(this.shopCart).forEach(categoryItem => {
        console.log(1)
        Object.values(categoryItem).forEach(itemValue=> {
          console.log(2)
          Object.values(itemValue).forEach(item => {
            console.log(3)
            newArr.push({
              attrs: [],
              extra: {},
              id: item.id,
              name: item.name,
              packing_fee: item.packing_fee,
              price: item.price,
              quantity: item.num,
              sku_id: item.sku_id,
              specs: [item.specs],
              stock: item.stock,
            })
            console.log(4,newArr)
          })
        })
      })
      console.log(5)
      //检验订单是否满足条件

      this.checkoutData = await checkout(this.geohash, [newArr], this.shopId);
      console.log(7)
      this.SAVE_CART_ID_SIG({cart_id: this.checkoutData.cart.id, sig:  this.checkoutData.sig})
      console.log(8)
      this.initAddress();
      console.log(9)
      this.showLoading = false;
      console.log(10)
    },
    //获取地址信息，第一个地址为默认选择地址
    async initAddress(){
      if (this.userInfo && this.userInfo.user_id) {
        const addressRes = await getAddressList(this.userInfo.user_id);
        if (addressRes instanceof Array && addressRes.length) {
          this.CHOOSE_ADDRESS({address: addressRes[0], index: 0});
        }
      }
    },
    //显示付款方式
    showPayWayFun(){
      this.showPayWay = !this.showPayWay;
    },
    //选择付款方式
    choosePayWay(is_online_payment, id){
      if (is_online_payment) {
        this.showPayWay = !this.showPayWay;
        this.payWayId = id;
      }
    },
    //地址备注颜色
    iconColor(name){
      switch(name){
        case '公司': return '#4cd964';
        case '学校': return '#3190e8';
      }
    },
    //确认订单
    async confrimOrder(){
      //用户未登录时弹出提示框
      if (!(this.userInfo && this.userInfo.user_id)) {
        this.showAlert = true;
        this.alertText = '请登录';
        return
        //未选择地址则提示
      }else if(!this.choosedAddress){
        this.showAlert = true;
        this.alertText = '请添加一个收货地址';
        return
      }
      //保存订单
      this.SAVE_ORDER_PARAM({
        user_id: this.userInfo.user_id,
        cart_id: this.checkoutData.cart.id,
        address_id: this.choosedAddress.id,
        description: this.remarklist,
        entities: this.checkoutData.cart.groups,
        geohash: this.geohash,
        sig: this.checkoutData.sig,
      });
      //发送订单信息
      let orderRes = await placeOrders(this.userInfo.user_id, this.checkoutData.cart.id, this.choosedAddress.id, this.remarklist, this.checkoutData.cart.groups, this.geohash, this.checkoutData.sig);
      //第一次下单的手机号需要进行验证，否则直接下单成功
      if (orderRes.need_validation) {
        this.NEED_VALIDATION(orderRes);
        this.$router.push('/confirmOrder/userValidation');
      }else{
        this.ORDER_SUCCESS(orderRes);
        this.$router.push('/confirmOrder/payment');
      }
    },
  },
  watch: {
    userInfo: function (value) {
      if (value && value.user_id) {
        this.initAddress();
      }
    },
  }
}
