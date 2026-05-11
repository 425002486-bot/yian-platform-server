package cn.iocoder.yudao.module.mes.service.asset.battery;

import cn.iocoder.yudao.module.mes.controller.admin.asset.battery.vo.YianAssetBatteryListReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportExcelVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportSegmentRespVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.asset.battery.YianAssetBatteryDO;

import java.util.List;

public interface YianAssetBatteryService {

    List<YianAssetBatteryDO> getBatteryList(YianAssetBatteryListReqVO reqVO);

    YianAssetImportSegmentRespVO importBatteryList(List<YianAssetImportExcelVO> importBatteryList,
                                                   boolean updateSupport);
}
