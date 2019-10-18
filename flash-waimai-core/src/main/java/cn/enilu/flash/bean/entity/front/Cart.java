package cn.enilu.flash.bean.entity.front;

import lombok.Data;

import java.util.List;

/**
 * @author ：enilu.cn
 * @date ：Created in 2019/10/18 22:46
 */
@Data
public class Cart {
    private Long id;
    private List groups;
    private List extra;
    private Integer deliver_amount;
    private String deliver_time;
    private String discount_amount;
    private String dist_info;
    private Boolean is_address_too_far=false;
    private Boolean is_deliver_by_fengniao;
    private Integer is_online_paid=1;
    private Integer is_ontime_available=0;
    private Integer must_new_user=0;
    private Integer must_pay_online=0;
    private Integer ontime_status=0;
    private String ontime_unavailable_reason;
    private Integer original_total;
    private String phone;
    private Integer  promise_delivery_time=0;
    private Long restaurant_id;
    private Shop restaurant_info;
    private Integer restaurant_minimum_order_amount;
    private String restaurant_name_for_url;
    private Integer restaurant_status=1;
    private Integer service_fee_explanation=0;
    private String total;
    private Long user_id;
}
