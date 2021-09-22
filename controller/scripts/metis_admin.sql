drop database if exists dev_metis_admin;
CREATE DATABASE  IF NOT EXISTS dev_metis_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use dev_metis_admin;
/*
Navicat MySQL Data Transfer

Source Server         : 192.168.9.191
Source Server Version : 50724
Source Host           : 192.168.9.191:3306
Source Database       : metis_admin

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2021-07-30 11:39:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bootstrap_node
-- ----------------------------
DROP TABLE IF EXISTS `bootstrap_node`;
CREATE TABLE `bootstrap_node` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `node_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点ID，添加成功后由调度服务返回',
  `node_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '节点名称，种子节点名称不可重复，设置之后无法修改',
  `internal_ip` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内部IP',
  `internal_port` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内部端口',
  `is_init_node` tinyint(1) DEFAULT '0' COMMENT '是否是初始节点，0否，1是',
  `status` tinyint(1) DEFAULT '0' COMMENT '节点状态：0 网络连接失败，1 网络连接成功',
  `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='种子节点配置表';

-- ----------------------------
-- Table structure for global_data_file
-- ----------------------------
DROP TABLE IF EXISTS `global_data_file`;
CREATE TABLE `global_data_file` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织身份ID',
  `org_name` varchar(256) CHARACTER SET utf8mb4 NOT NULL COMMENT '组织名称',
  `file_id` varchar(256) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '源文件ID',
  `meta_data_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '元数据ID,hash',
  `file_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '源文件名称',
  `file_path` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件存储路径',
  `file_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件后缀/类型, csv',
  `resource_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据名称',
  `size` BIGINT NOT NULL DEFAULT '0' COMMENT '文件大小(字节)',
  `rows` BIGINT NOT NULL DEFAULT '0' COMMENT '数据行数(不算title)',
  `columns` INT NOT NULL DEFAULT '0' COMMENT '数据列数',
  `has_title` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否带标题,0表示不带，1表示带标题',
  `remarks` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据描述',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'created' COMMENT '数据的状态 (created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表)',
  `publish_time` datetime DEFAULT NULL COMMENT '元数据发布时间',
  `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `file_id` (`file_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='全网数据文件表';

-- ----------------------------
-- Table structure for global_meta_data_column
-- ----------------------------
DROP TABLE IF EXISTS `global_meta_data_column`;
CREATE TABLE `global_meta_data_column` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `file_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件ID',
  `column_idx` INT DEFAULT NULL COMMENT '列索引',
  `column_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '列名',
  `column_type` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '列类型',
  `size` BIGINT DEFAULT '0' COMMENT '列大小（byte）',
  `remarks` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '列描述',
  `visible` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否对外可见 Y:可见，N:不可见',
  `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='全网数据文件表列详细表';

-- ----------------------------
-- Table structure for global_power
-- ----------------------------
DROP TABLE IF EXISTS `global_power`;
CREATE TABLE `global_power` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '算力提供方身份标识',
  `org_name` varchar(128) CHARACTER SET utf8mb4 NOT NULL COMMENT '组织名称',
  `total_core` INT NOT NULL DEFAULT '0' COMMENT '总CPU，单位：个',
  `total_Memory` BIGINT NOT NULL DEFAULT '0' COMMENT '总内存，单位：byte',
  `total_Bandwidth` BIGINT NOT NULL DEFAULT '0' COMMENT '总带宽，单位：bps',
  `used_core` INT NOT NULL DEFAULT '0' COMMENT '已使用CPU信息，单位：个',
  `used_Memory` BIGINT NOT NULL DEFAULT '0' COMMENT '已使用内存，单位：byte',
  `used_Bandwidth` BIGINT NOT NULL DEFAULT '0' COMMENT '已使用带宽，单位：bps',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `identity_id` (`identity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='全网算力资源表 记录全网的算力资源信息';

-- ----------------------------
-- Table structure for local_data_file
-- ----------------------------
DROP TABLE IF EXISTS `local_data_file`;
CREATE TABLE `local_data_file` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织身份ID',
  `file_id` varchar(256) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '源文件ID，上传文件成功后返回源文件ID',
  `file_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '源文件名称',
  `file_path` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件存储路径',
  `file_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件后缀/类型, csv',
  `size` BIGINT NOT NULL DEFAULT '0' COMMENT '文件大小(字节)',
  `rows` BIGINT NOT NULL DEFAULT '0' COMMENT '数据行数(不算title)',
  `columns` INT NOT NULL DEFAULT '0' COMMENT '数据列数',
  `has_title` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否带标题,0表示不带，1表示带标题',
  `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `file_id` (`file_id`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织数据文件表，数据信息表';

-- ----------------------------
-- Table structure for local_meta_data
-- ----------------------------
DROP TABLE IF EXISTS `local_meta_data`;
CREATE TABLE `local_meta_data` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `data_file_id` INT NOT NULL COMMENT '数据文件表自增id（不是文件id）',
  `meta_data_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '元数据ID,hash',
  `meta_data_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '元数据名称',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'entered' COMMENT '数据的状态 (entered：录入数据(创建未发布新表之前的操作); created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表; deleted:已删除的表)',
  `has_other_save` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否另存，0非另存，1是另存',
  `publish_time` datetime DEFAULT NULL COMMENT '元数据发布时间',
  `remarks` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '元数据描述',
  `industry` int(4) DEFAULT NULL COMMENT '所属行业 1：金融业（银行）、2：金融业（保险）、3：金融业（证券）、4：金融业（其他）、5：ICT、 6：制造业、 7：能源业、 8：交通运输业、 9 ：医疗健康业、 10 ：公共服务业、 11：传媒广告业、 12 ：其他行业',
  `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `meta_data_id` (`meta_data_id`),
  KEY `data_file_id` (`data_file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织元数据文件表，文件的元数据信息';


-- ----------------------------
-- Table structure for local_meta_data_column
-- ----------------------------
DROP TABLE IF EXISTS `local_meta_data_column`;
CREATE TABLE `local_meta_data_column` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `meta_id` INT NOT NULL COMMENT '元数据自增id',
  `column_idx` INT DEFAULT NULL COMMENT '列索引',
  `column_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '列名',
  `column_type` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '列类型',
  `size` BIGINT DEFAULT '0' COMMENT '列大小（byte）',
  `remarks` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '列描述',
  `visible` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '是否对外可见 Y:可见，N:不可见',
  `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY (`meta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织数据文件表列详细表，描述源文件中每一列的列信息';

-- ----------------------------
-- Table structure for local_data_auth
-- ----------------------------
DROP TABLE IF EXISTS `local_data_auth`;
CREATE TABLE `local_data_auth` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `auth_id` varchar(256) NOT NULL COMMENT '元数据授权申请Id',
  `meta_data_id` varchar(256) NOT NULL COMMENT '元数据ID',
  `apply_user` varchar(256) DEFAULT NULL COMMENT '发起任务的用户的信息 (task是属于用户的)',
  `user_type` int(4) DEFAULT '0' COMMENT '发起任务用户类型 (0: 未定义; 1: 以太坊地址; 2: Alaya地址; 3: PlatON地址)',
  `auth_type` int(4) DEFAULT '0' COMMENT '授权方式(0：未定义，1：时间，2：次数)',
  `auth_value_amount` int(100) DEFAULT '0' COMMENT '授权值(以授权次数)，auth_type = 2使用此字段',
  `auth_value_start_at` datetime DEFAULT NULL COMMENT '授权值开始时间，auth_type = 1使用此字段',
  `auth_value_end_at` datetime DEFAULT NULL COMMENT '授权值结束时间，auth_type = 1使用此字段',
  `create_at` datetime DEFAULT NULL COMMENT '授权申请发起时间',
  `auth_at` datetime DEFAULT NULL COMMENT '授权数据时间',
  `status` int(4) DEFAULT '0' COMMENT '授权数据状态：0：等待授权审核，1:同意， 2:拒绝 ',
  `identity_name` varchar(256) DEFAULT NULL COMMENT '元数据所属的组织信息，组织名称',
  `identity_id` varchar(256) DEFAULT NULL COMMENT '元数据所属的组织信息,组织的身份标识Id',
  `identity_node_id` varchar(256) DEFAULT NULL COMMENT '组织中调度服务的 nodeId',
  `rec_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_meta_data_id` (`meta_data_id`) USING BTREE COMMENT '元数据ID唯一'
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='本组织申请授权数据表';


-- ----------------------------
-- Table structure for local_seed_node
-- ----------------------------
DROP TABLE IF EXISTS `local_seed_node`;
CREATE TABLE `local_seed_node` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织身份ID',
  `out_node_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '外部节点id',
  `seed_node_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布后底层返回的host唯一ID',
  `seed_node_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点名称',
  `internal_ip` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点内部IP',
  `internal_port` INT DEFAULT NULL COMMENT '节点内部端口',
  `conn_status` int(2) DEFAULT -1 COMMENT '节点状态 -1: 网络连接失败; 0: 网络连接成功',
  `init_flag` int(2) DEFAULT NULL COMMENT '是否是初始节点(0:否, 1:是)',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  key `seed_node_id` (`seed_node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织种子节点配置表 配置当前参与方的种子节点信息';

-- ----------------------------
-- Table structure for local_data_node
-- ----------------------------
DROP TABLE IF EXISTS `local_data_node`;
CREATE TABLE `local_data_node` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织身份ID',
  `node_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布后底层返回的host唯一ID',
  `host_Name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点名称',
  `internal_IP` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点内部IP',
  `internal_Port` INT DEFAULT NULL COMMENT '节点内部端口',
  `external_IP` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点外部IP',
  `external_Port` INT DEFAULT NULL COMMENT '节点外部端口',
  `conn_Status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT '-1' COMMENT '节点状态 -1: 未被调度服务连接上; 0: 连接上;',
  `conn_message` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点(连接失败)信息',
  `conn_Time` datetime DEFAULT NULL COMMENT '节点上一次连接时间',
  `status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT 'disabled' COMMENT '节点状态 enabled：可用, disabled:不可用',
  `remarks` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点备注',
  `rec_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织数据节点配置表 配置数据节点相关信息';

-- ----------------------------
-- Table structure for local_org
-- ----------------------------
DROP TABLE IF EXISTS `local_org`;
CREATE TABLE `local_org` (
  `name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机构名称',
  `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构身份标识ID',
  `carrier_node_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构调度服务node id，入网后可以获取到',
  `carrier_ip` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调度服务IP地址',
  `carrier_port` INT DEFAULT NULL COMMENT '调度服务端口号',
  `carrier_conn_status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '连接状态 enabled：可用, disabled:不可用',
  `carrier_status` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '调度服务的状态：active: 活跃; leave: 离开网络; join: 加入网络 unuseful: 不可用',
  `conn_node_count` INT  DEFAULT NULL COMMENT '节点连接的数量',
  `carrier_conn_time` datetime DEFAULT NULL COMMENT '服务连接时间',
  `status` tinyint(1) DEFAULT '0' COMMENT '0未入网，1已入网',
  `rec_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本地组织信息表';

-- ----------------------------
-- Table structure for local_power_history
-- ----------------------------
DROP TABLE IF EXISTS `local_power_history`;
CREATE TABLE `local_power_history` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `power_node_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布后底层返回的host唯一ID',
  `refresh_status` varchar(2) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '刷新时间标志（0：小时，1：天）',
  `used_memory` BIGINT DEFAULT '0' COMMENT '使用的内存, 字节',
  `used_core` INT DEFAULT '0' COMMENT '使用的core',
  `used_bandwidth` BIGINT DEFAULT '0' COMMENT '使用的带宽, bps',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `power_node_id` (`power_node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='计算节点使用资源定时刷新数据表（按时间段刷新）';

-- ----------------------------
-- Table structure for local_power_join_task
-- ----------------------------
DROP TABLE IF EXISTS `local_power_join_task`;
CREATE TABLE `local_power_join_task` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `power_node_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '计算节点ID',
  `task_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务id',
  `task_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务名称',
  `owner_identity_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发起方ID',
  `owner_identity_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发起方名称',
  `task_start_time` datetime DEFAULT NULL COMMENT '发起时间',
  `result_side_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果方ID',
  `result_side_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果方名称',
  `coordinate_side_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '协作方ID',
  `coordinate_side_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '协作方名称',
  `used_memory` BIGINT DEFAULT '0' COMMENT '使用的内存, 字节（占此节点总算力比）',
  `used_core` INT DEFAULT '0' COMMENT '使用的core（占此节点总算力比）',
  `used_bandwidth` BIGINT DEFAULT '0' COMMENT '使用的带宽, bps（占此节点总算力比）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `power_node_id` (`power_node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='计算节点参与任务信息';

-- ----------------------------
-- Table structure for local_power_node
-- ----------------------------
DROP TABLE IF EXISTS `local_power_node`;
CREATE TABLE `local_power_node` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织身份ID',
  `power_node_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布后底层返回的host唯一ID',
  `power_node_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点名称(同一个组织不可重复）',
  `internal_ip` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点内网IP',
  `internal_port` INT DEFAULT NULL COMMENT '节点内网端口',
  `external_ip` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点外网IP',
  `external_port` INT DEFAULT NULL COMMENT '节点外网端口',
  `start_time` datetime DEFAULT NULL COMMENT '节点启用时间',
  `remarks` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点备注',
  `conn_time` datetime DEFAULT NULL COMMENT '节点上一次连接时间',
  `conn_status` varchar(5) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点状态，-1: 未被调度服务连接上; 0: 连接上; 1: 算力启用<计算服务>; 2: 算力被占用(计算服务算力正在被任务占用)',
  `conn_message` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点(连接失败)信息',
  `power_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点启动后底层返回的算力ID',
  `power_status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '算力状态 (create: 还未发布的算力; release: 已发布的算力; revoke: 已撤销的算力)',
  `memory` BIGINT NOT NULL DEFAULT '0' COMMENT '计算host内存, 字节',
  `core` INT NOT NULL DEFAULT '0' COMMENT '计算host core',
  `bandwidth` BIGINT NOT NULL DEFAULT '0' COMMENT '计算host带宽, bps',
  `used_memory` BIGINT DEFAULT '0' COMMENT '使用的内存, 字节',
  `used_core` INT DEFAULT '0' COMMENT '使用的core',
  `used_bandwidth` BIGINT DEFAULT '0' COMMENT '使用的带宽, bps',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `power_node_id` (`power_node_id`),
  KEY `power_node_name` (`power_node_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织计算节点配置表 配置当前参与方的计算节点信息';

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `config_Key` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置项',
  `config_Value` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置值',
  `status` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '配置状态 enabled：可用, disabled:不可用',
  `desc` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置描述',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_config_key` (`config_Key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表 管理系统相关配置项，使用k-v形式';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `user_name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码 MD5加密',
  `status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户状态 enabled：可用, disabled:不可用',
  `is_Master` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否为管理员 1管理员，0非管理',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_name` (`user_name`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表 管理系统用户登陆信息';

INSERT INTO `sys_user` (`id`, `user_name`, `password`, `status`, `is_Master`, `rec_update_time`) VALUES ('1', 'admin', 'admin', 'enabled', '1', '2021-07-12 03:14:45');


-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `task_Id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID',
  `task_Name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务名称',
  `owner_Identity_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务发起方身份',
  `apply_user` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发起任务的用户的信息 (task是属于用户的)',
  `user_type` int(4) DEFAULT '0' COMMENT '发起任务用户类型 (0: 未定义; 1: 以太坊地址; 2: Alaya地址; 3: PlatON地址)',
  `create_At` datetime DEFAULT NULL COMMENT '任务发起时间',
  `start_At` datetime DEFAULT NULL COMMENT '任务启动时间',
  `auth_At` datetime DEFAULT NULL COMMENT '任务授权时间',
  `auth_Status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务授权状态: pending:等待授权、denied:授权未通过',
  `end_At` datetime DEFAULT NULL COMMENT '任务结束时间',
  `status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务状态 pending: 等在中; running: 计算中; failed: 失败; success: 成功)',
  `role` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '我在任务中的角色 (owner: 任务发起方; dataSupplier: 数据提供方: powerSupplier: 算力提供方; receiver: 结果接收方; algoSupplier:算法提供方)',
  `duration` datetime DEFAULT NULL COMMENT '任务声明计算时间',
  `cost_core` INT DEFAULT '0' COMMENT '任务声明所需CPU',
  `cost_Memory` BIGINT DEFAULT '0' COMMENT '任务声明所需内存',
  `cost_Bandwidth` BIGINT DEFAULT '0' COMMENT '任务声明所需带宽',
  `alg_Identity_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '算法提供方身份ID',
  `reviewed` tinyint(1) DEFAULT '0' COMMENT '任务是否被查看过，默认为false(0)',
  `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `taskID` (`task_Id`) USING BTREE COMMENT 'task_id唯一'
) ENGINE=InnoDB AUTO_INCREMENT=38841 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='全网任务表 用于同步本地任务数据以及全网的相关数据';

-- ----------------------------
-- Table structure for task_data_provider
-- ----------------------------
DROP TABLE IF EXISTS `task_data_provider`;
CREATE TABLE `task_data_provider` (
  `task_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID,hash',
  `meta_data_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参与任务的元数据ID',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据提供者组织身份ID',
  `meta_data_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '元数据名称',
  PRIMARY KEY (`task_id`,`meta_data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务数据提供方表 存储某个任务数据提供方的信息';

-- ----------------------------
-- Table structure for task_event
-- ----------------------------
DROP TABLE IF EXISTS `task_event`;
CREATE TABLE `task_event` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `task_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID,hash',
  `event_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '事件类型',
  `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产生事件的组织身份ID',
  `event_at` datetime NOT NULL COMMENT '产生事件的时间',
  `event_content` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '事件内容',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4631 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务事件表';

-- ----------------------------
-- Table structure for task_org
-- ----------------------------
DROP TABLE IF EXISTS `task_org`;
CREATE TABLE `task_org` (
  `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机构身份标识ID(主键)',
  `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构名称',
  `carrier_node_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织中调度服务的 nodeId',
  `rec_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务组织信息表，用于存储从调度服务获取的任务数据快照中组织信息数据';

-- ----------------------------
-- Table structure for task_power_provider
-- ----------------------------
DROP TABLE IF EXISTS `task_power_provider`;
CREATE TABLE `task_power_provider` (
  `task_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID,hash',
  `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '算力提供者组织身份ID',
  `total_core` INT DEFAULT '0' COMMENT '任务总CPU信息',
  `used_core` INT DEFAULT '0' COMMENT '任务占用CPU信息',
  `total_memory` BIGINT DEFAULT '0' COMMENT '任务总内存信息',
  `used_memory` BIGINT DEFAULT '0' COMMENT '任务占用内存信息',
  `total_Bandwidth` BIGINT DEFAULT '0' COMMENT '任务总带宽信息',
  `used_Bandwidth` BIGINT DEFAULT '0' COMMENT '任务占用带宽信息',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`task_id`,`identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务算力提供方表 任务数据提供方基础信息';

-- ----------------------------
-- Table structure for task_result_consumer
-- ----------------------------
DROP TABLE IF EXISTS `task_result_consumer`;
CREATE TABLE `task_result_consumer` (
  `task_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID,hash',
  `consumer_identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '结果消费者组织身份ID',
  `producer_identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '结果产生者的组织身份ID',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`task_id`,`consumer_identity_id`,`producer_identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务结果接收方表 任务结果接收方信息';

-- ----------------------------
-- Table structure for store_calculate_result
-- ----------------------------
DROP TABLE IF EXISTS `store_calculate_result`;
CREATE TABLE `store_calculate_result` (
    `time_interval` varchar(10) NOT NULL COMMENT '时间间隔',
    `store_type` varchar(50) NOT NULL COMMENT '存储类型',
    `reside_time` varchar(10) NOT NULL COMMENT '所属时间',
    `calculate_result` BIGINT DEFAULT 0 COMMENT '计算结果（单位：字节/bps）',
    `status` int(4) DEFAULT 1 COMMENT '是否有效（1:有效，2:无效）',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    KEY (`store_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='存储计算结果表';

-- ----------------------------
-- View structure for v_past_12_month
-- ----------------------------
drop view if exists v_past_12_month;
CREATE VIEW `v_past_12_month` AS
SELECT DATE_FORMAT((CURDATE() - INTERVAL 1 MONTH), '%Y-%m') AS `MONTH`
    UNION SELECT DATE_FORMAT((CURDATE() - INTERVAL 2 MONTH), '%Y-%m') AS `MONTH`
    UNION SELECT DATE_FORMAT((CURDATE() - INTERVAL 3 MONTH), '%Y-%m') AS `MONTH`
    UNION SELECT DATE_FORMAT((CURDATE() - INTERVAL 4 MONTH), '%Y-%m') AS `MONTH`
    UNION SELECT DATE_FORMAT((CURDATE() - INTERVAL 5 MONTH), '%Y-%m') AS `MONTH`
    UNION SELECT DATE_FORMAT((CURDATE() - INTERVAL 6 MONTH), '%Y-%m') AS `MONTH`
    UNION SELECT DATE_FORMAT((CURDATE() - INTERVAL 7 MONTH), '%Y-%m') AS `MONTH`
    UNION SELECT DATE_FORMAT((CURDATE() - INTERVAL 8 MONTH), '%Y-%m') AS `MONTH`
    UNION SELECT DATE_FORMAT((CURDATE() - INTERVAL 9 MONTH), '%Y-%m') AS `MONTH`
    UNION SELECT DATE_FORMAT((CURDATE() - INTERVAL 10 MONTH), '%Y-%m') AS `MONTH`
    UNION SELECT DATE_FORMAT((CURDATE() - INTERVAL 11 MONTH), '%Y-%m') AS `MONTH`
    UNION SELECT DATE_FORMAT((CURDATE() - INTERVAL 12 MONTH), '%Y-%m') AS `MONTH`;