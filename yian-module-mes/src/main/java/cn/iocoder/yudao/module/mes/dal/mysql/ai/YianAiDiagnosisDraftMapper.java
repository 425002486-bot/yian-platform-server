package cn.iocoder.yudao.module.mes.dal.mysql.ai;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiDiagnosisDraftDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YianAiDiagnosisDraftMapper extends BaseMapperX<YianAiDiagnosisDraftDO> {

    default YianAiDiagnosisDraftDO selectLatest(Long workorderId, String orderNo) {
        return CollUtil.getFirst(selectList(new LambdaQueryWrapperX<YianAiDiagnosisDraftDO>()
                .eqIfPresent(YianAiDiagnosisDraftDO::getWorkorderId, workorderId)
                .eqIfPresent(YianAiDiagnosisDraftDO::getOrderNo, orderNo)
                .orderByDesc(YianAiDiagnosisDraftDO::getCreateTime)
                .orderByDesc(YianAiDiagnosisDraftDO::getId)));
    }
}
