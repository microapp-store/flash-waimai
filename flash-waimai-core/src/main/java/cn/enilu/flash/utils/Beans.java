package cn.enilu.flash.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
public class Beans<T> {
    static BeanWrapper beanWrapper =  null;
    public static <T> Beans<T> me( T obj) {
        return new Beans<T>(obj);
    }
    private Beans(T obj){
        beanWrapper = new BeanWrapperImpl(obj);
    }
    public    Object get(String propName){

        return beanWrapper.getPropertyValue(propName);
    }

    public void set(String propName, Object propVal) {
        beanWrapper.setPropertyValue(propName,propVal);
    }
}
