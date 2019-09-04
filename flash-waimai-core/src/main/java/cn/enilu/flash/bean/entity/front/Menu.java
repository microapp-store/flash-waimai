package cn.enilu.flash.bean.entity.front;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 2018/1/3 0003.
 *
 * @author zt
 */
@Document(collection = "menus")
@Data
public class Menu  extends BaseMongoEntity {

    private String name;
    private String description;
    private Long id;
    private Long restaurant_id;
    private List<Food> foods=new ArrayList<Food>();
    private Integer type=1;
    private String icon_url="";
    private Boolean is_selected=true;

}
