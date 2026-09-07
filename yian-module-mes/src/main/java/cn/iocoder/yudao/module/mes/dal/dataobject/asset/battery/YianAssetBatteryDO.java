package cn.iocoder.yudao.module.mes.dal.dataobject.asset.battery;

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

@TableName("mes_dv_battery")
@KeySequence("mes_dv_battery_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YianAssetBatteryDO extends BaseDO {

    @TableId
    private Long id;

    private String code;

    private String serialNumber;

    private String model;

    private Long workshopId;

    private Long linkedMachineryId;

    private Integer soh;

    private Integer cycleCount;

    private LocalDateTime lastCheckTime;

    private String checkSource;

    private String healthStatus;

    private String sourceEvidence;

    private String recommendation;

    private String remark;
}
