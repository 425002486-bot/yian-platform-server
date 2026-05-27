SET NAMES utf8mb4;

-- =============================================
-- 翼安智链 - 业务菜单初始化 SQL
-- 使用 5000+ 的 ID 段，避免与 RuoYi 原有菜单冲突
-- 菜单 type: 1=目录, 2=菜单, 3=按钮
-- 支持重复执行：先删除旧数据再插入
-- =============================================
-- =============================================
-- 翼安智链 - 业务菜单初始化 SQL
-- 使用 5000+ 的 ID 段，避免与 RuoYi 原有菜单冲突
-- 菜单 type: 1=目录, 2=菜单, 3=按钮
-- 支持重复执行：先删除旧数据再插入
-- =============================================

-- 先修改系统默认租户名称
UPDATE system_tenant SET name = '翼安智链' WHERE id = 1;

-- 清理旧的翼安智链菜单（ID 5000-5999），支持重复执行
DELETE FROM system_role_menu WHERE menu_id BETWEEN 5000 AND 5999;
DELETE FROM system_menu WHERE id BETWEEN 5000 AND 5999;

-- =============================================
-- 一级目录菜单
-- =============================================

-- 1. 工作台（首页驾驶舱）
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5000, '工作台', '', 2, 0, 0, '/dashboard', 'ep:odometer', 'yian/dashboard/index', 'YianDashboard', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- 2. 资产中心
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5100, '资产中心', '', 1, 1, 0, '/asset', 'ep:cpu', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 3. 工单中心
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5200, '工单中心', '', 1, 2, 0, '/workorder', 'ep:document-checked', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 4. 备件库存
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5300, '备件库存', '', 1, 3, 0, '/inventory', 'ep:box', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 5. 审计日志
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5400, '审计日志', '', 1, 4, 0, '/audit', 'ep:document-copy', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 6. 基础配置（站点、人员、角色、规则等）
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5500, '基础配置', '', 1, 5, 0, '/config', 'ep:setting', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 资产中心 - 子菜单 (parent_id = 5100)
-- =============================================

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5101, '设备台账', 'asset:device:list', 2, 1, 5100, 'device', 'ep:monitor', 'yian/asset/device/index', 'AssetDevice', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5102, '设备详情', 'asset:device:detail', 2, 2, 5100, 'device/detail/:id', '', 'yian/asset/device/detail', 'AssetDeviceDetail', 0, b'0', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5105, '资产巡检', 'asset:inspection:list', 2, 2, 5100, 'inspection', 'ep:checked', 'yian/asset/inspection/index', 'AssetInspection', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5106, '发起巡检', 'asset:inspection:create', 2, 5, 5100, 'inspection/create', '', 'yian/asset/inspection/create', 'AssetInspectionCreate', 0, b'0', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5103, '资产导入', 'asset:device:import', 2, 3, 5100, 'import', 'ep:upload', 'yian/asset/import/index', 'AssetImport', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5104, '电池管理', 'asset:battery:list', 2, 4, 5100, 'battery', 'ep:lightning', 'yian/asset/battery/index', 'AssetBattery', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 工单中心 - 子菜单 (parent_id = 5200)
-- =============================================

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5201, '工单看板', 'workorder:board:view', 2, 1, 5200, 'board', 'ep:data-board', 'yian/workorder/board/index', 'WorkorderBoard', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5202, '工单列表', 'workorder:order:list', 2, 2, 5200, 'list', 'ep:list', 'yian/workorder/list/index', 'WorkorderList', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5203, '新建工单', 'workorder:order:create', 2, 3, 5200, 'create', 'ep:circle-plus', 'yian/workorder/create/index', 'WorkorderCreate', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5204, '工单详情', 'workorder:order:detail', 2, 4, 5200, 'detail/:id', '', 'yian/workorder/detail/index', 'WorkorderDetail', 0, b'0', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 备件库存 - 子菜单 (parent_id = 5300)
-- =============================================

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5301, '库存列表', 'inventory:stock:list', 2, 1, 5300, 'stock', 'ep:goods', 'yian/inventory/stock/index', 'InventoryStock', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5302, '入库登记', 'inventory:inbound:create', 2, 2, 5300, 'inbound', 'ep:bottom', 'yian/inventory/inbound/index', 'InventoryInbound', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5303, '领退料记录', 'inventory:pick:list', 2, 3, 5300, 'pick-records', 'ep:notebook', 'yian/inventory/pick/index', 'InventoryPick', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5304, '备件导入', 'inventory:import:create', 2, 4, 5300, 'import', 'ep:upload', 'yian/inventory/import/index', 'InventoryImport', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 审计日志 - 子菜单 (parent_id = 5400)
-- =============================================

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5401, '操作日志', 'audit:log:list', 2, 1, 5400, 'log', 'ep:tickets', 'yian/audit/log/index', 'AuditLog', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5402, '日志详情', 'audit:log:detail', 2, 2, 5400, 'log/detail/:id', '', 'yian/audit/detail/index', 'AuditLogDetail', 0, b'0', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 基础配置 - 子菜单 (parent_id = 5500)
-- =============================================

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5501, '站点管理', 'config:station:list', 2, 1, 5500, 'station', 'ep:office-building', 'yian/config/station/index', 'ConfigStation', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5502, '人员管理', 'config:people:list', 2, 2, 5500, 'people', 'ep:user', 'yian/config/people/index', 'ConfigPeople', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5503, '角色权限', 'config:role:list', 2, 3, 5500, 'role', 'ep:lock', 'yian/config/role/index', 'ConfigRole', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5504, '规则中心', 'config:rule:list', 2, 4, 5500, 'rule', 'ep:warning', 'yian/config/rule/index', 'ConfigRule', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5505, 'SLA 规则', 'config:sla:list', 2, 5, 5500, 'sla', 'ep:timer', 'yian/config/sla/index', 'ConfigSla', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 给超级管理员角色分配新菜单权限
-- (RuoYi 默认角色 ID=1 为超级管理员，通常自动拥有所有权限)
-- 如果需要手动分配，取消下面的注释
-- =============================================

-- INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted)
-- SELECT 1, id, 'admin', NOW(), 'admin', NOW(), b'0' FROM system_menu WHERE id BETWEEN 5000 AND 5999;

-- 先修改系统默认租户名称
UPDATE system_tenant SET name = '翼安智链' WHERE id = 1;

-- 清理旧的翼安智链菜单（ID 5000-5999），支持重复执行
DELETE FROM system_role_menu WHERE menu_id BETWEEN 5000 AND 5999;
DELETE FROM system_menu WHERE id BETWEEN 5000 AND 5999;

-- =============================================
-- 一级目录菜单
-- =============================================

-- 1. 工作台（首页驾驶舱）
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5000, '工作台', '', 2, 0, 0, '/dashboard', 'ep:odometer', 'yian/dashboard/index', 'YianDashboard', 0, b'1', b'1', b'0', 'admin', NOW(), 'admin', NOW(), b'0');

-- 2. 资产中心
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5100, '资产中心', '', 1, 1, 0, '/asset', 'ep:cpu', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 3. 工单中心
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5200, '工单中心', '', 1, 2, 0, '/workorder', 'ep:document-checked', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 4. 备件库存
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5300, '备件库存', '', 1, 3, 0, '/inventory', 'ep:box', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 5. 审计日志
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5400, '审计日志', '', 1, 4, 0, '/audit', 'ep:document-copy', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 6. 基础配置（站点、人员、角色、规则等）
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5500, '基础配置', '', 1, 5, 0, '/config', 'ep:setting', NULL, NULL, 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 资产中心 - 子菜单 (parent_id = 5100)
-- =============================================

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5101, '设备台账', 'asset:device:list', 2, 1, 5100, 'device', 'ep:monitor', 'yian/asset/device/index', 'AssetDevice', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5102, '设备详情', 'asset:device:detail', 2, 2, 5100, 'device/detail/:id', '', 'yian/asset/device/detail', 'AssetDeviceDetail', 0, b'0', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5105, '资产巡检', 'asset:inspection:list', 2, 2, 5100, 'inspection', 'ep:checked', 'yian/asset/inspection/index', 'AssetInspection', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5106, '发起巡检', 'asset:inspection:create', 2, 5, 5100, 'inspection/create', '', 'yian/asset/inspection/create', 'AssetInspectionCreate', 0, b'0', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5103, '资产导入', 'asset:device:import', 2, 3, 5100, 'import', 'ep:upload', 'yian/asset/import/index', 'AssetImport', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5104, '电池管理', 'asset:battery:list', 2, 4, 5100, 'battery', 'ep:lightning', 'yian/asset/battery/index', 'AssetBattery', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 工单中心 - 子菜单 (parent_id = 5200)
-- =============================================

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5201, '工单看板', 'workorder:board:view', 2, 1, 5200, 'board', 'ep:data-board', 'yian/workorder/board/index', 'WorkorderBoard', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5202, '工单列表', 'workorder:order:list', 2, 2, 5200, 'list', 'ep:list', 'yian/workorder/list/index', 'WorkorderList', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5203, '新建工单', 'workorder:order:create', 2, 3, 5200, 'create', 'ep:circle-plus', 'yian/workorder/create/index', 'WorkorderCreate', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5204, '工单详情', 'workorder:order:detail', 2, 4, 5200, 'detail/:id', '', 'yian/workorder/detail/index', 'WorkorderDetail', 0, b'0', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 备件库存 - 子菜单 (parent_id = 5300)
-- =============================================

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5301, '库存列表', 'inventory:stock:list', 2, 1, 5300, 'stock', 'ep:goods', 'yian/inventory/stock/index', 'InventoryStock', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5302, '入库登记', 'inventory:inbound:create', 2, 2, 5300, 'inbound', 'ep:bottom', 'yian/inventory/inbound/index', 'InventoryInbound', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5303, '领退料记录', 'inventory:pick:list', 2, 3, 5300, 'pick-records', 'ep:notebook', 'yian/inventory/pick/index', 'InventoryPick', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5304, '备件导入', 'inventory:import:create', 2, 4, 5300, 'import', 'ep:upload', 'yian/inventory/import/index', 'InventoryImport', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 审计日志 - 子菜单 (parent_id = 5400)
-- =============================================

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5401, '操作日志', 'audit:log:list', 2, 1, 5400, 'log', 'ep:tickets', 'yian/audit/log/index', 'AuditLog', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5402, '日志详情', 'audit:log:detail', 2, 2, 5400, 'log/detail/:id', '', 'yian/audit/detail/index', 'AuditLogDetail', 0, b'0', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 基础配置 - 子菜单 (parent_id = 5500)
-- =============================================

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5501, '站点管理', 'config:station:list', 2, 1, 5500, 'station', 'ep:office-building', 'yian/config/station/index', 'ConfigStation', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5502, '人员管理', 'config:people:list', 2, 2, 5500, 'people', 'ep:user', 'yian/config/people/index', 'ConfigPeople', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5503, '角色权限', 'config:role:list', 2, 3, 5500, 'role', 'ep:lock', 'yian/config/role/index', 'ConfigRole', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5504, '规则中心', 'config:rule:list', 2, 4, 5500, 'rule', 'ep:warning', 'yian/config/rule/index', 'ConfigRule', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (5505, 'SLA 规则', 'config:sla:list', 2, 5, 5500, 'sla', 'ep:timer', 'yian/config/sla/index', 'ConfigSla', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- =============================================
-- 给超级管理员角色分配新菜单权限
-- (RuoYi 默认角色 ID=1 为超级管理员，通常自动拥有所有权限)
-- 如果需要手动分配，取消下面的注释
-- =============================================

-- INSERT INTO system_role_menu (role_id, menu_id, creator, create_time, updater, update_time, deleted)
-- SELECT 1, id, 'admin', NOW(), 'admin', NOW(), b'0' FROM system_menu WHERE id BETWEEN 5000 AND 5999;
