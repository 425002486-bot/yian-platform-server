package cn.iocoder.yudao.module.mes.service.officialsite;

import cn.iocoder.yudao.module.mes.controller.app.officialsite.vo.YianOfficialSiteLeadCreateReqVO;
import cn.iocoder.yudao.module.mes.controller.app.officialsite.vo.YianOfficialSiteOverviewRespVO;

public interface YianOfficialSiteService {

    YianOfficialSiteOverviewRespVO getOverview();

    Long createLead(YianOfficialSiteLeadCreateReqVO createReqVO);
}
