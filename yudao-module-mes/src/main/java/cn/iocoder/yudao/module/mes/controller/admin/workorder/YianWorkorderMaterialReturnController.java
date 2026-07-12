package cn.iocoder.yudao.module.mes.controller.admin.workorder;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.mes.controller.admin.workorder.vo.YianWorkorderReturnMaterialReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.workorder.vo.YianWorkorderReturnMaterialRespVO;
import cn.iocoder.yudao.module.mes.service.workorder.YianWorkorderMaterialReturnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - Yian 工单退料")
@RestController
@RequestMapping("/mes/yian/workorder")
@Validated
public class YianWorkorderMaterialReturnController {

    @Resource
    private YianWorkorderMaterialReturnService materialReturnService;

    @PostMapping("/return-material")
    @Operation(summary = "提交工单退料")
    @PreAuthorize("@ss.hasPermission('mes:wm-return-issue:create')")
    public CommonResult<YianWorkorderReturnMaterialRespVO> submitReturnMaterial(
            @Valid @RequestBody YianWorkorderReturnMaterialReqVO reqVO) {
        return success(materialReturnService.submitReturnMaterial(reqVO));
    }
}
