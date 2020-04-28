package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.cache.TokenCache;
import cn.enilu.flash.utils.CaptchaCode;
import cn.enilu.flash.utils.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@RestController
public class CaptchaController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CaptchaController.class);
    @Autowired
    private TokenCache tokenCache;

    @RequestMapping(value = "/v1/captchas", method = RequestMethod.POST)
    public Object get() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Map<String, Object> map = CaptchaCode.getImageCode(60, 20, outputStream);
        String captchCodeId = UUID.randomUUID().toString();
        tokenCache.put(captchCodeId, map.get("strEnsure").toString().toLowerCase());
        logger.info("captchCode:{}", map.get("strEnsure").toString().toLowerCase());
        try {
            ImageIO.write((BufferedImage) map.get("image"), "png", outputStream);
            Base64.Encoder encoder = Base64.getEncoder();
            String base64 =  new String(encoder.encode(outputStream.toByteArray()));;
            String captchaBase64 = "data:image/png;base64," + base64.replaceAll("\r\n", "");
            return Rets.success(Maps.newHashMap("captchCodeId",captchCodeId,"code",captchaBase64));
        } catch (IOException e) {
            return Rets.failure(e.getMessage());
        }

    }
}
