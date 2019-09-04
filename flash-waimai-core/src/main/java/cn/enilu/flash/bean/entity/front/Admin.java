package cn.enilu.flash.bean.entity.front;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by zt on 2017/12/29 0029.
 */
@Document(collection = "admins")
@Data
public class Admin extends BaseMongoEntity {

    private Long id;
    private String user_name;
    private String password;
    private String create_time;
    private Integer status;
    private String city;
    private String avatar;
    private String admin;

}
