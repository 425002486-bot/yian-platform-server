package cn.iocoder.yudao.module.mes.dal.dataobject.officialsite;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("yian_official_site_lead")
@Data
@EqualsAndHashCode(callSuper = true)
public class YianOfficialSiteLeadDO extends BaseDO {

    @TableId
    private Long id;

    private String contactName;

    private String companyName;

    private String phoneNumber;

    private String email;

    private String interestedScene;

    private String message;

    /**
     * 0-待跟进 1-已联系 2-已转化
     */
    private Integer status;
}
