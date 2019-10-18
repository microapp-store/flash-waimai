
import {mapState, mapMutations} from 'vuex'
import {msiteAddress, shopDetails, foodMenu, getRatingList, ratingScores, ratingTags} from 'src/service/getData'
import loading from 'src/components/common/loading'
import buyCart from 'src/components/common/buyCart'
import ratingStar from 'src/components/common/ratingStar'
import {loadMore, getImgPath} from 'src/components/common/mixin'
import {imgBaseUrl} from 'src/config/env'
import BScroll from 'better-scroll'
export default {
  data(){
    return{
      geohash: '', //geohash位置信息
      shopId: null, //商店id值
      showLoading: true, //显示加载动画
      changeShowType: 'food',//切换显示商品或者评价
      shopDetailData: null, //商铺详情
      showActivities: false, //是否显示活动详情
      menuList: [], //食品列表
      menuIndex: 0, //已选菜单索引值，默认为0
      menuIndexChange: true,//解决选中index时，scroll监听事件重复判断设置index的bug
      shopListTop: [], //商品列表的高度集合
      TitleDetailIndex: null, //点击展示列表头部详情
      categoryNum: [], //商品类型右上角已加入购物车的数量
      totalPrice: 0, //总共价格
      cartFoodList: [], //购物车商品列表
      showCartList: false,//显示购物车列表
      receiveInCart: false, //购物车组件下落的圆点是否到达目标位置
      ratingList: null, //评价列表
      ratingOffset: 0, //评价获取数据offset值
      ratingScoresData: null, //评价总体分数
      ratingTagsList: null, //评价分类列表
      ratingTageIndex: 0, //评价分类索引
      preventRepeatRequest: false,// 防止多次触发数据请求
      ratingTagName: '',//评论的类型
      loadRatings: false, //加载更多评论是显示加载组件
      foodScroll: null,  //食品列表scroll
      showSpecs: false,//控制显示食品规格
      specsIndex: 0, //当前选中的规格索引值
      choosedFoods: null, //当前选中视频数据
      showDeleteTip: false, //多规格商品点击减按钮，弹出提示框
      showMoveDot: [], //控制下落的小圆点显示隐藏
      windowHeight: null, //屏幕的高度
      elLeft: 0, //当前点击加按钮在网页中的绝对top值
      elBottom: 0, //当前点击加按钮在网页中的绝对left值
      ratingScroll: null, //评论页Scroll
      imgBaseUrl,
    }
  },
  created(){
    this.geohash = this.$route.query.geohash;
    this.shopId = this.$route.query.id;
    this.INIT_BUYCART();
  },
  mounted(){
    this.initData();
    this.windowHeight = window.innerHeight;
  },
  beforeDestroy(){
    // this.foodScroll.removeEventListener('scroll', )
  },
  mixins: [loadMore, getImgPath],
  components: {
    loading,
    ratingStar,
    buyCart,
  },
  computed: {
    ...mapState([
      'latitude','longitude','cartList'
    ]),
    promotionInfo: function (){
      return this.shopDetailData.promotion_info || '欢迎光临，用餐高峰期请提前下单，谢谢。'
    },
    //配送费
    deliveryFee: function () {
      if (this.shopDetailData) {
        return this.shopDetailData.float_delivery_fee;
      }else{
        return null;
      }
    },
    //还差多少元起送，为负数时显示去结算按钮
    minimumOrderAmount: function () {
      if (this.shopDetailData) {
        return this.shopDetailData.float_minimum_order_amount - this.totalPrice;
      }else{
        return null;
      }
    },
    //当前商店购物信息
    shopCart: function (){
      return {...this.cartList[this.shopId]};
    },
    //购物车中总共商品的数量
    totalNum: function (){
      let num = 0;
      this.cartFoodList.forEach(item => {
        num += item.num
      })
      return num
    },
  },
  methods: {
    ...mapMutations([
      'RECORD_ADDRESS','ADD_CART','REDUCE_CART','INIT_BUYCART','CLEAR_CART','RECORD_SHOPDETAIL'
    ]),
    //初始化时获取基本数据
    async initData(){
      if (!this.latitude) {
        //获取位置信息
        let res = await msiteAddress(this.geohash);
        // 记录当前经度纬度进入vuex
        this.RECORD_ADDRESS(res);
      }
      //获取商铺信息
      this.shopDetailData = await shopDetails(this.shopId, this.latitude, this.longitude);
      //获取商铺食品列表
      this.menuList = await foodMenu(this.shopId);
      //评论列表
      this.ratingList = await getRatingList(this.shopId, this.ratingOffset);
      //商铺评论详情
      this.ratingScoresData = await ratingScores(this.shopId);
      //评论Tag列表
      this.ratingTagsList = await ratingTags(this.shopId);
      this.RECORD_SHOPDETAIL(this.shopDetailData)
      //隐藏加载动画
      this.hideLoading();
    },
    //获取食品列表的高度，存入shopListTop
    getFoodListHeight(){
      const listContainer = this.$refs.menuFoodList;
      if (listContainer) {
        const listArr = Array.from(listContainer.children[0].children);
        listArr.forEach((item, index) => {
          this.shopListTop[index] = item.offsetTop;
        });
        this.listenScroll(listContainer)
      }
    },
    //当滑动食品列表时，监听其scrollTop值来设置对应的食品列表标题的样式
    listenScroll(element){
      this.foodScroll = new BScroll(element, {
        probeType: 3,
        deceleration: 0.001,
        bounce: false,
        swipeTime: 2000,
        click: true,
      });
      const wrapperMenu = new BScroll('#wrapper_menu', {
        click: true,
      });
      const wrapMenuHeight = this.$refs.wrapperMenu.clientHeight;
      this.foodScroll.on('scroll', (pos) => {
        if (!this.$refs.wrapperMenu) {
          return
        }
        this.shopListTop.forEach((item, index) => {
          if (this.menuIndexChange && Math.abs(Math.round(pos.y)) >= item) {
            this.menuIndex = index;
            const menuList=this.$refs.wrapperMenu.querySelectorAll('.activity_menu');
            const el = menuList[0];
            wrapperMenu.scrollToElement(el, 800, 0, -(wrapMenuHeight/2 - 50));
          }
        })
      })
    },
    //控制活动详情页的显示隐藏
    showActivitiesFun(){
      this.showActivities = !this.showActivities;
    },
    //点击左侧食品列表标题，相应列表移动到最顶层
    chooseMenu(index){
      this.menuIndex = index;
      //menuIndexChange解决运动时listenScroll依然监听的bug
      this.menuIndexChange = false;
      this.foodScroll.scrollTo(0, -this.shopListTop[index], 400);
      this.foodScroll.on('scrollEnd', () => {
        this.menuIndexChange = true;
      })
    },
    showTitleDetail(index){
      if (this.TitleDetailIndex == index) {
        this.TitleDetailIndex = null;
      }else{
        this.TitleDetailIndex = index;
      }
    },
    //加入购物车，所需7个参数，商铺id，食品分类id，食品id，食品规格id，食品名字，食品价格，食品规格
    addToCart(category_id, item_id, food_id, name, price, specs){
      this.ADD_CART({shopid: this.shopId, category_id, item_id, food_id, name, price, specs});
    },
    //移出购物车，所需7个参数，商铺id，食品分类id，食品id，食品规格id，食品名字，食品价格，食品规格
    removeOutCart(category_id, item_id, food_id, name, price, specs){
      this.REDUCE_CART({shopid: this.shopId, category_id, item_id, food_id, name, price, specs});
    },
    /**
     * 初始化和shopCart变化时，重新获取购物车改变过的数据，赋值 categoryNum，totalPrice，cartFoodList，整个数据流是自上而下的形式，所有的购物车数据都交给vuex统一管理，包括购物车组件中自身的商品数量，使整个数据流更加清晰
     */
    initCategoryNum(){
      let newArr = [];
      let cartFoodNum = 0;
      this.totalPrice = 0;
      this.cartFoodList = [];
      this.menuList.forEach((item, index) => {
        if (this.shopCart&&this.shopCart[item.foods[0].category_id]) {
          let num = 0;
          Object.keys(this.shopCart[item.foods[0].category_id]).forEach(itemid => {
            Object.keys(this.shopCart[item.foods[0].category_id][itemid]).forEach(foodid => {
              let foodItem = this.shopCart[item.foods[0].category_id][itemid][foodid];
              num += foodItem.num;
              if (item.type == 1) {
                this.totalPrice += foodItem.num*foodItem.price;
                if (foodItem.num > 0) {
                  this.cartFoodList[cartFoodNum] = {};
                  this.cartFoodList[cartFoodNum].category_id = item.foods[0].category_id;
                  this.cartFoodList[cartFoodNum].item_id = itemid;
                  this.cartFoodList[cartFoodNum].food_id = foodid;
                  this.cartFoodList[cartFoodNum].num = foodItem.num;
                  this.cartFoodList[cartFoodNum].price = foodItem.price;
                  this.cartFoodList[cartFoodNum].name = foodItem.name;
                  this.cartFoodList[cartFoodNum].specs = foodItem.specs;
                  cartFoodNum ++;
                }
              }
            })
          })
          newArr[index] = num;
        }else{
          newArr[index] = 0;
        }
      })
      this.totalPrice = this.totalPrice.toFixed(2);
      this.categoryNum = [...newArr];
    },
    //控制购物列表是否显示
    toggleCartList(){
      this.cartFoodList.length ? this.showCartList = !this.showCartList : true;
    },
    //清除购物车
    clearCart(){
      this.toggleCartList();
      this.CLEAR_CART(this.shopId);
    },
    //监听圆点是否进入购物车
    listenInCart(){
      if (!this.receiveInCart) {
        this.receiveInCart = true;
        this.$refs.cartContainer.addEventListener('animationend', () => {
          this.receiveInCart = false;
        })
        this.$refs.cartContainer.addEventListener('webkitAnimationEnd', () => {
          this.receiveInCart = false;
        })
      }
    },
    //获取不同类型的评论列表
    async changeTgeIndex(index, name){
      this.ratingTageIndex = index;
      this.ratingOffset = 0;
      this.ratingTagName = name;
      let res = await getRatingList(this.shopId, this.ratingOffset, name);
      this.ratingList = [...res];
      this.$nextTick(() => {
        this.ratingScroll.refresh();
      })
    },
    //加载更多评论
    async loaderMoreRating(){
      if (this.preventRepeatRequest) {
        return
      }
      this.loadRatings = true;
      this.preventRepeatRequest = true;
      this.ratingOffset += 10;
      let ratingDate = await getRatingList(this.shopId, this.ratingOffset, this.ratingTagName);
      this.ratingList = [...this.ratingList,...ratingDate];
      this.loadRatings = false;
      if (ratingDate.length >= 10) {
        this.preventRepeatRequest = false;
      }
    },
    //隐藏动画
    hideLoading(){
      this.showLoading = false;
    },
    //显示规格列表
    showChooseList(foods){
      if (foods) {
        this.choosedFoods = foods;
      }
      this.showSpecs = !this.showSpecs;
      this.specsIndex = 0;
    },
    //记录当前所选规格的索引值
    chooseSpecs(index){
      this.specsIndex = index;
    },
    //多规格商品加入购物车
    addSpecs(category_id, item_id, food_id, name, price, specs, packing_fee, sku_id, stock){
      food_id = item_id
      this.ADD_CART({shopid: this.shopId, category_id, item_id, food_id, name, price, specs, packing_fee, sku_id, stock});
      this.showChooseList();
    },
    //显示提示，无法减去商品
    showReduceTip(){
      this.showDeleteTip = true;
      clearTimeout(this.timer);
      this.timer = setTimeout(() => {
        clearTimeout(this.timer);
        this.showDeleteTip = false;
      }, 3000);
    },
    //显示下落圆球
    showMoveDotFun(showMoveDot, elLeft, elBottom){
      this.showMoveDot = [...this.showMoveDot, ...showMoveDot];
      this.elLeft = elLeft;
      this.elBottom = elBottom;
    },
    beforeEnter(el){
      el.style.transform = `translate3d(0,${37 + this.elBottom - this.windowHeight}px,0)`;
      el.children[0].style.transform = `translate3d(${this.elLeft - 30}px,0,0)`;
      el.children[0].style.opacity = 0;
    },
    afterEnter(el){
      el.style.transform = `translate3d(0,0,0)`;
      el.children[0].style.transform = `translate3d(0,0,0)`;
      el.style.transition = 'transform .55s cubic-bezier(0.3, -0.25, 0.7, -0.15)';
      el.children[0].style.transition = 'transform .55s linear';
      this.showMoveDot = this.showMoveDot.map(item => false);
      el.children[0].style.opacity = 1;
      el.children[0].addEventListener('transitionend', () => {
        this.listenInCart();
      })
      el.children[0].addEventListener('webkitAnimationEnd', () => {
        this.listenInCart();
      })
    },
    goback(){
      this.$router.go(-1);
    }
  },
  watch: {
    //showLoading变化时说明组件已经获取初始化数据，在下一帧nextTick进行后续操作
    showLoading: function (value){
      if (!value) {
        this.$nextTick(() => {
          this.getFoodListHeight();
          this.initCategoryNum();
        })
      }
    },
    shopCart: function (value){
      this.initCategoryNum();
    },
    //购物车列表发生变化，没有商铺时，隐藏
    cartFoodList: function (value){
      if(!value.length){
        this.showCartList = false;
      }
    },
    //商品、评论切换状态
    changeShowType: function (value){
      if (value === 'rating') {
        this.$nextTick(() => {
          this.ratingScroll = new BScroll('#ratingContainer', {
            probeType: 3,
            deceleration: 0.003,
            bounce: false,
            swipeTime: 2000,
            click: true,
          });
          this.ratingScroll.on('scroll', (pos) => {
            if (Math.abs(Math.round(pos.y)) >=  Math.abs(Math.round(this.ratingScroll.maxScrollY))) {
              this.loaderMoreRating();
              this.ratingScroll.refresh();
            }
          })
        })
      }
    }
  }
}
