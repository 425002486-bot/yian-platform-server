package cn.iocoder.yudao.module.system.api.logger.dto;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 操作日志分页 Request DTO
 *
 * @author HUIHUI
 */
@Data
public class OperateLogPageReqDTO extends PageParam {

    /**
     * 模块类型
     */
    private String type;
    /**
     * 模块类型集合（IN 查询，与 type 二选一）
     */
    private Collection<String> types;
    /**
     * 模块数据编号
     */
    private Long bizId;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 创建时间范围
     */
    private LocalDateTime[] createTime;

}
