package cn.enilu.elm.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
@Component
public class AppConfiguration {
    @Value("${api.qq.get.location}")
    private String apiQqGetLocation;
    @Value("${api.qq.search.place}")
    private String apiQqSearchPlace;
    @Value("${cfg.tencentkey}")
    private String tencentKey;
    @Value("${cfg.tencentkey2}")
    private String tencentKey2;
    @Value("${cfg.tencentkey3}")
    private String tencentKey3;
    @Value("${cfg.baidukey}")
    private String baiduKey;
    @Value("${cfg.baidukey2}")
    private String baiduKey2;
    @Value("${img.dir}")
    private String imgDir;

    public String getApiQqGetLocation() {
        return apiQqGetLocation;
    }

    public void setApiQqGetLocation(String apiQqGetLocation) {
        this.apiQqGetLocation = apiQqGetLocation;
    }

    public String getApiQqSearchPlace() {
        return apiQqSearchPlace;
    }

    public void setApiQqSearchPlace(String apiQqSearchPlace) {
        this.apiQqSearchPlace = apiQqSearchPlace;
    }

    public String getTencentKey() {
        return tencentKey;
    }

    public void setTencentKey(String tencentKey) {
        this.tencentKey = tencentKey;
    }

    public String getTencentKey2() {
        return tencentKey2;
    }

    public void setTencentKey2(String tencentKey2) {
        this.tencentKey2 = tencentKey2;
    }

    public String getTencentKey3() {
        return tencentKey3;
    }

    public void setTencentKey3(String tencentKey3) {
        this.tencentKey3 = tencentKey3;
    }

    public String getBaiduKey() {
        return baiduKey;
    }

    public void setBaiduKey(String baiduKey) {
        this.baiduKey = baiduKey;
    }

    public String getBaiduKey2() {
        return baiduKey2;
    }

    public void setBaiduKey2(String baiduKey2) {
        this.baiduKey2 = baiduKey2;
    }

    public String getImgDir() {
        return imgDir;
    }

    public void setImgDir(String imgDir) {
        this.imgDir = imgDir;
    }
}
