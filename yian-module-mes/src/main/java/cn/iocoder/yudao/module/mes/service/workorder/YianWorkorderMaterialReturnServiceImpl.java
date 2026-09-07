package cn.iocoder.yudao.module.mes.service.workorder;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.yudao.module.mes.controller.admin.wm.returnissue.vo.detail.MesWmReturnIssueDetailSaveReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.wm.returnissue.vo.line.MesWmReturnIssueLineSaveReqVO;
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
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Validated
public class YianWorkorderMaterialReturnServiceImpl implements YianWorkorderMaterialReturnService {

    private static final DateTimeFormatter ISSUE_CODE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");

    @Resource
    private YianWorkorderMapper workorderMapper;
    @Resource
    private YianWorkorderMaterialMapper workorderMaterialMapper;
    @Resource
    private YianWorkorderReturnRecordMapper returnRecordMapper;
    @Resource
    private YianWorkorderTimelineMapper timelineMapper;
    @Resource
    private MesWmReturnIssueMapper returnIssueMapper;
    @Resource
    private MesWmReturnIssueLineService returnIssueLineService;
    @Resource
    private MesWmReturnIssueDetailService returnIssueDetailService;
    @Resource
    private MesWmReturnIssueService returnIssueService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public YianWorkorderReturnMaterialRespVO submitReturnMaterial(YianWorkorderReturnMaterialReqVO reqVO) {
        YianWorkorderDO workorder = validateWorkorder(reqVO);

        List<YianWorkorderMaterialDO> pickedMaterials = workorderMaterialMapper.selectListByWorkorderId(workorder.getId());
        backfillLegacyPickedMaterials(workorder, reqVO, pickedMaterials);
        pickedMaterials = workorderMaterialMapper.selectListByWorkorderId(workorder.getId());

        Map<String, Integer> pickedQuantityMap = pickedMaterials.stream()
                .collect(Collectors.toMap(this::materialKey,
                        item -> item.getQuantity() == null ? 0 : item.getQuantity(),
                        Integer::sum));
        Map<String, BigDecimal> returnedQuantityMap = returnRecordMapper.selectListByWorkorderId(workorder.getId()).stream()
                .collect(Collectors.toMap(this::returnRecordKey,
                        YianWorkorderReturnRecordDO::getReturnQuantity,
                        BigDecimal::add));

        validateReturnItems(reqVO, pickedQuantityMap, returnedQuantityMap);

        LocalDateTime now = LocalDateTime.now();
        MesWmReturnIssueDO issue = MesWmReturnIssueDO.builder()
                .code(buildIssueCode(workorder.getOrderNo(), now))
                .name(workorder.getOrderNo() + "退料入库")
                .workOrderId(null)
                .type(1)
                .returnDate(now)
                .status(0)
                .remark(workorder.getOrderNo() + " / " + reqVO.getReason())
                .build();
        returnIssueMapper.insert(issue);

        List<YianWorkorderReturnMaterialRespVO.Item> respItems = new ArrayList<>();
        List<String> returnedSummaries = new ArrayList<>();
        for (YianWorkorderReturnMaterialReqVO.Item item : reqVO.getItems()) {
            Long lineId = createReturnIssueLine(issue.getId(), reqVO.getReason(), item);
            createReturnIssueDetails(issue.getId(), lineId, reqVO.getReason(), item);
            createReturnRecord(workorder, reqVO, issue, now, item);

            YianWorkorderReturnMaterialRespVO.Item respItem = new YianWorkorderReturnMaterialRespVO.Item();
            respItem.setItemId(item.getItemId());
            respItem.setItemName(item.getItemName());
            respItem.setItemSpec(item.getItemSpec());
            respItem.setReturnQuantity(item.getReturnQuantity());
            respItem.setReturnReason(reqVO.getReason());
            respItems.add(respItem);
            returnedSummaries.add(item.getItemName() + " x" + item.getReturnQuantity().stripTrailingZeros().toPlainString());
        }

        returnIssueService.submitReturnIssue(issue.getId());
        returnIssueService.stockReturnIssue(issue.getId());
        returnIssueService.finishReturnIssue(issue.getId());

        timelineMapper.insert(YianWorkorderTimelineDO.builder()
                .workorderId(workorder.getId())
                .stage(resolveTimelineStage(workorder.getStatus()))
                .title("退料登记")
                .detail("退回 " + String.join("、", returnedSummaries) + "，原因：" + reqVO.getReason())
                .operator(reqVO.getOperator())
                .operatedAt(now)
                .build());

        YianWorkorderReturnMaterialRespVO respVO = new YianWorkorderReturnMaterialRespVO();
        respVO.setIssueId(issue.getId());
        respVO.setIssueCode(issue.getCode());
        respVO.setReturnedAt(now);
        respVO.setItems(respItems);
        return respVO;
    }

    private void backfillLegacyPickedMaterials(YianWorkorderDO workorder,
                                               YianWorkorderReturnMaterialReqVO reqVO,
                                               List<YianWorkorderMaterialDO> pickedMaterials) {
        Map<String, YianWorkorderMaterialDO> existingMap = pickedMaterials.stream()
                .collect(Collectors.toMap(this::materialKey, item -> item, (left, right) -> left));
        for (YianWorkorderReturnMaterialReqVO.Item item : reqVO.getItems()) {
            String key = itemKey(item.getItemName(), item.getItemSpec());
            if (existingMap.containsKey(key)) {
                continue;
            }
            if (item.getPickedQuantity() == null || item.getPickedQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            if (item.getPickedQuantity().compareTo(item.getReturnQuantity()) < 0) {
                throw ServiceExceptionUtil.invalidParamException(
                        "物料 {} 的已领数量异常，无法兼容补齐后端领料记录", item.getItemName());
            }

            YianWorkorderMaterialDO materialDO = YianWorkorderMaterialDO.builder()
                    .workorderId(workorder.getId())
                    .name(item.getItemName())
                    .spec(item.getItemSpec())
                    .quantity(item.getPickedQuantity().intValue())
                    .status("picked")
                    .build();
            workorderMaterialMapper.insert(materialDO);
            existingMap.put(key, materialDO);
        }
    }

    private void validateReturnItems(YianWorkorderReturnMaterialReqVO reqVO,
                                     Map<String, Integer> pickedQuantityMap,
                                     Map<String, BigDecimal> returnedQuantityMap) {
        for (YianWorkorderReturnMaterialReqVO.Item item : reqVO.getItems()) {
            validateReturnItem(item, pickedQuantityMap, returnedQuantityMap);
            BigDecimal allocationTotal = item.getAllocations().stream()
                    .map(YianWorkorderReturnMaterialReqVO.Allocation::getQuantity)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (allocationTotal.compareTo(item.getReturnQuantity()) != 0) {
                throw ServiceExceptionUtil.invalidParamException(
                        "物料 {} 的退料分配数量之和必须与本次退料数量一致", item.getItemName());
            }
        }
    }

    private Long createReturnIssueLine(Long issueId, String reason, YianWorkorderReturnMaterialReqVO.Item item) {
        MesWmReturnIssueLineSaveReqVO lineReqVO = new MesWmReturnIssueLineSaveReqVO();
        lineReqVO.setIssueId(issueId);
        lineReqVO.setItemId(item.getItemId());
        lineReqVO.setQuantity(item.getReturnQuantity());
        lineReqVO.setMaterialStockId(item.getAllocations().get(0).getMaterialStockId());
        lineReqVO.setBatchId(item.getAllocations().get(0).getBatchId());
        lineReqVO.setBatchCode(item.getAllocations().get(0).getBatchCode());
        lineReqVO.setRqcCheckFlag(Boolean.FALSE);
        lineReqVO.setRemark(reason);
        return returnIssueLineService.createReturnIssueLine(lineReqVO);
    }

    private void createReturnIssueDetails(Long issueId, Long lineId, String reason,
                                          YianWorkorderReturnMaterialReqVO.Item item) {
        for (YianWorkorderReturnMaterialReqVO.Allocation allocation : item.getAllocations()) {
            MesWmReturnIssueDetailSaveReqVO detailReqVO = new MesWmReturnIssueDetailSaveReqVO();
            detailReqVO.setLineId(lineId);
            detailReqVO.setIssueId(issueId);
            detailReqVO.setItemId(allocation.getItemId());
            detailReqVO.setMaterialStockId(allocation.getMaterialStockId());
            detailReqVO.setQuantity(allocation.getQuantity());
            detailReqVO.setBatchId(allocation.getBatchId());
            detailReqVO.setBatchCode(allocation.getBatchCode());
            detailReqVO.setWarehouseId(allocation.getWarehouseId());
            detailReqVO.setLocationId(allocation.getLocationId());
            detailReqVO.setAreaId(allocation.getAreaId());
            detailReqVO.setRemark(reason);
            returnIssueDetailService.createReturnIssueDetail(detailReqVO);
        }
    }

    private void createReturnRecord(YianWorkorderDO workorder, YianWorkorderReturnMaterialReqVO reqVO,
                                    MesWmReturnIssueDO issue, LocalDateTime now,
                                    YianWorkorderReturnMaterialReqVO.Item item) {
        returnRecordMapper.insert(YianWorkorderReturnRecordDO.builder()
                .workorderId(workorder.getId())
                .orderNo(workorder.getOrderNo())
                .issueId(issue.getId())
                .issueCode(issue.getCode())
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .itemSpec(item.getItemSpec())
                .returnQuantity(item.getReturnQuantity())
                .returnReason(reqVO.getReason())
                .operatorName(reqVO.getOperator())
                .returnedAt(now)
                .build());
    }

    private YianWorkorderDO validateWorkorder(YianWorkorderReturnMaterialReqVO reqVO) {
        YianWorkorderDO workorder = workorderMapper.selectById(reqVO.getWorkorderId());
        if (workorder != null && Objects.equals(workorder.getOrderNo(), reqVO.getOrderNo())) {
            return validateReqItemsExist(reqVO, workorder);
        }
        workorder = workorderMapper.selectByOrderNo(reqVO.getOrderNo());
        if (workorder == null) {
            throw ServiceExceptionUtil.invalidParamException("未找到匹配的工单，无法提交退料");
        }
        return validateReqItemsExist(reqVO, workorder);
    }

    private YianWorkorderDO validateReqItemsExist(YianWorkorderReturnMaterialReqVO reqVO, YianWorkorderDO workorder) {
        if (CollUtil.isEmpty(reqVO.getItems())) {
            throw ServiceExceptionUtil.invalidParamException("至少需要一条退料项");
        }
        return workorder;
    }

    private void validateReturnItem(YianWorkorderReturnMaterialReqVO.Item item,
                                    Map<String, Integer> pickedQuantityMap,
                                    Map<String, BigDecimal> returnedQuantityMap) {
        String key = itemKey(item.getItemName(), item.getItemSpec());
        Integer pickedQuantity = pickedQuantityMap.getOrDefault(key, 0);
        if (pickedQuantity <= 0) {
            throw ServiceExceptionUtil.invalidParamException(
                    "物料 {} 不属于当前工单已领料项，不能退料", item.getItemName());
        }
        BigDecimal alreadyReturned = returnedQuantityMap.getOrDefault(key, BigDecimal.ZERO);
        BigDecimal remaining = BigDecimal.valueOf(pickedQuantity).subtract(alreadyReturned);
        if (remaining.compareTo(item.getReturnQuantity()) < 0) {
            throw ServiceExceptionUtil.invalidParamException(
                    "物料 {} 可退数量不足，当前最多还能退 {}", item.getItemName(),
                    remaining.stripTrailingZeros().toPlainString());
        }
    }

    private String resolveTimelineStage(String currentStatus) {
        if ("inspecting".equals(currentStatus) || "releasing".equals(currentStatus) || "completed".equals(currentStatus)) {
            return currentStatus;
        }
        return "repairing";
    }

    private String buildIssueCode(String orderNo, LocalDateTime now) {
        return "RT-" + orderNo + "-" + now.format(ISSUE_CODE_TIME_FORMATTER);
    }

    private String materialKey(YianWorkorderMaterialDO materialDO) {
        return itemKey(materialDO.getName(), materialDO.getSpec());
    }

    private String returnRecordKey(YianWorkorderReturnRecordDO recordDO) {
        return itemKey(recordDO.getItemName(), recordDO.getItemSpec());
    }

    private String itemKey(String itemName, String itemSpec) {
        return (itemName == null ? "" : itemName.trim()) + "||" + (itemSpec == null ? "" : itemSpec.trim());
    }
}
