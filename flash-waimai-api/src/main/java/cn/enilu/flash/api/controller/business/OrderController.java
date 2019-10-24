package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.front.Carts;
import cn.enilu.flash.bean.entity.front.Ids;
import cn.enilu.flash.bean.entity.front.Order;
import cn.enilu.flash.bean.entity.front.Shop;
import cn.enilu.flash.bean.entity.front.sub.OrderBasket;
import cn.enilu.flash.bean.entity.front.sub.OrderFee;
import cn.enilu.flash.bean.entity.front.sub.OrderStatusBar;
import cn.enilu.flash.bean.vo.business.OrderVo;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.service.front.IdsService;
import cn.enilu.flash.utils.*;
import cn.enilu.flash.utils.factory.Page;
import org.nutz.lang.Strings;
import org.nutz.mapl.Mapl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@RestController
public class OrderController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;
    @RequestMapping(value = "/bos/v2/users/{user_id}/orders" ,method = RequestMethod.GET)
    public Object orders(@PathVariable("user_id")Long userId,@RequestParam("limit") Integer limit,
    @RequestParam("offset")Integer offset){
        Page<Order> page = new PageFactory<Order>().defaultPage();
       return Rets.success(mongoRepository.queryPage(page, Order.class,Maps.newHashMap("user_id", userId)));


    }
    @RequestMapping(value = "/bos/orders/count",method = RequestMethod.GET)
    public Object count(@RequestParam("restaurant_id")String restaurantId){
        long count = 0;
        if(Strings.isBlank(restaurantId)&&Strings.equals("undefined",restaurantId)){
            count = mongoRepository.count(Order.class, Maps.newHashMap("restaurant_id",Long.valueOf(restaurantId)));
        }else {
             count = mongoRepository.count(Order.class);
        }
        return Rets.success("count",count);
    }
    @RequestMapping(value="/bos/orders",method = RequestMethod.GET)
    public Object list(@RequestParam(value = "restaurant_id",required = false) Long restaurantId,
                       @RequestParam(value = "id",required = false) Long orderId) {
        Page<Order> page =  new PageFactory<Order>().defaultPage();
        Map<String,Object> params = Maps.newHashMap();
        if (restaurantId!=null){
            params.put("restaurant_id",restaurantId);
        }
        if (orderId!=null){
            params.put("id",orderId);
        }
        page = mongoRepository.queryPage(page,Order.class,params);
        return Rets.success(page);
    }
    @RequestMapping(value="/v1/users/{userId}/carts/{cartId}/orders")
    public Object save(@PathVariable("userId")Long userId,@PathVariable("cartId")Long cartId){
        OrderVo orderVo = getRequestPayload(OrderVo.class);
        //获取购物车信息
        Carts cart = mongoRepository.findOne(Carts.class,cartId);
        Date createTime = new Date();
        Shop shop = mongoRepository.findOne(Shop.class,cart.getCart().getRestaurant_id());
        Order order = new Order();
        order.setId(idsService.getId(Ids.ORDER_ID));
        order.setRestaurant_id(shop.getId());
        order.setRestaurant_name(shop.getName());
        order.setRestaurant_image_url(shop.getImage_path());
        order.setFormatted_create_at(DateUtil.format(createTime,"yyyy-MM-dd HH:mm"));
        order.setOrder_time(createTime.getTime());
        order.setTime_pass(900);
        OrderBasket basket = order.getBasket();
        basket.setGroup(cart.getCart().getGroups());
        if(!cart.getCart().getExtra().isEmpty()){
              cart.getCart().getExtra().get(0);
            OrderFee orderFee = (OrderFee) Mapl.maplistToObj( cart.getCart().getExtra().get(0),OrderFee.class);
            basket.setDeliver_fee( orderFee);
            basket.setPacking_fee(Maps.newHashMap("price",cart.getCart().getDeliver_amount()));

        }
        order.setBasket(basket);
        OrderStatusBar statusBar = new OrderStatusBar();
        statusBar.setColor("f60");
        statusBar.setSub_title("15分钟内支付");
        statusBar.setTitle("等待支付");
        order.setStatus_bar(statusBar);
        order.setTotal_amount(Integer.valueOf(cart.getCart().getTotal()));
        order.setTotal_quantity(Integer.valueOf(basket.getGroup().get(0).size()));
        order.setUnique_id(order.getId());
        order.setUser_id(userId);
        order.setAddress_id(Long.valueOf(orderVo.getAddress_id()));
        mongoRepository.save(order);
        return Rets.success();
    }

}
