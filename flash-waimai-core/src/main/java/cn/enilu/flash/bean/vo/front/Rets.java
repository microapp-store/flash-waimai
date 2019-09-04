package cn.enilu.flash.bean.vo.front;

import com.google.common.collect.Maps;

import java.util.Map;

public class Rets {

    public static final Integer SUCCESS = 20000;
    public static final Integer FAILURE = 9999;
    public static  final Integer TOKEN_EXPIRE=50014;

    public static Ret success(Object data) {
        return new Ret(Rets.SUCCESS, "成功", data);
    }
    public static Map<String,Object> success(String key, Object data){
        Map<String,Object> result = Maps.newHashMap();
        result.put("code",SUCCESS);
        result.put(key,data);
        return result;
    }
    public static Ret failure(String msg) {
        return new Ret(Rets.FAILURE, msg, null);
    }
    public static Ret failure() {
        return new Ret(Rets.FAILURE, null, null);
    }
    public static  Map<String,Object> failure(Map<String,Object> data){
        data.put("code",FAILURE);
        return data;
    }
    public static Map<String,Object> failure(String key, Object data){
        Map<String,Object> result = Maps.newHashMap();
        result.put("code",FAILURE);
        result.put(key,data);
        return result;
    }
    public static Ret success() {
        return new Ret(Rets.SUCCESS, "成功", null);
    }
    public static  Ret expire(){
        return new Ret(Rets.TOKEN_EXPIRE,"token 过期",null);
    }
}
