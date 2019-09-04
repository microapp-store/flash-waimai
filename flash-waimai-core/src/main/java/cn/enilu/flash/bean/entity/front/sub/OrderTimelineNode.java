package cn.enilu.flash.bean.entity.front.sub;

import lombok.Data;

import java.util.List;

/**
 * Created  on 2018/1/7 0007.
 *
 * @author zt
 */
@Data
public class OrderTimelineNode {
    private List actions;
    private String description;
    private String sub_description;
    private String title;
    private Integer in_processing;

}
