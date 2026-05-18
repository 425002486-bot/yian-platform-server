package cn.iocoder.yudao.module.mes.dal.dataobject.config.rule;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("yian_sla_rule")
@Data
public class YianSlaRuleDO extends BaseDO {
    @TableId
    private Long id;
    private String stage;
    private Integer deadlineHours;
    private String timeoutAction;
    private Boolean adjustable;
}
