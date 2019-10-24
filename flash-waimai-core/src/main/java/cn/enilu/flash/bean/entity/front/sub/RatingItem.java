package cn.enilu.flash.bean.entity.front.sub;

import lombok.Data;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/24 23:25
 */
@Data
public class RatingItem {
    private Integer food_id;
    private String food_name;
    private String image_hash = "";
    private Integer is_valid = 1;
}
