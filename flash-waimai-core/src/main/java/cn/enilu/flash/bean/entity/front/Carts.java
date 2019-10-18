package cn.enilu.flash.bean.entity.front;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * @author ：enilu.cn
 * @date ：Created in 2019/10/18 22:40
 */
@Document(collection = "carts")
@Data
public class Carts extends BaseMongoEntity {
    @Id
    private String _id;
    private Long id;
    //到达时间
    private String delivery_reach_time;
    private String sig;
    private Integer is_support_ninja=1;
    private Boolean is_support_coupon = false;
    private Cart cart;
    //发票信息
    private Map invoice;
    private Map currrent_address;
    private List<Payment> payments;




    //必须在setget方法加上该注释，否则_id值会覆盖在id上
    @JSONField(name="_id")
    public String get_id() {
        return _id;
    }
    @JSONField(name="_id")
    public void set_id(String _id) {
        this._id = _id;
    }


}
