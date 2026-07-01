package cn.iocoder.yudao.module.mes.controller.admin.ai.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "管理后台 - 设备建档附件解析 Response VO")
public class YianAssetDeviceDocumentParseRespVO {

    @Schema(description = "返回模式", example = "mock")
    private String mode;

    @Schema(description = "解析摘要")
    private String parseSummary;

    @Schema(description = "解析来源")
    private String parseSource;

    @Schema(description = "最近解析时间", example = "2026-06-30 14:30")
    private String parseUpdatedAt;

    @Schema(description = "结构化字段")
    private List<FieldItem> parsedFields;

    @Schema(description = "预警列表")
    private List<String> warnings;

    @Schema(description = "缺失项列表")
    private List<String> missingItems;

    @Schema(description = "附件结果列表")
    private List<DocumentItem> documents;

    @Data
    public static class FieldItem {
        @Schema(description = "字段名")
        private String label;

        @Schema(description = "字段值")
        private String value;
    }

    @Data
    public static class DocumentItem {
        @Schema(description = "前端临时 ID")
        private String id;

        @Schema(description = "文件名")
        private String fileName;

        @Schema(description = "资料类型")
        private String documentType;

        @Schema(description = "解析结果")
        private String parseResult;

        @Schema(description = "解析来源")
        private String parseSource;

        @Schema(description = "上传人")
        private String uploadedBy;

        @Schema(description = "上传时间")
        private String uploadedAt;

        @Schema(description = "文件访问 URL")
        private String url;
    }
}
