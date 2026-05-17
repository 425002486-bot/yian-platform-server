package cn.iocoder.yudao.module.mes.controller.admin.config.station.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 翼安站点创建/修改 Request VO")
@Data
public class YianStationSaveReqVO {

    @Schema(description = "站点编号（更新时传）", example = "1")
    private Long id;

    @Schema(description = "站点编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "站点编码不能为空")
    private String code;

    @Schema(description = "站点名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "站点名称不能为空")
    private String name;

    @Schema(description = "所属区域", example = "华东")
    private String region;

    @Schema(description = "负责人用户ID", example = "1")
    private Long principalUserId;

    @Schema(description = "服务范围", example = "电力巡检 / 河道巡检")
    private String serviceScope;

    @Schema(description = "运行状态: 0-启用 1-受限运行 2-停用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "运行状态不能为空")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

}
