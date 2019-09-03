package cn.enilu.elm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by zt on 2017/12/12 0012.
 */
@SpringBootApplication
public class ApplicationStart extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}
