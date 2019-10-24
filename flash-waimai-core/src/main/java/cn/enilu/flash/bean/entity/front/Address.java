package cn.enilu.flash.bean.entity.front;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


/**
 * Created  on 2018/1/5 0005.
 *
 * @author enilu.cn
 */
@Document(collection = "addresses")
public class Address extends BaseMongoEntity{
    @Id
    private String _id;
    private Long id;
    private String address;
    private String phone;
    private String phone_bk;
    private String name;
    private String st_geohash;
    private String address_detail;
    private Integer tag_type;
    private Long user_id;
    private Boolean phone_had_bound=true;
    private Integer deliver_amount=0;
    private Integer agent_fee=0;
    private Boolean is_deliverable=true;
    private Boolean is_user_default=true;
    private String tag;
    private Integer city_id;
    private Integer sex;
    private Integer poi_type;
    private Date created_at;
    private Integer is_valid;
    //必须在setget方法加上该注释，否则_id值会覆盖在id上
    @JSONField(name="_id")
    public String get_id() {
        return _id;
    }
    @JSONField(name="_id")
    public void set_id(String _id) {
        this._id = _id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_bk() {
        return phone_bk;
    }

    public void setPhone_bk(String phone_bk) {
        this.phone_bk = phone_bk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSt_geohash() {
        return st_geohash;
    }

    public void setSt_geohash(String st_geohash) {
        this.st_geohash = st_geohash;
    }

    public String getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }

    public Integer getTag_type() {
        return tag_type;
    }

    public void setTag_type(Integer tag_type) {
        this.tag_type = tag_type;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Boolean getPhone_had_bound() {
        return phone_had_bound;
    }

    public void setPhone_had_bound(Boolean phone_had_bound) {
        this.phone_had_bound = phone_had_bound;
    }

    public Integer getDeliver_amount() {
        return deliver_amount;
    }

    public void setDeliver_amount(Integer deliver_amount) {
        this.deliver_amount = deliver_amount;
    }

    public Integer getAgent_fee() {
        return agent_fee;
    }

    public void setAgent_fee(Integer agent_fee) {
        this.agent_fee = agent_fee;
    }

    public Boolean getIs_deliverable() {
        return is_deliverable;
    }

    public void setIs_deliverable(Boolean is_deliverable) {
        this.is_deliverable = is_deliverable;
    }

    public Boolean getIs_user_default() {
        return is_user_default;
    }

    public void setIs_user_default(Boolean is_user_default) {
        this.is_user_default = is_user_default;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getPoi_type() {
        return poi_type;
    }

    public void setPoi_type(Integer poi_type) {
        this.poi_type = poi_type;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Integer getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(Integer is_valid) {
        this.is_valid = is_valid;
    }
}
