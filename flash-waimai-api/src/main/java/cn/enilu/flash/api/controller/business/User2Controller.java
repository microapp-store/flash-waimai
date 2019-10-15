package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.entity.front.FrontUser;
import cn.enilu.flash.bean.entity.front.FrontUserInfo;
import cn.enilu.flash.bean.entity.front.Ids;
import cn.enilu.flash.bean.vo.business.CityInfo;
import cn.enilu.flash.bean.vo.business.LoginVo;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.cache.TokenCache;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.service.front.IdsService;
import cn.enilu.flash.service.front.PositionService;
import cn.enilu.flash.utils.CaptchaCode;
import cn.enilu.flash.utils.DateUtil;
import cn.enilu.flash.utils.MD5;
import cn.enilu.flash.utils.Maps;
import org.nutz.lang.Strings;
import org.nutz.mapl.Mapl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zt on 2017/12/12 0012.
 */
@RestController
@RequestMapping("/v1/users")
public class User2Controller extends BaseController {
    private Logger logger = LoggerFactory.getLogger(User2Controller.class);
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private TokenCache tokenCache;

    @RequestMapping(value = "/v1/user", method = RequestMethod.GET)
    public Object getUser() {
        return getSession("currentUser");
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object getUser(@RequestParam("user_id") Long userId) {
          return Rets.success(mongoRepository.findOne(FrontUser.class,"user_id",userId));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        List list = mongoRepository.findAll("userinfos");
        return list;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Object count() {
        return Rets.success("count", 2);
    }

    @RequestMapping(value = "/v2/login", method = RequestMethod.POST)
    public Object login(@RequestBody LoginVo loginVo) {
        String captchCode =   tokenCache.get(loginVo.getCaptchCodeId(),String.class);
        if (!Strings.equals(loginVo.getCaptchaCode(), captchCode)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_CAPTCHA", "message", "验证码不正确"));
        }
        Map user = mongoRepository.findOne("users", "username", loginVo.getUsername());
        String newPassword = MD5.getMD5String(MD5.getMD5String(loginVo.getPassword()).substring(2, 7) + MD5.getMD5String(loginVo.getPassword()));
        if (user == null) {
            FrontUser frontUser = new FrontUser();
            frontUser.setUser_id(idsService.getId(Ids.USER_ID));
            frontUser.setUsername(loginVo.getUsername());
            frontUser.setPassword(newPassword);
            mongoRepository.save(frontUser);
            FrontUserInfo userInfo = new FrontUserInfo();
            userInfo.setId(frontUser.getUser_id());
            userInfo.setUser_id(frontUser.getUser_id());
            userInfo.setRegiste_time(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
            String ip = getIp();
            CityInfo cityInfo = positionService.getPostion(ip);
            userInfo.setCity(cityInfo.getCity());
            userInfo.setUsername(frontUser.getUsername());
            mongoRepository.save(userInfo);
            Object result = Mapl.merge(frontUser, userInfo);
            setSession("currentUser", result);
            return frontUser;
        } else {
            if (newPassword.equals(user.get("password"))) {
                Map userInfo = mongoRepository.findOne("userinfos", "user_id", Long.valueOf(user.get("user_id").toString()));
                Object result = Mapl.merge(user, userInfo);
                setSession("currentUser", result);
                return result;
            } else {
                return Rets.failure(Maps.newHashMap("type", "ERROR_PASSWORD", "message", "密码错误"));
            }

        }


    }

    @RequestMapping(value = "/v2/signout", method = RequestMethod.GET)
    public Object signOut() {
        getRequest().getSession().removeAttribute("currentUser");
        return Rets.success();
    }

    @RequestMapping(value = "/v2/changepassword", method = RequestMethod.POST)
    public Object changePassword(@RequestParam("username") String userName,
                                 @RequestParam("oldpassWord") String oldPassword,
                                 @RequestParam("newpassword") String newPassword,
                                 @RequestParam("confirmpassword") String confirmPassword,
                                 @RequestParam("captcha_code") String captchaCode) {

        String captch = (String) getSession(CaptchaCode.CAPTCH_KEY);
        if (!Strings.equals(captchaCode, captch)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_CAPTCHA", "message", "验证码不正确"));
        }
        Map user = mongoRepository.findOne("users", "username", userName);
        if (user == null) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "用户不存在"));
        }
        if (!Strings.equals(oldPassword, Strings.sNull(user.get("password")))) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "原密码错误"));
        }
        if (Strings.equals(newPassword, confirmPassword)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "新密码不一致"));
        }

        user.put("password", newPassword);
        mongoRepository.update(Long.valueOf(user.get("id").toString()), "users", user);

        return Rets.success();
    }


}
