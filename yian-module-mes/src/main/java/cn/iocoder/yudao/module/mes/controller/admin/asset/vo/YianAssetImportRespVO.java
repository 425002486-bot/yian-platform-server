package cn.iocoder.yudao.module.mes.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 统一资产导入 Response VO")
@Data
@Builder
public class YianAssetImportRespVO {

    private List<String> machineryCreateCodes;

    private List<String> machineryUpdateCodes;

    private List<String> batteryCreateCodes;

    private List<String> batteryUpdateCodes;

    private Map<String, String> failureCodes;
}
