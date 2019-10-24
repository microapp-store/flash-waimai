package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.entity.front.*;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.service.front.IdsService;
import cn.enilu.flash.service.front.PositionService;
import cn.enilu.flash.utils.Lists;
import cn.enilu.flash.utils.Maps;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@RestController
public class CartController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;
    @Autowired
    private PositionService positionService;

    @RequestMapping(value = "/v1/carts/checkout", method = RequestMethod.POST)
    public Object checkout(HttpServletRequest request) {

        Map data = getRequestPayload(Map.class);
        System.out.println(Json.toJson(data));
        String from = data.get("geohash").toString();
        Long restaurantId = Long.valueOf(data.get("restaurant_id").toString());
        Carts carts = new Carts();
        List<Payment> paymentList = mongoRepository.findAll(Payment.class);
        Shop shop = mongoRepository.findOne(Shop.class, restaurantId);
        String to = shop.getLatitude() + "," + shop.getLongitude();
        Map distance = positionService.getDistance(from, to);
        String deliver_time = distance != null ? distance.get("duration").toString() : "";
        carts.setDelivery_reach_time(deliver_time);
        carts.setId(idsService.getId(Ids.CART_ID));
        carts.setPayments(paymentList);
        carts.setSig(String.valueOf(Math.ceil(Math.random() * 1000000)));
        carts.setInvoice(Maps.newHashMap("status_text", "不需要开发票", "is_available", true));

        Cart cart = new Cart();
        cart.setId(carts.getId());
        cart.setRestaurant_id(restaurantId);
        cart.setRestaurant_info(shop);
        cart.setDeliver_amount(4);
        List<List> entities = (List<List>) data.get("entities");
        List<List> groups = Lists.newArrayList(Lists.newArrayList());
        BigDecimal total = new BigDecimal(0);
        List extraList = Lists.newArrayList();
        Map extra = Maps.newHashMap(
                "description", "",
                "name", "",
                "price", 0,
                "quantity", 1,
                "type", 0);
        for (int i = 0; i < entities.get(0).size(); i++) {
            Map map = (Map) entities.get(0).get(i);
            Map items = Maps.newHashMap();
            items.put("id", Long.valueOf(map.get("id").toString()));
            items.put("name", map.get("name"));
            items.put("packing_fee", map.get("packing_fee"));
            items.put("price", map.get("price"));
            items.put("quantity", map.get("quantity"));
            Double amount= Double.valueOf(map.get("packing_fee").toString()) * Integer.valueOf(map.get("quantity").toString());
            extra = Maps.newHashMap(
                    "description", "",
                    "name", extra.get("name").toString() + " " + map.get("name").toString() + "-" + ((List) map.get("specs")).get(0),
                    "price", Double.valueOf(extra.get("price").toString()) + amount,
                    "quantity", Integer.valueOf(extra.get("quantity").toString()) + Integer.valueOf(map.get("quantity").toString()),
                    "type", 0
            );

            total = total.add(new BigDecimal(items.get("price").toString()).multiply(new BigDecimal(items.get("quantity").toString())));

            groups.get(0).add(items);
        }

        extraList.add(extra);
        cart.setExtra(extraList);

        cart.setTotal(total.toPlainString());
        cart.setGroups(groups);
        carts.setCart(cart);

        //todo 暂时不保存,开发中
        mongoRepository.save(carts, "carts");
        return Rets.success(carts);
    }

    @RequestMapping(value = "/v1/carts/{cart_id}/remarks", method = RequestMethod.GET)
    public Object remarks(@PathVariable("cart_id") Long cartId, @RequestParam(value = "sig", required = false) String sig) {
        return Rets.success(mongoRepository.findOne("remarks"));
    }
}
