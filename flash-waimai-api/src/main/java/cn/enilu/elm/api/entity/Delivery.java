package cn.enilu.elm.api.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@Document(collection = "deliveries")
public class Delivery implements BaseEntity {
    @Id
    private String _id;
    private Long id;
    private String color;
    private Boolean is_solid;
    private String text;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getIs_solid() {
        return is_solid;
    }

    public void setIs_solid(Boolean is_solid) {
        this.is_solid = is_solid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
