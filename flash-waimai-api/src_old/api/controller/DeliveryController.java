package cn.enilu.elm.api.controller;

import cn.enilu.elm.api.entity.Delivery;
import cn.enilu.elm.api.repository.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@RestController
public class DeliveryController extends BaseController {
    @Autowired
    private BaseDao baseDao;

    @RequestMapping(value = "/shopping/v1/restaurants/delivery_modes",method = RequestMethod.GET)
    public Object list(@RequestParam(value = "latitude",required = false) String latitude,
                       @RequestParam(value = "longitude",required = false) String longitude){
        return baseDao.findAll(Delivery.class);
    }
}
