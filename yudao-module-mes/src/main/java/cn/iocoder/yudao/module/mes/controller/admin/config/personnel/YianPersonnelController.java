package cn.iocoder.yudao.module.mes.controller.admin.config.personnel;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo.*;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.personnel.YianPersonnelExtDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.station.YianStationDO;
import cn.iocoder.yudao.module.mes.dal.mysql.config.personnel.YianPersonnelExtMapper;
import cn.iocoder.yudao.module.mes.service.config.personnel.YianPersonnelService;
import cn.iocoder.yudao.module.mes.service.config.station.YianStationService;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - 翼安人员管理")
@RestController
@RequestMapping("/mes/config/personnel")
@Validated
public class YianPersonnelController {

    private static final Map<String, String> BIZ_ROLE_LABELS = new LinkedHashMap<>();
    static {
        BIZ_ROLE_LABELS.put("site_lead", "站点负责人");
        BIZ_ROLE_LABELS.put("ops_staff", "机务人员");
        BIZ_ROLE_LABELS.put("inspector", "复检人员");
        BIZ_ROLE_LABELS.put("release_approver", "放行审核人");
        BIZ_ROLE_LABELS.put("parts_manager", "备件管理员");
        BIZ_ROLE_LABELS.put("auditor", "审计人员");
    }

    @Resource
    private YianPersonnelService personnelService;
    @Resource
    private YianPersonnelExtMapper personnelExtMapper;
    @Resource
    private YianStationService stationService;
    @Resource
    private AdminUserApi adminUserApi;

    @PostMapping("/create")
    @Operation(summary = "新增人员")
    @PreAuthorize("@ss.hasPermission('mes:personnel:create')")
    public CommonResult<Long> createPersonnel(@Valid @RequestBody YianPersonnelSaveReqVO createReqVO) {
        return success(personnelService.createPersonnel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新人员")
    @PreAuthorize("@ss.hasPermission('mes:personnel:update')")
    public CommonResult<Boolean> updatePersonnel(@Valid @RequestBody YianPersonnelSaveReqVO updateReqVO) {
        personnelService.updatePersonnel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除人员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mes:personnel:delete')")
    public CommonResult<Boolean> deletePersonnel(@RequestParam("id") Long id) {
        personnelService.deletePersonnel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得人员详情")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mes:personnel:query')")
    public CommonResult<YianPersonnelRespVO> getPersonnel(@RequestParam("id") Long id) {
        YianPersonnelExtDO ext = personnelService.getPersonnel(id);
        return success(buildRespVO(ext));
    }

    @GetMapping("/page")
    @Operation(summary = "获得人员分页")
    @PreAuthorize("@ss.hasPermission('mes:personnel:query')")
    public CommonResult<PageResult<YianPersonnelRespVO>> getPersonnelPage(@Valid YianPersonnelPageReqVO pageReqVO) {
        PageResult<YianPersonnelExtDO> pageResult = personnelService.getPersonnelPage(pageReqVO);

        // 批量查关联
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSet(pageResult.getList(), YianPersonnelExtDO::getUserId));
        Set<Long> stationIds = convertSet(pageResult.getList(), YianPersonnelExtDO::getStationId);
        stationIds.remove(null);
        Map<Long, YianStationDO> stationMap = new HashMap<>();
        for (Long sid : stationIds) {
            YianStationDO station = stationService.getStation(sid);
            if (station != null) stationMap.put(sid, station);
        }

        List<YianPersonnelRespVO> voList = new ArrayList<>();
        for (YianPersonnelExtDO ext : pageResult.getList()) {
            YianPersonnelRespVO vo = BeanUtils.toBean(ext, YianPersonnelRespVO.class);
            AdminUserRespDTO user = userMap.get(ext.getUserId());
            if (user != null) {
                vo.setUserName(user.getNickname());
                vo.setMobile(user.getMobile());
                vo.setUserStatus(user.getStatus());
            }
            YianStationDO station = stationMap.get(ext.getStationId());
            if (station != null) {
                vo.setStationName(station.getName());
            }
            vo.setBizRoleLabel(BIZ_ROLE_LABELS.getOrDefault(ext.getBizRole(), ext.getBizRole()));
            voList.add(vo);
        }
        return success(new PageResult<>(voList, pageResult.getTotal()));
    }

    @GetMapping("/biz-roles")
    @Operation(summary = "获得业务角色列表（下拉用）")
    public CommonResult<List<Map<String, String>>> getBizRoles() {
        List<Map<String, String>> list = new ArrayList<>();
        BIZ_ROLE_LABELS.forEach((code, label) -> {
            Map<String, String> item = new LinkedHashMap<>();
            item.put("code", code);
            item.put("label", label);
            list.add(item);
        });
        return success(list);
    }

    @GetMapping("/role-summary")
    @Operation(summary = "获得角色概览（含人数和权限描述）")
    public CommonResult<List<Map<String, Object>>> getRoleSummary() {
        // 预定义角色描述和权限矩阵
        String[][] roleDefs = {
            {"site_lead",        "站点负责人", "查看设备与工单，派工调度，推动审批节点",
             "view", "exec", "view", "view", "view", "view", "view", "exec"},
            {"ops_staff",        "机务人员",   "执行受理、初诊、领料、维修等一线作业",
             "view", "exec", "exec", "exec", "view", "view", "view", "none"},
            {"inspector",        "复检人员",   "确认或驳回复检结果",
             "view", "view", "view", "view", "exec", "view", "view", "none"},
            {"release_approver", "放行审核人", "完成放行审核，形成放行结论",
             "view", "view", "view", "view", "view", "exec", "view", "none"},
            {"parts_manager",    "备件管理员", "库存管理、入库登记、领退料处理",
             "view", "view", "view", "exec", "view", "view", "view", "none"},
            {"auditor",          "审计人员",   "查看日志、履历和责任链，完整追溯关键操作",
             "view", "view", "view", "view", "view", "view", "exec", "none"},
        };
        String[] permKeys = {"asset", "intake", "repair", "parts", "inspect", "release", "audit", "config"};

        List<Map<String, Object>> result = new ArrayList<>();
        for (String[] def : roleDefs) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("code", def[0]);
            item.put("name", def[1]);
            item.put("description", def[2]);
            item.put("userCount", personnelExtMapper.selectCountByBizRole(def[0]));
            Map<String, String> perms = new LinkedHashMap<>();
            for (int i = 0; i < permKeys.length; i++) {
                perms.put(permKeys[i], def[3 + i]);
            }
            item.put("perms", perms);
            result.add(item);
        }
        return success(result);
    }

    private YianPersonnelRespVO buildRespVO(YianPersonnelExtDO ext) {
        if (ext == null) return null;
        YianPersonnelRespVO vo = BeanUtils.toBean(ext, YianPersonnelRespVO.class);
        AdminUserRespDTO user = adminUserApi.getUser(ext.getUserId());
        if (user != null) {
            vo.setUserName(user.getNickname());
            vo.setMobile(user.getMobile());
            vo.setUserStatus(user.getStatus());
        }
        if (ext.getStationId() != null) {
            YianStationDO station = stationService.getStation(ext.getStationId());
            if (station != null) vo.setStationName(station.getName());
        }
        vo.setBizRoleLabel(BIZ_ROLE_LABELS.getOrDefault(ext.getBizRole(), ext.getBizRole()));
        return vo;
    }

}
