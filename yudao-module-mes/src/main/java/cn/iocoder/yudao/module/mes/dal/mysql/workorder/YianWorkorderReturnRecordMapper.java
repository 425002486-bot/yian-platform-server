package cn.iocoder.yudao.module.mes.dal.mysql.workorder;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.mes.dal.dataobject.workorder.YianWorkorderReturnRecordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface YianWorkorderReturnRecordMapper extends BaseMapperX<YianWorkorderReturnRecordDO> {

    default List<YianWorkorderReturnRecordDO> selectListByWorkorderId(Long workorderId) {
        return selectList(YianWorkorderReturnRecordDO::getWorkorderId, workorderId);
    }
}
