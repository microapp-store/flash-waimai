package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.AppConfiguration;
import cn.enilu.flash.bean.entity.front.Admin;
import cn.enilu.flash.bean.entity.front.Ids;
import cn.enilu.flash.bean.vo.business.CityInfo;
import cn.enilu.flash.bean.vo.business.Constants;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.dao.MongoRepository;
import cn.enilu.flash.service.front.IdsService;
import cn.enilu.flash.service.front.PositionService;
import cn.enilu.flash.utils.DateUtil;
import cn.enilu.flash.utils.MD5;
import com.google.common.collect.Maps;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.enilu.flash.utils.MD5.getMD5String;

/**
 * Created on 2017/12/12 0012.
 * @author zt
 */

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    public static final String ROOT = "upload-dir";

    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private AppConfiguration appConfiguration;
    @Autowired
    private PositionService positionService;
    @Autowired
    private IdsService idsService;
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request) {
        Admin admins= getRequestPayload(Admin.class);
        Admin result = mongoRepository.findOne(Admin.class,"user_name",admins.getUser_name());
        String password = admins.getPassword();
        String newPwd = MD5.getMD5String(getMD5String(password).substring(2,7)+ getMD5String(password));
        if(result!=null){
            if(newPwd.equals(result.getPassword())) {

                Cookie[] cookies = request.getCookies();
                logger.info("cookies:{}", Json.toJson(cookies));
                setSession( Constants.SESSION_ID,admins);
                return Rets.success("success", "登录成功");

            }else{
                return Rets.failure("message","密码错误");
            }
        }else{
            admins.setCreate_time(DateUtil.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss"));
            admins.setStatus(1);
            admins.setAvatar("default.jpg");
            admins.setAdmin("管理员");
            String ip = getIp();
            CityInfo cityInfo = positionService.getPostion(ip);
            admins.setCity(cityInfo!=null?cityInfo.getCity():null);
            admins.setId(idsService.getId(Ids.ADMIN_ID));
            admins.setPassword(newPwd);
            mongoRepository.save(admins);
            setSession(Constants.SESSION_ID,admins);
            return Rets.success("success", "登录成功");
        }

    }
    @RequestMapping(value = "info",method = RequestMethod.GET)
    @ResponseBody
    public Object info(HttpServletRequest request) {
        return Rets.success("data", mongoRepository.findOne(1L,"admins"));
    }

    @RequestMapping(value = "count",method = RequestMethod.GET)
    @ResponseBody
    public Object count(HttpServletRequest request) {
        long count = mongoRepository.count("admins");
        return Rets.success("count",count);
    }

    @RequestMapping(value="all",method = RequestMethod.GET)
    @ResponseBody
    public Object all(@RequestParam( name="offset") Integer offset,
                       @RequestParam(name="limit") Integer limit) {
         List list = mongoRepository.findAll("admins");
        return Rets.success("data",list);
    }

    /**
     * 更新管理员头像
     * @param adminId
     * @param file
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/update/avatar/{id}")
    @ResponseBody
    public Object uploadImg(@PathVariable("id") Long adminId,@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String fileName= System.currentTimeMillis()+"."+file.getOriginalFilename().split("\\.")[1];
                Files.copy(file.getInputStream(), Paths.get(appConfiguration.getImgDir(), fileName));
                Map map  =Maps.newHashMap();
                map.put("avatar",fileName);
                mongoRepository.update(adminId,"admins", map);
                return Rets.success("image_path",fileName);
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();

            }
        }
        return Rets.failure();


    }

}
