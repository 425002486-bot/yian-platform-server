package cn.iocoder.yudao.module.mes.service.asset.battery;

import cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo.YianAssetBatteryListReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo.YianAssetBatterySaveReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportExcelVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportSegmentRespVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.asset.battery.YianAssetBatteryDO;

import java.util.List;

public interface YianAssetBatteryService {

    Long createBattery(YianAssetBatterySaveReqVO createReqVO);

    void updateBattery(YianAssetBatterySaveReqVO updateReqVO);

    YianAssetBatteryDO getBattery(Long id);

    List<YianAssetBatteryDO> getBatteryList(YianAssetBatteryListReqVO reqVO);

    YianAssetImportSegmentRespVO importBatteryList(List<YianAssetImportExcelVO> importBatteryList,
                                                   boolean updateSupport);
}
