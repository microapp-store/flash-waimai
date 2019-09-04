package cn.enilu.flash.bean.entity.front;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
@Document(collection = "ids")
@Data
public class Ids extends BaseMongoEntity {
    public static  final String ADMIN_ID="admin_id";
    public static  final String RESTAURANT_ID="restaurant_id";
    public static  final String ITEM_ID="item_id";
    public static  final String FOOD_ID="food_id";
    public static  final String SKU_ID="sku_id";
    public static  final String CATEGORY_ID="category_id";
    public static  final String CART_ID = "cart_id";
    public static  final String ADDRESS_ID="address_id";

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

}
