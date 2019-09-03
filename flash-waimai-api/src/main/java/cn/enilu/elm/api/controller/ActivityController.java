package cn.enilu.elm.api.controller;

import cn.enilu.elm.api.entity.Activity;
import cn.enilu.elm.api.repository.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@RestController
public class ActivityController extends BaseController {
    @Autowired
    private BaseDao baseDao;

    @RequestMapping(value = "/shopping/v1/restaurants/activity_attributes",method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(value = "latitude",required = false) String latitude,
                       @RequestParam(value = "longitude",required = false) String longitude){
        return baseDao.findAll(Activity.class);
    }
}
