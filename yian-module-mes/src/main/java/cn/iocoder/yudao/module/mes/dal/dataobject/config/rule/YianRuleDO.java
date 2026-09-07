package cn.iocoder.yudao.module.mes.dal.dataobject.config.rule;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("yian_rule")
@Data
public class YianRuleDO extends BaseDO {
    @TableId
    private Long id;
    private String name;
    /** asset/workorder/release */
    private String category;
    private String triggerCondition;
    private String triggerObject;
    private String scope;
    /** intercept/restrict/alert/review/restricted_flight */
    private String action;
    private String execNode;
    private String stationScope;
    private Integer impactCount;
    /** 0-启用 1-停用 */
    private Integer status;
    private String remark;
}
