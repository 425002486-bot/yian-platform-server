package cn.iocoder.yudao.module.mes.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * MES 閿欒鐮佹灇涓剧被
 * <p>
 * mes 绯荤粺锛屼娇鐢?1-040-000-000 娈?
 */
public interface ErrorCodeConstants {

    // ========== MES 鍩虹鏁版嵁-鐗╂枡鍒嗙被锛?-040-100-000锛?==========
    ErrorCode MD_ITEM_TYPE_NOT_EXISTS = new ErrorCode(1_040_100_000, "鐗╂枡鍒嗙被涓嶅瓨鍦?");
    ErrorCode MD_ITEM_TYPE_EXITS_CHILDREN = new ErrorCode(1_040_100_001, "瀛樺湪瀛愬垎绫伙紝鏃犳硶鍒犻櫎");
    ErrorCode MD_ITEM_TYPE_PARENT_NOT_EXITS = new ErrorCode(1_040_100_002, "鐖剁骇鍒嗙被涓嶅瓨鍦?");
    ErrorCode MD_ITEM_TYPE_PARENT_ERROR = new ErrorCode(1_040_100_003, "涓嶈兘璁剧疆鑷繁涓虹埗鍒嗙被");
    ErrorCode MD_ITEM_TYPE_NAME_DUPLICATE = new ErrorCode(1_040_100_004, "鍚屼竴鐖跺垎绫讳笅宸插瓨鍦ㄨ鍚嶇О鐨勫垎绫?");
    ErrorCode MD_ITEM_TYPE_CODE_DUPLICATE = new ErrorCode(1_040_100_005, "鍚屼竴鐖跺垎绫讳笅宸插瓨鍦ㄨ缂栫爜鐨勫垎绫?");
    ErrorCode MD_ITEM_TYPE_PARENT_IS_CHILD = new ErrorCode(1_040_100_006, "涓嶈兘璁剧疆鑷繁鐨勫瓙鍒嗙被涓虹埗鍒嗙被");
    ErrorCode MD_ITEM_TYPE_EXITS_ITEM = new ErrorCode(1_040_100_007, "璇ュ垎绫讳笅瀛樺湪鐗╂枡锛屾棤娉曞垹闄?");
    ErrorCode MD_ITEM_TYPE_NOT_LEAF = new ErrorCode(1_040_100_008, "鍙兘灏嗙墿鏂欐寕杞藉埌鍙跺瓙鍒嗙被锛堣鍒嗙被涓嬪瓨鍦ㄥ瓙鍒嗙被锛?");

    // ========== MES 鍩虹鏁版嵁-璁￠噺鍗曚綅锛?-040-101-000锛?==========
    ErrorCode MD_UNIT_MEASURE_NOT_EXISTS = new ErrorCode(1_040_101_000, "璁￠噺鍗曚綅涓嶅瓨鍦?");
    ErrorCode MD_UNIT_MEASURE_CODE_DUPLICATE = new ErrorCode(1_040_101_001, "璁￠噺鍗曚綅缂栫爜宸插瓨鍦?");
    ErrorCode MD_UNIT_MEASURE_HAS_ITEM = new ErrorCode(1_040_101_002, "璇ヨ閲忓崟浣嶄笅瀛樺湪鐗╂枡锛屾棤娉曞垹闄?");
    ErrorCode MD_UNIT_MEASURE_HAS_SECONDARY = new ErrorCode(1_040_101_003, "璇ヤ富鍗曚綅涓嬪瓨鍦ㄨ緟鍗曚綅锛屾棤娉曞垹闄?");
    ErrorCode MD_UNIT_MEASURE_HAS_TASK_ISSUE = new ErrorCode(1_040_101_004, "璇ヨ閲忓崟浣嶅凡琚敓浜ф姇鏂欏紩鐢紝鏃犳硶鍒犻櫎");
    ErrorCode MD_UNIT_MEASURE_HAS_QC_TEMPLATE_INDICATOR = new ErrorCode(1_040_101_005, "璇ヨ閲忓崟浣嶅凡琚川妫€鏂规鎸囨爣椤瑰紩鐢紝鏃犳硶鍒犻櫎");
    ErrorCode MD_UNIT_MEASURE_HAS_QC_LINE = new ErrorCode(1_040_101_006, "璇ヨ閲忓崟浣嶅凡琚川妫€鍗曟嵁琛屽紩鐢紝鏃犳硶鍒犻櫎");

    // ========== MES 鍩虹鏁版嵁-鐗╂枡锛?-040-102-000锛?==========
    ErrorCode MD_ITEM_NOT_EXISTS = new ErrorCode(1_040_102_000, "鐗╂枡涓嶅瓨鍦?");
    ErrorCode MD_ITEM_CODE_DUPLICATE = new ErrorCode(1_040_102_001, "鐗╂枡缂栫爜宸插瓨鍦?");
    ErrorCode MD_ITEM_NAME_DUPLICATE = new ErrorCode(1_040_102_002, "鐗╂枡鍚嶇О宸插瓨鍦?");
    ErrorCode MD_ITEM_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_040_102_003, "瀵煎叆鐗╂枡鏁版嵁涓嶈兘涓虹┖");
    ErrorCode MD_ITEM_BATCH_REQUIRED = new ErrorCode(1_040_102_004, "褰撳墠鐗╂枡鍚敤浜嗘壒娆＄鐞嗭紝璇烽€夋嫨鎵规");
    ErrorCode MD_ITEM_IS_DISABLE = new ErrorCode(1_040_102_005, "物料已禁用");

    // ========== MES 鍩虹鏁版嵁-鐗╂枡鎵规灞炴€ч厤缃紙1-040-102-100锛?==========
    ErrorCode MD_ITEM_BATCH_CONFIG_NOT_EXISTS = new ErrorCode(1_040_102_100, "鐗╂枡鎵规灞炴€ч厤缃笉瀛樺湪");
    ErrorCode MD_ITEM_BATCH_CONFIG_AT_LEAST_ONE_FLAG = new ErrorCode(1_040_102_101, "鎵规绠＄悊宸插惎鐢紝鑷冲皯闇€瑕侀厤缃竴涓壒娆″睘鎬?");
    ErrorCode MD_ITEM_PRODUCT_BOM_REQUIRED = new ErrorCode(1_040_102_102, "浜у搧绫荤墿鏂欏惎鐢ㄥ墠锛屽繀椤婚厤缃嚦灏戜竴涓?BOM 缁勬垚");

    // ========== MES 浠撳簱绠＄悊-鎵规绠＄悊锛?-040-717-000锛?==========
    ErrorCode WM_BATCH_PRODUCE_DATE_REQUIRED = new ErrorCode(1_040_717_000, "鎵规閰嶇疆瑕佹眰鐢熶骇鏃ユ湡涓嶈兘涓虹┖");
    ErrorCode WM_BATCH_RECEIPT_DATE_REQUIRED = new ErrorCode(1_040_717_001, "鎵规閰嶇疆瑕佹眰鍏ュ簱鏃ユ湡涓嶈兘涓虹┖");
    ErrorCode WM_BATCH_EXPIRE_DATE_REQUIRED = new ErrorCode(1_040_717_002, "鎵规閰嶇疆瑕佹眰鏈夋晥鏈熶笉鑳戒负绌?");
    ErrorCode WM_BATCH_VENDOR_REQUIRED = new ErrorCode(1_040_717_003, "鎵规閰嶇疆瑕佹眰渚涘簲鍟嗕笉鑳戒负绌?");
    ErrorCode WM_BATCH_CLIENT_REQUIRED = new ErrorCode(1_040_717_004, "鎵规閰嶇疆瑕佹眰瀹㈡埛涓嶈兘涓虹┖");
    ErrorCode WM_BATCH_PURCHASE_ORDER_CODE_REQUIRED = new ErrorCode(1_040_717_005, "鎵规閰嶇疆瑕佹眰閲囪喘璁㈠崟缂栧彿涓嶈兘涓虹┖");
    ErrorCode WM_BATCH_CUSTOMER_ORDER_CODE_REQUIRED = new ErrorCode(1_040_717_006, "鎵规閰嶇疆瑕佹眰閿€鍞鍗曠紪鍙蜂笉鑳戒负绌?");
    ErrorCode WM_BATCH_WORK_ORDER_REQUIRED = new ErrorCode(1_040_717_007, "鎵规閰嶇疆瑕佹眰鐢熶骇宸ュ崟涓嶈兘涓虹┖");
    ErrorCode WM_BATCH_TASK_REQUIRED = new ErrorCode(1_040_717_008, "鎵规閰嶇疆瑕佹眰鐢熶骇浠诲姟涓嶈兘涓虹┖");
    ErrorCode WM_BATCH_WORKSTATION_REQUIRED = new ErrorCode(1_040_717_009, "鎵规閰嶇疆瑕佹眰宸ヤ綔绔欎笉鑳戒负绌?");
    ErrorCode WM_BATCH_TOOL_REQUIRED = new ErrorCode(1_040_717_010, "鎵规閰嶇疆瑕佹眰宸ュ叿涓嶈兘涓虹┖");
    ErrorCode WM_BATCH_MOLD_REQUIRED = new ErrorCode(1_040_717_011, "鎵规閰嶇疆瑕佹眰妯″叿涓嶈兘涓虹┖");
    ErrorCode WM_BATCH_LOT_NUMBER_REQUIRED = new ErrorCode(1_040_717_012, "鎵规閰嶇疆瑕佹眰鐢熶骇鎵瑰彿涓嶈兘涓虹┖");
    ErrorCode WM_BATCH_QUALITY_STATUS_REQUIRED = new ErrorCode(1_040_717_013, "鎵规閰嶇疆瑕佹眰璐ㄩ噺鐘舵€佷笉鑳戒负绌?");
    ErrorCode WM_BATCH_NOT_EXISTS = new ErrorCode(1_040_717_014, "鎵规涓嶅瓨鍦?");
    ErrorCode WM_BATCH_ITEM_MISMATCH = new ErrorCode(1_040_717_015, "鎵规涓嶅睘浜庡綋鍓嶇墿鏂?");
    ErrorCode WM_BATCH_CLIENT_MISMATCH = new ErrorCode(1_040_717_016, "鎵规涓嶅睘浜庡綋鍓嶅鎴?");
    ErrorCode WM_BATCH_VENDOR_MISMATCH = new ErrorCode(1_040_717_017, "鎵规涓嶅睘浜庡綋鍓嶄緵搴斿晢");

    // ========== MES 鍩虹鏁版嵁-瀹㈡埛锛?-040-103-000锛?==========
    ErrorCode MD_CLIENT_NOT_EXISTS = new ErrorCode(1_040_103_000, "瀹㈡埛涓嶅瓨鍦?");
    ErrorCode MD_CLIENT_CODE_DUPLICATE = new ErrorCode(1_040_103_001, "瀹㈡埛缂栫爜宸插瓨鍦?");
    ErrorCode MD_CLIENT_NAME_DUPLICATE = new ErrorCode(1_040_103_002, "瀹㈡埛鍚嶇О宸插瓨鍦?");
    ErrorCode MD_CLIENT_NICKNAME_DUPLICATE = new ErrorCode(1_040_103_003, "瀹㈡埛绠€绉板凡瀛樺湪");
    ErrorCode MD_CLIENT_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_040_103_004, "瀵煎叆瀹㈡埛鏁版嵁涓嶈兘涓虹┖");
    ErrorCode MD_CLIENT_IS_DISABLE = new ErrorCode(1_040_103_005, "瀹㈡埛宸茬鐢?");

    // ========== MES 鍩虹鏁版嵁-渚涘簲鍟嗭紙1-040-104-000锛?==========
    ErrorCode MD_VENDOR_NOT_EXISTS = new ErrorCode(1_040_104_000, "渚涘簲鍟嗕笉瀛樺湪");
    ErrorCode MD_VENDOR_CODE_DUPLICATE = new ErrorCode(1_040_104_001, "渚涘簲鍟嗙紪鐮佸凡瀛樺湪");
    ErrorCode MD_VENDOR_NAME_DUPLICATE = new ErrorCode(1_040_104_002, "渚涘簲鍟嗗悕绉板凡瀛樺湪");
    ErrorCode MD_VENDOR_NICKNAME_DUPLICATE = new ErrorCode(1_040_104_003, "渚涘簲鍟嗙畝绉板凡瀛樺湪");
    ErrorCode MD_VENDOR_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_040_104_004, "瀵煎叆渚涘簲鍟嗘暟鎹笉鑳戒负绌?");
    ErrorCode MD_VENDOR_HAS_REFERENCE = new ErrorCode(1_040_104_005, "璇ヤ緵搴斿晢宸茶鍏朵粬涓氬姟寮曠敤锛屾棤娉曞垹闄?");
    ErrorCode MD_VENDOR_IS_DISABLE = new ErrorCode(1_040_104_006, "渚涘簲鍟嗗凡绂佺敤");

    // ========== MES 鍩虹鏁版嵁-杞﹂棿锛?-040-105-000锛?==========
    ErrorCode MD_WORKSHOP_NOT_EXISTS = new ErrorCode(1_040_105_000, "杞﹂棿涓嶅瓨鍦?");
    ErrorCode MD_WORKSHOP_CODE_DUPLICATE = new ErrorCode(1_040_105_001, "杞﹂棿缂栫爜宸插瓨鍦?");
    ErrorCode MD_WORKSHOP_NAME_DUPLICATE = new ErrorCode(1_040_105_002, "杞﹂棿鍚嶇О宸插瓨鍦?");
    ErrorCode MD_WORKSHOP_HAS_WORKSTATION = new ErrorCode(1_040_105_003, "杞﹂棿涓嬪瓨鍦ㄥ伐浣滅珯锛屾棤娉曞垹闄?");

    // ========== MES 鍩虹鏁版嵁-宸ヤ綔绔欙紙1-040-106-000锛?==========
    ErrorCode MD_WORKSTATION_NOT_EXISTS = new ErrorCode(1_040_106_000, "宸ヤ綔绔欎笉瀛樺湪");
    ErrorCode MD_WORKSTATION_CODE_DUPLICATE = new ErrorCode(1_040_106_001, "宸ヤ綔绔欑紪鐮佸凡瀛樺湪");
    ErrorCode MD_WORKSTATION_NAME_DUPLICATE = new ErrorCode(1_040_106_002, "宸ヤ綔绔欏悕绉板凡瀛樺湪");
    ErrorCode MD_WORKSTATION_IS_DISABLE = new ErrorCode(1_040_106_003, "宸ヤ綔绔欏凡绂佺敤");
    // ========== MES 鍩虹鏁版嵁-璁惧璧勬簮锛?-040-106-100锛?==========
    ErrorCode MD_WORKSTATION_MACHINE_NOT_EXISTS = new ErrorCode(1_040_106_100, "璁惧璧勬簮璁板綍涓嶅瓨鍦?");
    ErrorCode MD_WORKSTATION_MACHINE_EXISTS = new ErrorCode(1_040_106_101, "璇ヨ澶囧凡鍒嗛厤鑷冲伐浣滅珯锛歿}");
    // ========== MES 鍩虹鏁版嵁-宸ヨ澶瑰叿璧勬簮锛?-040-106-200锛?==========
    ErrorCode MD_WORKSTATION_TOOL_NOT_EXISTS = new ErrorCode(1_040_106_200, "宸ヨ澶瑰叿璧勬簮璁板綍涓嶅瓨鍦?");
    ErrorCode MD_WORKSTATION_TOOL_TYPE_EXISTS = new ErrorCode(1_040_106_201, "璇ュ伐鍏风被鍨嬪凡鍦ㄦ宸ヤ綔绔欎腑瀛樺湪");
    // ========== MES 鍩虹鏁版嵁-浜哄姏璧勬簮锛?-040-106-300锛?==========
    ErrorCode MD_WORKSTATION_WORKER_NOT_EXISTS = new ErrorCode(1_040_106_300, "浜哄姏璧勬簮璁板綍涓嶅瓨鍦?");
    ErrorCode MD_WORKSTATION_WORKER_POST_EXISTS = new ErrorCode(1_040_106_301, "璇ュ矖浣嶅凡鍦ㄦ宸ヤ綔绔欎腑瀛樺湪");

    // ========== MES 鍩虹鏁版嵁-浜у搧BOM锛?-040-107-000锛?==========
    ErrorCode MD_PRODUCT_BOM_NOT_EXISTS = new ErrorCode(1_040_107_000, "浜у搧BOM涓嶅瓨鍦?");
    ErrorCode MD_PRODUCT_BOM_SELF_REFERENCE = new ErrorCode(1_040_107_001, "浜у搧涓嶈兘浣滀负鑷韩鐨凚OM鐗╂枡");
    ErrorCode MD_PRODUCT_BOM_CIRCULAR = new ErrorCode(1_040_107_002, "BOM鐗╂枡瀛樺湪闂幆锛屾棤娉曟柊澧?");
    ErrorCode MD_PRODUCT_BOM_ITEM_INVALID = new ErrorCode(1_040_107_003, "閫夋嫨鐨?BOM 鐗╂枡涓嶅睘浜庡綋鍓嶄骇鍝?");

    // ========== MES 鍩虹鏁版嵁-浜у搧SOP锛?-040-108-000锛?==========
    ErrorCode MD_PRODUCT_SOP_NOT_EXISTS = new ErrorCode(1_040_108_000, "浜у搧SOP涓嶅瓨鍦?");
    ErrorCode MD_PRODUCT_SOP_SORT_DUPLICATE = new ErrorCode(1_040_108_001, "璇ュ睍绀哄簭鍙峰凡瀛樺湪");

    // ========== MES 鍩虹鏁版嵁-浜у搧SIP锛?-040-109-000锛?==========
    ErrorCode MD_PRODUCT_SIP_NOT_EXISTS = new ErrorCode(1_040_109_000, "浜у搧SIP涓嶅瓨鍦?");
    ErrorCode MD_PRODUCT_SIP_SORT_DUPLICATE = new ErrorCode(1_040_109_001, "璇ュ睍绀哄簭鍙峰凡瀛樺湪");

    // ========== MES 鍩虹鏁版嵁-缂栫爜瑙勫垯锛?-040-110-000锛?==========
    ErrorCode AUTO_CODE_RULE_NOT_EXISTS = new ErrorCode(1_040_110_000, "缂栫爜瑙勫垯涓嶅瓨鍦?");
    ErrorCode AUTO_CODE_RULE_CODE_DUPLICATE = new ErrorCode(1_040_110_001, "瑙勫垯缂栫爜宸插瓨鍦?");
    ErrorCode AUTO_CODE_PART_NOT_EXISTS = new ErrorCode(1_040_110_002, "瑙勫垯缁勬垚涓嶅瓨鍦?");
    ErrorCode AUTO_CODE_REDIS_ERROR = new ErrorCode(1_040_110_003, "缂栫爜鐢熸垚鏈嶅姟涓嶅彲鐢紝璇风◢鍚庨噸璇?");
    ErrorCode AUTO_CODE_GENERATE_FAILED = new ErrorCode(1_040_110_004, "缂栫爜鐢熸垚澶辫触");
    ErrorCode AUTO_CODE_PART_SERIAL_NUMBER_DUPLICATE = new ErrorCode(1_040_110_005, "娴佹按鍙峰垎娈靛彧鑳藉瓨鍦ㄤ竴涓?");

    // ========== MES 鏃ュ巻鎺掔彮-璁″垝鐝锛?-040-200-000锛?==========
    ErrorCode CAL_PLAN_SHIFT_NOT_EXISTS = new ErrorCode(1_040_200_000, "璁″垝鐝涓嶅瓨鍦?");
    ErrorCode CAL_PLAN_SHIFT_COUNT_EXCEED = new ErrorCode(1_040_200_001, "鐝鏁伴噺宸茶揪鍒拌疆鐝柟寮忕殑涓婇檺");

    // ========== MES 鏃ュ巻鎺掔彮-鐝粍锛?-040-201-000锛?==========
    ErrorCode CAL_TEAM_NOT_EXISTS = new ErrorCode(1_040_201_000, "鐝粍涓嶅瓨鍦?");
    ErrorCode CAL_TEAM_CODE_DUPLICATE = new ErrorCode(1_040_201_001, "鐝粍缂栫爜宸插瓨鍦?");
    // ========== MES 鏃ュ巻鎺掔彮-鐝粍鎴愬憳锛?-040-201-100锛?==========
    ErrorCode CAL_TEAM_MEMBER_NOT_EXISTS = new ErrorCode(1_040_201_100, "鐝粍鎴愬憳涓嶅瓨鍦?");
    ErrorCode CAL_TEAM_MEMBER_USER_DUPLICATE = new ErrorCode(1_040_201_101, "璇ョ敤鎴峰凡鍒嗛厤鍒板叾浠栫彮缁?");
    ErrorCode CAL_TEAM_MEMBER_USER_NOT_EXISTS = new ErrorCode(1_040_201_102, "鐢ㄦ埛涓嶅瓨鍦?");
    // ========== MES 鏃ュ巻鎺掔彮-鐝粍鎺掔彮锛?-040-201-200锛?==========
    ErrorCode CAL_TEAM_SHIFT_NOT_EXISTS = new ErrorCode(1_040_201_200, "鐝粍鎺掔彮璁板綍涓嶅瓨鍦?");
    ErrorCode CAL_TEAM_SHIFT_GENERATE_TEAM_NOT_ENOUGH = new ErrorCode(1_040_201_201, "鐝粍鏁伴噺涓嶆弧瓒宠疆鐝柟寮忚姹?");
    ErrorCode CAL_TEAM_SHIFT_GENERATE_SHIFT_NOT_ENOUGH = new ErrorCode(1_040_201_202, "鐝鏁伴噺涓嶆弧瓒宠疆鐝柟寮忚姹?");

    // ========== MES 鏃ュ巻鎺掔彮-鎺掔彮璁″垝锛?-040-202-000锛?==========
    ErrorCode CAL_PLAN_NOT_EXISTS = new ErrorCode(1_040_202_000, "鎺掔彮璁″垝涓嶅瓨鍦?");
    ErrorCode CAL_PLAN_CODE_DUPLICATE = new ErrorCode(1_040_202_001, "鎺掔彮璁″垝缂栫爜宸插瓨鍦?");
    ErrorCode CAL_PLAN_NOT_PREPARE = new ErrorCode(1_040_202_002, "鎺掔彮璁″垝宸茬‘璁わ紝涓嶅厑璁镐慨鏀规垨鍒犻櫎");
    ErrorCode CAL_PLAN_TEAM_COUNT_NOT_MATCH = new ErrorCode(1_040_202_003, "纭鎺掔彮璁″垝鏃讹紝鍒嗛厤鐨勭彮缁勬暟閲忎笌杞彮鏂瑰紡涓嶅尮閰?");
    // ========== MES 鏃ュ巻鎺掔彮-璁″垝鐝粍鍏宠仈锛?-040-202-100锛?==========
    ErrorCode CAL_PLAN_TEAM_NOT_EXISTS = new ErrorCode(1_040_202_100, "璁″垝鐝粍鍏宠仈涓嶅瓨鍦?");
    ErrorCode CAL_PLAN_TEAM_DUPLICATE = new ErrorCode(1_040_202_101, "璇ョ彮缁勫凡鍒嗛厤鍒版璁″垝");

    // ========== MES 鏃ュ巻鎺掔彮-鍋囨湡璁剧疆锛?-040-203-000锛?==========
    ErrorCode CAL_HOLIDAY_NOT_EXISTS = new ErrorCode(1_040_203_000, "鍋囨湡璁剧疆涓嶅瓨鍦?");

    // ========== MES 璁惧绠＄悊-璁惧绫诲瀷锛?-040-300-000锛?==========
    ErrorCode DV_MACHINERY_TYPE_NOT_EXISTS = new ErrorCode(1_040_300_000, "璁惧绫诲瀷涓嶅瓨鍦?");
    ErrorCode DV_MACHINERY_TYPE_EXITS_CHILDREN = new ErrorCode(1_040_300_001, "瀛樺湪瀛愮被鍨嬶紝鏃犳硶鍒犻櫎");
    ErrorCode DV_MACHINERY_TYPE_PARENT_NOT_EXITS = new ErrorCode(1_040_300_002, "鐖剁骇绫诲瀷涓嶅瓨鍦?");
    ErrorCode DV_MACHINERY_TYPE_PARENT_ERROR = new ErrorCode(1_040_300_003, "涓嶈兘璁剧疆鑷繁涓虹埗绫诲瀷");
    ErrorCode DV_MACHINERY_TYPE_NAME_DUPLICATE = new ErrorCode(1_040_300_004, "鍚屼竴鐖剁被鍨嬩笅宸插瓨鍦ㄨ鍚嶇О");
    ErrorCode DV_MACHINERY_TYPE_CODE_DUPLICATE = new ErrorCode(1_040_300_005, "璁惧绫诲瀷缂栫爜宸插瓨鍦?");
    ErrorCode DV_MACHINERY_TYPE_PARENT_IS_CHILD = new ErrorCode(1_040_300_006, "涓嶈兘璁剧疆鑷繁鐨勫瓙绫诲瀷涓虹埗绫诲瀷");
    ErrorCode DV_MACHINERY_TYPE_HAS_MACHINERY = new ErrorCode(1_040_300_007, "璇ョ被鍨嬩笅瀛樺湪璁惧锛屾棤娉曞垹闄?");

    // ========== MES 璁惧绠＄悊-璁惧鍙拌处锛?-040-301-000锛?==========
    ErrorCode DV_MACHINERY_NOT_EXISTS = new ErrorCode(1_040_301_000, "璁惧涓嶅瓨鍦?");
    ErrorCode DV_MACHINERY_CODE_DUPLICATE = new ErrorCode(1_040_301_001, "璁惧缂栫爜宸插瓨鍦?");
    ErrorCode DV_MACHINERY_IS_DISABLE = new ErrorCode(1_040_301_007, "璁惧宸茬鐢?");
    ErrorCode DV_MACHINERY_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_040_301_002, "瀵煎叆璁惧鏁版嵁涓嶈兘涓虹┖");
    ErrorCode DV_MACHINERY_HAS_CHECK_PLAN = new ErrorCode(1_040_301_003, "璁惧宸插叧鑱旂偣妫€璁″垝锛屾棤娉曞垹闄?");
    ErrorCode DV_MACHINERY_HAS_CHECK_RECORD = new ErrorCode(1_040_301_004, "璁惧宸插叧鑱旂偣妫€璁板綍锛屾棤娉曞垹闄?");
    ErrorCode DV_MACHINERY_HAS_MAINTEN_RECORD = new ErrorCode(1_040_301_005, "璁惧宸插叧鑱斾繚鍏昏褰曪紝鏃犳硶鍒犻櫎");
    ErrorCode DV_MACHINERY_HAS_REPAIR = new ErrorCode(1_040_301_006, "璁惧宸插叧鑱旂淮淇伐鍗曪紝鏃犳硶鍒犻櫎");

    // ========== MES 资产中心-电池台账（1-040-307-000）==========
    ErrorCode ASSET_BATTERY_NOT_EXISTS = new ErrorCode(1_040_307_000, "电池不存在");
    ErrorCode ASSET_BATTERY_CODE_DUPLICATE = new ErrorCode(1_040_307_001, "电池编号已存在");
    ErrorCode ASSET_BATTERY_SERIAL_NUMBER_DUPLICATE = new ErrorCode(1_040_307_002, "电池 SN 已存在");

    // ========== MES 璁惧绠＄悊-鐐规淇濆吇椤圭洰锛?-040-304-000锛?==========
    ErrorCode DV_SUBJECT_NOT_EXISTS = new ErrorCode(1_040_304_000, "鐐规淇濆吇椤圭洰涓嶅瓨鍦?");
    ErrorCode DV_SUBJECT_CODE_DUPLICATE = new ErrorCode(1_040_304_001, "椤圭洰缂栫爜宸插瓨鍦?");
    ErrorCode DV_SUBJECT_USED_BY_CHECK_PLAN = new ErrorCode(1_040_304_002, "鐐规淇濆吇椤圭洰宸茶鐐规淇濆吇鏂规浣跨敤锛屾棤娉曞垹闄?");
    ErrorCode DV_SUBJECT_IS_DISABLE = new ErrorCode(1_040_304_003, "鐐规淇濆吇椤圭洰宸茬鐢?");

    // ========== MES 璁惧绠＄悊-鐐规璁″垝锛?-040-302-000锛?==========
    ErrorCode DV_CHECK_PLAN_NOT_EXISTS = new ErrorCode(1_040_302_000, "鐐规璁″垝涓嶅瓨鍦?");
    ErrorCode DV_CHECK_PLAN_CODE_DUPLICATE = new ErrorCode(1_040_302_001, "鐐规淇濆吇鏂规缂栫爜宸插瓨鍦?");
    ErrorCode DV_CHECK_PLAN_NOT_PREPARE = new ErrorCode(1_040_302_002, "鐐规淇濆吇鏂规宸插惎鐢紝涓嶅厑璁镐慨鏀规垨鍒犻櫎");
    ErrorCode DV_CHECK_PLAN_NO_MACHINERY = new ErrorCode(1_040_302_003, "鍚敤鏂规鏃讹紝鑷冲皯闇€瑕佸叧鑱斾竴鍙拌澶?");
    ErrorCode DV_CHECK_PLAN_NO_SUBJECT = new ErrorCode(1_040_302_004, "鍚敤鏂规鏃讹紝鑷冲皯闇€瑕佸叧鑱斾竴涓偣妫€淇濆吇椤圭洰");
    ErrorCode DV_CHECK_PLAN_NOT_ENABLED = new ErrorCode(1_040_302_005, "鐐规淇濆吇鏂规鏈惎鐢紝涓嶅厑璁稿仠鐢?");
    ErrorCode DV_CHECK_PLAN_TYPE_MISMATCH = new ErrorCode(1_040_302_006, "鐐规淇濆吇鏂规绫诲瀷涓庡綋鍓嶄笟鍔′笉鍖归厤");
    ErrorCode DV_CHECK_PLAN_NOT_ENABLED_FOR_RECORD = new ErrorCode(1_040_302_007, "鐐规淇濆吇鏂规鏈惎鐢紝涓嶅厑璁稿垱寤鸿褰?");
    // ========== MES 璁惧绠＄悊-鐐规鏂规璁惧锛?-040-302-100锛?==========
    ErrorCode DV_CHECK_PLAN_MACHINERY_NOT_EXISTS = new ErrorCode(1_040_302_100, "鐐规淇濆吇鏂规璁惧涓嶅瓨鍦?");
    ErrorCode DV_CHECK_PLAN_MACHINERY_DUPLICATE = new ErrorCode(1_040_302_101, "璇ヨ澶囧凡鍏宠仈鍒板綋鍓嶆柟妗堬紝璇峰嬁閲嶅娣诲姞");
    ErrorCode DV_CHECK_PLAN_MACHINERY_EXISTS_IN_SAME_TYPE = new ErrorCode(1_040_302_102, "璇ヨ澶囧凡瀛樺湪浜庡悓绫诲瀷鐨勫叾浠栧惎鐢ㄧ殑鎴栬崏绋跨殑鏂规涓紝涓嶅厑璁稿悓涓€璁惧娣诲姞澶氫釜鍚岀被鍨嬬殑鏂规");
    // ========== MES 璁惧绠＄悊-鐐规鏂规椤圭洰锛?-040-302-200锛?==========
    ErrorCode DV_CHECK_PLAN_SUBJECT_NOT_EXISTS = new ErrorCode(1_040_302_200, "鐐规淇濆吇鏂规椤圭洰涓嶅瓨鍦?");
    ErrorCode DV_CHECK_PLAN_SUBJECT_DUPLICATE = new ErrorCode(1_040_302_201, "璇ラ」鐩凡鍏宠仈鍒板綋鍓嶆柟妗堬紝璇峰嬁閲嶅娣诲姞");

    // ========== MES 璁惧绠＄悊-缁翠慨宸ュ崟锛?-040-303-000锛?==========
    ErrorCode DV_REPAIR_NOT_EXISTS = new ErrorCode(1_040_303_000, "缁翠慨宸ュ崟涓嶅瓨鍦?");
    ErrorCode DV_REPAIR_NOT_PREPARE = new ErrorCode(1_040_303_001, "缁翠慨宸ュ崟涓嶆槸鑽夌鐘舵€侊紝涓嶅厑璁镐慨鏀规垨鍒犻櫎");
    ErrorCode DV_REPAIR_CODE_DUPLICATE = new ErrorCode(1_040_303_002, "缁翠慨宸ュ崟缂栫爜宸插瓨鍦?");
    ErrorCode DV_REPAIR_NOT_CONFIRMED = new ErrorCode(1_040_303_003, "鍙湁缁翠慨涓姸鎬佺殑缁翠慨宸ュ崟鎵嶈兘瀹屾垚缁翠慨");
    ErrorCode DV_REPAIR_NOT_APPROVING = new ErrorCode(1_040_303_004, "鍙湁寰呴獙鏀剁姸鎬佺殑缁翠慨宸ュ崟鎵嶈兘楠屾敹");
    // ========== MES 璁惧绠＄悊-缁翠慨宸ュ崟琛岋紙1-040-303-100锛?==========
    ErrorCode DV_REPAIR_LINE_NOT_EXISTS = new ErrorCode(1_040_303_100, "缁翠慨宸ュ崟琛屼笉瀛樺湪");

    // ========== MES 璁惧绠＄悊-淇濆吇璁板綍锛?-040-305-000锛?==========
    ErrorCode MAINTEN_RECORD_NOT_EXISTS = new ErrorCode(1_040_305_000, "璁惧淇濆吇璁板綍涓嶅瓨鍦?");
    ErrorCode MAINTEN_RECORD_NOT_DRAFT = new ErrorCode(1_040_305_001, "璁惧淇濆吇璁板綍宸叉彁浜わ紝涓嶅厑璁镐慨鏀规垨鍒犻櫎");
    ErrorCode MAINTEN_RECORD_NO_LINE = new ErrorCode(1_040_305_002, "鎻愪氦淇濆吇璁板綍鏃讹紝鑷冲皯闇€瑕佷竴鏉′繚鍏婚」鐩?");
    // ========== MES 璁惧绠＄悊-淇濆吇璁板綍鏄庣粏锛?-040-305-100锛?==========
    ErrorCode MAINTEN_RECORD_LINE_NOT_EXISTS = new ErrorCode(1_040_305_100, "璁惧淇濆吇璁板綍鏄庣粏涓嶅瓨鍦?");

    // ========== MES 璁惧绠＄悊-鐐规璁板綍锛?-040-306-000锛?==========
    ErrorCode DV_CHECK_RECORD_NOT_EXISTS = new ErrorCode(1_040_306_000, "璁惧鐐规璁板綍涓嶅瓨鍦?");
    ErrorCode DV_CHECK_RECORD_NOT_DRAFT = new ErrorCode(1_040_306_001, "璁惧鐐规璁板綍宸插畬鎴愶紝涓嶅厑璁镐慨鏀规垨鍒犻櫎");
    ErrorCode DV_CHECK_RECORD_NO_LINE = new ErrorCode(1_040_306_002, "鎻愪氦鐐规璁板綍鏃讹紝鑷冲皯闇€瑕佷竴鏉＄偣妫€椤圭洰");
    // ========== MES 璁惧绠＄悊-鐐规璁板綍鏄庣粏锛?-040-306-100锛?==========
    ErrorCode DV_CHECK_RECORD_LINE_NOT_EXISTS = new ErrorCode(1_040_306_100, "璁惧鐐规璁板綍鏄庣粏涓嶅瓨鍦?");

    // ========== MES 宸ュ叿绠＄悊-宸ュ叿绫诲瀷锛?-040-400-000锛?==========
    ErrorCode TM_TOOL_TYPE_NOT_EXISTS = new ErrorCode(1_040_400_000, "宸ュ叿绫诲瀷涓嶅瓨鍦?");
    ErrorCode TM_TOOL_TYPE_CODE_DUPLICATE = new ErrorCode(1_040_400_001, "宸ュ叿绫诲瀷缂栫爜宸插瓨鍦?");
    ErrorCode TM_TOOL_TYPE_NAME_DUPLICATE = new ErrorCode(1_040_400_002, "宸ュ叿绫诲瀷鍚嶇О宸插瓨鍦?");
    ErrorCode TM_TOOL_TYPE_HAS_TOOL = new ErrorCode(1_040_400_003, "璇ュ伐鍏风被鍨嬩笅瀛樺湪宸ュ叿锛屾棤娉曞垹闄?");
    ErrorCode TM_TOOL_TYPE_HAS_WORKSTATION_TOOL = new ErrorCode(1_040_400_004, "璇ュ伐鍏风被鍨嬪凡琚伐浣滅珯宸ヨ璧勬簮寮曠敤锛屾棤娉曞垹闄?");

    // ========== MES 宸ュ叿绠＄悊-宸ュ叿鍙拌处锛?-040-401-000锛?==========
    ErrorCode TM_TOOL_NOT_EXISTS = new ErrorCode(1_040_401_000, "宸ュ叿涓嶅瓨鍦?");
    ErrorCode TM_TOOL_CODE_DUPLICATE = new ErrorCode(1_040_401_001, "宸ュ叿缂栫爜宸插瓨鍦?");
    ErrorCode TM_TOOL_HAS_BATCH = new ErrorCode(1_040_401_002, "璇ュ伐鍏峰凡琚壒娆″紩鐢紝鏃犳硶鍒犻櫎");

    // ========== MES 鐢熶骇绠＄悊-宸ュ簭锛?-040-500-000锛?==========
    ErrorCode PRO_PROCESS_NOT_EXISTS = new ErrorCode(1_040_500_000, "宸ュ簭涓嶅瓨鍦?");
    ErrorCode PRO_PROCESS_CODE_EXISTS = new ErrorCode(1_040_500_001, "宸ュ簭缂栫爜宸插瓨鍦?");
    ErrorCode PRO_PROCESS_NAME_EXISTS = new ErrorCode(1_040_500_002, "宸ュ簭鍚嶇О宸插瓨鍦?");
    ErrorCode PRO_PROCESS_USED_BY_ROUTE = new ErrorCode(1_040_500_003, "宸ュ簭宸茶宸ヨ壓璺嚎寮曠敤锛屾棤娉曞垹闄?");
    ErrorCode PRO_PROCESS_IS_DISABLE = new ErrorCode(1_040_500_004, "宸ュ簭宸茬鐢?");
    // ========== MES 鐢熶骇绠＄悊-宸ュ簭鍐呭锛?-040-500-100锛?==========
    ErrorCode PRO_PROCESS_CONTENT_NOT_EXISTS = new ErrorCode(1_040_500_100, "宸ュ簭鍐呭涓嶅瓨鍦?");

    // ========== MES 鐢熶骇绠＄悊-宸ヨ壓璺嚎锛?-040-501-000锛?==========
    ErrorCode PRO_ROUTE_NOT_EXISTS = new ErrorCode(1_040_501_000, "宸ヨ壓璺嚎涓嶅瓨鍦?");
    ErrorCode PRO_ROUTE_CODE_DUPLICATE = new ErrorCode(1_040_501_001, "宸ヨ壓璺嚎缂栫爜宸插瓨鍦?");
    ErrorCode PRO_ROUTE_ENABLE_NO_PROCESS = new ErrorCode(1_040_501_002, "璇峰厛娣诲姞缁勬垚宸ュ簭");
    ErrorCode PRO_ROUTE_ENABLE_NO_KEY_PROCESS = new ErrorCode(1_040_501_003, "宸ヨ壓璺嚎蹇呴』瑕佹湁鍏抽敭宸ュ簭");
    ErrorCode PRO_ROUTE_ENABLE_PRODUCT_NO_BOM = new ErrorCode(1_040_501_004, "浜у搧 {} 鏈厤缃伐搴忕殑 BOM 娑堣€?");
    ErrorCode PRO_ROUTE_IS_ENABLE = new ErrorCode(1_040_501_005, "宸ヨ壓璺嚎宸插惎鐢紝涓嶅厑璁告搷浣?");
    // ========== MES 鐢熶骇绠＄悊-宸ヨ壓璺嚎宸ュ簭锛?-040-501-100锛?==========
    ErrorCode PRO_ROUTE_PROCESS_NOT_EXISTS = new ErrorCode(1_040_501_100, "宸ヨ壓璺嚎宸ュ簭涓嶅瓨鍦?");
    ErrorCode PRO_ROUTE_PROCESS_SORT_DUPLICATE = new ErrorCode(1_040_501_101, "搴忓彿宸插瓨鍦?");
    ErrorCode PRO_ROUTE_PROCESS_DUPLICATE = new ErrorCode(1_040_501_102, "涓嶈兘閲嶅娣诲姞宸ュ簭");
    ErrorCode PRO_ROUTE_PROCESS_KEY_DUPLICATE = new ErrorCode(1_040_501_103, "褰撳墠宸ヨ壓璺嚎宸茬粡鎸囧畾杩囧叧閿伐搴?");
    // ========== MES 鐢熶骇绠＄悊-宸ヨ壓璺嚎浜у搧锛?-040-501-200锛?==========
    ErrorCode PRO_ROUTE_PRODUCT_NOT_EXISTS = new ErrorCode(1_040_501_200, "宸ヨ壓璺嚎浜у搧涓嶅瓨鍦?");
    ErrorCode PRO_ROUTE_PRODUCT_ITEM_DUPLICATE = new ErrorCode(1_040_501_201, "姝や骇鍝佸凡閰嶇疆浜嗗伐鑹鸿矾绾?");
    // ========== MES 鐢熶骇绠＄悊-宸ヨ壓璺嚎浜у搧BOM锛?-040-501-300锛?==========
    ErrorCode PRO_ROUTE_PRODUCT_BOM_NOT_EXISTS = new ErrorCode(1_040_501_300, "宸ヨ壓璺嚎浜у搧 BOM 涓嶅瓨鍦?");
    ErrorCode PRO_ROUTE_PRODUCT_BOM_DUPLICATE = new ErrorCode(1_040_501_301, "褰撳墠 BOM 鐗╂枡鍦ㄦ宸ュ簭宸茬粡閰嶇疆杩?");

    // ========== MES 鐢熶骇绠＄悊-鐢熶骇宸ュ崟锛?-040-502-000锛?==========
    ErrorCode PRO_WORK_ORDER_NOT_EXISTS = new ErrorCode(1_040_502_000, "鐢熶骇宸ュ崟涓嶅瓨鍦?");
    ErrorCode PRO_WORK_ORDER_CODE_DUPLICATE = new ErrorCode(1_040_502_001, "鐢熶骇宸ュ崟缂栫爜宸插瓨鍦?");
    ErrorCode PRO_WORK_ORDER_NOT_PREPARE = new ErrorCode(1_040_502_002, "鍙湁鑽夌鐘舵€佺殑宸ュ崟鎵嶈兘鎵ц姝ゆ搷浣?");
    ErrorCode PRO_WORK_ORDER_NOT_CONFIRMED = new ErrorCode(1_040_502_003, "鍙湁宸茬‘璁ょ姸鎬佺殑宸ュ崟鎵嶈兘鎵ц姝ゆ搷浣?");
    ErrorCode PRO_WORK_ORDER_HAS_CHILDREN = new ErrorCode(1_040_502_004, "瀛樺湪瀛愬伐鍗曪紝鏃犳硶鍒犻櫎");
    ErrorCode PRO_WORK_ORDER_PRODUCT_MISMATCH = new ErrorCode(1_040_502_005, "褰撳墠浜у搧鐗╂枡涓庣敓浜у伐鍗曚骇鍝佷笉涓€鑷?");
    ErrorCode PRO_WORK_ORDER_BOM_NOT_EXISTS = new ErrorCode(1_040_502_100, "鐢熶骇宸ュ崟BOM涓嶅瓨鍦?");

    // ========== MES 鐢熶骇绠＄悊-鐢熶骇浠诲姟锛?-040-503-000锛?==========
    ErrorCode PRO_TASK_NOT_EXISTS = new ErrorCode(1_040_503_000, "鐢熶骇浠诲姟涓嶅瓨鍦?");
    ErrorCode PRO_TASK_ALREADY_FINISHED = new ErrorCode(1_040_503_001, "鐢熶骇浠诲姟宸插畬鎴愭垨宸插彇娑堬紝涓嶈兘缁х画鎿嶄綔");
    ErrorCode PRO_TASK_WORK_ORDER_MISMATCH = new ErrorCode(1_040_503_002, "鐢熶骇浠诲姟涓嶅睘浜庡綋鍓嶇敓浜у伐鍗?");
    ErrorCode PRO_TASK_WORKSTATION_MISMATCH = new ErrorCode(1_040_503_003, "鐢熶骇浠诲姟涓嶅睘浜庡綋鍓嶅伐浣滅珯");
    ErrorCode PRO_TASK_ROUTE_PROCESS_MISMATCH = new ErrorCode(1_040_503_004, "鐢熶骇浠诲姟涓庡綋鍓嶅伐鑹鸿矾绾挎垨宸ュ簭涓嶄竴鑷?");
    ErrorCode PRO_TASK_ITEM_MISMATCH = new ErrorCode(1_040_503_005, "鐢熶骇浠诲姟浜у搧涓庡綋鍓嶄骇鍝佺墿鏂欎笉涓€鑷?");
    ErrorCode PRO_WORKSTATION_PROCESS_MISMATCH = new ErrorCode(1_040_503_006, "宸ヤ綔绔欐墍灞炲伐搴忎笌褰撳墠宸ュ簭涓嶄竴鑷?");
    // ========== MES 鐢熶骇绠＄悊-鐢熶骇浠诲姟鎶曟枡锛?-040-503-100锛?==========
    ErrorCode PRO_TASK_ISSUE_NOT_EXISTS = new ErrorCode(1_040_503_100, "鐢熶骇浠诲姟鎶曟枡璁板綍涓嶅瓨鍦?");

    // ========== MES 鐢熶骇绠＄悊-瀹夌伅鍛煎彨閰嶇疆锛?-040-504-000锛?==========
    ErrorCode PRO_ANDON_CONFIG_NOT_EXISTS = new ErrorCode(1_040_504_000, "瀹夌伅鍛煎彨閰嶇疆涓嶅瓨鍦?");

    // ========== MES 鐢熶骇绠＄悊-瀹夌伅鍛煎彨璁板綍锛?-040-505-000锛?==========
    ErrorCode PRO_ANDON_RECORD_NOT_EXISTS = new ErrorCode(1_040_505_000, "瀹夌伅鍛煎彨璁板綍涓嶅瓨鍦?");
    ErrorCode PRO_ANDON_RECORD_ALREADY_HANDLED = new ErrorCode(1_040_505_001, "瀹夌伅璁板綍宸插缃紝涓嶅厑璁搁噸澶嶅缃?");
    ErrorCode PRO_ANDON_RECORD_HANDLE_TIME_REQUIRED = new ErrorCode(1_040_505_002, "鏍囪宸插缃椂锛屽缃椂闂翠笉鑳戒负绌?");
    ErrorCode PRO_ANDON_RECORD_HANDLER_USER_REQUIRED = new ErrorCode(1_040_505_003, "鏍囪宸插缃椂锛屽缃汉涓嶈兘涓虹┖");

    // ========== MES 鐢熶骇绠＄悊-鐢熶骇鎶ュ伐锛?-040-506-000锛?==========
    ErrorCode PRO_FEEDBACK_NOT_EXISTS = new ErrorCode(1_040_506_000, "鐢熶骇鎶ュ伐涓嶅瓨鍦?");
    ErrorCode PRO_FEEDBACK_NOT_PREPARE = new ErrorCode(1_040_506_001, "鍙兘淇敼鎴栧垹闄よ崏绋跨姸鎬佺殑鎶ュ伐鍗?");
    ErrorCode PRO_FEEDBACK_NOT_APPROVING = new ErrorCode(1_040_506_002, "鍙湁瀹℃壒涓姸鎬佺殑鎶ュ伐鍗曟墠鑳芥墽琛屾鎿嶄綔");
    ErrorCode PRO_FEEDBACK_NOT_UNCHECK = new ErrorCode(1_040_506_003, "鍙湁寰呮楠岀姸鎬佺殑鎶ュ伐鍗曟墠鑳藉畬鎴愭楠?");
    ErrorCode PRO_FEEDBACK_QUANTITY_EXCEED = new ErrorCode(1_040_506_004, "鎶ュ伐鏁伴噺涓嶈兘瓒呰繃鎺掍骇鏁伴噺");
    ErrorCode PRO_FEEDBACK_STATUS_ERROR = new ErrorCode(1_040_506_005, "鎶ュ伐鍗曠姸鎬佷笉姝ｇ‘锛屾棤娉曟墽琛屾鎿嶄綔");
    ErrorCode PRO_FEEDBACK_WORK_ORDER_NOT_CONFIRMED = new ErrorCode(1_040_506_006, "鍏宠仈鐨勫伐鍗曟湭纭锛屾棤娉曞垱寤烘姤宸?");
    ErrorCode PRO_FEEDBACK_QUALIFIED_UNQUALIFIED_MISMATCH = new ErrorCode(1_040_506_007, "鍚堟牸鍝佹暟閲忎笌涓嶈壇鍝佹暟閲忎箣鍜屽繀椤荤瓑浜庢姤宸ユ暟閲?");
    ErrorCode PRO_FEEDBACK_ROUTE_PROCESS_INVALID = new ErrorCode(1_040_506_008, "鏈壘鍒板搴旂殑宸ヨ壓宸ュ簭閰嶇疆锛岃妫€鏌ュ伐鑹鸿矾绾夸笌宸ュ簭");
    ErrorCode PRO_FEEDBACK_TASK_OR_ORDER_FINISHED = new ErrorCode(1_040_506_009, "褰撳墠鐢熶骇浠诲姟鎴栧伐鍗曞凡瀹屾垚锛屼笉鑳界户缁姤宸?");
    ErrorCode PRO_FEEDBACK_QUANTITY_MUST_POSITIVE = new ErrorCode(1_040_506_010, "鎶ュ伐鏁伴噺蹇呴』澶т簬 0");
    ErrorCode PRO_FEEDBACK_QUALIFIED_UNQUALIFIED_REQUIRED = new ErrorCode(1_040_506_011, "璇疯緭鍏ュ悎鏍煎搧鍜屼笉鑹搧鏁伴噺锛屼笖鍚堣椤诲ぇ浜?0");
    ErrorCode PRO_FEEDBACK_UNCHECK_QUANTITY_EXISTS = new ErrorCode(1_040_506_012, "褰撳墠鎶ュ伐鍗曟湭瀹屾垚妫€楠岋紙寰呮鏁伴噺锛歿}锛夛紝鏃犳硶鎵ц鎶ュ伐");

    // ========== MES 鐢熶骇绠＄悊-鐢熶骇娴佽浆鍗★紙1-040-507-000锛?==========
    ErrorCode PRO_CARD_NOT_EXISTS = new ErrorCode(1_040_507_000, "鐢熶骇娴佽浆鍗′笉瀛樺湪");
    ErrorCode PRO_CARD_CODE_DUPLICATE = new ErrorCode(1_040_507_001, "娴佽浆鍗＄紪鐮佸凡瀛樺湪");
    ErrorCode PRO_CARD_STATUS_ERROR = new ErrorCode(1_040_507_002, "娴佽浆鍗＄姸鎬佷笉姝ｇ‘");
    ErrorCode PRO_CARD_CANCEL_NOT_ALLOWED = new ErrorCode(1_040_507_003, "宸插畬鎴愭垨宸插彇娑堢殑娴佽浆鍗′笉鍏佽鍙栨秷");
    ErrorCode PRO_CARD_NOT_PREPARE = new ErrorCode(1_040_507_004, "娴佽浆鍗′笉鏄崏绋跨姸鎬侊紝涓嶅厑璁镐慨鏀规垨鍒犻櫎");
    // ========== MES 鐢熶骇绠＄悊-娴佽浆鍗″伐搴忥紙1-040-507-100锛?==========
    ErrorCode PRO_CARD_PROCESS_NOT_EXISTS = new ErrorCode(1_040_507_100, "娴佽浆鍗″伐搴忚褰曚笉瀛樺湪");

    // ========== MES 鐢熶骇绠＄悊-宸ヤ綔璁板綍锛?-040-508-000锛?==========
    ErrorCode WORK_RECORD_NOT_CLOCK_IN = new ErrorCode(1_040_508_001, "褰撳墠鐢ㄦ埛鏈笂宸ワ紝鏃犳硶涓嬪伐");
    ErrorCode WORK_RECORD_ALREADY_CLOCK_IN = new ErrorCode(1_040_508_002, "褰撳墠鐢ㄦ埛宸蹭笂宸ワ紝璇峰厛涓嬪伐鍐嶆搷浣?");

    // ========== MES 璐ㄩ噺绠＄悊-璐ㄦ鏂规锛?-040-600-000锛?==========
    ErrorCode QC_TEMPLATE_NOT_EXISTS = new ErrorCode(1_040_600_000, "璐ㄦ鏂规涓嶅瓨鍦?");
    ErrorCode QC_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1_040_600_001, "璐ㄦ鏂规缂栧彿宸插瓨鍦?");
    // ========== MES 璐ㄩ噺绠＄悊-璐ㄦ鏂规妫€娴嬫寚鏍囬」锛?-040-600-100锛?==========
    ErrorCode QC_TEMPLATE_INDICATOR_NOT_EXISTS = new ErrorCode(1_040_600_100, "璐ㄦ鏂规妫€娴嬫寚鏍囬」涓嶅瓨鍦?");
    // ========== MES 璐ㄩ噺绠＄悊-璐ㄦ鏂规浜у搧鍏宠仈锛?-040-600-200锛?==========
    ErrorCode QC_TEMPLATE_ITEM_NOT_EXISTS = new ErrorCode(1_040_600_200, "璐ㄦ鏂规浜у搧鍏宠仈涓嶅瓨鍦?");
    ErrorCode QC_TEMPLATE_ITEM_DUPLICATE = new ErrorCode(1_040_600_201, "璇ヤ骇鍝佸凡鍏宠仈姝よ川妫€鏂规");

    // ========== MES 璐ㄩ噺绠＄悊-璐ㄦ鎸囨爣锛?-040-601-000锛?==========
    ErrorCode QC_INDICATOR_NOT_EXISTS = new ErrorCode(1_040_601_000, "璐ㄦ鎸囨爣涓嶅瓨鍦?");
    ErrorCode QC_INDICATOR_CODE_DUPLICATE = new ErrorCode(1_040_601_001, "璐ㄦ鎸囨爣缂栫爜宸插瓨鍦?");
    ErrorCode QC_INDICATOR_NAME_DUPLICATE = new ErrorCode(1_040_601_002, "璐ㄦ鎸囨爣鍚嶇О宸插瓨鍦?");
    ErrorCode QC_INDICATOR_RESULT_SPECIFICATION_REQUIRED = new ErrorCode(1_040_601_003, "缁撴灉鍊煎睘鎬т笉鑳戒负绌?");

    // ========== MES 璐ㄩ噺绠＄悊-缂洪櫡绫诲瀷锛?-040-602-000锛?==========
    ErrorCode QC_DEFECT_NOT_EXISTS = new ErrorCode(1_040_602_000, "缂洪櫡绫诲瀷涓嶅瓨鍦?");
    ErrorCode QC_DEFECT_CODE_DUPLICATE = new ErrorCode(1_040_602_001, "缂洪櫡绫诲瀷缂栫爜宸插瓨鍦?");
    ErrorCode QC_DEFECT_NAME_DUPLICATE = new ErrorCode(1_040_602_002, "缂洪櫡绫诲瀷鍚嶇О宸插瓨鍦?");

    // ========== MES 璐ㄩ噺绠＄悊-鏉ユ枡妫€楠?IQC锛?-040-603-000锛?==========
    ErrorCode QC_IQC_NOT_EXISTS = new ErrorCode(1_040_603_000, "鏉ユ枡妫€楠屽崟涓嶅瓨鍦?");
    ErrorCode QC_IQC_CODE_DUPLICATE = new ErrorCode(1_040_603_001, "鏉ユ枡妫€楠屽崟缂栧彿宸插瓨鍦?");
    ErrorCode QC_IQC_NOT_PREPARE = new ErrorCode(1_040_603_002, "鍙湁鑽夌鐘舵€佺殑妫€楠屽崟鎵嶅彲鎿嶄綔");
    ErrorCode QC_IQC_QUANTITY_MISMATCH = new ErrorCode(1_040_603_004, "鍚堟牸鍝佷笌涓嶅悎鏍煎搧鏁伴噺涔嬪拰椤荤瓑浜庢娴嬫暟閲?");
    ErrorCode QC_NO_TEMPLATE = new ErrorCode(1_040_603_005, "褰撳墠浜у搧鏈厤缃娴嬫ā鏉?");
    ErrorCode QC_IQC_SOURCE_DOC_PARAMS_MISSING = new ErrorCode(1_040_603_006, "鏉ユ簮鍗曟嵁绫诲瀷闈炵┖鏃讹紝鏉ユ簮鍗曟嵁 ID 鍜屾潵婧愬崟鎹 ID 涓嶈兘涓虹┖");
    ErrorCode QC_IQC_CHECK_RESULT_EMPTY = new ErrorCode(1_040_603_007, "瀹屾垚妫€楠屽崟鍓嶏紝妫€娴嬬粨鏋滃繀椤诲～鍐?");
    // ========== MES 璐ㄩ噺绠＄悊-鏉ユ枡妫€楠岃锛?-040-603-100锛?==========
    ErrorCode QC_IQC_LINE_NOT_EXISTS = new ErrorCode(1_040_603_100, "鏉ユ枡妫€楠岃涓嶅瓨鍦?");

    // ========== MES 璐ㄩ噺绠＄悊-杩囩▼妫€楠?IPQC锛?-040-604-000锛?==========
    ErrorCode QC_IPQC_NOT_EXISTS = new ErrorCode(1_040_604_000, "杩囩▼妫€楠屽崟涓嶅瓨鍦?");
    ErrorCode QC_IPQC_CODE_DUPLICATE = new ErrorCode(1_040_604_001, "杩囩▼妫€楠屽崟缂栧彿宸插瓨鍦?");
    ErrorCode QC_IPQC_NOT_PREPARE = new ErrorCode(1_040_604_002, "鍙湁鑽夌鐘舵€佺殑妫€楠屽崟鎵嶅彲鎿嶄綔");
    ErrorCode QC_IPQC_QUANTITY_MISMATCH = new ErrorCode(1_040_604_004, "鍚堟牸鍝佷笌涓嶅悎鏍煎搧鏁伴噺涔嬪拰椤荤瓑浜庢娴嬫暟閲?");
    ErrorCode QC_IPQC_NO_TEMPLATE = new ErrorCode(1_040_604_005, "褰撳墠浜у搧鏈厤缃?IPQC 妫€娴嬫ā鏉?");
    ErrorCode QC_IPQC_CHECK_RESULT_EMPTY = new ErrorCode(1_040_604_006, "瀹屾垚妫€楠屽崟鍓嶏紝妫€娴嬬粨鏋滃繀椤诲～鍐?");
    ErrorCode QC_IPQC_SOURCE_DOC_TYPE_UNKNOWN = new ErrorCode(1_040_604_007, "鏈煡鐨?IPQC 鏉ユ簮鍗曟嵁绫诲瀷");
    ErrorCode QC_IPQC_SOURCE_DOC_NO_PENDING_LINE = new ErrorCode(1_040_604_008, "鏉ユ簮鎶ュ伐鍗曚笉瀛樺湪寰呮浜у嚭琛岋紝鏃犳硶鍒涘缓 IPQC");
    ErrorCode QC_IPQC_SOURCE_LINE_NOT_BELONG = new ErrorCode(1_040_604_009, "鏉ユ簮鍗曟嵁琛屼笉灞炰簬璇ユ姤宸ュ崟");
    ErrorCode QC_IPQC_SOURCE_LINE_REQUIRED = new ErrorCode(1_040_604_010, "鏉ユ簮鍗曟嵁绫诲瀷涓烘姤宸ユ椂锛屾潵婧愪骇鍑鸿 ID 涓嶈兘涓虹┖");
    ErrorCode QC_IPQC_SOURCE_LINE_NOT_PENDING = new ErrorCode(1_040_604_011, "鏉ユ簮浜у嚭琛屼笉鏄緟妫€楠岀姸鎬?");
    // ========== MES 璐ㄩ噺绠＄悊-杩囩▼妫€楠岃锛?-040-604-100锛?==========
    ErrorCode QC_IPQC_LINE_NOT_EXISTS = new ErrorCode(1_040_604_100, "杩囩▼妫€楠岃涓嶅瓨鍦?");

    // ========== MES 璐ㄩ噺绠＄悊-璐ㄦ缂洪櫡璁板綍锛堥€氱敤锛夛紙1-040-605-000锛?==========
    ErrorCode QC_DEFECT_RECORD_NOT_EXISTS = new ErrorCode(1_040_605_000, "缂洪櫡璁板綍涓嶅瓨鍦?");
    ErrorCode QC_DEFECT_RECORD_LEVEL_UNKNOWN = new ErrorCode(1_040_605_001, "鏈煡鐨勭己闄风瓑绾?");
    ErrorCode QC_DEFECT_RECORD_QC_TYPE_UNSUPPORTED = new ErrorCode(1_040_605_002, "涓嶆敮鎸佺殑妫€楠岀被鍨?");

    // ========== MES 璐ㄩ噺绠＄悊-妫€楠岀粨鏋滐紙1-040-606-000锛?==========
    ErrorCode QC_RESULT_NOT_EXISTS = new ErrorCode(1_040_606_000, "妫€楠岀粨鏋滀笉瀛樺湪");
    ErrorCode QC_RESULT_VALUE_FORMAT_INVALID = new ErrorCode(1_040_606_001, "妫€娴嬪€兼牸寮忎笉姝ｇ‘锛歿}");
    ErrorCode QC_FINISH_INDICATOR_RESULT_REQUIRED = new ErrorCode(1_040_606_002, "瀹屾垚妫€楠屽崟鍓嶏紝鑷冲皯闇€瑕佸綍鍏ヤ竴鏉℃娴嬬粨鏋?");

    // ========== MES 璐ㄩ噺绠＄悊-鍑鸿揣妫€楠岋紙1-040-607-000锛?==========
    ErrorCode QC_OQC_NOT_EXISTS = new ErrorCode(1_040_607_000, "鍑鸿揣妫€楠屽崟涓嶅瓨鍦?");
    ErrorCode QC_OQC_CODE_DUPLICATE = new ErrorCode(1_040_607_001, "鍑鸿揣妫€楠屽崟缂栧彿宸插瓨鍦?");
    ErrorCode QC_OQC_NOT_PREPARE = new ErrorCode(1_040_607_002, "鍙湁鑽夌鐘舵€佺殑妫€楠屽崟鎵嶅彲鎿嶄綔");
    ErrorCode QC_OQC_QUANTITY_MISMATCH = new ErrorCode(1_040_607_004, "鍚堟牸鍝佷笌涓嶅悎鏍煎搧鏁伴噺涔嬪拰椤荤瓑浜庢娴嬫暟閲?");
    ErrorCode QC_OQC_NO_TEMPLATE = new ErrorCode(1_040_607_005, "褰撳墠浜у搧鏈厤缃?OQC 妫€娴嬫ā鏉?");
    ErrorCode QC_OQC_CHECK_RESULT_EMPTY = new ErrorCode(1_040_607_006, "瀹屾垚妫€楠屽崟鍓嶏紝妫€娴嬬粨鏋滃繀椤诲～鍐?");
    ErrorCode QC_OQC_SOURCE_DOC_TYPE_UNKNOWN = new ErrorCode(1_040_607_007, "鏈煡鐨?OQC 鏉ユ簮鍗曟嵁绫诲瀷");

    // ========== MES 璐ㄩ噺绠＄悊-鍑鸿揣妫€楠岃锛?-040-607-100锛?==========
    ErrorCode QC_OQC_LINE_NOT_EXISTS = new ErrorCode(1_040_607_100, "鍑鸿揣妫€楠岃涓嶅瓨鍦?");

    // ========== MES 璐ㄩ噺绠＄悊-閫€璐ф楠?RQC锛?-040-608-000锛?==========
    ErrorCode QC_RQC_NOT_EXISTS = new ErrorCode(1_040_608_000, "閫€璐ф楠屽崟涓嶅瓨鍦?");
    ErrorCode QC_RQC_CODE_DUPLICATE = new ErrorCode(1_040_608_001, "閫€璐ф楠屽崟缂栧彿宸插瓨鍦?");
    ErrorCode QC_RQC_NOT_PREPARE = new ErrorCode(1_040_608_002, "鍙湁鑽夌鐘舵€佺殑妫€楠屽崟鎵嶅彲鎿嶄綔");
    ErrorCode QC_RQC_QUANTITY_MISMATCH = new ErrorCode(1_040_608_004, "鍚堟牸鍝佷笌涓嶅悎鏍煎搧鏁伴噺涔嬪拰椤荤瓑浜庢娴嬫暟閲?");
    ErrorCode QC_RQC_NO_TEMPLATE = new ErrorCode(1_040_608_005, "褰撳墠浜у搧鏈厤缃?RQC 妫€娴嬫ā鏉?");
    ErrorCode QC_RQC_CHECK_RESULT_EMPTY = new ErrorCode(1_040_608_006, "瀹屾垚妫€楠屽崟鍓嶏紝妫€娴嬬粨鏋滃繀椤诲～鍐?");
    // ========== MES 璐ㄩ噺绠＄悊-閫€璐ф楠岃锛?-040-608-100锛?==========
    ErrorCode QC_RQC_LINE_NOT_EXISTS = new ErrorCode(1_040_608_100, "閫€璐ф楠岃涓嶅瓨鍦?");

    // ========== MES 浠撳簱绠＄悊-浠撳簱锛?-040-700-000锛?==========
    ErrorCode WM_WAREHOUSE_NOT_EXISTS = new ErrorCode(1_040_700_000, "浠撳簱涓嶅瓨鍦?");
    ErrorCode WM_WAREHOUSE_CODE_DUPLICATE = new ErrorCode(1_040_700_001, "浠撳簱缂栫爜宸插瓨鍦?");
    ErrorCode WM_WAREHOUSE_NAME_DUPLICATE = new ErrorCode(1_040_700_002, "浠撳簱鍚嶇О宸插瓨鍦?");
    ErrorCode WM_WAREHOUSE_HAS_LOCATION = new ErrorCode(1_040_700_003, "浠撳簱涓嬪瓨鍦ㄥ簱鍖猴紝鏃犳硶鍒犻櫎");
    ErrorCode WM_WAREHOUSE_HAS_WORKSTATION = new ErrorCode(1_040_700_004, "浠撳簱宸茶宸ヤ綔绔欏紩鐢紝鏃犳硶鍒犻櫎");
    ErrorCode WM_WAREHOUSE_HAS_MATERIAL_STOCK = new ErrorCode(1_040_700_005, "浠撳簱涓嬫湁搴撳瓨璁板綍锛屾棤娉曞垹闄?");
    ErrorCode WM_WAREHOUSE_IS_VIRTUAL = new ErrorCode(1_040_700_006, "铏氭嫙浠撳簱涓嶅厑璁告搷浣?");

    // ========== MES 浠撳簱绠＄悊-搴撳尯锛?-040-701-000锛?==========
    ErrorCode WM_WAREHOUSE_LOCATION_NOT_EXISTS = new ErrorCode(1_040_701_000, "搴撳尯涓嶅瓨鍦?");
    ErrorCode WM_WAREHOUSE_LOCATION_CODE_DUPLICATE = new ErrorCode(1_040_701_001, "鍚屼竴浠撳簱涓嬪簱鍖虹紪鐮佸凡瀛樺湪");
    ErrorCode WM_WAREHOUSE_LOCATION_NAME_DUPLICATE = new ErrorCode(1_040_701_002, "鍚屼竴浠撳簱涓嬪簱鍖哄悕绉板凡瀛樺湪");
    ErrorCode WM_WAREHOUSE_LOCATION_HAS_AREA = new ErrorCode(1_040_701_003, "搴撳尯涓嬪瓨鍦ㄥ簱浣嶏紝鏃犳硶鍒犻櫎");
    ErrorCode WM_WAREHOUSE_LOCATION_HAS_WORKSTATION = new ErrorCode(1_040_701_004, "搴撳尯宸茶宸ヤ綔绔欏紩鐢紝鏃犳硶鍒犻櫎");
    ErrorCode WM_WAREHOUSE_REQUIRED = new ErrorCode(1_040_701_005, "閫夋嫨搴撳尯鏃讹紝浠撳簱涓嶈兘涓虹┖");
    ErrorCode WM_WAREHOUSE_LOCATION_RELATION_INVALID = new ErrorCode(1_040_701_006, "搴撳尯涓嶅睘浜庢墍閫変粨搴?");
    ErrorCode WM_WAREHOUSE_LOCATION_HAS_MATERIAL_STOCK = new ErrorCode(1_040_701_007, "搴撳尯涓嬫湁搴撳瓨璁板綍锛屾棤娉曞垹闄?");
    ErrorCode WM_WAREHOUSE_LOCATION_IS_VIRTUAL = new ErrorCode(1_040_701_008, "铏氭嫙搴撳尯涓嶅厑璁告搷浣?");

    // ========== MES 浠撳簱绠＄悊-搴撲綅锛?-040-702-000锛?==========
    ErrorCode WM_WAREHOUSE_AREA_NOT_EXISTS = new ErrorCode(1_040_702_000, "库位不存在");
    ErrorCode WM_WAREHOUSE_AREA_CODE_DUPLICATE = new ErrorCode(1_040_702_001, "同一库区下库位编码已存在");
    ErrorCode WM_WAREHOUSE_AREA_NAME_DUPLICATE = new ErrorCode(1_040_702_002, "同一库区下库位名称已存在");
    ErrorCode WM_WAREHOUSE_AREA_HAS_WORKSTATION = new ErrorCode(1_040_702_003, "库位已被工作站引用，无法删除");
    ErrorCode WM_WAREHOUSE_LOCATION_REQUIRED = new ErrorCode(1_040_702_004, "閫夋嫨搴撲綅鏃讹紝搴撳尯涓嶈兘涓虹┖");
    ErrorCode WM_WAREHOUSE_AREA_RELATION_INVALID = new ErrorCode(1_040_702_005, "库位不属于所选库区");
    ErrorCode WM_WAREHOUSE_AREA_HAS_MATERIAL_STOCK = new ErrorCode(1_040_702_006, "库位下有库存记录，无法删除");
    ErrorCode WM_WAREHOUSE_AREA_WAREHOUSE_MISMATCH = new ErrorCode(1_040_702_007, "库位不属于所选仓库");
    ErrorCode WM_WAREHOUSE_AREA_IS_VIRTUAL = new ErrorCode(1_040_702_008, "虚拟库位不允许操作");

    // ========== MES 浠撳簱绠＄悊-搴撳瓨锛?-040-703-000锛?==========
    ErrorCode WM_MATERIAL_STOCK_NOT_EXISTS = new ErrorCode(1_040_703_000, "搴撳瓨璁板綍涓嶅瓨鍦?");
    ErrorCode WM_MATERIAL_STOCK_INSUFFICIENT = new ErrorCode(1_040_703_001, "搴撳瓨鏁伴噺涓嶈冻");
    ErrorCode WM_TRANSACTION_TYPE_NOT_EXISTS = new ErrorCode(1_040_703_002, "搴撳瓨浜嬪姟绫诲瀷涓嶅瓨鍦?");
    ErrorCode WM_TRANSACTION_WAREHOUSE_FROZEN = new ErrorCode(1_040_703_003, "浠撳簱({})宸茶鍐荤粨");
    ErrorCode WM_TRANSACTION_LOCATION_FROZEN = new ErrorCode(1_040_703_004, "搴撳尯({})宸茶鍐荤粨");
    ErrorCode WM_TRANSACTION_AREA_FROZEN = new ErrorCode(1_040_703_005, "搴撲綅({})宸茶鍐荤粨");
    ErrorCode WM_TRANSACTION_STOCK_FROZEN = new ErrorCode(1_040_703_006, "瀛樻斁浜?{}/{}/{})涓嬬殑鐗╂枡宸茶鍐荤粨");
    ErrorCode WM_TRANSACTION_BATCH_REQUIRED = new ErrorCode(1_040_703_007, "褰撳墠鐗╂枡鍚敤浜嗘壒娆＄鐞嗭紝鎵规鍙蜂笉鑳戒负绌?");
    ErrorCode WM_MATERIAL_STOCK_AREA_ITEM_MIXING_NOT_ALLOWED = new ErrorCode(1_040_703_008, "搴撲綅({})涓嶅厑璁哥墿鏂欐贩鏀撅紝璇烽€夋嫨鍏朵粬搴撲綅");
    ErrorCode WM_MATERIAL_STOCK_AREA_BATCH_MIXING_NOT_ALLOWED = new ErrorCode(1_040_703_009, "搴撲綅({})涓嶅厑璁告壒娆℃贩鏀撅紝璇烽€夋嫨鍏朵粬搴撲綅");
    ErrorCode WM_TRANSACTION_RELATED_NOT_EXISTS = new ErrorCode(1_040_703_010, "鍏宠仈鐨勫簱瀛樹簨鍔′笉瀛樺湪");
    ErrorCode WM_TRANSACTION_LIST_EMPTY = new ErrorCode(1_040_703_011, "搴撳瓨浜嬪姟鍒楄〃涓嶈兘涓虹┖");
    ErrorCode WM_TRANSACTION_BATCH_NOT_EXISTS = new ErrorCode(1_040_703_012, "鎵规璁板綍涓嶅瓨鍦?");
    ErrorCode WM_MATERIAL_STOCK_REQUIRED = new ErrorCode(1_040_703_013, "搴撳瓨璁板綍涓嶈兘涓虹┖");
    ErrorCode WM_MATERIAL_STOCK_SELECTION_MISMATCH = new ErrorCode(1_040_703_014, "搴撳瓨璁板綍涓庢彁浜ょ殑鐗╂枡銆佹壒娆℃垨搴撲綅淇℃伅涓嶄竴鑷?");

    // ========== MES 浠撳簱绠＄悊-鍒拌揣閫氱煡鍗曪紙1-040-704-000锛?==========
    ErrorCode WM_ARRIVAL_NOTICE_NOT_EXISTS = new ErrorCode(1_040_704_000, "鍒拌揣閫氱煡鍗曚笉瀛樺湪");
    ErrorCode WM_ARRIVAL_NOTICE_CODE_DUPLICATE = new ErrorCode(1_040_704_001, "鍒拌揣閫氱煡鍗曠紪鐮佸凡瀛樺湪");
    ErrorCode WM_ARRIVAL_NOTICE_STATUS_NOT_PREPARE = new ErrorCode(1_040_704_002, "鍙湁鑽夌鐘舵€佹墠鍏佽姝ゆ搷浣?");
    ErrorCode WM_ARRIVAL_NOTICE_STATUS_NOT_PENDING_QC = new ErrorCode(1_040_704_003, "鍙湁寰呰川妫€鐘舵€佹墠鍏佽瀹℃壒");
    ErrorCode WM_ARRIVAL_NOTICE_STATUS_NOT_PENDING_RECEIPT = new ErrorCode(1_040_704_004, "鍙湁寰呭叆搴撶姸鎬佹墠鍏佽瀹屾垚");
    ErrorCode WM_ARRIVAL_NOTICE_IQC_PENDING = new ErrorCode(1_040_704_005, "瀛樺湪寰呮楠岃锛屾棤娉曞鎵归€氳繃");
    ErrorCode WM_ARRIVAL_NOTICE_NO_LINE = new ErrorCode(1_040_704_006, "鑷冲皯闇€瑕佷竴鏉¤椤圭洰");
    ErrorCode WM_ARRIVAL_NOTICE_VENDOR_MISMATCH = new ErrorCode(1_040_704_007, "鍒拌揣閫氱煡鍗曠殑渚涘簲鍟嗕笌褰撳墠鍗曟嵁涓嶄竴鑷?");
    ErrorCode WM_ARRIVAL_NOTICE_LINE_NOT_EXISTS = new ErrorCode(1_040_704_100, "鍒拌揣閫氱煡鍗曡涓嶅瓨鍦?");
    ErrorCode WM_ARRIVAL_NOTICE_LINE_NOT_MATCH = new ErrorCode(1_040_704_101, "鍒拌揣閫氱煡鍗曡涓嶅睘浜庢寚瀹氱殑鍒拌揣閫氱煡鍗?");

    // ========== MES 浠撳簱绠＄悊-閲囪喘鍏ュ簱鍗曪紙1-040-705-000锛?==========
    ErrorCode WM_ITEM_RECEIPT_NOT_EXISTS = new ErrorCode(1_040_705_000, "閲囪喘鍏ュ簱鍗曚笉瀛樺湪");
    ErrorCode WM_ITEM_RECEIPT_CODE_DUPLICATE = new ErrorCode(1_040_705_001, "閲囪喘鍏ュ簱鍗曠紪鐮佸凡瀛樺湪");
    ErrorCode WM_ITEM_RECEIPT_STATUS_NOT_PREPARE = new ErrorCode(1_040_705_002, "鍙湁鑽夌鎴栧緟涓婃灦鐘舵€佹墠鍏佽姝ゆ搷浣?");
    ErrorCode WM_ITEM_RECEIPT_NO_LINE = new ErrorCode(1_040_705_003, "鑷冲皯闇€瑕佷竴鏉¤椤圭洰");
    ErrorCode WM_ITEM_RECEIPT_DETAIL_QUANTITY_MISMATCH = new ErrorCode(1_040_705_004, "鏄庣粏涓婃灦鎬绘暟涓庤鍏ュ簱鏁伴噺涓嶅尮閰?");
    ErrorCode WM_ITEM_RECEIPT_STATUS_ERROR = new ErrorCode(1_040_705_005, "鍏ュ簱鍗曠姸鎬佷笉姝ｇ‘");
    ErrorCode WM_ITEM_RECEIPT_CANCEL_NOT_ALLOWED = new ErrorCode(1_040_705_006, "宸插畬鎴愭垨宸插彇娑堢殑鍏ュ簱鍗曚笉鍏佽鍙栨秷");
    ErrorCode WM_ITEM_RECEIPT_LINE_NOT_EXISTS = new ErrorCode(1_040_705_100, "閲囪喘鍏ュ簱鍗曡涓嶅瓨鍦?");
    ErrorCode WM_ITEM_RECEIPT_LINE_ARRIVAL_NOTICE_LINE_REQUIRED = new ErrorCode(1_040_705_101,
            "鍏ュ簱鍗曞叧鑱斾簡鍒拌揣閫氱煡鍗曪紝蹇呴』閫夋嫨鍒拌揣閫氱煡鍗曡");
    ErrorCode WM_ITEM_RECEIPT_LINE_ARRIVAL_NOTICE_LINE_NOT_ALLOWED = new ErrorCode(1_040_705_102,
            "鍏ュ簱鍗曟湭鍏宠仈鍒拌揣閫氱煡鍗曪紝涓嶈兘閫夋嫨鍒拌揣閫氱煡鍗曡");
    ErrorCode WM_ITEM_RECEIPT_DETAIL_NOT_EXISTS = new ErrorCode(1_040_705_200, "閲囪喘鍏ュ簱鏄庣粏涓嶅瓨鍦?");
    ErrorCode WM_ITEM_RECEIPT_DETAIL_QUANTITY_EXCEED = new ErrorCode(1_040_705_202, "涓婃灦鏄庣粏鎬绘暟閲忎笉鑳借秴杩囪鍏ュ簱鏁伴噺");

    // ========== MES 浠撳簱绠＄悊-棰嗘枡鐢宠鍗曪紙1-040-706-000锛?==========
    ErrorCode WM_MATERIAL_REQUEST_NOT_EXISTS = new ErrorCode(1_040_706_000, "棰嗘枡鐢宠鍗曚笉瀛樺湪");
    ErrorCode WM_MATERIAL_REQUEST_STATUS_INVALID = new ErrorCode(1_040_706_001, "棰嗘枡鐢宠鍗曠姸鎬佷笉姝ｇ‘锛屾棤娉曟墽琛岃鎿嶄綔");
    ErrorCode WM_MATERIAL_REQUEST_LINE_NOT_EXISTS = new ErrorCode(1_040_706_100, "棰嗘枡鐢宠鍗曡涓嶅瓨鍦?");

    // ========== MES 浠撳簱绠＄悊-澶栧崗鍙戞枡鍗曪紙1-040-707-000锛?==========
    ErrorCode WM_OUTSOURCE_ISSUE_NOT_EXISTS = new ErrorCode(1_040_707_000, "澶栧崗鍙戞枡鍗曚笉瀛樺湪");
    ErrorCode WM_OUTSOURCE_ISSUE_CODE_DUPLICATE = new ErrorCode(1_040_707_001, "澶栧崗鍙戞枡鍗曠紪鐮佸凡瀛樺湪");
    ErrorCode WM_OUTSOURCE_ISSUE_STATUS_NOT_PREPARE = new ErrorCode(1_040_707_002, "鍙湁鑽夌鐘舵€佹墠鍏佽姝ゆ搷浣?");
    ErrorCode WM_OUTSOURCE_ISSUE_NO_LINE = new ErrorCode(1_040_707_003, "鑷冲皯闇€瑕佷竴鏉″彂鏂欒");
    ErrorCode WM_OUTSOURCE_ISSUE_QUANTITY_MISMATCH = new ErrorCode(1_040_707_004, "鍙戞枡鍗曡鏁伴噺涓庢槑缁嗘暟閲忎笉涓€鑷?");
    ErrorCode WM_OUTSOURCE_ISSUE_STATUS_NOT_APPROVING = new ErrorCode(1_040_707_005, "鍙湁寰呮嫞璐х姸鎬佹墠鍏佽姝ゆ搷浣?");
    ErrorCode WM_OUTSOURCE_ISSUE_STATUS_NOT_APPROVED = new ErrorCode(1_040_707_006, "鍙湁寰呮墽琛屽嚭搴撶姸鎬佹墠鍏佽姝ゆ搷浣?");
    ErrorCode WM_OUTSOURCE_ISSUE_CANCEL_NOT_ALLOWED = new ErrorCode(1_040_707_007, "宸插畬鎴愭垨宸插彇娑堢殑鍙戞枡鍗曚笉鍏佽鍙栨秷");
    ErrorCode WM_OUTSOURCE_ISSUE_LINE_NOT_EXISTS = new ErrorCode(1_040_707_100, "澶栧崗鍙戞枡鍗曡涓嶅瓨鍦?");
    ErrorCode WM_OUTSOURCE_ISSUE_LINE_ITEM_NOT_IN_BOM = new ErrorCode(1_040_707_101, "鍙戞枡鍗曡瀵瑰簲鐨勭墿鏂欎笉鍦ㄥ綋鍓嶅伐鍗曠殑 BOM 鍒楄〃涓?");
    ErrorCode WM_OUTSOURCE_ISSUE_DETAIL_NOT_EXISTS = new ErrorCode(1_040_707_200, "澶栧崗鍙戞枡鍗曟槑缁嗕笉瀛樺湪");
    ErrorCode WM_OUTSOURCE_ISSUE_DETAIL_LINE_NOT_MATCH = new ErrorCode(1_040_707_201, "鎷ｈ揣鏄庣粏涓嶅睘浜庢寚瀹氱殑澶栧崗鍙戞枡鍗?");
    ErrorCode WM_OUTSOURCE_ISSUE_DETAIL_ITEM_MISMATCH = new ErrorCode(1_040_707_202, "鎷ｈ揣鏄庣粏鐨勭墿鏂欎笌澶栧崗鍙戞枡鍗曡鐨勭墿鏂欎笉涓€鑷?");
    ErrorCode WM_OUTSOURCE_ISSUE_WORK_ORDER_TYPE_INVALID = new ErrorCode(1_040_707_008, "宸ュ崟绫诲瀷涓嶆槸澶栧崗锛堜唬宸ワ級绫诲瀷");

    // ========== MES 浠撳簱绠＄悊-鐢熶骇棰嗘枡鍑哄簱鍗曪紙1-040-708-000锛?==========
    ErrorCode WM_PRODUCT_ISSUE_NOT_EXISTS = new ErrorCode(1_040_708_000, "鐢熶骇棰嗘枡鍑哄簱鍗曚笉瀛樺湪");
    ErrorCode WM_PRODUCT_ISSUE_STATUS_INVALID = new ErrorCode(1_040_708_001, "鐢熶骇棰嗘枡鍑哄簱鍗曠姸鎬佷笉姝ｇ‘锛屾棤娉曟墽琛岃鎿嶄綔");
    ErrorCode WM_PRODUCT_ISSUE_NO_LINE = new ErrorCode(1_040_708_002, "鐢熶骇棰嗘枡鍑哄簱鍗曡嚦灏戦渶瑕佷竴鏉¤鏁版嵁");
    ErrorCode WM_PRODUCT_ISSUE_DETAIL_QUANTITY_MISMATCH = new ErrorCode(1_040_708_003, "棰嗘枡鍑哄簱鍗曡鏁伴噺涓庢槑缁嗘暟閲忎笉涓€鑷?");
    ErrorCode WM_PRODUCT_ISSUE_WORKORDER_NOT_EXISTS = new ErrorCode(1_040_708_004, "鐢熶骇宸ュ崟涓嶅瓨鍦?");
    ErrorCode WM_PRODUCT_ISSUE_WORKSTATION_NOT_EXISTS = new ErrorCode(1_040_708_005, "宸ヤ綔绔欎笉瀛樺湪");
    ErrorCode WM_PRODUCT_ISSUE_CANCEL_NOT_ALLOWED = new ErrorCode(1_040_708_006, "鐢熶骇棰嗘枡鍑哄簱鍗曞凡瀹屾垚鎴栧凡鍙栨秷锛屾棤娉曞彇娑?");
    ErrorCode WM_PRODUCT_ISSUE_LINE_NOT_EXISTS = new ErrorCode(1_040_708_100, "鐢熶骇棰嗘枡鍑哄簱鍗曡涓嶅瓨鍦?");
    ErrorCode WM_PRODUCT_ISSUE_LINE_ITEM_NOT_IN_BOM = new ErrorCode(1_040_708_101, "褰撳墠鐗╂枡涓嶅湪鐢熶骇宸ュ崟鐨?BOM 鐗╂枡娓呭崟涓?");
    ErrorCode WM_PRODUCT_ISSUE_CODE_DUPLICATE = new ErrorCode(1_040_708_102, "棰嗘枡鍑哄簱鍗曠紪鐮佸凡瀛樺湪");
    ErrorCode WM_PRODUCT_ISSUE_DETAIL_NOT_EXISTS = new ErrorCode(1_040_708_200, "鐢熶骇棰嗘枡鍑哄簱鍗曟槑缁嗕笉瀛樺湪");
    ErrorCode WM_PRODUCT_ISSUE_DETAIL_ITEM_MISMATCH = new ErrorCode(1_040_708_201, "鎷ｈ揣鏄庣粏鐨勭墿鏂欎笌棰嗘枡鍗曡鐨勭墿鏂欎笉涓€鑷?");
    ErrorCode WM_PRODUCT_ISSUE_DETAIL_LINE_NOT_MATCH = new ErrorCode(1_040_708_202, "鎷ｈ揣鏄庣粏涓嶅睘浜庢寚瀹氱殑棰嗘枡鍑哄簱鍗?");
    ErrorCode WM_PRODUCT_ISSUE_NO_DETAIL = new ErrorCode(1_040_708_203, "棰嗘枡鍑哄簱鍗曟病鏈夋嫞璐ф槑缁嗭紝鏃犳硶鎵ц棰嗗嚭");

    // ========== MES 浠撳簱绠＄悊-鐢熶骇鍏ュ簱鍗曪紙1-040-709-000锛?==========
    ErrorCode WM_PRODUCT_PRODUCE_NOT_EXISTS = new ErrorCode(1_040_709_000, "鐢熶骇鍏ュ簱鍗曚笉瀛樺湪");
    ErrorCode WM_PRODUCT_PRODUCE_STATUS_INVALID = new ErrorCode(1_040_709_001, "鐢熶骇鍏ュ簱鍗曠姸鎬佷笉姝ｇ‘锛屾棤娉曟墽琛岃鎿嶄綔");
    ErrorCode WM_PRODUCT_PRODUCE_NO_LINE = new ErrorCode(1_040_709_002, "鐢熶骇鍏ュ簱鍗曡嚦灏戦渶瑕佷竴鏉¤鏁版嵁");
    ErrorCode WM_PRODUCT_PRODUCE_DETAIL_QUANTITY_MISMATCH = new ErrorCode(1_040_709_003, "鐢熶骇鍏ュ簱鍗曡鏁伴噺涓庢槑缁嗘暟閲忎笉涓€鑷?");
    ErrorCode WM_PRODUCT_PRODUCE_CANCEL_NOT_ALLOWED = new ErrorCode(1_040_709_004, "鐢熶骇鍏ュ簱鍗曞凡瀹屾垚鎴栧凡鍙栨秷锛屾棤娉曞彇娑?");
    ErrorCode WM_PRODUCT_PRODUCE_LINE_NOT_EXISTS = new ErrorCode(1_040_709_100, "鐢熶骇鍏ュ簱鍗曡涓嶅瓨鍦?");
    ErrorCode WM_PRODUCT_PRODUCE_DETAIL_NOT_EXISTS = new ErrorCode(1_040_709_200, "鐢熶骇鍏ュ簱鍗曟槑缁嗕笉瀛樺湪");

    // ========== MES 浠撳簱绠＄悊-杞Щ璋冩嫧锛?-040-710-000锛?==========
    ErrorCode WM_TRANSFER_NOT_EXISTS = new ErrorCode(1_040_710_000, "杞Щ鍗曚笉瀛樺湪");
    ErrorCode WM_TRANSFER_NOT_EDITABLE = new ErrorCode(1_040_710_001, "褰撳墠杞Щ鍗曠姸鎬佷笉鍏佽缂栬緫");
    ErrorCode WM_TRANSFER_CODE_DUPLICATE = new ErrorCode(1_040_710_002, "杞Щ鍗曠紪鍙峰凡瀛樺湪");
    ErrorCode WM_TRANSFER_NOT_DRAFT = new ErrorCode(1_040_710_003, "鍙湁鑽夌鐘舵€佺殑杞Щ鍗曟墠鍙搷浣?");
    ErrorCode WM_TRANSFER_NOT_CONFIRMED = new ErrorCode(1_040_710_004, "鍙湁寰呯‘璁ょ姸鎬佺殑杞Щ鍗曟墠鍙墽琛岀‘璁?");
    ErrorCode WM_TRANSFER_NOT_APPROVING = new ErrorCode(1_040_710_005, "鍙湁寰呬笂鏋剁姸鎬佺殑杞Щ鍗曟墠鍙墽琛屼笂鏋?");
    ErrorCode WM_TRANSFER_NOT_APPROVED = new ErrorCode(1_040_710_006, "鍙湁寰呮墽琛岀姸鎬佺殑杞Щ鍗曟墠鍙畬鎴?");
    ErrorCode WM_TRANSFER_ALREADY_FINISHED = new ErrorCode(1_040_710_007, "杞Щ鍗曞凡瀹屾垚鎴栧凡鍙栨秷锛屾棤娉曠户缁搷浣?");
    ErrorCode WM_TRANSFER_NO_LINE = new ErrorCode(1_040_710_008, "杞Щ鍗曡嚦灏戦渶瑕佷竴鏉¤鏁版嵁");
    ErrorCode WM_TRANSFER_DETAIL_QUANTITY_MISMATCH = new ErrorCode(1_040_710_009, "杞Щ鍗曡鏁伴噺涓庢槑缁嗘暟閲忎笉涓€鑷?");
    ErrorCode WM_TRANSFER_LINE_NOT_EXISTS = new ErrorCode(1_040_710_100, "杞Щ鍗曡涓嶅瓨鍦?");
    ErrorCode WM_TRANSFER_LINE_QUANTITY_EXCEED_STOCK = new ErrorCode(1_040_710_101, "杞Щ鏁伴噺涓嶈兘瓒呰繃搴撳瓨鏁伴噺");
    ErrorCode WM_TRANSFER_DETAIL_NOT_EXISTS = new ErrorCode(1_040_710_200, "璋冩嫧鏄庣粏涓嶅瓨鍦?");
    ErrorCode WM_TRANSFER_DETAIL_QUANTITY_EXCEED = new ErrorCode(1_040_710_201, "璋冩嫧鏄庣粏鎬绘暟閲忎笉鑳借秴杩囪皟鎷ㄥ崟琛屾暟閲?");
    ErrorCode WM_TRANSFER_DETAIL_MIXED_GOODS = new ErrorCode(1_040_710_202, "鍚屼竴鐩爣浠撲綅涓嬪凡瀛樺湪鍏朵粬鐗╂枡鐨勬槑缁嗭紝涓嶅厑璁告贩璐?");

    // ========== MES 浠撳簱绠＄悊-鐢熶骇閫€鏂欏崟锛?-040-711-000锛?==========
    ErrorCode WM_RETURN_ISSUE_NOT_EXISTS = new ErrorCode(1_040_710_000, "鐢熶骇閫€鏂欏崟涓嶅瓨鍦?");
    ErrorCode WM_RETURN_ISSUE_STATUS_INVALID = new ErrorCode(1_040_710_001, "鐢熶骇閫€鏂欏崟鐘舵€佷笉姝ｇ‘锛屾棤娉曟墽琛岃鎿嶄綔");
    ErrorCode WM_RETURN_ISSUE_NOT_PREPARE = new ErrorCode(1_040_710_002, "鍙湁鑽夌鐘舵€佺殑閫€鏂欏崟鎵嶅彲鎿嶄綔");
    ErrorCode WM_RETURN_ISSUE_NOT_CONFIRMED = new ErrorCode(1_040_710_003, "鍙湁寰呮楠岀姸鎬佺殑閫€鏂欏崟鎵嶅彲鎻愪氦");
    ErrorCode WM_RETURN_ISSUE_NOT_APPROVING = new ErrorCode(1_040_710_004, "鍙湁寰呬笂鏋剁姸鎬佺殑閫€鏂欏崟鎵嶅彲鍏ュ簱涓婃灦");
    ErrorCode WM_RETURN_ISSUE_NOT_APPROVED = new ErrorCode(1_040_710_005, "鍙湁寰呮墽琛岄€€鏂欑姸鎬佺殑閫€鏂欏崟鎵嶅彲瀹屾垚");
    ErrorCode WM_RETURN_ISSUE_NO_LINE = new ErrorCode(1_040_710_006, "鐢熶骇閫€鏂欏崟鑷冲皯闇€瑕佷竴鏉¤鏁版嵁");
    ErrorCode WM_RETURN_ISSUE_DETAIL_QUANTITY_MISMATCH = new ErrorCode(1_040_710_007, "閫€鏂欏崟琛屾暟閲忎笌鏄庣粏鏁伴噺涓嶄竴鑷?");
    ErrorCode WM_RETURN_ISSUE_CANCEL_NOT_ALLOWED = new ErrorCode(1_040_710_008, "鐢熶骇閫€鏂欏崟宸插畬鎴愭垨宸插彇娑堬紝鏃犳硶鍙栨秷");
    ErrorCode WM_RETURN_ISSUE_LINE_NOT_EXISTS = new ErrorCode(1_040_710_100, "鐢熶骇閫€鏂欏崟琛屼笉瀛樺湪");
    ErrorCode WM_RETURN_ISSUE_DETAIL_NOT_EXISTS = new ErrorCode(1_040_710_200, "鐢熶骇閫€鏂欏崟鏄庣粏涓嶅瓨鍦?");
    ErrorCode WM_RETURN_ISSUE_DETAIL_QUANTITY_INVALID = new ErrorCode(1_040_710_201, "閫€鏂欐槑缁嗘暟閲忓繀椤诲ぇ浜?");
    ErrorCode WM_RETURN_ISSUE_DETAIL_QUANTITY_EXCEED = new ErrorCode(1_040_710_202, "閫€鏂欐槑缁嗘€绘暟閲忎笉鑳借秴杩囬€€鏂欏崟琛屾暟閲?");
    ErrorCode WM_RETURN_ISSUE_CODE_DUPLICATE = new ErrorCode(1_040_710_203, "閫€鏂欏崟缂栫爜宸插瓨鍦?");
    ErrorCode WM_RETURN_ISSUE_DETAIL_LINE_NOT_MATCH = new ErrorCode(1_040_710_204, "閫€鏂欐槑缁嗕笉灞炰簬鎸囧畾鐨勯€€鏂欏崟");
    ErrorCode WM_RETURN_ISSUE_DETAIL_ITEM_MISMATCH = new ErrorCode(1_040_710_205, "閫€鏂欐槑缁嗙殑鐗╂枡涓庨€€鏂欏崟琛岀殑鐗╂枡涓嶄竴鑷?");

    // ========== MES 浠撳簱绠＄悊-渚涘簲鍟嗛€€璐у崟锛?-040-711-000锛?==========
    ErrorCode WM_RETURN_VENDOR_NOT_EXISTS = new ErrorCode(1_040_711_000, "渚涘簲鍟嗛€€璐у崟涓嶅瓨鍦?");
    ErrorCode WM_RETURN_VENDOR_STATUS_INVALID = new ErrorCode(1_040_711_001, "渚涘簲鍟嗛€€璐у崟鐘舵€佷笉姝ｇ‘锛屾棤娉曟墽琛岃鎿嶄綔");
    ErrorCode WM_RETURN_VENDOR_NO_LINE = new ErrorCode(1_040_711_002, "渚涘簲鍟嗛€€璐у崟鑷冲皯闇€瑕佷竴鏉¤鏁版嵁");
    ErrorCode WM_RETURN_VENDOR_DETAIL_QUANTITY_MISMATCH = new ErrorCode(1_040_711_003, "渚涘簲鍟嗛€€璐у崟琛屾暟閲忎笌鏄庣粏鏁伴噺涓嶄竴鑷?");
    ErrorCode WM_RETURN_VENDOR_CANCEL_NOT_ALLOWED = new ErrorCode(1_040_711_004, "渚涘簲鍟嗛€€璐у崟宸插畬鎴愭垨宸插彇娑堬紝鏃犳硶鍙栨秷");
    ErrorCode WM_RETURN_VENDOR_CODE_DUPLICATE = new ErrorCode(1_040_711_005, "渚涘簲鍟嗛€€璐у崟缂栧彿宸插瓨鍦?");
    ErrorCode WM_RETURN_VENDOR_NO_DETAIL = new ErrorCode(1_040_711_006, "渚涘簲鍟嗛€€璐у崟娌℃湁鎷ｈ揣鏄庣粏锛屾棤娉曟墽琛岄€€璐?");
    ErrorCode WM_RETURN_VENDOR_DETAIL_ITEM_MISMATCH = new ErrorCode(1_040_711_007, "鎷ｈ揣鏄庣粏鐨勭墿鏂欎笌閫€璐у崟琛岀殑鐗╂枡涓嶄竴鑷?");
    ErrorCode WM_RETURN_VENDOR_LINE_NOT_EXISTS = new ErrorCode(1_040_711_100, "渚涘簲鍟嗛€€璐у崟琛屼笉瀛樺湪");
    ErrorCode WM_RETURN_VENDOR_DETAIL_NOT_EXISTS = new ErrorCode(1_040_711_200, "渚涘簲鍟嗛€€璐у崟鏄庣粏涓嶅瓨鍦?");
    ErrorCode WM_RETURN_VENDOR_DETAIL_QUANTITY_INVALID = new ErrorCode(1_040_711_201, "閫€璐ф槑缁嗘暟閲忓繀椤诲ぇ浜?0");
    ErrorCode WM_RETURN_VENDOR_DETAIL_LINE_NOT_MATCH = new ErrorCode(1_040_711_202, "鎷ｈ揣鏄庣粏涓嶅睘浜庢寚瀹氱殑渚涘簲鍟嗛€€璐у崟");

    // ========== MES 浠撳簱绠＄悊-浜у搧鏀惰揣鍗曪紙1-040-712-000锛?==========
    ErrorCode WM_PRODUCT_RECPT_NOT_EXISTS = new ErrorCode(1_040_712_000, "浜у搧鏀惰揣鍗曚笉瀛樺湪");
    ErrorCode WM_PRODUCT_RECPT_CODE_DUPLICATE = new ErrorCode(1_040_712_001, "浜у搧鏀惰揣鍗曠紪鐮佸凡瀛樺湪");
    ErrorCode WM_PRODUCT_RECPT_STATUS_NOT_PREPARE = new ErrorCode(1_040_712_002, "鍙湁鑽夌鎴栧緟涓婃灦鐘舵€佹墠鍏佽姝ゆ搷浣?");
    ErrorCode WM_PRODUCT_RECPT_NO_LINE = new ErrorCode(1_040_712_003, "鑷冲皯闇€瑕佷竴鏉¤椤圭洰");
    ErrorCode WM_PRODUCT_RECPT_DETAIL_QUANTITY_MISMATCH = new ErrorCode(1_040_712_004, "鏄庣粏涓婃灦鎬绘暟涓庤鏀惰揣鏁伴噺涓嶅尮閰?");
    ErrorCode WM_PRODUCT_RECPT_STATUS_ERROR = new ErrorCode(1_040_712_005, "鏀惰揣鍗曠姸鎬佷笉姝ｇ‘");
    ErrorCode WM_PRODUCT_RECPT_CANCEL_NOT_ALLOWED = new ErrorCode(1_040_712_006, "宸插畬鎴愭垨宸插彇娑堢殑鏀惰揣鍗曚笉鍏佽鍙栨秷");
    ErrorCode WM_PRODUCT_RECPT_NO_DETAIL = new ErrorCode(1_040_712_007, "鏀惰揣鍗曟病鏈変笂鏋舵槑缁嗭紝鏃犳硶鎵ц鍏ュ簱");
    ErrorCode WM_PRODUCT_RECPT_LINE_NOT_EXISTS = new ErrorCode(1_040_712_100, "浜у搧鏀惰揣鍗曡涓嶅瓨鍦?");
    ErrorCode WM_PRODUCT_RECPT_DETAIL_NOT_EXISTS = new ErrorCode(1_040_712_200, "浜у搧鏀惰揣鏄庣粏涓嶅瓨鍦?");

    // ========== MES 浠撳簱绠＄悊-澶栧崗鍏ュ簱鍗曪紙1-040-713-000锛?==========
    ErrorCode WM_OUTSOURCE_RECEIPT_NOT_EXISTS = new ErrorCode(1_040_713_000, "澶栧崗鍏ュ簱鍗曚笉瀛樺湪");
    ErrorCode WM_OUTSOURCE_RECEIPT_CODE_DUPLICATE = new ErrorCode(1_040_713_001, "澶栧崗鍏ュ簱鍗曠紪鐮佸凡瀛樺湪");
    ErrorCode WM_OUTSOURCE_RECEIPT_STATUS_NOT_PREPARE = new ErrorCode(1_040_713_002, "鍙湁鑽夌鐘舵€佹墠鍏佽姝ゆ搷浣?");
    ErrorCode WM_OUTSOURCE_RECEIPT_NO_LINE = new ErrorCode(1_040_713_003, "鑷冲皯闇€瑕佷竴鏉¤椤圭洰");
    ErrorCode WM_OUTSOURCE_RECEIPT_DETAIL_QUANTITY_MISMATCH = new ErrorCode(1_040_713_004, "鏄庣粏涓婃灦鎬绘暟涓庤鍏ュ簱鏁伴噺涓嶅尮閰?");
    ErrorCode WM_OUTSOURCE_RECEIPT_STATUS_ERROR = new ErrorCode(1_040_713_005, "鍏ュ簱鍗曠姸鎬佷笉姝ｇ‘");
    ErrorCode WM_OUTSOURCE_RECEIPT_CANCEL_NOT_ALLOWED = new ErrorCode(1_040_713_006, "宸插畬鎴愭垨宸插彇娑堢殑鍏ュ簱鍗曚笉鍏佽鍙栨秷");
    ErrorCode WM_OUTSOURCE_RECEIPT_LINE_NOT_EXISTS = new ErrorCode(1_040_713_100, "澶栧崗鍏ュ簱鍗曡涓嶅瓨鍦?");
    ErrorCode WM_OUTSOURCE_RECEIPT_DETAIL_NOT_EXISTS = new ErrorCode(1_040_713_200, "澶栧崗鍏ュ簱鏄庣粏涓嶅瓨鍦?");

    // ========== MES 浠撳簱绠＄悊-閿€鍞€€璐у崟锛?-040-713-000锛?==========
    ErrorCode WM_RETURN_SALES_NOT_EXISTS = new ErrorCode(1_040_713_000, "閿€鍞€€璐у崟涓嶅瓨鍦?");
    ErrorCode WM_RETURN_SALES_CODE_DUPLICATE = new ErrorCode(1_040_713_001, "閿€鍞€€璐у崟缂栫爜宸插瓨鍦?");
    ErrorCode WM_RETURN_SALES_STATUS_NOT_PREPARE = new ErrorCode(1_040_713_002, "鍙湁鑽夌鐘舵€佹墠鍏佽姝ゆ搷浣?");
    ErrorCode WM_RETURN_SALES_STATUS_NOT_APPROVING = new ErrorCode(1_040_713_003, "鍙湁寰呮墽琛岀姸鎬佹墠鍏佽鎵ц閫€璐?");
    ErrorCode WM_RETURN_SALES_STATUS_NOT_APPROVED = new ErrorCode(1_040_713_004, "鍙湁寰呬笂鏋剁姸鎬佹墠鍏佽鎵ц涓婃灦");
    ErrorCode WM_RETURN_SALES_NO_LINE = new ErrorCode(1_040_713_005, "閿€鍞€€璐у崟鑷冲皯闇€瑕佷竴鏉¤鏁版嵁");
    ErrorCode WM_RETURN_SALES_DETAIL_QUANTITY_MISMATCH = new ErrorCode(1_040_713_006, "閿€鍞€€璐у崟琛屾暟閲忎笌鏄庣粏鏁伴噺涓嶄竴鑷?");
    ErrorCode WM_RETURN_SALES_CANCEL_NOT_ALLOWED = new ErrorCode(1_040_713_007, "閿€鍞€€璐у崟宸插畬鎴愭垨宸插彇娑堬紝鏃犳硶鍙栨秷");
    ErrorCode WM_RETURN_SALES_LINE_NOT_EXISTS = new ErrorCode(1_040_713_100, "閿€鍞€€璐у崟琛屼笉瀛樺湪");
    ErrorCode WM_RETURN_SALES_DETAIL_NOT_EXISTS = new ErrorCode(1_040_713_200, "閿€鍞€€璐у崟鏄庣粏涓嶅瓨鍦?");
    ErrorCode WM_RETURN_SALES_DETAIL_QUANTITY_EXCEED = new ErrorCode(1_040_713_201, "涓婃灦鏄庣粏鎬绘暟閲忎笉鑳借秴杩囬€€璐ц鏁伴噺");

    // ========== MES 浠撳簱绠＄悊-鐩樼偣鏂规/浠诲姟/缁撴灉锛?-040-714-100锛?==========
    ErrorCode WM_STOCK_TAKING_PLAN_NOT_EXISTS = new ErrorCode(1_040_714_100, "鐩樼偣鏂规涓嶅瓨鍦?");
    ErrorCode WM_STOCK_TAKING_PLAN_CODE_DUPLICATE = new ErrorCode(1_040_714_101, "鐩樼偣鏂规缂栫爜宸插瓨鍦?");
    ErrorCode WM_STOCK_TAKING_PLAN_NOT_DISABLED = new ErrorCode(1_040_714_102, "鍙湁绂佺敤鐘舵€佺殑鐩樼偣鏂规鎵嶅厑璁镐慨鏀广€佸垹闄ゆ垨缁存姢鍙傛暟");
    ErrorCode WM_STOCK_TAKING_PLAN_NOT_ENABLED = new ErrorCode(1_040_714_103, "鍙湁鍚敤鐘舵€佺殑鐩樼偣鏂规鎵嶅厑璁哥敓鎴愪换鍔?");
    ErrorCode WM_STOCK_TAKING_PLAN_PARAM_NOT_EXISTS = new ErrorCode(1_040_714_104, "鐩樼偣鏂规鍙傛暟涓嶅瓨鍦?");
    ErrorCode WM_STOCK_TAKING_PLAN_PARAM_EMPTY = new ErrorCode(1_040_714_105, "鐩樼偣鏂规鍙傛暟涓嶈兘涓虹┖锛岃鍏堥厤缃洏鐐瑰弬鏁?");
    ErrorCode WM_STOCK_TAKING_PLAN_DYNAMIC_TIME_INVALID = new ErrorCode(1_040_714_106, "鍔ㄦ€佺洏鐐规柟妗堝繀椤昏缃紑濮嬫椂闂村拰缁撴潫鏃堕棿锛屼笖缁撴潫鏃堕棿蹇呴』鏅氫簬寮€濮嬫椂闂?");
    ErrorCode WM_STOCK_TAKING_TASK_NOT_EXISTS = new ErrorCode(1_040_714_110, "鐩樼偣浠诲姟涓嶅瓨鍦?");
    ErrorCode WM_STOCK_TAKING_TASK_CODE_DUPLICATE = new ErrorCode(1_040_714_111, "鐩樼偣浠诲姟缂栫爜宸插瓨鍦?");
    ErrorCode WM_STOCK_TAKING_TASK_NOT_PREPARE = new ErrorCode(1_040_714_112, "鍙湁鑽夌鐘舵€佺殑鐩樼偣浠诲姟鎵嶅厑璁告鎿嶄綔");
    ErrorCode WM_STOCK_TAKING_TASK_NOT_APPROVING = new ErrorCode(1_040_714_113, "鍙湁鐩樼偣涓姸鎬佺殑浠诲姟鎵嶅厑璁告鎿嶄綔");
    ErrorCode WM_STOCK_TAKING_TASK_CANNOT_CANCEL = new ErrorCode(1_040_714_114, "宸插畬鎴愭垨宸插彇娑堢殑鐩樼偣浠诲姟涓嶅厑璁稿彇娑?");
    ErrorCode WM_STOCK_TAKING_TASK_NO_STOCK = new ErrorCode(1_040_714_115, "鏈壘鍒扮鍚堟潯浠剁殑搴撳瓨鏁版嵁");
    ErrorCode WM_STOCK_TAKING_TASK_NO_LINE = new ErrorCode(1_040_714_116, "鐩樼偣浠诲姟鑷冲皯闇€瑕佷竴鏉′换鍔¤");
    ErrorCode WM_STOCK_TAKING_TASK_LINE_NOT_EXISTS = new ErrorCode(1_040_714_117, "鐩樼偣浠诲姟琛屼笉瀛樺湪");
    ErrorCode WM_STOCK_TAKING_TASK_DYNAMIC_TIME_REQUIRED = new ErrorCode(1_040_714_118, "鍔ㄦ€佺洏鐐瑰繀椤婚€夋嫨寮€濮嬫椂闂村拰缁撴潫鏃堕棿");
    ErrorCode WM_STOCK_TAKING_TASK_RESULT_NOT_EXISTS = new ErrorCode(1_040_714_119, "鐩樼偣缁撴灉涓嶅瓨鍦?");
    ErrorCode WM_STOCK_TAKING_TASK_LINE_ALREADY_TAKEN = new ErrorCode(1_040_714_120, "璇ョ洏鐐规竻鍗曡宸叉湁鐩樼偣璁板綍锛屼笉鑳介噸澶嶇洏鐐?");

    // ========== MES 浠撳簱绠＄悊-閿€鍞嚭搴撳崟锛?-040-714-000锛?==========
    ErrorCode WM_PRODUCT_SALES_NOT_EXISTS = new ErrorCode(1_040_714_000, "閿€鍞嚭搴撳崟涓嶅瓨鍦?");
    ErrorCode WM_PRODUCT_SALES_CODE_DUPLICATE = new ErrorCode(1_040_714_001, "閿€鍞嚭搴撳崟鍙峰凡瀛樺湪");
    ErrorCode WM_PRODUCT_SALES_NOT_PREPARE = new ErrorCode(1_040_714_002, "鍙湁鑽夌鐘舵€佹墠鍙搷浣?");
    ErrorCode WM_PRODUCT_SALES_LINES_EMPTY = new ErrorCode(1_040_714_003, "閿€鍞嚭搴撳崟琛屼笉鑳戒负绌?");
    ErrorCode WM_PRODUCT_SALES_CANNOT_SUBMIT = new ErrorCode(1_040_714_004, "褰撳墠鐘舵€佷笉鍏佽鎻愪氦");
    ErrorCode WM_PRODUCT_SALES_CANNOT_PICK = new ErrorCode(1_040_714_005, "褰撳墠鐘舵€佷笉鍏佽鎷ｈ揣");
    ErrorCode WM_PRODUCT_SALES_CANNOT_SHIPPING = new ErrorCode(1_040_714_006, "褰撳墠鐘舵€佷笉鍏佽濉啓杩愬崟");
    ErrorCode WM_PRODUCT_SALES_CANNOT_FINISH = new ErrorCode(1_040_714_007, "褰撳墠鐘舵€佷笉鍏佽鎵ц鍑哄簱");
    ErrorCode WM_PRODUCT_SALES_CANNOT_CANCEL = new ErrorCode(1_040_714_008, "褰撳墠鐘舵€佷笉鍏佽鍙栨秷");
    ErrorCode WM_PRODUCT_SALES_CANNOT_CONFIRM = new ErrorCode(1_040_714_009, "褰撳墠鐘舵€佷笉鍏佽纭妫€楠岄€氳繃");
    ErrorCode WM_PRODUCT_SALES_DETAILS_EMPTY = new ErrorCode(1_040_714_020, "鎷ｈ揣鏄庣粏涓嶈兘涓虹┖");
    ErrorCode WM_PRODUCT_SALES_DETAIL_QUANTITY_MISMATCH = new ErrorCode(1_040_714_010, "鎷ｈ揣鏁伴噺涓庡嚭搴撴暟閲忎笉鍖归厤");
    ErrorCode WM_PRODUCT_SALES_LINE_NOT_EXISTS = new ErrorCode(1_040_714_011, "閿€鍞嚭搴撳崟琛屼笉瀛樺湪");
    ErrorCode WM_PRODUCT_SALES_DETAIL_NOT_EXISTS = new ErrorCode(1_040_714_012, "閿€鍞嚭搴撴槑缁嗕笉瀛樺湪");
    ErrorCode WM_PRODUCT_SALES_STOCK_INSUFFICIENT = new ErrorCode(1_040_714_013, "搴撳瓨涓嶈冻锛屾棤娉曟嫞璐?");
    ErrorCode WM_PRODUCT_SALES_LINE_QUANTITY_INVALID = new ErrorCode(1_040_714_014, "鍑哄簱鏁伴噺蹇呴』澶т簬 0");
    ErrorCode WM_PRODUCT_SALES_DETAIL_LINE_NOT_MATCH = new ErrorCode(1_040_714_015, "鎷ｈ揣鏄庣粏涓嶅睘浜庢寚瀹氱殑閿€鍞嚭搴撳崟");
    ErrorCode WM_PRODUCT_SALES_DETAIL_ITEM_MISMATCH = new ErrorCode(1_040_714_016, "鎷ｈ揣鏄庣粏鐨勭墿鏂欎笌閿€鍞嚭搴撳崟琛岀殑鐗╂枡涓嶄竴鑷?");
    ErrorCode WM_PRODUCT_SALES_LINE_SALES_NOTICE_LINE_REQUIRED = new ErrorCode(1_040_714_017,
            "鍑哄簱鍗曞叧鑱斾簡鍙戣揣閫氱煡鍗曪紝蹇呴』閫夋嫨鍙戣揣閫氱煡鍗曡");
    ErrorCode WM_PRODUCT_SALES_LINE_SALES_NOTICE_LINE_NOT_ALLOWED = new ErrorCode(1_040_714_018,
            "鍑哄簱鍗曟湭鍏宠仈鍙戣揣閫氱煡鍗曪紝涓嶈兘閫夋嫨鍙戣揣閫氱煡鍗曡");
    ErrorCode WM_PRODUCT_SALES_LINE_NOTICE_LINE_ITEM_MISMATCH = new ErrorCode(1_040_714_030,
            "鍑哄簱琛岀墿鏂欎笌鍙戣揣閫氱煡鍗曡鐗╂枡涓嶄竴鑷?");
    ErrorCode WM_PRODUCT_SALES_LINE_NOTICE_LINE_QUANTITY_MISMATCH = new ErrorCode(1_040_714_031,
            "鍑哄簱琛屾暟閲忎笌鍙戣揣閫氱煡鍗曡鏁伴噺涓嶄竴鑷?");
    ErrorCode WM_PRODUCT_SALES_LINE_NOTICE_LINE_BATCH_MISMATCH = new ErrorCode(1_040_714_032,
            "鍑哄簱琛屾壒娆″彿涓庡彂璐ч€氱煡鍗曡鎵规鍙蜂笉涓€鑷?");
    ErrorCode WM_PRODUCT_SALES_LINE_NOTICE_LINE_OQC_MISMATCH = new ErrorCode(1_040_714_033,
            "鍑哄簱琛?OQC 妫€楠屾爣璇嗕笌鍙戣揣閫氱煡鍗曡涓嶄竴鑷?");

    // ========== MES 浠撳簱绠＄悊-鏉傞」鍑哄簱鍗曪紙1-040-715-000锛?==========
    ErrorCode WM_MISC_ISSUE_NOT_EXISTS = new ErrorCode(1_040_715_000, "杂项出库单不存在");
    ErrorCode WM_MISC_ISSUE_CODE_DUPLICATE = new ErrorCode(1_040_715_001, "杂项出库单编码已存在");
    ErrorCode WM_MISC_ISSUE_STATUS_INVALID = new ErrorCode(1_040_715_002, "杂项出库单状态不正确，无法执行该操作");
    ErrorCode WM_MISC_ISSUE_NO_LINE = new ErrorCode(1_040_715_003, "杂项出库单至少需要一条行数据");
    ErrorCode WM_MISC_ISSUE_CANCEL_NOT_ALLOWED = new ErrorCode(1_040_715_004, "杂项出库单已完成或已取消，无法取消");
    ErrorCode WM_MISC_ISSUE_DETAIL_QUANTITY_MISMATCH = new ErrorCode(1_040_715_005, "杂项出库单行数量与明细数量不一致");
    ErrorCode WM_MISC_ISSUE_LINE_NOT_EXISTS = new ErrorCode(1_040_715_100, "杂项出库单行不存在");
    ErrorCode WM_MISC_ISSUE_DETAIL_NOT_EXISTS = new ErrorCode(1_040_715_200, "杂项出库单明细不存在");

    // ========== MES 浠撳簱绠＄悊-鏉傞」鍏ュ簱鍗曪紙1-040-716-000锛?==========
    ErrorCode WM_MISC_RECEIPT_NOT_EXISTS = new ErrorCode(1_040_716_000, "鏉傞」鍏ュ簱鍗曚笉瀛樺湪");
    ErrorCode WM_MISC_RECEIPT_CODE_DUPLICATE = new ErrorCode(1_040_716_001, "鏉傞」鍏ュ簱鍗曠紪鐮佸凡瀛樺湪");
    ErrorCode WM_MISC_RECEIPT_STATUS_NOT_PREPARE = new ErrorCode(1_040_716_002, "鍙湁鑽夌鐘舵€佹墠鍏佽姝ゆ搷浣?");
    ErrorCode WM_MISC_RECEIPT_STATUS_NOT_APPROVED = new ErrorCode(1_040_716_003, "鍙湁宸插鎵圭姸鎬佹墠鍏佽鎵ц鍏ュ簱");
    ErrorCode WM_MISC_RECEIPT_NO_LINE = new ErrorCode(1_040_716_004, "鑷冲皯闇€瑕佷竴鏉¤椤圭洰");
    ErrorCode WM_MISC_RECEIPT_CANCEL_NOT_ALLOWED = new ErrorCode(1_040_716_005, "宸插畬鎴愭垨宸插彇娑堢殑鍏ュ簱鍗曚笉鍏佽鍙栨秷");
    ErrorCode WM_MISC_RECEIPT_LINE_NOT_EXISTS = new ErrorCode(1_040_716_100, "鏉傞」鍏ュ簱鍗曡涓嶅瓨鍦?");
    ErrorCode WM_MISC_RECEIPT_WAREHOUSE_REQUIRED = new ErrorCode(1_040_716_101, "浠撳簱涓嶈兘涓虹┖");
    ErrorCode WM_MISC_RECEIPT_QUANTITY_INVALID = new ErrorCode(1_040_716_102, "鍏ュ簱鏁伴噺蹇呴』澶т簬 0");
    ErrorCode WM_MISC_RECEIPT_DETAIL_NOT_EXISTS = new ErrorCode(1_040_716_200, "鏉傞」鍏ュ簱鍗曟槑缁嗕笉瀛樺湪");

    // ========== MES 浠撳簱绠＄悊-鍙戣揣閫氱煡鍗曪紙1-040-720-000锛?==========
    ErrorCode WM_SALES_NOTICE_NOT_EXISTS = new ErrorCode(1_040_720_000, "鍙戣揣閫氱煡鍗曚笉瀛樺湪");
    ErrorCode WM_SALES_NOTICE_CODE_DUPLICATE = new ErrorCode(1_040_720_001, "閫氱煡鍗曠紪鍙烽噸澶?");
    ErrorCode WM_SALES_NOTICE_STATUS_NOT_ALLOW_DELETE = new ErrorCode(1_040_720_002, "鍗曟嵁鐘舵€佷笉鍏佽鍒犻櫎");
    ErrorCode WM_SALES_NOTICE_STATUS_NOT_ALLOW_UPDATE = new ErrorCode(1_040_720_003, "鍗曟嵁鐘舵€佷笉鍏佽淇敼");
    ErrorCode WM_SALES_NOTICE_STATUS_NOT_APPROVED = new ErrorCode(1_040_720_004, "鍙戣揣閫氱煡鍗曚笉鏄緟鍑哄簱鐘舵€?");
    ErrorCode WM_SALES_NOTICE_CLIENT_MISMATCH = new ErrorCode(1_040_720_005, "鍙戣揣閫氱煡鍗曠殑瀹㈡埛涓庡綋鍓嶅崟鎹笉涓€鑷?");
    ErrorCode WM_SALES_NOTICE_LINE_NOT_EXISTS = new ErrorCode(1_040_720_010, "鍙戣揣閫氱煡鍗曡涓嶅瓨鍦?");
    ErrorCode WM_SALES_NOTICE_LINE_EMPTY = new ErrorCode(1_040_720_011, "鍙戣揣閫氱煡鍗曡涓虹┖锛屼笉鑳芥彁浜?");
    ErrorCode WM_SALES_NOTICE_LINE_NOT_MATCH = new ErrorCode(1_040_720_012, "鍙戣揣閫氱煡鍗曡涓嶅睘浜庢寚瀹氱殑鍙戣揣閫氱煡鍗?");

    // ========== MES 浠撳簱绠＄悊-鏉＄爜閰嶇疆锛?-040-730-000锛?==========
    ErrorCode WM_BARCODE_CONFIG_NOT_EXISTS = new ErrorCode(1_040_730_000, "鏉＄爜閰嶇疆涓嶅瓨鍦?");
    ErrorCode WM_BARCODE_CONFIG_BIZ_TYPE_DUPLICATE = new ErrorCode(1_040_730_001, "璇ヤ笟鍔＄被鍨嬬殑鏉＄爜閰嶇疆宸插瓨鍦?");
    ErrorCode WM_BARCODE_CONFIG_HAS_BARCODE = new ErrorCode(1_040_730_002, "璇ユ潯鐮侀厤缃凡琚潯鐮佽褰曞叧鑱旓紝鏃犳硶鍒犻櫎");

    // ========== MES 浠撳簱绠＄悊-鏉＄爜娓呭崟锛?-040-731-000锛?==========
    ErrorCode WM_BARCODE_NOT_EXISTS = new ErrorCode(1_040_731_000, "鏉＄爜涓嶅瓨鍦?");
    ErrorCode WM_BARCODE_ALREADY_EXISTS = new ErrorCode(1_040_731_001, "璇ヤ笟鍔″璞＄殑鏉＄爜宸插瓨鍦?");
    ErrorCode WM_BARCODE_CONTENT_DUPLICATE = new ErrorCode(1_040_731_002, "鏉＄爜鍐呭宸插瓨鍦?");
    ErrorCode BARCODE_BIZ_TYPE_NOT_EXISTS = new ErrorCode(1_040_731_003, "涓氬姟绫诲瀷涓嶈兘涓虹┖");
    ErrorCode BARCODE_BIZ_CODE_NOT_EXISTS = new ErrorCode(1_040_731_004, "涓氬姟缂栫爜涓嶈兘涓虹┖");
    ErrorCode BARCODE_CONFIG_NOT_EXISTS = new ErrorCode(1_040_731_005, "鏉＄爜閰嶇疆涓嶅瓨鍦?");

    // ========== MES 浠撳簱绠＄悊-瑁呯鍗曪紙1-040-740-000锛?==========
    ErrorCode WM_PACKAGE_NOT_EXISTS = new ErrorCode(1_040_740_000, "瑁呯鍗曚笉瀛樺湪");
    ErrorCode WM_PACKAGE_CODE_DUPLICATE = new ErrorCode(1_040_740_001, "瑁呯鍗曠紪鐮佸凡瀛樺湪");
    ErrorCode WM_PACKAGE_STATUS_NOT_PREPARE = new ErrorCode(1_040_740_002, "鍙湁鑽夌鐘舵€佹墠鍏佽姝ゆ搷浣?");
    ErrorCode WM_PACKAGE_PARENT_NOT_EXISTS = new ErrorCode(1_040_740_003, "鐖剁涓嶅瓨鍦?");
    ErrorCode WM_PACKAGE_PARENT_SELF = new ErrorCode(1_040_740_004, "涓嶈兘閫夋嫨鑷繁浣滀负鐖剁");
    ErrorCode WM_PACKAGE_CHILD_HAS_PARENT = new ErrorCode(1_040_740_005, "璇ヨ绠卞崟宸叉湁鐖剁锛屼笉鑳介噸澶嶆坊鍔?");
    ErrorCode WM_PACKAGE_PARENT_IS_CHILD = new ErrorCode(1_040_740_006, "涓嶈兘閫夋嫨瀛愮鐨勫悗浠ｄ綔涓虹埗绠憋紝浼氬舰鎴愮幆璺?");
    ErrorCode WM_PACKAGE_CHILD_NOT_FINISHED = new ErrorCode(1_040_740_007, "瀛愮蹇呴』鏄凡瀹屾垚鐘舵€佹墠鑳芥坊鍔?");
    ErrorCode WM_PACKAGE_HAS_CHILDREN = new ErrorCode(1_040_740_008, "瀛樺湪瀛愮锛屼笉鍏佽鍒犻櫎");
    ErrorCode WM_PACKAGE_LINE_NOT_EXISTS = new ErrorCode(1_040_740_100, "瑁呯鏄庣粏涓嶅瓨鍦?");

}

