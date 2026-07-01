package cn.iocoder.yudao.module.mes.dal.dataobject.ai;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("yian_ai_task_log")
@KeySequence("yian_ai_task_log_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YianAiTaskLogDO extends BaseDO {

    @TableId
    private Long id;

    private String bizType;

    private String bizKey;

    private String mode;

    private String modelName;

    private Boolean success;

    private String requestJson;

    private String responseJson;

    private String errorMessage;
}
