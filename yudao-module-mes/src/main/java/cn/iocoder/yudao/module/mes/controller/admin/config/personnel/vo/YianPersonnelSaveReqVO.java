package cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class YianPersonnelSaveReqVO {

    @Schema(description = "编号（更新时传）")
    private Long id;

    @Schema(description = "关联系统用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择系统用户")
    private Long userId;

    @Schema(description = "所属站点ID")
    private Long stationId;

    @Schema(description = "岗位名称")
    private String jobTitle;

    @Schema(description = "业务角色")
    @NotNull(message = "请选择业务角色")
    private String bizRole;

    @Schema(description = "可参与环节")
    private String stages;

}
