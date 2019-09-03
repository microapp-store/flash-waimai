package cn.enilu.elm.api.entity.sub;

import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
public class OrderBasket {

    private List<OrderFee> abandoned_extra = new ArrayList<OrderFee>();
    private OrderFee deliver_fee = new OrderFee();
    private OrderFee packing_fee = new OrderFee();
    private List extra = new ArrayList();
    private List pindan_map = new ArrayList();
    private List<List<OrderGroup>> group = new ArrayList<List<OrderGroup>>();

    public List<OrderFee> getAbandoned_extra() {
        return abandoned_extra;
    }

    public void setAbandoned_extra(List<OrderFee> abandoned_extra) {
        this.abandoned_extra = abandoned_extra;
    }

    public OrderFee getDeliver_fee() {
        return deliver_fee;
    }

    public void setDeliver_fee(OrderFee deliver_fee) {
        this.deliver_fee = deliver_fee;
    }

    public OrderFee getPacking_fee() {
        return packing_fee;
    }

    public void setPacking_fee(OrderFee packing_fee) {
        this.packing_fee = packing_fee;
    }

    public List getExtra() {
        return extra;
    }

    public void setExtra(List extra) {
        this.extra = extra;
    }

    public List getPindan_map() {
        return pindan_map;
    }

    public void setPindan_map(List pindan_map) {
        this.pindan_map = pindan_map;
    }

    public List<List<OrderGroup>> getGroup() {
        return group;
    }

    public void setGroup(List<List<OrderGroup>> group) {
        this.group = group;
    }
}
