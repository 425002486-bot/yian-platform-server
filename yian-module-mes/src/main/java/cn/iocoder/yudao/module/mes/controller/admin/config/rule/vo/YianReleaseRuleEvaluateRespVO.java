package cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 放行规则评估 Response VO")
@Data
public class YianReleaseRuleEvaluateRespVO {

    @Schema(description = "规则汇总结论", example = "当前建议限制放行，并补充限制条件后再放行。")
    private String summary;

    @Schema(description = "推荐放行结果", example = "limited")
    private String recommendedResult;

    @Schema(description = "提示列表")
    private List<String> alerts = new ArrayList<>();

    @Schema(description = "阻断原因列表")
    private List<String> blockingReasons = new ArrayList<>();
}
