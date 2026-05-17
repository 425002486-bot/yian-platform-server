package cn.iocoder.yudao.module.mes.controller.admin.asset.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "管理后台 - 资产设备画像 Response VO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YianAssetDeviceProfileRespVO {

    @Schema(description = "设备编码")
    private String deviceCode;

    @Schema(description = "设备名称")
    private String deviceName;

    @Schema(description = "当前阶段标签, 如：主档在册 / 停飞待放行 / 待检观察")
    private String currentStage;

    @Schema(description = "状态原因说明")
    private String statusReason;

    @Schema(description = "状态判断依据来源")
    private String statusSource;

    @Schema(description = "维修工单数量")
    private Long repairCount;

    @Schema(description = "保养记录数量")
    private Long maintenCount;

    @Schema(description = "点检记录数量")
    private Long checkCount;

    @Schema(description = "关联电池编码列表")
    private List<String> linkedBatteryCodes;

    @Schema(description = "关联工单摘要, 如: 当前无在途工单")
    private String workorderSummary;

    @Schema(description = "预警信息列表")
    private List<String> warnings;

    @Schema(description = "缺失项列表")
    private List<String> missingItems;

    @Schema(description = "设备履历事件列表")
    private List<TimelineEventVO> history;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimelineEventVO {
        @Schema(description = "事件ID")
        private String id;
        @Schema(description = "发生时间")
        private String happenedAt;
        @Schema(description = "事件标题")
        private String title;
        @Schema(description = "事件详情")
        private String detail;
        @Schema(description = "所属阶段")
        private String stage;
        @Schema(description = "依据来源")
        private String evidence;
        @Schema(description = "风格色调: success/warning/danger/info")
        private String tone;
    }
}
