drop database if exists `datum_admin`;

CREATE DATABASE `datum_admin` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use `datum_admin`;
/*
Navicat MySQL Data Transfer

Source Server Version : 50739
Target Server Type    : MYSQL
Target Server Version : 50739
File Encoding         : 65001

Date: 2022-08-15 07:37:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for apply_record
-- ----------------------------
DROP TABLE IF EXISTS `apply_record`;
CREATE TABLE `apply_record` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `pct_id` int(11) DEFAULT NULL COMMENT '证书模板pctid',
                                `apply_org` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发起申请的组织',
                                `approve_org` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审批的组织',
                                `start_time` timestamp NULL DEFAULT NULL COMMENT '申请时间',
                                `end_time` timestamp NULL DEFAULT NULL COMMENT '申请时间',
                                `progress` tinyint(1) DEFAULT NULL COMMENT '申请进度：0-申请中，1-申请通过，2-申请失败',
                                `apply_remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '申请备注',
                                `approve_remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审批备注',
                                `material` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '申请材料的url',
                                `material_desc` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '申请材料的描述',
                                `vc` text COLLATE utf8mb4_unicode_ci,
                                `expiration_date` varchar(0) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '过期时间',
                                `status` tinyint(1) DEFAULT NULL COMMENT '证书状态：0-无效，1-有效，2-待生效',
                                `context` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                `claim` text COLLATE utf8mb4_unicode_ci,
                                `used` tinyint(1) DEFAULT '0' COMMENT '证书申请成功后是否被使用：0-未使用，1-已使用',
                                `tx_hash` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建VC的交易hash',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of apply_record
-- ----------------------------

-- ----------------------------
-- Table structure for attribute_data_token
-- ----------------------------
DROP TABLE IF EXISTS `attribute_data_token`;
CREATE TABLE `attribute_data_token` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'dataTokenId',
                                        `address` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '合约地址',
                                        `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约名称',
                                        `symbol` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约符号',
                                        `total` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '总发行量',
                                        `desc` text COLLATE utf8mb4_unicode_ci COMMENT '价值证明描述',
                                        `owner` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '拥有者钱包地址',
                                        `meta_data_db_id` int(11) NOT NULL COMMENT '对应的metaData表中的Id',
                                        `publish_nonce` int(11) DEFAULT '0' COMMENT '发布凭证交易nonce',
                                        `publish_hash` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发布上链的交易hash，供查询发布状态',
                                        `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态：1-发布中，2-发布失败，3-发布成功，4-绑定中，5-绑定失败，6-绑定成功',
                                        `rec_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                        `rec_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        PRIMARY KEY (`id`),
                                        UNIQUE KEY `ukey_meta_data_id` (`meta_data_db_id`) USING HASH COMMENT 'meta_data_id唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据凭证表';

-- ----------------------------
-- Records of attribute_data_token
-- ----------------------------

-- ----------------------------
-- Table structure for attribute_data_token_inventory
-- ----------------------------
DROP TABLE IF EXISTS `attribute_data_token_inventory`;
CREATE TABLE `attribute_data_token_inventory` (
                                                  `data_token_address` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '合约地址',
                                                  `token_id` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '库存ID',
                                                  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
                                                  `image_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片路劲',
                                                  `desc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
                                                  `end_time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '库存有效期的结束时间',
                                                  `usage` tinyint(1) DEFAULT NULL COMMENT '用法：1-明文，2-密文',
                                                  `owner` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                                  UNIQUE KEY `tokenAddress_tokenId_ukey` (`data_token_address`,`token_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of attribute_data_token_inventory
-- ----------------------------

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
                             `identity_id` varchar(140) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织的地址',
                             `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织的url',
                             `join_time` timestamp NULL DEFAULT NULL COMMENT '加入委员会的时间',
                             `is_admin` tinyint(1) DEFAULT '0' COMMENT '是否是初始成员：0-否，1-是',
                             PRIMARY KEY (`identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of authority
-- ----------------------------

-- ----------------------------
-- Table structure for authority_business
-- ----------------------------
DROP TABLE IF EXISTS `authority_business`;
CREATE TABLE `authority_business` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                      `type` tinyint(4) DEFAULT NULL COMMENT '业务类型：1-申请认证，101-提名加入提案，102-提名踢出提案',
                                      `relation_id` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联的提案表和vc申请表ID',
                                      `apply_org` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发起申请的组织',
                                      `specify_org` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提案中涉及到的组织或者申请认证指定的审批组织',
                                      `start_time` timestamp NULL DEFAULT NULL COMMENT '业务开始时间',
                                      `process_status` tinyint(1) DEFAULT NULL COMMENT '处理状态：0-未处理，1-同意，2-不同意',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='委员会事务表';

-- ----------------------------
-- Records of authority_business
-- ----------------------------

-- ----------------------------
-- Table structure for data_auth
-- ----------------------------
DROP TABLE IF EXISTS `data_auth`;
CREATE TABLE `data_auth` (
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
-- Records of data_auth
-- ----------------------------

-- ----------------------------
-- Table structure for data_file
-- ----------------------------
DROP TABLE IF EXISTS `data_file`;
CREATE TABLE `data_file` (
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
-- Records of data_file
-- ----------------------------

-- ----------------------------
-- Table structure for data_node
-- ----------------------------
DROP TABLE IF EXISTS `data_node`;
CREATE TABLE `data_node` (
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
-- Records of data_node
-- ----------------------------

-- ----------------------------
-- Table structure for data_sync
-- ----------------------------
DROP TABLE IF EXISTS `data_sync`;
CREATE TABLE `data_sync` (
                             `data_type` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据类型',
                             `latest_synced` datetime(3) NOT NULL COMMENT '数据最新同步时间点点，精确到毫秒',
                             PRIMARY KEY (`data_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据同步时间记录';

-- ----------------------------
-- Records of data_sync
-- ----------------------------
INSERT INTO `data_sync` VALUES ('data_auth_req', '1970-01-01 00:00:00.000');
INSERT INTO `data_sync` VALUES ('global_org', '1970-01-01 00:00:00.000');
INSERT INTO `data_sync` VALUES ('local_meta_data', '1970-01-01 00:00:00.000');
INSERT INTO `data_sync` VALUES ('local_task', '1970-01-01 00:00:00.000');

-- ----------------------------
-- Table structure for data_token
-- ----------------------------
DROP TABLE IF EXISTS `data_token`;
CREATE TABLE `data_token` (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'dataTokenId',
                              `address` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '合约地址',
                              `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约名称',
                              `symbol` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约符号',
                              `init` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '初始发行量',
                              `total` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '总发行量',
                              `decimal` int(11) DEFAULT '18' COMMENT '合约精度',
                              `desc` text COLLATE utf8mb4_unicode_ci COMMENT '价值证明描述',
                              `owner` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '拥有者钱包地址',
                              `meta_data_db_id` int(11) NOT NULL COMMENT '对应的metaData表中的Id',
                              `plaintext_fee` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '明文费用',
                              `ciphertext_fee` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密文费用',
                              `new_plaintext_fee` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '想要修改成的明文费用',
                              `new_ciphertext_fee` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '想要修改成的密文费用',
                              `publish_nonce` int(11) DEFAULT '0' COMMENT '发布凭证交易nonce',
                              `publish_hash` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发布上链的交易hash，供查询发布状态',
                              `up_nonce` int(11) DEFAULT '0' COMMENT '上架交易nonce',
                              `up_hash` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上架dex的交易hash',
                              `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态：1-发布中，2-发布失败，3-发布成功，4-定价中，5-定价失败，6-定价成功，7-绑定中，8-绑定失败，9-绑定成功',
                              `rec_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              `rec_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              `fee_update_time` timestamp NULL DEFAULT NULL COMMENT '明文和密文消耗量上一次修改成功的时间',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `ukey_meta_data_id` (`meta_data_db_id`) USING HASH COMMENT 'meta_data_id唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据凭证表';

-- ----------------------------
-- Records of data_token
-- ----------------------------

-- ----------------------------
-- Table structure for global_org
-- ----------------------------
DROP TABLE IF EXISTS `global_org`;
CREATE TABLE `global_org` (
                              `identity_type` tinyint(4) NOT NULL COMMENT '身份认证标识的类型 0-未知, 1-DID',
                              `identity_id` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '身份认证标识的id',
                              `node_id` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织节点ID',
                              `node_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织身份名称',
                              `data_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预留',
                              `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1 - valid, 2 - invalid',
                              `image_url` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织机构图像url',
                              `details` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织机构简介',
                              `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of global_org
-- ----------------------------

-- ----------------------------
-- Table structure for meta_data
-- ----------------------------
DROP TABLE IF EXISTS `meta_data`;
CREATE TABLE `meta_data` (
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
                             `file_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件ID',
                             `meta_data_id` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '元数据ID,hash',
                             `meta_data_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '元数据名称',
                             `status` int(11) NOT NULL DEFAULT '0' COMMENT '元数据的状态 (0-未知;1-还未发布的新表;2-已发布的表;3-已撤销的表;101-已删除;102-发布中;103-撤回中;)',
                             `publish_time` datetime DEFAULT NULL COMMENT '元数据发布时间',
                             `desc` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '元数据描述',
                             `industry` int(4) DEFAULT NULL COMMENT '所属行业 1：金融业（银行）、2：金融业（保险）、3：金融业（证券）、4：金融业（其他）、5：ICT、 6：制造业、 7：能源业、 8：交通运输业、 9 ：医疗健康业、 10 ：公共服务业、 11：传媒广告业、 12 ：其他行业',
                             `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `user` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '拥有者钱包地址',
                             `user_type` tinyint(1) DEFAULT '1' COMMENT '用户类型：1-eth，2-alaya，3-platon',
                             `meta_data_type` tinyint(2) DEFAULT '1' COMMENT '表示该元数据是 普通数据 还是 模型数据的元数据 (0: 未定义; 1: 普通数据元数据; 2: 模型数据元数据)',
                             `usage` tinyint(1) DEFAULT NULL COMMENT '用法：1-明文，2-密文，3-都支持',
                             `data_token_address` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '绑定后的无属性数据凭证地址，执行绑定动作前该字段都是空的',
                             `attribute_data_token_address` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '绑定后的无属性数据凭证地址，执行绑定动作前该字段都是空的',
                             `sign` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户签名',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `meta_data_id` (`meta_data_id`),
                             KEY `file_id` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织元数据文件表，文件的元数据信息';

-- ----------------------------
-- Records of meta_data
-- ----------------------------

-- ----------------------------
-- Table structure for meta_data_column
-- ----------------------------
DROP TABLE IF EXISTS `meta_data_column`;
CREATE TABLE `meta_data_column` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
                                    `meta_data_db_id` int(11) NOT NULL COMMENT '元数据local_meta_data自增id',
                                    `column_idx` int(11) DEFAULT NULL COMMENT '列索引',
                                    `column_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '列名',
                                    `column_type` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '列类型',
                                    `size` int(11) DEFAULT '0' COMMENT '列大小（byte）',
                                    `remarks` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '列描述',
                                    `visible` tinyint(1) DEFAULT '0' COMMENT '是否可见',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `local_meta_data_db_id` (`meta_data_db_id`,`column_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本组织数据文件表列详细表，描述源文件中每一列的列信息';

-- ----------------------------
-- Records of meta_data_column
-- ----------------------------

-- ----------------------------
-- Table structure for org
-- ----------------------------
DROP TABLE IF EXISTS `org`;
CREATE TABLE `org` (
                       `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机构名称',
                       `identity_id` varchar(140) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构身份标识ID',
                       `carrier_node_id` varchar(140) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构调度服务node id，入网后可以获取到',
                       `carrier_ip` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调度服务IP地址',
                       `carrier_port` int(11) DEFAULT NULL COMMENT '调度服务端口号',
                       `carrier_conn_status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '连接状态 enabled：可用, disabled:不可用',
                       `carrier_status` int(11) DEFAULT '0' COMMENT '调度服务的状态（0:unknown未知、1: active活跃、2:leave: 离开网络、3:join加入网络 4:unuseful不可用）',
                       `conn_node_count` int(11) DEFAULT '0' COMMENT '节点连接的数量',
                       `carrier_conn_time` datetime DEFAULT NULL COMMENT '服务连接时间',
                       `status` tinyint(1) DEFAULT '0' COMMENT '0未入网，1已入网， 99已退网',
                       `local_bootstrap_node` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '当前系统的本地节点，可以作为引导节点提供给三方节点',
                       `local_multi_addr` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '当前系统本地的',
                       `image_url` varchar(140) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织机构图像url',
                       `profile` varchar(280) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织机构简介',
                       `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                       `observer_proxy_wallet_address` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调度服务钱包地址',
                       `is_authority` tinyint(1) DEFAULT '0' COMMENT '是否是委员会成员：0-否，1-是',
                       `credential` varchar(4086) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'vc json字符串',
                       PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本地组织信息表';

-- ----------------------------
-- Records of org
-- ----------------------------

-- ----------------------------
-- Table structure for power_join_task
-- ----------------------------
DROP TABLE IF EXISTS `power_join_task`;
CREATE TABLE `power_join_task` (
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
-- Records of power_join_task
-- ----------------------------

-- ----------------------------
-- Table structure for power_load_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `power_load_snapshot`;
CREATE TABLE `power_load_snapshot` (
                                       `node_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '算力节点id',
                                       `snapshot_time` datetime NOT NULL COMMENT '快照时间点，精确到小时',
                                       `used_core` int(11) DEFAULT NULL COMMENT '核心使用数',
                                       `used_memory` bigint(20) DEFAULT NULL COMMENT '内存使用数',
                                       `used_bandwidth` bigint(20) DEFAULT NULL COMMENT '带宽使用数',
                                       PRIMARY KEY (`node_id`,`snapshot_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='本地算力负载快照统计';

-- ----------------------------
-- Records of power_load_snapshot
-- ----------------------------

-- ----------------------------
-- Table structure for power_node
-- ----------------------------
DROP TABLE IF EXISTS `power_node`;
CREATE TABLE `power_node` (
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
-- Records of power_node
-- ----------------------------

-- ----------------------------
-- Table structure for proposal
-- ----------------------------
DROP TABLE IF EXISTS `proposal`;
CREATE TABLE `proposal` (
                            `id` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '提案id',
                            `submitter` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '提案提交者',
                            `candidate` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '提案关联者',
                            `type` int(11) NOT NULL COMMENT '提案类型, 1-增加委员会成员; 2-剔除委员会成员; 3-委员会成员退出',
                            `submission_bn` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提交块高',
                            `vote_begin_bn` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投票开始块高',
                            `vote_end_bn` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投票结束块高',
                            `auto_quit_bn` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主动退出的块高',
                            `vote_agree_number` int(11) DEFAULT NULL COMMENT '赞成票数量',
                            `authority_number` int(11) DEFAULT NULL COMMENT '委员总数，如果为空需要实时查询',
                            `status` int(11) NOT NULL COMMENT '提案状态：0-投票未开始；1-投票开始；2-投票结束，但是还未通过；3-投票通过；4-投票未通过；5-退出中；6-已退出；7-撤销中；8-已撤销',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `material` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提案的材料',
                            `material_desc` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提案材料的描述',
                            `remark` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提案附言',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of proposal
-- ----------------------------

-- ----------------------------
-- Table structure for proposal_log
-- ----------------------------
DROP TABLE IF EXISTS `proposal_log`;
CREATE TABLE `proposal_log` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志表id(自增长)',
                                `type` int(11) NOT NULL COMMENT '事件类型, 1-提交提案; 2-撤销提案; 3-对提案投票; 4-投票结果; 5-新增委员会; 6-删除委员会',
                                `block_number` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '事件所在块高',
                                `tx_hash` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '事件对应交易hash',
                                `log_index` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '事件日志index',
                                `content` varchar(2048) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '事件对应内容',
                                `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0-未处理，1-已处理',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of proposal_log
-- ----------------------------

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
                            `type` tinyint(4) NOT NULL COMMENT '资源类型：1-接口url，2-导航栏菜单，3-页面按钮',
                            `name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '资源名称',
                            `value` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '资源内容：根据type改变，如果是接口url，则value为url，如果type是其他的，则是前端定义值',
                            `url_resource_id` int(11) DEFAULT NULL COMMENT '按钮或者菜单对应的url的资源id',
                            `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父资源ID,如果没有父资源ID，则设置0',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资源表';

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('101', '1', '数据授权-授权数据列表分页查询', '/api/v1/dataAuth/listLocalDataAuth', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('102', '1', '数据授权-授权数据数量统计', '/api/v1/dataAuth/dataAuthStatistics', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('103', '1', '数据授权-数据授权同意、拒绝', '/api/v1/dataAuth/replyDataAuth', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('104', '1', '数据授权-授权申请查看', '/api/v1/dataAuth/dataAuthDetail', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('201', '1', '我的数据-数据列表关键字查询', '/api/v1/data/listLocalMetaDataByKeyword', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('202', '1', '我的数据-关键字查询未发布无属性凭证的元数据', '/api/v1/data/listMetaDataUnPublishDataTokenByKeyword', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('203', '1', '我的数据-关键字查询未发布有属性凭证的元数据', '/api/v1/data/listMetaDataUnPublishAttributeDataTokenByKeyword', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('204', '1', '我的数据-数据参与的任务信息列表', '/api/v1/data/listTaskByMetaDataId', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('205', '1', '我的数据-导入文件', '/api/v1/data/uploadFile', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('206', '1', '我的数据-添加数据/另存为新数据', '/api/v1/data/addLocalMetaData', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('207', '1', '我的数据-查看元数据详情', '/api/v1/data/localMetaDataInfo', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('208', '1', '我的数据-修改元数据信息', '/api/v1/data/updateLocalMetaData', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('209', '1', '我的数据-元数据操作：上架、下架和删除', '/api/v1/data/localMetaDataOp', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('210', '1', '我的数据-获取元数据MetaDataOption', '/api/v1/data/getMetaDataOption', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('211', '1', '我的数据-源文件下载', '/api/v1/data/download', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('212', '1', '我的数据-校验元数据名称是否合法', '/api/v1/data/checkResourceName', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('301', '1', '调度服务配置-连接调度节点', '/api/v1/carrier/connectNode', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('302', '1', '调度服务配置-申请准入网络', '/api/v1/carrier/applyJoinNetwork', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('303', '1', '调度服务配置-注销网络', '/api/v1/carrier/cancelJoinNetwork', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('401', '1', '数据节点管理-数据节点分页查询', '/api/v1/datanode/listNode', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('402', '1', '数据节点管理-修改数据节点名称', '/api/v1/datanode/updateNodeName', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('501', '1', '计算节点控制类-修改计算节点名称', '/api/v1/powernode/updateNodeName', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('502', '1', '计算节点控制类-查询计算节点详情', '/api/v1/powernode/powerNodeDetails', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('503', '1', '计算节点控制类-查询计算节点列表', '/api/v1/powernode/listPowerNode', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('504', '1', '计算节点控制类-启用算力', '/api/v1/powernode/publishPower', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('505', '1', '计算节点控制类-停用算力', '/api/v1/powernode/revokePower', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('506', '1', '计算节点控制类-查询计算节点参与的正在计算中的任务列表', '/api/v1/powernode/listRunningTaskByPowerNodeId', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('507', '1', '计算节点控制类-查询算力节点的最近24小时的负载情况', '/api/v1/powernode/listLocalPowerLoadSnapshotByPowerNodeId', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('508', '1', '计算节点控制类-查询算力节点当前的负载情况', '/api/v1/powernode/getCurrentLocalPowerLoadByPowerNodeId', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('601', '1', '种子节点控制类-新增种子节点', '/api/v1/seednode/addSeedNode', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('602', '1', '种子节点控制类-删除种子节点', '/api/v1/seednode/deleteSeedNode', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('603', '1', '种子节点控制类-查询种子节点服务列表', '/api/v1/seednode/listSeedNode', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('604', '1', '种子节点控制类-查询种子节点详情', '/api/v1/seednode/seedNodeDetails', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('605', '1', '种子节点控制类-校验计算节点名称是否可用', '/api/v1/seednode/checkSeedNodeId', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('701', '1', '委员会组织-主页内容', '/api/v1/authority/home', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('702', '1', '委员会组织-委员会列表', '/api/v1/authority/list', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('703', '1', '委员会组织-提名踢出', '/api/v1/authority/kickOut', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('704', '1', '委员会组织-退出委员会', '/api/v1/authority/exit', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('705', '1', '委员会组织-提名委员会时上传图片', '/api/v1/authority/upload', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('706', '1', '委员会组织-获取可提名的成员', '/api/v1/authority/getNominateMember', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('707', '1', '委员会组织-提名成员', '/api/v1/authority/nominate', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('708', '1', '委员会组织-我的待办列表', '/api/v1/authority/todoList', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('709', '1', '委员会组织-我的待办详情', '/api/v1/authority/todoDetail', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('710', '1', '委员会组织-处理我的待办', '/api/v1/authority/processTodo', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('711', '1', '委员会组织-我的已办列表', '/api/v1/authority/doneList', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('712', '1', '委员会组织-我的已办详情', '/api/v1/authority/doneDetail', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('713', '1', '委员会组织-我的提案列表', '/api/v1/authority/myProposalList', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('714', '1', '委员会组织-我的提案详情', '/api/v1/authority/proposalDetail', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('715', '1', '委员会组织-撤回提案', '/api/v1/authority/revokeProposal', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('801', '1', '普通组织-主页内容', '/api/v1/generalOrganization/home', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('802', '1', '普通组织-我的申请列表', '/api/v1/generalOrganization/applyList', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('803', '1', '普通组织-查看申请详情', '/api/v1/generalOrganization/applyDetail', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('804', '1', '普通组织-下载证书', '/api/v1/generalOrganization/download', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('805', '1', '普通组织-上传资料', '/api/v1/generalOrganization/uploadmMaterial', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('806', '1', '普通组织-申请认证', '/api/v1/generalOrganization/apply', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('807', '1', '普通组织-使用证书', '/api/v1/generalOrganization/use', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('901', '1', '系统概况-查询本组织计算资源占用情况', '/api/v1/overview/localPowerUsage', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('902', '1', '系统概况-查询我发布的数据', '/api/v1/overview/localDataFileStatsTrendMonthly', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('903', '1', '系统概况-查询本组织发布的算力', '/api/v1/overview/localPowerStatsTrendMonthly', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('904', '1', '系统概况-查询我的计算任务概况', '/api/v1/overview/myTaskOverview', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('905', '1', '系统概况-查询我的数据待授权列表', '/api/v1/overview/listDataAuthReqWaitingForApprove', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('906', '1', '系统概况-查询我的数据凭证概况', '/api/v1/overview/dataTokenOverview', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1001', '1', '计算任务相关接口-我参与的任务情况统计', '/api/v1/task/myTaskStatistics', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1002', '1', '计算任务相关接口-条件查询组织参与的任务列表', '/api/v1/task/listMyTask', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1003', '1', '计算任务相关接口-查询组织参与的单个任务详情', '/api/v1/task/taskInfo', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1004', '1', '计算任务相关接口-单个任务事件日志列表', '/api/v1/task/listTaskEvent', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1101', '1', '有属性数据凭证-获取发布凭证需要的配置', '/api/v1/attributeDataToken/getPublishConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1102', '1', '有属性数据凭证-发布凭证', '/api/v1/attributeDataToken/publish', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1103', '1', '有属性数据凭证-根据id获取attributeDataToken状态', '/api/v1/attributeDataToken/getAttributeDataTokenStatus', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1104', '1', '有属性数据凭证-修改凭证状态', '/api/v1/attributeDataToken/updateDataTokenStatus', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1105', '1', '有属性数据凭证-查询凭证列表', '/api/v1/attributeDataToken/page', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1106', '1', '有属性数据凭证-绑定元数据', '/api/v1/attributeDataToken/bindMetaData', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1107', '1', '有属性数据凭证-NFT交易所地址', '/api/v1/attributeDataToken/getExchange', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1108', '1', '有属性数据凭证-上传图片接口', '/api/v1/attributeDataToken/inventoryUpLoad', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1109', '1', '有属性数据凭证-上传图片，名称和描述接口', '/api/v1/attributeDataToken/inventoryUpLoad2', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1110', '1', '有属性数据凭证-刷新库存信息5次', '/api/v1/attributeDataToken/refreshInventory5', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1111', '1', '有属性数据凭证-刷新指定tokenId库存信息', '/api/v1/attributeDataToken/refreshInventoryByTokenId', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1112', '1', '有属性数据凭证-获取dataToken库存列表', '/api/v1/attributeDataToken/getDataTokenInventoryPage', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1113', '1', '有属性数据凭证-根据id获取dataToken库存详情', '/api/v1/attributeDataToken/getDataTokenInventoryDetail', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1201', '1', '无属性数据凭证-查询列表', '/api/v1/dataToken/page', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1202', '1', '无属性数据凭证-根据id获取dataToken状态', '/api/v1/dataToken/getDataTokenStatus', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1203', '1', '无属性数据凭证-查询dex链接地址', '/api/v1/dataToken/getDexWebUrl', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1204', '1', '无属性数据凭证-获取发布凭证需要的配置', '/api/v1/dataToken/getPublishConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1205', '1', '无属性数据凭证-发布凭证', '/api/v1/dataToken/publish', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1206', '1', '无属性数据凭证-获取上架市场需要的配置', '/api/v1/dataToken/getUpConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1207', '1', '无属性数据凭证-上架市场', '/api/v1/dataToken/up', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1208', '1', '无属性数据凭证-将凭证修改为上架状态', '/api/v1/dataToken/updateToPrinceSuccess', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1209', '1', '无属性数据凭证-修改明文和密文消耗量', '/api/v1/dataToken/updateFee', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1210', '1', '无属性数据凭证-绑定元数据', '/api/v1/dataToken/bindMetaData', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1301', '1', '用户相关接口-获取登录Nonce', '/api/v1/user/getLoginNonce', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1302', '1', '用户相关接口-用户登录', '/api/v1/user/login', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1303', '1', '用户相关接口-退出登录状态', '/api/v1/user/logout', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1304', '1', '用户相关接口-替换管理员', '/api/v1/user/updateAdmin', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1305', '1', '用户相关接口-设置机构名称', '/api/v1/user/setOrgName', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1306', '1', '用户相关接口-设置did', '/api/v1/user/setDid', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1307', '1', '用户相关接口-更新组织信息（机构信息识别名称，头像链接，或者描述', '/api/v1/user/setDesc', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1308', '1', '用户相关接口-查询出当前组织信息', '/api/v1/user/findLocalOrgInfo', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1401', '1', '系统相关-metaMask所需配置', '/api/v1/system/getMetaMaskConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1402', '1', '系统相关-查询系统配置的key', '/api/v1/system/getSystemConfigKey', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1403', '1', '系统相关-查询配置列表', '/api/v1/system/getAllConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1404', '1', '系统相关-根据key查询配置', '/api/v1/system/getConfigByKey', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1405', '1', '系统相关-新增自定义配置', '/api/v1/system/addCustomConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1406', '1', '系统相关-删除系统配置', '/api/v1/system/deleteCustomConfig', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('1407', '1', '系统相关-更新系统配置', '/api/v1/system/updateValueByKey', NULL, '0');

INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5001', '2', '系统概况', 'overview', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5002', '2', '个人主页', 'userCenter/Profile', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5003', '2', '个人信息', 'userCenter/userInfo', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5004', '2', '修改管理员', 'userCenter/updateAdmin', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5005', '2', '节点管理', 'nodeMgt', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5006', '2', '引导节点管理', 'nodeMgt/SeedNodeMgt', NULL, '5');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5007', '2', '存储资源管理', 'nodeMgt/dataNodeMgt', NULL, '5');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5008', '2', '计算资源管理', 'nodeMgt/computeNodeMgt', NULL, '5');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5009', '2', '我的数据', 'myData', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5010', '2', '数据管理', 'myData/dataMgt', NULL, '9');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5011', '2', '数据添加', 'myData/dataAddition', NULL, '9');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5012', '2', '计算任务', 'tasks', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5013', '2', '我的凭证', 'voucher', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5014', '2', '无属性凭证', 'voucher/NoAttribute', NULL, '13');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5015', '2', '凭证发布', 'myData/dataVoucherPublishing', NULL, '9');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5016', '2', '编辑数据', 'myData/dataMgt/saveNewData', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5017', '2', '数据详情', 'myData/dataMgt/dataDetail', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5018', '2', '任务详情', 'myData/dataMgt/dataDetail/dataDetailTask', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5019', '2', '数据任务详情', 'myData/dataMgt/dataDetail/dataDetailTask/taskDetail', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5020', '2', '数据任务事件', 'myData/dataMgt/dataDetail/dataDetailTask/TaskEvent', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5021', '2', '账号初始化', 'didApplication', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5022', '2', '计算资源节点详情', 'nodeMgt/computeNodeMgt/computeNodeDetail', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5023', '2', '无属性凭证价格设置', 'myData/dataVoucherPublishing/PriceSet', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5024', '2', '创建无属性凭证', 'myData/dataVoucherPublishing/CredentialInfo', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5025', '2', '计算任务详情', 'tasks/taskDetail', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5026', '2', '计算任务事件', 'tasks/TaskEvent', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5027', '2', '构建属性凭证', 'myData/dataVoucherPublishing/AttributedPublishing', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5028', '2', '属性数据凭证page', 'voucher/AttributeCredential', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5029', '2', '有属性凭证创建', 'voucher/AttributeCredential/createCredential', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5030', '2', '有属性凭证库存', 'voucher/AttributeCredential/credentialInventory', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5031', '2', '有属性凭证详情', 'voucher/AttributeCredential/credentialDetails', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5032', '2', '组织管理', 'OrgManage', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5033', '2', '提名委员会', 'OrgManage/nominationCommittee', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5034', '2', '委员会申请认证', 'OrgManage/applyCertification', NULL, '0');
INSERT INTO `resource` (`id`, `type`, `name`, `value`, `url_resource_id`, `parent_id`) VALUES ('5035', '2', '委员会申请认证', 'OrgManage/orgManageApplyDetails', NULL, '0');

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
                                 `role_id` int(11) NOT NULL COMMENT '角色id，默认只有两个角色，0是普通角色，1是管理员角色',
                                 `resource_id` int(11) NOT NULL COMMENT '资源id',
                                 UNIQUE KEY `role_resource_uk` (`role_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色资源关联表';

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '101');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '102');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '103');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '104');

INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '201');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '202');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '203');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '204');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '205');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '206');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '207');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '208');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '209');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '210');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '211');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '212');

INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '901');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '902');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '903');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '904');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '905');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '906');

INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1003');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1004');

INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1101');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1102');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1103');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1104');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1105');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1106');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1107');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1108');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1109');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1110');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1111');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1112');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1113');

INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1201');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1202');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1203');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1204');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1205');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1206');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1207');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1208');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1209');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1210');

INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1301');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1302');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1303');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '1308');

INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5001');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5009');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5010');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5011');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5012');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5013');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5014');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5015');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5016');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5017');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5018');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5019');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5020');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5021');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5022');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5023');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5024');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5025');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5026');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5027');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5028');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5029');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5030');
INSERT INTO `role_resource` (`role_id`, `resource_id`) VALUES ('0', '5031');

-- ----------------------------
-- Table structure for seed_node
-- ----------------------------
DROP TABLE IF EXISTS `seed_node`;
CREATE TABLE `seed_node` (
                             `seed_node_id` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '节点ID',
                             `conn_status` int(11) DEFAULT '0' COMMENT '节点与调度服务的连接状态，0: 未被调度服务连接上; 1: 连接上;',
                             `init_flag` int(2) DEFAULT NULL COMMENT '是否是初始节点(0:否, 1:是)',
                             PRIMARY KEY (`seed_node_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='种子节点配置表';

-- ----------------------------
-- Records of seed_node
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
                              `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
                              `key` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置项',
                              `value` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置值',
                              `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '配置状态 0-失效，1-有效',
                              `desc` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置描述',
                              `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
                              `rec_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `idx_config_key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表，使用k-v形式';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'data_token_factory_address', '0x1D3ee03b5CF808005886873311E33A4D948456C4', '1', '凭证工厂合约地址', '2022-08-05 07:25:32', '2022-04-06 02:05:53');
INSERT INTO `sys_config` VALUES ('2', 'dex_router_address', '0x4378dAF745E9053f6048c4f78c803f3bC8829703', '1', 'router合约地址', '2022-04-06 07:00:01', '2022-04-06 02:14:42');
INSERT INTO `sys_config` VALUES ('3', 'dex_web_url', 'http://10.10.8.181:8080/swap', '1', 'dex链接地址', '2022-04-06 02:43:03', '2022-04-06 02:18:56');
INSERT INTO `sys_config` VALUES ('4', 'chain_name', 'PlatON开发网', '1', '网络名称', '2022-04-07 12:03:53', '2022-04-07 12:01:35');
INSERT INTO `sys_config` VALUES ('5', 'chain_id', '2203181', '1', '链 ID', '2022-04-14 04:32:49', '2022-04-07 12:01:38');
INSERT INTO `sys_config` VALUES ('6', 'rpc_url', 'https://devnetopenapi2.platon.network/rpc', '1', '链rpcUrl，给用户使用，必须是外部ip', '2022-04-08 03:01:41', '2022-04-07 12:01:43');
INSERT INTO `sys_config` VALUES ('7', 'symbol', 'LAT', '1', '货币符号', '2022-04-07 12:04:27', '2022-04-07 12:01:47');
INSERT INTO `sys_config` VALUES ('8', 'block_explorer_url', 'https://devnetscan.platon.network/', '1', '区块浏览器', '2022-04-07 12:05:31', '2022-04-07 12:01:55');
INSERT INTO `sys_config` VALUES ('9', 'hrp', 'lat', '1', 'hrp', '2022-04-07 12:04:35', '2022-04-07 12:01:58');
INSERT INTO `sys_config` VALUES ('10', 'rpc_url_list', 'https://devnetopenapi2.platon.network/rpc', '1', '链rpcUrl，主要是给后台系统用，可以是内部IP', '2022-04-08 03:02:48', '2022-04-08 03:01:36');
INSERT INTO `sys_config` VALUES ('11', 'attribute_data_token_factory_address', '0x6FCf0573c4d95fc927d4319f2f859638d8c8492a', '1', '有属性凭证工厂合约地址', '2022-08-05 07:25:39', '2022-04-06 02:14:42');
INSERT INTO `sys_config` VALUES ('12', 'attribute_data_token_exchange', '<tofunft交易所|https://tofunft.com/>', '1', '有属性凭证交易所地址，格式为<交易所名称,交易所地址>，多个交易所以英文逗号分割', '2022-08-09 10:19:10', '2022-08-09 08:57:18');
INSERT INTO `sys_config` VALUES ('13', 'vote_contract_address', '0x5858781B484B7cdB0d76f34B8bC3bEa2C75561E9', '1', '投票合约合约地址', '2022-08-14 23:14:38', '2022-04-06 02:14:42');
INSERT INTO `sys_config` VALUES ('14', 'vote_contract_deploy_bn', '28197947', '1', '投票合约部署的区块，这个配置决定了事件从哪个区块开始读取', '2022-08-14 23:32:20', '2022-04-06 02:14:42');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='全网任务表 用于同步本地任务数据以及全网的相关数据';

-- ----------------------------
-- Records of task
-- ----------------------------

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
-- Records of task_algo_provider
-- ----------------------------

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
                                      `contract` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '元数据关联的凭证地址',
                                      PRIMARY KEY (`hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务数据提供方表 存储某个任务数据提供方的信息';

-- ----------------------------
-- Records of task_data_provider
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务事件表';

-- ----------------------------
-- Records of task_event
-- ----------------------------

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
-- Records of task_org
-- ----------------------------

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
-- Records of task_power_provider
-- ----------------------------

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
CREATE OR REPLACE VIEW v_data_file_stats_monthly as
SELECT a.stats_time, a.month_size, SUM(a.month_size) AS accu_size,a.`user` AS `user`
FROM (
         SELECT DATE_FORMAT(lmd.publish_time, '%Y-%m')  as stats_time, sum(ldf.size) as month_size,lmd.`user` as `user`
         FROM data_file ldf, meta_data lmd
         WHERE ldf.file_id = lmd.file_id AND lmd.status in (2)
         GROUP BY DATE_FORMAT(lmd.publish_time, '%Y-%m'),lmd.`user`
         ORDER BY DATE_FORMAT(lmd.publish_time, '%Y-%m')
     ) a
GROUP BY a.stats_time,a.`user`
ORDER BY a.stats_time;


-- 创建本地算力月统计视图
CREATE OR REPLACE VIEW v_power_stats_monthly as
SELECT a.stats_time, a.month_core, a.month_memory, a.month_bandwidth, SUM(a.month_core) AS accu_core, SUM(a.month_memory) AS accu_memory, SUM(a.month_bandwidth) AS accu_bandwidth
FROM (
         SELECT DATE_FORMAT(lpn.start_time, '%Y-%m')  as stats_time, sum(lpn.core) as month_core, sum(lpn.memory) as month_memory, sum(lpn.bandwidth) as month_bandwidth
         FROM power_node lpn
         WHERE lpn.power_status=2 or lpn.power_status=3
         GROUP BY DATE_FORMAT(lpn.start_time, '%Y-%m')
         ORDER BY DATE_FORMAT(lpn.start_time, '%Y-%m')
     ) a
GROUP BY a.stats_time
ORDER BY a.stats_time;
-- ----------------------------
-- Event structure for power_load_snapshot_event
-- ----------------------------
DROP EVENT IF EXISTS `power_load_snapshot_event`;
DELIMITER ;;
CREATE EVENT `power_load_snapshot_event` ON SCHEDULE EVERY 1 HOUR STARTS '2021-01-20 00:00:00' ON COMPLETION PRESERVE ENABLE DO BEGIN
    INSERT INTO power_load_snapshot (node_id, snapshot_time, used_core, used_memory, used_bandwidth)
    SELECT node_id, DATE_FORMAT(CURRENT_TIMESTAMP(), '%Y-%m-%d %H:00:00'), sum(used_core) as used_core, sum(used_memory) as used_memory, sum(used_bandwidth) as used_bandwidth
    FROM  power_join_task
    GROUP BY node_id;
END
;;
DELIMITER ;