package cn.enilu.flash.service.front;

import cn.enilu.flash.bean.AppConfiguration;
import cn.enilu.flash.bean.vo.business.CityInfo;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.utils.HttpClients;
import com.google.common.collect.Maps;
import org.nutz.json.Json;
import org.nutz.mapl.Mapl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
@Service
public class PositionService {
    private Logger logger = LoggerFactory.getLogger(PositionService.class);
    @Autowired
    private AppConfiguration appConfiguration;
    @Autowired
    private MongoRepository mongoRepository;

    public CityInfo getPostion(String ip) {
        Map<String, String> map = Maps.newHashMap();
        map.put("ip", ip);
        map.put("key", appConfiguration.getTencentKey());
        Map result = null;
        try {
            String str = HttpClients.get(appConfiguration.getApiUrl() + "location/v1/ip", map);
            result = (Map) Json.fromJson(str);
        } catch (Exception e) {
            logger.error("获取地理位置异常", e);
        }
        if (result == null || Integer.valueOf(result.get("status").toString()) != 0) {
            try {
                map.put("key", appConfiguration.getTencentKey2());
                String str = HttpClients.get(appConfiguration.getApiUrl() + "location/v1/ip", map);
                result = (Map) Json.fromJson(str);
            } catch (Exception e) {
                logger.error("获取地理位置异常", e);
            }
        }
        if (result == null || Integer.valueOf(result.get("status").toString()) != 0) {
            try {
                map.put("key", appConfiguration.getTencentKey3());
                String str = HttpClients.get(appConfiguration.getApiUrl() + "location/v1/ip", map);
                result = (Map) Json.fromJson(str);
            } catch (Exception e) {
                logger.error("获取地理位置异常", e);
            }

        }
        if (Integer.valueOf(result.get("status").toString()) == 0) {
            Map resultData = (Map) result.get("result");

            String lat = String.valueOf(Mapl.cell(resultData, "location.lat"));
            String lng = String.valueOf(Mapl.cell(resultData, "location.lng"));
            String city = (String) Mapl.cell(resultData, "ad_info.city");
            city = city.replace("市", "");
            CityInfo cityInfo = new CityInfo();
            cityInfo.setCity(city);
            cityInfo.setLat(lat);
            cityInfo.setLng(lng);
            return cityInfo;

        }
        return null;
    }

    public List searchPlace(String cityName, String keyword) {
        Map<String, String> params = Maps.newHashMap();
        params.put("key", appConfiguration.getTencentKey());
        params.put("keyword", URLEncoder.encode(keyword));
        params.put("boundary", "region(" + URLEncoder.encode(cityName) + ",0)");
        params.put("page_size", "10");
        try {
            String str = HttpClients.get(appConfiguration.getApiUrl() + "place/v1/search", params);
            Map result = (Map) Json.fromJson(str);
            if (Integer.valueOf(result.get("status").toString()).intValue() == 0) {
                return (List) result.get("data");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;

    }

    public Map findById(Integer id) {
        Map cities = mongoRepository.findOne("cities");
        Map<String, List> data = (Map) cities.get("data");
        Map result = null;
        for (Map.Entry<String, List> entry : data.entrySet()) {
            List list = entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                Map rec = (Map) list.get(i);
                if (id == Double.valueOf(rec.get("id").toString()).intValue()) {
                    result = rec;
                    break;
                }
            }
        }
        return result;
    }

    public Map findByName(String cityName) {
        Map cities = mongoRepository.findOne("cities");
        Map<String, List> data = (Map) cities.get("data");
        Map result = null;
        for (Map.Entry<String, List> entry : data.entrySet()) {
            List list = entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                Map rec = (Map) list.get(i);
                if (cityName.equals(rec.get("name"))) {
                    result = rec;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 根据经纬度坐标获取位置信息
     *
     * @param geohash
     * @return
     */
    public Map pois(String geohash) {
        Map<String, String> map = Maps.newHashMap();
        map.put("location", geohash);
        map.put("key", appConfiguration.getTencentKey());
        Map result = Maps.newHashMap();
        try {
            String str = HttpClients.get(appConfiguration.getApiUrl() + "geocoder/v1", map);
            Map response = (Map) Json.fromJson(str);
            if ("0".equals(response.get("status").toString())) {
                result.put("address", Mapl.cell(response,"result.address"));
                result.put("city", Mapl.cell(response, "result.address_component.city"));
                result.put("name", Mapl.cell(response, "result.formatted_addresses.recommend"));
                result.put("latitude", Mapl.cell(response, "result.location.lat"));
                result.put("longitude", Mapl.cell(response, "result.location.lng"));
            }

        } catch (Exception e) {
            logger.error("获取地理位置异常", e);
        }
        return result;
    }

}
