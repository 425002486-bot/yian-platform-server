package cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 放行规则评估 Request VO")
@Data
public class YianReleaseRuleEvaluateReqVO {

    @Schema(description = "风险等级", example = "medium")
    private String riskLevel;

    @Schema(description = "复检是否通过", example = "true")
    private Boolean inspectionPassed;

    @Schema(description = "关联电池列表")
    private List<YianBatteryRuleEvaluateReqVO> batteries = new ArrayList<>();
}
