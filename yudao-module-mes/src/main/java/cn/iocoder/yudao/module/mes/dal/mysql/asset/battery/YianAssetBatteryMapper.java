package cn.iocoder.yudao.module.mes.dal.mysql.asset.battery;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo.YianAssetBatteryListReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.asset.battery.YianAssetBatteryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface YianAssetBatteryMapper extends BaseMapperX<YianAssetBatteryDO> {

    default List<YianAssetBatteryDO> selectList(YianAssetBatteryListReqVO reqVO) {
        LambdaQueryWrapperX<YianAssetBatteryDO> wrapper = new LambdaQueryWrapperX<YianAssetBatteryDO>()
                .eqIfPresent(YianAssetBatteryDO::getHealthStatus, reqVO.getHealthStatus())
                .eqIfPresent(YianAssetBatteryDO::getWorkshopId, reqVO.getWorkshopId())
                .eqIfPresent(YianAssetBatteryDO::getLinkedMachineryId, reqVO.getLinkedMachineryId())
                .orderByDesc(YianAssetBatteryDO::getUpdateTime)
                .orderByDesc(YianAssetBatteryDO::getId);
        if (StrUtil.isNotBlank(reqVO.getKeyword())) {
            wrapper.and(w -> w.like(YianAssetBatteryDO::getCode, reqVO.getKeyword())
                    .or()
                    .like(YianAssetBatteryDO::getSerialNumber, reqVO.getKeyword()));
        }
        return selectList(wrapper);
    }

    default YianAssetBatteryDO selectByCode(String code) {
        return selectOne(YianAssetBatteryDO::getCode, code);
    }
}
