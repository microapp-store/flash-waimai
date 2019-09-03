package cn.enilu.elm.api.service;


import cn.enilu.elm.api.ApiJunitTest;
import cn.enilu.elm.api.vo.CityInfo;
import org.junit.Test;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
public class PositionServiceTest extends ApiJunitTest {
    @Autowired
    private PositionService positionService;
    @Test
    public void getPostion() throws Exception {
        CityInfo cityInfo = positionService.getPostion("101.81.121.39");
        System.out.println(Json.toJson(cityInfo));
    }
    @Test
    public void searchPlace()throws  Exception{
        Object obj  = positionService.searchPlace("上海","文通大厦");
        System.out.println(Json.toJson(obj));
    }
}