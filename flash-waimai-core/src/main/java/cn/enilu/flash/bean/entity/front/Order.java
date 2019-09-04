package cn.enilu.flash.bean.entity.front;

import cn.enilu.flash.bean.entity.front.sub.OrderBasket;
import cn.enilu.flash.bean.entity.front.sub.OrderStatusBar;
import cn.enilu.flash.bean.entity.front.sub.OrderTimelineNode;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created  on 2018/1/5 0005.
 * todo 未完成
 * @author zt
 */
@Document(collection = "orders")
@Data
public class Order extends BaseMongoEntity {

    private Long id;
    private Double total_amount;
    private Double total_quantity;
    private Long unique_id;
    private Long user_id;
    private Long address_id;
    private Integer top_show=0;
    private OrderBasket basket = new OrderBasket();
    private OrderStatusBar status_bar;
    private OrderTimelineNode timeline_node;
    private String formatted_create_at;
    private Double order_time;
    private Integer time_pass;
    private Integer is_brand;
    private Integer is_deletable;
    private Integer is_new_pay;
    private Integer is_pindan;
    private Integer operation_confirm;
    private Integer operation_rate;
    private Integer operation_rebuy;
    private Integer operation_upload_photo;
    private Integer pay_remain_seconds;
    private Integer rated_point;
    private Integer remind_reply_count;
    private Long restaurant_id;
    private String restaurant_image_hash;
    private String restaurant_image_url;
    private String restaurant_name;
    private Integer restaurant_type;
    private Integer status_code;

}
