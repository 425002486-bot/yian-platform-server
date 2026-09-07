package cn.iocoder.yudao.module.mes.controller.admin.workorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "管理后台 - Yian 工单退料 Request VO")
@Data
public class YianWorkorderReturnMaterialReqVO {

    @Schema(description = "工单 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "105")
    @NotNull(message = "工单 ID 不能为空")
    private Long workorderId;

    @Schema(description = "工单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "WO-20260508-002")
    @NotBlank(message = "工单编号不能为空")
    private String orderNo;

    @Schema(description = "操作人", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵工")
    @NotBlank(message = "操作人不能为空")
    private String operator;

    @Schema(description = "退料原因", requiredMode = Schema.RequiredMode.REQUIRED, example = "多领未使用")
    @NotBlank(message = "退料原因不能为空")
    private String reason;

    @Schema(description = "退料项列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    @NotEmpty(message = "退料项不能为空")
    private List<Item> items;

    @Data
    public static class Item {

        @Schema(description = "物料 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2001")
        @NotNull(message = "物料 ID 不能为空")
        private Long itemId;

        @Schema(description = "物料名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "减震球")
        @NotBlank(message = "物料名称不能为空")
        private String itemName;

        @Schema(description = "规格", example = "M30T")
        private String itemSpec;

        @Schema(description = "当前前端已领数量（仅用于兼容旧的本地领料记录）", example = "1")
        private BigDecimal pickedQuantity;

        @Schema(description = "退料数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "退料数量不能为空")
        @DecimalMin(value = "0.01", message = "退料数量必须大于 0")
        private BigDecimal returnQuantity;

        @Schema(description = "分配明细", requiredMode = Schema.RequiredMode.REQUIRED)
        @Valid
        @NotEmpty(message = "退料分配明细不能为空")
        private List<Allocation> allocations;
    }

    @Data
    public static class Allocation {

        @Schema(description = "库存记录 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "301")
        @NotNull(message = "库存记录 ID 不能为空")
        private Long materialStockId;

        @Schema(description = "物料 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2001")
        @NotNull(message = "物料 ID 不能为空")
        private Long itemId;

        @Schema(description = "退料数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "明细退料数量不能为空")
        @DecimalMin(value = "0.01", message = "明细退料数量必须大于 0")
        private BigDecimal quantity;

        @Schema(description = "批次 ID", example = "501")
        private Long batchId;

        @Schema(description = "批次号", example = "BATCH20260708001")
        private String batchCode;

        @Schema(description = "仓库 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "仓库 ID 不能为空")
        private Long warehouseId;

        @Schema(description = "库区 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "库区 ID 不能为空")
        private Long locationId;

        @Schema(description = "库位 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "库位 ID 不能为空")
        private Long areaId;
    }
}
