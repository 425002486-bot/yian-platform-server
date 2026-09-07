package cn.iocoder.yudao.module.mes.service.config.rule;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianBatteryRuleEvaluateReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianBatteryRuleEvaluateRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianDeviceAdmissionRuleEvaluateRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianReleaseRuleEvaluateReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianReleaseRuleEvaluateRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianRuleSaveReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianRuleRuntimeRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianSlaRuleSaveReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianWorkorderStageEvaluateReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianWorkorderStageEvaluateRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.device.vo.YianAssetDeviceProfileRespVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.asset.battery.YianAssetBatteryDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.dv.machinery.MesDvMachineryDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianRuleChangeLogDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianRuleDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianSlaRuleDO;
import cn.iocoder.yudao.module.mes.dal.mysql.config.rule.YianRuleChangeLogMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.config.rule.YianRuleMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.config.rule.YianSlaRuleMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.asset.battery.YianAssetBatteryMapper;
import cn.iocoder.yudao.module.mes.enums.dv.MesDvMachineryStatusEnum;
import cn.iocoder.yudao.module.mes.service.asset.device.YianAssetDeviceProfileService;
import cn.iocoder.yudao.module.mes.service.dv.machinery.MesDvMachineryService;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.mes.enums.ErrorCodeConstants.DV_MACHINERY_NOT_EXISTS;

@Service
@Validated
public class YianRuleServiceImpl implements YianRuleService {

    private static final String CATEGORY_ASSET = "asset";
    private static final String CATEGORY_WORKORDER = "workorder";
    private static final String ACTION_RESTRICT = "restrict";
    private static final String ACTION_INTERCEPT = "intercept";
    private static final String ACTION_REVIEW = "review";

    @Resource
    private YianRuleMapper ruleMapper;
    @Resource
    private YianSlaRuleMapper slaRuleMapper;
    @Resource
    private YianRuleChangeLogMapper changeLogMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private MesDvMachineryService machineryService;
    @Resource
    private YianAssetDeviceProfileService deviceProfileService;
    @Resource
    private YianAssetBatteryMapper batteryMapper;

    @Override
    public Long createRule(YianRuleSaveReqVO createReqVO) {
        YianRuleDO rule = BeanUtils.toBean(createReqVO, YianRuleDO.class);
        ruleMapper.insert(rule);
        addChangeLog(rule.getId(), rule.getName(), "create", null, "新建规则：" + rule.getName());
        return rule.getId();
    }

    @Override
    public void updateRule(YianRuleSaveReqVO updateReqVO) {
        YianRuleDO old = validateRuleExists(updateReqVO.getId());
        String before = "动作：" + old.getAction() + "，条件：" + old.getTriggerCondition();
        YianRuleDO updateObj = BeanUtils.toBean(updateReqVO, YianRuleDO.class);
        ruleMapper.updateById(updateObj);
        String after = "动作：" + updateReqVO.getAction() + "，条件：" + updateReqVO.getTriggerCondition();
        addChangeLog(old.getId(), old.getName(), "update", before, after);
    }

    @Override
    public void deleteRule(Long id) {
        YianRuleDO old = validateRuleExists(id);
        ruleMapper.deleteById(id);
        addChangeLog(id, old.getName(), "delete", old.getName(), null);
    }

    @Override
    public YianRuleDO getRule(Long id) {
        return ruleMapper.selectById(id);
    }

    @Override
    public List<YianRuleDO> getRuleListByCategory(String category) {
        return ruleMapper.selectListByCategory(category);
    }

    @Override
    public List<YianRuleDO> getAllRuleList() {
        return ruleMapper.selectList();
    }

    @Override
    public YianRuleRuntimeRespVO getRuleRuntime() {
        List<YianRuleDO> allRules = getAllRuleList();
        List<YianRuleDO> activeRules = allRules.stream().filter(item -> item.getStatus() != null && item.getStatus() == 0).toList();
        List<YianSlaRuleDO> slaRules = getSlaRuleList();

        Map<String, YianSlaRuleDO> slaByStage = new LinkedHashMap<>();
        for (YianSlaRuleDO slaRule : buildNormalizedSlaRules(slaRules)) {
            String stageKey = normalizeStageKey(slaRule, slaByStage.size());
            if (stageKey != null) {
                slaByStage.put(stageKey, slaRule);
            }
        }

        YianRuleRuntimeRespVO respVO = new YianRuleRuntimeRespVO();
        respVO.setFetchedAt(LocalDateTime.now().toString());
        respVO.setRules(activeRules);
        respVO.setSlaRules(buildNormalizedSlaRules(slaRules));
        respVO.setSlaByStage(slaByStage);
        respVO.setBatteryWarningSohThreshold(resolveBatteryWarningThreshold(activeRules));
        respVO.setBatteryDangerSohThreshold(resolveBatteryDangerThreshold(activeRules, respVO.getBatteryWarningSohThreshold()));
        respVO.setWorkorderEscalationHours(resolveWorkorderEscalationHours(activeRules));
        return respVO;
    }

    @Override
    public YianBatteryRuleEvaluateRespVO evaluateBatteryRule(YianBatteryRuleEvaluateReqVO reqVO) {
        YianRuleRuntimeRespVO runtime = getRuleRuntime();
        boolean missingEvidence = isBlank(reqVO.getLastCheckAt()) || isBlank(reqVO.getCheckSource());
        String sourceStatus = isBlank(reqVO.getHealthStatus()) ? "normal" : reqVO.getHealthStatus().trim().toLowerCase(Locale.ROOT);
        Integer soh = reqVO.getSoh();

        String healthStatus = sourceStatus;
        if (soh != null && soh <= runtime.getBatteryDangerSohThreshold()) {
            healthStatus = "danger";
        } else if (soh != null && soh < runtime.getBatteryWarningSohThreshold()) {
            healthStatus = "warning";
        } else if ("danger".equals(sourceStatus)) {
            healthStatus = "danger";
        } else if ("warning".equals(sourceStatus)) {
            healthStatus = "warning";
        }

        YianBatteryRuleEvaluateRespVO respVO = new YianBatteryRuleEvaluateRespVO();
        respVO.setHealthStatus(healthStatus);
        respVO.setMissingEvidence(missingEvidence);
        if ("danger".equals(healthStatus)) {
            respVO.setHealthLabel("禁止放行");
            respVO.setRecommendation("命中强规则，需完成更换或解绑后再进入放行审核。");
            return respVO;
        }
        if ("warning".equals(healthStatus)) {
            respVO.setHealthLabel("限制放行");
            respVO.setRecommendation("命中观察/寿命预警规则，建议缩短任务时长并尽快复检。");
            return respVO;
        }
        respVO.setHealthLabel("状态正常");
        respVO.setRecommendation(missingEvidence
                ? "当前健康状态正常，但请尽快补齐巡检或检测来源。"
                : "可按标准流程执行任务。");
        return respVO;
    }

    @Override
    public YianDeviceAdmissionRuleEvaluateRespVO evaluateDeviceAdmissionRule(Long machineryId) {
        MesDvMachineryDO machinery = machineryService.getMachinery(machineryId);
        if (machinery == null) {
            throw exception(DV_MACHINERY_NOT_EXISTS);
        }
        YianAssetDeviceProfileRespVO profile = deviceProfileService.getDeviceProfile(machineryId);
        List<YianAssetBatteryDO> batteries = batteryMapper.selectList(
                YianAssetBatteryDO::getLinkedMachineryId, machineryId);

        boolean disabled = machinery.getStatus() != null
                && machinery.getStatus().equals(MesDvMachineryStatusEnum.STOP.getStatus());
        boolean hasDangerBattery = batteries.stream()
                .anyMatch(item -> "danger".equalsIgnoreCase(item.getHealthStatus()));
        boolean hasWarningBattery = batteries.stream()
                .anyMatch(item -> "warning".equalsIgnoreCase(item.getHealthStatus()));
        boolean hasActiveWorkorder = !isBlank(profile.getWorkorderSummary())
                && !profile.getWorkorderSummary().contains("无在途维修工单");
        boolean pendingRelease = containsAny(profile.getCurrentStage(), "待放行", "放行")
                || containsAny(profile.getStatusReason(), "待放行", "放行")
                || containsAny(profile.getWorkorderSummary(), "待放行", "放行");
        boolean hasMissingItems = profile.getMissingItems() != null && !profile.getMissingItems().isEmpty();

        String currentStatus = "available";
        String currentStatusLabel = "可用";
        String currentStatusTagType = "success";
        if (pendingRelease) {
            currentStatus = "pending_release";
            currentStatusLabel = "待放行";
            currentStatusTagType = "warning";
        } else if (hasActiveWorkorder) {
            currentStatus = "repairing";
            currentStatusLabel = "维修中";
            currentStatusTagType = "warning";
        } else if (hasDangerBattery || hasWarningBattery || hasMissingItems || containsAny(profile.getCurrentStage(), "待检", "观察")) {
            currentStatus = "pending_check";
            currentStatusLabel = "待检";
            currentStatusTagType = "warning";
        }

        String warningLevel = "none";
        String warningLevelLabel = "正常";
        String warningSummary = "当前无显著风险";
        String recommendedAction = "可正常使用，按周期执行巡检";

        if (hasDangerBattery) {
            String code = batteries.stream()
                    .filter(item -> "danger".equalsIgnoreCase(item.getHealthStatus()))
                    .map(YianAssetBatteryDO::getCode)
                    .findFirst()
                    .orElse("关联电池");
            warningLevel = "danger";
            warningLevelLabel = "阻断";
            warningSummary = code + " 命中禁止放行规则";
            recommendedAction = "建议先解绑或更换高风险电池，并完成复检后再安排任务";
        } else if (pendingRelease) {
            warningLevel = "warning";
            warningLevelLabel = "预警";
            warningSummary = "存在待放行工单";
            recommendedAction = "建议优先完成放行审核，再恢复设备使用";
        } else if (hasActiveWorkorder) {
            warningLevel = "warning";
            warningLevelLabel = "预警";
            warningSummary = "存在在途维修工单";
            recommendedAction = "建议优先闭环维修和复检，再恢复设备投运";
        } else if (hasWarningBattery) {
            String code = batteries.stream()
                    .filter(item -> "warning".equalsIgnoreCase(item.getHealthStatus()))
                    .map(YianAssetBatteryDO::getCode)
                    .findFirst()
                    .orElse("关联电池");
            warningLevel = "warning";
            warningLevelLabel = "预警";
            warningSummary = code + " 命中限制放行规则";
            recommendedAction = "建议缩短任务时长并尽快安排电池复检";
        } else if (hasMissingItems) {
            warningLevel = "info";
            warningLevelLabel = "提示";
            warningSummary = profile.getMissingItems().get(0);
            recommendedAction = "建议先补齐建档附件和检测资料，再安排后续任务";
        }

        if (disabled && "available".equals(currentStatus)) {
            warningLevel = "info";
            warningLevelLabel = "提示";
            if ("当前无显著风险".equals(warningSummary)) {
                warningSummary = "设备当前处于停机状态";
            }
            recommendedAction = "设备已停机，如需重新投入使用请先人工启用并完成必要校验";
        }

        YianDeviceAdmissionRuleEvaluateRespVO respVO = new YianDeviceAdmissionRuleEvaluateRespVO();
        respVO.setMachineryId(machineryId);
        respVO.setDeviceCode(machinery.getCode());
        respVO.setEnableStatus(disabled ? "disabled" : "enabled");
        respVO.setEnableStatusLabel(disabled ? "停用" : "启用");
        respVO.setCurrentStatus(currentStatus);
        respVO.setCurrentStatusLabel(currentStatusLabel);
        respVO.setCurrentStatusTagType(currentStatusTagType);
        respVO.setStatusReason(defaultIfBlank(profile.getStatusReason(), "当前设备状态正常，可正常执行任务"));
        respVO.setStatusSource(defaultIfBlank(profile.getStatusSource(), "来源：设备画像"));
        respVO.setWarningLevel(warningLevel);
        respVO.setWarningLevelLabel(warningLevelLabel);
        respVO.setWarningSummary(warningSummary);
        respVO.setRecommendedAction(recommendedAction);
        respVO.setLinkedBatteryCodes(profile.getLinkedBatteryCodes() == null ? new ArrayList<>() : profile.getLinkedBatteryCodes());
        respVO.setWorkorderSummary(defaultIfBlank(profile.getWorkorderSummary(), "当前无在途维修工单"));
        respVO.setWarnings(profile.getWarnings() == null ? new ArrayList<>() : profile.getWarnings());
        respVO.setMissingItems(profile.getMissingItems() == null ? new ArrayList<>() : profile.getMissingItems());
        return respVO;
    }

    @Override
    public YianReleaseRuleEvaluateRespVO evaluateReleaseRule(YianReleaseRuleEvaluateReqVO reqVO) {
        List<String> alerts = new ArrayList<>();
        List<String> blockingReasons = new ArrayList<>();
        int warningCount = 0;
        int dangerCount = 0;
        int missingEvidenceCount = 0;

        if (reqVO.getBatteries() != null) {
            for (YianBatteryRuleEvaluateReqVO battery : reqVO.getBatteries()) {
                YianBatteryRuleEvaluateRespVO outcome = evaluateBatteryRule(battery);
                String batteryCode = isBlank(battery.getBatteryCode()) ? "关联电池" : battery.getBatteryCode();
                if ("danger".equals(outcome.getHealthStatus())) {
                    dangerCount++;
                    alerts.add(batteryCode + "命中禁止放行规则。");
                } else if ("warning".equals(outcome.getHealthStatus())) {
                    warningCount++;
                    alerts.add(batteryCode + "命中限制放行规则。");
                }
                if (Boolean.TRUE.equals(outcome.getMissingEvidence())) {
                    missingEvidenceCount++;
                    alerts.add(batteryCode + "缺少最近巡检或检测来源，不能作为正常放行依据。");
                }
            }
        }

        if (Boolean.FALSE.equals(reqVO.getInspectionPassed())) {
            blockingReasons.add("复检未通过，当前不允许提交正常放行或限制放行。");
        }
        if (dangerCount > 0) {
            blockingReasons.add("存在命中禁止放行规则的电池，当前只能驳回返修。");
        }

        YianReleaseRuleEvaluateRespVO respVO = new YianReleaseRuleEvaluateRespVO();
        respVO.setAlerts(alerts);
        respVO.setBlockingReasons(blockingReasons);
        if (!blockingReasons.isEmpty()) {
            respVO.setSummary("当前命中禁止放行条件，需先完成返修、解绑或更换后再重新发起放行审核。");
            respVO.setRecommendedResult("rejected");
            return respVO;
        }

        String riskLevel = isBlank(reqVO.getRiskLevel()) ? "medium" : reqVO.getRiskLevel().trim().toLowerCase(Locale.ROOT);
        if (warningCount > 0 || missingEvidenceCount > 0 || "high".equals(riskLevel)) {
            respVO.setSummary("当前建议限制放行，并补充限制条件或观察要求后再放行。");
            respVO.setRecommendedResult("limited");
            return respVO;
        }

        respVO.setSummary("当前未命中限制或拦截规则，可按标准流程提交正常放行。");
        respVO.setRecommendedResult("approved");
        return respVO;
    }

    @Override
    public YianWorkorderStageEvaluateRespVO evaluateWorkorderStageRule(YianWorkorderStageEvaluateReqVO reqVO) {
        YianRuleRuntimeRespVO runtime = getRuleRuntime();
        String stage = normalizeText(reqVO.getStage());
        YianWorkorderStageEvaluateRespVO respVO = new YianWorkorderStageEvaluateRespVO();
        respVO.setAllowed(Boolean.TRUE);
        respVO.setSummary("当前节点可按标准流程继续提交。");
        respVO.setTimeoutAction(resolveTimeoutAction(runtime, stage));

        switch (stage) {
            case "pending" -> {
                respVO.setNextStage("diagnosing");
                if (Boolean.TRUE.equals(reqVO.getGrounded())) {
                    respVO.getNotices().add("当前已选择停飞处理，后续诊断与放行会按停飞设备要求继续校验。");
                }
                respVO.setSummary("受理完成后将进入初始诊断，请关注当前节点 SLA 截止时间。");
            }
            case "diagnosing" -> {
                respVO.setNextStage(Boolean.TRUE.equals(reqVO.getNeedParts()) ? "picking" : "repairing");
                if ("high".equals(normalizeText(reqVO.getRiskLevel())) && !Boolean.TRUE.equals(reqVO.getGroundedSuggestion())) {
                    respVO.getNotices().add("当前已识别为高风险，建议同步勾选停飞建议。");
                    respVO.setSummary("高风险初诊已命中，请确认是否需要同步停飞。");
                }
            }
            case "picking" -> {
                respVO.setNextStage("repairing");
                if (reqVO.getItemCount() == null || reqVO.getItemCount() <= 0) {
                    respVO.setAllowed(Boolean.FALSE);
                    respVO.getBlockingReasons().add("领料节点至少需要确认一项备件，当前不允许空提交。");
                }
                if (Boolean.FALSE.equals(reqVO.getPickerProvided())) {
                    respVO.setAllowed(Boolean.FALSE);
                    respVO.getBlockingReasons().add("请先确认领料人再提交领料结果。");
                }
                if (Boolean.FALSE.equals(reqVO.getInventorySufficient())) {
                    respVO.setAllowed(Boolean.FALSE);
                    respVO.getBlockingReasons().add("当前存在库存不足或分配不足的备件，不允许继续提交领料。");
                }
                if (Boolean.FALSE.equals(respVO.getAllowed())) {
                    respVO.setSummary("领料节点命中库存或完整性校验规则，请先修正后再提交。");
                }
            }
            case "repairing" -> {
                respVO.setNextStage("inspecting");
                if (Boolean.FALSE.equals(reqVO.getTechnicianProvided())) {
                    respVO.setAllowed(Boolean.FALSE);
                    respVO.getBlockingReasons().add("请先确认维修责任人再提交维修结果。");
                    respVO.setSummary("维修节点需要记录维修责任人，当前不允许无责任人提交。");
                }
            }
            case "inspecting" -> {
                if (Boolean.TRUE.equals(reqVO.getInspectionPassed())) {
                    respVO.setNextStage("releasing");
                    if (Boolean.FALSE.equals(reqVO.getBatteryCheck())) {
                        respVO.setAllowed(Boolean.FALSE);
                        respVO.getBlockingReasons().add("复检通过前必须完成电池核验。");
                    }
                    if (Boolean.FALSE.equals(reqVO.getFlightTest())) {
                        respVO.setAllowed(Boolean.FALSE);
                        respVO.getBlockingReasons().add("复检通过前必须完成试飞或功能验证。");
                    }
                    if (Boolean.FALSE.equals(respVO.getAllowed())) {
                        respVO.setSummary("复检通过前未完成必要核验项，当前不允许进入放行审核。");
                    } else {
                        respVO.setSummary("复检结果通过，可进入放行审核。");
                    }
                } else {
                    respVO.setNextStage("repairing");
                    respVO.setSummary("复检未通过，将退回维修节点继续处理。");
                }
            }
            case "releasing" -> {
                respVO.setNextStage("completed");
                if ("limited".equals(normalizeText(reqVO.getReviewResult())) && !Boolean.TRUE.equals(reqVO.getRestrictionsProvided())) {
                    respVO.setAllowed(Boolean.FALSE);
                    respVO.getBlockingReasons().add("限制放行时必须填写限制条件。");
                    respVO.setSummary("放行审核命中限制放行提交校验，请先补充限制条件。");
                }
                if ("rejected".equals(normalizeText(reqVO.getReviewResult()))) {
                    respVO.setNextStage("repairing");
                    respVO.setSummary("驳回放行后将重新返回维修节点。");
                }
            }
            default -> respVO.setSummary("当前节点尚未定义统一规则，请按当前业务流程继续操作。");
        }
        return respVO;
    }

    // ===== SLA =====
    @Override
    public List<YianSlaRuleDO> getSlaRuleList() {
        return slaRuleMapper.selectList();
    }

    @Override
    public void updateSlaRule(YianSlaRuleSaveReqVO updateReqVO) {
        YianSlaRuleDO sla = slaRuleMapper.selectById(updateReqVO.getId());
        if (sla == null) throw exception(new ErrorCode(1_040_901_000, "SLA规则不存在"));
        YianSlaRuleDO updateObj = new YianSlaRuleDO();
        updateObj.setId(updateReqVO.getId());
        updateObj.setDeadlineHours(updateReqVO.getDeadlineHours());
        if (updateReqVO.getTimeoutAction() != null) {
            updateObj.setTimeoutAction(updateReqVO.getTimeoutAction());
        }
        slaRuleMapper.updateById(updateObj);
    }

    // ===== 变更记录 =====
    @Override
    public List<YianRuleChangeLogDO> getChangeLogList() {
        return changeLogMapper.selectListOrderByCreateTimeDesc();
    }

    private YianRuleDO validateRuleExists(Long id) {
        YianRuleDO rule = ruleMapper.selectById(id);
        if (rule == null) throw exception(new ErrorCode(1_040_901_001, "规则不存在"));
        return rule;
    }

    private void addChangeLog(Long ruleId, String ruleName, String changeType, String before, String after) {
        YianRuleChangeLogDO log = new YianRuleChangeLogDO();
        log.setRuleId(ruleId);
        log.setRuleName(ruleName);
        log.setChangeType(changeType);
        log.setBeforeValue(before);
        log.setAfterValue(after);
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        log.setOperatorId(userId);
        if (userId != null) {
            AdminUserRespDTO user = adminUserApi.getUser(userId);
            if (user != null) log.setOperatorName(user.getNickname());
        }
        log.setTenantId(0L);
        changeLogMapper.insert(log);
    }

    private List<YianSlaRuleDO> buildNormalizedSlaRules(List<YianSlaRuleDO> rawRules) {
        Map<String, YianSlaRuleDO> normalizedByStage = new LinkedHashMap<>();
        for (YianSlaRuleDO rule : rawRules) {
            String stageKey = normalizeStageKey(rule, normalizedByStage.size());
            if (stageKey == null) {
                continue;
            }
            normalizedByStage.put(stageKey, normalizeSlaRule(rule, stageKey));
        }
        for (Map.Entry<String, YianSlaRuleDO> entry : defaultSlaRules().entrySet()) {
            normalizedByStage.putIfAbsent(entry.getKey(), entry.getValue());
        }
        return new ArrayList<>(normalizedByStage.values());
    }

    private Map<String, YianSlaRuleDO> defaultSlaRules() {
        Map<String, YianSlaRuleDO> defaults = new LinkedHashMap<>();
        defaults.put("pending", createDefaultSlaRule(1L, "工单受理", 8, "超时升级到站点负责人", false));
        defaults.put("diagnosing", createDefaultSlaRule(2L, "初始诊断", 4, "超时提醒并要求补齐初诊结果", true));
        defaults.put("picking", createDefaultSlaRule(3L, "领料确认", 24, "超时提醒备件管理员", false));
        defaults.put("repairing", createDefaultSlaRule(4L, "维修执行", 48, "超时升级至维修主管", true));
        defaults.put("inspecting", createDefaultSlaRule(5L, "复检确认", 2, "超时提醒复检人员", false));
        defaults.put("releasing", createDefaultSlaRule(6L, "放行审核", 4, "超时升级至值班审核人", false));
        return defaults;
    }

    private YianSlaRuleDO createDefaultSlaRule(Long id, String stage, Integer deadlineHours, String timeoutAction, Boolean adjustable) {
        YianSlaRuleDO rule = new YianSlaRuleDO();
        rule.setId(id);
        rule.setStage(stage);
        rule.setDeadlineHours(deadlineHours);
        rule.setTimeoutAction(timeoutAction);
        rule.setAdjustable(adjustable);
        return rule;
    }

    private YianSlaRuleDO normalizeSlaRule(YianSlaRuleDO rule, String stageKey) {
        YianSlaRuleDO normalized = BeanUtils.toBean(rule, YianSlaRuleDO.class);
        YianSlaRuleDO fallback = defaultSlaRules().get(stageKey);
        if (normalized.getDeadlineHours() == null || normalized.getDeadlineHours() <= 0) {
            normalized.setDeadlineHours(fallback.getDeadlineHours());
        }
        if (isBlank(normalized.getTimeoutAction())) {
            normalized.setTimeoutAction(fallback.getTimeoutAction());
        }
        if (normalized.getAdjustable() == null) {
            normalized.setAdjustable(fallback.getAdjustable());
        }
        return normalized;
    }

    private String normalizeStageKey(YianSlaRuleDO row, int index) {
        String stageText = row.getStage() == null ? "" : row.getStage().toLowerCase(Locale.ROOT);
        if (stageText.contains("pending") || stageText.contains("受理")) return "pending";
        if (stageText.contains("diagnos") || stageText.contains("初诊") || stageText.contains("诊断")) return "diagnosing";
        if (stageText.contains("pick") || stageText.contains("领料")) return "picking";
        if (stageText.contains("repair") || stageText.contains("维修")) return "repairing";
        if (stageText.contains("inspect") || stageText.contains("复检")) return "inspecting";
        if (stageText.contains("releas") || stageText.contains("放行")) return "releasing";
        return switch (row.getId() == null ? index + 1 : row.getId().intValue()) {
            case 1 -> "pending";
            case 2 -> "diagnosing";
            case 3 -> "picking";
            case 4 -> "repairing";
            case 5 -> "inspecting";
            case 6 -> "releasing";
            default -> null;
        };
    }

    private Integer resolveBatteryWarningThreshold(List<YianRuleDO> rules) {
        YianRuleDO warningRule = rules.stream()
                .filter(item -> CATEGORY_ASSET.equals(item.getCategory()) && ACTION_RESTRICT.equals(item.getAction()))
                .findFirst()
                .orElse(null);
        return parseNumericThreshold(warningRule == null ? null : warningRule.getTriggerCondition(), 80);
    }

    private Integer resolveBatteryDangerThreshold(List<YianRuleDO> rules, Integer warningThreshold) {
        YianRuleDO dangerRule = rules.stream()
                .filter(item -> CATEGORY_ASSET.equals(item.getCategory()) && ACTION_INTERCEPT.equals(item.getAction()))
                .findFirst()
                .orElse(null);
        int parsed = parseNumericThreshold(dangerRule == null ? null : dangerRule.getTriggerCondition(), warningThreshold - 15);
        return Math.max(1, Math.min(parsed, warningThreshold - 1));
    }

    private Integer resolveWorkorderEscalationHours(List<YianRuleDO> rules) {
        YianRuleDO reviewRule = rules.stream()
                .filter(item -> CATEGORY_WORKORDER.equals(item.getCategory()) && ACTION_REVIEW.equals(item.getAction()))
                .findFirst()
                .orElse(null);
        return parseNumericThreshold(reviewRule == null ? null : reviewRule.getTriggerCondition(), 14);
    }

    private String resolveTimeoutAction(YianRuleRuntimeRespVO runtime, String stage) {
        if (runtime == null || runtime.getSlaByStage() == null) {
            return "";
        }
        YianSlaRuleDO slaRule = runtime.getSlaByStage().get(stage);
        return slaRule == null ? "" : slaRule.getTimeoutAction();
    }

    private String normalizeText(String value) {
        return isBlank(value) ? "" : value.trim().toLowerCase(Locale.ROOT);
    }

    private String defaultIfBlank(String value, String fallback) {
        return isBlank(value) ? fallback : value;
    }

    private boolean containsAny(String value, String... keywords) {
        if (isBlank(value) || keywords == null) {
            return false;
        }
        for (String keyword : keywords) {
            if (!isBlank(keyword) && value.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private int parseNumericThreshold(String text, int fallback) {
        if (isBlank(text)) {
            return fallback;
        }
        String candidate = text.replaceAll("[^0-9.]", " ").trim();
        if (candidate.isEmpty()) {
            return fallback;
        }
        String[] parts = candidate.split("\\s+");
        try {
            return (int) Double.parseDouble(parts[parts.length - 1]);
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

}
