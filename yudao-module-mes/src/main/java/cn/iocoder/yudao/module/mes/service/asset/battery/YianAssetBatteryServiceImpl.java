package cn.iocoder.yudao.module.mes.service.asset.battery;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo.YianAssetBatteryListReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo.YianAssetBatterySaveReqVO;
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

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.mes.enums.ErrorCodeConstants.*;

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
    public Long createBattery(YianAssetBatterySaveReqVO createReqVO) {
        workshopService.getWorkshop(createReqVO.getWorkshopId());
        validateLinkedMachineryExists(createReqVO.getLinkedDeviceId());
        validateBatteryCodeUnique(null, createReqVO.getBatteryCode());
        validateBatterySerialNumberUnique(null, createReqVO.getSerialNumber());

        YianAssetBatteryDO battery = BeanUtils.toBean(createReqVO, YianAssetBatteryDO.class, item -> {
            item.setCode(createReqVO.getBatteryCode());
            item.setLinkedMachineryId(createReqVO.getLinkedDeviceId());
            item.setHealthStatus(StrUtil.blankToDefault(createReqVO.getHealthStatus(), "normal"));
            item.setSoh(ObjUtil.defaultIfNull(createReqVO.getSoh(), 100));
            item.setCycleCount(ObjUtil.defaultIfNull(createReqVO.getCycleCount(), 0));
        });
        batteryMapper.insert(battery);
        return battery.getId();
    }

    @Override
    public void updateBattery(YianAssetBatterySaveReqVO updateReqVO) {
        validateBatteryExists(updateReqVO.getId());
        workshopService.getWorkshop(updateReqVO.getWorkshopId());
        validateLinkedMachineryExists(updateReqVO.getLinkedDeviceId());
        validateBatteryCodeUnique(updateReqVO.getId(), updateReqVO.getBatteryCode());
        validateBatterySerialNumberUnique(updateReqVO.getId(), updateReqVO.getSerialNumber());

        YianAssetBatteryDO updateObj = BeanUtils.toBean(updateReqVO, YianAssetBatteryDO.class, item -> {
            item.setCode(updateReqVO.getBatteryCode());
            item.setLinkedMachineryId(updateReqVO.getLinkedDeviceId());
            item.setHealthStatus(StrUtil.blankToDefault(updateReqVO.getHealthStatus(), "normal"));
            item.setSoh(ObjUtil.defaultIfNull(updateReqVO.getSoh(), 100));
            item.setCycleCount(ObjUtil.defaultIfNull(updateReqVO.getCycleCount(), 0));
        });
        batteryMapper.updateById(updateObj);
    }

    @Override
    public YianAssetBatteryDO getBattery(Long id) {
        return batteryMapper.selectById(id);
    }

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
                respVO.getFailureCodes().put(key, "所属站点编码不能为空");
                return;
            }
            MesMdWorkshopDO workshop = workshopCodeMap.get(importItem.getWorkshopCode());
            if (workshop == null) {
                respVO.getFailureCodes().put(key, "所属站点编码[" + importItem.getWorkshopCode() + "]不存在");
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

    private void validateBatteryExists(Long id) {
        if (id == null || batteryMapper.selectById(id) == null) {
            throw exception(ASSET_BATTERY_NOT_EXISTS);
        }
    }

    private void validateLinkedMachineryExists(Long linkedDeviceId) {
        if (linkedDeviceId == null) {
            return;
        }
        if (machineryMapper.selectById(linkedDeviceId) == null) {
            throw exception(DV_MACHINERY_NOT_EXISTS);
        }
    }

    private void validateBatteryCodeUnique(Long id, String batteryCode) {
        if (StrUtil.isBlank(batteryCode)) {
            return;
        }
        YianAssetBatteryDO battery = batteryMapper.selectByCode(batteryCode);
        if (battery == null) {
            return;
        }
        if (ObjUtil.notEqual(battery.getId(), id)) {
            throw exception(ASSET_BATTERY_CODE_DUPLICATE);
        }
    }

    private void validateBatterySerialNumberUnique(Long id, String serialNumber) {
        if (StrUtil.isBlank(serialNumber)) {
            return;
        }
        YianAssetBatteryDO battery = batteryMapper.selectBySerialNumber(serialNumber);
        if (battery == null) {
            return;
        }
        if (ObjUtil.notEqual(battery.getId(), id)) {
            throw exception(ASSET_BATTERY_SERIAL_NUMBER_DUPLICATE);
        }
    }
}
