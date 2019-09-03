package cn.enilu.elm.api.controller;

import com.google.common.base.Strings;
import org.nutz.json.Json;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created on 2017/12/29 0029.
 * @author zt
 */
public class BaseController {

    protected  String getRequestPayload( ){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = getRequest().getReader();) {
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    protected  <T>T getRequestPayload(  Class<T> klass)     {
        String json = getRequestPayload();
        try {
            T result = null;
            if(klass==Map.class||klass==null){
                result = (T) Json.fromJson(json);
            }else {
               result = Json.fromJson( klass,json);
            }
            return result;
        }catch (Exception e){

        }
        return null;
    }
    protected HttpServletRequest getRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        return sra.getRequest();
    }
    protected Object getSession(String key){
        return getRequest().getSession().getAttribute(key);
    }
    protected  void setSession(  String key,Object val){
        getRequest().getSession().setAttribute(key,val);
    }

    public String getIp(){
        String ip = getRequest().getHeader("x-forwarded-for");
        if(Strings.isNullOrEmpty(ip)){
            //测试ip
            ip = "101.81.121.39";
        }
        return ip;

    }
}
