package cn.iocoder.yudao.module.mes.controller.admin.ai;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianAiCapabilityRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianAssetDeviceDocumentParseRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianAssetDeviceReparseReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderDiagnosisDraftGenerateReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderDiagnosisDraftRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderFlightLogParseRespVO;
import cn.iocoder.yudao.module.mes.service.ai.YianAiIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "Admin - Yian AI Integration")
@RestController
@RequestMapping("/yian/ai")
@Validated
public class YianAiIntegrationController {

    @Resource
    private YianAiIntegrationService yianAiIntegrationService;

    @GetMapping("/capabilities")
    @Operation(summary = "Get AI capability status")
    public CommonResult<YianAiCapabilityRespVO> getCapabilities() {
        return success(yianAiIntegrationService.getCapabilities());
    }

    @PostMapping("/asset/device/parse-documents")
    @Operation(summary = "Upload and parse asset archive documents")
    public CommonResult<YianAssetDeviceDocumentParseRespVO> parseDeviceDocuments(
            @RequestParam(value = "machineryId", required = false) Long machineryId,
            @RequestParam("code") String code,
            @RequestParam("uploadedBy") String uploadedBy,
            @Parameter(description = "Archive files", required = true)
            @RequestParam("files") List<MultipartFile> files) throws Exception {
        return success(yianAiIntegrationService.parseDeviceDocuments(machineryId, code, uploadedBy, files));
    }

    @GetMapping("/asset/device/latest-documents")
    @Operation(summary = "Get latest persisted asset document parse result")
    public CommonResult<YianAssetDeviceDocumentParseRespVO> getLatestDeviceDocumentParseResult(
            @RequestParam(value = "machineryId", required = false) Long machineryId,
            @RequestParam(value = "code", required = false) String code) {
        return success(yianAiIntegrationService.getLatestDeviceDocumentParseResult(machineryId, code));
    }

    @PostMapping("/asset/device/reparse-documents")
    @Operation(summary = "Rebuild parse summary from archive metadata")
    public CommonResult<YianAssetDeviceDocumentParseRespVO> reparseDeviceDocuments(
            @Valid @RequestBody YianAssetDeviceReparseReqVO reqVO) {
        return success(yianAiIntegrationService.reparseDeviceDocuments(reqVO));
    }

    @PostMapping("/workorder/parse-flight-logs")
    @Operation(summary = "Upload and parse workorder flight logs")
    public CommonResult<YianWorkorderFlightLogParseRespVO> parseFlightLogs(
            @RequestParam(value = "workorderId", required = false) Long workorderId,
            @RequestParam("orderNo") String orderNo,
            @RequestParam("deviceCode") String deviceCode,
            @RequestParam("uploadedBy") String uploadedBy,
            @Parameter(description = "Flight log files", required = true)
            @RequestParam("files") List<MultipartFile> files) throws Exception {
        return success(yianAiIntegrationService.parseFlightLogs(workorderId, orderNo, deviceCode, uploadedBy, files));
    }

    @GetMapping("/workorder/latest-flight-log-parse")
    @Operation(summary = "Get latest persisted flight log parse result")
    public CommonResult<YianWorkorderFlightLogParseRespVO> getLatestFlightLogParseResult(
            @RequestParam(value = "workorderId", required = false) Long workorderId,
            @RequestParam(value = "orderNo", required = false) String orderNo) {
        return success(yianAiIntegrationService.getLatestFlightLogParseResult(workorderId, orderNo));
    }

    @PostMapping("/workorder/generate-diagnosis-draft")
    @Operation(summary = "Generate structured diagnosis draft")
    public CommonResult<YianWorkorderDiagnosisDraftRespVO> generateDiagnosisDraft(
            @Valid @RequestBody YianWorkorderDiagnosisDraftGenerateReqVO reqVO) {
        return success(yianAiIntegrationService.generateDiagnosisDraft(reqVO));
    }

    @GetMapping("/workorder/latest-diagnosis-draft")
    @Operation(summary = "Get latest persisted diagnosis draft")
    public CommonResult<YianWorkorderDiagnosisDraftRespVO> getLatestDiagnosisDraftResult(
            @RequestParam(value = "workorderId", required = false) Long workorderId,
            @RequestParam(value = "orderNo", required = false) String orderNo) {
        return success(yianAiIntegrationService.getLatestDiagnosisDraftResult(workorderId, orderNo));
    }
}
