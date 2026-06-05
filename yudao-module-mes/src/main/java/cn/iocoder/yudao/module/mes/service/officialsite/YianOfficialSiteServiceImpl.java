package cn.iocoder.yudao.module.mes.service.officialsite;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.mes.controller.app.officialsite.vo.YianOfficialSiteLeadCreateReqVO;
import cn.iocoder.yudao.module.mes.controller.app.officialsite.vo.YianOfficialSiteOverviewRespVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.officialsite.YianOfficialSiteLeadDO;
import cn.iocoder.yudao.module.mes.dal.mysql.asset.battery.YianAssetBatteryMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.config.station.YianStationMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.dv.machinery.MesDvMachineryMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.dv.repair.MesDvRepairMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.officialsite.YianOfficialSiteLeadMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.pro.workorder.MesProWorkOrderMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class YianOfficialSiteServiceImpl implements YianOfficialSiteService {

    @Resource
    private MesDvMachineryMapper machineryMapper;
    @Resource
    private YianAssetBatteryMapper batteryMapper;
    @Resource
    private MesProWorkOrderMapper workOrderMapper;
    @Resource
    private YianStationMapper stationMapper;
    @Resource
    private MesDvRepairMapper repairMapper;
    @Resource
    private YianOfficialSiteLeadMapper leadMapper;

    @Override
    public YianOfficialSiteOverviewRespVO getOverview() {
        YianOfficialSiteOverviewRespVO overview = new YianOfficialSiteOverviewRespVO();
        overview.setMachineryCount(machineryMapper.selectCount());
        overview.setBatteryCount(batteryMapper.selectCount());
        overview.setWorkOrderCount(workOrderMapper.selectCount());
        overview.setStationCount(stationMapper.selectCount());
        overview.setRepairCount(repairMapper.selectCount());
        return overview;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createLead(YianOfficialSiteLeadCreateReqVO createReqVO) {
        YianOfficialSiteLeadDO lead = BeanUtils.toBean(createReqVO, YianOfficialSiteLeadDO.class);
        lead.setStatus(0);
        leadMapper.insert(lead);
        return lead.getId();
    }
}
