package cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 翼安设备准入规则评估 Response VO")
@Data
public class YianDeviceAdmissionRuleEvaluateRespVO {

    @Schema(description = "设备编号")
    private Long machineryId;

    @Schema(description = "设备编码")
    private String deviceCode;

    @Schema(description = "启用状态", example = "enabled")
    private String enableStatus;

    @Schema(description = "启用状态文案")
    private String enableStatusLabel;

    @Schema(description = "当前状态", example = "pending_release")
    private String currentStatus;

    @Schema(description = "当前状态文案")
    private String currentStatusLabel;

    @Schema(description = "当前状态标签类型", example = "warning")
    private String currentStatusTagType;

    @Schema(description = "状态原因")
    private String statusReason;

    @Schema(description = "状态来源")
    private String statusSource;

    @Schema(description = "预警等级", example = "warning")
    private String warningLevel;

    @Schema(description = "预警等级文案")
    private String warningLevelLabel;

    @Schema(description = "预警摘要")
    private String warningSummary;

    @Schema(description = "处理建议")
    private String recommendedAction;

    @Schema(description = "关联电池编码列表")
    private List<String> linkedBatteryCodes = new ArrayList<>();

    @Schema(description = "关联工单摘要")
    private String workorderSummary;

    @Schema(description = "预警明细")
    private List<String> warnings = new ArrayList<>();

    @Schema(description = "缺失项明细")
    private List<String> missingItems = new ArrayList<>();
}
