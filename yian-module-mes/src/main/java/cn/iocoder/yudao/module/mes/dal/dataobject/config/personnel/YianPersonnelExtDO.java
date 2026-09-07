package cn.iocoder.yudao.module.mes.dal.dataobject.config.personnel;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("yian_personnel_ext")
@Data
public class YianPersonnelExtDO extends BaseDO {

    @TableId
    private Long id;
    private Long userId;
    private Long stationId;
    private String jobTitle;
    /** site_lead/ops_staff/inspector/release_approver/parts_manager/auditor */
    private String bizRole;
    private String stages;

}
