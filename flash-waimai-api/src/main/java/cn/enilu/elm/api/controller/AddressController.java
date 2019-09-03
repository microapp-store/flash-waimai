package cn.enilu.elm.api.controller;

import cn.enilu.elm.api.entity.Address;
import cn.enilu.elm.api.entity.Ids;
import cn.enilu.elm.api.repository.BaseDao;
import cn.enilu.elm.api.service.IdsService;
import cn.enilu.elm.api.utils.Maps;
import cn.enilu.elm.api.vo.Rets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@RestController
public class AddressController extends BaseController{
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private IdsService idsService;
    @RequestMapping(value = "/v1/users/${user_id}/addresses",method = RequestMethod.GET)
    public Object address(@PathVariable("user_id")Long userId){
        return baseDao.findAll(Address.class,"user_id",userId);
    }
    @RequestMapping(value = "/v1/usres/${user_id}/addresses",method =  RequestMethod.POST)
    public Object save(@PathVariable("user_id")Long userId){
        Address address = getRequestPayload(Address.class);
        address.setUser_id(userId);
        address.setId(idsService.getId(Ids.ADDRESS_ID));
        baseDao.save(address);
        return Rets.success("添加地址成功");
    }
    @RequestMapping(value = "/v1/usres/${user_id}/addresses/${address_id}",method =  RequestMethod.POST)
    public Object delete(@PathVariable("user_id")Long userId,@PathVariable("address_id") Long addressId){
        baseDao.delete("addresses",Maps.newHashMap("user_id",userId,"id",addressId));
        return Rets.success("删除地址成功");
    }
}
