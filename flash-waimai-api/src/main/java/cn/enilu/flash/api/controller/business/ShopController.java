package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.front.Ids;
import cn.enilu.flash.bean.entity.front.Menu;
import cn.enilu.flash.bean.entity.front.Shop;
import cn.enilu.flash.bean.vo.business.CityInfo;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.service.front.IdsService;
import cn.enilu.flash.service.front.PositionService;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.factory.Page;
import com.google.common.collect.Lists;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * Created  on 2018/1/2 0002.
 *
 * @author zt
 */
@RestController
@RequestMapping("/shopping")
public class ShopController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;
    @Autowired
    private PositionService positionService;
    @RequestMapping(value = "/restaurant/{id}",method = RequestMethod.GET)

    public Object getShop(@PathVariable("id")Long id) {
        Object data = mongoRepository.findOne(id,"shops");
        return data ;
    }

    @RequestMapping(value = "restaurants",method = RequestMethod.GET)

    public Object listShop(@RequestParam(value = "latitude",required = false) String latitude, @RequestParam(value = "longitude",required = false) String longitude) {
        if (com.google.common.base.Strings.isNullOrEmpty(latitude) || "undefined".equals(latitude)
                || com.google.common.base.Strings.isNullOrEmpty(longitude) || "undefined".equals(longitude)) {
            Page<Shop> page = new PageFactory<Shop>().defaultPage();
            return Rets.success(mongoRepository.queryPage(page,Shop.class));
        } else {
            //查询指定经纬度范围内的餐厅
            GeoResults<Map> geoResults =  mongoRepository.near(Double.valueOf(longitude),Double.valueOf(latitude),"shops");
            List<GeoResult<Map>> geoResultList =  geoResults.getContent();
            List list = Lists.newArrayList();
            for(int i=0;i<geoResultList.size();i++){
                list.add(geoResultList.get(i).getContent());
            }
            return Rets.success(list);
        }
    }

    @RequestMapping(value = "/restaurants/count",method = RequestMethod.GET)

    public Object countShop() {
        long count = mongoRepository.count(Shop.class);
        return Rets.success("count", count);
    }
    @RequestMapping(value = "/restaurants/{id}",method = RequestMethod.DELETE)

    public Object deleteShop(@PathVariable("id")Long id) {
        mongoRepository.delete(id,"shops");
        return Rets.success();
    }

    @RequestMapping(value = "/updateshop",method = RequestMethod.POST)

    public Object updateShop(@ModelAttribute @Valid Shop shop) {
//        Map data =   getRequestPayload( Map.class);
        Map<String, Object> updateMap = new HashMap<String, Object>(16);
        updateMap.put("name", Strings.sNull(shop.getName()));
        updateMap.put("address", Strings.sNull(shop.getAddress()));
        updateMap.put("description", Strings.sNull(shop.getDescription()));
        updateMap.put("category", Strings.sNull(shop.getCategory()));
        updateMap.put("phone", Strings.sNull(shop.getPhone()));
        updateMap.put("rating", Double.valueOf(shop.getRating()));
        updateMap.put("recent_order_num", shop.getRecent_order_num());
        updateMap.put("image_path", shop.getImage_path());

        mongoRepository.update( shop.getId(), "shops", updateMap);
        return Rets.success();
    }

    @RequestMapping(value = "/addShop",method = RequestMethod.POST)

    public Object addShop(HttpServletRequest request) {
        String json = getRequestPayload();
        Map data = (Map) Json.fromJson(json);
        Shop shop = Json.fromJson(Shop.class, json);
        shop.setId(idsService.getId(Ids.RESTAURANT_ID));
        List<Map> supports = new ArrayList<Map>(4);
        if ((boolean) data.get("bao")) {
            supports.add(buildSupport("已加入“外卖保”计划，食品安全有保障", "999999", "保", 7, "外卖保"));
        }
        if ((boolean) data.get("zhun")) {
            supports.add(buildSupport("准时必达，超时秒赔", "57A9FF", "准", 9, "准时达"));
        }
        if ((boolean) data.get("piao")) {
            supports.add(buildSupport("该商家支持开发票，请在下单时填写好发票抬头", "999999", "票", 4, "开发票"));
        }
        shop.setSupports(supports);
        shop.setIs_new((boolean) data.get("new"));

        if ((boolean) data.get("delivery_mode")) {
            Map<String, Object> deliveryMode = Maps.newHashMap("color", "57A9FF", "id", 1, "is_solid", true, "text", "蜂鸟专送");
            shop.setDelivery_mode(deliveryMode);
        }
        Map<String, String> tips = new HashMap<String, String>(2);
        tips.put("tips", "配送费约￥" + data.get("float_delivery_fee"));
        shop.setPiecewise_agent_fee(tips);
        List<String> openingHours = new ArrayList<String>();
        if (Strings.isNotBlank(Strings.sNull(data.get("startTime"))) &&
                Strings.isNotBlank(Strings.sNull(data.get("endTime")))) {
            openingHours.add(data.get("startTime").toString() + "/" + data.get("endTime").toString());
        } else {
            openingHours.add("08:30/20:30");
        }

        shop.setOpening_hours(openingHours);

        Map<String, String> license = new HashMap<String, String>();
        if (Strings.isNotBlank(Strings.sNull(data.get("business_license_image")))) {
            license.put("business_license_image", data.get("business_license_image").toString());
        }
        if (Strings.isNotBlank(Strings.sNull(data.get("catering_service_license_image")))) {
            license.put("catering_service_license_image", data.get("catering_service_license_image").toString());
        }
        shop.setLicense(license);


        Map<String, String> identification = Maps.newHashMap("company_name", "",
                "identificate_agency", "",
                "identificate_date", "",
                "legal_person", "",
                "licenses_date", "",
                "licenses_number", "",
                "licenses_scope", "",
                "operation_period", "",
                "registered_address", "",
                "registered_number", "");
        shop.setIdentification(identification);

        List activities = (List) data.get("activities");
        int index = 0;
        for (int i = 0; i < activities.size(); i++) {
            Map activity = (Map) activities.get(i);
            String iconName = activity.get("icon_name").toString();
            switch (iconName) {
                case "减":
                    activity.put("icon_color", "f07373");
                    activity.put("id", index++);
                    break;
                case "特":
                    activity.put("icon_color", "EDC123");
                    activity.put("id", index++);
                    break;
                case "新":
                    activity.put("icon_color ", "70bc46");
                    activity.put("id", index++);
                    break;
                case "领":
                    activity.put("icon_color ", "E3EE0D");
                    activity.put("id ", index++);
                    break;
                default:
                    break;
            }
        }
        shop.setActivities(activities);

        CityInfo cityInfo = positionService.getPostion(getIp());
        if (cityInfo != null) {
            shop.setLatitude(Double.valueOf(cityInfo.getLat()));
            shop.setLongitude(Double.valueOf(cityInfo.getLng()));
            List locations = new LinkedList();
            locations.add(Double.valueOf(cityInfo.getLng()));
            locations.add(Double.valueOf(cityInfo.getLat()));
            shop.setLocation(locations);
        }
        mongoRepository.save(shop);

        return Rets.success();
    }

    @RequestMapping(value = "addcategory",method = RequestMethod.POST)

    public Object addCategory(HttpServletRequest request) {
        Menu menu = getRequestPayload( Menu.class);
        menu.setId(idsService.getId(Ids.CATEGORY_ID));
        System.out.println(Json.toJson(menu));
        //todo 进行处理后保存
        mongoRepository.save(menu);
        return Rets.success();
    }
    @RequestMapping(value = "/v2/restaurant/category",method = RequestMethod.GET)

    public Object categories() {
        return Rets.success(mongoRepository.findAll("categories"));
    }

    @RequestMapping(value = "/getcategory/{id}",method = RequestMethod.GET)

    public Object getCategory(@PathVariable("id") Long restaurantId) {
        List list = mongoRepository.findAll("menus", "restaurant_id", restaurantId);
        return Rets.success("category_list", list);
    }


    @RequestMapping(value = "/v2/menu{id}",method = RequestMethod.GET)

    public Object getMenus(@PathVariable("id")Long id){
        return Rets.success(mongoRepository.findOne(id,"menus"));
    }
    @RequestMapping(value = "/v2/menu",method = RequestMethod.GET)

    public Object getMenu(@RequestParam("restaurant_id")Long restaurantId, @RequestParam("allMenu")boolean allMEnu){
        return Rets.success(mongoRepository.findAll("menus","restaurant_id",restaurantId));
    }


    private Map<String, Object> buildSupport(String description, String iconColor, String iconName, Integer id, String name) {
        Map<String, Object> map = new HashMap<String, Object>(8);
        map.put("description", description);
        map.put("icon_color", iconColor);
        map.put("icon_name", iconName);
        map.put("id", id);
        map.put("name", name);
        return map;
    }
}
