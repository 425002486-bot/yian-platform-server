package cn.iocoder.yudao.module.mes.service.ai;

import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianAiCapabilityRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianAssetDeviceDocumentParseRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianAssetDeviceReparseReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderDiagnosisDraftGenerateReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderDiagnosisDraftRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.ai.vo.YianWorkorderFlightLogParseRespVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface YianAiIntegrationService {

    YianAiCapabilityRespVO getCapabilities();

    YianAssetDeviceDocumentParseRespVO parseDeviceDocuments(Long machineryId, String code, String uploadedBy,
                                                            List<MultipartFile> files) throws Exception;

    YianAssetDeviceDocumentParseRespVO reparseDeviceDocuments(YianAssetDeviceReparseReqVO reqVO) throws Exception;

    YianAssetDeviceDocumentParseRespVO getLatestDeviceDocumentParseResult(Long machineryId, String code);

    YianWorkorderFlightLogParseRespVO parseFlightLogs(Long workorderId, String orderNo, String deviceCode,
                                                      String uploadedBy, List<MultipartFile> files) throws Exception;

    YianWorkorderFlightLogParseRespVO getLatestFlightLogParseResult(Long workorderId, String orderNo);

    YianWorkorderDiagnosisDraftRespVO generateDiagnosisDraft(YianWorkorderDiagnosisDraftGenerateReqVO reqVO);

    YianWorkorderDiagnosisDraftRespVO getLatestDiagnosisDraftResult(Long workorderId, String orderNo);
}
