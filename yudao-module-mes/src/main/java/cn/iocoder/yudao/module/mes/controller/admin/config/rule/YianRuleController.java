package cn.iocoder.yudao.module.mes.controller.admin.config.rule;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianRuleSaveReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.rule.vo.YianSlaRuleSaveReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianRuleChangeLogDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianRuleDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianSlaRuleDO;
import cn.iocoder.yudao.module.mes.service.config.rule.YianRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 翼安规则中心")
@RestController
@RequestMapping("/mes/config/rule")
@Validated
public class YianRuleController {

    @Resource
    private YianRuleService ruleService;

    // ===== 规则 CRUD =====

    @PostMapping("/create")
    @Operation(summary = "创建规则")
    @PreAuthorize("@ss.hasPermission('mes:rule:create')")
    public CommonResult<Long> createRule(@Valid @RequestBody YianRuleSaveReqVO createReqVO) {
        return success(ruleService.createRule(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新规则")
    @PreAuthorize("@ss.hasPermission('mes:rule:update')")
    public CommonResult<Boolean> updateRule(@Valid @RequestBody YianRuleSaveReqVO updateReqVO) {
        ruleService.updateRule(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除规则")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mes:rule:delete')")
    public CommonResult<Boolean> deleteRule(@RequestParam("id") Long id) {
        ruleService.deleteRule(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得规则详情")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<YianRuleDO> getRule(@RequestParam("id") Long id) {
        return success(ruleService.getRule(id));
    }

    @GetMapping("/list")
    @Operation(summary = "获得全部规则列表")
    public CommonResult<List<YianRuleDO>> getRuleList(
            @RequestParam(value = "category", required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return success(ruleService.getRuleListByCategory(category));
        }
        return success(ruleService.getAllRuleList());
    }

    // ===== SLA =====

    @GetMapping("/sla/list")
    @Operation(summary = "获得SLA规则列表")
    public CommonResult<List<YianSlaRuleDO>> getSlaRuleList() {
        return success(ruleService.getSlaRuleList());
    }

    @PutMapping("/sla/update")
    @Operation(summary = "更新SLA规则时限")
    @PreAuthorize("@ss.hasPermission('mes:rule:update')")
    public CommonResult<Boolean> updateSlaRule(@Valid @RequestBody YianSlaRuleSaveReqVO updateReqVO) {
        ruleService.updateSlaRule(updateReqVO);
        return success(true);
    }

    // ===== 变更记录 =====

    @GetMapping("/change-log/list")
    @Operation(summary = "获得规则变更记录")
    public CommonResult<List<YianRuleChangeLogDO>> getChangeLogList() {
        return success(ruleService.getChangeLogList());
    }

}
