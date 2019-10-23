package cn.enilu.flash.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
@Component
@Data
public class AppConfiguration {
    @Value("${api.qq.map.url}")
    private String qqApiUrl;
    @Value("${cfg.tencentkey}")
    private String tencentKey;
    @Value("${cfg.tencentkey2}")
    private String tencentKey2;
    @Value("${cfg.tencentkey3}")
    private String tencentKey3;
    @Value("${cfg.baidu.map.url}")
    private String baiduApiUrl;
    @Value(("${cfg.baidu.key}"))
    private String baiduKey;
    @Value(("${cfg.baidu.key2}"))
    private String baiduKey2;
    @Value("${img.dir}")
    private String imgDir;

}
