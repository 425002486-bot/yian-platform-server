-- Yian MVP bootstrap for MES device flow
-- Scope:
-- 1. Enable the current device ledger APIs and related detail tabs
-- 2. Support auto code generation and auto barcode generation for machinery
-- 3. Include tenant_id on MES tables because tenant interception is enabled by default

SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS `mes_md_workshop` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(64) NOT NULL COMMENT 'workshop code',
  `name` varchar(100) NOT NULL COMMENT 'workshop name',
  `area` decimal(18,2) DEFAULT NULL COMMENT 'area',
  `charge_user_id` bigint DEFAULT NULL COMMENT 'owner user id',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT 'status',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mes_md_workshop_code` (`code`, `tenant_id`),
  UNIQUE KEY `uk_mes_md_workshop_name` (`name`, `tenant_id`),
  KEY `idx_mes_md_workshop_status` (`status`),
  KEY `idx_mes_md_workshop_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes workshop';

CREATE TABLE IF NOT EXISTS `mes_md_workstation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(64) NOT NULL COMMENT 'workstation code',
  `name` varchar(100) NOT NULL COMMENT 'workstation name',
  `address` varchar(255) DEFAULT NULL COMMENT 'address',
  `workshop_id` bigint NOT NULL COMMENT 'workshop id',
  `process_id` bigint DEFAULT NULL COMMENT 'process id',
  `warehouse_id` bigint DEFAULT NULL COMMENT 'warehouse id',
  `location_id` bigint DEFAULT NULL COMMENT 'location id',
  `area_id` bigint DEFAULT NULL COMMENT 'area id',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT 'status',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mes_md_workstation_code` (`code`, `tenant_id`),
  UNIQUE KEY `uk_mes_md_workstation_name` (`name`, `tenant_id`),
  KEY `idx_mes_md_workstation_workshop` (`workshop_id`),
  KEY `idx_mes_md_workstation_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes workstation';

CREATE TABLE IF NOT EXISTS `mes_dv_machinery_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(64) NOT NULL COMMENT 'type code',
  `name` varchar(100) NOT NULL COMMENT 'type name',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT 'parent id',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT 'status',
  `sort` int NOT NULL DEFAULT '0' COMMENT 'sort',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mes_dv_machinery_type_code` (`code`, `tenant_id`),
  KEY `idx_mes_dv_machinery_type_parent` (`parent_id`),
  KEY `idx_mes_dv_machinery_type_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes machinery type';

CREATE TABLE IF NOT EXISTS `mes_dv_check_plan` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(64) NOT NULL COMMENT 'plan code',
  `name` varchar(100) NOT NULL COMMENT 'plan name',
  `type` tinyint NOT NULL COMMENT 'plan type',
  `start_date` datetime DEFAULT NULL COMMENT 'start date',
  `end_date` datetime DEFAULT NULL COMMENT 'end date',
  `cycle_type` tinyint DEFAULT NULL COMMENT 'cycle type',
  `cycle_count` int DEFAULT NULL COMMENT 'cycle count',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT 'status',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mes_dv_check_plan_code` (`code`, `tenant_id`),
  KEY `idx_mes_dv_check_plan_type_status` (`type`, `status`),
  KEY `idx_mes_dv_check_plan_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes check plan';

CREATE TABLE IF NOT EXISTS `mes_dv_check_plan_machinery` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `plan_id` bigint NOT NULL COMMENT 'plan id',
  `machinery_id` bigint NOT NULL COMMENT 'machinery id',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mes_dv_check_plan_machinery` (`plan_id`, `machinery_id`, `tenant_id`),
  KEY `idx_mes_dv_check_plan_machinery_mid` (`machinery_id`),
  KEY `idx_mes_dv_check_plan_machinery_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes check plan machinery';

CREATE TABLE IF NOT EXISTS `mes_dv_machinery` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(64) NOT NULL COMMENT 'machinery code',
  `name` varchar(100) NOT NULL COMMENT 'machinery name',
  `brand` varchar(100) DEFAULT NULL COMMENT 'brand',
  `specification` varchar(100) DEFAULT NULL COMMENT 'specification',
  `machinery_type_id` bigint NOT NULL COMMENT 'machinery type id',
  `workshop_id` bigint NOT NULL COMMENT 'workshop id',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT 'status',
  `last_mainten_time` datetime DEFAULT NULL COMMENT 'last mainten time',
  `last_check_time` datetime DEFAULT NULL COMMENT 'last check time',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mes_dv_machinery_code` (`code`, `tenant_id`),
  KEY `idx_mes_dv_machinery_type` (`machinery_type_id`),
  KEY `idx_mes_dv_machinery_workshop` (`workshop_id`),
  KEY `idx_mes_dv_machinery_status` (`status`),
  KEY `idx_mes_dv_machinery_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes machinery';

CREATE TABLE IF NOT EXISTS `mes_dv_battery` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(64) NOT NULL COMMENT 'battery code',
  `serial_number` varchar(100) DEFAULT NULL COMMENT 'serial number',
  `model` varchar(100) DEFAULT NULL COMMENT 'model',
  `workshop_id` bigint NOT NULL COMMENT 'workshop id',
  `linked_machinery_id` bigint DEFAULT NULL COMMENT 'linked machinery id',
  `soh` int DEFAULT NULL COMMENT 'state of health',
  `cycle_count` int DEFAULT NULL COMMENT 'cycle count',
  `last_check_time` datetime DEFAULT NULL COMMENT 'last check time',
  `check_source` varchar(100) DEFAULT NULL COMMENT 'check source',
  `health_status` varchar(16) NOT NULL DEFAULT 'normal' COMMENT 'health status',
  `source_evidence` varchar(500) DEFAULT NULL COMMENT 'source evidence',
  `recommendation` varchar(500) DEFAULT NULL COMMENT 'recommendation',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mes_dv_battery_code` (`code`, `tenant_id`),
  KEY `idx_mes_dv_battery_workshop` (`workshop_id`),
  KEY `idx_mes_dv_battery_machinery` (`linked_machinery_id`),
  KEY `idx_mes_dv_battery_health` (`health_status`),
  KEY `idx_mes_dv_battery_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes battery';

CREATE TABLE IF NOT EXISTS `mes_dv_repair` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(64) NOT NULL COMMENT 'repair code',
  `name` varchar(100) NOT NULL COMMENT 'repair name',
  `machinery_id` bigint NOT NULL COMMENT 'machinery id',
  `require_date` datetime DEFAULT NULL COMMENT 'require date',
  `finish_date` datetime DEFAULT NULL COMMENT 'finish date',
  `confirm_date` datetime DEFAULT NULL COMMENT 'confirm date',
  `result` tinyint DEFAULT NULL COMMENT 'result',
  `accepted_user_id` bigint DEFAULT NULL COMMENT 'accepted user id',
  `confirm_user_id` bigint DEFAULT NULL COMMENT 'confirm user id',
  `source_doc_type` int DEFAULT NULL COMMENT 'source doc type',
  `source_doc_id` bigint DEFAULT NULL COMMENT 'source doc id',
  `source_doc_code` varchar(64) DEFAULT NULL COMMENT 'source doc code',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT 'status',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mes_dv_repair_code` (`code`, `tenant_id`),
  KEY `idx_mes_dv_repair_mid` (`machinery_id`),
  KEY `idx_mes_dv_repair_status` (`status`),
  KEY `idx_mes_dv_repair_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes repair';

CREATE TABLE IF NOT EXISTS `mes_dv_mainten_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `plan_id` bigint DEFAULT NULL COMMENT 'plan id',
  `machinery_id` bigint NOT NULL COMMENT 'machinery id',
  `mainten_time` datetime DEFAULT NULL COMMENT 'mainten time',
  `user_id` bigint DEFAULT NULL COMMENT 'user id',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT 'status',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  KEY `idx_mes_dv_mainten_record_plan` (`plan_id`),
  KEY `idx_mes_dv_mainten_record_mid` (`machinery_id`),
  KEY `idx_mes_dv_mainten_record_user` (`user_id`),
  KEY `idx_mes_dv_mainten_record_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes mainten record';

CREATE TABLE IF NOT EXISTS `mes_dv_check_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `plan_id` bigint DEFAULT NULL COMMENT 'plan id',
  `machinery_id` bigint NOT NULL COMMENT 'machinery id',
  `check_time` datetime DEFAULT NULL COMMENT 'check time',
  `user_id` bigint DEFAULT NULL COMMENT 'user id',
  `status` tinyint NOT NULL DEFAULT '10' COMMENT 'status',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  KEY `idx_mes_dv_check_record_plan` (`plan_id`),
  KEY `idx_mes_dv_check_record_mid` (`machinery_id`),
  KEY `idx_mes_dv_check_record_user` (`user_id`),
  KEY `idx_mes_dv_check_record_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes check record';

CREATE TABLE IF NOT EXISTS `mes_wm_barcode_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `format` tinyint NOT NULL COMMENT 'barcode format',
  `biz_type` int NOT NULL COMMENT 'biz type',
  `content_format` varchar(255) DEFAULT NULL COMMENT 'content format',
  `content_example` varchar(255) DEFAULT NULL COMMENT 'content example',
  `auto_generate_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT 'auto generate flag',
  `default_template` varchar(255) DEFAULT NULL COMMENT 'default template',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT 'status',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mes_wm_barcode_config_biz` (`biz_type`, `tenant_id`),
  KEY `idx_mes_wm_barcode_config_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes barcode config';

CREATE TABLE IF NOT EXISTS `mes_wm_barcode` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `config_id` bigint NOT NULL COMMENT 'config id',
  `format` tinyint NOT NULL COMMENT 'barcode format',
  `biz_type` int NOT NULL COMMENT 'biz type',
  `content` varchar(255) NOT NULL COMMENT 'content',
  `biz_id` bigint NOT NULL COMMENT 'biz id',
  `biz_code` varchar(64) NOT NULL COMMENT 'biz code',
  `biz_name` varchar(100) DEFAULT NULL COMMENT 'biz name',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT 'status',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mes_wm_barcode_content` (`content`, `tenant_id`),
  UNIQUE KEY `uk_mes_wm_barcode_biz` (`biz_type`, `biz_id`, `tenant_id`),
  KEY `idx_mes_wm_barcode_config` (`config_id`),
  KEY `idx_mes_wm_barcode_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes barcode';

CREATE TABLE IF NOT EXISTS `mes_md_auto_code_rule` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(64) NOT NULL COMMENT 'rule code',
  `name` varchar(100) NOT NULL COMMENT 'rule name',
  `description` varchar(500) DEFAULT NULL COMMENT 'description',
  `max_length` int DEFAULT NULL COMMENT 'max length',
  `padded` bit(1) NOT NULL DEFAULT b'0' COMMENT 'padded',
  `padded_char` varchar(8) DEFAULT NULL COMMENT 'padded char',
  `padded_method` tinyint DEFAULT NULL COMMENT 'padded method',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT 'status',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mes_md_auto_code_rule_code` (`code`, `tenant_id`),
  KEY `idx_mes_md_auto_code_rule_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes auto code rule';

CREATE TABLE IF NOT EXISTS `mes_md_auto_code_part` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rule_id` bigint NOT NULL COMMENT 'rule id',
  `sort` int NOT NULL DEFAULT '1' COMMENT 'sort',
  `type` tinyint NOT NULL COMMENT 'type',
  `length` int NOT NULL DEFAULT '0' COMMENT 'length',
  `date_format` varchar(32) DEFAULT NULL COMMENT 'date format',
  `fix_character` varchar(64) DEFAULT NULL COMMENT 'fixed char',
  `serial_start_no` int DEFAULT NULL COMMENT 'serial start',
  `serial_step` int DEFAULT NULL COMMENT 'serial step',
  `cycle_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT 'cycle flag',
  `cycle_method` tinyint DEFAULT NULL COMMENT 'cycle method',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remark',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  KEY `idx_mes_md_auto_code_part_rule` (`rule_id`),
  KEY `idx_mes_md_auto_code_part_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes auto code part';

CREATE TABLE IF NOT EXISTS `mes_md_auto_code_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rule_id` bigint NOT NULL COMMENT 'rule id',
  `result` varchar(128) NOT NULL COMMENT 'result',
  `serial_no` bigint DEFAULT NULL COMMENT 'serial number',
  `input_char` varchar(100) DEFAULT NULL COMMENT 'input char',
  `creator` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `updater` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT 'deleted',
  `tenant_id` bigint NOT NULL DEFAULT '1' COMMENT 'tenant id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mes_md_auto_code_record_result` (`result`, `tenant_id`),
  KEY `idx_mes_md_auto_code_record_rule` (`rule_id`),
  KEY `idx_mes_md_auto_code_record_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='mes auto code record';

INSERT INTO `mes_md_workshop`
(`id`, `code`, `name`, `area`, `charge_user_id`, `status`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1001, 'WS-001', 'UAV Maintenance Workshop', 320.50, 1, 0, 'mvp sample workshop', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`name` = VALUES(`name`),
`area` = VALUES(`area`),
`charge_user_id` = VALUES(`charge_user_id`),
`status` = VALUES(`status`),
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `mes_dv_machinery_type`
(`id`, `code`, `name`, `parent_id`, `status`, `sort`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1000, 'DV-ROOT', '设备类型根节点', 0, 1, 1, '系统保留根节点，不参与选择', '1', '1', b'0', 1),
(1001, 'AIRCRAFT', '无人机整机', 0, 0, 10, '无人机主机设备', '1', '1', b'0', 1),
(1002, 'REMOTE_CONTROLLER', '遥控器', 0, 0, 20, '飞手操控终端', '1', '1', b'0', 1),
(1003, 'GIMBAL_PAYLOAD', '云台载荷', 0, 0, 30, '相机、喊话器、探照灯等载荷', '1', '1', b'0', 1),
(1004, 'RTK_DEVICE', 'RTK设备', 0, 0, 40, '定位与差分相关设备', '1', '1', b'0', 1),
(1005, 'CHARGING_DEVICE', '充电设备', 0, 0, 50, '电池或整机充电配套设备', '1', '1', b'0', 1),
(1006, 'HANGAR_DOCK', '机库/机场柜', 0, 0, 60, '机库、机场柜等停放保障设备', '1', '1', b'0', 1),
(1007, 'GROUND_STATION', '地面站终端', 0, 0, 70, '地面站、图传与调度终端', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`name` = VALUES(`name`),
`code` = VALUES(`code`),
`parent_id` = VALUES(`parent_id`),
`status` = VALUES(`status`),
`sort` = VALUES(`sort`),
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `mes_dv_check_plan`
(`id`, `code`, `name`, `type`, `start_date`, `end_date`, `cycle_type`, `cycle_count`, `status`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1101, 'PLAN-MAINT-001', 'Preflight Maintenance Plan', 2, '2026-05-01 00:00:00', '2026-12-31 23:59:59', 3, 30, 1, 'mvp maintenance plan', '1', '1', b'0', 1),
(1102, 'PLAN-CHECK-001', 'Release Check Plan', 1, '2026-05-01 00:00:00', '2026-12-31 23:59:59', 3, 7, 1, 'mvp check plan', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`name` = VALUES(`name`),
`type` = VALUES(`type`),
`start_date` = VALUES(`start_date`),
`end_date` = VALUES(`end_date`),
`cycle_type` = VALUES(`cycle_type`),
`cycle_count` = VALUES(`cycle_count`),
`status` = VALUES(`status`),
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `mes_dv_machinery`
(`id`, `code`, `name`, `brand`, `specification`, `machinery_type_id`, `workshop_id`, `status`, `last_mainten_time`, `last_check_time`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1201, 'UAV-MVP-001', 'Inspection UAV 01', 'DJI', 'Matrice 350 RTK', 1001, 1001, 2, '2026-05-08 10:30:00', '2026-05-10 09:00:00', 'primary aircraft', '1', '1', b'0', 1),
(1202, 'UAV-MVP-002', 'Inspection UAV 02', 'Autel', 'EVO Max 4T', 1001, 1001, 1, '2026-05-06 14:00:00', '2026-05-09 08:45:00', 'backup aircraft', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`name` = VALUES(`name`),
`brand` = VALUES(`brand`),
`specification` = VALUES(`specification`),
`machinery_type_id` = VALUES(`machinery_type_id`),
`workshop_id` = VALUES(`workshop_id`),
`status` = VALUES(`status`),
`last_mainten_time` = VALUES(`last_mainten_time`),
`last_check_time` = VALUES(`last_check_time`),
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `mes_dv_battery`
(`id`, `code`, `serial_number`, `model`, `workshop_id`, `linked_machinery_id`, `soh`, `cycle_count`, `last_check_time`, `check_source`, `health_status`, `source_evidence`, `recommendation`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1251, 'YA-BT-00891', 'BT24PO31052', 'TB65', 1001, 1201, 87, 186, '2026-05-10 08:40:00', 'report', 'warning', 'manual report uploaded', 'observe before long mission', 'sample battery 1', '1', '1', b'0', 1),
(1252, 'YA-BT-00912', 'BT25HZ01018', 'TB65', 1001, 1201, 93, 72, '2026-05-08 14:20:00', 'BMS', 'normal', 'BMS 自动同步，最近一次健康检查已回传', '可正常执行巡检任务', 'sample battery 2', '1', '1', b'0', 1),
(1253, 'YA-BT-01003', 'BT24SU22731', 'TB65', 1001, 1202, 78, 244, '2026-05-09 17:30:00', 'bench', 'warning', 'bench test uploaded', 'limit short daytime mission and recheck', 'sample battery 3', '1', '1', b'0', 1),
(1254, 'YA-BT-01007', 'BT24SU22739', 'TB65', 1001, 1202, 61, 398, '2026-05-06 11:10:00', '人工导入', 'danger', '人工导入检测结果并附检测附件，命中低寿命强规则', '禁止放行，需完成更换后再绑定主机', 'sample battery 4', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`serial_number` = VALUES(`serial_number`),
`model` = VALUES(`model`),
`workshop_id` = VALUES(`workshop_id`),
`linked_machinery_id` = VALUES(`linked_machinery_id`),
`soh` = VALUES(`soh`),
`cycle_count` = VALUES(`cycle_count`),
`last_check_time` = VALUES(`last_check_time`),
`check_source` = VALUES(`check_source`),
`health_status` = VALUES(`health_status`),
`source_evidence` = VALUES(`source_evidence`),
`recommendation` = VALUES(`recommendation`),
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `mes_dv_check_plan_machinery`
(`id`, `plan_id`, `machinery_id`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1601, 1101, 1201, 'maint plan binding', '1', '1', b'0', 1),
(1602, 1102, 1201, 'check plan binding', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `mes_dv_repair`
(`id`, `code`, `name`, `machinery_id`, `require_date`, `finish_date`, `confirm_date`, `result`, `accepted_user_id`, `confirm_user_id`, `source_doc_type`, `source_doc_id`, `source_doc_code`, `status`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1301, 'REP-MVP-001', 'Gimbal Offset Repair', 1201, '2026-05-07 09:00:00', '2026-05-08 16:30:00', '2026-05-09 11:00:00', 1, 1, 104, NULL, NULL, NULL, 4, 'done and accepted', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`name` = VALUES(`name`),
`machinery_id` = VALUES(`machinery_id`),
`require_date` = VALUES(`require_date`),
`finish_date` = VALUES(`finish_date`),
`confirm_date` = VALUES(`confirm_date`),
`result` = VALUES(`result`),
`accepted_user_id` = VALUES(`accepted_user_id`),
`confirm_user_id` = VALUES(`confirm_user_id`),
`status` = VALUES(`status`),
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `mes_dv_mainten_record`
(`id`, `plan_id`, `machinery_id`, `mainten_time`, `user_id`, `status`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1401, 1101, 1201, '2026-05-08 10:30:00', 1, 4, 'propeller and motor maintenance completed', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`plan_id` = VALUES(`plan_id`),
`machinery_id` = VALUES(`machinery_id`),
`mainten_time` = VALUES(`mainten_time`),
`user_id` = VALUES(`user_id`),
`status` = VALUES(`status`),
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `mes_dv_check_record`
(`id`, `plan_id`, `machinery_id`, `check_time`, `user_id`, `status`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1501, 1102, 1201, '2026-05-10 09:00:00', 104, 20, 'release check passed', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`plan_id` = VALUES(`plan_id`),
`machinery_id` = VALUES(`machinery_id`),
`check_time` = VALUES(`check_time`),
`user_id` = VALUES(`user_id`),
`status` = VALUES(`status`),
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `mes_wm_barcode_config`
(`id`, `format`, `biz_type`, `content_format`, `content_example`, `auto_generate_flag`, `default_template`, `status`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1701, 1, 400, '{BUSINESSCODE}', 'UAV-MVP-001', b'1', NULL, 0, 'machinery barcode auto generation', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`format` = VALUES(`format`),
`content_format` = VALUES(`content_format`),
`content_example` = VALUES(`content_example`),
`auto_generate_flag` = VALUES(`auto_generate_flag`),
`status` = VALUES(`status`),
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `mes_wm_barcode`
(`id`, `config_id`, `format`, `biz_type`, `content`, `biz_id`, `biz_code`, `biz_name`, `status`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1751, 1701, 1, 400, 'UAV-MVP-001', 1201, 'UAV-MVP-001', 'Inspection UAV 01', 0, 'sample machinery barcode', '1', '1', b'0', 1),
(1752, 1701, 1, 400, 'UAV-MVP-002', 1202, 'UAV-MVP-002', 'Inspection UAV 02', 0, 'sample machinery barcode', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`config_id` = VALUES(`config_id`),
`format` = VALUES(`format`),
`biz_code` = VALUES(`biz_code`),
`biz_name` = VALUES(`biz_name`),
`status` = VALUES(`status`),
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `mes_md_auto_code_rule`
(`id`, `code`, `name`, `description`, `max_length`, `padded`, `padded_char`, `padded_method`, `status`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1801, 'DV_MACHINERY_CODE', 'Machinery Code Rule', 'mvp machinery code rule', 16, b'0', '0', 1, 0, 'UAV-date-serial', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`name` = VALUES(`name`),
`description` = VALUES(`description`),
`max_length` = VALUES(`max_length`),
`padded` = VALUES(`padded`),
`padded_char` = VALUES(`padded_char`),
`padded_method` = VALUES(`padded_method`),
`status` = VALUES(`status`),
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `mes_md_auto_code_part`
(`id`, `rule_id`, `sort`, `type`, `length`, `date_format`, `fix_character`, `serial_start_no`, `serial_step`, `cycle_flag`, `cycle_method`, `remark`, `creator`, `updater`, `deleted`, `tenant_id`)
VALUES
(1811, 1801, 1, 3, 4, NULL, 'UAV-', NULL, NULL, b'0', NULL, 'fixed prefix', '1', '1', b'0', 1),
(1812, 1801, 2, 2, 8, 'yyyyMMdd', NULL, NULL, NULL, b'0', NULL, 'current date', '1', '1', b'0', 1),
(1813, 1801, 3, 4, 4, NULL, NULL, 1, 1, b'1', 3, 'daily serial', '1', '1', b'0', 1)
ON DUPLICATE KEY UPDATE
`rule_id` = VALUES(`rule_id`),
`sort` = VALUES(`sort`),
`type` = VALUES(`type`),
`length` = VALUES(`length`),
`date_format` = VALUES(`date_format`),
`fix_character` = VALUES(`fix_character`),
`serial_start_no` = VALUES(`serial_start_no`),
`serial_step` = VALUES(`serial_step`),
`cycle_flag` = VALUES(`cycle_flag`),
`cycle_method` = VALUES(`cycle_method`),
`remark` = VALUES(`remark`),
`updater` = '1',
`deleted` = b'0',
`tenant_id` = VALUES(`tenant_id`);

INSERT INTO `system_menu`
(`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `updater`, `deleted`)
VALUES
(950000, 'MES MVP Hidden', '', 1, 900, 0, 'mes-mvp-hidden', 'ep:lock', NULL, NULL, 0, b'0', b'0', b'0', '1', '1', b'0'),
(950001, 'Machinery Query', 'mes:dv-machinery:query', 3, 1, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950002, 'Machinery Create', 'mes:dv-machinery:create', 3, 2, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950003, 'Machinery Update', 'mes:dv-machinery:update', 3, 3, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950004, 'Machinery Delete', 'mes:dv-machinery:delete', 3, 4, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950005, 'Machinery Export', 'mes:dv-machinery:export', 3, 5, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950006, 'Machinery Import', 'mes:dv-machinery:import', 3, 6, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950007, 'Machinery Type Query', 'mes:dv-machinery-type:query', 3, 7, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950008, 'Workshop Query', 'mes:md-workshop:query', 3, 8, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950009, 'Repair Query', 'mes:dv-repair:query', 3, 9, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950010, 'Repair Create', 'mes:dv-repair:create', 3, 10, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950011, 'Repair Update', 'mes:dv-repair:update', 3, 11, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950012, 'Mainten Query', 'mes:dv-mainten-record:query', 3, 12, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950013, 'Mainten Create', 'mes:dv-mainten-record:create', 3, 13, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950014, 'Mainten Update', 'mes:dv-mainten-record:update', 3, 14, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950015, 'Check Query', 'mes:dv-check-record:query', 3, 15, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950016, 'Check Create', 'mes:dv-check-record:create', 3, 16, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950017, 'Check Update', 'mes:dv-check-record:update', 3, 17, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950018, 'Auto Code Query', 'mes:auto-code-rule:query', 3, 18, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0'),
(950019, 'Barcode Query', 'mes:wm-barcode:query', 3, 19, 950000, '', '', '', '', 0, b'0', b'0', b'0', '1', '1', b'0')
ON DUPLICATE KEY UPDATE
`name` = VALUES(`name`),
`permission` = VALUES(`permission`),
`type` = VALUES(`type`),
`sort` = VALUES(`sort`),
`parent_id` = VALUES(`parent_id`),
`path` = VALUES(`path`),
`icon` = VALUES(`icon`),
`component` = VALUES(`component`),
`component_name` = VALUES(`component_name`),
`status` = VALUES(`status`),
`visible` = VALUES(`visible`),
`keep_alive` = VALUES(`keep_alive`),
`always_show` = VALUES(`always_show`),
`updater` = '1',
`deleted` = b'0';

INSERT INTO `system_role_menu`
(`role_id`, `menu_id`, `creator`, `updater`, `deleted`, `tenant_id`)
SELECT 1, m.id, '1', '1', b'0', 1
FROM `system_menu` m
WHERE m.id BETWEEN 950000 AND 950019
  AND NOT EXISTS (
    SELECT 1
    FROM `system_role_menu` rm
    WHERE rm.role_id = 1
      AND rm.menu_id = m.id
      AND rm.deleted = b'0'
      AND rm.tenant_id = 1
  );

CREATE TABLE IF NOT EXISTS `yian_official_site_lead` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `contact_name` varchar(30) NOT NULL COMMENT '联系人姓名',
  `company_name` varchar(100) NOT NULL COMMENT '公司名称',
  `phone_number` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '联系邮箱',
  `interested_scene` varchar(50) DEFAULT NULL COMMENT '关注场景',
  `message` varchar(500) DEFAULT NULL COMMENT '需求描述',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态 0-待跟进 1-已联系 2-已转化',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_yian_official_site_lead_status` (`status`),
  KEY `idx_yian_official_site_lead_phone` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='翼安智链官网咨询线索表';
