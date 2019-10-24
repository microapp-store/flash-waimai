package cn.enilu.flash.bean.entity.front;

import cn.enilu.flash.bean.entity.front.sub.OrderBasket;
import cn.enilu.flash.bean.entity.front.sub.OrderStatusBar;
import cn.enilu.flash.bean.entity.front.sub.OrderTimelineNode;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author enilu
 */
@Document(collection = "orders")
@Data
public class Order extends BaseMongoEntity {
    @Id
    private String _id;
    private Long id;
    private Integer total_amount;
    private Integer total_quantity;
    private Long unique_id;
    private Long user_id;
    private Long address_id;
    private Integer top_show=0;
    private OrderBasket basket = new OrderBasket();
    private OrderStatusBar status_bar;
    private OrderTimelineNode timeline_node;
    private String formatted_create_at;
    private Long order_time;
    private Integer time_pass=900;
    private Integer is_brand=0;
    private Integer is_deletable=1;
    private Integer is_new_pay=1;
    private Integer is_pindan=0;
    private Integer operation_confirm=0;
    private Integer operation_rate=0;
    private Integer operation_rebuy=2;
    private Integer operation_pay=0;
    private Integer operation_upload_photo=0;
    private Integer pay_remain_seconds=0;
    private Integer rated_point=0;
    private Integer remind_reply_count=0;
    private Long restaurant_id;
    private String restaurant_image_hash;
    private String restaurant_image_url;
    private String restaurant_name;
    private Integer restaurant_type=0;
    private Integer status_code=0;
    @JSONField(name="_id")
    public String get_id() {
        return _id;
    }
    @JSONField(name="_id")
    public void set_id(String _id) {
        this._id = _id;
    }
}
