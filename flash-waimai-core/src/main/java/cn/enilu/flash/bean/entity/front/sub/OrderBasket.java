package cn.enilu.flash.bean.entity.front.sub;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@Data
public class OrderBasket {

    private List<OrderFee> abandoned_extra = new ArrayList<OrderFee>();
    private OrderFee deliver_fee = new OrderFee();
    private OrderFee packing_fee = new OrderFee();
    private List extra = new ArrayList();
    private List pindan_map = new ArrayList();
    private List<List<OrderGroup>> group = new ArrayList<List<OrderGroup>>();

}
