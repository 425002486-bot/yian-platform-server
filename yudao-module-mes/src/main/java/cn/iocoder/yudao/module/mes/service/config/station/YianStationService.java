package cn.iocoder.yudao.module.mes.service.config.station;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.mes.controller.admin.config.station.vo.YianStationPageReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.station.vo.YianStationSaveReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.station.YianStationDO;
import jakarta.validation.Valid;

public interface YianStationService {

    Long createStation(@Valid YianStationSaveReqVO createReqVO);

    void updateStation(@Valid YianStationSaveReqVO updateReqVO);

    void deleteStation(Long id);

    YianStationDO getStation(Long id);

    PageResult<YianStationDO> getStationPage(YianStationPageReqVO pageReqVO);

}
