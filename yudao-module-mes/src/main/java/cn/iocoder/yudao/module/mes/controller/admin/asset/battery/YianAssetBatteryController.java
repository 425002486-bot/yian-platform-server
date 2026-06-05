package cn.iocoder.yudao.module.mes.controller.admin.asset.battery;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo.YianAssetBatteryListReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo.YianAssetBatteryRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo.YianAssetBatterySaveReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianBatteryRuleEvaluateReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianBatteryRuleEvaluateRespVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.asset.battery.YianAssetBatteryDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.dv.machinery.MesDvMachineryDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.md.workstation.MesMdWorkshopDO;
import cn.iocoder.yudao.module.mes.service.asset.battery.YianAssetBatteryService;
import cn.iocoder.yudao.module.mes.service.config.rule.YianRuleService;
import cn.iocoder.yudao.module.mes.service.dv.machinery.MesDvMachineryService;
import cn.iocoder.yudao.module.mes.service.md.workstation.MesMdWorkshopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - 资产电池")
@RestController
@RequestMapping("/yian/asset/battery")
@Validated
public class YianAssetBatteryController {

    @Resource
    private YianAssetBatteryService batteryService;
    @Resource
    private MesMdWorkshopService workshopService;
    @Resource
    private MesDvMachineryService machineryService;
    @Resource
    private YianRuleService ruleService;

    @PostMapping("/create")
    @Operation(summary = "创建资产电池")
    @PreAuthorize("@ss.hasPermission('asset:battery:list')")
    public CommonResult<Long> createBattery(@Valid @RequestBody YianAssetBatterySaveReqVO createReqVO) {
        return success(batteryService.createBattery(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新资产电池")
    @PreAuthorize("@ss.hasPermission('asset:battery:list')")
    public CommonResult<Boolean> updateBattery(@Valid @RequestBody YianAssetBatterySaveReqVO updateReqVO) {
        batteryService.updateBattery(updateReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取资产电池详情")
    @PreAuthorize("@ss.hasPermission('asset:battery:list')")
    public CommonResult<YianAssetBatteryRespVO> getBattery(@RequestParam("id") Long id) {
        YianAssetBatteryDO battery = batteryService.getBattery(id);
        if (battery == null) {
            return success(null);
        }
        return success(buildBatteryRespList(Collections.singletonList(battery)).get(0));
    }

    @GetMapping("/list")
    @Operation(summary = "获取资产电池列表")
    @PreAuthorize("@ss.hasPermission('asset:battery:list')")
    public CommonResult<List<YianAssetBatteryRespVO>> getBatteryList(@Validated YianAssetBatteryListReqVO reqVO) {
        return success(buildBatteryRespList(batteryService.getBatteryList(reqVO)));
    }

    private List<YianAssetBatteryRespVO> buildBatteryRespList(List<YianAssetBatteryDO> list) {
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        Map<Long, MesMdWorkshopDO> workshopMap = workshopService.getWorkshopMap(convertSet(list, YianAssetBatteryDO::getWorkshopId));
        Map<Long, MesDvMachineryDO> machineryMap = machineryService.getMachineryMap(convertSet(list, YianAssetBatteryDO::getLinkedMachineryId));
        return list.stream().map(source -> {
            YianAssetBatteryRespVO target = BeanUtils.toBean(source, YianAssetBatteryRespVO.class);
            target.setBatteryCode(source.getCode());
            target.setLastCheckAt(source.getLastCheckTime() == null ? null : LocalDateTimeUtil.formatNormal(source.getLastCheckTime()));

            YianBatteryRuleEvaluateReqVO evaluateReqVO = new YianBatteryRuleEvaluateReqVO();
            evaluateReqVO.setBatteryCode(source.getCode());
            evaluateReqVO.setSoh(source.getSoh());
            evaluateReqVO.setCycleCount(source.getCycleCount());
            evaluateReqVO.setCheckSource(source.getCheckSource());
            evaluateReqVO.setLastCheckAt(target.getLastCheckAt());
            evaluateReqVO.setHealthStatus(source.getHealthStatus());
            evaluateReqVO.setRecommendation(source.getRecommendation());
            YianBatteryRuleEvaluateRespVO evaluation = ruleService.evaluateBatteryRule(evaluateReqVO);
            target.setHealthStatus(evaluation.getHealthStatus());
            target.setHealthLabel(evaluation.getHealthLabel());
            target.setRecommendation(evaluation.getRecommendation());

            MapUtils.findAndThen(workshopMap, source.getWorkshopId(), workshop -> target.setWorkshopName(workshop.getName()));
            MapUtils.findAndThen(machineryMap, source.getLinkedMachineryId(), machinery -> {
                target.setLinkedDeviceId(machinery.getId());
                target.setLinkedDeviceCode(machinery.getCode());
                target.setLinkedDeviceName(machinery.getName());
            });
            return target;
        }).collect(Collectors.toList());
    }
}
