package cn.iocoder.yudao.module.mes.controller.admin.workorder;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.test.core.ut.BaseMockitoUnitTest;
import cn.iocoder.yudao.module.mes.controller.admin.workorder.vo.YianWorkorderReturnMaterialReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.workorder.vo.YianWorkorderReturnMaterialRespVO;
import cn.iocoder.yudao.module.mes.service.workorder.YianWorkorderMaterialReturnService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class YianWorkorderMaterialReturnControllerTest extends BaseMockitoUnitTest {

    @InjectMocks
    private YianWorkorderMaterialReturnController controller;

    @Mock
    private YianWorkorderMaterialReturnService materialReturnService;

    @Test
    public void testSubmitReturnMaterial() {
        YianWorkorderReturnMaterialReqVO reqVO = new YianWorkorderReturnMaterialReqVO();
        reqVO.setWorkorderId(105L);
        reqVO.setOrderNo("WO-20260708-001");

        YianWorkorderReturnMaterialRespVO respVO = new YianWorkorderReturnMaterialRespVO();
        respVO.setIssueId(88L);
        respVO.setIssueCode("RT-WO-20260708-001-101010");
        when(materialReturnService.submitReturnMaterial(reqVO)).thenReturn(respVO);

        CommonResult<YianWorkorderReturnMaterialRespVO> result = controller.submitReturnMaterial(reqVO);

        assertEquals(0, result.getCode());
        assertSame(respVO, result.getData());
        verify(materialReturnService).submitReturnMaterial(reqVO);
    }
}
