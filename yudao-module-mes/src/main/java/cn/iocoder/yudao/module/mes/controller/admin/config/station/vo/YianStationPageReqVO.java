package cn.iocoder.yudao.module.mes.controller.admin.config.station.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 翼安站点分页 Request VO")
@Data
public class YianStationPageReqVO extends PageParam {

    @Schema(description = "站点名称", example = "华东")
    private String name;

    @Schema(description = "站点编码", example = "ST-HD")
    private String code;

    @Schema(description = "所属区域", example = "华东")
    private String region;

    @Schema(description = "运行状态", example = "0")
    private Integer status;

}
