-- drop database if exists `metis_admin`;

-- CREATE DATABASE `metis_admin` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use `metis_admin_lsy`;

-- 以下为0.4.0数据库变更

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


DROP TABLE IF EXISTS `user_resource`;
CREATE TABLE `user_resource` (
                                 `role_id` int NOT NULL COMMENT '角色id',
                                 `resource_id` int NOT NULL COMMENT '资源id',
                                 UNIQUE KEY `角色资源唯一` (`role_id`,`resource_id`)
) COMMENT='角色资源关联表';