package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.entity.front.Ratings;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@RestController
public class RatingController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;
    @RequestMapping(value = "/ugc/v2/restaurants/{restaurant_id}/ratings",method = RequestMethod.GET)
    public Object ratings(@PathVariable("restaurant_id")Long restaurantId){
        Map map = mongoRepository.findOne("ratings","restaurant_id",restaurantId);
        return Rets.success(map.get("ratings"));
    }
    @RequestMapping(value = "ugc/v2/restaurants/{restaurant_id}/ratings/scores",method = RequestMethod.GET)
    public Object score(@PathVariable("restaurant_id")Long restaurantId){
        Map map = mongoRepository.findOne("ratings","restaurant_id",restaurantId);
        return map.get("scores");
    }
    @RequestMapping(value = "ugc/v2/restaurants/{restaurant_id}/ratings/tags",method = RequestMethod.GET)
    public Object tags(@PathVariable("restaurant_id")Long restaurantId){
        Ratings ratings = mongoRepository.findOne(Ratings.class,"restaurant_id",restaurantId);
//        return map.get("tags");
        return  Rets.success(ratings.getTags());
    }

}
