drop database if exists `datum_admin`;

CREATE DATABASE `datum_admin` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use `datum_admin`;
/*
Navicat MySQL Data Transfer

Source Server         : 192.168.9.191
Source Server Version : 50724a
Source Host           : 192.168.9.191:3306
Source Database       : datum_admin

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2021-07-30 11:39:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for data_sync
-- ----------------------------
DROP TABLE IF EXISTS `data_sync`;
CREATE TABLE `data_sync` (
                             `data_type` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据类型',
                             `latest_synced` datetime(3) NOT NULL COMMENT '数据最新同步时间点点，精确到毫秒',
                             PRIMARY KEY (`data_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据同步时间记录';
INSERT INTO data_sync(data_type, latest_synced) VALUES('data_auth_req', '1970-01-01 00:00:00');
INSERT INTO data_sync(data_type, latest_synced) VALUES('local_meta_data', '1970-01-01 00:00:00');
INSERT INTO data_sync(data_type, latest_synced) VALUES('local_task', '1970-01-01 00:00:00');

-- ----------------------------
-- Table structure for data_token
-- ----------------------------
DROP TABLE IF EXISTS `data_token`;
CREATE TABLE `data_token` (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'dataTokenId',
                              `address` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '合约地址',
                              `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约名称',
                              `symbol` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约符号',
                              `init` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '初始发行量',
                              `total` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '总发行量',
                              `decimal` int(11) NOT NULL DEFAULT '18' COMMENT '合约精度',
                              `desc` text COLLATE utf8mb4_unicode_ci COMMENT '价值证明描述',
                              `owner` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '拥有者钱包地址',
                              `meta_data_id` int(11) NOT NULL COMMENT '对应的metaDataId',
                              `holder` int(11) DEFAULT '0' COMMENT '持有人数量',
                              `publish_nonce` int(11) DEFAULT '0' COMMENT '发布凭证交易nonce',
                              `publish_hash` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发布上链的交易hash，供查询发布状态',
                              `up_nonce` int(11) DEFAULT '0' COMMENT '上架交易nonce',
                              `up_hash` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上架dex的交易hash',
                              `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态：0-未发布，1-发布中，2-发布失败，3-发布成功，4-定价中，5-定价失败，6-定价成功',
                              `rec_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              `rec_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `ukey_meta_data_id` (`meta_data_id`) USING HASH COMMENT 'meta_data_id唯一'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据凭证表';

-- ----------------------------
-- Table structure for local_data_auth
-- ----------------------------
DROP TABLE IF EXISTS `local_data_auth`;
CREATE TABLE `local_data_auth` (
                                   `auth_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '元数据授权申请Id',
                                   `meta_data_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '元数据ID',
                                   `apply_user` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发起任务的用户的信息 (task是属于用户的)',
                                   `user_type` int(4) DEFAULT '0' COMMENT '发起任务用户类型 (0: 未定义; 1: 以太坊地址; 2: Alaya地址; 3: PlatON地址)',
                                   `auth_type` int(4) DEFAULT '0' COMMENT '授权方式(0：未定义，1：时间，2：次数)',
                                   `auth_value_amount` int(100) DEFAULT '0' COMMENT '授权值(以授权次数)，auth_type = 2使用此字段',
                                   `auth_value_start_at` datetime DEFAULT NULL COMMENT '授权值开始时间，auth_type = 1使用此字段',
                                   `auth_value_end_at` datetime DEFAULT NULL COMMENT '授权值结束时间，auth_type = 1使用此字段',
                                   `create_at` datetime(3) DEFAULT NULL COMMENT '授权申请发起时间',
                                   `auth_at` datetime(3) DEFAULT NULL COMMENT '授权数据时间',
                                   `status` int(4) DEFAULT '0' COMMENT '授权数据状态：0：等待授权审核，1:同意， 2:拒绝，3:失效(auth_type=1且auth_value_end_at超时) ',
                                   `identity_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '元数据所属的组织信息，组织名称',
                                   `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '元数据所属的组织信息,组织的身份标识Id',
                                   `identity_node_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织中调度服务的 nodeId',
                                   `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                                   PRIMARY KEY (`auth_id`),
                                   KEY `meta_data_id` (`meta_data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织申请授权数据表';

-- ----------------------------
-- Table structure for local_data_file
-- ----------------------------
DROP TABLE IF EXISTS `local_data_file`;
CREATE TABLE `local_data_file` (
                                   `file_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '全网唯一ID，上传文件成功后返回',
                                   `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织身份ID',
                                   `file_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '源文件名称',
                                   `file_path` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件存储路径',
                                   `file_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '文件后缀/类型, 0：未知、1：csv(目前只支持这个)',
                                   `size` bigint(20) NOT NULL DEFAULT '0' COMMENT '文件大小(字节)',
                                   `rows` int(11) NOT NULL DEFAULT '0' COMMENT '数据行数(不算title)',
                                   `columns` int(11) NOT NULL DEFAULT '0' COMMENT '数据列数',
                                   `has_title` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否带标题,0表示不带，1表示带标题',
                                   `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `data_hash` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原始数据内容hash',
                                   `location_type` tinyint(2) DEFAULT '1' COMMENT '源数据的存储位置类型 (组织本地服务器、远端服务器、云等)：0-未知，1-本地，2-远端服务器',
                                   PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织数据文件表，数据信息表';

-- ----------------------------
-- Table structure for local_data_node
-- ----------------------------
DROP TABLE IF EXISTS `local_data_node`;
CREATE TABLE `local_data_node` (
                                   `node_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发布后底层返回的host唯一ID',
                                   `node_name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点名称',
                                   `internal_IP` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点内部IP',
                                   `internal_Port` int(11) DEFAULT NULL COMMENT '节点内部端口',
                                   `external_IP` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点外部IP',
                                   `external_Port` int(11) DEFAULT NULL COMMENT '节点外部端口',
                                   `conn_Status` int(11) DEFAULT '0' COMMENT '节点与调度服务的连接状态，0: 未被调度服务连接上; 1: 连接上;',
                                   `conn_message` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点(连接失败)信息',
                                   `conn_Time` datetime DEFAULT NULL COMMENT '节点上一次连接时间',
                                   `status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT 'disabled' COMMENT '节点状态 enabled：可用, disabled:不可用',
                                   `remarks` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点备注',
                                   `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                                   PRIMARY KEY (`node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织数据节点配置表 配置数据节点相关信息';

-- ----------------------------
-- Table structure for local_meta_data
-- ----------------------------
DROP TABLE IF EXISTS `local_meta_data`;
CREATE TABLE `local_meta_data` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
                                   `file_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件ID',
                                   `meta_data_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '元数据ID,hash',
                                   `meta_data_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '元数据名称',
                                   `status` int(11) NOT NULL DEFAULT '0' COMMENT '元数据的状态 (0: 未知; 1: 还未发布的新表; 2: 已发布的表; 3: 已撤销的表; 4：已删除;5: 发布中; 6：撤回中;7:凭证发布失败;8: 凭证发布中; 9：已发布凭证;10:已绑定凭证)',
                                   `publish_time` datetime DEFAULT NULL COMMENT '元数据发布时间',
                                   `remarks` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '元数据描述',
                                   `industry` int(4) DEFAULT NULL COMMENT '所属行业 1：金融业（银行）、2：金融业（保险）、3：金融业（证券）、4：金融业（其他）、5：ICT、 6：制造业、 7：能源业、 8：交通运输业、 9 ：医疗健康业、 10 ：公共服务业、 11：传媒广告业、 12 ：其他行业',
                                   `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `data_token_id` int(11) DEFAULT NULL COMMENT '数据凭证id',
                                   `owner` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '拥有者钱包地址',
                                   `meta_data_type` tinyint(2) DEFAULT '1' COMMENT '表示该元数据是 普通数据 还是 模型数据的元数据 (0: 未定义; 1: 普通数据元数据; 2: 模型数据元数据)',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `meta_data_id` (`meta_data_id`),
                                   KEY `file_id` (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织元数据文件表，文件的元数据信息';

-- ----------------------------
-- Table structure for local_meta_data_column
-- ----------------------------
DROP TABLE IF EXISTS `local_meta_data_column`;
CREATE TABLE `local_meta_data_column` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
                                          `local_meta_data_db_id` int(11) NOT NULL COMMENT '元数据local_meta_data自增id',
                                          `column_idx` int(11) DEFAULT NULL COMMENT '列索引',
                                          `column_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '列名',
                                          `column_type` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '列类型',
                                          `size` int(11) DEFAULT '0' COMMENT '列大小（byte）',
                                          `remarks` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '列描述',
                                          `visible` tinyint(1) DEFAULT '0' COMMENT '是否可见',
                                          PRIMARY KEY (`id`),
                                          UNIQUE KEY `local_meta_data_db_id` (`local_meta_data_db_id`,`column_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织数据文件表列详细表，描述源文件中每一列的列信息';

-- ----------------------------
-- Table structure for local_org
-- ----------------------------
DROP TABLE IF EXISTS `local_org`;
CREATE TABLE `local_org` (
                             `name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机构名称',
                             `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构身份标识ID',
                             `carrier_node_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构调度服务node id，入网后可以获取到',
                             `carrier_ip` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调度服务IP地址',
                             `carrier_port` int(11) DEFAULT NULL COMMENT '调度服务端口号',
                             `carrier_conn_status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '连接状态 enabled：可用, disabled:不可用',
                             `carrier_status` int(11) DEFAULT '0' COMMENT '调度服务的状态（0:unknown未知、1: active活跃、2:leave: 离开网络、3:join加入网络 4:unuseful不可用）',
                             `conn_node_count` int(11) DEFAULT '0' COMMENT '节点连接的数量',
                             `carrier_conn_time` datetime DEFAULT NULL COMMENT '服务连接时间',
                             `status` tinyint(1) DEFAULT '0' COMMENT '0未入网，1已入网， 99已退网',
                             `local_bootstrap_node` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '当前系统的本地节点，可以作为引导节点提供给三方节点',
                             `local_multi_addr` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '当前系统本地的',
                             `image_url` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织机构图像url',
                             `profile` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织机构简介',
                             `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                             `carrier_wallet` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调度服务钱包地址'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本地组织信息表';

-- ----------------------------
-- Table structure for local_power_join_task
-- ----------------------------
DROP TABLE IF EXISTS `local_power_join_task`;
CREATE TABLE `local_power_join_task` (
                                         `node_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '计算节点ID',
                                         `task_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务id',
                                         `task_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务名称',
                                         `owner_identity_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发起方ID',
                                         `owner_identity_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发起方名称',
                                         `task_start_time` datetime DEFAULT NULL COMMENT '发起时间',
                                         `result_side_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果方ID',
                                         `result_side_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果方名称',
                                         `coordinate_side_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '协作方ID',
                                         `coordinate_side_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '协作方名称',
                                         `used_memory` bigint(20) DEFAULT '0' COMMENT '使用的内存, 字节（占此节点总算力比）',
                                         `used_core` int(11) DEFAULT '0' COMMENT '使用的core（占此节点总算力比）',
                                         `used_bandwidth` bigint(20) DEFAULT '0' COMMENT '使用的带宽, bps（占此节点总算力比）',
                                         `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                                         PRIMARY KEY (`node_id`,`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='计算节点参与任务信息';

-- ----------------------------
-- Table structure for local_power_load_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `local_power_load_snapshot`;
CREATE TABLE `local_power_load_snapshot` (
                                             `node_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '算力节点id',
                                             `snapshot_time` datetime NOT NULL COMMENT '快照时间点，精确到小时',
                                             `used_core` int(11) DEFAULT NULL COMMENT '核心使用数',
                                             `used_memory` bigint(20) DEFAULT NULL COMMENT '内存使用数',
                                             `used_bandwidth` bigint(20) DEFAULT NULL COMMENT '带宽使用数',
                                             PRIMARY KEY (`node_id`,`snapshot_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本地算力负载快照统计';

-- ----------------------------
-- Table structure for local_power_node
-- ----------------------------
DROP TABLE IF EXISTS `local_power_node`;
CREATE TABLE `local_power_node` (
                                    `node_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发布后底层返回的host唯一ID',
                                    `node_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点名称(同一个组织不可重复）',
                                    `internal_ip` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点内网IP',
                                    `internal_port` int(11) DEFAULT NULL COMMENT '节点内网端口',
                                    `external_ip` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点外网IP',
                                    `external_port` int(11) DEFAULT NULL COMMENT '节点外网端口',
                                    `start_time` datetime DEFAULT NULL COMMENT '节点启用时间',
                                    `remarks` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点备注',
                                    `conn_time` datetime DEFAULT NULL COMMENT '节点上一次连接时间',
                                    `conn_status` int(11) DEFAULT '0' COMMENT '节点与调度服务的连接状态，0: 未被调度服务连接上; 1: 连接上; ',
                                    `conn_message` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点(连接失败)信息',
                                    `power_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '节点启动后底层返回的算力ID',
                                    `power_status` tinyint(4) DEFAULT '1' COMMENT '算力状态 (0: 未知; 1: 还未发布的算力; 2: 已发布的算力(算力未被占用); 3: 已发布的算力(算力正在被占用); 4: 已撤销的算力;5: 发布中; 6：撤回中)',
                                    `memory` bigint(20) NOT NULL DEFAULT '0' COMMENT '计算host内存, 字节',
                                    `core` int(11) NOT NULL DEFAULT '0' COMMENT '计算host core',
                                    `bandwidth` bigint(20) NOT NULL DEFAULT '0' COMMENT '计算host带宽, bps',
                                    `used_memory` bigint(20) DEFAULT '0' COMMENT '使用的内存, 字节',
                                    `used_core` int(11) DEFAULT '0' COMMENT '使用的core',
                                    `used_bandwidth` bigint(20) DEFAULT '0' COMMENT '使用的带宽, bps',
                                    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                                    PRIMARY KEY (`node_id`),
                                    KEY `node_name` (`node_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织计算节点配置表 配置当前参与方的计算节点信息';

-- ----------------------------
-- Table structure for local_seed_node
-- ----------------------------
DROP TABLE IF EXISTS `local_seed_node`;
CREATE TABLE `local_seed_node` (
                                   `seed_node_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '节点ID',
                                   `conn_status` int(11) DEFAULT '0' COMMENT '节点与调度服务的连接状态，0: 未被调度服务连接上; 1: 连接上;',
                                   `init_flag` int(2) DEFAULT NULL COMMENT '是否是初始节点(0:否, 1:是)',
                                   PRIMARY KEY (`seed_node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='种子节点配置表';

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
                            `type` tinyint(4) NOT NULL COMMENT '资源类型：1-接口url，2-导航栏菜单，3-页面按钮',
                            `name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '资源名称',
                            `value` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '资源内容：根据type改变，如果是接口url，则value为url，如果type是其他的，则是前端定义值',
                            `url_resource_id` int(11) DEFAULT NULL COMMENT '按钮或者菜单对应的url的资源id',
                            `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父资源ID,如果没有父资源ID，则设置0',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资源表';
-- resource表初始化数据
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1', '2', '系统概况', 'overview', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('2', '2', '个人主页', 'userCenter/Profile', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('3', '2', '个人信息', 'userCenter/userInfo', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('4', '2', '修改管理员', 'userCenter/updateAdmin', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5', '2', '节点管理', 'nodeMgt', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('6', '2', '引导节点管理', 'nodeMgt/SeedNodeMgt', NULL, '5');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('7', '2', '存储资源管理', 'nodeMgt/dataNodeMgt', NULL, '5');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('8', '2', '计算资源管理', 'nodeMgt/computeNodeMgt', NULL, '5');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('9', '2', '我的数据', 'myData', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('10', '2', '数据管理', 'myData/dataMgt', NULL, '9');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('11', '2', '数据添加', 'myData/dataAddition', NULL, '9');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('12', '2', '计算任务', 'tasks', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('13', '2', '我的凭证', 'voucher', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('14', '2', '无属性凭证', 'voucher/NoAttribute', NULL, '13');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('15', '1', '我的数据-数据列表关键字查询', '/api/v1/data/listLocalMetaDataByKeyword', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('16', '1', '我的数据-数据列表关键字查询', '/api/v1/data/listLocalMetaDataByKeyword', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('17', '1', '我的数据-未绑定凭证Id数据列表关键字查询', '/api/v1/data/listUnBindLocalMetaDataByKeyword', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('18', '1', '我的数据-数据参与的任务信息列表', '/api/v1/data/listTaskByMetaDataId', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('19', '1', '我的数据-导入文件', '/api/v1/data/uploadFile', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('20', '1', '我的数据-添加数据/另存为新数据', '/api/v1/data/addLocalMetaData', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('21', '1', '我的数据-查看元数据详情', '/api/v1/data/localMetaDataInfo', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('22', '1', '我的数据-修改元数据信息', '/api/v1/data/updateLocalMetaData', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('23', '1', '我的数据-元数据操作：上架、下架和删除', '/api/v1/data/localMetaDataOp', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('24', '1', '我的数据-源文件下载', '/api/v1/data/download', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('25', '1', '我的数据-校验元数据名称是否合法', '/api/v1/data/checkResourceName', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('26', '1', '调度服务配置-连接调度节点', '/api/v1/carrier/connectNode', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('27', '1', '调度服务配置-申请准入网络', '/api/v1/carrier/applyJoinNetwork', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('28', '1', '调度服务配置-注销网络', '/api/v1/carrier/cancelJoinNetwork', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('29', '1', '数据节点管理-数据节点分页查询', '/api/v1/datanode/listNode', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('30', '1', '数据节点管理-修改数据节点名称', '/api/v1/datanode/updateNodeName', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('31', '1', '计算节点控制类-修改计算节点名称', '/api/v1/powernode/updateNodeName', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('32', '1', '计算节点控制类-查询计算节点详情', '/api/v1/powernode/powerNodeDetails', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('33', '1', '计算节点控制类-查询计算节点列表', '/api/v1/powernode/listPowerNode', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('34', '1', '计算节点控制类-启用算力', '/api/v1/powernode/publishPower', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('35', '1', '计算节点控制类-停用算力', '/api/v1/powernode/revokePower', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('36', '1', '计算节点控制类-查询计算节点参与的正在计算中的任务列表', '/api/v1/powernode/listRunningTaskByPowerNodeId', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('37', '1', '计算节点控制类-查询算力节点的最近24小时的负载情况', '/api/v1/powernode/listLocalPowerLoadSnapshotByPowerNodeId', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('38', '1', '计算节点控制类-查询算力节点当前的负载情况', '/api/v1/powernode/getCurrentLocalPowerLoadByPowerNodeId', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('39', '1', '种子节点控制类-新增种子节点', '/api/v1/seednode/addSeedNode', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('40', '1', '种子节点控制类-删除种子节点', '/api/v1/seednode/deleteSeedNode', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('41', '1', '种子节点控制类-查询种子节点服务列表', '/api/v1/seednode/listSeedNode', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('42', '1', '种子节点控制类-查询种子节点详情', '/api/v1/seednode/seedNodeDetails', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('43', '1', '种子节点控制类-校验计算节点名称是否可用', '/api/v1/seednode/checkSeedNodeId', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('44', '1', '系统概况-查询本地计算资源占用情况', '/api/v1/overview/localPowerUsage', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('45', '1', '系统概况-查询我发布的数据', '/api/v1/overview/localDataFileStatsTrendMonthly', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('46', '1', '系统概况-查询我发布的算力', '/api/v1/overview/localPowerStatsTrendMonthly', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('47', '1', '系统概况-查询我的计算任务概况', '/api/v1/overview/myTaskOverview', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('48', '1', '系统概况-查询数据待授权列表', '/api/v1/overview/listDataAuthReqWaitingForApprove', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('49', '1', '计算任务相关接口-我参与的任务情况统计', '/api/v1/task/myTaskStatistics', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('50', '1', '计算任务相关接口-条件查询组织参与的任务列表', '/api/v1/task/listMyTask', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('51', '1', '计算任务相关接口-查询组织参与的单个任务详情', '/api/v1/task/taskInfo', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('52', '1', '计算任务相关接口-单个任务事件日志列表', '/api/v1/task/listTaskEvent', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('53', '1', '无属性数据凭证-查询列表', '/api/v1/dataToken/page', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('54', '1', '无属性数据凭证-查询dex链接地址', '/api/v1/dataToken/getDexWebUrl', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('55', '1', '无属性数据凭证-获取发布凭证需要的配置', '/api/v1/dataToken/getPublishConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('56', '1', '无属性数据凭证-发布凭证', '/api/v1/dataToken/publish', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('57', '1', '无属性数据凭证-获取上架市场需要的配置', '/api/v1/dataToken/getUpConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('58', '1', '无属性数据凭证-上架市场', '/api/v1/dataToken/up', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('59', '1', '用户相关接口-获取登录Nonce', '/api/v1/user/getLoginNonce', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('60', '1', '用户相关接口-用户登录', '/api/v1/user/login', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('61', '1', '用户相关接口-退出登录状态', '/api/v1/user/logout', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('62', '1', '用户相关接口-替换管理员', '/api/v1/user/updateAdmin', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('63', '1', '用户相关接口-申请身份标识', '/api/v1/user/applyOrgIdentity', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('64', '1', '用户相关接口-更新组织信息', '/api/v1/user/updateLocalOrg', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('65', '1', '用户相关接口-查询出当前组织信息', '/api/v1/user/findLocalOrgInfo', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('66', '1', '系统相关-metaMask所需配置', '/api/v1/system/getMetaMaskConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('67', '1', '系统相关-查询系统配置的key', '/api/v1/system/getSystemConfigKey', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('68', '1', '系统相关-查询配置列表', '/api/v1/system/getAllConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('69', '1', '系统相关-根据key查询配置', '/api/v1/system/getConfigByKey', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('70', '1', '系统相关-新增自定义配置', '/api/v1/system/addCustomConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('71', '1', '系统相关-删除系统配置', '/api/v1/system/deleteCustomConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('72', '1', '系统相关-更新系统配置', '/api/v1/system/updateValueByKey', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('73', '1', '无属性数据凭证-根据id获取dataToken状态', '/api/v1/dataToken/getDataTokenStatus', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('74', '1', '我的数据-未绑定凭证Id数据列表关键字查询', '/api/v1/data/listUnBindLocalMetaDataByKeyword', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('75', '2', '凭证发布', 'myData/dataVoucherPublishing', NULL, '9');

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
                                 `role_id` int(11) NOT NULL COMMENT '角色id，默认只有两个角色，0是普通角色，1是管理员角色',
                                 `resource_id` int(11) NOT NULL COMMENT '资源id',
                                 UNIQUE KEY `role_resource_uk` (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色资源关联表';
-- role_resource表初始化数据
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '9');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '10');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '11');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '13');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '14');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '15');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '16');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '17');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '18');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '19');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '20');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '21');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '22');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '23');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '24');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '25');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '44');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '45');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '46');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '47');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '48');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '49');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '50');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '51');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '52');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '53');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '54');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '55');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '56');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '57');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '58');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '59');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '60');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '61');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '65');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '66');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '67');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '68');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '69');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '73');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '74');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '75');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '1');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '2');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '3');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '4');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '5');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '6');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '7');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '8');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '9');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '10');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '11');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '12');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '13');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '14');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '15');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '16');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '17');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '18');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '19');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '20');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '21');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '22');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '23');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '24');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '25');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '26');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '27');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '28');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '29');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '30');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '31');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '32');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '33');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '34');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '35');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '36');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '37');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '38');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '39');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '40');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '41');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '42');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '43');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '44');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '45');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '46');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '47');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '48');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '49');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '50');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '51');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '52');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '53');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '54');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '55');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '56');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '57');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '58');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '59');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '60');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '61');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '62');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '63');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '64');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '65');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '66');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '67');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '68');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '69');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '70');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '71');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '72');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '73');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '74');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '75');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
                              `key` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置项',
                              `value` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置值',
                              `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '配置状态 0-失效，1-有效',
                              `desc` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置描述',
                              `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                              `rec_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `idx_config_key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表，使用k-v形式';
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('1', 'data_token_factory_address', '0x0d932d4f60fa52fbb7192e32ffaf7ff558983839', '1', '凭证工厂合约地址', '2022-04-14 04:32:28', '2022-04-06 02:05:53');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('2', 'dex_router_address', '0x4378dAF745E9053f6048c4f78c803f3bC8829703', '1', 'router合约地址', '2022-04-06 07:00:01', '2022-04-06 02:14:42');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('3', 'dex_web_url', 'http://10.10.8.181:8080/swap', '1', 'dex链接地址', '2022-04-06 02:43:03', '2022-04-06 02:18:56');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('4', 'chain_name', 'PlatON开发网', '1', '网络名称', '2022-04-07 12:03:53', '2022-04-07 12:01:35');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('5', 'chain_id', '2203181', '1', '链 ID', '2022-04-14 04:32:49', '2022-04-07 12:01:38');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('6', 'rpc_url', 'https://devnetopenapi.platon.network/rpc', '1', '链rpcUrl，给用户使用，必须是外部ip', '2022-04-08 03:01:41', '2022-04-07 12:01:43');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('7', 'symbol', 'LAT', '1', '货币符号', '2022-04-07 12:04:27', '2022-04-07 12:01:47');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('8', 'block_explorer_url', 'https://devnetscan.platon.network/', '1', '区块浏览器', '2022-04-07 12:05:31', '2022-04-07 12:01:55');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('9', 'hrp', 'lat', '1', 'hrp', '2022-04-07 12:04:35', '2022-04-07 12:01:58');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('10', 'rpc_url_list', 'https://devnetopenapi.platon.network/rpc', '1', '链rpcUrl，主要是给后台系统用，可以是内部IP', '2022-04-08 03:02:48', '2022-04-08 03:01:36');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID(自增长)',
                            `user_name` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名',
                            `address` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户钱包地址',
                            `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态: 0-无效，1- 有效',
                            `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_admin` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否是管理员，0-否，1-是',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `UK_ADDRESS` (`address`) USING BTREE COMMENT '用户地址唯一'
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
                        `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
                        `task_Id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID',
                        `task_Name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务名称',
                        `owner_Identity_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务发起方组织ID',
                        `owner_party_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务发起方组织ID',
                        `apply_user` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发起任务的用户ID',
                        `user_type` int(4) DEFAULT '0' COMMENT '发起任务用户类型 (0: 未定义; 1: 以太坊地址; 2: Alaya地址; 3: PlatON地址)',
                        `create_At` datetime(3) DEFAULT NULL COMMENT '任务发起时间',
                        `start_At` datetime(3) DEFAULT NULL COMMENT '任务启动时间',
                        `auth_At` datetime(3) DEFAULT NULL COMMENT '任务授权时间',
                        `auth_Status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务授权状态: pending:等待授权、denied:授权未通过',
                        `end_At` datetime(3) DEFAULT NULL COMMENT '任务结束时间',
                        `status` tinyint(4) DEFAULT '0' COMMENT '任务状态(0:unknown未知、1:pending等在中、2:running计算中、3:failed失败、4:success成功)',
                        `duration` bigint(20) DEFAULT NULL COMMENT '任务声明计算时间',
                        `cost_core` int(11) DEFAULT '0' COMMENT '任务声明所需CPU',
                        `cost_Memory` bigint(20) DEFAULT '0' COMMENT '任务声明所需内存',
                        `cost_Bandwidth` bigint(20) DEFAULT '0' COMMENT '任务声明所需带宽',
                        `reviewed` tinyint(1) DEFAULT '0' COMMENT '任务是否被查看过，默认为false(0)',
                        `update_at` datetime(3) NOT NULL COMMENT '最后更新时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `taskID` (`task_Id`) USING BTREE COMMENT 'task_id唯一'
) ENGINE=InnoDB AUTO_INCREMENT=294 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='全网任务表 用于同步本地任务数据以及全网的相关数据';

-- ----------------------------
-- Table structure for task_algo_provider
-- ----------------------------
DROP TABLE IF EXISTS `task_algo_provider`;
CREATE TABLE `task_algo_provider` (
                                      `task_id` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID,hash',
                                      `identity_id` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '算法提供者组织身份ID',
                                      `party_id` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务参与方在本次任务中的唯一识别ID',
                                      PRIMARY KEY (`task_id`,`identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务算法提供者';

-- ----------------------------
-- Table structure for task_data_provider
-- ----------------------------
DROP TABLE IF EXISTS `task_data_provider`;
CREATE TABLE `task_data_provider` (
                                      `hash` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '唯一hash',
                                      `task_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID,hash',
                                      `meta_data_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参与任务的元数据ID',
                                      `meta_data_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '参与任务的元数据名称',
                                      `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据提供者组织身份ID',
                                      `party_id` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参与方在计算任务中的partyId',
                                      PRIMARY KEY (`hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务数据提供方表 存储某个任务数据提供方的信息';

-- ----------------------------
-- Table structure for task_event
-- ----------------------------
DROP TABLE IF EXISTS `task_event`;
CREATE TABLE `task_event` (
                              `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                              `task_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID,hash',
                              `event_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '事件类型',
                              `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产生事件的组织身份ID',
                              `party_id` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产生事件的partyId (单个组织可以担任任务的多个party, 区分是哪一方产生的event)',
                              `event_at` datetime(3) NOT NULL COMMENT '产生事件的时间',
                              `event_content` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '事件内容',
                              PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1364 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务事件表';

-- ----------------------------
-- Table structure for task_org
-- ----------------------------
DROP TABLE IF EXISTS `task_org`;
CREATE TABLE `task_org` (
                            `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机构身份标识ID(主键)',
                            `org_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构名称',
                            `carrier_node_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织中调度服务的 nodeId',
                            PRIMARY KEY (`identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务组织信息表，用于存储从调度服务获取的任务数据快照中组织信息数据';

-- ----------------------------
-- Table structure for task_power_provider
-- ----------------------------
DROP TABLE IF EXISTS `task_power_provider`;
CREATE TABLE `task_power_provider` (
                                       `task_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID,hash',
                                       `identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '算力提供者组织身份ID',
                                       `party_id` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参与方在计算任务中的partyId',
                                       `total_core` int(11) DEFAULT '0' COMMENT '任务总CPU信息',
                                       `used_core` int(11) DEFAULT '0' COMMENT '任务占用CPU信息',
                                       `total_memory` bigint(20) DEFAULT '0' COMMENT '任务总内存信息',
                                       `used_memory` bigint(20) DEFAULT '0' COMMENT '任务占用内存信息',
                                       `total_Bandwidth` bigint(20) DEFAULT '0' COMMENT '任务总带宽信息',
                                       `used_Bandwidth` bigint(20) DEFAULT '0' COMMENT '任务占用带宽信息',
                                       PRIMARY KEY (`task_id`,`identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务算力提供方表 任务数据提供方基础信息';

-- ----------------------------
-- Table structure for task_result_consumer
-- ----------------------------
DROP TABLE IF EXISTS `task_result_consumer`;
CREATE TABLE `task_result_consumer` (
                                        `task_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID,hash',
                                        `consumer_identity_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '结果消费者组织身份ID',
                                        `consumer_party_id` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参与方在计算任务中的partyId',
                                        `producer_identity_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果产生者的组织身份ID',
                                        `producer_party_id` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '参与方在计算任务中的partyId',
                                        PRIMARY KEY (`task_id`,`consumer_identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务结果接收方表 任务结果接收方信息';

-- 创建本地元数据月统计视图
CREATE OR REPLACE VIEW v_local_data_file_stats_monthly as
SELECT a.stats_time, a.month_size, SUM(a.month_size) AS accu_size
FROM (
         SELECT DATE_FORMAT(lmd.publish_time, '%Y-%m')  as stats_time, sum(ldf.size) as month_size
         FROM local_data_file ldf, local_meta_data lmd
         WHERE ldf.file_id = lmd.file_id AND lmd.status in (2,7,8,9,10)
         GROUP BY DATE_FORMAT(lmd.publish_time, '%Y-%m')
         ORDER BY DATE_FORMAT(lmd.publish_time, '%Y-%m')
     ) a
GROUP BY a.stats_time
ORDER BY a.stats_time;


-- 创建本地算力月统计视图
CREATE OR REPLACE VIEW v_local_power_stats_monthly as
SELECT a.stats_time, a.month_core, a.month_memory, a.month_bandwidth, SUM(a.month_core) AS accu_core, SUM(a.month_memory) AS accu_memory, SUM(a.month_bandwidth) AS accu_bandwidth
FROM (
         SELECT DATE_FORMAT(lpn.start_time, '%Y-%m')  as stats_time, sum(lpn.core) as month_core, sum(lpn.memory) as month_memory, sum(lpn.bandwidth) as month_bandwidth
         FROM local_power_node lpn
         WHERE lpn.power_status=2 or lpn.power_status=3
         GROUP BY DATE_FORMAT(lpn.start_time, '%Y-%m')
         ORDER BY DATE_FORMAT(lpn.start_time, '%Y-%m')
     ) a
GROUP BY a.stats_time
ORDER BY a.stats_time;
-- ----------------------------
-- Event structure for local_power_load_snapshot_event
-- ----------------------------
DROP EVENT IF EXISTS `local_power_load_snapshot_event`;
DELIMITER ;;
CREATE EVENT `local_power_load_snapshot_event` ON SCHEDULE EVERY 1 HOUR STARTS '2021-01-20 00:00:00' ON COMPLETION PRESERVE ENABLE DO BEGIN
    INSERT INTO local_power_load_snapshot (node_id, snapshot_time, used_core, used_memory, used_bandwidth)
    SELECT node_id, DATE_FORMAT(CURRENT_TIMESTAMP(), '%Y-%m-%d %H:00:00'), sum(used_core) as used_core, sum(used_memory) as used_memory, sum(used_bandwidth) as used_bandwidth
    FROM  local_power_join_task
    GROUP BY node_id;
END
;;
DELIMITER ;