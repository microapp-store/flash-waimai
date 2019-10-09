package cn.enilu.flash.bean.entity.front;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
/**
 * Created  on 2019/10/09
 *
 * @author enilu.cn
 */
@Data
@Document(collection = "users")
public class FrontUser extends BaseMongoEntity{
    @Id
    private String _id;
    private String username;
    private String password;
    private Long user_id;

}