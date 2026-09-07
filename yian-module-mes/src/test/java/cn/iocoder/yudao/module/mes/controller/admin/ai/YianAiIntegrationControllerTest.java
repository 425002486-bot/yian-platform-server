package cn.iocoder.yudao.module.mes.controller.admin.ai;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.test.core.ut.BaseMockitoUnitTest;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianAiCapabilityRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianAssetDeviceDocumentParseRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderDiagnosisDraftGenerateReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderDiagnosisDraftRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderFlightLogParseRespVO;
import cn.iocoder.yudao.module.mes.service.ai.YianAiIntegrationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class YianAiIntegrationControllerTest extends BaseMockitoUnitTest {

    @InjectMocks
    private YianAiIntegrationController controller;

    @Mock
    private YianAiIntegrationService yianAiIntegrationService;

    @Test
    public void testGetCapabilities() {
        YianAiCapabilityRespVO respVO = new YianAiCapabilityRespVO();
        respVO.setMockEnabled(true);
        respVO.setRecommendedMode("mock");
        when(yianAiIntegrationService.getCapabilities()).thenReturn(respVO);

        CommonResult<YianAiCapabilityRespVO> result = controller.getCapabilities();

        assertEquals(0, result.getCode());
        assertSame(respVO, result.getData());
        verify(yianAiIntegrationService).getCapabilities();
    }

    @Test
    public void testParseDeviceDocuments() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "certificate.pdf", "application/pdf",
                "deviceCode=UAV-001".getBytes(StandardCharsets.UTF_8));
        YianAssetDeviceDocumentParseRespVO respVO = new YianAssetDeviceDocumentParseRespVO();
        respVO.setMode("mock");
        respVO.setParseSummary("parsed");
        when(yianAiIntegrationService.parseDeviceDocuments(1L, "UAV-001", "tester", List.of(file)))
                .thenReturn(respVO);

        CommonResult<YianAssetDeviceDocumentParseRespVO> result = controller.parseDeviceDocuments(
                1L, "UAV-001", "tester", List.of(file));

        assertEquals(0, result.getCode());
        assertSame(respVO, result.getData());
        verify(yianAiIntegrationService).parseDeviceDocuments(1L, "UAV-001", "tester", List.of(file));
    }

    @Test
    public void testGetLatestDeviceDocumentParseResult() {
        YianAssetDeviceDocumentParseRespVO respVO = new YianAssetDeviceDocumentParseRespVO();
        respVO.setMode("model");
        when(yianAiIntegrationService.getLatestDeviceDocumentParseResult(1L, "UAV-001")).thenReturn(respVO);

        CommonResult<YianAssetDeviceDocumentParseRespVO> result =
                controller.getLatestDeviceDocumentParseResult(1L, "UAV-001");

        assertEquals(0, result.getCode());
        assertSame(respVO, result.getData());
        verify(yianAiIntegrationService).getLatestDeviceDocumentParseResult(1L, "UAV-001");
    }

    @Test
    public void testParseFlightLogs() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "mission.log", "text/plain",
                "drone_sn=UAV-001".getBytes(StandardCharsets.UTF_8));
        YianWorkorderFlightLogParseRespVO respVO = new YianWorkorderFlightLogParseRespVO();
        respVO.setMode("mock");
        respVO.setSummary("log parsed");
        when(yianAiIntegrationService.parseFlightLogs(2L, "WO-001", "UAV-001", "tester", List.of(file)))
                .thenReturn(respVO);

        CommonResult<YianWorkorderFlightLogParseRespVO> result = controller.parseFlightLogs(
                2L, "WO-001", "UAV-001", "tester", List.of(file));

        assertEquals(0, result.getCode());
        assertSame(respVO, result.getData());
        verify(yianAiIntegrationService).parseFlightLogs(2L, "WO-001", "UAV-001", "tester", List.of(file));
    }

    @Test
    public void testGetLatestFlightLogParseResult() {
        YianWorkorderFlightLogParseRespVO respVO = new YianWorkorderFlightLogParseRespVO();
        respVO.setMode("model");
        when(yianAiIntegrationService.getLatestFlightLogParseResult(2L, "WO-001")).thenReturn(respVO);

        CommonResult<YianWorkorderFlightLogParseRespVO> result =
                controller.getLatestFlightLogParseResult(2L, "WO-001");

        assertEquals(0, result.getCode());
        assertSame(respVO, result.getData());
        verify(yianAiIntegrationService).getLatestFlightLogParseResult(2L, "WO-001");
    }

    @Test
    public void testGenerateDiagnosisDraft() {
        YianWorkorderDiagnosisDraftGenerateReqVO reqVO = new YianWorkorderDiagnosisDraftGenerateReqVO();
        reqVO.setOrderNo("WO-001");
        YianWorkorderDiagnosisDraftRespVO respVO = new YianWorkorderDiagnosisDraftRespVO();
        respVO.setMode("mock");
        respVO.setFaultCategory("Flight Control System");
        when(yianAiIntegrationService.generateDiagnosisDraft(reqVO)).thenReturn(respVO);

        CommonResult<YianWorkorderDiagnosisDraftRespVO> result = controller.generateDiagnosisDraft(reqVO);

        assertEquals(0, result.getCode());
        assertSame(respVO, result.getData());
        verify(yianAiIntegrationService).generateDiagnosisDraft(reqVO);
    }

    @Test
    public void testGetLatestDiagnosisDraftResult() {
        YianWorkorderDiagnosisDraftRespVO respVO = new YianWorkorderDiagnosisDraftRespVO();
        respVO.setMode("model");
        when(yianAiIntegrationService.getLatestDiagnosisDraftResult(2L, "WO-001")).thenReturn(respVO);

        CommonResult<YianWorkorderDiagnosisDraftRespVO> result =
                controller.getLatestDiagnosisDraftResult(2L, "WO-001");

        assertEquals(0, result.getCode());
        assertSame(respVO, result.getData());
        verify(yianAiIntegrationService).getLatestDiagnosisDraftResult(2L, "WO-001");
    }
}
