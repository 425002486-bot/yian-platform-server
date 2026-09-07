package cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 电池规则评估 Request VO")
@Data
public class YianBatteryRuleEvaluateReqVO {

    @Schema(description = "电池编号", example = "YA-BT-01007")
    private String batteryCode;

    @Schema(description = "SOH", example = "78")
    private Integer soh;

    @Schema(description = "循环次数", example = "186")
    private Integer cycleCount;

    @Schema(description = "检测来源", example = "人工导入")
    private String checkSource;

    @Schema(description = "最近巡检时间", example = "2026-05-10 08:40")
    private String lastCheckAt;

    @Schema(description = "原始健康状态", example = "warning")
    private String healthStatus;

    @Schema(description = "原始处理建议", example = "建议复检")
    private String recommendation;
}
