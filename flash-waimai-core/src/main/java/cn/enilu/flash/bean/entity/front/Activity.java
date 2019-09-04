package cn.enilu.flash.bean.entity.front;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created  on 2018/1/4 0004.
 *
 * @author zt
 */
@Document(collection = "activities")
@Data
public class Activity extends BaseMongoEntity {

    private Long id;
    private String name;
    private String description;
    private String icon_color;
    private String icon_name;
    private Integer ranking_weight;

}
