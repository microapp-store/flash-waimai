package cn.enilu.flash.bean.entity.front.sub;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@Data
public class OrderGroup {
    @Id
    private String _id;
    private String name;
    private Double price;
    private Double quantity;

    private List specs;
    private List attrs;
    private List new_specs;


}
