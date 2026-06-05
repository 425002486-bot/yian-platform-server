package cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 电池规则评估 Response VO")
@Data
public class YianBatteryRuleEvaluateRespVO {

    @Schema(description = "规则计算后的健康状态", example = "warning")
    private String healthStatus;

    @Schema(description = "健康状态文案", example = "限制放行")
    private String healthLabel;

    @Schema(description = "规则建议", example = "命中观察规则，建议缩短任务时长并尽快复检。")
    private String recommendation;

    @Schema(description = "是否缺少检测依据", example = "false")
    private Boolean missingEvidence;
}
