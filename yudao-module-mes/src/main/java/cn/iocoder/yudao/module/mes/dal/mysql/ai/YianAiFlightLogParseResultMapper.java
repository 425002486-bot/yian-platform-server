package cn.iocoder.yudao.module.mes.dal.mysql.ai;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiFlightLogParseResultDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YianAiFlightLogParseResultMapper extends BaseMapperX<YianAiFlightLogParseResultDO> {

    default YianAiFlightLogParseResultDO selectLatest(Long workorderId, String orderNo) {
        return CollUtil.getFirst(selectList(new LambdaQueryWrapperX<YianAiFlightLogParseResultDO>()
                .eqIfPresent(YianAiFlightLogParseResultDO::getWorkorderId, workorderId)
                .eqIfPresent(YianAiFlightLogParseResultDO::getOrderNo, orderNo)
                .orderByDesc(YianAiFlightLogParseResultDO::getCreateTime)
                .orderByDesc(YianAiFlightLogParseResultDO::getId)));
    }
}
