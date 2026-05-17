package cn.iocoder.yudao.module.mes.controller.admin.audit;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.mes.controller.admin.audit.vo.MesAuditLogPageReqVO;
import cn.iocoder.yudao.module.mes.controller.admin.audit.vo.MesAuditLogRespVO;
import cn.iocoder.yudao.module.system.api.logger.OperateLogApi;
import cn.iocoder.yudao.module.system.api.logger.dto.OperateLogPageReqDTO;
import cn.iocoder.yudao.module.system.api.logger.dto.OperateLogRespDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.fhs.core.trans.anno.TransMethodResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.mes.enums.LogRecordConstants.*;

@Tag(name = "管理后台 - MES 审计日志")
@RestController
@RequestMapping("/mes/audit-log")
@Validated
public class MesAuditLogController {

    @Resource
    private OperateLogApi operateLogApi;
    @Resource
    private AdminUserApi adminUserApi;

    /** objectType（前端） -> LogRecordConstants type（后端）映射 */
    private static final Map<String, String> OBJECT_TYPE_MAP = new LinkedHashMap<>();
    /** LogRecordConstants type -> 前端显示名映射 */
    private static final Map<String, String> TYPE_DISPLAY_MAP = new LinkedHashMap<>();
    /** subType -> 来源页面映射 */
    private static final Map<String, String> SOURCE_PAGE_MAP = new LinkedHashMap<>();

    static {
        OBJECT_TYPE_MAP.put("device", MES_DEVICE_TYPE);
        OBJECT_TYPE_MAP.put("workorder", MES_WORKORDER_TYPE);
        OBJECT_TYPE_MAP.put("spare", MES_SPARE_TYPE);
        OBJECT_TYPE_MAP.put("release", MES_RELEASE_TYPE);

        TYPE_DISPLAY_MAP.put(MES_DEVICE_TYPE, "设备");
        TYPE_DISPLAY_MAP.put(MES_WORKORDER_TYPE, "工单");
        TYPE_DISPLAY_MAP.put(MES_SPARE_TYPE, "备件");
        TYPE_DISPLAY_MAP.put(MES_RELEASE_TYPE, "放行");

        SOURCE_PAGE_MAP.put(MES_DEVICE_CREATE_SUB_TYPE, "设备建档");
        SOURCE_PAGE_MAP.put(MES_DEVICE_UPDATE_SUB_TYPE, "设备详情");
        SOURCE_PAGE_MAP.put(MES_WORKORDER_CREATE_SUB_TYPE, "新建工单");
        SOURCE_PAGE_MAP.put(MES_WORKORDER_ACCEPT_SUB_TYPE, "工单受理");
        SOURCE_PAGE_MAP.put(MES_WORKORDER_DIAGNOSIS_SUB_TYPE, "诊断页");
        SOURCE_PAGE_MAP.put(MES_WORKORDER_REPAIR_SUB_TYPE, "维修页");
        SOURCE_PAGE_MAP.put(MES_WORKORDER_INSPECT_SUB_TYPE, "复检页");
        SOURCE_PAGE_MAP.put(MES_SPARE_PICK_SUB_TYPE, "领料页");
        SOURCE_PAGE_MAP.put(MES_SPARE_RETURN_SUB_TYPE, "领料页");
        SOURCE_PAGE_MAP.put(MES_RELEASE_SUBMIT_SUB_TYPE, "放行审核详情");
    }

    @GetMapping("/page")
    @Operation(summary = "获得审计日志分页")
    @PreAuthorize("@ss.hasPermission('mes:audit-log:query')")
    @TransMethodResult
    public CommonResult<PageResult<MesAuditLogRespVO>> getAuditLogPage(@Valid MesAuditLogPageReqVO pageReqVO) {
        // 1. 构建查询条件
        OperateLogPageReqDTO reqDTO = new OperateLogPageReqDTO();
        reqDTO.setPageNo(pageReqVO.getPageNo());
        reqDTO.setPageSize(pageReqVO.getPageSize());
        reqDTO.setCreateTime(pageReqVO.getCreateTime());

        // 1.1 过滤操作对象类型
        if (pageReqVO.getObjectType() != null && !pageReqVO.getObjectType().isEmpty()) {
            String type = OBJECT_TYPE_MAP.get(pageReqVO.getObjectType());
            if (type != null) {
                reqDTO.setType(type);
            }
        } else {
            reqDTO.setTypes(OBJECT_TYPE_MAP.values());
        }

        // 1.2 按操作人昵称查询 -> 转为 userId
        if (pageReqVO.getOperatorName() != null && !pageReqVO.getOperatorName().isEmpty()) {
            List<AdminUserRespDTO> users = adminUserApi.getUserListByNickname(pageReqVO.getOperatorName());
            if (users.isEmpty()) {
                return success(new PageResult<>(Collections.emptyList(), 0L));
            }
            reqDTO.setUserId(users.get(0).getId());
        }

        // 2. 查询操作日志
        PageResult<OperateLogRespDTO> logPage = operateLogApi.getOperateLogPage(reqDTO);

        // 3. 转换为审计日志 VO
        List<MesAuditLogRespVO> voList = new ArrayList<>(logPage.getList().size());
        for (OperateLogRespDTO log : logPage.getList()) {
            voList.add(convertToRespVO(log));
        }
        return success(new PageResult<>(voList, logPage.getTotal()));
    }

    @GetMapping("/get")
    @Operation(summary = "获得审计日志详情")
    @Parameter(name = "id", description = "日志编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mes:audit-log:query')")
    @TransMethodResult
    public CommonResult<MesAuditLogRespVO> getAuditLog(@RequestParam("id") Long id) {
        OperateLogRespDTO log = operateLogApi.getOperateLog(id);
        if (log == null) {
            return success(null);
        }
        return success(convertToRespVO(log));
    }

    private MesAuditLogRespVO convertToRespVO(OperateLogRespDTO log) {
        MesAuditLogRespVO vo = new MesAuditLogRespVO();
        vo.setId(log.getId());
        vo.setCreateTime(log.getCreateTime());
        vo.setUserName(log.getUserName());
        vo.setObjectType(TYPE_DISPLAY_MAP.getOrDefault(log.getType(), log.getType()));
        vo.setBizId(log.getBizId());
        vo.setAction(log.getAction());
        vo.setSubType(log.getSubType());
        vo.setSourcePage(SOURCE_PAGE_MAP.getOrDefault(log.getSubType(), log.getSubType()));
        vo.setUserIp(log.getUserIp());
        vo.setExtra(log.getExtra());
        return vo;
    }

}
