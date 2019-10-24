package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.utils.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：microapp.store
 * @date ：Created in 2019/10/24 15:58
 */
@RestController
@RequestMapping("/payapi/payment")
public class PaymentController extends BaseController {
    @RequestMapping(value = "/queryOrder",method = RequestMethod.GET)
    public Object queryOrder(@RequestParam("merchantId") Long merchantId,
                             @RequestParam("merchantOrderNo") String merchantOrderNo,
                             @RequestParam("source") String source,
                             @RequestParam("userId") Long userId,
                             @RequestParam("version") String version){
        return Rets.success(
                Maps.newHashMap(
                        "status", 0,
                        "type","PAY_FAILED",
                        "message","暂不开放支付功能"
                )
        );
    }
}
