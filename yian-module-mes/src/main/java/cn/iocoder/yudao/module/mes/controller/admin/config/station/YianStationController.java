package cn.iocoder.yudao.module.mes.controller.admin.config.station;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.mes.controller.admin.config.station.vo.YianStationPageReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.station.vo.YianStationRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.station.vo.YianStationSaveReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.station.YianStationDO;
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

import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - 翼安站点管理")
@RestController
@RequestMapping("/mes/config/station")
@Validated
public class YianStationController {

    @Resource
    private YianStationService stationService;
    @Resource
    private AdminUserApi adminUserApi;

    @PostMapping("/create")
    @Operation(summary = "创建站点")
    @PreAuthorize("@ss.hasPermission('mes:station:create')")
    public CommonResult<Long> createStation(@Valid @RequestBody YianStationSaveReqVO createReqVO) {
        return success(stationService.createStation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新站点")
    @PreAuthorize("@ss.hasPermission('mes:station:update')")
    public CommonResult<Boolean> updateStation(@Valid @RequestBody YianStationSaveReqVO updateReqVO) {
        stationService.updateStation(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除站点")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mes:station:delete')")
    public CommonResult<Boolean> deleteStation(@RequestParam("id") Long id) {
        stationService.deleteStation(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得站点")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mes:station:query')")
    public CommonResult<YianStationRespVO> getStation(@RequestParam("id") Long id) {
        YianStationDO station = stationService.getStation(id);
        return success(buildRespVO(station));
    }

    @GetMapping("/page")
    @Operation(summary = "获得站点分页")
    @PreAuthorize("@ss.hasPermission('mes:station:query')")
    public CommonResult<PageResult<YianStationRespVO>> getStationPage(@Valid YianStationPageReqVO pageReqVO) {
        PageResult<YianStationDO> pageResult = stationService.getStationPage(pageReqVO);
        // 批量查用户名
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSet(pageResult.getList(), YianStationDO::getPrincipalUserId));
        List<YianStationRespVO> voList = BeanUtils.toBean(pageResult.getList(), YianStationRespVO.class, vo -> {
            AdminUserRespDTO user = userMap.get(vo.getPrincipalUserId());
            if (user != null) {
                vo.setPrincipalUserName(user.getNickname());
            }
        });
        return success(new PageResult<>(voList, pageResult.getTotal()));
    }

    private YianStationRespVO buildRespVO(YianStationDO station) {
        if (station == null) return null;
        YianStationRespVO vo = BeanUtils.toBean(station, YianStationRespVO.class);
        if (station.getPrincipalUserId() != null) {
            AdminUserRespDTO user = adminUserApi.getUser(station.getPrincipalUserId());
            if (user != null) {
                vo.setPrincipalUserName(user.getNickname());
            }
        }
        return vo;
    }

}
