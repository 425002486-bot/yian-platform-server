package cn.iocoder.yudao.module.mes.dal.dataobject.ai;

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

@TableName("yian_ai_flight_log_parse_result")
@KeySequence("yian_ai_flight_log_parse_result_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YianAiFlightLogParseResultDO extends BaseDO {

    @TableId
    private Long id;

    private Long workorderId;

    private String orderNo;

    private String deviceCode;

    private String parseMode;

    private String parseSource;

    private String summary;

    private String assistantContextSummary;

    private String droneSn;

    private String batterySnsJson;

    private Integer flightDurationSec;

    private String logType;

    private String resultJson;
}
