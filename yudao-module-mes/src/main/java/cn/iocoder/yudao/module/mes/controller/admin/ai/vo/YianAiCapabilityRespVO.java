package cn.iocoder.yudao.module.mes.controller.admin.ai.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台 - 翼安 AI 能力状态 Response VO")
public class YianAiCapabilityRespVO {

    @Schema(description = "是否开启 Mock 模式", example = "true")
    private Boolean mockEnabled;

    @Schema(description = "真实模型是否可用", example = "false")
    private Boolean realModelReady;

    @Schema(description = "当前建议模式", example = "mock")
    private String recommendedMode;

    @Schema(description = "模型名称", example = "Qwen 3.7 Plus")
    private String modelName;

    @Schema(description = "状态说明")
    private String message;
}
