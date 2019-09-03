package cn.enilu.elm.api.controller;

import cn.enilu.elm.api.utils.CaptchaCode;
import cn.enilu.elm.api.vo.Rets;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created  on 2018/1/5 0005.
 *
 * @author zt
 */
@RestController
public class CaptchaController extends BaseController {
    @RequestMapping(value = "/v1/captchas",method = RequestMethod.POST)
    public Object get() throws IOException {
        ByteArrayOutputStream outputStream =  new ByteArrayOutputStream();
        Map<String,Object> map = CaptchaCode.getImageCode(60, 20, outputStream);

        setSession(CaptchaCode.CAPTCH_KEY, map.get("strEnsure").toString().toLowerCase());
        setSession("codeTime",System.currentTimeMillis());
        try {
            ImageIO.write((BufferedImage) map.get("image"), "png", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(outputStream.toByteArray());
            String captchaBase64 = "data:image/png;base64," + base64.replaceAll("\r\n", "");
           return Rets.success("code",captchaBase64);
        } catch (IOException e) {
           return Rets.failure(e.getMessage());
        }

    }
}
