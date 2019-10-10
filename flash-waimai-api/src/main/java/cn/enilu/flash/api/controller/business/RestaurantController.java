package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.utils.Lists;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.factory.Page;
import cn.enilu.flash.utils.gps.Distance;

import java.util.List;
import java.util.Map;

import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2018/1/5 0005. todo 未完成
 * 
 * @author zt
 */
@RestController
public class RestaurantController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;

    @RequestMapping(value = "/v4/restaurants", method = RequestMethod.GET)
    public Object restaurants(@RequestParam("geohash") String geoHash, @RequestParam("keyword") String keyWord) {
        String[] geoHashArr = geoHash.split(",");
        String longitude = geoHashArr[1];
        String latitude = geoHashArr[0];
        Map<String, Object> params = Maps.newHashMap("name", keyWord);
        System.out.println(Json.toJson(params));
        GeoResults<Map> geoResults = mongoRepository.near(Double.valueOf(longitude), Double.valueOf(latitude),
                "shops", params);
        List<GeoResult<Map>> geoResultList = geoResults.getContent();
        List<Map> list = Lists.newArrayList();
        for (int i = 0; i < geoResultList.size(); i++) {
            Map map = geoResultList.get(i).getContent();
            Distance distance = new Distance(Double.valueOf(longitude), Double.valueOf(latitude),
                    Double.valueOf(map.get("longitude").toString()), Double.valueOf(map.get("latitude").toString()));
            map.put("distance", distance.getDistance());
            list.add(map);
        }
        return Rets.success(list);        
    }
}
