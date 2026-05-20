package cn.iocoder.yudao.module.mes.controller.admin.asset.vo;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YianAssetImportExcelVO {

    @ExcelProperty("资产类型")
    private String assetType;

    @ExcelProperty("资产编码")
    private String assetCode;

    @ExcelProperty("资产名称")
    private String assetName;

    @ExcelProperty("品牌")
    private String brand;

    @ExcelProperty("规格型号")
    private String specification;

    @ExcelProperty("序列号")
    private String serialNumber;

    @ExcelProperty("设备类型编码")
    private String machineryTypeCode;

    @ExcelProperty("所属站点编码")
    private String workshopCode;

    @ExcelProperty("设备状态")
    private Integer status;

    @ExcelProperty("关联主机编码")
    private String linkedMachineryCode;

    @ExcelProperty("SOH")
    private Integer soh;

    @ExcelProperty("循环次数")
    private Integer cycleCount;

    @ExcelProperty("最近检测时间")
    private LocalDateTime lastCheckTime;

    @ExcelProperty("检测来源")
    private String checkSource;

    @ExcelProperty("健康状态")
    private String healthStatus;

    @ExcelProperty("来源依据")
    private String sourceEvidence;

    @ExcelProperty("建议动作")
    private String recommendation;

    @ExcelProperty("备注")
    private String remark;
}
