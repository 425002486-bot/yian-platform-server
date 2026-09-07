package cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class YianRuleSaveReqVO {
    private Long id;
    @NotEmpty(message = "规则名称不能为空")
    private String name;
    @NotEmpty(message = "规则类型不能为空")
    private String category;
    @NotEmpty(message = "触发条件不能为空")
    private String triggerCondition;
    private String triggerObject;
    private String scope;
    @NotEmpty(message = "动作不能为空")
    private String action;
    private String execNode;
    private String stationScope;
    private Integer impactCount;
    @NotNull(message = "状态不能为空")
    private Integer status;
    private String remark;
}
