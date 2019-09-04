package cn.enilu.flash.bean.entity.front;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created  on 2018/1/3 0003.
 *
 * @author zt
 */
@Document(collection = "foods")
@Data
public class Food extends BaseMongoEntity {

    private Double rating;
    private Integer is_featured=0;
    private Long restaurant_id;
    private Long category_id;
    private String pinyin_name;
    private List display_times;
    private List attrs;
    private String description;
    private Integer month_sales;
    private Integer rating_count;
    private String tips;
    private String image_path;
    private List specifications;
    private String server_utc;
    private boolean is_essential;
    private List attributes;
    private Long item_id;
    private List limitation;
    private String name;
    private Double satisfy_count;
    private String activity;
    private Double satisfy_rate;
    private List<SpecFood> specfoods;

}


