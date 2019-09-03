package cn.enilu.elm.api.service;

import cn.enilu.elm.api.entity.Ids;
import cn.enilu.elm.api.repository.BaseDao;
import cn.enilu.elm.api.utils.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created  on 2018/1/2 0002.
 *
 * @author zt
 */
@Service
public class IdsService {
    @Autowired
    private BaseDao baseDao;
    public synchronized Long getId(String propName){
        Ids ids =  baseDao.findOne(Ids.class);
        if(ids==null){
            ids = new Ids();
            Beans beans = Beans.me(ids);
            ids.setRestaurant_id(0L);
            ids.setFood_id(0L);
            ids.setOrder_id(0L);
            ids.setUser_id(0L);
            ids.setAddress_id(0L);
            ids.setCart_id(0L);
            ids.setImg_id(0L);
            ids.setSku_id(0L);
            ids.setAdmin_id(0L);
            ids.setStatis_id(0L);
            beans.set(propName,1L);
            baseDao.save(ids);
            return 1L;
        }else{
            Beans beans = Beans.me(ids);
            Long val = (Long) beans.get(propName);
            val++;
            beans.set(propName,val);
            baseDao.save(ids);
            return val;
        }
    }
}
