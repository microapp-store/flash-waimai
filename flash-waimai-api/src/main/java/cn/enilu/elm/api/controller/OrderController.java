package cn.enilu.elm.api.controller;

import cn.enilu.elm.api.entity.Order;
import cn.enilu.elm.api.repository.BaseDao;
import cn.enilu.elm.api.utils.Maps;
import cn.enilu.elm.api.vo.Rets;
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
    private BaseDao baseDao;
    @RequestMapping("/bos/v2/users/${user_id}/orders")
    public Object orders(@PathVariable("user_id")Long userId){
        return baseDao.findAll(Order.class,"user_id",userId);

    }
    @RequestMapping(value = "/bos/orders/count",method = RequestMethod.GET)
    public Object count(@RequestParam("restaurant_id")String restaurantId){
        long count = 0;
        if(Strings.isBlank(restaurantId)&&Strings.equals("undefined",restaurantId)){
            count = baseDao.count(Order.class, Maps.newHashMap("restaurant_id",Long.valueOf(restaurantId)));
        }else {
             count = baseDao.count(Order.class);
        }
        return Rets.success("count",count);
    }
    @RequestMapping(value="/bos/orders",method = RequestMethod.GET)
    public Object list(@RequestParam("restaurant_id") String restaurantId,
                       @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                       @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        restaurantId="11";
        if (Strings.isBlank(restaurantId)||Strings.equals("undefined",restaurantId)){
            return baseDao.findAll(Order.class,"restaurant_id",Long.valueOf(restaurantId));
        } else {
            return baseDao.findAll(Order.class);
        }
    }

}
