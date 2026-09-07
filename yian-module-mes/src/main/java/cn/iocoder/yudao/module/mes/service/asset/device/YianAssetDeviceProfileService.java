package cn.iocoder.yudao.module.mes.service.asset.device;

import cn.iocoder.yudao.module.mes.controller.admin.asset.device.vo.YianAssetDeviceProfileRespVO;

public interface YianAssetDeviceProfileService {

    /**
     * 获取设备画像（聚合维修、保养、点检、电池等信息）
     *
     * @param machineryId 设备ID
     * @return 设备画像
     */
    YianAssetDeviceProfileRespVO getDeviceProfile(Long machineryId);
}
