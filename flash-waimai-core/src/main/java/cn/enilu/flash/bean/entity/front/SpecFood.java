package cn.enilu.flash.bean.entity.front;

import java.util.List;

/**
 * 食品规格
 *
 * @author zt
 */
public class SpecFood {
    //原价
    private Double original_price;
    private Integer sku_id;
    private String name;
    private String pinyin_name;
    private Long restaurant_id;
    private Long food_id;
    //包装费
    private Double packing_fee=0.0;
    private Double recent_rating=0.0;
    private Double promotion_stock=-1.0;
    //现价
    private Double price;
    private Boolean sold_out;
    private Double recent_popularity=0.0;
    private Boolean is_essential=false;
    private Long item_id;
    private Integer checkout_mode=0;
    private Integer stock=1000;
    //规格名称
    private String specs_name;
    private List<KeyValue> specs;

    public Double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(Double original_price) {
        this.original_price = original_price;
    }

    public Integer getSku_id() {
        return sku_id;
    }

    public void setSku_id(Integer sku_id) {
        this.sku_id = sku_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin_name() {
        return pinyin_name;
    }

    public void setPinyin_name(String pinyin_name) {
        this.pinyin_name = pinyin_name;
    }

    public Long getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public Long getFood_id() {
        return food_id;
    }

    public void setFood_id(Long food_id) {
        this.food_id = food_id;
    }

    public Double getPacking_fee() {
        return packing_fee;
    }

    public void setPacking_fee(Double packing_fee) {
        this.packing_fee = packing_fee;
    }

    public Double getRecent_rating() {
        return recent_rating;
    }

    public void setRecent_rating(Double recent_rating) {
        this.recent_rating = recent_rating;
    }

    public Double getPromotion_stock() {
        return promotion_stock;
    }

    public void setPromotion_stock(Double promotion_stock) {
        this.promotion_stock = promotion_stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getSold_out() {
        return sold_out;
    }

    public void setSold_out(Boolean sold_out) {
        this.sold_out = sold_out;
    }

    public Double getRecent_popularity() {
        return recent_popularity;
    }

    public void setRecent_popularity(Double recent_popularity) {
        this.recent_popularity = recent_popularity;
    }

    public Boolean getIs_essential() {
        return is_essential;
    }

    public void setIs_essential(Boolean is_essential) {
        this.is_essential = is_essential;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Integer getCheckout_mode() {
        return checkout_mode;
    }

    public void setCheckout_mode(Integer checkout_mode) {
        this.checkout_mode = checkout_mode;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getSpecs_name() {
        return specs_name;
    }

    public void setSpecs_name(String specs_name) {
        this.specs_name = specs_name;
    }

    public List<KeyValue> getSpecs() {
        return specs;
    }

    public void setSpecs(List<KeyValue> specs) {
        this.specs = specs;
    }
}
