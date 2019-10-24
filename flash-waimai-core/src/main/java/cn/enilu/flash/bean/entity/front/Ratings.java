package cn.enilu.flash.bean.entity.front;

import cn.enilu.flash.bean.entity.front.sub.Rating;
import cn.enilu.flash.bean.entity.front.sub.Score;
import cn.enilu.flash.bean.entity.front.sub.Tag;
import cn.enilu.flash.utils.Lists;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/24 23:19
 */
@Data
@Document(collection = "ratings")
public class Ratings extends BaseMongoEntity {
    private Long restaurant_id;
    private List<Rating> ratings = Lists.newArrayList(new Rating());
    private List<Tag> tags = Lists.newArrayList(new Tag());
    private Score scores = new Score();

    public Ratings(){

    }
    public Ratings(Long restaurant_id){
        this.restaurant_id = restaurant_id;
    }
}
