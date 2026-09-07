package cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 工单节点规则评估 Request VO")
@Data
public class YianWorkorderStageEvaluateReqVO {

    @Schema(description = "当前节点", example = "diagnosing")
    private String stage;

    @Schema(description = "风险等级", example = "medium")
    private String riskLevel;

    @Schema(description = "是否停飞", example = "true")
    private Boolean grounded;

    @Schema(description = "是否建议停飞", example = "true")
    private Boolean groundedSuggestion;

    @Schema(description = "是否需要备件", example = "true")
    private Boolean needParts;

    @Schema(description = "领料人是否已确认", example = "true")
    private Boolean pickerProvided;

    @Schema(description = "领料项数量", example = "2")
    private Integer itemCount;

    @Schema(description = "库存是否充足", example = "true")
    private Boolean inventorySufficient;

    @Schema(description = "维修人是否已确认", example = "true")
    private Boolean technicianProvided;

    @Schema(description = "复检是否通过", example = "true")
    private Boolean inspectionPassed;

    @Schema(description = "是否完成电池核验", example = "true")
    private Boolean batteryCheck;

    @Schema(description = "是否完成试飞验证", example = "true")
    private Boolean flightTest;

    @Schema(description = "放行结果", example = "approved")
    private String reviewResult;

    @Schema(description = "是否填写限制条件", example = "false")
    private Boolean restrictionsProvided;
}
