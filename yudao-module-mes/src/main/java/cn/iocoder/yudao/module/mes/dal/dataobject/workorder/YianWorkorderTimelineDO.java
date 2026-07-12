package cn.iocoder.yudao.module.mes.dal.dataobject.workorder;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@TableName("yian_workorder_timeline")
@KeySequence("yian_workorder_timeline_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YianWorkorderTimelineDO extends BaseDO {

    @TableId
    private Long id;

    private Long workorderId;

    private String stage;

    private String title;

    private String detail;

    private String operator;

    private LocalDateTime operatedAt;
}
