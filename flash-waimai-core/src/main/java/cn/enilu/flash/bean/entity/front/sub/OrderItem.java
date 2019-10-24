package cn.enilu.flash.bean.entity.front.sub;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@Data
public class OrderItem {
    @Id
    private String _id;
    private Long id;
    private String name;
    private Double price;
    private Double quantity;

    private List specs;
    private List attrs;
    private List new_specs;
    private Integer packing_fee;


    @JSONField(name="_id")
    public String get_id() {
        return _id;
    }
    @JSONField(name="_id")
    public void set_id(String _id) {
        this._id = _id;
    }


}
