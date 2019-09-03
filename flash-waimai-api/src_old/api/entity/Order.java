package cn.enilu.elm.api.entity;

import cn.enilu.elm.api.entity.sub.OrderBasket;
import cn.enilu.elm.api.entity.sub.OrderStatusBar;
import cn.enilu.elm.api.entity.sub.OrderTimelineNode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created  on 2018/1/5 0005.
 * todo 未完成
 * @author zt
 */
@Document(collection = "orders")
public class Order implements BaseEntity {
    @Id
    private String _id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Double getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(Double total_quantity) {
        this.total_quantity = total_quantity;
    }

    public Long getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(Long unique_id) {
        this.unique_id = unique_id;
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

    public Integer getTop_show() {
        return top_show;
    }

    public void setTop_show(Integer top_show) {
        this.top_show = top_show;
    }

    public OrderBasket getBasket() {
        return basket;
    }

    public void setBasket(OrderBasket basket) {
        this.basket = basket;
    }

    public OrderStatusBar getStatus_bar() {
        return status_bar;
    }

    public void setStatus_bar(OrderStatusBar status_bar) {
        this.status_bar = status_bar;
    }

    public OrderTimelineNode getTimeline_node() {
        return timeline_node;
    }

    public void setTimeline_node(OrderTimelineNode timeline_node) {
        this.timeline_node = timeline_node;
    }

    public String getFormatted_create_at() {
        return formatted_create_at;
    }

    public void setFormatted_create_at(String formatted_create_at) {
        this.formatted_create_at = formatted_create_at;
    }

    public Double getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Double order_time) {
        this.order_time = order_time;
    }

    public Integer getTime_pass() {
        return time_pass;
    }

    public void setTime_pass(Integer time_pass) {
        this.time_pass = time_pass;
    }

    public Integer getIs_brand() {
        return is_brand;
    }

    public void setIs_brand(Integer is_brand) {
        this.is_brand = is_brand;
    }

    public Integer getIs_deletable() {
        return is_deletable;
    }

    public void setIs_deletable(Integer is_deletable) {
        this.is_deletable = is_deletable;
    }

    public Integer getIs_new_pay() {
        return is_new_pay;
    }

    public void setIs_new_pay(Integer is_new_pay) {
        this.is_new_pay = is_new_pay;
    }

    public Integer getIs_pindan() {
        return is_pindan;
    }

    public void setIs_pindan(Integer is_pindan) {
        this.is_pindan = is_pindan;
    }

    public Integer getOperation_confirm() {
        return operation_confirm;
    }

    public void setOperation_confirm(Integer operation_confirm) {
        this.operation_confirm = operation_confirm;
    }

    public Integer getOperation_rate() {
        return operation_rate;
    }

    public void setOperation_rate(Integer operation_rate) {
        this.operation_rate = operation_rate;
    }

    public Integer getOperation_rebuy() {
        return operation_rebuy;
    }

    public void setOperation_rebuy(Integer operation_rebuy) {
        this.operation_rebuy = operation_rebuy;
    }

    public Integer getOperation_upload_photo() {
        return operation_upload_photo;
    }

    public void setOperation_upload_photo(Integer operation_upload_photo) {
        this.operation_upload_photo = operation_upload_photo;
    }

    public Integer getPay_remain_seconds() {
        return pay_remain_seconds;
    }

    public void setPay_remain_seconds(Integer pay_remain_seconds) {
        this.pay_remain_seconds = pay_remain_seconds;
    }

    public Integer getRated_point() {
        return rated_point;
    }

    public void setRated_point(Integer rated_point) {
        this.rated_point = rated_point;
    }

    public Integer getRemind_reply_count() {
        return remind_reply_count;
    }

    public void setRemind_reply_count(Integer remind_reply_count) {
        this.remind_reply_count = remind_reply_count;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Long getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurant_image_hash() {
        return restaurant_image_hash;
    }

    public void setRestaurant_image_hash(String restaurant_image_hash) {
        this.restaurant_image_hash = restaurant_image_hash;
    }

    public String getRestaurant_image_url() {
        return restaurant_image_url;
    }

    public void setRestaurant_image_url(String restaurant_image_url) {
        this.restaurant_image_url = restaurant_image_url;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public Integer getRestaurant_type() {
        return restaurant_type;
    }

    public void setRestaurant_type(Integer restaurant_type) {
        this.restaurant_type = restaurant_type;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }
}
