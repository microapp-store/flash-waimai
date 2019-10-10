package cn.enilu.flash.api.controller.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.entity.front.Explain;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;

/**
 * Created  on 2019/10/10
 *
 * @author enilu.cn
 */
@RestController
public class ExplainController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;
    @RequestMapping(value="/v3/profile/explain",method = RequestMethod.GET)
    public Object getData(){
        Explain explain = mongoRepository.findOne(Explain.class);
        return Rets.success(explain);
    }
    
}