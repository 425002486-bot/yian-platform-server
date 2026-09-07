package cn.iocoder.yudao.module.mes.controller.admin.asset;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportExcelVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportRespVO;
import cn.iocoder.yudao.module.mes.service.asset.YianAssetImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 统一资产导入")
@RestController
@RequestMapping("/yian/asset")
@Validated
public class YianAssetImportController {

    @Resource
    private YianAssetImportService assetImportService;

    @GetMapping("/import-template")
    @Operation(summary = "获取统一资产导入模板")
    @PreAuthorize("@ss.hasPermission('asset:device:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
        List<YianAssetImportExcelVO> list = Arrays.asList(
                YianAssetImportExcelVO.builder()
                        .assetType("主机")
                        .assetCode("UAV-IMPORT-001")
                        .assetName("示例主机")
                        .brand("DJI")
                        .specification("Matrice 350 RTK")
                        .machineryTypeCode("MT-001")
                        .workshopCode("WS-001")
                        .status(1)
                        .remark("主机示例")
                        .build(),
                YianAssetImportExcelVO.builder()
                        .assetType("电池")
                        .assetCode("BAT-IMPORT-001")
                        .assetName("TB65")
                        .specification("TB65")
                        .serialNumber("TB650001")
                        .workshopCode("WS-001")
                        .linkedMachineryCode("UAV-IMPORT-001")
                        .soh(92)
                        .cycleCount(66)
                        .lastCheckTime(LocalDateTime.of(2026, 5, 11, 9, 30))
                        .checkSource("BMS")
                        .healthStatus("normal")
                        .sourceEvidence("BMS 自动回传")
                        .recommendation("可正常执行任务")
                        .remark("电池示例")
                        .build()
        );
        ExcelUtils.write(response, "资产导入模板.xls", "资产列表", YianAssetImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入统一资产")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('asset:device:import')")
    public CommonResult<YianAssetImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                           @RequestParam(value = "updateSupport", required = false,
                                                                   defaultValue = "false") Boolean updateSupport)
            throws Exception {
        List<YianAssetImportExcelVO> list = ExcelUtils.read(file, YianAssetImportExcelVO.class);
        return success(assetImportService.importAssetList(list, updateSupport));
    }
}
