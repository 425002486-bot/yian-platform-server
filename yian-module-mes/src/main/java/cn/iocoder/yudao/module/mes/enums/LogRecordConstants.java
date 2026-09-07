package cn.iocoder.yudao.module.mes.enums;

/**
 * MES 操作日志枚举（翼安智链审计日志）
 *
 * 统一管理 MES 模块中需要审计的 8 类关键操作
 */
public interface LogRecordConstants {

    // ======================= 设备 =======================

    String MES_DEVICE_TYPE = "MES 设备";
    String MES_DEVICE_CREATE_SUB_TYPE = "设备建档";
    String MES_DEVICE_CREATE_SUCCESS = "完成设备建档【{{#machinery.code}}】{{#machinery.name}}";
    String MES_DEVICE_UPDATE_SUB_TYPE = "更新设备";
    String MES_DEVICE_UPDATE_SUCCESS = "更新了设备【{{#machinery.code}}】: {_DIFF{#updateReqVO}}";

    // ======================= 工单 =======================

    String MES_WORKORDER_TYPE = "MES 工单";
    String MES_WORKORDER_CREATE_SUB_TYPE = "新建工单";
    String MES_WORKORDER_CREATE_SUCCESS = "新建工单【{{#workOrder.code}}】，关联设备 {{#deviceCode}}";
    String MES_WORKORDER_ACCEPT_SUB_TYPE = "工单受理";
    String MES_WORKORDER_ACCEPT_SUCCESS = "受理工单【{{#workOrder.code}}】，设备标记停飞";
    String MES_WORKORDER_DIAGNOSIS_SUB_TYPE = "初始诊断提交";
    String MES_WORKORDER_DIAGNOSIS_SUCCESS = "提交初始诊断，工单【{{#workOrderCode}}】，结论：{{#conclusion}}";
    String MES_WORKORDER_REPAIR_SUB_TYPE = "维修提交";
    String MES_WORKORDER_REPAIR_SUCCESS = "提交维修结果，工单【{{#workOrderCode}}】，结论：{{#conclusion}}";
    String MES_WORKORDER_INSPECT_SUB_TYPE = "复检提交";
    String MES_WORKORDER_INSPECT_SUCCESS = "提交复检，工单【{{#workOrderCode}}】，结论：{{#conclusion}}";

    // ======================= 备件 =======================

    String MES_SPARE_TYPE = "MES 备件";
    String MES_SPARE_PICK_SUB_TYPE = "领料";
    String MES_SPARE_PICK_SUCCESS = "领料 {{#quantity}} 个（{{#itemName}}），工单【{{#workOrderCode}}】";
    String MES_SPARE_RETURN_SUB_TYPE = "退料";
    String MES_SPARE_RETURN_SUCCESS = "退料 {{#quantity}} 个（{{#itemName}}），工单【{{#workOrderCode}}】";

    // ======================= 放行 =======================

    String MES_RELEASE_TYPE = "MES 放行";
    String MES_RELEASE_SUBMIT_SUB_TYPE = "放行审核提交";
    String MES_RELEASE_SUBMIT_SUCCESS = "提交放行审核，工单【{{#workOrderCode}}】，结论：{{#conclusion}}";

}
