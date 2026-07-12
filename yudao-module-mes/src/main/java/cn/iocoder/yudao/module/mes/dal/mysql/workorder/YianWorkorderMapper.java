package cn.iocoder.yudao.module.mes.dal.mysql.workorder;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.mes.dal.dataobject.workorder.YianWorkorderDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YianWorkorderMapper extends BaseMapperX<YianWorkorderDO> {

    default YianWorkorderDO selectByOrderNo(String orderNo) {
        return selectOne(YianWorkorderDO::getOrderNo, orderNo);
    }
}
