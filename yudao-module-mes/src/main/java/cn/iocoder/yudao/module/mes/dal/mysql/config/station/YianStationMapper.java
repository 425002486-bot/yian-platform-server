package cn.iocoder.yudao.module.mes.dal.mysql.config.station;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.mes.controller.admin.config.station.vo.YianStationPageReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.station.YianStationDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YianStationMapper extends BaseMapperX<YianStationDO> {

    default PageResult<YianStationDO> selectPage(YianStationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<YianStationDO>()
                .likeIfPresent(YianStationDO::getName, reqVO.getName())
                .likeIfPresent(YianStationDO::getCode, reqVO.getCode())
                .eqIfPresent(YianStationDO::getRegion, reqVO.getRegion())
                .eqIfPresent(YianStationDO::getStatus, reqVO.getStatus())
                .orderByDesc(YianStationDO::getId));
    }

    default YianStationDO selectByCode(String code) {
        return selectOne(YianStationDO::getCode, code);
    }

}
