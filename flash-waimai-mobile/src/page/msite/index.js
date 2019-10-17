
import {mapMutations} from 'vuex'
// import {imgBaseUrl} from 'src/config/env'
import headTop from 'src/components/header/head'
import footGuide from 'src/components/footer/footGuide'
import shopList from 'src/components/common/shoplist'
import {msiteAddress, msiteFoodTypes, cityGuess} from 'src/service/getData'
import 'src/plugins/swiper.min.js'
import 'src/style/swiper.min.css'

export default {
  data(){
    return {
      geohash: '', // city页面传递过来的地址geohash
      msiteTitle: '请选择地址...', // msite页面头部标题
      foodTypes: [], // 食品分类列表
      hasGetData: false, //是否已经获取地理位置数据，成功之后再获取商铺列表信息
      imgBaseUrl: 'https://fuss10.elemecdn.com', //图片域名地址
    }
  },
  async beforeMount(){
  if (!this.$route.query.geohash) {
    const address = await cityGuess();
    this.geohash = address.latitude + ',' + address.longitude;
  }else{
    this.geohash = this.$route.query.geohash
  }
  //保存geohash 到vuex
  this.SAVE_GEOHASH(this.geohash);
  //获取位置信息
  let res = await msiteAddress(this.geohash);
  this.msiteTitle = res.name;
  // 记录当前经度纬度
  this.RECORD_ADDRESS(res);

  this.hasGetData = true;
},
mounted(){
  //获取导航食品类型列表
  msiteFoodTypes(this.geohash).then(res => {
    console.log('res',res)
  let resLength = res.length;
  console.log(resLength)
  let resArr = [...res]; // 返回一个新的数组
  let foodArr = [];
  for (let i = 0, j = 0; i < resLength; i += 8, j++) {
    foodArr[j] = resArr.splice(0, 8);
  }
  this.foodTypes = foodArr;
}).then(() => {
    //初始化swiper
    new Swiper('.swiper-container', {
      pagination: '.swiper-pagination',
      loop: true
    });
})
},
components: {
  headTop,
    shopList,
    footGuide,
},
computed: {

},
methods: {
...mapMutations([
    'RECORD_ADDRESS', 'SAVE_GEOHASH'
  ]),
    // 解码url地址，求去restaurant_category_id值
    getCategoryId(url){
    let urlData = decodeURIComponent(url.split('=')[1].replace('&target_name',''));
    if (/restaurant_category_id/gi.test(urlData)) {
      return JSON.parse(urlData).restaurant_category_id.id
    }else{
      return ''
    }
  }
},
watch: {

}
}
