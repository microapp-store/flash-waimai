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
@Document(collection = "userinfos")
public class FrontUserInfo  extends BaseMongoEntity{
    @Id
    private String _id;
    private String username;
    private Long user_id;
    private Long id;
    private String city;
    private String registe_time;
    private Integer point=0;
    private String mobile;
    private Boolean is_mobile_valid=false;
    private Boolean is_email_valid=false;
    private Integer is_active=1;
    private Integer gift_amount=3;
    private String email;
    private Integer delivery_card_expire_days=0;
    private Integer current_invoice_id=0;
    private Integer current_address_id=0;
    private Integer balance=0;
    private Integer brand_member_new=0;
    private String avatar="avatar.jpg";

}