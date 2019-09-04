package cn.enilu.flash.bean.entity.front;

import com.alibaba.fastjson.annotation.JSONField;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author ：enilu
 * @date ：Created in 2019/9/4 14:31
 */
@MappedSuperclass
public class BaseMongoEntity implements Serializable {
    @Id
    @JSONField(name="_id")
    private ObjectId _id;
    @JSONField(name = "_id")
    public ObjectId get_id() {
        return _id;
    }
    @JSONField(name = "_id")
    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}
