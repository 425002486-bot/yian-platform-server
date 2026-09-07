package cn.iocoder.yudao.module.mes.controller.app.officialsite.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 APP - 翼安智链官网概览 Response VO")
@Data
public class YianOfficialSiteOverviewRespVO {

    @Schema(description = "设备档案总数", example = "128")
    private Long machineryCount;

    @Schema(description = "电池资产总数", example = "364")
    private Long batteryCount;

    @Schema(description = "维保工单总数", example = "186")
    private Long workOrderCount;

    @Schema(description = "站点总数", example = "12")
    private Long stationCount;

    @Schema(description = "维修任务总数", example = "48")
    private Long repairCount;
}
