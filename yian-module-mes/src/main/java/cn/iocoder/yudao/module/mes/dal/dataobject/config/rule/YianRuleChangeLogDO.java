package cn.iocoder.yudao.module.mes.dal.dataobject.config.rule;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("yian_rule_change_log")
@Data
public class YianRuleChangeLogDO {
    @TableId
    private Long id;
    private Long ruleId;
    private String ruleName;
    private String changeType;
    private String beforeValue;
    private String afterValue;
    private Long operatorId;
    private String operatorName;
    private LocalDateTime createTime;
    private Long tenantId;
}
