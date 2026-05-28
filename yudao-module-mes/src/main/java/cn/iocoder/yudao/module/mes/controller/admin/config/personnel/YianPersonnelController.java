package cn.iocoder.yudao.module.mes.controller.admin.config.personnel;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo.YianPersonnelPageReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo.YianPersonnelRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo.YianPersonnelSaveReqVO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - 翼安人员管理")
@RestController
@RequestMapping("/mes/config/personnel")
@Validated
public class YianPersonnelController {

    private static final String[] PERMISSION_KEYS = {
            "asset", "intake", "repair", "parts", "inspect", "release", "audit", "config"
    };

    private static final String[][] ROLE_DEFS = {
            {"site_lead", "站点负责人", "查看设备与工单、派工调度并推动审批节点",
                    "exec", "exec", "view", "view", "view", "view", "view", "exec"},
            {"ops_staff", "机务人员", "执行受理、初诊、领料、维修等一线作业",
                    "view", "exec", "exec", "exec", "view", "view", "view", "none"},
            {"inspector", "复检人员", "确认或驳回复检结果",
                    "view", "view", "view", "view", "exec", "view", "view", "none"},
            {"release_approver", "放行审核人", "完成放行审核并形成最终放行结论",
                    "view", "view", "view", "view", "view", "exec", "view", "none"},
            {"parts_manager", "备件管理员", "执行库存管理、入库登记和领退料处理",
                    "none", "view", "view", "exec", "view", "view", "view", "none"},
            {"auditor", "审计人员", "查看日志、履历和责任链以追溯关键操作",
                    "view", "view", "view", "view", "view", "view", "exec", "none"}
    };

    private static final Map<String, String> BIZ_ROLE_LABELS = new LinkedHashMap<>();
    private static final Map<String, Map<String, String>> ROLE_PERMISSION_MATRIX = buildRolePermissionMatrix();

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
        return success(buildRespVO(personnelService.getPersonnel(id)));
    }

    @GetMapping("/page")
    @Operation(summary = "获得人员分页")
    @PreAuthorize("@ss.hasPermission('mes:personnel:query')")
    public CommonResult<PageResult<YianPersonnelRespVO>> getPersonnelPage(@Valid YianPersonnelPageReqVO pageReqVO) {
        PageResult<YianPersonnelExtDO> pageResult = personnelService.getPersonnelPage(pageReqVO);
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSet(pageResult.getList(), YianPersonnelExtDO::getUserId));
        Set<Long> stationIds = convertSet(pageResult.getList(), YianPersonnelExtDO::getStationId);
        stationIds.remove(null);
        Map<Long, YianStationDO> stationMap = new HashMap<>();
        for (Long stationId : stationIds) {
            YianStationDO station = stationService.getStation(stationId);
            if (station != null) {
                stationMap.put(stationId, station);
            }
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
    @Operation(summary = "获得业务角色列表")
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
    @Operation(summary = "获得角色概览（含人数和权限矩阵）")
    public CommonResult<List<Map<String, Object>>> getRoleSummary() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (String[] def : ROLE_DEFS) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("code", def[0]);
            item.put("name", def[1]);
            item.put("description", def[2]);
            item.put("userCount", personnelExtMapper.selectCountByBizRole(def[0]));
            item.put("perms", ROLE_PERMISSION_MATRIX.getOrDefault(def[0], buildEmptyPermissionMap()));
            result.add(item);
        }
        return success(result);
    }

    @GetMapping("/current-access")
    @Operation(summary = "获得当前登录人的业务角色和权限")
    public CommonResult<Map<String, Object>> getCurrentAccess() {
        Long userId = SecurityFrameworkUtils.getLoginUserId();
        String nickname = SecurityFrameworkUtils.getLoginUserNickname();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("userId", userId);
        result.put("userName", nickname);

        if (userId != null && userId.equals(1L)) {
            result.put("bizRole", "super_admin");
            result.put("bizRoleLabel", "管理员特权");
            result.put("stationId", null);
            result.put("stationName", null);
            result.put("permissions", buildFullPermissionMap());
            return success(result);
        }

        YianPersonnelExtDO ext = userId == null ? null : personnelExtMapper.selectByUserId(userId);
        if (ext == null) {
            result.put("bizRole", null);
            result.put("bizRoleLabel", null);
            result.put("stationId", null);
            result.put("stationName", null);
            result.put("permissions", buildEmptyPermissionMap());
            return success(result);
        }

        result.put("bizRole", ext.getBizRole());
        result.put("bizRoleLabel", BIZ_ROLE_LABELS.getOrDefault(ext.getBizRole(), ext.getBizRole()));
        result.put("stationId", ext.getStationId());
        result.put("stationName", resolveStationName(ext.getStationId()));
        result.put("permissions", ROLE_PERMISSION_MATRIX.getOrDefault(ext.getBizRole(), buildEmptyPermissionMap()));
        return success(result);
    }

    private YianPersonnelRespVO buildRespVO(YianPersonnelExtDO ext) {
        if (ext == null) {
            return null;
        }
        YianPersonnelRespVO vo = BeanUtils.toBean(ext, YianPersonnelRespVO.class);
        AdminUserRespDTO user = adminUserApi.getUser(ext.getUserId());
        if (user != null) {
            vo.setUserName(user.getNickname());
            vo.setMobile(user.getMobile());
            vo.setUserStatus(user.getStatus());
        }
        if (ext.getStationId() != null) {
            YianStationDO station = stationService.getStation(ext.getStationId());
            if (station != null) {
                vo.setStationName(station.getName());
            }
        }
        vo.setBizRoleLabel(BIZ_ROLE_LABELS.getOrDefault(ext.getBizRole(), ext.getBizRole()));
        return vo;
    }

    private String resolveStationName(Long stationId) {
        if (stationId == null) {
            return null;
        }
        YianStationDO station = stationService.getStation(stationId);
        return station != null ? station.getName() : null;
    }

    private static Map<String, Map<String, String>> buildRolePermissionMatrix() {
        Map<String, Map<String, String>> matrix = new LinkedHashMap<>();
        for (String[] roleDef : ROLE_DEFS) {
            Map<String, String> permissions = new LinkedHashMap<>();
            for (int i = 0; i < PERMISSION_KEYS.length; i++) {
                permissions.put(PERMISSION_KEYS[i], roleDef[3 + i]);
            }
            matrix.put(roleDef[0], permissions);
        }
        return matrix;
    }

    private static Map<String, String> buildEmptyPermissionMap() {
        Map<String, String> permissions = new LinkedHashMap<>();
        for (String key : PERMISSION_KEYS) {
            permissions.put(key, "none");
        }
        return permissions;
    }

    private static Map<String, String> buildFullPermissionMap() {
        Map<String, String> permissions = new LinkedHashMap<>();
        for (String key : PERMISSION_KEYS) {
            permissions.put(key, "exec");
        }
        return permissions;
    }
}
