package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.entity.front.Order;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.utils.Maps;
import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@RestController
public class OrderController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;
    @RequestMapping("/bos/v2/users/${user_id}/orders")
    public Object orders(@PathVariable("user_id")Long userId){
        return mongoRepository.findAll(Order.class,"user_id",userId);

    }
    @RequestMapping(value = "/bos/orders/count",method = RequestMethod.GET)
    public Object count(@RequestParam("restaurant_id")String restaurantId){
        long count = 0;
        if(Strings.isBlank(restaurantId)&&Strings.equals("undefined",restaurantId)){
            count = mongoRepository.count(Order.class, Maps.newHashMap("restaurant_id",Long.valueOf(restaurantId)));
        }else {
             count = mongoRepository.count(Order.class);
        }
        return Rets.success("count",count);
    }
    @RequestMapping(value="/bos/orders",method = RequestMethod.GET)
    public Object list(@RequestParam("restaurant_id") String restaurantId,
                       @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                       @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        restaurantId="11";
        if (Strings.isBlank(restaurantId)||Strings.equals("undefined",restaurantId)){
            return mongoRepository.findAll(Order.class,"restaurant_id",Long.valueOf(restaurantId));
        } else {
            return mongoRepository.findAll(Order.class);
        }
    }

}
