package cn.iocoder.yudao.module.mes.controller.admin.ai.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "管理后台 - 工单飞行日志解析 Response VO")
public class YianWorkorderFlightLogParseRespVO {

    @Schema(description = "返回模式", example = "model")
    private String mode;

    @Schema(description = "日志解析摘要")
    private String summary;

    @Schema(description = "可写入初诊上下文的摘要")
    private String assistantContextSummary;

    @Schema(description = "解析来源")
    private String parseSource;

    @Schema(description = "识别到的设备编码")
    private String droneSn;

    @Schema(description = "识别到的电池编码列表")
    private List<String> batterySns;

    @Schema(description = "飞行时长（秒）", example = "720")
    private Integer flightDurationSec;

    @Schema(description = "日志类型")
    private String logType;

    @Schema(description = "上传附件")
    private List<AttachmentItem> attachments;

    @Data
    public static class AttachmentItem {
        @Schema(description = "文件名")
        private String name;

        @Schema(description = "附件类型", example = "log")
        private String type;

        @Schema(description = "文件大小", example = "1024")
        private Long size;

        @Schema(description = "MIME 类型")
        private String mimeType;

        @Schema(description = "文件访问 URL")
        private String url;
    }
}
