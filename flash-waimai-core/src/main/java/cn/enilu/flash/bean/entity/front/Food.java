package cn.enilu.flash.bean.entity.front;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created  on 2018/1/3 0003.
 *
 * @author zt
 */
@Document(collection = "foods")
public class Food extends BaseMongoEntity{
    @Id
    private String _id;
    private Double rating;
    private Integer is_featured=0;
    private Long restaurant_id;
    private Long category_id;
    private String pinyin_name;
    private List display_times;
    private List attrs;
    private String description;
    private Integer month_sales;
    private Integer rating_count;
    private String tips;
    private String image_path;
    private List specifications;
    private String server_utc;
    private boolean is_essential;
    private List attributes;
    private Long item_id;
    private List limitation;
    private String name;
    private Double satisfy_count;
    private String activity;
    private Double satisfy_rate;
    private List<SpecFood> specfoods;

    //必须在setget方法加上该注释，否则_id值会覆盖在id上
    @JSONField(name="_id")
    public String get_id() {
        return _id;
    }
    @JSONField(name="_id")
    public void set_id(String _id) {
        this._id = _id;
    }
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getIs_featured() {
        return is_featured;
    }

    public void setIs_featured(Integer is_featured) {
        this.is_featured = is_featured;
    }

    public Long getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getPinyin_name() {
        return pinyin_name;
    }

    public void setPinyin_name(String pinyin_name) {
        this.pinyin_name = pinyin_name;
    }

    public List getDisplay_times() {
        return display_times;
    }

    public void setDisplay_times(List display_times) {
        this.display_times = display_times;
    }

    public List getAttrs() {
        return attrs;
    }

    public void setAttrs(List attrs) {
        this.attrs = attrs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMonth_sales() {
        return month_sales;
    }

    public void setMonth_sales(Integer month_sales) {
        this.month_sales = month_sales;
    }

    public Integer getRating_count() {
        return rating_count;
    }

    public void setRating_count(Integer rating_count) {
        this.rating_count = rating_count;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public List getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List specifications) {
        this.specifications = specifications;
    }

    public String getServer_utc() {
        return server_utc;
    }

    public void setServer_utc(String server_utc) {
        this.server_utc = server_utc;
    }

    public boolean is_essential() {
        return is_essential;
    }

    public void setIs_essential(boolean is_essential) {
        this.is_essential = is_essential;
    }

    public List getAttributes() {
        return attributes;
    }

    public void setAttributes(List attributes) {
        this.attributes = attributes;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public List getLimitation() {
        return limitation;
    }

    public void setLimitation(List limitation) {
        this.limitation = limitation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSatisfy_count() {
        return satisfy_count;
    }

    public void setSatisfy_count(Double satisfy_count) {
        this.satisfy_count = satisfy_count;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Double getSatisfy_rate() {
        return satisfy_rate;
    }

    public void setSatisfy_rate(Double satisfy_rate) {
        this.satisfy_rate = satisfy_rate;
    }

    public List<SpecFood> getSpecfoods() {
        return specfoods;
    }

    public void setSpecfoods(List<SpecFood> specfoods) {
        this.specfoods = specfoods;
    }
}


