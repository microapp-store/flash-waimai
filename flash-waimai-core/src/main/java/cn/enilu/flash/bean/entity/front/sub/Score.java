package cn.enilu.flash.bean.entity.front.sub;

import lombok.Data;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/24 23:21
 */
@Data
public class Score {
    private Integer compare_rating = 0;
    private Integer deliver_time = 0;
    private Integer food_score = 0;
    private Integer order_rating_amount = 0;
    private Integer overall_score = 0;
    private Integer service_score = 0;
}
