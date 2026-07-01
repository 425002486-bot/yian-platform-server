package cn.iocoder.yudao.module.mes.controller.admin.ai.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "管理后台 - 工单初诊草案生成 Request VO")
public class YianWorkorderDiagnosisDraftGenerateReqVO {

    @Schema(description = "工单 ID", example = "2001")
    private Long workorderId;

    @NotBlank(message = "工单号不能为空")
    @Schema(description = "工单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "WO-20260630-001")
    private String orderNo;

    @NotBlank(message = "设备编码不能为空")
    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "UAV-MVP-001")
    private String deviceCode;

    @Schema(description = "设备名称", example = "M350 RTK")
    private String deviceName;

    @Schema(description = "站点名称", example = "深圳南山站")
    private String siteName;

    @Schema(description = "任务场景", example = "常规巡检")
    private String taskScene;

    @NotBlank(message = "异常现象不能为空")
    @Schema(description = "异常现象", requiredMode = Schema.RequiredMode.REQUIRED)
    private String symptom;

    @Schema(description = "现场描述")
    private String description;

    @Schema(description = "飞行日志解析摘要")
    private String flightLogSummary;

    @Schema(description = "现场图片摘要")
    private String imageSummary;

    @Schema(description = "附件名称列表")
    private List<String> attachmentNames;
}
