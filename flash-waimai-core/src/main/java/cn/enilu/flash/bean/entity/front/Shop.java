package cn.enilu.flash.bean.entity.front;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
@Document(collection = "shops")
@Data
public class Shop extends BaseMongoEntity {

    private String name;
    private String address;

    private Long id;
    private Double latitude;
    private Double longitude;
    private List location;
    private String phone;
    private String category;
    private List supports;
    private Integer status=1;
    private Integer recent_order_num=500;
    private Integer rating_count=200;
    private Double rating=4.5;
    private String promotion_info;
    private Map piecewise_agent_fee;

    private List opening_hours;

    private Map license;
    private Boolean is_new;
    private String is_premium;
    private String image_path;
    private Map identification;
    private String float_minimum_order_amount;
    private String float_delivery_fee;
    private String distance;
    private String order_lead_time;
    private String description;
    private Map delivery_mode;
    private List activities;

}
