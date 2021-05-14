<template>
    <div>
        <section v-if="!showLoading" class="shop_container">
            <nav class="goback" @click="goback">
                <svg width="4rem" height="100%" xmlns="http://www.w3.org/2000/svg" version="1.1">
                    <polyline points="12,18 4,9 12,0" style="fill:none;stroke:rgb(255,255,255);stroke-width:3"/>
                </svg>
            </nav>
            <header class="shop_detail_header" ref="shopheader" :style="{zIndex: showActivities? '14':'10'}">
                <div class="header_cover_img_con">
                  <img :src="imgBaseUrl + shopDetailData.image_path" class="header_cover_img">
                </div>
                <section class="description_header">
                    <router-link to="/shop/shopDetail" class="description_top">
                        <section class="description_left">
                            <img :src="imgBaseUrl + shopDetailData.image_path">
                        </section>
                        <section class="description_right">
                            <h4 class="description_title ellipsis">{{shopDetailData.name}}</h4>
                            <p class="description_text">商家配送／{{shopDetailData.order_lead_time}}分钟送达／配送费¥{{shopDetailData.float_delivery_fee}}</p>
                            <p class="description_promotion ellipsis">公告：{{promotionInfo}}</p>
                        </section>
                        <svg width="14" height="14" xmlns="http://www.w3.org/2000/svg" version="1.1" class="description_arrow" >
                            <path d="M0 0 L8 7 L0 14"  stroke="#fff" stroke-width="1" fill="none"/>
                        </svg>
                    </router-link>
                    <footer class="description_footer" v-if="shopDetailData.activities.length" @click="showActivitiesFun">
                        <p class="ellipsis">
                            <span class="tip_icon" :style="{backgroundColor: '#' + shopDetailData.activities[0].icon_color, borderColor: '#' + shopDetailData.activities[0].icon_color}">{{shopDetailData.activities[0].icon_name}}</span>
                            <span>{{shopDetailData.activities[0].description}}（APP专享）</span>
                        </p>
                        <p>{{shopDetailData.activities.length}}个活动</p>
                        <svg class="footer_arrow">
                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#arrow-left"></use>
                        </svg>
                    </footer>

                </section>
            </header>
            <transition name="fade">
                <section class="activities_details" v-if="showActivities">
                    <h2 class="activities_shoptitle">{{shopDetailData.name}}</h2>
                    <h3 class="activities_ratingstar">
                        <rating-star :rating='shopDetailData.rating'></rating-star>
                    </h3>
                    <section class="activities_list">
                        <header class="activities_title_style"><span>优惠信息</span></header>
                        <ul>
                            <li v-for="item in shopDetailData.activities" :key="item.id">
                                <span class="activities_icon" :style="{backgroundColor: '#' + item.icon_color, borderColor: '#' + item.icon_color}">{{item.icon_name}}</span>
                                <span>{{item.description}}（APP专享）</span>
                            </li>
                        </ul>
                    </section>
                    <section class="activities_shopinfo">
                        <header class="activities_title_style"><span>商家公告</span></header>
                        <p>{{promotionInfo}}</p>
                    </section>
                    <svg width="60" height="60" class="close_activities" @click.stop="showActivitiesFun">
                        <circle cx="30" cy="30" r="25" stroke="#555" stroke-width="1" fill="none"/>
                        <line x1="22" y1="38" x2="38" y2="22" style="stroke:#999;stroke-width:2"/>
                        <line x1="22" y1="22" x2="38" y2="38" style="stroke:#999;stroke-width:2"/>
                    </svg>
                </section>
            </transition>
            <section class="change_show_type" ref="chooseType">
                <div>
                    <span :class='{activity_show: changeShowType =="food"}' @click="changeShowType='food'">商品</span>
                </div>
                <div>
                    <span :class='{activity_show: changeShowType =="rating"}' @click="changeShowType='rating'">评价</span>
                </div>
            </section>
            <transition name="fade-choose">
                <section v-show="changeShowType =='food'" class="food_container">
                    <section class="menu_container">
                        <section class="menu_left" id="wrapper_menu" ref="wrapperMenu">
                            <ul>
                                <li v-for="(item,index) in menuList" :key="index" class="menu_left_li" :class="{activity_menu: index == menuIndex}" @click="chooseMenu(index)">
                                    <img :src="getImgPath(item.icon_url)" v-if="item.icon_url">
                                    <span>{{item.name}}</span>
                                    <span class="category_num" v-if="categoryNum[index]&&item.type==1">{{categoryNum[index]}}</span>
                                </li>
                            </ul>
                        </section>
                        <section class="menu_right" ref="menuFoodList">
                            <ul>
                                <li v-for="(item,index) in menuList" :key="index">
                                    <header class="menu_detail_header">
                                        <section class="menu_detail_header_left">
                                            <strong class="menu_item_title">{{item.name}}</strong>
                                            <span class="menu_item_description">{{item.description}}</span>
                                        </section>
                                        <span class="menu_detail_header_right" @click="showTitleDetail(index)"></span>
                                        <p class="description_tip" v-if="index == TitleDetailIndex">
                                            <span>{{item.name}}</span>
                                            {{item.description}}
                                        </p>
                                    </header>
                                    <section v-for="(foods,foodindex) in item.foods" :key="foodindex" class="menu_detail_list">
                                        <router-link  :to="{path: 'shop/foodDetail', query:{image_path:foods.image_path, description: foods.description, month_sales: foods.month_sales, name: foods.name, rating: foods.rating, rating_count: foods.rating_count, satisfy_rate: foods.satisfy_rate, foods, shopId}}" tag="div" class="menu_detail_link">
                                            <section class="menu_food_img">
                                                <img :src="imgBaseUrl + foods.image_path">
                                            </section>
                                            <section class="menu_food_description">
                                                <h3 class="food_description_head">
                                                    <strong class="description_foodname">{{foods.name}}</strong>
                                                    <ul v-if="foods.attributes.length" class="attributes_ul">
                                                        <li v-if="attribute" v-for="(attribute, foodindex) in foods.attributes" :key="foodindex" :style="{color: '#' + attribute.icon_color,borderColor:'#' + attribute.icon_color}" :class="{attribute_new: attribute.icon_name == '新'}">
                                                          <p :style="{color: attribute.icon_name == '新'? '#fff' : '#' + attribute.icon_color}">{{attribute.icon_name == '新'? '新品':attribute.icon_name}}</p>
                                                        </li>
                                                    </ul>

                                                </h3>
                                                <p class="food_description_content">{{foods.description}}</p>
                                                <p class="food_description_sale_rating">
                                                    <span>月售{{foods.month_sales}}份</span>
                                                    <span>好评率{{foods.satisfy_rate}}%</span>
                                                </p>
                                                <p v-if="foods.activity" class="food_activity">
                                                <!--<span :style="{color: '#' + foods.activity.image_text_color,borderColor:'#' +foods.activity.icon_color}">{{foods.activity.image_text}}</span>-->
                                                </p>
                                            </section>
                                        </router-link>
                                        <footer class="menu_detail_footer">
                                            <section class="food_price">
                                                <span>¥</span>
                                                <span>{{foods.specfoods[0].price}}</span>
                                                <span v-if="foods.specifications.length">起</span>
                                            </section>
                                            <buy-cart :shopId='shopId' :foods='foods' @moveInCart="listenInCart" @showChooseList="showChooseList" @showReduceTip="showReduceTip" @showMoveDot="showMoveDotFun"></buy-cart>
                                        </footer>
                                    </section>
                                </li>
                            </ul>
                        </section>
                    </section>
                    <section class="buy_cart_container">
                        <section @click="toggleCartList" class="cart_icon_num">
                            <div class="cart_icon_container" :class="{cart_icon_activity: totalPrice > 0, move_in_cart:receiveInCart}" ref="cartContainer">
                                <span v-if="totalNum" class="cart_list_length">
                                    {{totalNum}}
                                </span>
                                <svg class="cart_icon">
                                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#cart-icon"></use>
                                </svg>
                            </div>
                            <div class="cart_num">
                                <div>¥ {{totalPrice}}</div>
                                <div>配送费¥{{deliveryFee}}</div>
                            </div>
                        </section>
                        <section class="gotopay" :class="{gotopay_acitvity: minimumOrderAmount <= 0}">
                            <span class="gotopay_button_style" v-if="minimumOrderAmount > 0">还差¥{{minimumOrderAmount}}起送</span>
                            <router-link :to="{path:'/confirmOrder', query:{geohash, shopId}}" class="gotopay_button_style" v-else >去结算</router-link>
                        </section>
                    </section>
                    <transition name="toggle-cart">
                        <section class="cart_food_list" v-show="showCartList&&cartFoodList.length">
                            <header>
                                <h4>购物车</h4>
                                <div @click="clearCart">
                                    <svg>
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#cart-remove"></use>
                                    </svg>
                                    <span class="clear_cart">清空</span>
                                </div>
                            </header>
                            <section class="cart_food_details" id="cartFood">
                                <ul>
                                    <li v-for="(item, index) in cartFoodList" :key="index" class="cart_food_li">
                                        <div class="cart_list_num">
                                            <p class="ellipsis">{{item.name}}</p>
                                            <p class="ellipsis">{{item.specs}}</p>
                                        </div>
                                        <div class="cart_list_price">
                                            <span>¥</span>
                                            <span>{{item.price}}</span>
                                        </div>
                                        <section class="cart_list_control">
                                            <span @click="removeOutCart(item.category_id, item.item_id, item.food_id, item.name, item.price, item.specs)">
                                                <svg>
                                                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#cart-minus"></use>
                                                </svg>
                                            </span>
                                            <span class="cart_num">{{item.num}}</span>
                                            <svg class="cart_add" @click="addToCart(item.category_id, item.item_id, item.food_id, item.name, item.price, item.specs)">
                                                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#cart-add"></use>
                                            </svg>
                                        </section>
                                    </li>
                                </ul>
                            </section>
                        </section>
                    </transition>
                    <transition name="fade">
                        <div class="screen_cover" v-show="showCartList&&cartFoodList.length" @click="toggleCartList"></div>
                    </transition>
                </section>
            </transition>
            <transition name="fade-choose">
                <section class="rating_container" id="ratingContainer" v-show="changeShowType =='rating'">
                    <section v-load-more="loaderMoreRating" type="2">
                        <section>

                            <header class="rating_header">
                                <section class="rating_header_left">
                                    <p>{{shopDetailData.rating}}</p>
                                    <p>综合评价</p>
                                    <p>高于周边商家{{(ratingScoresData.compare_rating*100).toFixed(1)}}%</p>
                                </section>
                                <section class="rating_header_right">
                                    <p>
                                        <span>服务态度</span>
                                        <rating-star :rating='ratingScoresData.service_score'></rating-star>
                                        <span class="rating_num">{{ratingScoresData.service_score.toFixed(1)}}</span>
                                    </p>
                                    <p>
                                        <span>菜品评价</span>
                                        <rating-star :rating='ratingScoresData.food_score'></rating-star>
                                        <span class="rating_num">{{ratingScoresData.food_score.toFixed(1)}}</span>
                                    </p>
                                    <p>
                                        <span>送达时间</span>
                                        <span class="delivery_time">{{shopDetailData.order_lead_time}}分钟</span>
                                    </p>
                                </section>
                            </header>
                            <ul class="tag_list_ul">
                                <li v-for="(item, index) in ratingTagsList" :key="index" :class="{unsatisfied: item.unsatisfied, tagActivity: ratingTageIndex == index}" @click="changeTgeIndex(index, item.name)">{{item.name}}({{item.count}})</li>
                            </ul>
                            <ul class="rating_list_ul">
                                <li v-for="(item, index) in ratingList" :key="index" class="rating_list_li">
                                    <img :src="getImgPath(item.avatar)" class="user_avatar">
                                    <section class="rating_list_details">
                                        <header>
                                            <section class="username_star">
                                                <p class="username">{{item.username}}</p>
                                                <p class="star_desc">
                                                    <rating-star :rating='item.rating_star'></rating-star>
                                                    <span class="time_spent_desc">{{item.time_spent_desc}}</span>
                                                </p>
                                            </section>
                                            <time class="rated_at">{{item.rated_at}}</time>
                                        </header>
                                        <ul class="food_img_ul">
                                            <li v-for="(item, index) in item.item_ratings" :key="index">
                                                <img :src="getImgPath(item.image_hash)" v-if="item.image_hash">
                                            </li>
                                        </ul>
                                        <ul class="food_name_ul">
                                            <li v-for="(item, index) in item.item_ratings" :key="index" class="ellipsis">
                                                {{item.food_name}}
                                            </li>
                                        </ul>
                                    </section>
                                </li>
                            </ul>
                        </section>
                    </section>
                </section>
            </transition>
        </section>
        <section>
            <transition name="fade">
                <div class="specs_cover" @click="showChooseList" v-if="showSpecs"></div>
            </transition>
            <transition name="fadeBounce">
                <div class="specs_list" v-if="showSpecs">
                    <header class="specs_list_header">
                        <h4 class="ellipsis">{{choosedFoods.name}}</h4>
                        <svg width="16" height="16" xmlns="http://www.w3.org/2000/svg" version="1.1"class="specs_cancel" @click="showChooseList">
                            <line x1="0" y1="0" x2="16" y2="16"  stroke="#666" stroke-width="1.2"/>
                            <line x1="0" y1="16" x2="16" y2="0"  stroke="#666" stroke-width="1.2"/>
                        </svg>
                    </header>
                    <section class="specs_details">
                        <h5 class="specs_details_title">{{choosedFoods.specifications[0].name}}</h5>
                        <ul>
                            <li v-for="(item, itemIndex) in choosedFoods.specifications[0].values" :class="{specs_activity: itemIndex == specsIndex}" @click="chooseSpecs(itemIndex)">
                                {{item}}
                            </li>
                        </ul>
                    </section>
                    <footer class="specs_footer">
                        <div class="specs_price">
                            <span>¥ </span>
                            <span>{{choosedFoods.specfoods[specsIndex].price}}</span>
                        </div>
                        <div class="specs_addto_cart" @click="addSpecs(choosedFoods.category_id, choosedFoods.item_id, choosedFoods.specfoods[specsIndex].food_id, choosedFoods.specfoods[specsIndex].name, choosedFoods.specfoods[specsIndex].price, choosedFoods.specifications[0].values[specsIndex], choosedFoods.specfoods[specsIndex].packing_fee, choosedFoods.specfoods[specsIndex].sku_id, choosedFoods.specfoods[specsIndex].stock)">加入购物车</div>
                    </footer>
                </div>
            </transition>
        </section>
        <transition name="fade">
            <p class="show_delete_tip" v-if="showDeleteTip">多规格商品只能去购物车删除哦</p>
        </transition>
        <transition
        appear
        @after-appear = 'afterEnter'
        @before-appear="beforeEnter"
        v-for="(item,index) in showMoveDot"
        >
            <span class="move_dot" v-if="item">
                <svg class="move_liner">
                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#cart-add"></use>
                </svg>
            </span>
        </transition>
       <loading v-show="showLoading || loadRatings"></loading>
       <section class="animation_opactiy shop_back_svg_container" v-if="showLoading">
           <img src="../../images/shop_back_svg.svg">
       </section>
       <transition name="router-slid" mode="out-in">
            <router-view></router-view>
        </transition>
    </div>
</template>
<script src="./index.js"></script>
<style rel="stylesheet/scss" lang="scss" src="./shop.scss"></style>
