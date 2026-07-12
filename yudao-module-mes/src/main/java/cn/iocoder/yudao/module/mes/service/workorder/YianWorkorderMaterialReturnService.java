package cn.iocoder.yudao.module.mes.service.workorder;

import cn.iocoder.yudao.module.mes.controller.admin.workorder.vo.YianWorkorderReturnMaterialReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.workorder.vo.YianWorkorderReturnMaterialRespVO;
import jakarta.validation.Valid;

public interface YianWorkorderMaterialReturnService {

    YianWorkorderReturnMaterialRespVO submitReturnMaterial(@Valid YianWorkorderReturnMaterialReqVO reqVO);
}
