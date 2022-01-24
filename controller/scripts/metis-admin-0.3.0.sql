drop database if exists `dev_metis_admin_0.3.0`;

CREATE DATABASE `dev_metis_admin_0.3.0` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use `dev_metis_admin_0.3.0`;
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
  `node_id` varchar(256) DEFAULT NULL COMMENT '节点ID，添加成功后由调度服务返回',
  `node_name` varchar(30) NOT NULL COMMENT '节点名称，种子节点名称不可重复，设置之后无法修改',
  `internal_ip` varchar(15) NOT NULL COMMENT '内部IP',
  `internal_port` varchar(5) NOT NULL COMMENT '内部端口',
  `is_init_node` tinyint(1) DEFAULT '0' COMMENT '是否是初始节点，0否，1是',
  `status` tinyint(1) DEFAULT '0' COMMENT '节点状态：0 网络连接失败，1 网络连接成功',
  `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) COMMENT='种子节点配置表';

-- ----------------------------
-- Table structure for global_data_file
-- ----------------------------
DROP TABLE IF EXISTS `global_data_file`;
CREATE TABLE `global_data_file` (
    `meta_data_id` varchar(256) NOT NULL COMMENT '元数据ID,hash',
    `identity_id` varchar(256) NOT NULL COMMENT '组织身份ID',
    `org_name` varchar(256) NOT NULL COMMENT '组织名称',
    `file_id` varchar(256) NOT NULL DEFAULT '' COMMENT '源文件ID',
    `file_name` varchar(100) NOT NULL COMMENT '源文件名称',
    `file_path` varchar(100) NOT NULL COMMENT '文件存储路径',
    `file_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '文件后缀/类型, 0：未知、1：csv(目前只支持这个)',
    `resource_name` varchar(100) NOT NULL COMMENT '数据名称',
    `size` BIGINT NOT NULL DEFAULT '0' COMMENT '文件大小(字节)',
    `rows` INT NOT NULL DEFAULT '0' COMMENT '数据行数(不算title)',
    `columns` INT NOT NULL DEFAULT '0' COMMENT '数据列数',
    `has_title` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否带标题,0表示不带，1表示带标题',
    `remarks` varchar(100) DEFAULT NULL COMMENT '数据描述',
    `status` INT NOT NULL DEFAULT 0 COMMENT '数据的状态 (0: 未知; 1: 还未发布的新表; 2: 已发布的表; 3: 已撤销的表)',
    `publish_time` datetime(3) DEFAULT NULL COMMENT '元数据发布时间',
    `update_time` datetime DEFAULT NULL COMMENT '元数据修改时间',
    PRIMARY KEY (`meta_data_id`)
) COMMENT='全网数据文件表';

-- ----------------------------
-- Table structure for global_meta_data_column
-- ----------------------------
DROP TABLE IF EXISTS `global_meta_data_column`;
CREATE TABLE `global_meta_data_column` (
  `meta_data_id` varchar(256) NOT NULL COMMENT '元数据ID,hash',
  `column_idx` INT NOT NULL COMMENT '列索引',
  `column_name` varchar(100) DEFAULT NULL COMMENT '列名',
  `column_type` varchar(32) DEFAULT NULL COMMENT '列类型',
  `size` INT DEFAULT 0 COMMENT '列大小（byte）',
  `remarks` varchar(32) DEFAULT NULL COMMENT '列描述',
  `visible` varchar(1) NOT NULL DEFAULT 'N' COMMENT '是否对外可见 Y:可见，N:不可见',
  PRIMARY KEY (`meta_data_id`, `column_idx`)
) COMMENT='全网数据文件表列详细表';

-- ----------------------------
-- Table structure for global_power
-- ----------------------------
DROP TABLE IF EXISTS global_power;
CREATE TABLE global_power (
  id VARCHAR(200) NOT NULL comment '计算服务主机ID,hash',
  identity_id VARCHAR(200) NOT NULL COMMENT '组织身份ID',
  org_name VARCHAR(100) NOT NULL COMMENT '组织名称',
  memory BIGINT  NOT NULL DEFAULT 0 COMMENT '计算服务内存, 字节',
  core INT NOT NULL DEFAULT 0 COMMENT '计算服务core',
  bandwidth BIGINT  NOT NULL DEFAULT 0 COMMENT '计算服务带宽, bps',
  used_memory BIGINT DEFAULT 0 COMMENT '使用的内存, 字节',
  used_core INT DEFAULT 0 COMMENT '使用的core',
  used_bandwidth BIGINT DEFAULT 0 COMMENT '使用的带宽, bps',
  published BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否发布，true/false',
  publish_at DATETIME(3) NOT NULL comment '发布时间',
  status int COMMENT '算力的状态 (0: 未知; 1: 还未发布的算力; 2: 已发布的算力(算力未被占用); 3: 已发布的算力(算力正在被占用); 4: 已撤销的算力)',
  update_at DATETIME NOT NULL comment '(状态)修改时间',
  PRIMARY KEY (id)
) comment '计算服务信息';

-- ----------------------------
-- Table structure for local_data_file
-- ----------------------------
DROP TABLE IF EXISTS `local_data_file`;
CREATE TABLE `local_data_file` (
   `file_id` varchar(256) NOT NULL DEFAULT '' COMMENT '全网唯一ID，上传文件成功后返回',
   `identity_id` varchar(256) NOT NULL COMMENT '组织身份ID',
   `file_name` varchar(100) NOT NULL COMMENT '源文件名称',
   `file_path` varchar(100) NOT NULL COMMENT '文件存储路径',
   `file_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '文件后缀/类型, 0：未知、1：csv(目前只支持这个)',
   `size` BIGINT NOT NULL DEFAULT 0 COMMENT '文件大小(字节)',
   `rows` INT NOT NULL DEFAULT 0 COMMENT '数据行数(不算title)',
   `columns` INT NOT NULL DEFAULT 0 COMMENT '数据列数',
   `has_title` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否带标题,0表示不带，1表示带标题',
   `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`file_id`)
) COMMENT='本组织数据文件表，数据信息表';


DROP TABLE IF EXISTS `local_data_file_column`;
CREATE TABLE `local_data_file_column` (
    `file_id` varchar(256) NOT NULL COMMENT '文件ID',
    `column_idx` INT NOT NULL COMMENT '列索引',
    `column_name` varchar(100) DEFAULT NULL COMMENT '列名',
    `column_type` varchar(32) DEFAULT NULL COMMENT '列类型',
    `size` INT DEFAULT 0 COMMENT '列大小（byte）',
    `remarks` varchar(32) DEFAULT NULL COMMENT '列描述',
    PRIMARY KEY (`file_id`, column_idx)
) COMMENT='本组织数据文件表列详细表';

-- ----------------------------
-- Table structure for local_meta_data
-- ----------------------------
DROP TABLE IF EXISTS `local_meta_data`;
CREATE TABLE `local_meta_data` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `file_id` varchar(256) NOT NULL COMMENT '文件ID',
  `meta_data_id` varchar(256) DEFAULT NULL COMMENT '元数据ID,hash',
  `meta_data_name` varchar(100) NOT NULL COMMENT '元数据名称',
  `status` INT NOT NULL DEFAULT 0 COMMENT '元数据的状态 (0: 未知; 1: 还未发布的新表; 2: 已发布的表; 3: 已撤销的表)',
  `publish_time` datetime DEFAULT NULL COMMENT '元数据发布时间',
  `remarks` varchar(100) DEFAULT '' COMMENT '元数据描述',
  `industry` int(4) DEFAULT NULL COMMENT '所属行业 1：金融业（银行）、2：金融业（保险）、3：金融业（证券）、4：金融业（其他）、5：ICT、 6：制造业、 7：能源业、 8：交通运输业、 9 ：医疗健康业、 10 ：公共服务业、 11：传媒广告业、 12 ：其他行业',
  `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `meta_data_id` (`meta_data_id`),
  KEY `file_id` (`file_id`)
) COMMENT='本组织元数据文件表，文件的元数据信息';


-- ----------------------------
-- Table structure for local_meta_data_column
-- ----------------------------
DROP TABLE IF EXISTS `local_meta_data_column`;
CREATE TABLE `local_meta_data_column` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
      `local_meta_data_db_id` INT NOT NULL COMMENT '元数据local_meta_data自增id',
  `column_idx` INT DEFAULT NULL COMMENT '列索引',
  `column_name` varchar(100) DEFAULT '' COMMENT '列名',
  `column_type` varchar(32) DEFAULT '' COMMENT '列类型',
  `size` int DEFAULT 0 COMMENT '列大小（byte）',
  `remarks` varchar(32) DEFAULT '' COMMENT '列描述',
  `visible` BOOLEAN DEFAULT FALSE COMMENT '是否可见',
  PRIMARY KEY (`id`),
  UNIQUE KEY (`local_meta_data_db_id`, `column_idx`)
) COMMENT='本组织数据文件表列详细表，描述源文件中每一列的列信息';

-- ----------------------------
-- Table structure for local_data_auth
-- ----------------------------
DROP TABLE IF EXISTS `local_data_auth`;
CREATE TABLE `local_data_auth` (
  `auth_id` varchar(256) NOT NULL COMMENT '元数据授权申请Id',
  `meta_data_id` varchar(256) NOT NULL COMMENT '元数据ID',
  `apply_user` varchar(256) DEFAULT NULL COMMENT '发起任务的用户的信息 (task是属于用户的)',
  `user_type` int(4) DEFAULT '0' COMMENT '发起任务用户类型 (0: 未定义; 1: 以太坊地址; 2: Alaya地址; 3: PlatON地址)',
  `auth_type` int(4) DEFAULT '0' COMMENT '授权方式(0：未定义，1：时间，2：次数)',
  `auth_value_amount` int(100) DEFAULT '0' COMMENT '授权值(以授权次数)，auth_type = 2使用此字段',
  `auth_value_start_at` datetime DEFAULT NULL COMMENT '授权值开始时间，auth_type = 1使用此字段',
  `auth_value_end_at` datetime DEFAULT NULL COMMENT '授权值结束时间，auth_type = 1使用此字段',
  `create_at` datetime(3) DEFAULT NULL COMMENT '授权申请发起时间',
  `auth_at` datetime(3) DEFAULT NULL COMMENT '授权数据时间',
  `status` int(4) DEFAULT '0' COMMENT '授权数据状态：0：等待授权审核，1:同意， 2:拒绝，3:失效(auth_type=1且auth_value_end_at超时) ',
  `identity_name` varchar(256) DEFAULT NULL COMMENT '元数据所属的组织信息，组织名称',
  `identity_id` varchar(256) DEFAULT NULL COMMENT '元数据所属的组织信息,组织的身份标识Id',
  `identity_node_id` varchar(256) DEFAULT NULL COMMENT '组织中调度服务的 nodeId',
  `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`auth_id`),
  KEY (`meta_data_id`)
) COMMENT='本组织申请授权数据表';


-- ----------------------------
-- Table structure for local_seed_node
-- ----------------------------
DROP TABLE IF EXISTS `local_seed_node`;
CREATE TABLE `local_seed_node` (
  `seed_node_id` varchar(256) NOT NULL COMMENT '节点ID',
  `conn_status` int DEFAULT 0 COMMENT '节点与调度服务的连接状态，0: 未被调度服务连接上; 1: 连接上;',
  `init_flag` int(2) DEFAULT NULL COMMENT '是否是初始节点(0:否, 1:是)',
  PRIMARY KEY (`seed_node_id`)
) COMMENT='种子节点配置表';

-- ----------------------------
-- Table structure for local_data_node
-- ----------------------------
DROP TABLE IF EXISTS `local_data_node`;
CREATE TABLE `local_data_node` (
  `node_id` varchar(256) NOT NULL COMMENT '发布后底层返回的host唯一ID',
  `node_name` varchar(64) DEFAULT NULL COMMENT '节点名称',
  `internal_IP` varchar(32) DEFAULT NULL COMMENT '节点内部IP',
  `internal_Port` INT DEFAULT NULL COMMENT '节点内部端口',
  `external_IP` varchar(32) DEFAULT NULL COMMENT '节点外部IP',
  `external_Port` INT DEFAULT NULL COMMENT '节点外部端口',
  `conn_Status` INT DEFAULT 0 COMMENT '节点与调度服务的连接状态，0: 未被调度服务连接上; 1: 连接上;',
  `conn_message` varchar(32) DEFAULT NULL COMMENT '节点(连接失败)信息',
  `conn_Time` datetime DEFAULT NULL COMMENT '节点上一次连接时间',
  `status` varchar(10) DEFAULT 'disabled' COMMENT '节点状态 enabled：可用, disabled:不可用',
  `remarks` varchar(32) DEFAULT NULL COMMENT '节点备注',
  `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`node_id`)
) COMMENT='本组织数据节点配置表 配置数据节点相关信息';

-- ----------------------------
-- Table structure for local_org
-- ----------------------------
DROP TABLE IF EXISTS `local_org`;
CREATE TABLE `local_org` (
     `name` varchar(32) NOT NULL COMMENT '机构名称',
     `identity_id` varchar(256) DEFAULT NULL COMMENT '机构身份标识ID',
     `carrier_node_id` varchar(256) DEFAULT NULL COMMENT '机构调度服务node id，入网后可以获取到',
     `carrier_ip` varchar(32) DEFAULT NULL COMMENT '调度服务IP地址',
     `carrier_port` INT DEFAULT NULL COMMENT '调度服务端口号',
     `carrier_conn_status` varchar(10) DEFAULT NULL COMMENT '连接状态 enabled：可用, disabled:不可用',
     `carrier_status` INT DEFAULT 0 COMMENT '调度服务的状态（0:unknown未知、1: active活跃、2:leave: 离开网络、3:join加入网络 4:unuseful不可用）',
     `conn_node_count` INT  DEFAULT 0 COMMENT '节点连接的数量',
     `carrier_conn_time` datetime DEFAULT NULL COMMENT '服务连接时间',
     `status` tinyint(1) DEFAULT 0 COMMENT '0未入网，1已入网， 99已退网',
     local_bootstrap_node varchar(256) DEFAULT NULL COMMENT '当前系统的本地节点，可以作为引导节点提供给三方节点',
     local_multi_addr varchar(256) DEFAULT NULL COMMENT '当前系统本地的',
     image_url varchar(256) COMMENT '组织机构图像url',
     profile  varchar(256) COMMENT '组织机构简介',
     `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) COMMENT='本地组织信息表';


-- ----------------------------
-- Table structure for local_power_join_task
-- ----------------------------
DROP TABLE IF EXISTS `local_power_join_task`;
CREATE TABLE `local_power_join_task` (
  `node_id` varchar(256) NOT NULL COMMENT '计算节点ID',
  `task_id` varchar(256) NOT NULL COMMENT '任务id',
  `task_name` varchar(100) DEFAULT NULL COMMENT '任务名称',
  `owner_identity_id` varchar(256) DEFAULT NULL COMMENT '发起方ID',
  `owner_identity_name` varchar(128) DEFAULT NULL COMMENT '发起方名称',
  `task_start_time` datetime DEFAULT NULL COMMENT '发起时间',
  `result_side_id` varchar(256) DEFAULT NULL COMMENT '结果方ID',
  `result_side_name` varchar(32) DEFAULT NULL COMMENT '结果方名称',
  `coordinate_side_id` varchar(256) DEFAULT NULL COMMENT '协作方ID',
  `coordinate_side_name` varchar(32) DEFAULT NULL COMMENT '协作方名称',
  `used_memory` BIGINT DEFAULT '0' COMMENT '使用的内存, 字节（占此节点总算力比）',
  `used_core` INT DEFAULT '0' COMMENT '使用的core（占此节点总算力比）',
  `used_bandwidth` BIGINT DEFAULT '0' COMMENT '使用的带宽, bps（占此节点总算力比）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`node_id`, `task_id`)
) COMMENT='计算节点参与任务信息';

-- ----------------------------
-- Table structure for local_power_node
-- ----------------------------
DROP TABLE IF EXISTS `local_power_node`;
CREATE TABLE `local_power_node` (
    `node_id` varchar(256) NOT NULL COMMENT '发布后底层返回的host唯一ID',
    `node_name` varchar(32) DEFAULT NULL COMMENT '节点名称(同一个组织不可重复）',
    `internal_ip` varchar(32) DEFAULT NULL COMMENT '节点内网IP',
    `internal_port` INT DEFAULT NULL COMMENT '节点内网端口',
    `external_ip` varchar(32) DEFAULT NULL COMMENT '节点外网IP',
    `external_port` INT DEFAULT NULL COMMENT '节点外网端口',
    `start_time` datetime DEFAULT NULL COMMENT '节点启用时间',
    `remarks` varchar(32) DEFAULT NULL COMMENT '节点备注',
    `conn_time` datetime DEFAULT NULL COMMENT '节点上一次连接时间',
    `conn_status` int DEFAULT 0 COMMENT '节点与调度服务的连接状态，0: 未被调度服务连接上; 1: 连接上; ',
    `conn_message` varchar(32) DEFAULT NULL COMMENT '节点(连接失败)信息',
    `power_id` varchar(256) DEFAULT NULL COMMENT '节点启动后底层返回的算力ID',
    `power_status` tinyint(4) DEFAULT 0 COMMENT '算力状态 (0: 未知; 1: 还未发布的算力; 2: 已发布的算力(算力未被占用); 3: 已发布的算力(算力正在被占用); 4: 已撤销的算力)',
    `memory` BIGINT NOT NULL DEFAULT 0 COMMENT '计算host内存, 字节',
    `core` INT NOT NULL DEFAULT 0 COMMENT '计算host core',
    `bandwidth` BIGINT NOT NULL DEFAULT '0' COMMENT '计算host带宽, bps',
    `used_memory` BIGINT DEFAULT 0 COMMENT '使用的内存, 字节',
    `used_core` INT DEFAULT 0 COMMENT '使用的core',
    `used_bandwidth` BIGINT DEFAULT 0 COMMENT '使用的带宽, bps',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`node_id`),
    KEY `node_name` (`node_name`)
) COMMENT='本组织计算节点配置表 配置当前参与方的计算节点信息';



-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `config_Key` varchar(32) DEFAULT NULL COMMENT '配置项',
  `config_Value` varchar(32) DEFAULT NULL COMMENT '配置值',
  `status` varchar(10) NOT NULL DEFAULT 'N' COMMENT '配置状态 enabled：可用, disabled:不可用',
  `desc` varchar(32) DEFAULT NULL COMMENT '配置描述',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_config_key` (`config_Key`)
) COMMENT='系统配置表 管理系统相关配置项，使用k-v形式';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码 MD5加密',
  `status` varchar(10) DEFAULT NULL COMMENT '用户状态 enabled：可用, disabled:不可用',
  `is_Master` char(1) DEFAULT NULL COMMENT '是否为管理员 1管理员，0非管理',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_name` (`user_name`) USING HASH
) COMMENT='系统用户表 管理系统用户登陆信息';

INSERT INTO `sys_user` (`id`, `user_name`, `password`, `status`, `is_Master`, `rec_update_time`) VALUES ('1', 'admin', 'admin', 'enabled', '1', '2021-07-12 03:14:45');


-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
    `id` INT NOT NULL AUTO_INCREMENT COMMENT '序号',
    `task_Id` varchar(256) NOT NULL COMMENT '任务ID',
    `task_Name` varchar(100) DEFAULT NULL COMMENT '任务名称',
    `owner_Identity_id` varchar(256) DEFAULT NULL COMMENT '任务发起方组织ID',
    `owner_party_id` varchar(256) DEFAULT NULL COMMENT '任务发起方组织ID',
    `apply_user` varchar(256) DEFAULT NULL COMMENT '发起任务的用户ID',
    `user_type` int(4) DEFAULT 0 COMMENT '发起任务用户类型 (0: 未定义; 1: 以太坊地址; 2: Alaya地址; 3: PlatON地址)',
    `create_At` datetime(3) DEFAULT NULL COMMENT '任务发起时间',
    `start_At` datetime(3) DEFAULT NULL COMMENT '任务启动时间',
    `auth_At` datetime(3) DEFAULT NULL COMMENT '任务授权时间',
    `auth_Status` varchar(10) DEFAULT NULL COMMENT '任务授权状态: pending:等待授权、denied:授权未通过',
    `end_At` datetime(3) DEFAULT NULL COMMENT '任务结束时间',
    `status` tinyint(4) DEFAULT 0 COMMENT '任务状态(0:unknown未知、1:pending等在中、2:running计算中、3:failed失败、4:success成功)',
    `duration` BIGINT DEFAULT NULL COMMENT '任务声明计算时间',
    `cost_core` INT DEFAULT 0 COMMENT '任务声明所需CPU',
    `cost_Memory` BIGINT DEFAULT 0 COMMENT '任务声明所需内存',
    `cost_Bandwidth` BIGINT DEFAULT 0 COMMENT '任务声明所需带宽',
    `reviewed` tinyint(1) DEFAULT 0 COMMENT '任务是否被查看过，默认为false(0)',
    `update_at` datetime(3) NOT NULL COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `taskID` (`task_Id`) USING BTREE COMMENT 'task_id唯一'
) COMMENT='全网任务表 用于同步本地任务数据以及全网的相关数据';

DROP TABLE IF EXISTS task_algo_provider;
CREATE TABLE task_algo_provider (
    task_id VARCHAR(200) NOT NULL comment '任务ID,hash',
    identity_id VARCHAR(200) NOT NULL COMMENT '算法提供者组织身份ID',
    party_id VARCHAR(200) NOT NULL COMMENT '任务参与方在本次任务中的唯一识别ID',
    PRIMARY KEY (task_ID, identity_id)
) comment '任务算法提供者';

-- ----------------------------
-- Table structure for task_data_provider
-- ----------------------------
DROP TABLE IF EXISTS `task_data_provider`;
CREATE TABLE `task_data_provider` (
  `task_id` varchar(256) NOT NULL COMMENT '任务ID,hash',
  `meta_data_id` varchar(256) NOT NULL COMMENT '参与任务的元数据ID',
  `meta_data_name` varchar(100) COMMENT '参与任务的元数据名称',
  `identity_id` varchar(256) NOT NULL COMMENT '数据提供者组织身份ID',
  party_id VARCHAR(200) NOT NULL COMMENT '参与方在计算任务中的partyId',
  PRIMARY KEY (`task_id`,`meta_data_id`)
) COMMENT='任务数据提供方表 存储某个任务数据提供方的信息';

-- ----------------------------
-- Table structure for task_event
-- ----------------------------
DROP TABLE IF EXISTS `task_event`;
CREATE TABLE `task_event` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `task_id` varchar(256) NOT NULL COMMENT '任务ID,hash',
  `event_type` varchar(20) NOT NULL COMMENT '事件类型',
  `identity_id` varchar(256) NOT NULL COMMENT '产生事件的组织身份ID',
  party_id VARCHAR(200) NOT NULL COMMENT '产生事件的partyId (单个组织可以担任任务的多个party, 区分是哪一方产生的event)',
  `event_at` datetime(3) NOT NULL COMMENT '产生事件的时间',
  `event_content` varchar(1024) NOT NULL COMMENT '事件内容',
  PRIMARY KEY (`ID`)
) COMMENT='任务事件表';

-- ----------------------------
-- Table structure for task_org
-- ----------------------------
DROP TABLE IF EXISTS `task_org`;
CREATE TABLE `task_org` (
  `identity_id` varchar(256) NOT NULL COMMENT '机构身份标识ID(主键)',
  `org_name` varchar(32) DEFAULT NULL COMMENT '机构名称',
  `carrier_node_id` varchar(256) NOT NULL COMMENT '组织中调度服务的 nodeId',
  PRIMARY KEY (`identity_id`)
) COMMENT='任务组织信息表，用于存储从调度服务获取的任务数据快照中组织信息数据';

-- ----------------------------
-- Table structure for task_power_provider
-- ----------------------------
DROP TABLE IF EXISTS `task_power_provider`;
CREATE TABLE `task_power_provider` (
  `task_id` varchar(256) NOT NULL COMMENT '任务ID,hash',
  `identity_id` varchar(256) NOT NULL COMMENT '算力提供者组织身份ID',
  party_id VARCHAR(200) NOT NULL COMMENT '参与方在计算任务中的partyId',
  `total_core` INT DEFAULT '0' COMMENT '任务总CPU信息',
  `used_core` INT DEFAULT '0' COMMENT '任务占用CPU信息',
  `total_memory` BIGINT DEFAULT '0' COMMENT '任务总内存信息',
  `used_memory` BIGINT DEFAULT '0' COMMENT '任务占用内存信息',
  `total_Bandwidth` BIGINT DEFAULT '0' COMMENT '任务总带宽信息',
  `used_Bandwidth` BIGINT DEFAULT '0' COMMENT '任务占用带宽信息',
  PRIMARY KEY (`task_id`,`identity_id`)
) COMMENT='任务算力提供方表 任务数据提供方基础信息';

-- ----------------------------
-- Table structure for task_result_consumer
-- ----------------------------
DROP TABLE IF EXISTS `task_result_consumer`;
CREATE TABLE `task_result_consumer` (
  `task_id` varchar(256) NOT NULL COMMENT '任务ID,hash',
  `consumer_identity_id` varchar(256) NOT NULL COMMENT '结果消费者组织身份ID',
  consumer_party_id VARCHAR(200) NOT NULL COMMENT '参与方在计算任务中的partyId',
  `producer_identity_id` varchar(256) COMMENT '结果产生者的组织身份ID',
  producer_party_id VARCHAR(200) COMMENT '参与方在计算任务中的partyId',
  PRIMARY KEY (`task_id`,`consumer_identity_id`)
) COMMENT='任务结果接收方表 任务结果接收方信息';


DROP TABLE IF EXISTS `local_power_load_snapshot`;
CREATE TABLE `local_power_load_snapshot` (
    node_id varchar(256) NOT NULL COMMENT '算力节点id',
    snapshot_time DATETIME NOT NULL COMMENT '快照时间点，精确到小时',
    used_core int COMMENT '核心使用数',
    used_memory BIGINT COMMENT '内存使用数',
    used_bandwidth BIGINT COMMENT '带宽使用数',
    PRIMARY KEY (node_id, snapshot_time)
) COMMENT='本地算力负载快照统计';


DROP TABLE IF EXISTS `data_sync`;
CREATE TABLE `data_sync` (
    data_type varchar(256) NOT NULL COMMENT '数据类型',
    latest_synced DATETIME(3) NOT NULL COMMENT '数据最新同步时间点点，精确到毫秒',
    PRIMARY KEY (data_type)
) COMMENT='数据同步时间记录';

INSERT INTO data_sync(data_type, latest_synced) VALUES('data_auth_req', '1970-01-01 00:00:00');
INSERT INTO data_sync(data_type, latest_synced) VALUES('local_meta_data', '1970-01-01 00:00:00');
INSERT INTO data_sync(data_type, latest_synced) VALUES('local_task', '1970-01-01 00:00:00');


-- 创建全网元数据月统计视图
CREATE OR REPLACE VIEW v_global_data_file_stats_monthly as
SELECT a.stats_time, a.month_size, SUM(b.month_size) AS accu_size
FROM (
    SELECT DATE_FORMAT(gdf.publish_time, '%Y-%m')  as stats_time, sum(gdf.size) as month_size
    FROM global_data_file gdf
    WHERE gdf.status=2
    GROUP BY DATE_FORMAT(gdf.publish_time, '%Y-%m')
    ORDER BY DATE_FORMAT(gdf.publish_time, '%Y-%m')
) a
JOIN (
    SELECT DATE_FORMAT(gdf.publish_time, '%Y-%m')  as stats_time, sum(gdf.size) as month_size
    FROM global_data_file gdf
    WHERE gdf.status=2
    GROUP BY DATE_FORMAT(gdf.publish_time, '%Y-%m')
    ORDER BY DATE_FORMAT(gdf.publish_time, '%Y-%m')
) b
ON a.stats_time >= b.stats_time
GROUP BY a.stats_time
ORDER BY a.stats_time;

-- 创建全网元数据日统计视图
CREATE OR REPLACE VIEW v_global_data_file_stats_daily as
SELECT a.stats_time, a.day_size, SUM(b.day_size) AS accu_size
FROM (
    SELECT DATE(gdf.publish_time) as stats_time, sum(gdf.size) as day_size
    FROM global_data_file gdf
    WHERE gdf.status=2
    GROUP BY DATE(gdf.publish_time)
    ORDER BY DATE(gdf.publish_time)
) a
JOIN (
    SELECT DATE(gdf.publish_time) as stats_time, sum(gdf.size) as day_size
    FROM global_data_file gdf
    WHERE gdf.status=2
    GROUP BY DATE(gdf.publish_time)
    ORDER BY DATE(gdf.publish_time)
) b
ON a.stats_time >= b.stats_time
GROUP BY a.stats_time
ORDER BY a.stats_time;

-- 创建全网算力月统计视图
CREATE OR REPLACE VIEW v_global_power_stats_monthly as
SELECT a.stats_time, a.month_core, a.month_memory, a.month_bandwidth, SUM(b.month_core) AS accu_core, SUM(b.month_memory) AS accu_memory, SUM(b.month_bandwidth) AS accu_bandwidth
FROM (
    SELECT DATE_FORMAT(gp.publish_at, '%Y-%m')  as stats_time, sum(gp.core) as month_core, sum(gp.memory) as month_memory, sum(gp.bandwidth) as month_bandwidth
    FROM global_power gp
    WHERE gp.status=2 or gp.status=3
    GROUP BY DATE_FORMAT(gp.publish_at, '%Y-%m')
    ORDER BY DATE_FORMAT(gp.publish_at, '%Y-%m')
) a
JOIN (
    SELECT DATE_FORMAT(gp.publish_at, '%Y-%m')  as stats_time, sum(gp.core) as month_core, sum(gp.memory) as month_memory, sum(gp.bandwidth) as month_bandwidth
    FROM global_power gp
    WHERE gp.status=2 or gp.status=3
    GROUP BY DATE_FORMAT(gp.publish_at, '%Y-%m')
    ORDER BY DATE_FORMAT(gp.publish_at, '%Y-%m')
) b
ON a.stats_time >= b.stats_time
GROUP BY a.stats_time
ORDER BY a.stats_time;


-- 创建全网算力日统计视图
CREATE OR REPLACE VIEW v_global_power_stats_daily as
SELECT a.stats_time, a.day_core, a.day_memory, a.day_bandwidth, SUM(b.day_core) AS accu_core, SUM(b.day_memory) AS accu_memory, SUM(b.day_bandwidth) AS accu_bandwidth
FROM (
    SELECT DATE(gp.publish_at)  as stats_time, sum(gp.core) as day_core, sum(gp.memory) as day_memory, sum(gp.bandwidth) as day_bandwidth
    FROM global_power gp
    WHERE gp.status=2 or gp.status=3
    GROUP BY DATE(gp.publish_at)
    ORDER BY DATE(gp.publish_at)
) a
JOIN (
    SELECT DATE(gp.publish_at)  as stats_time, sum(gp.core) as day_core, sum(gp.memory) as day_memory, sum(gp.bandwidth) as day_bandwidth
    FROM global_power gp
    WHERE gp.status=2 or gp.status=3
    GROUP BY DATE(gp.publish_at)
    ORDER BY DATE(gp.publish_at)
) b
ON a.stats_time >= b.stats_time
GROUP BY a.stats_time
ORDER BY a.stats_time;

-- 本地统计

-- 创建本地元数据月统计视图
CREATE OR REPLACE VIEW v_local_data_file_stats_monthly as
SELECT a.stats_time, a.month_size, SUM(b.month_size) AS accu_size
FROM (
    SELECT DATE_FORMAT(lmd.publish_time, '%Y-%m')  as stats_time, sum(ldf.size) as month_size
    FROM local_data_file ldf, local_meta_data lmd
    WHERE ldf.file_id = lmd.file_id AND lmd.status = 2
    GROUP BY DATE_FORMAT(lmd.publish_time, '%Y-%m')
    ORDER BY DATE_FORMAT(lmd.publish_time, '%Y-%m')
) a
JOIN (
    SELECT DATE_FORMAT(lmd.publish_time, '%Y-%m')  as stats_time, sum(ldf.size) as month_size
    FROM local_data_file ldf, local_meta_data lmd
    WHERE ldf.file_id = lmd.file_id AND lmd.status = 2
    GROUP BY DATE_FORMAT(lmd.publish_time, '%Y-%m')
    ORDER BY DATE_FORMAT(lmd.publish_time, '%Y-%m')
) b
ON a.stats_time >= b.stats_time
GROUP BY a.stats_time
ORDER BY a.stats_time;


-- 创建本地算力月统计视图
CREATE OR REPLACE VIEW v_local_power_stats_monthly as
SELECT a.stats_time, a.month_core, a.month_memory, a.month_bandwidth, SUM(b.month_core) AS accu_core, SUM(b.month_memory) AS accu_memory, SUM(b.month_bandwidth) AS accu_bandwidth
FROM (
    SELECT DATE_FORMAT(lpn.start_time, '%Y-%m')  as stats_time, sum(lpn.core) as month_core, sum(lpn.memory) as month_memory, sum(lpn.bandwidth) as month_bandwidth
    FROM local_power_node lpn
    WHERE lpn.power_status=2 or lpn.power_status=3
    GROUP BY DATE_FORMAT(lpn.start_time, '%Y-%m')
    ORDER BY DATE_FORMAT(lpn.start_time, '%Y-%m')
) a
JOIN (
    SELECT DATE_FORMAT(lpn.start_time, '%Y-%m')  as stats_time, sum(lpn.core) as month_core, sum(lpn.memory) as month_memory, sum(lpn.bandwidth) as month_bandwidth
    FROM local_power_node lpn
    WHERE lpn.power_status=2 or lpn.power_status=3
    GROUP BY DATE_FORMAT(lpn.start_time, '%Y-%m')
    ORDER BY DATE_FORMAT(lpn.start_time, '%Y-%m')
) b
ON a.stats_time >= b.stats_time
GROUP BY a.stats_time
ORDER BY a.stats_time;


DELIMITER $$
drop event if EXISTS local_power_load_snapshot_event $$
CREATE EVENT local_power_load_snapshot_event
	ON SCHEDULE EVERY 1 hour STARTS now()
	ON COMPLETION PRESERVE
	DO
BEGIN
    INSERT INTO local_power_load_snapshot (node_id, snapshot_time, used_core, used_memory, used_bandwidth)
    SELECT node_id, DATE_FORMAT(CURRENT_TIMESTAMP(), '%Y-%m-%d %H:00:00'), sum(used_core) as used_core, sum(used_memory) as used_memory, sum(used_bandwidth) as used_bandwidth
    FROM  local_power_join_task
    GROUP BY node_id;
END$$
ALTER EVENT local_power_load_snapshot_event ENABLE$$
DELIMITER ;

SET Global event_scheduler=1;