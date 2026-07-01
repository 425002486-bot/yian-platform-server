package cn.iocoder.yudao.module.mes.service.ai;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.ai.service.model.AiModelService;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import cn.iocoder.yudao.module.mes.config.ai.YianAiProperties;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianAssetDeviceDocumentParseRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderDiagnosisDraftGenerateReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderDiagnosisDraftRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderFlightLogParseRespVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiAssetParseResultDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiDiagnosisDraftDO;
import cn.iocoder.yudao.module.mes.dal.mysql.ai.YianAiAssetParseResultMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.ai.YianAiDiagnosisDraftMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.ai.YianAiFlightLogParseResultMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.ai.YianAiTaskLogMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@Import({YianAiIntegrationServiceImpl.class, YianAiProperties.class, ObjectMapper.class})
public class YianAiIntegrationServiceImplTest extends BaseDbUnitTest {

    @Resource
    private YianAiIntegrationServiceImpl service;

    @Resource
    private YianAiProperties properties;

    @Resource
    private ObjectMapper objectMapper;

    @MockitoBean
    private AiModelService aiModelService;

    @MockitoBean
    private FileService fileService;

    @MockitoBean
    private YianAiTaskLogMapper taskLogMapper;

    @MockitoBean
    private YianAiAssetParseResultMapper assetParseResultMapper;

    @MockitoBean
    private YianAiFlightLogParseResultMapper flightLogParseResultMapper;

    @MockitoBean
    private YianAiDiagnosisDraftMapper diagnosisDraftMapper;

    @Test
    public void testParseDeviceDocuments_mock() throws Exception {
        properties.setMockEnabled(true);
        when(fileService.createFile(any(), any(), any(), any())).thenReturn("http://mock/doc");
        MockMultipartFile file = new MockMultipartFile("files", "first-flight-checklist.pdf", "application/pdf",
                "deviceCode=UAV-MVP-001".getBytes(StandardCharsets.UTF_8));

        YianAssetDeviceDocumentParseRespVO respVO = service.parseDeviceDocuments(1L, "UAV-MVP-001",
                "tester", List.of(file));

        assertEquals("mock", respVO.getMode());
        assertFalse(respVO.getDocuments().isEmpty());
        assertTrue(respVO.getParseSummary().contains("1"));
        assertEquals("首飞检查单", respVO.getDocuments().get(0).getDocumentType());
        verify(assetParseResultMapper).insert(any(YianAiAssetParseResultDO.class));
        verify(taskLogMapper).insert(any(cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiTaskLogDO.class));
        verifyNoInteractions(aiModelService);
    }

    @Test
    public void testParseFlightLogs_mock() throws Exception {
        properties.setMockEnabled(true);
        when(fileService.createFile(any(), any(), any(), any())).thenReturn("http://mock/log");
        MockMultipartFile file = new MockMultipartFile("files", "mission-flight.log", "text/plain",
                "drone_sn=UAV-MVP-001 battery_sn=BAT-001 duration=600".getBytes(StandardCharsets.UTF_8));

        YianWorkorderFlightLogParseRespVO respVO = service.parseFlightLogs(11L, "WO-001",
                "UAV-MVP-001", "tester", List.of(file));

        assertEquals("mock", respVO.getMode());
        assertEquals("UAV-MVP-001", respVO.getDroneSn());
        assertEquals(600, respVO.getFlightDurationSec());
        assertTrue(respVO.getBatterySns().contains("BAT-001"));
        assertTrue(respVO.getSummary().contains("已解析"));
        verify(flightLogParseResultMapper).insert(any(cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiFlightLogParseResultDO.class));
        verify(taskLogMapper).insert(any(cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiTaskLogDO.class));
        verifyNoInteractions(aiModelService);
    }

    @Test
    public void testGenerateDiagnosisDraft_mock() {
        properties.setMockEnabled(true);
        YianWorkorderDiagnosisDraftGenerateReqVO reqVO = new YianWorkorderDiagnosisDraftGenerateReqVO();
        reqVO.setOrderNo("WO-001");
        reqVO.setDeviceCode("UAV-MVP-001");
        reqVO.setSymptom("Battery temperature warning triggered during the mission");
        reqVO.setFlightLogSummary("Detected battery_sn=BAT-001 and high temperature in the log");

        YianWorkorderDiagnosisDraftRespVO respVO = service.generateDiagnosisDraft(reqVO);

        assertEquals("mock", respVO.getMode());
        assertEquals("动力系统 / 电池", respVO.getFaultCategory());
        assertTrue(respVO.getGroundedSuggestion());
        assertTrue(respVO.getNeedParts());
        verify(diagnosisDraftMapper).insert(any(YianAiDiagnosisDraftDO.class));
        verify(taskLogMapper).insert(any(cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiTaskLogDO.class));
        verifyNoInteractions(aiModelService);
    }

    @Test
    public void testGenerateDiagnosisDraft_fallbackWhenRealModelUnavailable() {
        properties.setMockEnabled(false);
        doThrow(new RuntimeException("model unavailable")).when(aiModelService).getRequiredDefaultModel(anyInt());
        YianWorkorderDiagnosisDraftGenerateReqVO reqVO = new YianWorkorderDiagnosisDraftGenerateReqVO();
        reqVO.setOrderNo("WO-002");
        reqVO.setDeviceCode("UAV-MVP-002");
        reqVO.setSymptom("Signal quality is unstable");

        YianWorkorderDiagnosisDraftRespVO respVO = service.generateDiagnosisDraft(reqVO);

        assertEquals("fallback", respVO.getMode());
        assertTrue(respVO.getRawResponse().contains("model unavailable"));
        verify(diagnosisDraftMapper).insert(any(YianAiDiagnosisDraftDO.class));
        verify(taskLogMapper).insert(any(cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiTaskLogDO.class));
    }

    @Test
    public void testGetLatestDeviceDocumentParseResult() throws Exception {
        YianAssetDeviceDocumentParseRespVO stored = new YianAssetDeviceDocumentParseRespVO();
        stored.setMode("model");
        stored.setParseSummary("Parsed drone asset documentation containing certificate metadata.");
        stored.setParseSource("Source: persisted");
        YianAssetDeviceDocumentParseRespVO.DocumentItem item = new YianAssetDeviceDocumentParseRespVO.DocumentItem();
        item.setDocumentType("Certificate");
        item.setParseResult("Device certificate for UAV-MVP-001.");
        stored.setDocuments(List.of(item));
        YianAiAssetParseResultDO latest = YianAiAssetParseResultDO.builder()
                .id(10L)
                .resultJson(objectMapper.writeValueAsString(stored))
                .build();
        latest.setCreateTime(LocalDateTime.of(2026, 7, 1, 12, 0));
        when(assetParseResultMapper.selectLatest(1L, "UAV-MVP-001")).thenReturn(latest);

        YianAssetDeviceDocumentParseRespVO result = service.getLatestDeviceDocumentParseResult(1L, "UAV-MVP-001");

        assertNotNull(result);
        assertEquals("model", result.getMode());
        assertTrue(result.getParseSummary().contains("已完成建档资料解析"));
        assertEquals("合格证", result.getDocuments().get(0).getDocumentType());
        assertEquals("来源：persisted", result.getParseSource());
        assertEquals("2026-07-01 12:00:00", result.getParseUpdatedAt());
    }

    @Test
    public void testGetLatestDiagnosisDraftResult() throws Exception {
        YianWorkorderDiagnosisDraftRespVO stored = new YianWorkorderDiagnosisDraftRespVO();
        stored.setMode("model");
        stored.setFaultCategory("Battery Thermal Management");
        stored.setRiskLevel("medium");
        stored.setSuggestedParts(List.of("Spare battery"));
        stored.setSuggestedPartsText("Replace both batteries (BAT-001 and BAT-002) after diagnostic verification.");
        YianAiDiagnosisDraftDO latest = YianAiDiagnosisDraftDO.builder()
                .id(20L)
                .resultJson(objectMapper.writeValueAsString(stored))
                .build();
        when(diagnosisDraftMapper.selectLatest(2L, "WO-001")).thenReturn(latest);

        YianWorkorderDiagnosisDraftRespVO result = service.getLatestDiagnosisDraftResult(2L, "WO-001");

        assertNotNull(result);
        assertEquals("model", result.getMode());
        assertEquals("电池热管理异常", result.getFaultCategory());
        assertEquals("备用电池", result.getSuggestedParts().get(0));
        assertEquals("建议在完成诊断确认后更换 BAT-001 和 BAT-002。", result.getSuggestedPartsText());
    }
    @Test
    public void testGetLatestDiagnosisDraftResult_localizesLegacyEnglishGimbalDraft() throws Exception {
        YianWorkorderDiagnosisDraftRespVO stored = new YianWorkorderDiagnosisDraftRespVO();
        stored.setMode("model");
        stored.setFaultCategory("Gimbal Stability Issue");
        stored.setProbableCause("Gimbal motor calibration drift or IMU misalignment; possible firmware inconsistency between gimbal controller and flight controller.");
        stored.setRiskLevel("medium");
        stored.setSuggestedParts(List.of("Flight-control diagnostic toolkit", "Attitude sensor assembly"));
        stored.setSuggestedPartsText("Flight-control diagnostic toolkit, Attitude sensor assembly");
        stored.setConclusion("Gimbal instability detected during stability check mission; no hardware failure evident from logs or images, but requires recalibration and firmware validation before flight.");
        stored.setSummary("Flight log confirms gimbal stability check executed for 642 seconds using batteries BAT-UI-201 and BAT-UI-202. No visual anomalies in image summary and no attachments show mechanical damage. Likely software/firmware or calibration-related — not a critical hardware fault, but unsafe to fly until verified.");
        YianAiDiagnosisDraftDO latest = YianAiDiagnosisDraftDO.builder()
                .id(21L)
                .resultJson(objectMapper.writeValueAsString(stored))
                .build();
        when(diagnosisDraftMapper.selectLatest(102L, "WO-20260513-003")).thenReturn(latest);

        YianWorkorderDiagnosisDraftRespVO result = service.getLatestDiagnosisDraftResult(102L, "WO-20260513-003");

        assertNotNull(result);
        assertEquals("云台稳定性异常", result.getFaultCategory());
        assertEquals("疑似云台电机校准漂移或 IMU 未对齐，也不排除云台控制器与飞控之间存在固件版本不一致。", result.getProbableCause());
        assertEquals("飞控诊断工具", result.getSuggestedParts().get(0));
        assertEquals("姿态传感器组件", result.getSuggestedParts().get(1));
        assertEquals("飞控诊断工具, 姿态传感器组件", result.getSuggestedPartsText());
        assertEquals("在稳定性检查任务中检测到云台异常抖动；日志和图像暂未发现明确的硬件损坏，但起飞前仍需完成重新校准和固件一致性核验。", result.getConclusion());
        assertTrue(result.getSummary().contains("飞行日志显示本次任务使用 BAT-UI-201 和 BAT-UI-202"));
    }
}
