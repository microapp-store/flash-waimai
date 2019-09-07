package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.front.Food;
import cn.enilu.flash.bean.entity.front.Ids;
import cn.enilu.flash.bean.entity.front.KeyValue;
import cn.enilu.flash.bean.entity.front.SpecFood;
import cn.enilu.flash.bean.vo.business.FoodVo;
import cn.enilu.flash.bean.vo.business.SpecVo;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.service.front.IdsService;
import cn.enilu.flash.utils.Lists;
import cn.enilu.flash.utils.Maps;
import cn.enilu.flash.utils.StringUtils;
import cn.enilu.flash.utils.factory.Page;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "addfood",method = RequestMethod.GET)

    public Object add(HttpServletRequest request) {
        String json = getRequestPayload();
        Food food = Json.fromJson(Food.class, json);
        food.setItem_id(idsService.getId(Ids.ITEM_ID));
        List<SpecFood> specFoods = new ArrayList<SpecFood>(2);
        specFoods.add(buidSpecFood(food));
        food.setSpecfoods(specFoods);
        setTips(food);
        food.setSatisfy_rate(new BigDecimal(Math.ceil(Math.random() * 100)).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
        food.setSatisfy_count(new BigDecimal(Math.ceil(Math.random() * 1000)).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
        food.setRating(new BigDecimal(Math.random() * 5).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue());
        mongoRepository.save(food);
        return Rets.success();
    }
    @RequestMapping(value="/v2/foods",method = RequestMethod.GET)
    public Object list(@RequestParam(value = "restaurant_id",required = false) Long restaurantId
                       ) {
        //restaurantId="11";
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
    //todo 未完成
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
        System.out.println(Json.toJson(food));

        old.setName(food.getName());
        old.setDescription(food.getDescript());
        old.setCategory_id(food.getIdMenu());
        old.setImage_path(food.getImagePath());
        old.setSpecfoods(specList);
        System.out.println(Json.toJson(old));
        mongoRepository.update(old);
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
