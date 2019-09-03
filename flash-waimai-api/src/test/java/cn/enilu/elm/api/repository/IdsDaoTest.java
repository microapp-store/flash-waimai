package cn.enilu.elm.api.repository;

import cn.enilu.elm.api.ApiJunitTest;
import cn.enilu.elm.api.service.IdsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
public class IdsDaoTest  extends ApiJunitTest{
    @Autowired
    private IdsService idsService;
    @Test
    public void getId() throws Exception {
        Long cartId = idsService.getId("cart_id");
        System.out.println(cartId);
    }
}