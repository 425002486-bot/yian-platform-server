package cn.iocoder.yudao.module.mes.dal.mysql.config.personnel;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo.YianPersonnelPageReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.personnel.YianPersonnelExtDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YianPersonnelExtMapper extends BaseMapperX<YianPersonnelExtDO> {

    default PageResult<YianPersonnelExtDO> selectPage(YianPersonnelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YianPersonnelExtDO>()
                .eqIfPresent(YianPersonnelExtDO::getStationId, reqVO.getStationId())
                .eqIfPresent(YianPersonnelExtDO::getBizRole, reqVO.getBizRole())
                .orderByDesc(YianPersonnelExtDO::getId));
    }

    default YianPersonnelExtDO selectByUserId(Long userId) {
        return selectOne(YianPersonnelExtDO::getUserId, userId);
    }

    default Long selectCountByBizRole(String bizRole) {
        return selectCount(YianPersonnelExtDO::getBizRole, bizRole);
    }

}
