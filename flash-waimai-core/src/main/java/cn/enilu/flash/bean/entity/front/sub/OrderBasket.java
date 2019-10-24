package cn.enilu.flash.bean.entity.front.sub;

import cn.enilu.flash.utils.Maps;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@Data
public class OrderBasket {

    private List<OrderFee> abandoned_extra = new ArrayList<OrderFee>();
    private OrderFee deliver_fee = new OrderFee();
    private Map packing_fee = Maps.newHashMap();
    private List extra = new ArrayList();
    private List pindan_map = new ArrayList();
    private List<List<OrderItem>> group = new ArrayList<List<OrderItem>>();

}
