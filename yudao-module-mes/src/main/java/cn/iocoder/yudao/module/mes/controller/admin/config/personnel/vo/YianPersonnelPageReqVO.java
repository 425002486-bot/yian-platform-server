package cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class YianPersonnelPageReqVO extends PageParam {

    @Schema(description = "所属站点ID")
    private Long stationId;

    @Schema(description = "业务角色")
    private String bizRole;

}
