package cn.iocoder.yudao.module.mes.service.asset;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportExcelVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportSegmentRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.dv.machinery.vo.MesDvMachineryImportExcelVO;
import cn.iocoder.yudao.module.mes.controller.admin.dv.machinery.vo.MesDvMachineryImportRespVO;
import cn.iocoder.yudao.module.mes.service.asset.battery.YianAssetBatteryService;
import cn.iocoder.yudao.module.mes.service.dv.machinery.MesDvMachineryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Validated
public class YianAssetImportServiceImpl implements YianAssetImportService {

    @Resource
    private MesDvMachineryService machineryService;
    @Resource
    private YianAssetBatteryService batteryService;

    @Override
    public YianAssetImportRespVO importAssetList(List<YianAssetImportExcelVO> importAssetList, boolean updateSupport) {
        List<YianAssetImportExcelVO> machineryRows = new ArrayList<>();
        List<YianAssetImportExcelVO> batteryRows = new ArrayList<>();
        Map<String, String> failureCodes = new LinkedHashMap<>();
        int rowNo = 1;
        for (YianAssetImportExcelVO item : importAssetList) {
            String assetType = StrUtil.trimToEmpty(item.getAssetType());
            if (StrUtil.equalsAnyIgnoreCase(assetType, "主机", "无人机主机")) {
                machineryRows.add(item);
            } else if (StrUtil.equalsAnyIgnoreCase(assetType, "电池")) {
                batteryRows.add(item);
            } else {
                String key = StrUtil.blankToDefault(item.getAssetCode(), "第 " + rowNo + " 行");
                failureCodes.put(key, "资产类型仅支持“主机”或“电池”");
            }
            rowNo++;
        }

        MesDvMachineryImportRespVO machineryResp = CollUtil.isEmpty(machineryRows)
                ? MesDvMachineryImportRespVO.builder()
                .createCodes(new ArrayList<>())
                .updateCodes(new ArrayList<>())
                .failureCodes(new LinkedHashMap<>())
                .build()
                : machineryService.importMachineryList(machineryRows.stream().map(this::buildMachineryImportVO)
                .collect(Collectors.toList()), updateSupport);
        YianAssetImportSegmentRespVO batteryResp = batteryService.importBatteryList(batteryRows, updateSupport);

        failureCodes.putAll(machineryResp.getFailureCodes());
        failureCodes.putAll(batteryResp.getFailureCodes());
        return YianAssetImportRespVO.builder()
                .machineryCreateCodes(machineryResp.getCreateCodes())
                .machineryUpdateCodes(machineryResp.getUpdateCodes())
                .batteryCreateCodes(batteryResp.getCreateCodes())
                .batteryUpdateCodes(batteryResp.getUpdateCodes())
                .failureCodes(failureCodes)
                .build();
    }

    private MesDvMachineryImportExcelVO buildMachineryImportVO(YianAssetImportExcelVO item) {
        MesDvMachineryImportExcelVO machineryImportVO = new MesDvMachineryImportExcelVO();
        machineryImportVO.setCode(item.getAssetCode());
        machineryImportVO.setName(item.getAssetName());
        machineryImportVO.setBrand(item.getBrand());
        machineryImportVO.setSpecification(item.getSpecification());
        machineryImportVO.setMachineryTypeCode(item.getMachineryTypeCode());
        machineryImportVO.setWorkshopCode(item.getWorkshopCode());
        machineryImportVO.setStatus(item.getStatus() == null ? 1 : item.getStatus());
        machineryImportVO.setRemark(item.getRemark());
        return machineryImportVO;
    }
}
