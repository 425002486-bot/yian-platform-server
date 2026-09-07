package cn.iocoder.yudao.module.mes.service.config.station;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.mes.controller.admin.config.station.vo.YianStationPageReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.station.vo.YianStationSaveReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.station.YianStationDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.md.workstation.MesMdWorkshopDO;
import cn.iocoder.yudao.module.mes.dal.mysql.config.station.YianStationMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.md.workstation.MesMdWorkshopMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.NOT_FOUND;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.mes.enums.ErrorCodeConstants.MD_WORKSHOP_CODE_DUPLICATE;

@Service
@Validated
public class YianStationServiceImpl implements YianStationService {

    @Resource
    private YianStationMapper stationMapper;
    @Resource
    private MesMdWorkshopMapper workshopMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createStation(YianStationSaveReqVO createReqVO) {
        validateCodeUnique(null, createReqVO.getCode());
        YianStationDO station = BeanUtils.toBean(createReqVO, YianStationDO.class);
        stationMapper.insert(station);
        syncWorkshop(null, station);
        return station.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStation(YianStationSaveReqVO updateReqVO) {
        YianStationDO existingStation = validateStationExists(updateReqVO.getId());
        validateCodeUnique(updateReqVO.getId(), updateReqVO.getCode());
        YianStationDO updateObj = BeanUtils.toBean(updateReqVO, YianStationDO.class);
        stationMapper.updateById(updateObj);
        syncWorkshop(existingStation, updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteStation(Long id) {
        YianStationDO station = validateStationExists(id);
        stationMapper.deleteById(id);
        disableWorkshop(station);
    }

    @Override
    public YianStationDO getStation(Long id) {
        return stationMapper.selectById(id);
    }

    @Override
    public PageResult<YianStationDO> getStationPage(YianStationPageReqVO pageReqVO) {
        return stationMapper.selectPage(pageReqVO);
    }

    private YianStationDO validateStationExists(Long id) {
        YianStationDO station = stationMapper.selectById(id);
        if (station == null) {
            throw exception(NOT_FOUND);
        }
        return station;
    }

    private void validateCodeUnique(Long id, String code) {
        YianStationDO station = stationMapper.selectByCode(code);
        if (station == null) {
            return;
        }
        if (ObjUtil.notEqual(id, station.getId())) {
            throw exception(MD_WORKSHOP_CODE_DUPLICATE);
        }
    }

    private void syncWorkshop(YianStationDO before, YianStationDO after) {
        MesMdWorkshopDO workshop = findMatchedWorkshop(before, after);
        if (workshop == null) {
            workshop = new MesMdWorkshopDO();
            workshop.setArea(BigDecimal.ZERO);
        }
        workshop.setCode(after.getCode());
        workshop.setName(after.getName());
        workshop.setChargeUserId(after.getPrincipalUserId());
        workshop.setStatus(mapWorkshopStatus(after.getStatus()));
        workshop.setRemark(buildWorkshopRemark(after));
        if (workshop.getId() == null) {
            workshopMapper.insert(workshop);
        } else {
            workshopMapper.updateById(workshop);
        }
    }

    private void disableWorkshop(YianStationDO station) {
        MesMdWorkshopDO workshop = findMatchedWorkshop(station, station);
        if (workshop == null) {
            return;
        }
        workshop.setStatus(1);
        workshop.setRemark(buildDeletedWorkshopRemark(station, workshop.getRemark()));
        workshopMapper.updateById(workshop);
    }

    private MesMdWorkshopDO findMatchedWorkshop(YianStationDO before, YianStationDO after) {
        MesMdWorkshopDO workshop = findWorkshopByCode(after != null ? after.getCode() : null);
        if (workshop != null) {
            return workshop;
        }
        workshop = findWorkshopByCode(before != null ? before.getCode() : null);
        if (workshop != null) {
            return workshop;
        }
        workshop = findWorkshopByName(after != null ? after.getName() : null);
        if (workshop != null) {
            return workshop;
        }
        return findWorkshopByName(before != null ? before.getName() : null);
    }

    private MesMdWorkshopDO findWorkshopByCode(String code) {
        return StrUtil.isBlank(code) ? null : workshopMapper.selectByCode(code);
    }

    private MesMdWorkshopDO findWorkshopByName(String name) {
        return StrUtil.isBlank(name) ? null : workshopMapper.selectByName(name);
    }

    private Integer mapWorkshopStatus(Integer stationStatus) {
        return ObjUtil.equal(stationStatus, 0) ? 0 : 1;
    }

    private String buildWorkshopRemark(YianStationDO station) {
        StringBuilder builder = new StringBuilder();
        appendRemarkSegment(builder, "区域", station.getRegion());
        appendRemarkSegment(builder, "服务范围", station.getServiceScope());
        appendRemarkSegment(builder, "备注", station.getRemark());
        return builder.toString();
    }

    private void appendRemarkSegment(StringBuilder builder, String label, String value) {
        if (StrUtil.isBlank(value)) {
            return;
        }
        if (builder.length() > 0) {
            builder.append("；");
        }
        builder.append(label).append("：").append(value);
    }

    private String buildDeletedWorkshopRemark(YianStationDO station, String originalRemark) {
        String deletionRemark = "站点管理已删除：" + station.getName() + "(" + station.getCode() + ")";
        if (StrUtil.isBlank(originalRemark)) {
            return deletionRemark;
        }
        if (StrUtil.contains(originalRemark, deletionRemark)) {
            return originalRemark;
        }
        return originalRemark + "；" + deletionRemark;
    }
}
