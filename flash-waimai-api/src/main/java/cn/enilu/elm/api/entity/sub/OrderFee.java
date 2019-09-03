package cn.enilu.elm.api.entity.sub;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
public class OrderFee {
    private Long category_id;
    private String name;
    private Double price;
    private Double quantity;

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
