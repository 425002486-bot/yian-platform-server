package cn.iocoder.yudao.module.mes.controller.admin.wm.materialstock.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - MES 入库登记 Request VO")
@Data
public class MesWmMaterialStockInboundReqVO {

    @Schema(description = "入库类型：purchase-采购到货, repair_return-返修回库, pick_return-退料入库",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "purchase")
    @NotNull(message = "入库类型不能为空")
    private String inboundType;

    @Schema(description = "物料编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "备件不能为空")
    private Long itemId;

    @Schema(description = "入库数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "入库数量不能为空")
    private BigDecimal quantity;

    @Schema(description = "供应商编号", example = "1")
    private Long vendorId;

    @Schema(description = "批次号", example = "PL20260507-A")
    private String batchCode;

    @Schema(description = "仓库编号（MVP单仓，可不传，默认使用备件仓库）", example = "1")
    private Long warehouseId;

    @Schema(description = "库区编号", example = "1")
    private Long locationId;

    @Schema(description = "库位编号", example = "1")
    private Long areaId;

    @Schema(description = "入库备注", example = "本次到货12套，已完成到货核验")
    private String remark;

}
