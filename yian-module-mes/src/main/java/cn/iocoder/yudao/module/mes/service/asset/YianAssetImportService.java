package cn.iocoder.yudao.module.mes.service.asset;

import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportExcelVO;
import cn.iocoder.yudao.module.mes.controller.admin.asset.vo.YianAssetImportRespVO;

import java.util.List;

public interface YianAssetImportService {

    YianAssetImportRespVO importAssetList(List<YianAssetImportExcelVO> importAssetList, boolean updateSupport);
}
