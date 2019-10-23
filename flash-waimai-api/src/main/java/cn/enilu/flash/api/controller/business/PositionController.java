package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.vo.business.City;
import cn.enilu.flash.bean.vo.business.CityInfo;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.service.front.PositionService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
@RestController
public class PositionController extends BaseController {

    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private PositionService positionService;

    @RequestMapping(value = "/v1/cities",method = RequestMethod.GET)
    public Object cities(@RequestParam("type") String type, HttpServletRequest request) {
        Map cities = mongoRepository.findOne("cities");
        Map data = (Map) cities.get("data");
        switch (type){
            case "guess":
                CityInfo cityInfo = positionService.getPostion(getIp());
                String city = cityInfo.getCity();
                if (Strings.isNullOrEmpty(city)) {
                    return Rets.failure();
                }
                return  Rets.success(positionService.findByName(city));

            case "hot":

                return Rets.success(data.get("hotCities"));

            case "group":
                return  Rets.success(data);


            default:
                    break;


        }
        return Rets.failure();

    }
    @RequestMapping(value = "/v1/cities/{id}",method = RequestMethod.GET)
    public Object getCity(@PathVariable("id")Integer id){
        return Rets.success(positionService.findById(id));
    }

    @RequestMapping(value = "/v1/pois",method = RequestMethod.GET)
    public Object getPoiByCityAndKeyword(@RequestParam(value = "type",defaultValue = "search")String type,
                       @RequestParam(value = "city_id",required = false)Integer cityId,
                       @RequestParam(value = "keyword")String keyword){
        String cityName = null;
        if(cityId==null){
            City city = positionService.guessCity(getIp());
            cityName = city.getName();
        }else {
            Map map = positionService.findById(cityId);
            cityName = map.get("name").toString();
        }
        return Rets.success(positionService.searchPlace(cityName, keyword));

    }

    @RequestMapping(value = "/v1/position/pois",method = RequestMethod.GET)
    public Object getPoiByGeoHash(@RequestParam("geohash")String geoHash){
        System.out.println("geohash:"+geoHash);
        return Rets.success(positionService.pois(geoHash));
    }
}
