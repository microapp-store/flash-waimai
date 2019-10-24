package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.front.Food;
import cn.enilu.flash.bean.entity.front.Ids;
import cn.enilu.flash.bean.entity.front.KeyValue;
import cn.enilu.flash.bean.entity.front.Menu;
import cn.enilu.flash.bean.entity.front.SpecFood;
import cn.enilu.flash.bean.vo.business.FoodVo;
import cn.enilu.flash.bean.vo.business.SpecVo;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.service.front.IdsService;
import cn.enilu.flash.utils.BeanUtil;
import cn.enilu.flash.utils.Lists;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.StringUtils;
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
 * @author zt
 */
@RestController
@RequestMapping("/shopping")
public class FoodController extends BaseController {

    @Autowired
    private MongoRepository mongoRepository;

    @Autowired
    private IdsService idsService;
    private Logger logger = LoggerFactory.getLogger(FoodController.class);

    @RequestMapping(value = "addfood",method = RequestMethod.POST)
    public Object add(@Valid @ModelAttribute FoodVo foodVo) {
        logger.info(Json.toJson(foodVo));
        Food food = new Food();
        BeanUtil.copyProperties(foodVo,food);
        food.setRestaurant_id(foodVo.getIdShop());
        List<SpecVo> specVoList = Json.fromJsonAsList(SpecVo.class,foodVo.getSpecsJson());
        List<SpecFood> specList = Lists.newArrayList();
        for(SpecVo specVo:specVoList){
            SpecFood specFood = new SpecFood();
            specFood.setName(specVo.getSpecs());
            specFood.setPinyin_name(StringUtils.getPingYin(specVo.getSpecs()));
            specFood.setPrice(Double.valueOf(specVo.getPrice()));
            specFood.setPacking_fee(Double.valueOf(specVo.getPacking_fee()));
            specFood.setSpecs_name(specVo.getSpecs());
            specList.add(specFood);
        }
        List<String> attributes = Json.fromJsonAsList(String.class,foodVo.getAttributesJson());
        List<Map> attributes1 = Lists.newArrayList();
        for(String attribute:attributes){
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
        List specifications =  Lists.newArrayList();
        List<String> specificationValues = Lists.newArrayList();
        if(!specList.isEmpty()) {
            for (SpecFood specFood : specList) {
                specificationValues.add(specFood.getName());
            }
            specifications.add(Maps.newHashMap("name","规格","values",specificationValues));
        }
        food.setSpecifications(specifications);
        food.setItem_id(idsService.getId(Ids.ITEM_ID));
        setTips(food);
        food.setPinyin_name(StringUtils.getPingYin(food.getName()));
        food.setSatisfy_rate(new BigDecimal(Math.ceil(Math.random() * 100)).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
        food.setSatisfy_count(new BigDecimal(Math.ceil(Math.random() * 1000)).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
        food.setRating(new BigDecimal(Math.random() * 5).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());        
        mongoRepository.save(food);

        Menu menu = mongoRepository.findOne(Menu.class,food.getCategory_id());
        menu.getFoods().add(food);
        mongoRepository.update(menu);
        return Rets.success();
    }
    @RequestMapping(value="/v2/foods",method = RequestMethod.GET)
    public Object list(@RequestParam(value = "restaurant_id",required = false) Long restaurantId) {
        Page<Food> page = new PageFactory<Food>().defaultPage();
        if (StringUtils.isNullOrEmpty(restaurantId) || "undefined".equals(restaurantId)) {
            return Rets.success(mongoRepository.queryPage(page,Food.class));
        } else {
            return Rets.success(mongoRepository.queryPage(page,Food.class,Maps.newHashMap("restaurant_id",restaurantId)));
        }
    }

    @RequestMapping(value = "/v2/foods/count",method = RequestMethod.GET)

    public Object count() {
        long count = mongoRepository.count("foods");
        return Rets.success("count", count);
    }
    @RequestMapping(value = "/v2/food/{id}",method = RequestMethod.DELETE)
    public Object delete(@PathVariable("id") Long id) {
         mongoRepository.delete("foods", Maps.newHashMap("item_id",id));
        return Rets.success();
    }

    @RequestMapping(value = "/v2/updatefood",method = RequestMethod.POST)
    public Object update(@ModelAttribute @Valid FoodVo food){
        List<SpecVo> specVoList = Json.fromJsonAsList(SpecVo.class,food.getSpecsJson());
        List<SpecFood> specList = Lists.newArrayList();
        for(SpecVo specVo:specVoList){
            SpecFood specFood = new SpecFood();
            specFood.setName(specVo.getSpecs());
            specFood.setPrice(Double.valueOf(specVo.getPrice()));
            specFood.setPacking_fee(Double.valueOf(specVo.getPacking_fee()));
            specFood.setSpecs_name(specVo.getSpecs());
            specList.add(specFood);
        }
        Food old = mongoRepository.findOne(Food.class,"item_id",food.getId());
        old.setName(food.getName());
        old.setPinyin_name(StringUtils.getPingYin(food.getName()));
        old.setDescription(food.getDescript());
        old.setCategory_id(food.getIdMenu());
        old.setImage_path(food.getImagePath());
        old.setSpecfoods(specList);
        mongoRepository.update(old);
        Menu menu = mongoRepository.findOne(Menu.class,old.getCategory_id());
        for(Food item:menu.getFoods()){
            if(item.getItem_id().intValue() == old.getItem_id().intValue()){
                menu.getFoods().remove(item);
                menu.getFoods().add(old);
                break;
            }
        }
        mongoRepository.update(menu);
        return Rets.success();
    }






    private void setTips(Food food) {
        Double ratingCount = Math.ceil(Math.random() * 1000);
        Double monthSales = Math.ceil(Math.random() * 1000);
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
