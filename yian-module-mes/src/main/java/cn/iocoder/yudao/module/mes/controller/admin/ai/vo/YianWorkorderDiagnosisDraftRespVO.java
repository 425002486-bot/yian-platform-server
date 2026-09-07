package cn.iocoder.yudao.module.mes.controller.admin.ai.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "管理后台 - 工单初诊草案 Response VO")
public class YianWorkorderDiagnosisDraftRespVO {

    @Schema(description = "返回模式", example = "fallback")
    private String mode;

    @Schema(description = "故障分类")
    private String faultCategory;

    @Schema(description = "疑似原因")
    private String probableCause;

    @Schema(description = "风险等级", example = "high")
    private String riskLevel;

    @Schema(description = "是否建议停飞", example = "true")
    private Boolean groundedSuggestion;

    @Schema(description = "是否需要备件", example = "true")
    private Boolean needParts;

    @Schema(description = "建议领料项")
    private List<String> suggestedParts;

    @Schema(description = "建议领料项文本")
    private String suggestedPartsText;

    @Schema(description = "处理建议")
    private String conclusion;

    @Schema(description = "诊断摘要")
    private String summary;

    @Schema(description = "原始模型输出")
    private String rawResponse;
}
