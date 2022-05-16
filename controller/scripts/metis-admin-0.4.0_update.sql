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

-- 新增角色资源关联表
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
                                 `role_id` int NOT NULL COMMENT '角色id，默认只有两个角色，0是普通角色，1是管理员角色',
                                 `resource_id` int NOT NULL COMMENT '资源id',
                                 UNIQUE KEY `role_resource_uk` (`role_id`,`resource_id`)
) COMMENT='角色资源关联表';

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
                              `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态：0-未发布，1-发布中，2-发布失败，3-发布成功，4-定价中，5-定价失败，6-定价成功',
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

-- 创建本地算力月统计视图
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

DROP TABLE IF EXISTS `bootstrap_node`;
DROP TABLE IF EXISTS `global_data_file`;
DROP TABLE IF EXISTS `global_meta_data_column`;
DROP TABLE IF EXISTS `global_power`;
DROP TABLE IF EXISTS `local_data_file_column`;

Alter TABLE local_meta_data modify `meta_data_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '元数据名称';