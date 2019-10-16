package cn.enilu.flash.bean.vo.business;

import lombok.Data;

import java.io.Serializable;

/**
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
@Data
public class CityInfo implements Serializable {
    private String lat;
    private String lng;
    private String city;
}
