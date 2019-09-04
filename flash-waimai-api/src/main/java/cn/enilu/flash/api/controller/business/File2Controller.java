package cn.enilu.flash.api.controller.business;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.AppConfiguration;
import cn.enilu.flash.bean.vo.front.Rets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created  on 2018/1/2 0002.
 *
 * @author zt
 */
@Controller
public class File2Controller extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(File2Controller.class);

    @Autowired
    private AppConfiguration appConfiguration;
    @Autowired
    private  ResourceLoader resourceLoader;
    @RequestMapping(method = RequestMethod.POST, value = "/v1/addimg/{type}")
    @ResponseBody
    public Object add(@PathVariable("type") String type, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String fileName= type+"_"+System.currentTimeMillis()+"."+file.getOriginalFilename().split("\\.")[1];
                Files.copy(file.getInputStream(), Paths.get(appConfiguration.getImgDir(), fileName));
                return Rets.success("image_path",fileName);
            } catch (IOException | RuntimeException e) {
                e.printStackTrace();

            }
        }
        return Rets.failure();


    }

    @RequestMapping(method = RequestMethod.GET, value = "/img/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable String filename) {

        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(appConfiguration.getImgDir(), filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
