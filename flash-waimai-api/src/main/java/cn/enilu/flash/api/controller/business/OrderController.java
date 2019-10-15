package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.front.Order;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.factory.Page;
import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@RestController
public class OrderController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;
    @RequestMapping(value = "/bos/v2/users/{user_id}/orders" ,method = RequestMethod.GET)
    public Object orders(@PathVariable("user_id")Long userId,@RequestParam("limit") Integer limit,
    @RequestParam("offset")Integer offset){
        Page<Order> page = new PageFactory<Order>().defaultPage();
       return Rets.success(mongoRepository.queryPage(page, Order.class,Maps.newHashMap("user_id", userId)));


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
    public Object list(@RequestParam(value = "restaurant_id",required = false) Long restaurantId,
                       @RequestParam(value = "id",required = false) Long orderId) {
        Page<Order> page =  new PageFactory<Order>().defaultPage();
        Map<String,Object> params = Maps.newHashMap();
        if (restaurantId!=null){
            params.put("restaurant_id",restaurantId);
        }
        if (orderId!=null){
            params.put("id",orderId);
        }
        page = mongoRepository.queryPage(page,Order.class,params);
        return Rets.success(page);
    }

}
