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

@TableName("yian_ai_diagnosis_draft")
@KeySequence("yian_ai_diagnosis_draft_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YianAiDiagnosisDraftDO extends BaseDO {

    @TableId
    private Long id;

    private Long workorderId;

    private String orderNo;

    private String deviceCode;

    private String draftMode;

    private String faultCategory;

    private String probableCause;

    private String riskLevel;

    private Boolean groundedSuggestion;

    private Boolean needParts;

    private String suggestedPartsJson;

    private String suggestedPartsText;

    private String conclusion;

    private String summary;

    private String resultJson;
}
