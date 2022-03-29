-- drop database if exists `metis_admin`;

-- CREATE DATABASE `metis_admin` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use `metis_admin_lsy`;

-- 以下为0.4.0数据库变更

-- 重新定义用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID(自增长)',
                            `user_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
                            `address` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户钱包地址',
                            `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态: 0-无效，1- 有效',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
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

-- 新增角色资源关联表
DROP TABLE IF EXISTS `user_resource`;
CREATE TABLE `user_resource` (
                                 `role_id` int NOT NULL COMMENT '角色id',
                                 `resource_id` int NOT NULL COMMENT '资源id',
                                 UNIQUE KEY `角色资源唯一` (`role_id`,`resource_id`)
) COMMENT='角色资源关联表';

-- 新增数据凭证表
DROP TABLE IF EXISTS `data_token`;
CREATE TABLE `data_token` (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'dataTokenId',
                              `address` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '合约地址',
                              `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约名称',
                              `symbol` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约符号',
                              `total` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '总发行量',
                              `decimal` int(11) NOT NULL DEFAULT '18' COMMENT '合约精度',
                              `desc` text COLLATE utf8mb4_unicode_ci COMMENT '价值证明描述',
                              `owner` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '拥有者钱包地址',
                              `agent` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '代理钱包地址',
                              `meta_data_id` int(11) NOT NULL COMMENT '对应的metaDataId',
                              `holder` int(11) DEFAULT '0' COMMENT '持有人数量',
                              `pricing_status` tinyint(1) NOT NULL COMMENT '定价状态：0-未定价，1-定价中，2-定价失败，3-定价成功',
                              `publish_status` tinyint(1) NOT NULL COMMENT '发布状态：0-发布中，1-发布失败，2-发布成功',
                              `publish_hash` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发布上链的交易hash，供查询发布状态',
                              `up_hash` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上架dex的交易hash',
                              `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `ukey_meta_data_id` (`meta_data_id`) USING HASH COMMENT 'meta_data_id唯一'
) COMMENT='数据凭证表';

-- 组织表新增carrier钱包字段
ALTER table local_org add COLUMN `carrier_wallet` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调度服务钱包地址';

-- 元数据表新增数据凭证id字段
ALTER table local_meta_data ADD COLUMN `data_token_id` int(11) DEFAULT NULL COMMENT '数据凭证id';