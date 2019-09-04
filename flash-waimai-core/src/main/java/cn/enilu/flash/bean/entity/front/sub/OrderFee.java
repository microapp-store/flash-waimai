package cn.enilu.flash.bean.entity.front.sub;

import lombok.Data;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@Data
public class OrderFee {
    private Long category_id;
    private String name;
    private Double price;
    private Double quantity;

}
