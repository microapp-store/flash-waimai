package cn.enilu.flash.api.controller;

import cn.enilu.flash.api.utils.ApiConstants;
import cn.enilu.flash.bean.core.ShiroUser;
import cn.enilu.flash.bean.entity.front.Shop;
import cn.enilu.flash.bean.entity.system.User;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.security.AccountInfo;
import cn.enilu.flash.security.JwtUtil;
import cn.enilu.flash.security.ShiroFactroy;
import cn.enilu.flash.service.system.AccountService;
import cn.enilu.flash.service.system.MenuService;
import cn.enilu.flash.service.system.UserService;
import cn.enilu.flash.utils.*;
import org.nutz.mapl.Mapl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AccountController
 *
 *@Author enilu
 * @version 2018/9/12 0012
 */
@RestController
@RequestMapping("/account")
public class AccountController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MongoRepository mongoRepository;


    /**
     * 用户登录<br>
     * 1，验证没有注册<br>
     * 2，验证密码错误<br>
     * 3，登录成功
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(
            @RequestParam("userType") String userType,
            @RequestParam("username") String userName,
            @RequestParam("password") String password) {
        try {
            logger.info("用户登录:" + userName + ",密码:" + password);
            String token = null;
            if (Constants.USER_TYPE_MGR.equals(userType)) {
                //1,
                User user = userService.findByAccount(userName);
                if (user == null) {
                    return Rets.failure("该用户不存在");
                }
                String passwdMd5 = MD5.md5(password, user.getSalt());
                //2,
                if (!user.getPassword().equals(passwdMd5)) {
                    return Rets.failure("输入的密码错误");
                }
                token = JwtUtil.sign(user);
            } else if (Constants.USER_TYPE_SHOP.equals(userType)) {

                Shop shop = mongoRepository.findOne(Shop.class, Maps.newHashMap("name", userName, "password", password));
                if(shop==null){
                    return Rets.failure("没有改账号");
                }
                if(shop.getDisabled() == 1){
                    return Rets.failure("该商户已停用");
                }
                if (shop == null) {
                    return Rets.failure("账号或密码错误");
                }
                token = JwtUtil.sign(shop);
            }

            Map<String, String> result = new HashMap<>(1);
            logger.info("token:{}", token);
            result.put("token", token);

            return Rets.success(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Rets.failure("登录时失败");
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Object logout(HttpServletRequest request) {
        String token = this.getToken(request);
        accountService.logout(token);
        return Rets.success();
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object info(HttpServletRequest request) {
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        Long idUser = accountInfo.getUserId();
        if (idUser != null) {
            ShiroUser shiroUser = null;
            Map profile = null;
            if (Constants.USER_TYPE_MGR.equals(accountInfo.getUserType())) {
                User user = userService.get(idUser);
                if (StringUtils.isEmpty(user.getRoleid())) {
                    return Rets.failure("该用户未配置权限");
                }
                shiroUser = ShiroFactroy.me().shiroUser(user);
                profile = (Map) Mapl.toMaplist(user);
            } else {
                Shop shop = mongoRepository.findOne(Shop.class, Maps.newHashMap("name", accountInfo.getUsername(), "password", accountInfo.getPassword()));
                shiroUser = ShiroFactroy.me().shiroUser(shop);
                profile = (Map) Mapl.toMaplist(shop);

            }

            Map map = Maps.newHashMap("name", accountInfo.getUsername(), "role", "admin", "roles", shiroUser.getRoleCodes());

            List menus = menuService.getMenusByRoleIds(shiroUser.getRoleList());
            map.put("menus", menus);
            map.put("permissions", shiroUser.getUrls());

            profile.put("dept", shiroUser.getDeptName());
            profile.put("roles", shiroUser.getRoleNames());
            map.put("profile", profile);

            return Rets.success(map);
        }
        return Rets.failure("获取用户信息失败");
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public Object updatePwd(String oldPassword, String password, String rePassword) {
        try {
            if (!password.equals(rePassword)) {
                return Rets.failure("新密码前后不一致");
            }
            AccountInfo accountInfo = JwtUtil.getAccountInfo();
            if(Constants.USER_TYPE_MGR.equals(accountInfo.getUserType())) {
                User user = userService.get(getIdUser(HttpKit.getRequest()));
                if (ApiConstants.ADMIN_ACCOUNT.equals(user.getAccount())) {
                    return Rets.failure("不能修改超级管理员密码");
                }
                logger.info("oldPassword:{},password:{},rePassword:{}", MD5.md5(oldPassword, user.getSalt()), password, rePassword);

                if (!MD5.md5(oldPassword, user.getSalt()).equals(user.getPassword())) {
                    return Rets.failure("旧密码输入错误");
                }

                user.setPassword(MD5.md5(password, user.getSalt()));
                userService.update(user);
            }else if(Constants.USER_TYPE_SHOP.equals(accountInfo.getUserType())){
                Shop shop = mongoRepository.findOne(Shop.class, Maps.newHashMap("name", accountInfo.getUsername(), "password", oldPassword));
                if(shop==null){
                    return Rets.failure("旧密码输入错误");
                }
                if(shop.getId()!=accountInfo.getUserId()){
                    //基本不会出现这种情况
                    return Rets.failure("不允许该操作");
                }
                mongoRepository.update(accountInfo.getUserId(),"shops",Maps.newHashMap("password",password));
            }
            return Rets.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Rets.failure("更改密码失败");
    }


}
