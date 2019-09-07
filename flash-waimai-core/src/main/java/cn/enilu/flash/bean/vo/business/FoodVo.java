package cn.enilu.flash.bean.vo.business;

import lombok.Data;

import java.util.List;

/**
 * 接收食品信息参数
 * @author ：enilu
 * @date ：Created in 2019/9/7 10:36
 */
@Data
public class FoodVo {
    private Long id;
    private String name;
    private String descript;
    private Long idMenu;
    private String imagePath;
    private Long idShop;
    private List<SpecVo> specs;
    private String specsJson;

}
