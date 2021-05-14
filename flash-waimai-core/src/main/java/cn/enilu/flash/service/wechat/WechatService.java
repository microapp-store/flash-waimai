package cn.enilu.flash.service.wechat;

import cn.enilu.flash.bean.enumeration.ConfigKeyEnum;
import cn.enilu.flash.bean.vo.wechat.MiniProgramUserInfo;
import cn.enilu.flash.service.system.CfgService;
import cn.enilu.flash.utils.StringUtils;
import org.nutz.http.Http;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 微信后台服务
 *
 * @Author enilu
 * @Date 2021/4/24 20:09
 * @Version 1.0
 */
@Service
public class WechatService {
    private Logger logger = LoggerFactory.getLogger(WechatService.class);
    @Autowired
    private CfgService cfgService;

    public MiniProgramUserInfo code2Session(String code) {
//        https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String appId = cfgService.getCfgValue(ConfigKeyEnum.API_TENCENT_MINI_PROGRAM_APPID.getValue());
        String appSecret = cfgService.getCfgValue(ConfigKeyEnum.API_TENCENT_MINI_PROGRAM_APPSECRET.getValue());
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        url += "?appid=" + appId;
        url += "&secret=" + appSecret;
        url += "&js_code=" + code;
        url += "&grant_type=authorization_code";

        try {
            String result = Http.get(url).getContent();

            Map object = (Map) Json.fromJson(result);
            logger.info("url:" + url + ";  response:" + Json.toJson(object));
            String errcode = object.get("errcode")==null?"":object.get("errcode").toString();
            MiniProgramUserInfo miniProgramUserInfo = new MiniProgramUserInfo();
            if("0".equals(errcode) || StringUtils.isEmpty(errcode)){

                miniProgramUserInfo.setOpenid(object.get("openid").toString());
                miniProgramUserInfo.setSessionKey(object.get("session_key").toString());
                miniProgramUserInfo.setUnionid(Strings.sNull(object.get("unionid")));
                miniProgramUserInfo.setErrcode(0);

            }else{
                miniProgramUserInfo.setErrcode(Integer.valueOf(errcode));
                miniProgramUserInfo.setErrmsg(object.get("errmsg").toString());
            }
            return miniProgramUserInfo;
        } catch (Exception e) {
            logger.error("获取token失败", e);
        }
        return null;

    }
}
