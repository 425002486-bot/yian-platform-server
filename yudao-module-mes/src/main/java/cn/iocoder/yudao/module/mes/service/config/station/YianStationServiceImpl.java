package cn.iocoder.yudao.module.mes.service.config.station;

import cn.hutool.core.util.ObjUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.mes.controller.admin.config.station.vo.YianStationPageReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.station.vo.YianStationSaveReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.station.YianStationDO;
import cn.iocoder.yudao.module.mes.dal.mysql.config.station.YianStationMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.NOT_FOUND;

@Service
@Validated
public class YianStationServiceImpl implements YianStationService {

    @Resource
    private YianStationMapper stationMapper;

    @Override
    public Long createStation(YianStationSaveReqVO createReqVO) {
        // 校验编码唯一
        validateCodeUnique(null, createReqVO.getCode());
        YianStationDO station = BeanUtils.toBean(createReqVO, YianStationDO.class);
        stationMapper.insert(station);
        return station.getId();
    }

    @Override
    public void updateStation(YianStationSaveReqVO updateReqVO) {
        validateStationExists(updateReqVO.getId());
        validateCodeUnique(updateReqVO.getId(), updateReqVO.getCode());
        YianStationDO updateObj = BeanUtils.toBean(updateReqVO, YianStationDO.class);
        stationMapper.updateById(updateObj);
    }

    @Override
    public void deleteStation(Long id) {
        validateStationExists(id);
        stationMapper.deleteById(id);
    }

    @Override
    public YianStationDO getStation(Long id) {
        return stationMapper.selectById(id);
    }

    @Override
    public PageResult<YianStationDO> getStationPage(YianStationPageReqVO pageReqVO) {
        return stationMapper.selectPage(pageReqVO);
    }

    private void validateStationExists(Long id) {
        if (stationMapper.selectById(id) == null) {
            throw exception(NOT_FOUND);
        }
    }

    private void validateCodeUnique(Long id, String code) {
        YianStationDO station = stationMapper.selectByCode(code);
        if (station == null) return;
        if (ObjUtil.notEqual(id, station.getId())) {
            throw exception(NOT_FOUND); // TODO: 用专用错误码
        }
    }

}
