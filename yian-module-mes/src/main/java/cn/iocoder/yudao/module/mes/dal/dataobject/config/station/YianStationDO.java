package cn.iocoder.yudao.module.mes.dal.dataobject.config.station;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("yian_station")
@Data
public class YianStationDO extends BaseDO {

    @TableId
    private Long id;
    private String code;
    private String name;
    private String region;
    private Long principalUserId;
    private String serviceScope;
    /** 0-启用 1-受限运行 2-停用 */
    private Integer status;
    private String remark;

}
