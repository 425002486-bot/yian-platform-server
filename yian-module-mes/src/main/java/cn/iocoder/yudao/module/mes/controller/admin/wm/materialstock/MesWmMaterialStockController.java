package cn.iocoder.yudao.module.mes.controller.admin.wm.materialstock;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.mes.controller.admin.md.item.vo.type.MesMdItemTypeListReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.wm.materialstock.vo.MesWmPickReturnRecordRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.wm.materialstock.vo.MesWmMaterialStockInboundReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.wm.materialstock.vo.YianSparePartImportExcelVO;
import cn.iocoder.yudao.module.mes.controller.admin.md.item.vo.MesMdItemSaveReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.wm.miscissue.MesWmMiscIssueDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.wm.miscissue.MesWmMiscIssueLineDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.wm.productissue.MesWmProductIssueDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.wm.productissue.MesWmProductIssueLineDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.wm.returnissue.MesWmReturnIssueDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.wm.returnissue.MesWmReturnIssueLineDO;
import cn.iocoder.yudao.module.mes.dal.mysql.wm.miscissue.MesWmMiscIssueLineMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.wm.miscissue.MesWmMiscIssueMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.wm.productissue.MesWmProductIssueLineMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.wm.productissue.MesWmProductIssueMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.wm.returnissue.MesWmReturnIssueLineMapper;
import cn.iocoder.yudao.module.mes.dal.mysql.wm.returnissue.MesWmReturnIssueMapper;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import cn.iocoder.yudao.module.mes.controller.admin.wm.materialstock.vo.MesWmMaterialStockPageReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.wm.materialstock.vo.MesWmMaterialStockRespVO;
import cn.iocoder.yudao.module.mes.controller.admin.wm.materialstock.vo.MesWmMaterialStockFreezeReqVO;
import cn.iocoder.yudao.module.mes.dal.dataobject.md.item.MesMdItemDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.md.item.MesMdItemTypeDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.md.unitmeasure.MesMdUnitMeasureDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.md.vendor.MesMdVendorDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.wm.materialstock.MesWmMaterialStockDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.wm.warehouse.MesWmWarehouseAreaDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.wm.warehouse.MesWmWarehouseDO;
import cn.iocoder.yudao.module.mes.dal.dataobject.wm.warehouse.MesWmWarehouseLocationDO;
import cn.iocoder.yudao.module.mes.service.md.item.MesMdItemService;
import cn.iocoder.yudao.module.mes.service.md.item.MesMdItemTypeService;
import cn.iocoder.yudao.module.mes.service.md.unitmeasure.MesMdUnitMeasureService;
import cn.iocoder.yudao.module.mes.service.md.vendor.MesMdVendorService;
import cn.iocoder.yudao.module.mes.service.wm.materialstock.MesWmMaterialStockService;
import cn.iocoder.yudao.module.mes.service.wm.warehouse.MesWmWarehouseAreaService;
import cn.iocoder.yudao.module.mes.service.wm.warehouse.MesWmWarehouseLocationService;
import cn.iocoder.yudao.module.mes.service.wm.warehouse.MesWmWarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.mes.enums.LogRecordConstants.*;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - MES 库存台账")
@RestController
@RequestMapping("/mes/wm/material-stock")
@Validated
public class MesWmMaterialStockController {

    @Resource
    private MesWmMaterialStockService materialStockService;
    @Resource
    private MesMdItemService itemService;
    @Resource
    private MesMdItemTypeService itemTypeService;
    @Resource
    private MesMdUnitMeasureService unitMeasureService;
    @Resource
    private MesWmWarehouseService warehouseService;
    @Resource
    private MesWmWarehouseLocationService locationService;
    @Resource
    private MesWmWarehouseAreaService areaService;
    @Resource
    private MesMdVendorService vendorService;
    @Resource
    private MesWmProductIssueMapper productIssueMapper;
    @Resource
    private MesWmProductIssueLineMapper productIssueLineMapper;
    @Resource
    private MesWmMiscIssueMapper miscIssueMapper;
    @Resource
    private MesWmMiscIssueLineMapper miscIssueLineMapper;
    @Resource
    private MesWmReturnIssueMapper returnIssueMapper;
    @Resource
    private MesWmReturnIssueLineMapper returnIssueLineMapper;
    @Resource
    private AdminUserApi adminUserApi;

    @GetMapping("/get")
    @Operation(summary = "获得库存记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mes:wm-material-stock:query')")
    public CommonResult<MesWmMaterialStockRespVO> getMaterialStock(@RequestParam("id") Long id) {
        MesWmMaterialStockDO stock = materialStockService.getMaterialStock(id);
        if (stock == null) {
            return success(null);
        }
        return success(buildRespVOList(Collections.singletonList(stock)).get(0));
    }

    @GetMapping("/page")
    @Operation(summary = "获得库存台账分页")
    @PreAuthorize("@ss.hasPermission('mes:wm-material-stock:query')")
    public CommonResult<PageResult<MesWmMaterialStockRespVO>> getMaterialStockPage(
            @Valid MesWmMaterialStockPageReqVO pageReqVO) {
        PageResult<MesWmMaterialStockDO> pageResult = materialStockService.getMaterialStockPage(pageReqVO);
        return success(new PageResult<>(buildRespVOList(pageResult.getList()), pageResult.getTotal()));
    }

    @PutMapping("/update-frozen")
    @Operation(summary = "更新库存冻结状态")
    @PreAuthorize("@ss.hasPermission('mes:wm-material-stock:update')")
    public CommonResult<Boolean> updateMaterialStockFrozen(
            @Valid @RequestBody MesWmMaterialStockFreezeReqVO updateReqVO) {
        materialStockService.updateMaterialStockFrozen(updateReqVO);
        return success(true);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出库存台账 Excel")
    @PreAuthorize("@ss.hasPermission('mes:wm-material-stock:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMaterialStockExcel(@Valid MesWmMaterialStockPageReqVO pageReqVO,
                                          HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        PageResult<MesWmMaterialStockDO> pageResult = materialStockService.getMaterialStockPage(pageReqVO);
        ExcelUtils.write(response, "库存台账.xls", "数据", MesWmMaterialStockRespVO.class,
                buildRespVOList(pageResult.getList()));
    }

    @PostMapping("/inbound")
    @Operation(summary = "入库登记")
    @PreAuthorize("@ss.hasPermission('mes:wm-material-stock:update')")
    @LogRecord(type = MES_SPARE_TYPE, subType = MES_SPARE_PICK_SUB_TYPE, bizNo = "{{#stock.id}}",
            success = "入库登记【{{#item.code}}】{{#item.name}}，数量 {{#reqVO.quantity}}，类型 {{#reqVO.inboundType}}")
    public CommonResult<Long> inbound(@Valid @RequestBody MesWmMaterialStockInboundReqVO reqVO) {
        // 1. 校验备件存在
        MesMdItemDO item = itemService.validateItemExists(reqVO.getItemId());

        // 2. MVP 单仓模式：默认使用 ID=1 的备件仓库
        Long warehouseId = reqVO.getWarehouseId() != null ? reqVO.getWarehouseId() : 1L;
        Long locationId = reqVO.getLocationId() != null ? reqVO.getLocationId() : 1L;
        Long areaId = reqVO.getAreaId() != null ? reqVO.getAreaId() : 1L;

        // 3. 获取或创建库存记录
        MesWmMaterialStockDO stock = materialStockService.getOrCreateMaterialStock(
                reqVO.getItemId(), warehouseId, locationId, areaId,
                null, reqVO.getBatchCode(), reqVO.getVendorId(), LocalDateTime.now());

        // 4. 增加库存数量
        materialStockService.updateMaterialStockQuantity(stock.getId(), reqVO.getQuantity(), false);

        // 5. 审计日志上下文
        LogRecordContext.putVariable("stock", stock);
        LogRecordContext.putVariable("item", item);
        return success(stock.getId());
    }

    @GetMapping("/item-simple-list")
    @Operation(summary = "获得备件精简列表（用于下拉选择）")
    public CommonResult<List<Map<String, Object>>> getItemSimpleList(
            @RequestParam(value = "keyword", required = false) String keyword) {
        List<MesMdItemDO> items;
        if (keyword != null && !keyword.isEmpty()) {
            items = itemService.getItemListByKeyword(keyword);
        } else {
            // 默认返回前 50 条
            items = itemService.getItemListByKeyword("");
        }
        return success(convertList(items, item -> {
            Map<String, Object> map = new java.util.LinkedHashMap<>();
            map.put("id", item.getId());
            map.put("code", item.getCode());
            map.put("name", item.getName());
            map.put("specification", item.getSpecification());
            return map;
        }));
    }

    @GetMapping("/pick-return-records")
    @Operation(summary = "获得领退料记录列表（统一视图）")
    @PreAuthorize("@ss.hasPermission('mes:wm-material-stock:query')")
    public CommonResult<List<MesWmPickReturnRecordRespVO>> getPickReturnRecords(
            @RequestParam(value = "issueCode", required = false) String issueCode,
            @RequestParam(value = "actionType", required = false) String actionType) {
        List<MesWmPickReturnRecordRespVO> result = new ArrayList<>();

        // 1. 查领料记录
        if (actionType == null || "pick".equals(actionType)) {
            List<MesWmProductIssueLineDO> pickLines = productIssueLineMapper.selectList();
            if (CollUtil.isNotEmpty(pickLines)) {
                // 关联领料单主表
                Set<Long> issueIds = convertSet(pickLines, MesWmProductIssueLineDO::getIssueId);
                Map<Long, MesWmProductIssueDO> issueMap = new HashMap<>();
                for (MesWmProductIssueDO issue : productIssueMapper.selectByIds(issueIds)) {
                    issueMap.put(issue.getId(), issue);
                }
                // 关联物料和用户
                Map<Long, MesMdItemDO> itemMap = itemService.getItemMap(
                        convertSet(pickLines, MesWmProductIssueLineDO::getItemId));
                Set<Long> userIds = pickLines.stream()
                        .map(l -> { try { return Long.parseLong(l.getCreator()); } catch (Exception e) { return null; } })
                        .filter(Objects::nonNull).collect(Collectors.toSet());
                Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(userIds);

                for (MesWmProductIssueLineDO line : pickLines) {
                    MesWmProductIssueDO issue = issueMap.get(line.getIssueId());
                    String code = issue != null ? issue.getCode() : "";
                    // 过滤单据编号
                    if (issueCode != null && !issueCode.isEmpty() && !code.contains(issueCode)) {
                        continue;
                    }
                    MesMdItemDO item = itemMap.get(line.getItemId());
                    Long creatorId = null;
                    try { creatorId = Long.parseLong(line.getCreator()); } catch (Exception ignored) {}
                    AdminUserRespDTO user = creatorId != null ? userMap.get(creatorId) : null;

                    result.add(MesWmPickReturnRecordRespVO.builder()
                            .id(line.getId())
                            .issueCode(code)
                            .workOrderCode(code) // MVP: 用领料单号代替工单号
                            .itemName(item != null ? item.getName() : "")
                            .itemCode(item != null ? item.getCode() : "")
                            .actionType("领料")
                            .quantity(line.getQuantity())
                            .operatorName(user != null ? user.getNickname() : line.getCreator())
                            .resultStatus("已出库")
                            .createTime(line.getCreateTime())
                            .build());
                }
            }

            List<MesWmMiscIssueLineDO> miscIssueLines = miscIssueLineMapper.selectList();
            if (CollUtil.isNotEmpty(miscIssueLines)) {
                Set<Long> issueIds = convertSet(miscIssueLines, MesWmMiscIssueLineDO::getIssueId);
                Map<Long, MesWmMiscIssueDO> issueMap = new HashMap<>();
                for (MesWmMiscIssueDO issue : miscIssueMapper.selectByIds(issueIds)) {
                    issueMap.put(issue.getId(), issue);
                }
                Map<Long, MesMdItemDO> itemMap = itemService.getItemMap(
                        convertSet(miscIssueLines, MesWmMiscIssueLineDO::getItemId));
                Set<Long> userIds = miscIssueLines.stream()
                        .map(l -> {
                            try {
                                return Long.parseLong(l.getCreator());
                            } catch (Exception e) {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
                Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(userIds);

                for (MesWmMiscIssueLineDO line : miscIssueLines) {
                    MesWmMiscIssueDO issue = issueMap.get(line.getIssueId());
                    if (issue == null) {
                        continue;
                    }
                    String code = issue.getCode() != null ? issue.getCode() : "";
                    String workOrderCode = issue.getSourceDocCode() != null ? issue.getSourceDocCode() : code;
                    if (issueCode != null
                            && !issueCode.isEmpty()
                            && !code.contains(issueCode)
                            && !workOrderCode.contains(issueCode)) {
                        continue;
                    }
                    MesMdItemDO item = itemMap.get(line.getItemId());
                    Long creatorId = null;
                    try {
                        creatorId = Long.parseLong(line.getCreator());
                    } catch (Exception ignored) {
                    }
                    AdminUserRespDTO user = creatorId != null ? userMap.get(creatorId) : null;

                    result.add(MesWmPickReturnRecordRespVO.builder()
                            .id(line.getId())
                            .issueCode(code)
                            .workOrderCode(workOrderCode)
                            .itemName(item != null ? item.getName() : "")
                            .itemCode(item != null ? item.getCode() : "")
                            .actionType("领料")
                            .quantity(line.getQuantity())
                            .operatorName(user != null ? user.getNickname() : line.getCreator())
                            .resultStatus("已出库")
                            .createTime(line.getCreateTime())
                            .build());
                }
            }
        }

        // 2. 查退料记录
        if (actionType == null || "return".equals(actionType)) {
            List<MesWmReturnIssueLineDO> returnLines = returnIssueLineMapper.selectList();
            if (CollUtil.isNotEmpty(returnLines)) {
                Set<Long> issueIds = convertSet(returnLines, MesWmReturnIssueLineDO::getIssueId);
                Map<Long, MesWmReturnIssueDO> issueMap = new HashMap<>();
                for (MesWmReturnIssueDO issue : returnIssueMapper.selectByIds(issueIds)) {
                    issueMap.put(issue.getId(), issue);
                }
                Map<Long, MesMdItemDO> itemMap = itemService.getItemMap(
                        convertSet(returnLines, MesWmReturnIssueLineDO::getItemId));
                Set<Long> userIds = returnLines.stream()
                        .map(l -> { try { return Long.parseLong(l.getCreator()); } catch (Exception e) { return null; } })
                        .filter(Objects::nonNull).collect(Collectors.toSet());
                Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(userIds);

                for (MesWmReturnIssueLineDO line : returnLines) {
                    MesWmReturnIssueDO issue = issueMap.get(line.getIssueId());
                    String code = issue != null ? issue.getCode() : "";
                    if (issueCode != null && !issueCode.isEmpty() && !code.contains(issueCode)) {
                        continue;
                    }
                    MesMdItemDO item = itemMap.get(line.getItemId());
                    Long creatorId = null;
                    try { creatorId = Long.parseLong(line.getCreator()); } catch (Exception ignored) {}
                    AdminUserRespDTO user = creatorId != null ? userMap.get(creatorId) : null;

                    result.add(MesWmPickReturnRecordRespVO.builder()
                            .id(line.getId())
                            .issueCode(code)
                            .workOrderCode(code)
                            .itemName(item != null ? item.getName() : "")
                            .itemCode(item != null ? item.getCode() : "")
                            .actionType("退料")
                            .quantity(line.getQuantity())
                            .operatorName(user != null ? user.getNickname() : line.getCreator())
                            .resultStatus("已入库")
                            .createTime(line.getCreateTime())
                            .build());
                }
            }
        }

        // 3. 按时间倒序
        result.sort(Comparator.comparing(MesWmPickReturnRecordRespVO::getCreateTime,
                Comparator.nullsLast(Comparator.reverseOrder())));
        return success(result);
    }

    @GetMapping("/vendor-simple-list")
    @Operation(summary = "获得供应商精简列表（用于下拉选择）")
    public CommonResult<List<Map<String, Object>>> getVendorSimpleList() {
        List<MesMdVendorDO> vendors = vendorService.getVendorSimpleList();
        return success(convertList(vendors, vendor -> {
            Map<String, Object> map = new java.util.LinkedHashMap<>();
            map.put("id", vendor.getId());
            map.put("code", vendor.getCode());
            map.put("name", vendor.getName());
            return map;
        }));
    }

    @GetMapping("/import-template")
    @Operation(summary = "下载备件导入模板")
    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        List<YianSparePartImportExcelVO> example = Collections.singletonList(
                YianSparePartImportExcelVO.builder()
                        .code("SP-BLD-001").name("标准桨叶套装").specification("M350 RTK 专用")
                        .categoryName("桨叶").vendorName("DJI官方").quantity(new BigDecimal("10"))
                        .minStock(new BigDecimal("5")).unitMeasureName("套")
                        .build());
        ExcelUtils.write(response, "备件导入模板.xls", "备件数据", YianSparePartImportExcelVO.class, example);
    }

    @PostMapping("/import-excel")
    @Operation(summary = "导入备件数据（含期初库存）")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否更新已有数据", example = "false")
    })
    @PreAuthorize("@ss.hasPermission('mes:wm-material-stock:update')")
    public CommonResult<Map<String, Object>> importSparePartExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport)
            throws Exception {
        List<YianSparePartImportExcelVO> list = readSparePartImportRows(file);
        MesMdItemTypeListReqVO itemTypeReqVO = new MesMdItemTypeListReqVO();
        itemTypeReqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<MesMdItemTypeDO> itemTypes = itemTypeService.getItemTypeList(itemTypeReqVO);
        List<MesMdUnitMeasureDO> unitMeasures = unitMeasureService
                .getUnitMeasureListByStatus(CommonStatusEnum.ENABLE.getStatus());
        List<MesMdVendorDO> vendors = vendorService.getVendorSimpleList();

        int createCount = 0, updateCount = 0, failCount = 0;
        List<String> failMessages = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            YianSparePartImportExcelVO row = list.get(i);
            int rowNum = i + 2; // Excel行号（第1行是表头）
            try {
                String code = trimToNull(row.getCode());
                String name = trimToNull(row.getName());
                String specification = trimToNull(row.getSpecification());
                String categoryName = trimToNull(row.getCategoryName());
                String vendorName = trimToNull(row.getVendorName());
                String unitMeasureName = trimToNull(row.getUnitMeasureName());

                // 1. 校验必填
                if (code == null) {
                    failMessages.add("第" + rowNum + "行：料号不能为空");
                    failCount++;
                    continue;
                }
                if (name == null) {
                    failMessages.add("第" + rowNum + "行：备件名称不能为空");
                    failCount++;
                    continue;
                }
                if (categoryName == null) {
                    failMessages.add("第" + rowNum + "行：分类不能为空");
                    failCount++;
                    continue;
                }
                if (unitMeasureName == null) {
                    failMessages.add("第" + rowNum + "行：计量单位不能为空");
                    failCount++;
                    continue;
                }

                MesMdItemTypeDO itemType = findItemTypeByName(itemTypes, categoryName);
                if (itemType == null) {
                    failMessages.add("第" + rowNum + "行：未找到分类[" + categoryName + "]");
                    failCount++;
                    continue;
                }

                MesMdUnitMeasureDO unitMeasure = findUnitMeasureByName(unitMeasures, unitMeasureName);
                if (unitMeasure == null) {
                    failMessages.add("第" + rowNum + "行：未找到计量单位[" + unitMeasureName + "]");
                    failCount++;
                    continue;
                }

                MesMdVendorDO vendor = null;
                if (vendorName != null) {
                    vendor = findVendorByName(vendors, vendorName);
                    if (vendor == null) {
                        failMessages.add("第" + rowNum + "行：未找到供应商[" + vendorName + "]");
                        failCount++;
                        continue;
                    }
                }

                // 2. 查找或创建物料
                List<MesMdItemDO> existItems = itemService.getItemListByKeyword(code);
                MesMdItemDO matchItem = existItems.stream()
                        .filter(item -> code.equals(item.getCode()))
                        .findFirst().orElse(null);

                if (matchItem != null && !updateSupport) {
                    failMessages.add("第" + rowNum + "行：料号[" + code + "]已存在，未开启更新模式");
                    failCount++;
                    continue;
                }

                Long itemId;
                if (matchItem != null) {
                    MesMdItemSaveReqVO updateReq = buildItemSaveReq(
                            matchItem.getId(), code, name, specification, itemType.getId(), unitMeasure.getId(),
                            row.getMinStock(), matchItem.getMaxStock(), matchItem.getHighValue(),
                            matchItem.getBatchFlag(), matchItem.getRemark());
                    itemService.updateItem(updateReq);
                    itemId = matchItem.getId();
                    updateCount++;
                } else {
                    MesMdItemSaveReqVO createReq = buildItemSaveReq(
                            null, code, name, specification, itemType.getId(), unitMeasure.getId(),
                            row.getMinStock(), null, false, true, "备件导入创建");
                    itemId = itemService.createItem(createReq);
                    itemService.updateItemStatus(itemId, CommonStatusEnum.ENABLE.getStatus());
                    createCount++;
                }

                // 3. 如果有库存数量，创建库存记录
                if (row.getQuantity() != null && row.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
                    MesWmMaterialStockDO stock = materialStockService.getOrCreateMaterialStock(
                            itemId, 1L, 1L, 1L, null, null, vendor != null ? vendor.getId() : null,
                            LocalDateTime.now());
                    materialStockService.updateMaterialStockQuantity(stock.getId(), row.getQuantity(), false);
                }
            } catch (Exception ex) {
                failMessages.add("第" + rowNum + "行：" + ex.getMessage());
                failCount++;
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("createCount", createCount);
        result.put("updateCount", updateCount);
        result.put("failCount", failCount);
        result.put("failMessages", failMessages);
        return success(result);
    }

    private List<YianSparePartImportExcelVO> readSparePartImportRows(MultipartFile file) throws IOException {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getNumberOfSheets() > 0 ? workbook.getSheetAt(0) : null;
            if (sheet == null) {
                return Collections.emptyList();
            }
            DataFormatter formatter = new DataFormatter();
            Row headerRow = sheet.getRow(sheet.getFirstRowNum());
            if (headerRow == null) {
                return Collections.emptyList();
            }
            Map<String, Integer> headerIndexMap = buildHeaderIndexMap(headerRow, formatter);
            List<YianSparePartImportExcelVO> rows = new ArrayList<>();
            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowBlank(row, formatter)) {
                    continue;
                }
                YianSparePartImportExcelVO vo = new YianSparePartImportExcelVO();
                vo.setCode(getCellString(row, headerIndexMap.get("料号"), formatter));
                vo.setName(getCellString(row, headerIndexMap.get("备件名称"), formatter));
                vo.setSpecification(getCellString(row, headerIndexMap.get("规格型号"), formatter));
                vo.setCategoryName(getCellString(row, headerIndexMap.get("分类"), formatter));
                vo.setVendorName(getCellString(row, headerIndexMap.get("供应商"), formatter));
                vo.setQuantity(getCellBigDecimal(row, headerIndexMap.get("当前库存"), formatter));
                vo.setMinStock(getCellBigDecimal(row, headerIndexMap.get("安全库存"), formatter));
                vo.setUnitMeasureName(getCellString(row, headerIndexMap.get("计量单位"), formatter));
                rows.add(vo);
            }
            return rows;
        } catch (Exception ex) {
            throw new IOException("导入文件解析失败，请确认模板格式正确", ex);
        }
    }

    private Map<String, Integer> buildHeaderIndexMap(Row headerRow, DataFormatter formatter) {
        Map<String, Integer> headerIndexMap = new LinkedHashMap<>();
        for (Cell cell : headerRow) {
            String header = trimToNull(formatter.formatCellValue(cell));
            if (header != null) {
                headerIndexMap.put(header, cell.getColumnIndex());
            }
        }
        return headerIndexMap;
    }

    private boolean isRowBlank(Row row, DataFormatter formatter) {
        for (Cell cell : row) {
            if (trimToNull(formatter.formatCellValue(cell)) != null) {
                return false;
            }
        }
        return true;
    }

    private String getCellString(Row row, Integer columnIndex, DataFormatter formatter) {
        if (columnIndex == null) {
            return null;
        }
        return trimToNull(formatter.formatCellValue(row.getCell(columnIndex)));
    }

    private BigDecimal getCellBigDecimal(Row row, Integer columnIndex, DataFormatter formatter) {
        String value = getCellString(row, columnIndex, formatter);
        if (value == null) {
            return null;
        }
        return new BigDecimal(value);
    }

    private MesMdItemSaveReqVO buildItemSaveReq(Long id, String code, String name, String specification,
                                                Long itemTypeId, Long unitMeasureId, BigDecimal minStock,
                                                BigDecimal maxStock, Boolean highValue, Boolean batchFlag,
                                                String remark) {
        MesMdItemSaveReqVO reqVO = new MesMdItemSaveReqVO();
        reqVO.setId(id);
        reqVO.setCode(code);
        reqVO.setName(name);
        reqVO.setSpecification(specification);
        reqVO.setItemTypeId(itemTypeId);
        reqVO.setUnitMeasureId(unitMeasureId);
        reqVO.setMinStock(minStock);
        reqVO.setMaxStock(maxStock);
        reqVO.setSafeStockFlag(minStock != null);
        reqVO.setHighValue(Boolean.TRUE.equals(highValue));
        reqVO.setBatchFlag(!Boolean.FALSE.equals(batchFlag));
        reqVO.setRemark(remark);
        return reqVO;
    }

    private MesMdItemTypeDO findItemTypeByName(List<MesMdItemTypeDO> itemTypes, String name) {
        return itemTypes.stream()
                .filter(itemType -> name.equals(trimToNull(itemType.getName())))
                .findFirst()
                .orElse(null);
    }

    private MesMdUnitMeasureDO findUnitMeasureByName(List<MesMdUnitMeasureDO> unitMeasures, String name) {
        return unitMeasures.stream()
                .filter(unitMeasure -> name.equals(trimToNull(unitMeasure.getName())))
                .findFirst()
                .orElse(null);
    }

    private MesMdVendorDO findVendorByName(List<MesMdVendorDO> vendors, String name) {
        return vendors.stream()
                .filter(vendor -> name.equals(trimToNull(vendor.getName())))
                .findFirst()
                .orElse(null);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    // ==================== 拼接 VO ====================

    private List<MesWmMaterialStockRespVO> buildRespVOList(List<MesWmMaterialStockDO> list) {
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        // 1. 获得关联数据
        Map<Long, MesMdItemDO> itemMap = itemService.getItemMap(
                convertSet(list, MesWmMaterialStockDO::getItemId));
        Map<Long, MesMdItemTypeDO> itemTypeMap = itemTypeService.getItemTypeMap(
                convertSet(itemMap.values(), MesMdItemDO::getItemTypeId));
        Map<Long, MesMdUnitMeasureDO> unitMeasureMap = unitMeasureService.getUnitMeasureMap(
                convertSet(itemMap.values(), MesMdItemDO::getUnitMeasureId));
        Map<Long, MesWmWarehouseDO> warehouseMap = warehouseService.getWarehouseMap(
                convertSet(list, MesWmMaterialStockDO::getWarehouseId));
        Map<Long, MesWmWarehouseLocationDO> locationMap = locationService.getWarehouseLocationMap(
                convertSet(list, MesWmMaterialStockDO::getLocationId));
        Map<Long, MesWmWarehouseAreaDO> areaMap = areaService.getWarehouseAreaMap(
                convertSet(list, MesWmMaterialStockDO::getAreaId));
        Map<Long, MesMdVendorDO> vendorMap = vendorService.getVendorMap(
                convertSet(list, MesWmMaterialStockDO::getVendorId));
        // 2. 构建结果
        return BeanUtils.toBean(list, MesWmMaterialStockRespVO.class, vo -> {
            MapUtils.findAndThen(itemMap, vo.getItemId(), item -> {
                vo.setItemCode(item.getCode()).setItemName(item.getName()).setSpecification(item.getSpecification());
                vo.setMinStock(item.getMinStock());
                MapUtils.findAndThen(itemTypeMap, item.getItemTypeId(),
                        itemType -> vo.setItemTypeName(itemType.getName()));
                MapUtils.findAndThen(unitMeasureMap, item.getUnitMeasureId(),
                        unitMeasure -> vo.setUnitMeasureName(unitMeasure.getName()));
            });
            MapUtils.findAndThen(warehouseMap, vo.getWarehouseId(), warehouse ->
                    vo.setWarehouseCode(warehouse.getCode()).setWarehouseName(warehouse.getName()));
            MapUtils.findAndThen(locationMap, vo.getLocationId(),
                    location -> vo.setLocationName(location.getName()));
            MapUtils.findAndThen(areaMap, vo.getAreaId(),
                    area -> vo.setAreaName(area.getName()));
            MapUtils.findAndThen(vendorMap, vo.getVendorId(),
                    vendor -> vo.setVendorName(vendor.getName()));
        });
    }

}
