package cn.enilu.elm.api.repository;

import cn.enilu.elm.api.ApiJunitTest;
import cn.enilu.elm.api.entity.Address;
import cn.enilu.elm.api.entity.Order;
import org.junit.Test;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
public class BaseDaoTest extends ApiJunitTest {
    @Autowired
    private BaseDao baseDao;
    @Test
    public void save() throws Exception {

    }
    @Test
    public void findOne() throws  Exception{
        Object data = baseDao.findOne(11L,"shops");
        System.out.println(Json.toJson(data));
    }
    @Test
    public void find() throws Exception {
        Map map = (Map) baseDao.findOne(1L,"admins");
        System.out.println(Json.toJson(map));
    }

    @Test
    public void find1() throws Exception {

    }
    @Test
    public void near() throws  Exception{
        Object obj = baseDao.near(125.51181,11.26169,"shops");
        System.out.println(Json.toJson(obj));
    }
    @Test
    public void count() throws Exception {

    }

    @Test
    public void query() throws Exception {

    }

    @Test
    public void update() throws Exception {
        Address address = baseDao.findOne(Address.class,"user_id",1L);
        address.setCreated_at(new Date());
        baseDao.update(address);
    }

    @Test
    public void queryAll1() throws Exception {
        List list = baseDao.findAll(Order.class);
        System.out.println(Json.toJson(list));
    }
}