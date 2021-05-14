package cn.enilu.flash.bean.vo.wechat;

import lombok.Data;

/**
 * 小程序用户信息
 *
 * @Author enilu
 * @Date 2021/4/24 20:41
 * @Version 1.0
 */
@Data
public class MiniProgramUserInfo {
    private String openid;
    private String unionid;
    private Integer errcode;
    private String sessionKey;
    private String errmsg;
}
