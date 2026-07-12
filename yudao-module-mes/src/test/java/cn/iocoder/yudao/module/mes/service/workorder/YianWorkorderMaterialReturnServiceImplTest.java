package cn.iocoder.yudao.module.mes.service.workorder;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.test.core.ut.BaseMockitoUnitTest;
import cn.iocoder.yudao.module.mes.controller.admin.workorder.vo.YianWorkorderReturnMaterialReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.workorder.vo.YianWorkorderReturnMaterialRespVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.wm.returnissue.MesWmReturnIssueDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.workorder.YianWorkorderDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.workorder.YianWorkorderMaterialDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.workorder.YianWorkorderReturnRecordDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.workorder.YianWorkorderTimelineDO;
import cn.iocoder.yudao.module.mes.dal.mysql.wm.returnissue.MesWmReturnIssueMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.workorder.YianWorkorderMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.workorder.YianWorkorderMaterialMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.workorder.YianWorkorderReturnRecordMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.workorder.YianWorkorderTimelineMapper;
import cn.iocoder.yudao.module.mes.service.wm.returnissue.MesWmReturnIssueDetailService;
import cn.iocoder.yudao.module.mes.service.wm.returnissue.MesWmReturnIssueLineService;
import cn.iocoder.yudao.module.mes.service.wm.returnissue.MesWmReturnIssueService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class YianWorkorderMaterialReturnServiceImplTest extends BaseMockitoUnitTest {

    @InjectMocks
    private YianWorkorderMaterialReturnServiceImpl service;

    @Mock
    private YianWorkorderMapper workorderMapper;
    @Mock
    private YianWorkorderMaterialMapper workorderMaterialMapper;
    @Mock
    private YianWorkorderReturnRecordMapper returnRecordMapper;
    @Mock
    private YianWorkorderTimelineMapper timelineMapper;
    @Mock
    private MesWmReturnIssueMapper returnIssueMapper;
    @Mock
    private MesWmReturnIssueLineService returnIssueLineService;
    @Mock
    private MesWmReturnIssueDetailService returnIssueDetailService;
    @Mock
    private MesWmReturnIssueService returnIssueService;

    @Test
    public void testSubmitReturnMaterial_success() {
        YianWorkorderReturnMaterialReqVO reqVO = buildReqVO();

        when(workorderMapper.selectById(105L))
                .thenReturn(YianWorkorderDO.builder().id(105L).orderNo("WO-20260708-001").status("repairing").build());
        when(workorderMaterialMapper.selectListByWorkorderId(105L))
                .thenReturn(List.of(YianWorkorderMaterialDO.builder()
                        .id(1L).workorderId(105L).name("减震球").spec("M30T").quantity(4).status("picked").build()));
        when(returnRecordMapper.selectListByWorkorderId(105L))
                .thenReturn(List.of(YianWorkorderReturnRecordDO.builder()
                        .id(7L).workorderId(105L).itemName("减震球").itemSpec("M30T")
                        .returnQuantity(BigDecimal.ONE).build()));
        doAnswer(invocation -> {
            MesWmReturnIssueDO issue = invocation.getArgument(0);
            issue.setId(88L);
            return 1;
        }).when(returnIssueMapper).insert(any(MesWmReturnIssueDO.class));
        when(returnIssueLineService.createReturnIssueLine(any())).thenReturn(990L);

        YianWorkorderReturnMaterialRespVO respVO = service.submitReturnMaterial(reqVO);

        assertEquals(88L, respVO.getIssueId());
        assertEquals(1, respVO.getItems().size());
        assertEquals("减震球", respVO.getItems().get(0).getItemName());
        verify(returnIssueService).submitReturnIssue(88L);
        verify(returnIssueService).stockReturnIssue(88L);
        verify(returnIssueService).finishReturnIssue(88L);

        ArgumentCaptor<YianWorkorderReturnRecordDO> returnCaptor = ArgumentCaptor.forClass(YianWorkorderReturnRecordDO.class);
        verify(returnRecordMapper).insert(returnCaptor.capture());
        assertEquals("多领未使用", returnCaptor.getValue().getReturnReason());
        assertEquals(new BigDecimal("1"), returnCaptor.getValue().getReturnQuantity());

        ArgumentCaptor<YianWorkorderTimelineDO> timelineCaptor = ArgumentCaptor.forClass(YianWorkorderTimelineDO.class);
        verify(timelineMapper).insert(timelineCaptor.capture());
        assertEquals("repairing", timelineCaptor.getValue().getStage());
        assertEquals("退料登记", timelineCaptor.getValue().getTitle());
    }

    @Test
    public void testSubmitReturnMaterial_rejectWhenExceedRemainingQuantity() {
        YianWorkorderReturnMaterialReqVO reqVO = buildReqVO();
        reqVO.getItems().get(0).setReturnQuantity(new BigDecimal("3"));

        when(workorderMapper.selectById(105L))
                .thenReturn(YianWorkorderDO.builder().id(105L).orderNo("WO-20260708-001").status("repairing").build());
        when(workorderMaterialMapper.selectListByWorkorderId(105L))
                .thenReturn(List.of(YianWorkorderMaterialDO.builder()
                        .id(1L).workorderId(105L).name("减震球").spec("M30T").quantity(3).status("picked").build()));
        when(returnRecordMapper.selectListByWorkorderId(105L))
                .thenReturn(List.of(YianWorkorderReturnRecordDO.builder()
                        .id(7L).workorderId(105L).itemName("减震球").itemSpec("M30T")
                        .returnQuantity(new BigDecimal("1")).build()));

        assertThrows(ServiceException.class, () -> service.submitReturnMaterial(reqVO));
        verify(returnIssueMapper, never()).insert(any(MesWmReturnIssueDO.class));
        verify(returnIssueService, never()).submitReturnIssue(any());
    }

    @Test
    public void testSubmitReturnMaterial_backfillLegacyPickedMaterialsByOrderNo() {
        YianWorkorderReturnMaterialReqVO reqVO = buildReqVO();
        reqVO.setWorkorderId(999L);
        reqVO.setOrderNo("WO-20260708-LEGACY");
        reqVO.getItems().get(0).setPickedQuantity(new BigDecimal("2"));

        when(workorderMapper.selectById(999L)).thenReturn(null);
        when(workorderMapper.selectByOrderNo("WO-20260708-LEGACY"))
                .thenReturn(YianWorkorderDO.builder().id(201L).orderNo("WO-20260708-LEGACY").status("repairing").build());
        when(workorderMaterialMapper.selectListByWorkorderId(201L))
                .thenReturn(List.of(), List.of(YianWorkorderMaterialDO.builder()
                        .id(9L).workorderId(201L).name("减震球").spec("M30T").quantity(2).status("picked").build()));
        when(returnRecordMapper.selectListByWorkorderId(201L)).thenReturn(List.of());
        doAnswer(invocation -> {
            MesWmReturnIssueDO issue = invocation.getArgument(0);
            issue.setId(66L);
            return 1;
        }).when(returnIssueMapper).insert(any(MesWmReturnIssueDO.class));
        when(returnIssueLineService.createReturnIssueLine(any())).thenReturn(991L);

        YianWorkorderReturnMaterialRespVO respVO = service.submitReturnMaterial(reqVO);

        assertEquals(66L, respVO.getIssueId());
        ArgumentCaptor<YianWorkorderMaterialDO> materialCaptor = ArgumentCaptor.forClass(YianWorkorderMaterialDO.class);
        verify(workorderMaterialMapper).insert(materialCaptor.capture());
        assertEquals(Long.valueOf(201L), materialCaptor.getValue().getWorkorderId());
        assertEquals("减震球", materialCaptor.getValue().getName());
        assertEquals(Integer.valueOf(2), materialCaptor.getValue().getQuantity());
    }

    private YianWorkorderReturnMaterialReqVO buildReqVO() {
        YianWorkorderReturnMaterialReqVO reqVO = new YianWorkorderReturnMaterialReqVO();
        reqVO.setWorkorderId(105L);
        reqVO.setOrderNo("WO-20260708-001");
        reqVO.setOperator("王工");
        reqVO.setReason("多领未使用");

        YianWorkorderReturnMaterialReqVO.Allocation allocation = new YianWorkorderReturnMaterialReqVO.Allocation();
        allocation.setMaterialStockId(301L);
        allocation.setItemId(2001L);
        allocation.setQuantity(new BigDecimal("1"));
        allocation.setBatchId(501L);
        allocation.setBatchCode("BATCH-001");
        allocation.setWarehouseId(11L);
        allocation.setLocationId(21L);
        allocation.setAreaId(31L);

        YianWorkorderReturnMaterialReqVO.Item item = new YianWorkorderReturnMaterialReqVO.Item();
        item.setItemId(2001L);
        item.setItemName("减震球");
        item.setItemSpec("M30T");
        item.setPickedQuantity(new BigDecimal("2"));
        item.setReturnQuantity(new BigDecimal("1"));
        item.setAllocations(List.of(allocation));

        reqVO.setItems(List.of(item));
        return reqVO;
    }
}
