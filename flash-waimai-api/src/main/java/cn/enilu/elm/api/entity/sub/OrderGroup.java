package cn.enilu.elm.api.entity.sub;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
public class OrderGroup {
    @Id
    private String _id;
    private String name;
    private Double price;
    private Double quantity;

    private List specs;
    private List attrs;
    private List new_specs;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setSpecs(List specs) {
        this.specs = specs;
    }

    public List getAttrs() {
        return attrs;
    }

    public void setAttrs(List attrs) {
        this.attrs = attrs;
    }

    public List getNew_specs() {
        return new_specs;
    }

    public void setNew_specs(List new_specs) {
        this.new_specs = new_specs;
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
