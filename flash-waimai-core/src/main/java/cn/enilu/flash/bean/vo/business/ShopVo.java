package cn.enilu.flash.bean.vo.business;

import cn.enilu.flash.bean.entity.front.Shop;
import lombok.Data;

/**
 * @author ：enilu
 * @date ：Created in 2019/9/8 19:21
 */
@Data
public class ShopVo extends Shop {

    private String activitiesJson;
    private Boolean deliveryMode;
    private Boolean news;
    private Boolean bao;
    private Boolean zhun;
    private Boolean piao;
    private String image_path;
    private String business_license_image;
    private String catering_service_license_image;
    private String startTime;
    private String endTime;
}


//name: 小梦鲜花
//        address: 上海市杨浦区昆明路739号
//        description: 店铺简洁
//        phone: 15029292929
//        promotion_info: 店铺标语
//        float_delivery_fee: 5
//        float_minimum_order_amount: 20
//        is_premium: true
//        delivery_mode: true
//        new: true
//        bao: true
//        zhun: true
//        piao: true
//        startTime: 05:45
//        endTime: 10:30
//        image_path: 27c8c711-dec4-45de-ba13-cad3c62299bf.jpg
//        business_license_image: 3ec157ee-d647-4cb7-b522-1907fdcf6062.jpg
//        catering_service_license_image: f3dea320-c3c1-4478-9e5a-71234ca707b7.jpg
//        cateringServiceLicenseImage: http://127.0.0.1:8082/file/getImgStream?fileName=f3dea320-c3c1-4478-9e5a-71234ca707b7.jpg
//        businessLicenseImage: http://127.0.0.1:8082/file/getImgStream?fileName=3ec157ee-d647-4cb7-b522-1907fdcf6062.jpg
//        imagePath: http://127.0.0.1:8082/file/getImgStream?fileName=27c8c711-dec4-45de-ba13-cad3c62299bf.jpg
//        category: 快餐便当/简餐
//        activitiesJson: [{"icon_name":"减","name":"满减优惠","description":"满30减5，满60减8"}]