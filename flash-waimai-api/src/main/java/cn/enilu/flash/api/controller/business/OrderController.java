package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.front.*;
import cn.enilu.flash.bean.entity.front.sub.OrderBasket;
import cn.enilu.flash.bean.entity.front.sub.OrderFee;
import cn.enilu.flash.bean.entity.front.sub.OrderItem;
import cn.enilu.flash.bean.entity.front.sub.OrderStatusBar;
import cn.enilu.flash.bean.vo.business.OrderVo;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.security.AccountInfo;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.service.front.IdsService;
import cn.enilu.flash.utils.*;
import cn.enilu.flash.utils.factory.Page;
import org.nutz.lang.Strings;
import org.nutz.mapl.Mapl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created  on 2018/1/5 0005.
 *
 *@Author enilu
 */
@RestController
public class OrderController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;

    @RequestMapping(value = "/bos/v2/users/{user_id}/orders", method = RequestMethod.GET)
    public Object orders(@PathVariable("user_id") Long userId, @RequestParam("limit") Integer limit,
                         @RequestParam("offset") Integer offset) {
        Page<Order> page = new PageFactory<Order>().defaultPage();
        page = mongoRepository.queryPage(page, Order.class, Maps.newHashMap("user_id", userId));
        return Rets.success(page);


    }

    @RequestMapping(value = "/bos/orders/count", method = RequestMethod.GET)
    public Object count(@RequestParam("restaurant_id") String restaurantId) {
        long count = 0;
        if (Strings.isBlank(restaurantId) && Strings.equals("undefined", restaurantId)) {
            count = mongoRepository.count(Order.class, Maps.newHashMap("restaurant_id", Long.valueOf(restaurantId)));
        } else {
            count = mongoRepository.count(Order.class);
        }
        return Rets.success("count", count);
    }

    @RequestMapping(value = "/bos/orders", method = RequestMethod.GET)
    public Object list(@RequestParam(value = "restaurant_id", required = false) Long restaurantId,
                       @RequestParam(value = "id", required = false) Long orderId) {
        Page<Order> page = new PageFactory<Order>().defaultPage();
        Map<String, Object> params = Maps.newHashMap();
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        if (Constants.USER_TYPE_MGR.equals(accountInfo.getUserType())) {
            if (restaurantId != null) {
                params.put("restaurant_id", restaurantId);
            }
            if (orderId != null) {
                params.put("id", orderId);
            }
        } else if (Constants.USER_TYPE_SHOP.equals(accountInfo.getUserType())) {
            params.put("restaurant_id", accountInfo.getUserId());
        }
        page = mongoRepository.queryPage(page, Order.class, params);
        return Rets.success(page);
    }

    @RequestMapping(value = "/bos/getOrder", method = RequestMethod.GET)
    public Object getOrder(
            @RequestParam(value = "id", required = false) Long orderId) {
        Order order = mongoRepository.findOne(Order.class, orderId);
        OrderBasket basket = order.getBasket();
        List<List<OrderItem>>  groups = basket.getGroup();
        List<Map> orderItems = Lists.newArrayList();
        for(int i=0;i<groups.size();i++){

            List<OrderItem> list = groups.get(i);
            for(OrderItem orderItem:list){
                Map item = Maps.newHashMap();
                Food food = mongoRepository.findOne(Food.class,Maps.newHashMap("item_id",orderItem.getId()));
                if(food!=null) {
                    item.put("foodName",  orderItem.getName() );
                    item.put("image_path", food.getImage_path());
                }else{
                    item.put("foodName",orderItem.getName());
                }
                item.put("price",orderItem.getPrice());
                item.put("quantity",orderItem.getQuantity());
                item.put("spec",orderItem.getName());
                item.put("packingFee",orderItem.getPacking_fee()==null?0:orderItem.getPacking_fee());
                item.put("totalPrice",orderItem.getPrice().intValue()*orderItem.getQuantity().intValue()+(orderItem.getPacking_fee()==null?0:orderItem.getPacking_fee()));
                orderItems.add(item);
            }
        }

        Address address = mongoRepository.findOne(Address.class,order.getAddress_id());
        FrontUser user = mongoRepository.findOne(FrontUser.class,Maps.newHashMap("user_id",order.getUser_id()));
        Map ret = Maps.newHashMap(
                "order",order,
                "address",address,
                "user",user,
                "orderItems",orderItems
        );
        return Rets.success(ret);
    }

    /**
     * 修改订单状态
     * @param orderId
     * @param status
     * @return
     */
    @RequestMapping(value = "/bos/updateOrderStatus", method = RequestMethod.POST)
    public Object updateOrderStatus(
            @RequestParam(value = "id", required = false) Long orderId,
            @RequestParam(value = "status", required = false) Integer status) {
        Order order = mongoRepository.findOne(Order.class, orderId);
        OrderStatusBar statusBar = order.getStatus_bar();
        statusBar.setTitle(Order.getStatusCodeStr(status));
        statusBar.setSub_title("");
        order.setStatus_bar(statusBar);
        order.setStatus_code(status);
        mongoRepository.update(order);
        return Rets.success(order);
    }

    /**
     * 手机端用户确认完成订单
     * 将当前订单金额加入到商户未结算金额
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/bos/v1/users/{user_id}/orders/{orderId}/finish", method = RequestMethod.GET)
    public Object finishorder(@PathVariable("user_id") Long userId,
                              @PathVariable("orderId") Long orderId) {


        Order order = mongoRepository.findOne(Order.class, orderId);
        if(order.getUser_id().intValue()!=userId.intValue()){
            return Rets.failure("无权操作该订单");
        }
        OrderStatusBar statusBar = order.getStatus_bar();
        statusBar.setTitle(Order.getStatusCodeStr(Order.STATUS_DONE));
        statusBar.setSub_title("");
        order.setStatus_bar(statusBar);
        order.setStatus_code(Order.STATUS_DONE);
        mongoRepository.update(order);

        //订单金额加入到商铺未结算金额
        Shop shop = mongoRepository.findOne(Shop.class,order.getRestaurant_id());
        Map updateMap = Maps.newHashMap();
        String unliquidatedAmount = shop.getUnliquidatedAmount();
        if(StringUtils.isEmpty(unliquidatedAmount)){
            unliquidatedAmount = "0";
        }
        unliquidatedAmount = new BigDecimal(unliquidatedAmount).add(new BigDecimal(order.getTotal_amount())).intValue()+"";
        updateMap.put("unliquidatedAmount",unliquidatedAmount);
        mongoRepository.update(shop.getId(),"shops",updateMap);
        return Rets.success(order);


    }

    /**
     * 取消订单
     * 1，订单设置为取消状态
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/bos/v1/users/{user_id}/orders/{orderId}/cancel", method = RequestMethod.GET)
    public Object cancelOrder(@PathVariable("user_id") Long userId,
                              @PathVariable("orderId") Long orderId) {


        Order order = mongoRepository.findOne(Order.class, orderId);
        if(order.getUser_id().intValue()!=userId.intValue()){
            return Rets.failure("无权操作该订单");
        }
        OrderStatusBar statusBar = order.getStatus_bar();
        statusBar.setTitle(Order.getStatusCodeStr(Order.STATUS_CANCEL));
        statusBar.setSub_title("已取消");
        order.setStatus_bar(statusBar);
        order.setStatus_code(Order.STATUS_CANCEL);
        mongoRepository.update(order);
        return Rets.success(order);


    }
    @PostMapping(value = "/v1/users/{userId}/carts/{cartId}/orders")
    public Object save(@PathVariable("userId") Long userId, @PathVariable("cartId") Long cartId) {
        OrderVo orderVo = getRequestPayload(OrderVo.class);
        //获取购物车信息
        Carts cart = mongoRepository.findOne(Carts.class, cartId);
        Date createTime = new Date();
        Shop shop = mongoRepository.findOne(Shop.class, cart.getCart().getRestaurant_id());
        Order order = new Order();
        order.setId(idsService.getId(Ids.ORDER_ID));
        order.setRestaurant_id(shop.getId());
        order.setRestaurant_name(shop.getName());
        order.setRestaurant_image_url(shop.getImage_path());
        order.setFormatted_create_at(DateUtil.format(createTime, "yyyy-MM-dd HH:mm"));
        order.setOrder_time(createTime.getTime());
        order.setTime_pass(900);
        OrderBasket basket = order.getBasket();
        basket.setGroup(cart.getCart().getGroups());
        if (!cart.getCart().getExtra().isEmpty()) {
            cart.getCart().getExtra().get(0);
            OrderFee orderFee = (OrderFee) Mapl.maplistToObj(cart.getCart().getExtra().get(0), OrderFee.class);
            basket.setDeliver_fee(orderFee);
            basket.setPacking_fee(Maps.newHashMap("price", cart.getCart().getDeliver_amount()));

        }
        order.setBasket(basket);
        OrderStatusBar statusBar = new OrderStatusBar();
        statusBar.setColor("f60");
        statusBar.setSub_title("");
        statusBar.setTitle("已支付");
        order.setStatus_code(Order.STATUS_PAID);
        order.setStatus_bar(statusBar);
        order.setTotal_amount(Double.valueOf(cart.getCart().getTotal()).intValue());
        order.setTotal_quantity(Integer.valueOf(basket.getGroup().get(0).size()));
        order.setUnique_id(order.getId());
        order.setUser_id(userId);
        order.setAddress_id(Long.valueOf(orderVo.getAddress_id()));
        mongoRepository.save(order);
        return Rets.success();
    }

}
