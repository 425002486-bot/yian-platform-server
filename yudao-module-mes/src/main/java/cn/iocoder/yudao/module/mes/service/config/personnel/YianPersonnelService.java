package cn.iocoder.yudao.module.mes.service.config.personnel;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo.YianPersonnelPageReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo.YianPersonnelSaveReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.personnel.YianPersonnelExtDO;
import jakarta.validation.Valid;

public interface YianPersonnelService {

    Long createPersonnel(@Valid YianPersonnelSaveReqVO createReqVO);
    void updatePersonnel(@Valid YianPersonnelSaveReqVO updateReqVO);
    void deletePersonnel(Long id);
    YianPersonnelExtDO getPersonnel(Long id);
    PageResult<YianPersonnelExtDO> getPersonnelPage(YianPersonnelPageReqVO pageReqVO);

}
