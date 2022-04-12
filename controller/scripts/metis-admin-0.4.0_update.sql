-- drop database if exists `metis_admin`;

-- CREATE DATABASE `metis_admin` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use `metis_admin`;

-- 以下为0.4.0数据库变更

-- 重新定义用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID(自增长)',
                            `user_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
                            `address` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户钱包地址',
                            `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态: 0-无效，1- 有效',
                            `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_admin` tinyint NOT NULL DEFAULT '0' COMMENT '是否是管理员，0-否，1-是',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `UK_ADDRESS` (`address`) USING BTREE COMMENT '用户地址唯一'
) COMMENT='用户表';

-- 新增资源表
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
                            `id` int NOT NULL AUTO_INCREMENT COMMENT '资源ID',
                            `type` tinyint NOT NULL COMMENT '资源类型：1-接口url，2-导航栏菜单，3-页面按钮',
                            `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源名称',
                            `value` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源内容：根据type改变，如果是接口url，则value为url，如果type是其他的，则是前端定义值',
                            `url_resource_id` int DEFAULT NULL COMMENT '按钮或者菜单对应的url的资源id',
                            `parent_id` int NOT NULL DEFAULT '0' COMMENT '父资源ID,如果没有父资源ID，则设置0',
                            PRIMARY KEY (`id`)
) COMMENT='资源表';

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1', '1', '登录接口', 'login', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('2', '1', '登出接口', 'logout', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('3', '2', '系统概况', 'overview', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('4', '2', '个人主页', 'userCenter/Profile', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5', '2', '个人信息', 'userCenter/userInfo', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('6', '2', '修改管理员', 'userCenter/updateAdmin', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('7', '2', '节点管理', 'nodeMgt', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('8', '2', '引导节点管理', 'nodeMgt/SeedNodeMgt', NULL, '7');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('9', '2', '存储资源管理', 'nodeMgt/dataNodeMgt', NULL, '7');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('10', '2', '计算资源管理', 'nodeMgt/computeNodeMgt', NULL, '7');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('11', '2', '我的数据', 'myData', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('12', '2', '数据管理', 'myData/dataMgt', NULL, '11');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('13', '2', '数据添加', 'myData/dataAddition', NULL, '11');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('15', '2', '计算任务', 'tasks', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('16', '2', '我的凭证', 'voucher', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('17', '2', '无属性凭证', 'voucher/NoAttribute', NULL, '16');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('18', '1', '我的数据-数据列表关键字查询', '/api/v1/data/listLocalMetaDataByKeyword', NULL, '0');


-- 新增角色资源关联表
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
                                 `role_id` int NOT NULL COMMENT '角色id，默认只有两个角色，0是普通角色，1是管理员角色',
                                 `resource_id` int NOT NULL COMMENT '资源id',
                                 UNIQUE KEY `角色资源唯一` (`role_id`,`resource_id`)
) COMMENT='角色资源关联表';

INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '2');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '3');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '11');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '12');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '13');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '16');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '17');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '18');
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
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '15');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '16');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '17');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('1', '18');


-- 新增数据凭证表
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
                              `publish_nonce` int(11) DEFAULT 0 COMMENT '发布凭证交易nonce',
                              `publish_hash` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发布上链的交易hash，供查询发布状态',
                              `up_nonce` int(11) DEFAULT 0 COMMENT '上架交易nonce',
                              `up_hash` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上架dex的交易hash',
                              `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态：0-发布中，1-发布失败，2-发布成功，3-定价中，4-定价失败，5-定价成功',
                              `rec_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              `rec_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `ukey_meta_data_id` (`meta_data_id`) USING HASH COMMENT 'meta_data_id唯一'
) COMMENT='数据凭证表';

-- 组织表新增carrier钱包字段
ALTER table local_org add COLUMN `carrier_wallet` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调度服务钱包地址';

-- 元数据表新增数据凭证id字段
ALTER table local_meta_data ADD COLUMN `data_token_id` int(11) DEFAULT NULL COMMENT '数据凭证id';

-- 元数据表新增拥有者钱包地址字段
ALTER table local_meta_data ADD COLUMN `owner` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '拥有者钱包地址';

-- 元数据表状态字段新增状态码
ALTER table local_meta_data MODIFY COLUMN `status` int(11) NOT NULL DEFAULT '0' COMMENT '元数据的状态 (0: 未知; 1: 还未发布的新表; 2: 已发布的表; 3: 已撤销的表; 4：已删除;5: 发布中; 6：撤回中;7:凭证发布失败;8: 凭证发布中; 9：已发布凭证;10:已绑定凭证)';

-- 修改系统配置表结构
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
) COMMENT='系统配置表，使用k-v形式';


ALTER TABLE local_data_file ADD COLUMN `data_hash` VARCHAR (255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原始数据内容hash';

ALTER TABLE local_data_file ADD COLUMN `location_type` TINYINT (2) DEFAULT '1' COMMENT '源数据的存储位置类型 (组织本地服务器、远端服务器、云等)：0-未知，1-本地，2-远端服务器';

ALTER TABLE local_meta_data ADD COLUMN `meta_data_type` TINYINT (2) DEFAULT '1' COMMENT '表示该元数据是 普通数据 还是 模型数据的元数据 (0: 未定义; 1: 普通数据元数据; 2: 模型数据元数据)';

INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('1', 'data_token_factory_address', '0x', '1', '凭证工厂合约地址', '2022-04-06 02:42:51', '2022-04-06 02:05:53');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('2', 'dex_router_address', '0x26D637E206Cc39942628421e7B0D6Fb41dB0bC06', '1', 'router合约地址', '2022-04-06 07:00:01', '2022-04-06 02:14:42');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('3', 'dex_web_url', 'http://10.10.8.181:8080/swap', '1', 'dex链接地址', '2022-04-06 02:43:03', '2022-04-06 02:18:56');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('4', 'chain_name', 'PlatON开发网', '1', '网络名称', '2022-04-07 12:03:53', '2022-04-07 12:01:35');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('5', 'chain_id', '2203181', '1', '链 ID', '2022-04-07 12:05:22', '2022-04-07 12:01:38');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('6', 'rpc_url', 'https://devnetopenapi.platon.network/rpc', '1', '链rpcUrl，给用户使用，必须是外部ip', '2022-04-08 03:01:41', '2022-04-07 12:01:43');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('7', 'symbol', 'LAT', '1', '货币符号', '2022-04-07 12:04:27', '2022-04-07 12:01:47');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('8', 'block_explorer_url', 'https://devnetscan.platon.network/', '1', '区块浏览器', '2022-04-07 12:05:31', '2022-04-07 12:01:55');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('9', 'hrp', 'lat', '1', 'hrp', '2022-04-07 12:04:35', '2022-04-07 12:01:58');
INSERT INTO `sys_config` (`id`, `key`, `value`, `status`, `desc`, `rec_update_time`, `rec_create_time`) VALUES ('10', 'rpc_url_list', 'https://devnetopenapi.platon.network/rpc', '1', '链rpcUrl，主要是给后台系统用，可以是内部IP', '2022-04-08 03:02:48', '2022-04-08 03:01:36');

