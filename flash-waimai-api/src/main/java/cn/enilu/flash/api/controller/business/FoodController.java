package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.front.*;
import cn.enilu.flash.bean.vo.business.FoodVo;
import cn.enilu.flash.bean.vo.business.SpecVo;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.security.AccountInfo;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.service.front.IdsService;
import cn.enilu.flash.utils.*;
import cn.enilu.flash.utils.factory.Page;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created  on 2017/12/29 0029.
 *
 *@Author enilu
 */
@RestController
@RequestMapping("/shopping")
public class FoodController extends BaseController {

    @Autowired
    private MongoRepository mongoRepository;

    @Autowired
    private IdsService idsService;
    private Logger logger = LoggerFactory.getLogger(FoodController.class);

    @RequestMapping(value = "addfood", method = RequestMethod.POST)
    public Object add(@Valid @ModelAttribute FoodVo foodVo) {

        Food food = new Food();
        BeanUtil.copyProperties(foodVo, food);
        food.setRestaurant_id(foodVo.getIdShop());
        List<SpecVo> specVoList = Json.fromJsonAsList(SpecVo.class, foodVo.getSpecsJson());
        List<SpecFood> specList = Lists.newArrayList();
        for (SpecVo specVo : specVoList) {
            SpecFood specFood = new SpecFood();
            specFood.setName(specVo.getSpecs());
            specFood.setPinyin_name(StringUtils.getPingYin(specVo.getSpecs()));
            specFood.setPrice(Double.valueOf(specVo.getPrice()));
            specFood.setPacking_fee(Double.valueOf(specVo.getPacking_fee()));
            specFood.setSpecs_name(specVo.getSpecs());
            specList.add(specFood);
        }
        List<String> attributes = Json.fromJsonAsList(String.class, foodVo.getAttributesJson());
        List<Map> attributes1 = Lists.newArrayList();
        for (String attribute : attributes) {
            switch (attribute) {
                case "新":
                    attributes1.add(Maps.newHashMap(
                            "icon_color", "5ec452",
                            "icon_name", "新"
                    ));

                    break;
                case "招牌":
                    attributes1.add(Maps.newHashMap(
                            "icon_color", "f07373",
                            "icon_name", "'招牌'"
                    ));
                    break;
            }

        }
        food.setAttributes(attributes1);
        food.setDescription(foodVo.getDescript());
        food.setSpecfoods(specList);
        List specifications = Lists.newArrayList();
        List<String> specificationValues = Lists.newArrayList();
        if (!specList.isEmpty()) {
            for (SpecFood specFood : specList) {
                specificationValues.add(specFood.getName());
            }
            specifications.add(Maps.newHashMap("name", "规格", "values", specificationValues));
        }
        food.setSpecifications(specifications);
        food.setItem_id(idsService.getId(Ids.ITEM_ID));
        setTips(food);
        food.setPinyin_name(StringUtils.getPingYin(food.getName()));
        food.setSatisfy_rate(0.0);
        food.setSatisfy_count(500.0);
        food.setRating(0.0);
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        if (!accountInfo.isMgr()) {
            food.setState(Food.STATE_ING);
        } else {
            food.setState(Food.STATE_YES);
        }
        mongoRepository.save(food);
        return Rets.success();
    }

    @RequestMapping(value = "/v2/foods", method = RequestMethod.GET)
    public Object list(@RequestParam(value = "state", required = false) String state,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "restaurant_id", required = false) Long restaurantId) {
        Page<Food> page = new PageFactory<Food>().defaultPage();
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        Map params = Maps.newHashMap();
        shopNameMap   = Maps.newHashMap();

        if (StringUtils.isNotEmpty(state)) {
            params.put("state", state);
        }
        if (StringUtils.isNotEmpty(name)) {
            params.put("name", name);
        }
        if (Constants.USER_TYPE_MGR.equals(accountInfo.getUserType())) {
            if (StringUtils.isNullOrEmpty(restaurantId) || "undefined".equals(restaurantId)) {
                page = mongoRepository.queryPage(page, Food.class, params);
            } else {
                params.put("restaurant_id", restaurantId);
                page = mongoRepository.queryPage(page, Food.class, params);
            }
        } else if (Constants.USER_TYPE_SHOP.equals(accountInfo.getUserType())) {
            params.put("restaurant_id", accountInfo.getUserId());
            page = mongoRepository.queryPage(page, Food.class, params);
        }
        List<Food> foods = page.getRecords();
        for(int i=0;i<foods.size();i++){
            foods.get(i).setShopName(getShopName(foods.get(i).getRestaurant_id()));
        }
        return Rets.success(page);
    }
    Map<Long, String> shopNameMap = Maps.newHashMap();
    private String getShopName(Long shopId){
        String shopname = shopNameMap.get(shopId);
        if(shopname==null){
            Shop shop = mongoRepository.findOne(Shop.class,shopId);
            if(shop!=null){
                shopname = shop.getName();
                shopNameMap.put(shopId,shopname);
            }
        }
        return shopname;
    }

    @RequestMapping(value = "/v2/foods/count", method = RequestMethod.GET)

    public Object count() {
        long count = mongoRepository.count("foods");
        return Rets.success("count", count);
    }
    @GetMapping(value = "/v2/food/{item_id}")
    public Object get(@PathVariable("item_id") Long id) {
        Food food =mongoRepository.findOne(Food.class, Maps.newHashMap("item_id",id));
        return Rets.success(food);
    }

    /**
     * 删除食品
     * todo 不建议使用该功能，且该功能为完善，删除食品的时候应该将对应菜单中的食品也下架，所以计划将该功能调整为将食品下架
     * @param id
     * @return
     */
    @RequestMapping(value = "/v2/food/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable("id") Long id) {
        mongoRepository.delete("foods", Maps.newHashMap("item_id", id));
        return Rets.success();
    }

    @RequestMapping(value = "/v2/updatefood", method = RequestMethod.POST)
    public Object update(@ModelAttribute @Valid FoodVo food) {
        List<SpecVo> specVoList = Json.fromJsonAsList(SpecVo.class, food.getSpecsJson());
        List<SpecFood> specList = Lists.newArrayList();
        for (SpecVo specVo : specVoList) {
            SpecFood specFood = new SpecFood();
            specFood.setName(specVo.getSpecs());
            specFood.setPrice(Double.valueOf(specVo.getPrice()));
            specFood.setPacking_fee(Double.valueOf(specVo.getPacking_fee()));
            specFood.setSpecs_name(specVo.getSpecs());
            specList.add(specFood);
        }
        Food old = mongoRepository.findOne(Food.class, "item_id", food.getId());
        old.setName(food.getName());
        old.setPinyin_name(StringUtils.getPingYin(food.getName()));
        old.setDescription(food.getDescript());
        old.setCategory_id(food.getIdMenu());
        old.setImage_path(food.getImagePath());
        old.setSpecfoods(specList);

        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        if (!accountInfo.isMgr()) {
            old.setState(Food.STATE_ING);
        } else {
            old.setState(Food.STATE_YES);
        }
        mongoRepository.update(old);
        Menu menu = mongoRepository.findOne(Menu.class, old.getCategory_id());
        List<Food> foods = menu.getFoods();
        for (Food rec : foods) {
            if (rec.getItem_id().intValue() == old.getItem_id().intValue()) {
                foods.remove(rec);
                break;
            }
        }

        mongoRepository.update(menu);

        return Rets.success();
    }

    /**
     * 审核食品，通过，则加入菜单
     *
     * @param food
     * @return
     */
    @RequestMapping(value = "/auditFood", method = RequestMethod.POST)
    public Object auditFood(@ModelAttribute Food food) {
        Food old = mongoRepository.findOne(Food.class, "item_id", food.getItem_id());
        old.setState(food.getState());
        old.setAuditRemark(food.getAuditRemark());
        mongoRepository.update(old);
        if (Food.STATE_YES.equals(food.getState())) {
            Menu menu = mongoRepository.findOne(Menu.class, old.getCategory_id());
            List<Food> foods = menu.getFoods();
            for (Food rec : foods) {
                if (rec.getItem_id().intValue() == old.getItem_id().intValue()) {
                    foods.remove(rec);
                    break;
                }
            }
            menu.getFoods().add(old);

            mongoRepository.update(menu);
        }
        return Rets.success();
    }

    private void setTips(Food food) {
        Double ratingCount =0.0;
        Double monthSales =0.0;
        food.setRating_count(ratingCount.intValue());
        food.setMonth_sales(monthSales.intValue());
        food.setTips(ratingCount.intValue() + "评价 月售" + monthSales.intValue() + "份");


    }

    private SpecFood buidSpecFood(Food food) {
        SpecFood specFood = new SpecFood();
        specFood.setItem_id(food.getItem_id());
        specFood.setFood_id(idsService.getId(Ids.FOOD_ID));
        specFood.setName(food.getName());
        specFood.setRestaurant_id(food.getRestaurant_id());
        BigDecimal recentRating = new BigDecimal(Math.random() * 5).setScale(BigDecimal.ROUND_HALF_DOWN, 1);
        specFood.setRecent_rating(recentRating.doubleValue());
        BigDecimal recentPopularity = new BigDecimal(Math.random() * 1000).setScale(BigDecimal.ROUND_HALF_DOWN, 1);
        specFood.setRecent_popularity(recentPopularity.doubleValue());
        specFood.setSpecs(new ArrayList<KeyValue>());
        return specFood;

    }


}
