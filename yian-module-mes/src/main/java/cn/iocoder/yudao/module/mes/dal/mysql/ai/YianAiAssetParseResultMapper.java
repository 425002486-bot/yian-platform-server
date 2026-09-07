package cn.iocoder.yudao.module.mes.dal.mysql.ai;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.mes.dal.dataobject.ai.YianAiAssetParseResultDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YianAiAssetParseResultMapper extends BaseMapperX<YianAiAssetParseResultDO> {

    default YianAiAssetParseResultDO selectLatest(Long machineryId, String machineryCode) {
        return CollUtil.getFirst(selectList(new LambdaQueryWrapperX<YianAiAssetParseResultDO>()
                .eqIfPresent(YianAiAssetParseResultDO::getMachineryId, machineryId)
                .eqIfPresent(YianAiAssetParseResultDO::getMachineryCode, machineryCode)
                .orderByDesc(YianAiAssetParseResultDO::getCreateTime)
                .orderByDesc(YianAiAssetParseResultDO::getId)));
    }
}
