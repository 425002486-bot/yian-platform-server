package cn.iocoder.yudao.module.mes.controller.admin.ai.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "管理后台 - 设备建档附件重新解析 Request VO")
public class YianAssetDeviceReparseReqVO {

    @Schema(description = "设备 ID", example = "1001")
    private Long machineryId;

    @NotBlank(message = "设备编码不能为空")
    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "UAV-MVP-001")
    private String code;

    @NotBlank(message = "操作人不能为空")
    @Schema(description = "操作人", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String operator;

    @Schema(description = "已有附件名称列表，兼容旧版前端")
    private List<String> fileNames;

    @Schema(description = "已有附件引用列表，用于重新解析时回读原始附件")
    private List<FileRef> files;

    @Data
    public static class FileRef {

        @Schema(description = "文件名称", example = "UAV-202606080002_无人机合格证样本.png")
        private String fileName;

        @Schema(description = "文件 MIME 类型", example = "image/png")
        private String mimeType;

        @Schema(description = "文件访问 URL")
        private String url;
    }
}
