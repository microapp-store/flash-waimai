package cn.enilu.flash.bean.entity.front;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author ：enilu.cn
 * @date ：Created in 2019/10/18 22:54
 */
@Document(collection = "payments")
@Data
public class Payment {
    @Id
    private String _id;
    private String description;
    private String disabled_reason;
    private Long id;
    private Boolean is_online_payment=true;
    private String name;
    private List promotion;
    private Integer select_state;


    @JSONField(name="_id")
    public String get_id() {
        return _id;
    }
    @JSONField(name="_id")
    public void set_id(String _id) {
        this._id = _id;
    }

}
