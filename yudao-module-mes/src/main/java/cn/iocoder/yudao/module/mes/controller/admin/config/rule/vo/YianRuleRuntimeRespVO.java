package cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo;

import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianRuleDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianSlaRuleDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 规则运行时快照 Response VO")
@Data
public class YianRuleRuntimeRespVO {

    @Schema(description = "拉取时间", example = "2026-06-05T10:30:00")
    private String fetchedAt;

    @Schema(description = "启用中的规则列表")
    private List<YianRuleDO> rules = new ArrayList<>();

    @Schema(description = "SLA 规则列表")
    private List<YianSlaRuleDO> slaRules = new ArrayList<>();

    @Schema(description = "按阶段索引的 SLA 规则")
    private Map<String, YianSlaRuleDO> slaByStage = new LinkedHashMap<>();

    @Schema(description = "电池观察阈值", example = "80")
    private Integer batteryWarningSohThreshold;

    @Schema(description = "电池强拦截阈值", example = "65")
    private Integer batteryDangerSohThreshold;

    @Schema(description = "工单超时升级小时数", example = "14")
    private Integer workorderEscalationHours;
}
