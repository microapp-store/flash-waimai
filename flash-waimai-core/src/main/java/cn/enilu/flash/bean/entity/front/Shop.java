package cn.enilu.flash.bean.entity.front;

import cn.enilu.flash.utils.Lists;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
@Document(collection = "shops")
public class Shop extends BaseMongoEntity {
    @Id
    private String _id;
    @NotBlank(message = "商铺名称不能为空")
    private String name;
    private String address;
    private Long id;
    private Double latitude;
    private Double longitude;
    private List location;
    private String phone;
    private String category;
    private List supports= Lists.newArrayList();
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List getLocation() {
        return location;
    }

    public void setLocation(List location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List getSupports() {
        return supports;
    }

    public void setSupports(List supports) {
        this.supports = supports;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRecent_order_num() {
        return recent_order_num;
    }

    public void setRecent_order_num(Integer recent_order_num) {
        this.recent_order_num = recent_order_num;
    }

    public Integer getRating_count() {
        return rating_count;
    }

    public void setRating_count(Integer rating_count) {
        this.rating_count = rating_count;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPromotion_info() {
        return promotion_info;
    }

    public void setPromotion_info(String promotion_info) {
        this.promotion_info = promotion_info;
    }

    public Map getPiecewise_agent_fee() {
        return piecewise_agent_fee;
    }

    public void setPiecewise_agent_fee(Map piecewise_agent_fee) {
        this.piecewise_agent_fee = piecewise_agent_fee;
    }

    public List getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(List opening_hours) {
        this.opening_hours = opening_hours;
    }

    public Map getLicense() {
        return license;
    }

    public void setLicense(Map license) {
        this.license = license;
    }

    public Boolean getIs_new() {
        return is_new;
    }

    public void setIs_new(Boolean is_new) {
        this.is_new = is_new;
    }

    public String getIs_premium() {
        return is_premium;
    }

    public void setIs_premium(String is_premium) {
        this.is_premium = is_premium;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public Map getIdentification() {
        return identification;
    }

    public void setIdentification(Map identification) {
        this.identification = identification;
    }

    public String getFloat_minimum_order_amount() {
        return float_minimum_order_amount;
    }

    public void setFloat_minimum_order_amount(String float_minimum_order_amount) {
        this.float_minimum_order_amount = float_minimum_order_amount;
    }

    public String getFloat_delivery_fee() {
        return float_delivery_fee;
    }

    public void setFloat_delivery_fee(String float_delivery_fee) {
        this.float_delivery_fee = float_delivery_fee;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getOrder_lead_time() {
        return order_lead_time;
    }

    public void setOrder_lead_time(String order_lead_time) {
        this.order_lead_time = order_lead_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map getDelivery_mode() {
        return delivery_mode;
    }

    public void setDelivery_mode(Map delivery_mode) {
        this.delivery_mode = delivery_mode;
    }

    public List getActivities() {
        return activities;
    }

    public void setActivities(List activities) {
        this.activities = activities;
    }
}
