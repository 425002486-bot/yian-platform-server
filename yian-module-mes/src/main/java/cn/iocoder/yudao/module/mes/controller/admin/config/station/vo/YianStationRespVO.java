package cn.iocoder.yudao.module.mes.controller.admin.config.station.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 翼安站点 Response VO")
@Data
public class YianStationRespVO {

    @Schema(description = "站点编号")
    private Long id;

    @Schema(description = "站点编码")
    private String code;

    @Schema(description = "站点名称")
    private String name;

    @Schema(description = "所属区域")
    private String region;

    @Schema(description = "负责人用户ID")
    private Long principalUserId;

    @Schema(description = "负责人姓名")
    private String principalUserName;

    @Schema(description = "服务范围")
    private String serviceScope;

    @Schema(description = "运行状态: 0-启用 1-受限运行 2-停用")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
