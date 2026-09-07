package cn.iocoder.yudao.module.mes.dal.mysql.workorder;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.mes.dal.dataobject.workorder.YianWorkorderMaterialDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface YianWorkorderMaterialMapper extends BaseMapperX<YianWorkorderMaterialDO> {

    default List<YianWorkorderMaterialDO> selectListByWorkorderId(Long workorderId) {
        return selectList(YianWorkorderMaterialDO::getWorkorderId, workorderId);
    }
}
