package cn.iocoder.yudao.module.mes.controller.app.officialsite;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.mes.controller.app.officialsite.vo.YianOfficialSiteLeadCreateReqVO;
import cn.iocoder.yudao.module.mes.controller.app.officialsite.vo.YianOfficialSiteOverviewRespVO;
import cn.iocoder.yudao.module.mes.service.officialsite.YianOfficialSiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 翼安智链官网")
@RestController
@RequestMapping("/mes/official-site")
@Validated
public class YianOfficialSiteController {

    @Resource
    private YianOfficialSiteService officialSiteService;

    @GetMapping("/overview")
    @Operation(summary = "获取官网概览数据")
    @PermitAll
    public CommonResult<YianOfficialSiteOverviewRespVO> getOverview() {
        return success(officialSiteService.getOverview());
    }

    @PostMapping("/lead")
    @Operation(summary = "提交官网咨询线索")
    @PermitAll
    public CommonResult<Long> createLead(@Valid @RequestBody YianOfficialSiteLeadCreateReqVO createReqVO) {
        return success(officialSiteService.createLead(createReqVO));
    }
}
