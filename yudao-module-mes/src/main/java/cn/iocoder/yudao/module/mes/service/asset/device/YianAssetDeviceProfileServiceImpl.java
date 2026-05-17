package cn.iocoder.yudao.module.mes.service.asset.device;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.module.mes.controller.admin.asset.device.vo.YianAssetDeviceProfileRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.device.vo.YianAssetDeviceProfileRespVO.TimelineEventVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.asset.battery.YianAssetBatteryDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.dv.checkrecord.MesDvCheckRecordDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.dv.machinery.MesDvMachineryDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.dv.maintenrecord.MesDvMaintenRecordDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.dv.repair.MesDvRepairDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.md.workstation.MesMdWorkshopDO;
import cn.iocoder.yudao.module.mes.dal.mysql.asset.battery.YianAssetBatteryMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.dv.checkrecord.MesDvCheckRecordMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.dv.maintenrecord.MesDvMaintenRecordMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.dv.repair.MesDvRepairMapper;
import cn.iocoder.yudao.module.mes.service.dv.machinery.MesDvMachineryService;
import cn.iocoder.yudao.module.mes.service.md.workstation.MesMdWorkshopService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.mes.enums.ErrorCodeConstants.DV_MACHINERY_NOT_EXISTS;

@Service
@Validated
public class YianAssetDeviceProfileServiceImpl implements YianAssetDeviceProfileService {

    @Resource
    private MesDvMachineryService machineryService;
    @Resource
    private MesMdWorkshopService workshopService;
    @Resource
    private MesDvRepairMapper repairMapper;
    @Resource
    private MesDvMaintenRecordMapper maintenRecordMapper;
    @Resource
    private MesDvCheckRecordMapper checkRecordMapper;
    @Resource
    private YianAssetBatteryMapper batteryMapper;

    @Override
    public YianAssetDeviceProfileRespVO getDeviceProfile(Long machineryId) {
        // 1. 查询设备基本信息
        MesDvMachineryDO machinery = machineryService.getMachinery(machineryId);
        if (machinery == null) {
            throw exception(DV_MACHINERY_NOT_EXISTS);
        }

        // 2. 查询车间名称
        String workshopName = "-";
        MesMdWorkshopDO workshop = workshopService.getWorkshop(machinery.getWorkshopId());
        if (workshop != null) {
            workshopName = workshop.getName();
        }

        // 3. 查询关联电池
        List<YianAssetBatteryDO> batteries = batteryMapper.selectList(
                YianAssetBatteryDO::getLinkedMachineryId, machineryId);
        List<String> batteryCodes = batteries.stream()
                .map(YianAssetBatteryDO::getCode).toList();

        // 4. 查询维修、保养、点检记录
        List<MesDvRepairDO> repairs = repairMapper.selectList(
                MesDvRepairDO::getMachineryId, machineryId);
        List<MesDvMaintenRecordDO> maintens = maintenRecordMapper.selectList(
                MesDvMaintenRecordDO::getMachineryId, machineryId);
        List<MesDvCheckRecordDO> checks = checkRecordMapper.selectList(
                MesDvCheckRecordDO::getMachineryId, machineryId);

        // 5. 构建预警信息
        List<String> warnings = new ArrayList<>();
        List<String> missingItems = new ArrayList<>();
        for (YianAssetBatteryDO battery : batteries) {
            if ("danger".equalsIgnoreCase(battery.getHealthStatus())) {
                warnings.add("关联电池 " + battery.getCode() + " 命中低寿命强规则，当前不建议安排放飞任务");
            } else if ("warning".equalsIgnoreCase(battery.getHealthStatus())) {
                warnings.add("关联电池 " + battery.getCode() + " 寿命预警，建议优先复检");
            }
        }
        // 未完成的维修工单
        long activeRepairCount = repairs.stream()
                .filter(r -> r.getStatus() != null && r.getStatus() < 4)
                .count();
        if (activeRepairCount > 0) {
            warnings.add("当前有 " + activeRepairCount + " 条未关闭的维修工单");
        }

        // 6. 构建当前状态推断
        String currentStage = resolveCurrentStage(machinery, batteries, activeRepairCount);
        String statusReason = resolveStatusReason(machinery, batteries, activeRepairCount);
        String statusSource = resolveStatusSource(repairs, checks);

        // 7. 构建工单摘要
        String workorderSummary = activeRepairCount > 0
                ? "有 " + activeRepairCount + " 条在途维修工单"
                : "当前无在途维修工单";

        // 8. 构建履历时间线
        List<TimelineEventVO> history = buildTimeline(machinery, repairs, maintens, checks, workshopName);

        return YianAssetDeviceProfileRespVO.builder()
                .deviceCode(machinery.getCode())
                .deviceName(machinery.getName())
                .currentStage(currentStage)
                .statusReason(statusReason)
                .statusSource(statusSource)
                .repairCount((long) repairs.size())
                .maintenCount((long) maintens.size())
                .checkCount((long) checks.size())
                .linkedBatteryCodes(batteryCodes)
                .workorderSummary(workorderSummary)
                .warnings(warnings)
                .missingItems(missingItems)
                .history(history)
                .build();
    }

    private String resolveCurrentStage(MesDvMachineryDO machinery,
                                       List<YianAssetBatteryDO> batteries,
                                       long activeRepairCount) {
        // 有未关闭维修 -> 维修中/停飞待放行
        if (activeRepairCount > 0) {
            return "停飞待放行";
        }
        // 有危险电池 -> 待检观察
        boolean hasDangerBattery = batteries.stream()
                .anyMatch(b -> "danger".equalsIgnoreCase(b.getHealthStatus()));
        if (hasDangerBattery) {
            return "待检观察";
        }
        // 有预警电池 -> 待检观察
        boolean hasWarningBattery = batteries.stream()
                .anyMatch(b -> "warning".equalsIgnoreCase(b.getHealthStatus()));
        if (hasWarningBattery) {
            return "待检观察";
        }
        // 默认
        return "主档在册";
    }

    private String resolveStatusReason(MesDvMachineryDO machinery,
                                       List<YianAssetBatteryDO> batteries,
                                       long activeRepairCount) {
        if (activeRepairCount > 0) {
            return "设备有未关闭的维修工单，当前处于停飞状态";
        }
        boolean hasDangerBattery = batteries.stream()
                .anyMatch(b -> "danger".equalsIgnoreCase(b.getHealthStatus()));
        if (hasDangerBattery) {
            return "关联电池命中低寿命强规则，建议优先完成健康复检";
        }
        boolean hasWarningBattery = batteries.stream()
                .anyMatch(b -> "warning".equalsIgnoreCase(b.getHealthStatus()));
        if (hasWarningBattery) {
            return "关联电池存在寿命预警，建议安排复检";
        }
        return "当前设备状态正常，可正常执行任务";
    }

    private String resolveStatusSource(List<MesDvRepairDO> repairs,
                                       List<MesDvCheckRecordDO> checks) {
        List<String> sources = new ArrayList<>();
        if (!repairs.isEmpty()) {
            sources.add("维修记录");
        }
        if (!checks.isEmpty()) {
            sources.add("点检记录");
        }
        return sources.isEmpty() ? "来源：设备主档" : "来源：" + String.join("、", sources);
    }

    private List<TimelineEventVO> buildTimeline(MesDvMachineryDO machinery,
                                                 List<MesDvRepairDO> repairs,
                                                 List<MesDvMaintenRecordDO> maintens,
                                                 List<MesDvCheckRecordDO> checks,
                                                 String workshopName) {
        List<TimelineEventVO> events = new ArrayList<>();

        // 建档事件
        if (machinery.getCreateTime() != null) {
            events.add(TimelineEventVO.builder()
                    .id("device-create")
                    .happenedAt(formatTime(machinery.getCreateTime()))
                    .title("设备建档启用")
                    .detail("完成单机建档，进入设备台账。所属车间：" + workshopName)
                    .stage("建档")
                    .evidence("来源：设备主档")
                    .tone("success")
                    .build());
        }

        // 维修事件
        for (MesDvRepairDO repair : repairs) {
            LocalDateTime time = repair.getFinishDate() != null ? repair.getFinishDate() : repair.getRequireDate();
            String title = repair.getStatus() != null && repair.getStatus() >= 4
                    ? "维修完成：" + repair.getName()
                    : "维修中：" + repair.getName();
            String tone = repair.getStatus() != null && repair.getStatus() >= 4 ? "info" : "warning";
            events.add(TimelineEventVO.builder()
                    .id("repair-" + repair.getId())
                    .happenedAt(formatTime(time))
                    .title(title)
                    .detail(repair.getRemark() != null ? repair.getRemark() : repair.getName())
                    .stage("维修")
                    .evidence("来源：维修工单 " + repair.getCode())
                    .tone(tone)
                    .build());
        }

        // 保养事件
        for (MesDvMaintenRecordDO mainten : maintens) {
            events.add(TimelineEventVO.builder()
                    .id("mainten-" + mainten.getId())
                    .happenedAt(formatTime(mainten.getMaintenTime()))
                    .title("保养完成")
                    .detail(mainten.getRemark() != null ? mainten.getRemark() : "保养记录")
                    .stage("保养")
                    .evidence("来源：保养记录")
                    .tone("success")
                    .build());
        }

        // 点检事件
        for (MesDvCheckRecordDO check : checks) {
            String title = check.getStatus() != null && check.getStatus() == 20
                    ? "点检通过" : "点检进行中";
            events.add(TimelineEventVO.builder()
                    .id("check-" + check.getId())
                    .happenedAt(formatTime(check.getCheckTime()))
                    .title(title)
                    .detail(check.getRemark() != null ? check.getRemark() : "点检记录")
                    .stage("点检")
                    .evidence("来源：点检记录")
                    .tone("success")
                    .build());
        }

        // 按时间倒序排列
        events.sort(Comparator.comparing(TimelineEventVO::getHappenedAt, Comparator.nullsLast(Comparator.reverseOrder())));
        return events;
    }

    private String formatTime(LocalDateTime time) {
        return time == null ? null : LocalDateTimeUtil.formatNormal(time);
    }
}
