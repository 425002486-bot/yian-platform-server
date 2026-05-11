package cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 资产电池列表 Request VO")
@Data
public class YianAssetBatteryListReqVO {

    @Schema(description = "关键字，支持电池编码或序列号")
    private String keyword;

    @Schema(description = "健康状态", example = "warning")
    private String healthStatus;

    @Schema(description = "所属车间编号", example = "1001")
    private Long workshopId;

    @Schema(description = "关联主机编号", example = "1201")
    private Long linkedMachineryId;
}
