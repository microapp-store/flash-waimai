package cn.enilu.elm.api.controller;

import cn.enilu.elm.api.repository.BaseDao;
import cn.enilu.elm.api.utils.CaptchaCode;
import cn.enilu.elm.api.utils.Maps;
import cn.enilu.elm.api.vo.Rets;
import org.nutz.lang.Strings;
import org.nutz.mapl.Mapl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by zt on 2017/12/12 0012.
 */
@RestController
@RequestMapping("/v1/users")
public class UserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private BaseDao baseDao;

    @RequestMapping(value = "/v1/user",method = RequestMethod.GET)
    public Object getUser(){
        return getSession("currentUser");
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list(@RequestParam("offset") Integer offset,@RequestParam("limit") Integer limit){
        List list = baseDao.findAll("userinfos");
        return list;
    }
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public Object count(){
        return Rets.success("count",2);
    }

    @RequestMapping(value = "/v2/login",method = RequestMethod.POST)
    public Object login(@RequestParam("username")String userName,
                        @RequestParam("password")String password,
                        @RequestParam("captcha_code") String captchaCode
                      ){
        String captch = (String) getSession(CaptchaCode.CAPTCH_KEY);
        if(!Strings.equals(captchaCode,captch)){
            return Rets.failure(Maps.newHashMap("type","ERROR_CAPTCHA","message","验证码不正确"));
        }
        Map user = baseDao.findOne("users","username",userName,"password",password);
        if(user!=null) {
            Map userInfo = baseDao.findOne("userinfos", "user_id", Long.valueOf(user.get("user_id").toString()));
            Object result = Mapl.merge(user, userInfo);
            setSession("currentUser",result);
            return result;
        }
        return Rets.failure(Maps.newHashMap("type","ERROR_PASSWORD","message","密码错误"));

    }
    @RequestMapping(value = "/v2/signout",method = RequestMethod.GET)
    public Object signOut(){
        getRequest().getSession().removeAttribute("currentUser");
        return Rets.success();
    }
    @RequestMapping(value = "/v2/changepassword",method = RequestMethod.POST)
    public Object changePassword(@RequestParam("username")String userName,
                                 @RequestParam("oldpassWord")String oldPassword,
                                 @RequestParam("newpassword")String newPassword,
                                 @RequestParam("confirmpassword")String confirmPassword,
                                 @RequestParam("captcha_code")String captchaCode){

        String captch = (String) getSession(CaptchaCode.CAPTCH_KEY);
        if(!Strings.equals(captchaCode,captch)){
            return Rets.failure(Maps.newHashMap("type","ERROR_CAPTCHA","message","验证码不正确"));
        }
        Map user = baseDao.findOne("users","username",userName);
        if(user==null){
            return Rets.failure(Maps.newHashMap("type","ERROR_QUERY","message","用户不存在"));
        }
        if(!Strings.equals(oldPassword,Strings.sNull(user.get("password")))){
            return Rets.failure(Maps.newHashMap("type","ERROR_QUERY","message","原密码错误"));
        }
        if(Strings.equals(newPassword,confirmPassword)){
            return Rets.failure(Maps.newHashMap("type","ERROR_QUERY","message","新密码不一致"));
        }

        user.put("password",newPassword);
        baseDao.update(Long.valueOf(user.get("id").toString()),"users",user);

        return Rets.success();
    }




}
