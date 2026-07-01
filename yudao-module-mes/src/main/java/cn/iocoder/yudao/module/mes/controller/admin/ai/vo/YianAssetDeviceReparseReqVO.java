package cn.iocoder.yudao.module.mes.controller.admin.ai.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "管理后台 - 设备建档附件重解析 Request VO")
public class YianAssetDeviceReparseReqVO {

    @Schema(description = "设备 ID", example = "1001")
    private Long machineryId;

    @NotBlank(message = "设备编码不能为空")
    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "UAV-MVP-001")
    private String code;

    @NotBlank(message = "操作人不能为空")
    @Schema(description = "操作人", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String operator;

    @Schema(description = "已有附件名称列表")
    private List<String> fileNames;
}
