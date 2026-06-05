package cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 工单节点规则评估 Response VO")
@Data
public class YianWorkorderStageEvaluateRespVO {

    @Schema(description = "是否允许提交", example = "true")
    private Boolean allowed;

    @Schema(description = "建议下一节点", example = "repairing")
    private String nextStage;

    @Schema(description = "评估摘要")
    private String summary;

    @Schema(description = "默认超时动作")
    private String timeoutAction;

    @Schema(description = "阻断原因")
    private List<String> blockingReasons = new ArrayList<>();

    @Schema(description = "提示信息")
    private List<String> notices = new ArrayList<>();
}
