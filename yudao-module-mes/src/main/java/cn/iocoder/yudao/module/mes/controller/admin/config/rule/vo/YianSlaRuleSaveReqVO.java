package cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class YianSlaRuleSaveReqVO {
    @NotNull(message = "编号不能为空")
    private Long id;
    @NotNull(message = "时限不能为空")
    private Integer deadlineHours;
    private String timeoutAction;
}
