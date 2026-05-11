package cn.iocoder.yudao.module.mes.service.asset.battery;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo.YianAssetBatteryListReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportExcelVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportSegmentRespVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.asset.battery.YianAssetBatteryDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.dv.machinery.MesDvMachineryDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.md.workstation.MesMdWorkshopDO;
import cn.iocoder.yudao.module.mes.dal.mysql.asset.battery.YianAssetBatteryMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.dv.machinery.MesDvMachineryMapper;
import cn.iocoder.yudao.module.mes.service.md.workstation.MesMdWorkshopService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Validated
public class YianAssetBatteryServiceImpl implements YianAssetBatteryService {

    @Resource
    private YianAssetBatteryMapper batteryMapper;
    @Resource
    private MesMdWorkshopService workshopService;
    @Resource
    private MesDvMachineryMapper machineryMapper;

    @Override
    public List<YianAssetBatteryDO> getBatteryList(YianAssetBatteryListReqVO reqVO) {
        return batteryMapper.selectList(reqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public YianAssetImportSegmentRespVO importBatteryList(List<YianAssetImportExcelVO> importBatteryList,
                                                          boolean updateSupport) {
        if (CollUtil.isEmpty(importBatteryList)) {
            return YianAssetImportSegmentRespVO.builder()
                    .createCodes(new ArrayList<>())
                    .updateCodes(new ArrayList<>())
                    .failureCodes(new LinkedHashMap<>())
                    .build();
        }

        List<MesMdWorkshopDO> allWorkshops = workshopService.getWorkshopListByStatus(
                CommonStatusEnum.ENABLE.getStatus());
        Map<String, MesMdWorkshopDO> workshopCodeMap = allWorkshops.stream()
                .collect(Collectors.toMap(MesMdWorkshopDO::getCode, w -> w, (a, b) -> a));
        Map<String, MesDvMachineryDO> machineryCodeMap = machineryMapper.selectList().stream()
                .collect(Collectors.toMap(MesDvMachineryDO::getCode, m -> m, (a, b) -> a));

        YianAssetImportSegmentRespVO respVO = YianAssetImportSegmentRespVO.builder()
                .createCodes(new ArrayList<>())
                .updateCodes(new ArrayList<>())
                .failureCodes(new LinkedHashMap<>())
                .build();
        AtomicInteger index = new AtomicInteger(1);
        importBatteryList.forEach(importItem -> {
            int currentIndex = index.getAndIncrement();
            String key = StrUtil.blankToDefault(importItem.getAssetCode(), "电池第 " + currentIndex + " 行");
            if (StrUtil.isBlank(importItem.getAssetCode())) {
                respVO.getFailureCodes().put(key, "电池编码不能为空");
                return;
            }
            if (StrUtil.isBlank(importItem.getWorkshopCode())) {
                respVO.getFailureCodes().put(key, "所属车间编码不能为空");
                return;
            }
            MesMdWorkshopDO workshop = workshopCodeMap.get(importItem.getWorkshopCode());
            if (workshop == null) {
                respVO.getFailureCodes().put(key, "所属车间编码[" + importItem.getWorkshopCode() + "]不存在");
                return;
            }
            MesDvMachineryDO linkedMachinery = null;
            if (StrUtil.isNotBlank(importItem.getLinkedMachineryCode())) {
                linkedMachinery = machineryCodeMap.get(importItem.getLinkedMachineryCode());
                if (linkedMachinery == null) {
                    respVO.getFailureCodes().put(key, "关联主机编码[" + importItem.getLinkedMachineryCode() + "]不存在");
                    return;
                }
            }

            YianAssetBatteryDO existsBattery = batteryMapper.selectByCode(importItem.getAssetCode());
            if (existsBattery == null) {
                YianAssetBatteryDO battery = buildBatteryDO(importItem, workshop.getId(),
                        linkedMachinery == null ? null : linkedMachinery.getId());
                batteryMapper.insert(battery);
                respVO.getCreateCodes().add(importItem.getAssetCode());
            } else if (updateSupport) {
                YianAssetBatteryDO updateObj = buildBatteryDO(importItem, workshop.getId(),
                        linkedMachinery == null ? null : linkedMachinery.getId());
                updateObj.setId(existsBattery.getId());
                batteryMapper.updateById(updateObj);
                respVO.getUpdateCodes().add(importItem.getAssetCode());
            } else {
                respVO.getFailureCodes().put(key, "电池编码已存在");
            }
        });
        return respVO;
    }

    private YianAssetBatteryDO buildBatteryDO(YianAssetImportExcelVO importItem, Long workshopId,
                                              Long linkedMachineryId) {
        YianAssetBatteryDO battery = BeanUtils.toBean(importItem, YianAssetBatteryDO.class, item -> {
            item.setCode(importItem.getAssetCode());
            item.setModel(StrUtil.blankToDefault(importItem.getSpecification(), importItem.getAssetName()));
            item.setWorkshopId(workshopId);
            item.setLinkedMachineryId(linkedMachineryId);
            item.setHealthStatus(StrUtil.blankToDefault(importItem.getHealthStatus(), "normal"));
            item.setSoh(ObjUtil.defaultIfNull(importItem.getSoh(), 100));
            item.setCycleCount(ObjUtil.defaultIfNull(importItem.getCycleCount(), 0));
        });
        return battery;
    }
}
