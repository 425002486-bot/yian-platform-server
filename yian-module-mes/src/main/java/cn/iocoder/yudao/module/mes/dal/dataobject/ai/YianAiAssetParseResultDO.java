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

@TableName("yian_ai_asset_parse_result")
@KeySequence("yian_ai_asset_parse_result_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YianAiAssetParseResultDO extends BaseDO {

    @TableId
    private Long id;

    private Long machineryId;

    private String machineryCode;

    private String triggerType;

    private String parseMode;

    private String parseSource;

    private String parseSummary;

    private String latestWarning;

    private String latestMissingItem;

    private String resultJson;
}
