package cn.iocoder.yudao.module.mes.controller.admin.asset.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class YianAssetImportSegmentRespVO {

    private List<String> createCodes;

    private List<String> updateCodes;

    private Map<String, String> failureCodes;
}
