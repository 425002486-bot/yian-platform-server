package cn.iocoder.yudao.module.mes.controller.admin.asset.device;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.mes.controller.admin.asset.device.vo.YianAssetDeviceProfileRespVO;
import cn.iocoder.yudao.module.mes.service.asset.device.YianAssetDeviceProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 资产设备画像")
@RestController
@RequestMapping("/yian/asset/device")
@Validated
public class YianAssetDeviceProfileController {

    @Resource
    private YianAssetDeviceProfileService profileService;

    @GetMapping("/profile")
    @Operation(summary = "获取设备画像（聚合详情）")
    @Parameter(name = "id", description = "设备ID", required = true)
    @PreAuthorize("@ss.hasPermission('mes:dv-machinery:query')")
    public CommonResult<YianAssetDeviceProfileRespVO> getDeviceProfile(@RequestParam("id") Long machineryId) {
        return success(profileService.getDeviceProfile(machineryId));
    }
}
