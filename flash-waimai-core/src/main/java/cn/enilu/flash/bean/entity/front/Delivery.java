package cn.enilu.flash.bean.entity.front;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@Document(collection = "deliveries")
@Data
public class Delivery extends BaseMongoEntity {

    private Long id;
    private String color;
    private Boolean is_solid;
    private String text;

}
