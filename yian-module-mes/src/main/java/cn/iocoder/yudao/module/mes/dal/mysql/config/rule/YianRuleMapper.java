package cn.iocoder.yudao.module.mes.dal.mysql.config.rule;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.rule.YianRuleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface YianRuleMapper extends BaseMapperX<YianRuleDO> {

    default List<YianRuleDO> selectListByCategory(String category) {
        return selectList(YianRuleDO::getCategory, category);
    }

}
