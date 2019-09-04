package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2018/1/5 0005.
 * todo 未完成
 * @author zt
 */
@RestController
public class RestaurantController extends BaseController {
    @RequestMapping(value = "/v4/restaurants",method = RequestMethod.GET)

    public Object restaurants(@RequestParam("geohash")String geoHash,
                              @RequestParam("keyword")String keyWord){

        return null;
    }
}
