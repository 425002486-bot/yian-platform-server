package cn.iocoder.yudao.module.mes.service.config.rule;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianRuleSaveReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianSlaRuleSaveReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianRuleChangeLogDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianRuleDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianSlaRuleDO;
import cn.iocoder.yudao.module.mes.dal.mysql.config.rule.YianRuleChangeLogMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.config.rule.YianRuleMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.config.rule.YianSlaRuleMapper;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class YianRuleServiceImpl implements YianRuleService {

    @Resource
    private YianRuleMapper ruleMapper;
    @Resource
    private YianSlaRuleMapper slaRuleMapper;
    @Resource
    private YianRuleChangeLogMapper changeLogMapper;
    @Resource
    private AdminUserApi adminUserApi;

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

}
