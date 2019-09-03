package cn.enilu.elm.api.controller;

import cn.enilu.elm.api.entity.Entry;
import cn.enilu.elm.api.repository.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2018/1/4 0004.
 *
 * @author zt
 */
@RestController
public class EntryController extends BaseController {
    @Autowired
    private BaseDao baseDao;
    @RequestMapping(value = "/v2/index_entry",method = RequestMethod.GET)
    public Object list(){
        return baseDao.findAll(Entry.class);
    }
}
