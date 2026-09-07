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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("yian_workorder_return_record")
@KeySequence("yian_workorder_return_record_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YianWorkorderReturnRecordDO extends BaseDO {

    @TableId
    private Long id;

    private Long workorderId;

    private String orderNo;

    private Long issueId;

    private String issueCode;

    private Long itemId;

    private String itemName;

    private String itemSpec;

    private BigDecimal returnQuantity;

    private String returnReason;

    private String operatorName;

    private LocalDateTime returnedAt;
}
