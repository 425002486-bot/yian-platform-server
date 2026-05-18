package cn.iocoder.yudao.module.mes.dal.mysql.config.rule;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianRuleChangeLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface YianRuleChangeLogMapper extends BaseMapperX<YianRuleChangeLogDO> {

    default List<YianRuleChangeLogDO> selectListOrderByCreateTimeDesc() {
        return selectList(new LambdaQueryWrapperX<YianRuleChangeLogDO>()
                .orderByDesc(YianRuleChangeLogDO::getCreateTime));
    }

}
