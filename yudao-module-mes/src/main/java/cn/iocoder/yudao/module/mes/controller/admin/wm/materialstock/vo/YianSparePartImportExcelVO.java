package cn.iocoder.yudao.module.mes.controller.admin.wm.materialstock.vo;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 翼安智链 - 备件批量导入 Excel VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YianSparePartImportExcelVO {

    @ExcelProperty("料号")
    private String code;

    @ExcelProperty("备件名称")
    private String name;

    @ExcelProperty("规格型号")
    private String specification;

    @ExcelProperty("分类")
    private String categoryName;

    @ExcelProperty("供应商")
    private String vendorName;

    @ExcelProperty("当前库存")
    private BigDecimal quantity;

    @ExcelProperty("安全库存")
    private BigDecimal minStock;

    @ExcelProperty("计量单位")
    private String unitMeasureName;

}
