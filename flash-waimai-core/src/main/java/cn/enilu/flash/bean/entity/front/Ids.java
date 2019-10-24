package cn.enilu.flash.bean.entity.front;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
@Document(collection = "ids")
public class Ids extends BaseMongoEntity{
    public static  final String ADMIN_ID="admin_id";
    public static  final String RESTAURANT_ID="restaurant_id";
    public static  final String ITEM_ID="item_id";
    public static  final String FOOD_ID="food_id";
    public static  final String SKU_ID="sku_id";
    public static  final String CATEGORY_ID="category_id";
    public static  final String CART_ID = "cart_id";
    public static  final String ADDRESS_ID="address_id";
    public static  final String USER_ID="user_id";
    public static  final String ORDER_ID="order_id";
    @Id
    private String _id;
    private Long restaurant_id;
    private Long food_id;
    private Long order_id;
    private Long user_id;
    private Long address_id;
    private Long cart_id;
    private Long img_id;
    private Long category_id;
    private Long item_id;
    private Long sku_id;
    private Long admin_id;
    private Long statis_id;
    //必须在setget方法加上该注释，否则_id值会覆盖在id上
    @JSONField(name="_id")
    public String get_id() {
        return _id;
    }
    @JSONField(name="_id")
    public void set_id(String _id) {
        this._id = _id;
    }
    public Long getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public Long getFood_id() {
        return food_id;
    }

    public void setFood_id(Long food_id) {
        this.food_id = food_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Long address_id) {
        this.address_id = address_id;
    }

    public Long getCart_id() {
        return cart_id;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }

    public Long getImg_id() {
        return img_id;
    }

    public void setImg_id(Long img_id) {
        this.img_id = img_id;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Long getSku_id() {
        return sku_id;
    }

    public void setSku_id(Long sku_id) {
        this.sku_id = sku_id;
    }

    public Long getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Long admin_id) {
        this.admin_id = admin_id;
    }

    public Long getStatis_id() {
        return statis_id;
    }

    public void setStatis_id(Long statis_id) {
        this.statis_id = statis_id;
    }
}
