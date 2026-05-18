package cn.iocoder.yudao.module.mes.controller.admin.config.personnel;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo.*;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.personnel.YianPersonnelExtDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.station.YianStationDO;
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
