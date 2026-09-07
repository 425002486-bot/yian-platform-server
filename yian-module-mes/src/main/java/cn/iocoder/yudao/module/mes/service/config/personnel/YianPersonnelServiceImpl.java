package cn.iocoder.yudao.module.mes.service.config.personnel;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo.YianPersonnelPageReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.config.personnel.vo.YianPersonnelSaveReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.config.personnel.YianPersonnelExtDO;
import cn.iocoder.yudao.module.mes.dal.mysql.config.personnel.YianPersonnelExtMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Service
@Validated
public class YianPersonnelServiceImpl implements YianPersonnelService {

    @Resource
    private YianPersonnelExtMapper personnelMapper;

    @Override
    public Long createPersonnel(YianPersonnelSaveReqVO createReqVO) {
        if (personnelMapper.selectByUserId(createReqVO.getUserId()) != null) {
            throw exception(new ErrorCode(1_040_900_000, "该用户已绑定人员信息"));
        }
        YianPersonnelExtDO ext = BeanUtils.toBean(createReqVO, YianPersonnelExtDO.class);
        personnelMapper.insert(ext);
        return ext.getId();
    }

    @Override
    public void updatePersonnel(YianPersonnelSaveReqVO updateReqVO) {
        validateExists(updateReqVO.getId());
        YianPersonnelExtDO updateObj = BeanUtils.toBean(updateReqVO, YianPersonnelExtDO.class);
        personnelMapper.updateById(updateObj);
    }

    @Override
    public void deletePersonnel(Long id) {
        validateExists(id);
        personnelMapper.deleteById(id);
    }

    @Override
    public YianPersonnelExtDO getPersonnel(Long id) {
        return personnelMapper.selectById(id);
    }

    @Override
    public PageResult<YianPersonnelExtDO> getPersonnelPage(YianPersonnelPageReqVO pageReqVO) {
        return personnelMapper.selectPage(pageReqVO);
    }

    private void validateExists(Long id) {
        if (personnelMapper.selectById(id) == null) {
            throw exception(new ErrorCode(1_040_900_001, "人员记录不存在"));
        }
    }
}
