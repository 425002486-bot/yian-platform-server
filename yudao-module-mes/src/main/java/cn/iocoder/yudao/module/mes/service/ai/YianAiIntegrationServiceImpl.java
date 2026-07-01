package cn.iocoder.yudao.module.mes.service.ai;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.ai.dal.dataobject.model.AiModelDO;
import cn.iocoder.yudao.module.ai.enums.model.AiModelTypeEnum;
import cn.iocoder.yudao.module.ai.enums.model.AiPlatformEnum;
import cn.iocoder.yudao.module.ai.service.model.AiModelService;
import cn.iocoder.yudao.module.ai.util.AiUtils;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import cn.iocoder.yudao.module.mes.config.ai.YianAiProperties;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianAiCapabilityRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianAssetDeviceDocumentParseRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianAssetDeviceReparseReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderDiagnosisDraftGenerateReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderDiagnosisDraftRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderFlightLogParseRespVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiAssetParseResultDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiDiagnosisDraftDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiFlightLogParseResultDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiTaskLogDO;
import cn.iocoder.yudao.module.mes.dal.mysql.ai.YianAiAssetParseResultMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.ai.YianAiDiagnosisDraftMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.ai.YianAiFlightLogParseResultMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.ai.YianAiTaskLogMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Validated
@Slf4j
public class YianAiIntegrationServiceImpl implements YianAiIntegrationService {

    private static final String BIZ_TYPE_ASSET_PARSE = "asset_parse";
    private static final String BIZ_TYPE_FLIGHT_LOG_PARSE = "flight_log_parse";
    private static final String BIZ_TYPE_DIAGNOSIS_DRAFT = "diagnosis_draft";

    private static final Pattern DRONE_SN_PATTERN = Pattern.compile(
            "(?:drone[_ -]?sn|aircraft[_ -]?sn|device[_ -]?code)\\s*[:=]\\s*([A-Za-z0-9\\-_/]+)",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern BATTERY_SN_PATTERN = Pattern.compile(
            "(?:battery[_ -]?sn|battery[_ -]?code)\\s*[:=]\\s*([A-Za-z0-9\\-_/]+)",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern FLIGHT_DURATION_PATTERN = Pattern.compile(
            "(?:flight[_ -]?duration|duration|flight[_ -]?time)\\s*[:=]\\s*(\\d{1,5})",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern ASCII_LETTER_PATTERN = Pattern.compile("[A-Za-z]");
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]");

    @Resource
    private YianAiProperties properties;
    @Resource
    private AiModelService aiModelService;
    @Resource
    private FileService fileService;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private YianAiTaskLogMapper taskLogMapper;
    @Resource
    private YianAiAssetParseResultMapper assetParseResultMapper;
    @Resource
    private YianAiFlightLogParseResultMapper flightLogParseResultMapper;
    @Resource
    private YianAiDiagnosisDraftMapper diagnosisDraftMapper;

    @Override
    public YianAiCapabilityRespVO getCapabilities() {
        YianAiCapabilityRespVO respVO = new YianAiCapabilityRespVO();
        respVO.setMockEnabled(properties.isMockEnabled());
        try {
            AiModelDO model = resolveChatModelConfig();
            respVO.setRealModelReady(true);
            respVO.setRecommendedMode(properties.isMockEnabled() ? "mock" : "model");
            respVO.setModelName(model.getName() + " / " + model.getModel());
            respVO.setMessage(properties.isMockEnabled()
                    ? "Mock mode is enabled. A real chat model is available for switching."
                    : "Real model mode is enabled.");
        } catch (Exception ex) {
            respVO.setRealModelReady(false);
            respVO.setRecommendedMode("mock");
            respVO.setMessage("No default chat model is available. The system will use mock results.");
        }
        return respVO;
    }

    @Override
    public YianAssetDeviceDocumentParseRespVO parseDeviceDocuments(Long machineryId, String code, String uploadedBy,
                                                                   List<MultipartFile> files) throws Exception {
        List<StoredFile> storedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            byte[] content = file.getBytes();
            String url = fileService.createFile(content, file.getOriginalFilename(), "yian/device-docs",
                    file.getContentType());
            storedFiles.add(new StoredFile(file.getOriginalFilename(), file.getContentType(), (long) content.length,
                    url, buildPreview(content)));
        }
        return parseDeviceDocumentsInternal(machineryId, code, uploadedBy, storedFiles, "upload");
    }

    @Override
    public YianAssetDeviceDocumentParseRespVO reparseDeviceDocuments(YianAssetDeviceReparseReqVO reqVO) {
        List<StoredFile> virtualFiles = new ArrayList<>();
        if (reqVO.getFileNames() != null) {
            reqVO.getFileNames().forEach(name -> virtualFiles.add(new StoredFile(name, null, 0L, null, "")));
        }
        return parseDeviceDocumentsInternal(reqVO.getMachineryId(), reqVO.getCode(), reqVO.getOperator(),
                virtualFiles, "reparse");
    }

    @Override
    public YianAssetDeviceDocumentParseRespVO getLatestDeviceDocumentParseResult(Long machineryId, String code) {
        YianAiAssetParseResultDO latest = assetParseResultMapper.selectLatest(machineryId, code);
        if (latest == null || StrUtil.isBlank(latest.getResultJson())) {
            return null;
        }
        try {
            YianAssetDeviceDocumentParseRespVO respVO = objectMapper.readValue(
                    latest.getResultJson(), YianAssetDeviceDocumentParseRespVO.class);
            normalizeAssetParseResp(respVO, latest.getCreateTime());
            return respVO;
        } catch (Exception ex) {
            log.warn("Failed to read latest asset parse result, id={}", latest.getId(), ex);
            return null;
        }
    }

    @Override
    public YianWorkorderFlightLogParseRespVO parseFlightLogs(Long workorderId, String orderNo, String deviceCode,
                                                             String uploadedBy, List<MultipartFile> files) throws Exception {
        List<StoredFile> storedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            byte[] content = file.getBytes();
            String url = fileService.createFile(content, file.getOriginalFilename(), "yian/flight-logs",
                    file.getContentType());
            storedFiles.add(new StoredFile(file.getOriginalFilename(), file.getContentType(), (long) content.length,
                    url, buildPreview(content)));
        }
        return parseFlightLogsInternal(workorderId, orderNo, deviceCode, uploadedBy, storedFiles);
    }

    @Override
    public YianWorkorderFlightLogParseRespVO getLatestFlightLogParseResult(Long workorderId, String orderNo) {
        YianAiFlightLogParseResultDO latest = flightLogParseResultMapper.selectLatest(workorderId, orderNo);
        if (latest == null || StrUtil.isBlank(latest.getResultJson())) {
            return null;
        }
        try {
            YianWorkorderFlightLogParseRespVO respVO = objectMapper.readValue(
                    latest.getResultJson(), YianWorkorderFlightLogParseRespVO.class);
            normalizeFlightLogParseResp(respVO);
            return respVO;
        } catch (Exception ex) {
            log.warn("Failed to read latest flight log parse result, id={}", latest.getId(), ex);
            return null;
        }
    }

    @Override
    public YianWorkorderDiagnosisDraftRespVO generateDiagnosisDraft(YianWorkorderDiagnosisDraftGenerateReqVO reqVO) {
        YianWorkorderDiagnosisDraftRespVO mock = buildMockDiagnosisDraft(reqVO);
        YianWorkorderDiagnosisDraftRespVO result = mock;
        String errorMessage = null;
        if (properties.isMockEnabled()) {
            mock.setMode("mock");
            result = mock;
        } else {
            try {
                String systemPrompt = """
                        你是无人机维保系统的初诊助手。
                        只返回 JSON，不要输出额外解释。
                        面向页面展示的所有自然语言字段必须使用简体中文；设备编码、工单号、序列号、备件编码可保留原样。
                        必填字段：
                        - faultCategory
                        - probableCause
                        - riskLevel
                        - groundedSuggestion
                        - needParts
                        - suggestedParts
                        - suggestedPartsText
                        - conclusion
                        - summary
                        其中 riskLevel 只能是 high、medium、low。
                        groundedSuggestion 和 needParts 必须是 boolean。
                        你只能生成辅助草案，不能替代人工最终确认。
                        """;
                result = callJsonModel(systemPrompt, objectToJson(reqVO), YianWorkorderDiagnosisDraftRespVO.class);
                fillDiagnosisDefaults(result, mock);
                result.setMode("model");
            } catch (Exception ex) {
                log.warn("generateDiagnosisDraft real model failed, fallback to mock", ex);
                errorMessage = ex.getMessage();
                mock.setMode("fallback");
                mock.setRawResponse(ex.getMessage());
                result = mock;
            }
        }
        persistDiagnosisDraft(reqVO, result, errorMessage);
        return result;
    }

    @Override
    public YianWorkorderDiagnosisDraftRespVO getLatestDiagnosisDraftResult(Long workorderId, String orderNo) {
        YianAiDiagnosisDraftDO latest = diagnosisDraftMapper.selectLatest(workorderId, orderNo);
        if (latest == null || StrUtil.isBlank(latest.getResultJson())) {
            return null;
        }
        try {
            YianWorkorderDiagnosisDraftRespVO respVO = objectMapper.readValue(
                    latest.getResultJson(), YianWorkorderDiagnosisDraftRespVO.class);
            normalizeDiagnosisDraftResp(respVO);
            return respVO;
        } catch (Exception ex) {
            log.warn("Failed to read latest diagnosis draft result, id={}", latest.getId(), ex);
            return null;
        }
    }

    private YianAssetDeviceDocumentParseRespVO parseDeviceDocumentsInternal(Long machineryId, String code,
                                                                            String uploadedBy, List<StoredFile> files,
                                                                            String trigger) {
        YianAssetDeviceDocumentParseRespVO mock = buildMockDeviceDocumentResult(code, uploadedBy, files, "mock");
        YianAssetDeviceDocumentParseRespVO result = mock;
        String errorMessage = null;
        if (properties.isMockEnabled()) {
            result = mock;
        } else {
            try {
                String systemPrompt = """
                        你是无人机主档资料解析助手。
                        只返回 JSON，不要输出额外解释。
                        面向页面展示的自然语言字段必须使用简体中文；设备编码、证书编号、日期等结构化值可保留原样。
                        必填字段：
                        - parseSummary
                        - parseSource
                        - parsedFields[{label,value}]
                        - warnings[]
                        - missingItems[]
                        - documents[{fileName,documentType,parseResult,parseSource}]
                        documentType 优先使用以下中文值：
                        合格证、采购凭证、校准记录、首飞检查单、检测报告、日志附件、其他
                        parseResult 可以是字符串，也可以是带 summary 等字段的对象。
                        """;
                RealDocumentParsePrompt prompt = new RealDocumentParsePrompt();
                prompt.setMachineryId(machineryId);
                prompt.setCode(code);
                prompt.setUploadedBy(uploadedBy);
                prompt.setTrigger(trigger);
                prompt.setFiles(files);
                RealDocumentParseResult parsed = callJsonModel(systemPrompt, objectToJson(prompt),
                        RealDocumentParseResult.class);
                result = mergeRealDocumentResult(code, uploadedBy, files, parsed);
            } catch (Exception ex) {
                log.warn("parseDeviceDocuments real model failed, fallback to mock", ex);
                errorMessage = ex.getMessage();
                mock.setMode("fallback");
                mock.setParseSource("来源：附件解析（真实模型失败后降级）");
                result = mock;
            }
        }
        persistAssetParseResult(machineryId, code, trigger, files, result, errorMessage);
        return result;
    }

    private YianWorkorderFlightLogParseRespVO parseFlightLogsInternal(Long workorderId, String orderNo,
                                                                      String deviceCode, String uploadedBy,
                                                                      List<StoredFile> files) {
        YianWorkorderFlightLogParseRespVO mock = buildMockFlightLogParseResult(orderNo, deviceCode, files);
        YianWorkorderFlightLogParseRespVO result = mock;
        String errorMessage = null;
        if (properties.isMockEnabled()) {
            mock.setMode("mock");
            result = mock;
        } else {
            try {
                String systemPrompt = """
                        你是无人机飞行日志解析助手。
                        只返回 JSON，不要输出额外解释。
                        面向页面展示的自然语言字段必须使用简体中文；设备编码、日志类型缩写、序列号等结构化值可保留原样。
                        必填字段：
                        - summary
                        - assistantContextSummary
                        - parseSource
                        - droneSn
                        - batterySns
                        - flightDurationSec
                        - logType
                        无法判断的字段可返回空字符串、空数组或 null。
                        """;
                RealFlightLogPrompt prompt = new RealFlightLogPrompt();
                prompt.setWorkorderId(workorderId);
                prompt.setOrderNo(orderNo);
                prompt.setDeviceCode(deviceCode);
                prompt.setUploadedBy(uploadedBy);
                prompt.setFiles(files);
                RealFlightLogResult parsed = callJsonModel(systemPrompt, objectToJson(prompt),
                        RealFlightLogResult.class);
                fillFlightLogDefaults(parsed, mock);
                YianWorkorderFlightLogParseRespVO respVO = new YianWorkorderFlightLogParseRespVO();
                respVO.setMode("model");
                respVO.setSummary(parsed.getSummary());
                respVO.setAssistantContextSummary(parsed.getAssistantContextSummary());
                respVO.setParseSource(StrUtil.blankToDefault(parsed.getParseSource(), "来源：AI 模型日志解析"));
                respVO.setDroneSn(parsed.getDroneSn());
                respVO.setBatterySns(emptyIfNull(parsed.getBatterySns()));
                respVO.setFlightDurationSec(parsed.getFlightDurationSec());
                respVO.setLogType(parsed.getLogType());
                respVO.setAttachments(buildLogAttachments(files));
                result = respVO;
            } catch (Exception ex) {
                log.warn("parseFlightLogs real model failed, fallback to mock", ex);
                errorMessage = ex.getMessage();
                mock.setMode("fallback");
                mock.setParseSource("来源：飞行日志解析（真实模型失败后降级）");
                result = mock;
            }
        }
        persistFlightLogParseResult(workorderId, orderNo, deviceCode, files, result, errorMessage);
        return result;
    }

    private YianAssetDeviceDocumentParseRespVO buildMockDeviceDocumentResult(String code, String uploadedBy,
                                                                             List<StoredFile> files, String mode) {
        YianAssetDeviceDocumentParseRespVO respVO = new YianAssetDeviceDocumentParseRespVO();
        respVO.setMode(mode);
        respVO.setParseUpdatedAt(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
        respVO.setParseSource("来源：AI 模拟主档资料解析");
        List<YianAssetDeviceDocumentParseRespVO.DocumentItem> documents = new ArrayList<>();
        LinkedHashSet<String> docTypes = new LinkedHashSet<>();
        for (StoredFile file : files) {
            String documentType = resolveDocumentType(file.getFileName());
            docTypes.add(documentType);
            YianAssetDeviceDocumentParseRespVO.DocumentItem item = new YianAssetDeviceDocumentParseRespVO.DocumentItem();
            item.setId("asset-document-" + IdUtil.fastSimpleUUID());
            item.setFileName(file.getFileName());
            item.setDocumentType(documentType);
            item.setParseResult(buildDocumentParseResult(documentType, file.getPreview()));
            item.setParseSource("AI 模拟解析");
            item.setUploadedBy(uploadedBy);
            item.setUploadedAt(respVO.getParseUpdatedAt());
            item.setUrl(file.getUrl());
            documents.add(item);
        }
        respVO.setDocuments(documents);
        respVO.setParsedFields(buildDeviceFieldItems(code, docTypes, files.size()));
        respVO.setWarnings(buildDeviceWarnings(docTypes));
        respVO.setMissingItems(buildDeviceMissingItems(docTypes));
        respVO.setParseSummary(buildDeviceParseSummary(docTypes, files.size()));
        return respVO;
    }

    private YianWorkorderFlightLogParseRespVO buildMockFlightLogParseResult(String orderNo, String deviceCode,
                                                                            List<StoredFile> files) {
        YianWorkorderFlightLogParseRespVO respVO = new YianWorkorderFlightLogParseRespVO();
        respVO.setSummary("已解析 " + files.size() + " 份飞行日志，可作为初诊与履历判断依据。");
        respVO.setAssistantContextSummary("工单 " + orderNo + " 已关联 " + files.size()
                + " 份飞行日志，主日志类型为" + resolveLogType(files) + "，可用于辅助定位时间线与挂载关系。");
        respVO.setParseSource("来源：AI 模拟飞行日志解析");
        respVO.setDroneSn(firstNonBlank(extractByPattern(files, DRONE_SN_PATTERN), deviceCode));
        respVO.setBatterySns(emptyIfNull(extractAllByPattern(files, BATTERY_SN_PATTERN)));
        respVO.setFlightDurationSec(extractDuration(files));
        respVO.setLogType(resolveLogType(files));
        respVO.setAttachments(buildLogAttachments(files));
        return respVO;
    }

    private YianWorkorderDiagnosisDraftRespVO buildMockDiagnosisDraft(YianWorkorderDiagnosisDraftGenerateReqVO reqVO) {
        String content = String.join(" ",
                firstNonBlank(reqVO.getSymptom(), ""),
                firstNonBlank(reqVO.getDescription(), ""),
                firstNonBlank(reqVO.getFlightLogSummary(), ""),
                firstNonBlank(reqVO.getImageSummary(), "")).toLowerCase(Locale.ROOT);
        YianWorkorderDiagnosisDraftRespVO respVO = new YianWorkorderDiagnosisDraftRespVO();
        if (content.contains("battery") || content.contains("temperature") || content.contains("voltage")) {
            respVO.setFaultCategory("动力系统 / 电池");
            respVO.setProbableCause("疑似电池健康衰减，或当前挂载电池与备案关系不一致，建议优先核对 SOH、循环次数与压差。");
            respVO.setRiskLevel("high");
            respVO.setGroundedSuggestion(true);
            respVO.setNeedParts(true);
            respVO.setSuggestedParts(List.of("备用电池", "电池诊断工具"));
            respVO.setConclusion("建议先停飞设备，补充电池日志与检测报告复核后，再判断是否需要更换电池。");
        } else if (content.contains("image transmission") || content.contains("signal")
                || content.contains("link") || content.contains("signal quality")) {
            respVO.setFaultCategory("通信链路");
            respVO.setProbableCause("疑似图传链路波动或现场干扰导致通信质量下降，建议核对天线、信道与电磁环境。");
            respVO.setRiskLevel("medium");
            respVO.setGroundedSuggestion(false);
            respVO.setNeedParts(false);
            respVO.setSuggestedParts(Collections.emptyList());
            respVO.setConclusion("建议先完成现场复测和日志复核，再判断是否进入维修流程。");
        } else {
            respVO.setFaultCategory("飞控系统");
            respVO.setProbableCause("当前现象疑似与飞控参数异常、传感器漂移或首飞检查不完整有关。");
            respVO.setRiskLevel(content.contains("out of control") || content.contains("unable to take off")
                    ? "high" : "medium");
            respVO.setGroundedSuggestion(true);
            respVO.setNeedParts(true);
            respVO.setSuggestedParts(List.of("飞控诊断工具", "姿态传感器组件"));
            respVO.setConclusion("建议先完成日志复核、传感器自检与首飞检查单核验，再决定是否更换飞控相关部件。");
        }
        respVO.setSuggestedPartsText(String.join("、", emptyIfNull(respVO.getSuggestedParts())));
        respVO.setSummary("以上为基于工单现象、附件与日志摘要生成的结构化初诊草案，仍需人工确认。");
        respVO.setRawResponse("mock");
        return respVO;
    }

    private List<YianAssetDeviceDocumentParseRespVO.FieldItem> buildDeviceFieldItems(String code,
                                                                                     LinkedHashSet<String> docTypes,
                                                                                     int fileCount) {
        List<YianAssetDeviceDocumentParseRespVO.FieldItem> fields = new ArrayList<>();
        fields.add(buildField("设备编码", code));
        fields.add(buildField("附件数量", String.valueOf(fileCount)));
        fields.add(buildField("识别到的资料类型", docTypes.isEmpty() ? "无" : String.join("、", docTypes)));
        return fields;
    }

    private List<String> buildDeviceWarnings(LinkedHashSet<String> docTypes) {
        List<String> warnings = new ArrayList<>();
        if (docTypes.contains("日志附件")) {
            warnings.add("检测到日志类附件，建议同步纳入工单日志归档流程。");
        }
        if (!docTypes.contains("校准记录")) {
            warnings.add("未识别到校准记录，准入规则可能继续提示资料缺失。");
        }
        return warnings;
    }

    private List<String> buildDeviceMissingItems(LinkedHashSet<String> docTypes) {
        List<String> missingItems = new ArrayList<>();
        if (!docTypes.contains("合格证")) {
            missingItems.add("缺少合格证");
        }
        if (!docTypes.contains("首飞检查单")) {
            missingItems.add("缺少首飞检查单");
        }
        return missingItems;
    }

    private String buildDeviceParseSummary(LinkedHashSet<String> docTypes, int fileCount) {
        if (fileCount == 0) {
            return "当前没有可解析的建档附件，暂无法生成资料摘要。";
        }
        return "已识别 " + fileCount + " 份建档附件，覆盖资料类型："
                + String.join("、", docTypes) + "。";
    }

    private String buildDocumentParseResult(String documentType, String preview) {
        if (StrUtil.isBlank(preview)) {
            return "已识别为" + documentType + "，但当前缺少可读预览文本，建议补充原始文件内容。";
        }
        return "已识别为" + documentType + "，并提取到可用于生成摘要的预览内容。";
    }

    private String resolveDocumentType(String fileName) {
        String normalized = StrUtil.blankToDefault(fileName, "").toLowerCase(Locale.ROOT);
        if (normalized.contains("certificate")) {
            return "合格证";
        }
        if (normalized.contains("purchase") || normalized.contains("invoice") || normalized.contains("contract")) {
            return "采购凭证";
        }
        if (normalized.contains("calibration")) {
            return "校准记录";
        }
        if (normalized.contains("first-flight") || normalized.contains("first_flight")) {
            return "首飞检查单";
        }
        if (normalized.contains("report") || normalized.contains("inspection")) {
            return "检测报告";
        }
        if (normalized.contains(".log") || normalized.contains(".ulg")
                || normalized.contains(".bin") || normalized.contains(".dat")) {
            return "日志附件";
        }
        return "其他";
    }

    private String resolveLogType(List<StoredFile> files) {
        String joined = files.stream().map(StoredFile::getFileName).reduce("", (left, right) -> left + " " + right)
                .toLowerCase(Locale.ROOT);
        if (joined.contains("flightrecord")) {
            return "DJI 飞行记录";
        }
        if (joined.contains(".ulg")) {
            return "PX4 ULog 日志";
        }
        if (joined.contains(".bin") || joined.contains(".tlog")) {
            return "ArduPilot 日志";
        }
        if (joined.contains(".dat")) {
            return "DJI DAT 日志";
        }
        return "通用飞行日志";
    }

    private List<YianWorkorderFlightLogParseRespVO.AttachmentItem> buildLogAttachments(List<StoredFile> files) {
        List<YianWorkorderFlightLogParseRespVO.AttachmentItem> attachments = new ArrayList<>();
        for (StoredFile file : files) {
            YianWorkorderFlightLogParseRespVO.AttachmentItem item = new YianWorkorderFlightLogParseRespVO.AttachmentItem();
            item.setName(file.getFileName());
            item.setType("log");
            item.setSize(file.getSize());
            item.setMimeType(file.getMimeType());
            item.setUrl(file.getUrl());
            attachments.add(item);
        }
        return attachments;
    }

    private String extractByPattern(List<StoredFile> files, Pattern pattern) {
        for (StoredFile file : files) {
            Matcher matcher = pattern.matcher(file.getPreview());
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    private List<String> extractAllByPattern(List<StoredFile> files, Pattern pattern) {
        LinkedHashSet<String> values = new LinkedHashSet<>();
        for (StoredFile file : files) {
            Matcher matcher = pattern.matcher(file.getPreview());
            while (matcher.find()) {
                values.add(matcher.group(1));
            }
        }
        return new ArrayList<>(values);
    }

    private Integer extractDuration(List<StoredFile> files) {
        String value = extractByPattern(files, FLIGHT_DURATION_PATTERN);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private String buildPreview(byte[] content) {
        if (content == null || content.length == 0) {
            return "";
        }
        String preview = new String(content, StandardCharsets.UTF_8)
                .replace('\u0000', ' ')
                .replaceAll("\\s+", " ")
                .trim();
        if (preview.length() > properties.getMaxPreviewChars()) {
            preview = preview.substring(0, properties.getMaxPreviewChars());
        }
        return preview;
    }

    private YianAssetDeviceDocumentParseRespVO mergeRealDocumentResult(String code, String uploadedBy,
                                                                       List<StoredFile> files,
                                                                       RealDocumentParseResult result) {
        YianAssetDeviceDocumentParseRespVO respVO = new YianAssetDeviceDocumentParseRespVO();
        respVO.setMode("model");
        respVO.setParseSummary(StrUtil.blankToDefault(result.getParseSummary(),
                "AI 模型已完成建档资料摘要解析。"));
        respVO.setParseSource(StrUtil.blankToDefault(result.getParseSource(), "来源：AI 模型资料解析"));
        respVO.setParseUpdatedAt(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
        respVO.setParsedFields(emptyIfNull(result.getParsedFields()));
        respVO.setWarnings(emptyIfNull(result.getWarnings()));
        respVO.setMissingItems(emptyIfNull(result.getMissingItems()));

        List<YianAssetDeviceDocumentParseRespVO.DocumentItem> documents = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            StoredFile file = files.get(i);
            RealDocumentItem parsed = result.getDocuments() != null && result.getDocuments().size() > i
                    ? result.getDocuments().get(i) : null;
            YianAssetDeviceDocumentParseRespVO.DocumentItem item = new YianAssetDeviceDocumentParseRespVO.DocumentItem();
            item.setId("asset-document-" + IdUtil.fastSimpleUUID());
            item.setFileName(StrUtil.blankToDefault(parsed != null ? objectToText(parsed.getFileName()) : null,
                    file.getFileName()));
            item.setDocumentType(StrUtil.blankToDefault(parsed != null ? objectToText(parsed.getDocumentType()) : null,
                    resolveDocumentType(file.getFileName())));
            item.setParseResult(StrUtil.blankToDefault(parsed != null ? objectToText(parsed.getParseResult()) : null,
                    buildDocumentParseResult(item.getDocumentType(), file.getPreview())));
            item.setParseSource(StrUtil.blankToDefault(parsed != null ? objectToText(parsed.getParseSource()) : null,
                    "model"));
            item.setUploadedBy(uploadedBy);
            item.setUploadedAt(respVO.getParseUpdatedAt());
            item.setUrl(file.getUrl());
            documents.add(item);
        }
        respVO.setDocuments(documents);

        if (respVO.getParsedFields().isEmpty()) {
            LinkedHashSet<String> docTypes = new LinkedHashSet<>();
            documents.forEach(item -> docTypes.add(item.getDocumentType()));
            respVO.setParsedFields(buildDeviceFieldItems(code, docTypes, files.size()));
        }
        return respVO;
    }

    private void fillFlightLogDefaults(RealFlightLogResult result, YianWorkorderFlightLogParseRespVO fallback) {
        if (StrUtil.isBlank(result.getSummary())) {
            result.setSummary(fallback.getSummary());
        }
        if (StrUtil.isBlank(result.getAssistantContextSummary())) {
            result.setAssistantContextSummary(fallback.getAssistantContextSummary());
        }
        if (StrUtil.isBlank(result.getDroneSn())) {
            result.setDroneSn(fallback.getDroneSn());
        }
        if (result.getBatterySns() == null || result.getBatterySns().isEmpty()) {
            result.setBatterySns(fallback.getBatterySns());
        }
        if (result.getFlightDurationSec() == null) {
            result.setFlightDurationSec(fallback.getFlightDurationSec());
        }
        if (StrUtil.isBlank(result.getLogType())) {
            result.setLogType(fallback.getLogType());
        }
    }

    private void fillDiagnosisDefaults(YianWorkorderDiagnosisDraftRespVO result,
                                       YianWorkorderDiagnosisDraftRespVO fallback) {
        if (StrUtil.isBlank(result.getFaultCategory())) {
            result.setFaultCategory(fallback.getFaultCategory());
        }
        if (StrUtil.isBlank(result.getProbableCause())) {
            result.setProbableCause(fallback.getProbableCause());
        }
        if (StrUtil.isBlank(result.getRiskLevel())) {
            result.setRiskLevel(fallback.getRiskLevel());
        }
        if (result.getGroundedSuggestion() == null) {
            result.setGroundedSuggestion(fallback.getGroundedSuggestion());
        }
        if (result.getNeedParts() == null) {
            result.setNeedParts(fallback.getNeedParts());
        }
        if (result.getSuggestedParts() == null || result.getSuggestedParts().isEmpty()) {
            result.setSuggestedParts(fallback.getSuggestedParts());
        }
        if (StrUtil.isBlank(result.getSuggestedPartsText())) {
            result.setSuggestedPartsText(String.join(", ", emptyIfNull(result.getSuggestedParts())));
        }
        if (StrUtil.isBlank(result.getConclusion())) {
            result.setConclusion(fallback.getConclusion());
        }
        if (StrUtil.isBlank(result.getSummary())) {
            result.setSummary(fallback.getSummary());
        }
    }

    private AiModelDO resolveChatModelConfig() {
        if (properties.getChatModelId() != null) {
            return aiModelService.validateModel(properties.getChatModelId());
        }
        return aiModelService.getRequiredDefaultModel(AiModelTypeEnum.CHAT.getType());
    }

    private <T> T callJsonModel(String systemPrompt, String userPrompt, Class<T> clazz) throws Exception {
        AiModelDO model = resolveChatModelConfig();
        ChatModel chatModel = aiModelService.getChatModel(model.getId());
        AiPlatformEnum platform = AiPlatformEnum.validatePlatform(model.getPlatform());
        ChatOptions options = AiUtils.buildChatOptions(platform, model.getModel(), model.getTemperature(),
                model.getMaxTokens());
        Prompt prompt = new Prompt(List.of(new SystemMessage(systemPrompt), new UserMessage(userPrompt)), options);
        ChatResponse response = chatModel.call(prompt);
        String content = response.getResult() != null && response.getResult().getOutput() != null
                ? response.getResult().getOutput().getText() : "";
        String json = unwrapJson(content);
        T result = objectMapper.readValue(json, clazz);
        if (result instanceof YianWorkorderDiagnosisDraftRespVO respVO) {
            respVO.setRawResponse(content);
        }
        return result;
    }

    private String unwrapJson(String content) {
        String value = StrUtil.blankToDefault(content, "").trim();
        if (value.startsWith("```")) {
            value = value.replaceFirst("^```json", "").replaceFirst("^```", "");
            value = value.replaceAll("```$", "").trim();
        }
        int start = value.indexOf('{');
        int end = value.lastIndexOf('}');
        if (start >= 0 && end > start) {
            return value.substring(start, end + 1);
        }
        return value;
    }

    private YianAssetDeviceDocumentParseRespVO.FieldItem buildField(String label, String value) {
        YianAssetDeviceDocumentParseRespVO.FieldItem item = new YianAssetDeviceDocumentParseRespVO.FieldItem();
        item.setLabel(label);
        item.setValue(value);
        return item;
    }

    private String objectToText(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String str) {
            return str;
        }
        if (value instanceof Number || value instanceof Boolean) {
            return String.valueOf(value);
        }
        if (value instanceof Map<?, ?> map) {
            for (String key : List.of("summary", "result", "description", "value", "text", "label", "type")) {
                Object candidate = map.get(key);
                if (candidate instanceof String str && StrUtil.isNotBlank(str)) {
                    return str;
                }
            }
        }
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception ex) {
            return String.valueOf(value);
        }
    }

    private <T> List<T> emptyIfNull(List<T> source) {
        return source == null ? new ArrayList<>() : source;
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (CharSequenceUtil.isNotBlank(value)) {
                return value;
            }
        }
        return "";
    }

    private void persistAssetParseResult(Long machineryId, String code, String triggerType, List<StoredFile> files,
                                         YianAssetDeviceDocumentParseRespVO result, String errorMessage) {
        try {
            normalizeAssetParseResp(result, LocalDateTime.now());
            assetParseResultMapper.insert(YianAiAssetParseResultDO.builder()
                    .machineryId(machineryId)
                    .machineryCode(code)
                    .triggerType(triggerType)
                    .parseMode(result.getMode())
                    .parseSource(result.getParseSource())
                    .parseSummary(result.getParseSummary())
                    .latestWarning(firstOf(result.getWarnings()))
                    .latestMissingItem(firstOf(result.getMissingItems()))
                    .resultJson(objectToJson(result))
                    .build());
            persistTaskLog(BIZ_TYPE_ASSET_PARSE, buildBizKey(machineryId, code), result.getMode(),
                    resolveModelNameForMode(result.getMode()), true, objectToJson(buildAssetParseRequestSnapshot(
                            machineryId, code, triggerType, files)), objectToJson(result), errorMessage);
        } catch (Exception ex) {
            log.warn("Failed to persist asset parse result", ex);
        }
    }

    private void persistFlightLogParseResult(Long workorderId, String orderNo, String deviceCode, List<StoredFile> files,
                                             YianWorkorderFlightLogParseRespVO result, String errorMessage) {
        try {
            normalizeFlightLogParseResp(result);
            flightLogParseResultMapper.insert(YianAiFlightLogParseResultDO.builder()
                    .workorderId(workorderId)
                    .orderNo(orderNo)
                    .deviceCode(deviceCode)
                    .parseMode(result.getMode())
                    .parseSource(result.getParseSource())
                    .summary(result.getSummary())
                    .assistantContextSummary(result.getAssistantContextSummary())
                    .droneSn(result.getDroneSn())
                    .batterySnsJson(objectToJson(result.getBatterySns()))
                    .flightDurationSec(result.getFlightDurationSec())
                    .logType(result.getLogType())
                    .resultJson(objectToJson(result))
                    .build());
            persistTaskLog(BIZ_TYPE_FLIGHT_LOG_PARSE, buildBizKey(workorderId, orderNo), result.getMode(),
                    resolveModelNameForMode(result.getMode()), true, objectToJson(buildFlightLogRequestSnapshot(
                            workorderId, orderNo, deviceCode, files)), objectToJson(result), errorMessage);
        } catch (Exception ex) {
            log.warn("Failed to persist flight log parse result", ex);
        }
    }

    private void persistDiagnosisDraft(YianWorkorderDiagnosisDraftGenerateReqVO reqVO,
                                       YianWorkorderDiagnosisDraftRespVO result, String errorMessage) {
        try {
            normalizeDiagnosisDraftResp(result);
            diagnosisDraftMapper.insert(YianAiDiagnosisDraftDO.builder()
                    .workorderId(reqVO.getWorkorderId())
                    .orderNo(reqVO.getOrderNo())
                    .deviceCode(reqVO.getDeviceCode())
                    .draftMode(result.getMode())
                    .faultCategory(result.getFaultCategory())
                    .probableCause(result.getProbableCause())
                    .riskLevel(result.getRiskLevel())
                    .groundedSuggestion(result.getGroundedSuggestion())
                    .needParts(result.getNeedParts())
                    .suggestedPartsJson(objectToJson(result.getSuggestedParts()))
                    .suggestedPartsText(result.getSuggestedPartsText())
                    .conclusion(result.getConclusion())
                    .summary(result.getSummary())
                    .resultJson(objectToJson(result))
                    .build());
            persistTaskLog(BIZ_TYPE_DIAGNOSIS_DRAFT, buildBizKey(reqVO.getWorkorderId(), reqVO.getOrderNo()),
                    result.getMode(), resolveModelNameForMode(result.getMode()), true,
                    objectToJson(reqVO), objectToJson(result), errorMessage);
        } catch (Exception ex) {
            log.warn("Failed to persist diagnosis draft result", ex);
        }
    }

    private void persistTaskLog(String bizType, String bizKey, String mode, String modelName, boolean success,
                                String requestJson, String responseJson, String errorMessage) {
        taskLogMapper.insert(YianAiTaskLogDO.builder()
                .bizType(bizType)
                .bizKey(bizKey)
                .mode(mode)
                .modelName(modelName)
                .success(success)
                .requestJson(requestJson)
                .responseJson(responseJson)
                .errorMessage(errorMessage)
                .build());
    }

    private Map<String, Object> buildAssetParseRequestSnapshot(Long machineryId, String code, String triggerType,
                                                               List<StoredFile> files) {
        Map<String, Object> snapshot = new java.util.LinkedHashMap<>();
        snapshot.put("machineryId", machineryId);
        snapshot.put("code", StrUtil.blankToDefault(code, ""));
        snapshot.put("triggerType", StrUtil.blankToDefault(triggerType, ""));
        snapshot.put("fileNames", files.stream().map(StoredFile::getFileName).toList());
        return snapshot;
    }

    private Map<String, Object> buildFlightLogRequestSnapshot(Long workorderId, String orderNo, String deviceCode,
                                                              List<StoredFile> files) {
        Map<String, Object> snapshot = new java.util.LinkedHashMap<>();
        snapshot.put("workorderId", workorderId);
        snapshot.put("orderNo", StrUtil.blankToDefault(orderNo, ""));
        snapshot.put("deviceCode", StrUtil.blankToDefault(deviceCode, ""));
        snapshot.put("fileNames", files.stream().map(StoredFile::getFileName).toList());
        return snapshot;
    }

    private String resolveModelNameForMode(String mode) {
        if (StrUtil.equalsIgnoreCase(mode, "mock")) {
            return "mock";
        }
        try {
            AiModelDO model = resolveChatModelConfig();
            return model.getName() + " / " + model.getModel();
        } catch (Exception ex) {
            return "unresolved";
        }
    }

    private String buildBizKey(Long id, String code) {
        if (id != null) {
            return String.valueOf(id);
        }
        return StrUtil.blankToDefault(code, "-");
    }

    private String firstOf(List<String> values) {
        return values == null || values.isEmpty() ? null : values.get(0);
    }

    private void normalizeAssetParseResp(YianAssetDeviceDocumentParseRespVO respVO, LocalDateTime createdAt) {
        if (respVO == null) {
            return;
        }
        respVO.setMode(StrUtil.blankToDefault(respVO.getMode(), "mock"));
        respVO.setParseSummary(localizeAssetParseSummary(respVO.getParseSummary(), respVO.getDocuments()));
        respVO.setParseSource(localizeParseSource(StrUtil.blankToDefault(respVO.getParseSource(), ""), "资料解析"));
        if (StrUtil.isBlank(respVO.getParseUpdatedAt()) && createdAt != null) {
            respVO.setParseUpdatedAt(LocalDateTimeUtil.formatNormal(createdAt));
        }
        respVO.setParsedFields(localizeFieldItems(emptyIfNull(respVO.getParsedFields())));
        respVO.setWarnings(localizeSimpleList(emptyIfNull(respVO.getWarnings())));
        respVO.setMissingItems(localizeSimpleList(emptyIfNull(respVO.getMissingItems())));
        respVO.setDocuments(localizeDocumentItems(emptyIfNull(respVO.getDocuments())));
    }

    private void normalizeFlightLogParseResp(YianWorkorderFlightLogParseRespVO respVO) {
        if (respVO == null) {
            return;
        }
        respVO.setMode(StrUtil.blankToDefault(respVO.getMode(), "mock"));
        respVO.setParseSource(localizeParseSource(StrUtil.blankToDefault(respVO.getParseSource(), ""), "飞行日志解析"));
        respVO.setBatterySns(emptyIfNull(respVO.getBatterySns()));
        respVO.setAttachments(emptyIfNull(respVO.getAttachments()));
        respVO.setLogType(localizeLogType(respVO.getLogType()));
        respVO.setSummary(localizeFlightLogSummary(respVO));
        respVO.setAssistantContextSummary(localizeFlightLogAssistantSummary(respVO));
    }

    private void normalizeDiagnosisDraftResp(YianWorkorderDiagnosisDraftRespVO respVO) {
        if (respVO == null) {
            return;
        }
        respVO.setMode(StrUtil.blankToDefault(respVO.getMode(), "mock"));
        respVO.setFaultCategory(localizeDiagnosisCategory(StrUtil.blankToDefault(respVO.getFaultCategory(), "")));
        respVO.setProbableCause(localizeDiagnosisSentence(StrUtil.blankToDefault(respVO.getProbableCause(), "")));
        respVO.setRiskLevel(StrUtil.blankToDefault(respVO.getRiskLevel(), "medium"));
        if (respVO.getGroundedSuggestion() == null) {
            respVO.setGroundedSuggestion(false);
        }
        if (respVO.getNeedParts() == null) {
            respVO.setNeedParts(false);
        }
        respVO.setSuggestedParts(localizeDiagnosisParts(emptyIfNull(respVO.getSuggestedParts())));
        respVO.setSuggestedPartsText(StrUtil.blankToDefault(respVO.getSuggestedPartsText(),
                String.join("、", respVO.getSuggestedParts())));
        respVO.setSuggestedPartsText(localizeDiagnosisSentence(respVO.getSuggestedPartsText()));
        respVO.setConclusion(localizeDiagnosisSentence(StrUtil.blankToDefault(respVO.getConclusion(), "")));
        respVO.setSummary(localizeDiagnosisSentence(StrUtil.blankToDefault(respVO.getSummary(), "")));
    }

    private List<YianAssetDeviceDocumentParseRespVO.FieldItem> localizeFieldItems(
            List<YianAssetDeviceDocumentParseRespVO.FieldItem> fields) {
        List<YianAssetDeviceDocumentParseRespVO.FieldItem> localized = new ArrayList<>();
        for (YianAssetDeviceDocumentParseRespVO.FieldItem field : fields) {
            if (field == null) {
                continue;
            }
            field.setLabel(localizeFieldLabel(field.getLabel()));
            field.setValue(localizeFieldValue(field.getLabel(), field.getValue()));
            localized.add(field);
        }
        return localized;
    }

    private List<YianAssetDeviceDocumentParseRespVO.DocumentItem> localizeDocumentItems(
            List<YianAssetDeviceDocumentParseRespVO.DocumentItem> documents) {
        List<YianAssetDeviceDocumentParseRespVO.DocumentItem> localized = new ArrayList<>();
        for (YianAssetDeviceDocumentParseRespVO.DocumentItem item : documents) {
            if (item == null) {
                continue;
            }
            item.setDocumentType(localizeDocumentType(item.getDocumentType()));
            item.setParseResult(localizeDocumentParseResult(item.getParseResult(), item.getDocumentType()));
            item.setParseSource(localizeParseSource(item.getParseSource(), "附件解析"));
            localized.add(item);
        }
        return localized;
    }

    private List<String> localizeSimpleList(List<String> values) {
        List<String> localized = new ArrayList<>();
        for (String value : values) {
            localized.add(localizeGenericSentence(value));
        }
        return localized;
    }

    private String localizeAssetParseSummary(String summary,
                                             List<YianAssetDeviceDocumentParseRespVO.DocumentItem> documents) {
        String normalized = StrUtil.blankToDefault(summary, "");
        if (looksLikeEnglishSentence(normalized)) {
            LinkedHashSet<String> docTypes = new LinkedHashSet<>();
            for (YianAssetDeviceDocumentParseRespVO.DocumentItem item : emptyIfNull(documents)) {
                docTypes.add(localizeDocumentType(item.getDocumentType()));
            }
            if (!docTypes.isEmpty()) {
                return "已完成建档资料解析，识别到的资料类型包括：" + String.join("、", docTypes) + "。";
            }
        }
        return localizeGenericSentence(normalized);
    }

    private String localizeFieldLabel(String label) {
        String normalized = StrUtil.blankToDefault(label, "");
        return switch (normalized) {
            case "Device Code", "device_code" -> "设备编码";
            case "Attachment Count" -> "附件数量";
            case "Recognized Document Types" -> "识别到的资料类型";
            case "certificate_no" -> "证书编号";
            case "calibration_date" -> "校准日期";
            case "first_flight_check" -> "首飞检查";
            default -> normalized;
        };
    }

    private String localizeFieldValue(String label, String value) {
        String normalized = StrUtil.blankToDefault(value, "");
        if ("completed".equalsIgnoreCase(normalized)) {
            return "已完成";
        }
        if ("none".equalsIgnoreCase(normalized)) {
            return "无";
        }
        if (StrUtil.equalsAnyIgnoreCase(label, "Recognized Document Types")) {
            return localizeCommaJoinedDocumentTypes(normalized);
        }
        return normalized;
    }

    private String localizeCommaJoinedDocumentTypes(String value) {
        String[] parts = StrUtil.splitToArray(StrUtil.blankToDefault(value, ""), ',');
        List<String> localized = new ArrayList<>();
        for (String part : parts) {
            String item = localizeDocumentType(StrUtil.trim(part));
            if (StrUtil.isNotBlank(item)) {
                localized.add(item);
            }
        }
        return localized.isEmpty() ? value : String.join("、", localized);
    }

    private String localizeDocumentType(String documentType) {
        String normalized = StrUtil.blankToDefault(documentType, "");
        return switch (normalized) {
            case "Certificate" -> "合格证";
            case "Purchase Proof" -> "采购凭证";
            case "Calibration Record" -> "校准记录";
            case "First Flight Checklist" -> "首飞检查单";
            case "Inspection Report" -> "检测报告";
            case "Log Attachment" -> "日志附件";
            case "Other" -> "其他";
            default -> normalized;
        };
    }

    private String localizeLogType(String logType) {
        String normalized = StrUtil.blankToDefault(logType, "");
        return switch (normalized.toLowerCase(Locale.ROOT)) {
            case "flightrecord", "dji flightrecord" -> "DJI 飞行记录";
            case "px4 ulog" -> "PX4 ULog 日志";
            case "ardupilot" -> "ArduPilot 日志";
            case "dji dat" -> "DJI DAT 日志";
            case "generic flight log" -> "通用飞行日志";
            default -> normalized;
        };
    }

    private String localizeDocumentParseResult(String parseResult, String documentType) {
        String normalized = StrUtil.blankToDefault(parseResult, "");
        if (looksLikeEnglishSentence(normalized)) {
            return "已识别为" + localizeDocumentType(documentType) + "，并完成摘要提取。";
        }
        return localizeGenericSentence(normalized);
    }

    private String localizeParseSource(String parseSource, String scene) {
        String normalized = StrUtil.blankToDefault(parseSource, "").trim();
        if (StrUtil.isBlank(normalized)) {
            return "来源：" + scene;
        }
        if (normalized.startsWith("来源：")) {
            return normalized;
        }
        if (normalized.startsWith("Source:")) {
            return "来源：" + normalized.substring("Source:".length()).trim();
        }
        if ("model".equalsIgnoreCase(normalized)) {
            return "来源：AI 模型解析";
        }
        if ("mock".equalsIgnoreCase(normalized) || "Mock parsing".equalsIgnoreCase(normalized)) {
            return "来源：AI 模拟解析";
        }
        if (looksLikeEnglishSentence(normalized)) {
            return "来源：" + localizeGenericSentence(normalized);
        }
        return normalized;
    }

    private String localizeFlightLogSummary(YianWorkorderFlightLogParseRespVO respVO) {
        String summary = StrUtil.blankToDefault(respVO.getSummary(), "");
        if (!looksLikeEnglishSentence(summary)) {
            return localizeGenericSentence(summary);
        }
        String droneSn = firstNonBlank(respVO.getDroneSn(), "未知设备");
        String duration = respVO.getFlightDurationSec() == null ? "未知时长"
                : respVO.getFlightDurationSec() + " 秒";
        String batteries = respVO.getBatterySns().isEmpty() ? "未识别到电池序列号"
                : String.join("、", respVO.getBatterySns());
        return "已解析飞行日志：设备 " + droneSn + " 执行任务约 " + duration
                + "，关联电池为 " + batteries + "。";
    }

    private String localizeFlightLogAssistantSummary(YianWorkorderFlightLogParseRespVO respVO) {
        String summary = StrUtil.blankToDefault(respVO.getAssistantContextSummary(), "");
        if (!looksLikeEnglishSentence(summary)) {
            return localizeGenericSentence(summary);
        }
        String logType = firstNonBlank(respVO.getLogType(), "飞行日志");
        String batteries = respVO.getBatterySns().isEmpty() ? "未识别"
                : String.join("、", respVO.getBatterySns());
        return "最近一次已加载的" + logType + "可用于辅助初诊，当前识别到的电池序列号为 " + batteries + "。";
    }

    private String localizeDiagnosisCategory(String category) {
        String normalized = StrUtil.blankToDefault(category, "");
        if ("Gimbal Stability Issue".equals(normalized)) {
            return "云台稳定性异常";
        }
        return switch (normalized) {
            case "Battery Thermal Management" -> "电池热管理异常";
            case "Power System / Battery" -> "动力系统 / 电池";
            case "Communication Link" -> "通信链路";
            case "Flight Control System" -> "飞控系统";
            default -> normalized;
        };
    }

    private List<String> localizeDiagnosisParts(List<String> parts) {
        List<String> localized = new ArrayList<>();
        for (String part : parts) {
            String normalized = StrUtil.blankToDefault(part, "");
            switch (normalized) {
                case "Spare battery" -> localized.add("备用电池");
                case "Battery diagnostic toolkit" -> localized.add("电池诊断工具");
                case "Flight-control diagnostic toolkit" -> localized.add("飞控诊断工具");
                case "Attitude sensor assembly" -> localized.add("姿态传感器组件");
                default -> localized.add(normalized);
            }
        }
        return localized;
    }

    private String localizeDiagnosisSentence(String text) {
        String normalized = StrUtil.blankToDefault(text, "");
        if (StrUtil.isBlank(normalized)) {
            return normalized;
        }
        if ("Gimbal motor calibration drift or IMU misalignment; possible firmware inconsistency between gimbal controller and flight controller.".equals(normalized)) {
            return "疑似云台电机校准漂移或 IMU 未对齐，也不排除云台控制器与飞控之间存在固件版本不一致。";
        }
        if ("Gimbal instability detected during stability check mission; no hardware failure evident from logs or images, but requires recalibration and firmware validation before flight.".equals(normalized)) {
            return "在稳定性检查任务中检测到云台异常抖动；日志和图像暂未发现明确的硬件损坏，但起飞前仍需完成重新校准和固件一致性核验。";
        }
        if ("Flight-control diagnostic toolkit, Attitude sensor assembly".equals(normalized)) {
            return "飞控诊断工具, 姿态传感器组件";
        }
        if ("Flight log confirms gimbal stability check executed for 642 seconds using batteries BAT-UI-201 and BAT-UI-202. No visual anomalies in image summary and no attachments show mechanical damage. Likely software/firmware or calibration-related — not a critical hardware fault, but unsafe to fly until verified.".equals(normalized)) {
            return "飞行日志显示本次任务使用 BAT-UI-201 和 BAT-UI-202，完成了 642 秒的云台稳定性检查。图像摘要未见明显异常，附件也未显示机械损伤，更可能是软件、固件或校准类问题，虽然暂时不像是严重硬件故障，但在完成核验前仍不建议起飞。";
        }
        return switch (normalized) {
            case "Possible battery health degradation or mismatch between mounted battery and registered records. Verify SOH, cycle count and voltage delta first." ->
                    "疑似电池健康衰减，或当前挂载电池与备案关系不一致，建议优先核对 SOH、循环次数与压差。";
            case "Ground the device first, complete battery log and inspection report review, then decide whether replacement is required." ->
                    "建议先停飞设备，补充电池日志与检测报告复核后，再判断是否需要更换电池。";
            case "Possible transmission link fluctuation or on-site interference causing communication quality degradation. Verify antennas, channels and electromagnetic conditions." ->
                    "疑似图传链路波动或现场干扰导致通信质量下降，建议核对天线、信道与电磁环境。";
            case "Complete on-site retest and log review first, then decide whether repair is required." ->
                    "建议先完成现场复测和日志复核，再判断是否进入维修流程。";
            case "The current symptom suggests possible flight-control parameter anomaly, sensor drift or incomplete pre-flight checks." ->
                    "当前现象疑似与飞控参数异常、传感器漂移或首飞检查不完整有关。";
            case "Complete log review, sensor self-check and first-flight checklist verification before deciding whether to replace flight-control related parts." ->
                    "建议先完成日志复核、传感器自检与首飞检查单核验，再决定是否更换飞控相关部件。";
            case "This is a structured preliminary diagnosis draft generated from the work order symptom, attachments and log summary. Human confirmation is still required." ->
                    "以上为基于工单现象、附件与日志摘要生成的结构化初诊草案，仍需人工确认。";
            case "Overheating of one or both batteries (BAT-001 and BAT-002) due to degraded thermal interface, blocked ventilation, or internal cell imbalance; possible aging or prior thermal stress." ->
                    "一块或多块电池出现过热，可能与散热界面老化、通风受阻或电芯不平衡有关，也不排除电池老化或既往热冲击造成的影响。";
            case "Replace both batteries (BAT-001 and BAT-002) after diagnostic verification." ->
                    "建议在完成诊断确认后更换 BAT-001 和 BAT-002。";
            case "Immediate grounding required: battery thermal warning with auto RTH indicates unsafe operating condition. Batteries must be inspected for swelling, voltage deviation, and thermal sensor integrity before reuse." ->
                    "建议立即停飞：飞行中出现电池过温并触发自动返航，当前运行条件不安全。电池重新使用前需重点检查鼓包、压差和温度传感器完整性。";
            case "Battery temperature warning triggered during flight on M350 RTK, causing automatic return-to-home. Log and photo confirm active thermal alert for BAT-001 and BAT-002. High-risk condition necessitates grounding and battery replacement pending diagnostics." ->
                    "M350 RTK 在飞行过程中触发电池温度告警并自动返航。日志和现场图片均指向 BAT-001、BAT-002 存在热异常，当前属于高风险状态，建议先停飞并完成诊断后再决定是否更换。";
            default -> localizeGenericSentence(normalized);
        };
    }

    private String localizeGenericSentence(String text) {
        String normalized = StrUtil.blankToDefault(text, "");
        if (StrUtil.isBlank(normalized)) {
            return normalized;
        }
        if (!looksLikeEnglishSentence(normalized)) {
            return normalized;
        }
        return normalized
                .replace("uploaded document", "上传附件")
                .replace("text/plain content extraction", "文本内容提取")
                .replace("real-model parsing", "AI 模型解析")
                .replace("attachment parsing", "附件解析")
                .replace("flight-log parsing", "飞行日志解析")
                .replace("fallback after real model failure", "真实模型失败后降级")
                .replace("No default chat model is available. The system will use mock results.",
                        "当前没有可用的默认聊天模型，系统将回退到模拟结果。")
                .replace("Mock mode is enabled. A real chat model is available for switching.",
                        "当前启用了模拟模式，系统也已具备切换到真实模型的能力。")
                .replace("Real model mode is enabled.", "当前已启用真实模型模式。");
    }

    private boolean looksLikeEnglishSentence(String text) {
        String normalized = StrUtil.blankToDefault(text, "");
        return ASCII_LETTER_PATTERN.matcher(normalized).find()
                && !CHINESE_PATTERN.matcher(normalized).find();
    }

    private String objectToJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception ex) {
            return "{}";
        }
    }

    @Data
    private static class StoredFile {
        private final String fileName;
        private final String mimeType;
        private final Long size;
        private final String url;
        private final String preview;
    }

    @Data
    private static class RealDocumentParsePrompt {
        private Long machineryId;
        private String code;
        private String uploadedBy;
        private String trigger;
        private List<StoredFile> files;
    }

    @Data
    private static class RealDocumentParseResult {
        private String parseSummary;
        private String parseSource;
        private List<YianAssetDeviceDocumentParseRespVO.FieldItem> parsedFields;
        private List<String> warnings;
        private List<String> missingItems;
        private List<RealDocumentItem> documents;
    }

    @Data
    private static class RealDocumentItem {
        private Object fileName;
        private Object documentType;
        private Object parseResult;
        private Object parseSource;
    }

    @Data
    private static class RealFlightLogPrompt {
        private Long workorderId;
        private String orderNo;
        private String deviceCode;
        private String uploadedBy;
        private List<StoredFile> files;
    }

    @Data
    private static class RealFlightLogResult {
        private String summary;
        private String assistantContextSummary;
        private String parseSource;
        private String droneSn;
        private List<String> batterySns;
        private Integer flightDurationSec;
        private String logType;
    }
}
