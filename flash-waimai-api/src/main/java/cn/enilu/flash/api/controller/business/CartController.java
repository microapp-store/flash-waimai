package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.entity.front.Ids;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.service.front.IdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@RestController
public class CartController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;
    @RequestMapping(value = "/v1/carts/checkout",method = RequestMethod.POST)
    public Object checkout(HttpServletRequest request){
        Map data = getRequestPayload(Map.class);
        data.put("id",idsService.getId(Ids.CATEGORY_ID));
        mongoRepository.save(data,"carts");
        return  null;
    }
    @RequestMapping(value = "v1/carts/${cart_id}/remarks",method = RequestMethod.GET)
    public Object remarks(@PathVariable("cart_id")Long cartId){
        return mongoRepository.findOne("remarks");
    }
}
