package cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产电池新增/修改 Request VO")
@Data
public class YianAssetBatterySaveReqVO {

    @Schema(description = "编号", example = "1024")
    private Long id;

    @Schema(description = "电池编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "YA-BT-01001")
    @NotBlank(message = "电池编号不能为空")
    private String batteryCode;

    @Schema(description = "序列号 SN", requiredMode = Schema.RequiredMode.REQUIRED, example = "BT24SU22739")
    @NotBlank(message = "序列号不能为空")
    private String serialNumber;

    @Schema(description = "型号", requiredMode = Schema.RequiredMode.REQUIRED, example = "TB65")
    @NotBlank(message = "型号不能为空")
    private String model;

    @Schema(description = "所属站点/车间编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1001")
    @NotNull(message = "所属站点不能为空")
    private Long workshopId;

    @Schema(description = "关联主机编号", example = "1201")
    private Long linkedDeviceId;

    @Schema(description = "SOH", example = "95")
    @Min(value = 0, message = "SOH 不能小于 0")
    @Max(value = 100, message = "SOH 不能大于 100")
    private Integer soh;

    @Schema(description = "循环次数", example = "18")
    @Min(value = 0, message = "循环次数不能小于 0")
    private Integer cycleCount;

    @Schema(description = "最近检测时间")
    private LocalDateTime lastCheckTime;

    @Schema(description = "检测来源", example = "BMS")
    private String checkSource;

    @Schema(description = "健康状态", example = "normal")
    private String healthStatus;

    @Schema(description = "来源依据", example = "检测报告 / 人工补录")
    private String sourceEvidence;

    @Schema(description = "建议动作", example = "建议 48 小时内复检")
    private String recommendation;

    @Schema(description = "备注", example = "首批建档")
    private String remark;
}
