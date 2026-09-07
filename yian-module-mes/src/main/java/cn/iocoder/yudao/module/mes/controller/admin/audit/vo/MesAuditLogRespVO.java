package cn.iocoder.yudao.module.mes.controller.admin.audit.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - MES 审计日志 Response VO")
@Data
public class MesAuditLogRespVO {

    @Schema(description = "日志编号")
    private Long id;

    @Schema(description = "操作时间")
    private LocalDateTime createTime;

    @Schema(description = "操作人昵称")
    private String userName;

    @Schema(description = "操作对象类型：设备/工单/备件/放行")
    private String objectType;

    @Schema(description = "对象标识（业务编号）")
    private Long bizId;

    @Schema(description = "对象标识（业务编码，如 WO-xxx）")
    private String bizCode;

    @Schema(description = "操作内容")
    private String action;

    @Schema(description = "操作名（子类型，如新建工单/工单受理）")
    private String subType;

    @Schema(description = "来源页面")
    private String sourcePage;

    @Schema(description = "用户IP")
    private String userIp;

    @Schema(description = "拓展字段 JSON")
    private String extra;

}
