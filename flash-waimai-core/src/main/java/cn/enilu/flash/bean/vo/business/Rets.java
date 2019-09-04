package cn.enilu.flash.bean.vo.business;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created on 2017/12/29 0029.
 * @author zt
 */
public class Rets {
    public static  final int STATUS_SUCCESS=1;
    public static  final int STATUS_FAILURE=0;
    public static  Map<String,Object> success(){
        Map<String,Object> result = Maps.newHashMap();
        result.put("status",STATUS_SUCCESS);
        return result;
    }
    public static Map<String,Object> success(String message){
        Map<String,Object> result = Maps.newHashMap();
        result.put("status",STATUS_SUCCESS);
        result.put("success",message);
        return result;
    }
    public static Map<String,Object> success(String key,Object data){
        Map<String,Object> result = Maps.newHashMap();
        result.put("status",STATUS_SUCCESS);
        result.put(key,data);
        return result;
    }
    public static  Map<String,Object> failure(){
        Map<String,Object> result = Maps.newHashMap();
        result.put("status",STATUS_FAILURE);
        return result;
    }
    public static Map<String,Object> failure(String key,Object data){
        Map<String,Object> result = Maps.newHashMap();
        result.put("status",STATUS_FAILURE);
        result.put(key,data);
        return result;
    }
    public static  Map<String,Object> failure(Map<String,Object> data){
        data.put("status",STATUS_FAILURE);
        return data;
    }
    public static Map<String,Object> failure(String message){
        Map<String,Object> result = Maps.newHashMap();
        result.put("status",STATUS_FAILURE);
        result.put("message",message);
        return result;
    }

}
