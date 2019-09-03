package cn.enilu.elm.api.controller;

import org.springframework.web.bind.annotation.*;

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
