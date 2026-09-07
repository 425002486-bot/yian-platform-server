package cn.iocoder.yudao.module.mes.controller.admin.audit.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - MES 审计日志分页 Request VO")
@Data
public class MesAuditLogPageReqVO extends PageParam {

    @Schema(description = "操作对象类型：device/workorder/spare/release", example = "workorder")
    private String objectType;

    @Schema(description = "操作人昵称（模糊查询）", example = "陈工")
    private String operatorName;

    @Schema(description = "创建时间范围")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
