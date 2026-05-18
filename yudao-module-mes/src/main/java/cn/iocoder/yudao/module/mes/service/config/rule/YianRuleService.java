package cn.iocoder.yudao.module.mes.service.config.rule;

import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianRuleSaveReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianSlaRuleSaveReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianRuleChangeLogDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianRuleDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianSlaRuleDO;
import jakarta.validation.Valid;

import java.util.List;

public interface YianRuleService {

    // ===== 规则 CRUD =====
    Long createRule(@Valid YianRuleSaveReqVO createReqVO);
    void updateRule(@Valid YianRuleSaveReqVO updateReqVO);
    void deleteRule(Long id);
    YianRuleDO getRule(Long id);
    List<YianRuleDO> getRuleListByCategory(String category);
    List<YianRuleDO> getAllRuleList();

    // ===== SLA =====
    List<YianSlaRuleDO> getSlaRuleList();
    void updateSlaRule(@Valid YianSlaRuleSaveReqVO updateReqVO);

    // ===== 变更记录 =====
    List<YianRuleChangeLogDO> getChangeLogList();

}
