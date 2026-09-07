package cn.iocoder.yudao.module.mes.controller.admin.wm.materialstock.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - MES 领退料记录 Response VO")
@Data
@Builder
public class MesWmPickReturnRecordRespVO {

    @Schema(description = "记录编号")
    private Long id;

    @Schema(description = "单据编码（领料单号/退料单号）")
    private String issueCode;

    @Schema(description = "工单编号")
    private String workOrderCode;

    @Schema(description = "备件名称")
    private String itemName;

    @Schema(description = "料号")
    private String itemCode;

    @Schema(description = "动作类型：领料/退料")
    private String actionType;

    @Schema(description = "数量")
    private BigDecimal quantity;

    @Schema(description = "操作人")
    private String operatorName;

    @Schema(description = "结果状态：已出库/已入库")
    private String resultStatus;

    @Schema(description = "操作时间")
    private LocalDateTime createTime;

}
