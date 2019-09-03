package cn.enilu.elm.api.controller;

import cn.enilu.elm.api.utils.AppConfiguration;
import cn.enilu.elm.api.vo.Rets;
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
public class FileController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

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

//    @RequestMapping(method = RequestMethod.GET, value = "/img")
//    public String provideUploadInfo(Model model) throws IOException {
//
//        model.addAttribute("files", Files.walk(Paths.get(appConfiguration.getImgDir()))
//                .filter(path -> !path.equals(Paths.get(appConfiguration.getImgDir())))
//                .map(path -> Paths.get(appConfiguration.getImgDir()).relativize(path))
//                .map(path -> linkTo(methodOn(FileController.class).getFile(path.toString())).withRel(path.toString()))
//                .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }

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
