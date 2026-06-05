package cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产电池 Response VO")
@Data
public class YianAssetBatteryRespVO {

    private Long id;

    private LocalDateTime createTime;

    private String batteryCode;

    private String serialNumber;

    private String model;

    private Long workshopId;

    private String workshopName;

    private Long linkedDeviceId;

    private String linkedDeviceCode;

    private String linkedDeviceName;

    private Integer soh;

    private Integer cycleCount;

    private LocalDateTime lastCheckTime;

    private String lastCheckAt;

    private String checkSource;

    private String healthStatus;

    private String healthLabel;

    private String sourceEvidence;

    private String recommendation;

    private String remark;
}
