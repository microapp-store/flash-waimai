package cn.enilu.flash.bean.entity.front;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created  on 2018/1/4 0004.
 *
 * @author zt
 */
@Document(collection = "entries")
@Data
public class Entry extends BaseMongoEntity {

    private Long id;
    private Boolean is_in_serving;
    private String title;
    private String description;
    private String link;
    private String image_url;
    private String icon_url;
    private String title_color;

}
