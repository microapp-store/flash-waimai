package cn.enilu.flash.bean.entity.front;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@Document(collection = "addresses")
@Data
public class Address extends BaseMongoEntity {

    private Long id;
    private String address;
    private String phone;
    private String phone_bk;
    private String name;
    private String st_geohash;
    private String address_detail;
    private Integer tag_type;
    private Long user_id;
    private Boolean phone_had_bound;
    private Integer deliver_amount;
    private Integer agent_fee;
    private Boolean is_deliverable;
    private Boolean is_user_default;
    private String tag;
    private Integer city_id;
    private Integer sex;
    private Integer poi_type;
    private Date created_at;
    private Integer is_valid;

}
