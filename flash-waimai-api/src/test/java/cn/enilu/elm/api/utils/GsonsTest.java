package cn.enilu.elm.api.utils;

import org.junit.Test;
import org.nutz.json.Json;

import static org.nutz.castor.castor.String2Class.map;

/**
 * Created  on 2018/1/3 0003.
 *
 * @author zt
 */
public class GsonsTest {

    @Test
    public void fromJson() throws Exception {
        String json = "{\n" +
                "    \"name\":\"星巴克\",\n" +
                "    \"address\":\"上海市杨浦区昆明路739号\",\n" +
                "    \"description\":\"爱爱爱\",\n" +
                "    \"phone\":11001100,\n" +
                "    \"promotion_info\":\"端到端\",\n" +
                "    \"float_delivery_fee\":6,\n" +
                "    \"float_minimum_order_amount\":21,\n" +
                "    \"is_premium\":true,\n" +
                "    \"delivery_mode\":true,\n" +
                "    \"new\":true,\n" +
                "    \"bao\":true,\n" +
                "    \"zhun\":true,\n" +
                "    \"piao\":true,\n" +
                "    \"startTime\":\"05:30\",\n" +
                "    \"endTime\":\"10:45\",\n" +
                "    \"image_path\":\"shop_1514965660456.jpg\",\n" +
                "    \"business_license_image\":\"shop_1514965662962.jpg\",\n" +
                "    \"catering_service_license_image\":\"shop_1514965665218.jpg\",\n" +
                "    \"activities\":[\n" +
                "        {\n" +
                "            \"icon_name\":\"减\",\n" +
                "            \"name\":\"满减优惠\",\n" +
                "            \"description\":\"满30减5，满60减8\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"icon_name\":\"特\",\n" +
                "            \"name\":\"优惠大酬宾\",\n" +
                "            \"description\":\"酬宾啦\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"category\":\"甜品饮品/咖啡\"\n" +
                "}";

        Object obj = Json.fromJson(json);
        System.out.println(map.get("item_id"));
    }
}