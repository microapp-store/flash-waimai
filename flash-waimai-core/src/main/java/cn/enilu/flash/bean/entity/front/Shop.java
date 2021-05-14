package cn.enilu.flash.bean.entity.front;

import cn.enilu.flash.utils.Lists;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 商铺
 * Created  on 2017/12/29 0029.
 *
 * @author zt
 */
@Document(collection = "shops")
@Data
public class Shop extends BaseMongoEntity {
    /**
     * 审核状态：审核中
     */
    public static  final String STATE_ING="0";
    /**
     * 审核状态：审核通过
     */
    public static  final String STATE_YES = "1";
    /**
     * 审核状态：审核拒绝
     */
    public static  final String STATE_NO = "-1";
    @Id
    private String _id;
    @NotBlank(message = "商铺名称不能为空")
    private String name;
    private String address;
    private Long id;
    private Double latitude;
    private Double longitude;
    private List location;
    private String phone;
    private String category;
    private List supports= Lists.newArrayList();
    /**
     * 监督检查结果：1:良好,其他:差
     */
    private Integer status=1;

    private Integer recent_order_num=500;
    private Integer rating_count=200;
    private Double rating=4.5;
    private String promotion_info;
    private Map piecewise_agent_fee;

    private List opening_hours;

    private Map license;
    private Boolean is_new;
    private String is_premium;
    private String image_path;
    private Map identification;
    private String float_minimum_order_amount;
    private String float_delivery_fee;
    private String distance;
    private String order_lead_time="30分钟";
    private String description;
    private Map delivery_mode;
    private List activities;
    /**
     *审核状态：0:审核中，1:审核通过，-1:审核失败
     */
    private String state ;
    /**
     * 审核拒绝的时候备注信息
     */
    private String auditRemark;
    /**
     * 未结算金额
     */
    private String unliquidatedAmount;
    /**
     * 总交易额
     */
    private String totalAmount;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否停用，0/null：不禁用，1：禁用
     */
    private Integer disabled;
    /**
     * 平台收取费率（百分比）
     */
    private String platform_rate;

    @JSONField(name="_id")
    public String get_id() {
        return _id;
    }
    @JSONField(name="_id")
    public void set_id(String _id) {
        this._id = _id;
    }

    /**
     * 转换审核状态为字符串形式
     * @return
     */
    public String getStateStr(){
        if(STATE_ING.equals(state)){
            return "审核中";
        }

        if(STATE_YES.equals(state)){
            return "审核通过";
        }

        if(STATE_NO.equals(state)){
            return "审核失败";
        }
        return "";
    }



}
