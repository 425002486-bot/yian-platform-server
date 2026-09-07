package cn.iocoder.yudao.module.mes.controller.admin.workorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - Yian 工单退料 Response VO")
@Data
public class YianWorkorderReturnMaterialRespVO {

    @Schema(description = "退料单 ID", example = "801")
    private Long issueId;

    @Schema(description = "退料单编号", example = "RT-WO-20260508-002-140501")
    private String issueCode;

    @Schema(description = "退料时间")
    private LocalDateTime returnedAt;

    @Schema(description = "退料项")
    private List<Item> items;

    @Data
    public static class Item {
        private Long itemId;
        private String itemName;
        private String itemSpec;
        private BigDecimal returnQuantity;
        private String returnReason;
    }
}
