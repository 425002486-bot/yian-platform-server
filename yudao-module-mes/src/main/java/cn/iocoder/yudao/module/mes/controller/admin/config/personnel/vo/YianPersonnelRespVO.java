package cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class YianPersonnelRespVO {

    private Long id;
    private Long userId;
    private String userName;
    private String mobile;
    private Integer userStatus;
    private Long stationId;
    private String stationName;
    private String jobTitle;
    private String bizRole;
    private String bizRoleLabel;
    private String stages;
    private LocalDateTime createTime;

}
