drop database if exists rosettanet_admin;

CREATE DATABASE rosettanet_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE rosettanet_admin;

DELIMITER ;;


DROP TABLE IF EXISTS bootstrap_node;;
CREATE TABLE bootstrap_node(
     id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
     rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '种子节点配置表';;


DROP TABLE IF EXISTS sys_config;;
CREATE TABLE sys_config(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    config_Key VARCHAR(32)    COMMENT '配置项' ,
    config_Value VARCHAR(32)    COMMENT '配置值' ,
    status VARCHAR(10) NOT NULL  DEFAULT 'N' COMMENT '配置状态 enabled：可用, disabled:不可用' ,
    `desc` VARCHAR(32) COMMENT '配置描述' ,
    rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '系统配置表 管理系统相关配置项，使用k-v形式';;
ALTER TABLE sys_config ADD UNIQUE idx_config_key(config_Key);;

DROP TABLE IF EXISTS sys_user;;
CREATE TABLE  sys_user(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    `user_name` VARCHAR(32) NOT NULL   COMMENT '用户名' ,
    password VARCHAR(32) NOT NULL   COMMENT '密码 MD5加密' ,
    status VARCHAR(10)    COMMENT '用户状态 enabled：可用, disabled:不可用' ,
    is_Master CHAR(1)    COMMENT '是否为管理员 1管理员，0非管理' ,
    rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '系统用户表 管理系统用户登陆信息';;

-- 此表只有一条记录，有管理台添加
DROP TABLE IF EXISTS local_org;;
CREATE TABLE local_org(
    name VARCHAR(32) NOT NULL COMMENT '机构名称' ,
    identity_id VARCHAR(128)    COMMENT '机构身份标识ID',
    carrier_node_id varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构调度服务node id，入网后可以获取到',
    carrier_IP VARCHAR(32)    COMMENT '调度服务IP地址' ,
    carrier_Port INT    COMMENT '调度服务端口号' ,
    carrier_conn_Status VARCHAR(10)    COMMENT '连接状态 enabled：可用, disabled:不可用' ,
    carrier_conn_Time DATETIME    COMMENT '服务连接时间' ,
    carrier_status VARCHAR(10)    COMMENT '服务状态 enabled：可用, disabled:不可用' ,
    rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) COMMENT = '本地组织信息表';;


-- 此表数据调用调度服务接口获取，rpc GetIdentityList(IdentityListRequest) returns (IdentityListResponse);
DROP TABLE IF EXISTS global_org;;
CREATE TABLE global_org(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    name VARCHAR(32)    COMMENT '机构名称' ,
    identity_id VARCHAR(128)    COMMENT '机构身份标识ID' ,
    carrier_node_id varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织中调度服务的 nodeId',
    status VARCHAR(10)    COMMENT '状态 enabled：可用, disabled:不可用' ,
    rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
    PRIMARY KEY (id),
    UNIQUE (identity_id)
) COMMENT = '全网组织信息表，用于存储从全网同步过来的组织信息数据';;

-- 此表数据有管理台添加
DROP TABLE IF EXISTS local_power_node;;
CREATE TABLE local_power_node
(
    id             INT          NOT NULL AUTO_INCREMENT COMMENT '序号',
    identity_id    VARCHAR(128) NOT NULL COMMENT '组织身份ID',
    power_node_id        VARCHAR(128) COMMENT '发布后底层返回的host唯一ID',
    power_node_name      VARCHAR(32) COMMENT '节点名称(同一个组织不可重复）',
    internal_ip    VARCHAR(32) COMMENT '节点内网IP',
    internal_port  INT COMMENT '节点内网端口',
    external_ip    VARCHAR(32) COMMENT '节点外网IP',
    external_port  INT COMMENT '节点外网端口',
    start_time     DATETIME COMMENT '节点启用时间',
    remarks        VARCHAR(32) COMMENT '节点备注',
    conn_message   VARCHAR(32) COMMENT '节点(连接失败)信息',
    power_id       VARCHAR(128) COMMENT '节点启动后底层返回的算力ID',
    conn_time      DATETIME COMMENT '节点上一次连接时间',
    conn_status    VARCHAR(10)  not null COMMENT '节点状态，-1: 未被调度服务连接上; 0: 连接上; 1: 算力启用<计算服务>; 2: 算力被占用(计算服务算力正在被任务占用)',
    memory         BIGINT       NOT NULL DEFAULT 0 COMMENT '计算host内存, 字节',
    core           INT          NOT NULL DEFAULT 0 COMMENT '计算host core',
    bandwidth      BIGINT       NOT NULL DEFAULT 0 COMMENT '计算host带宽, bps',
    used_memory    BIGINT                DEFAULT 0 COMMENT '使用的内存, 字节',
    used_core      INT                   DEFAULT 0 COMMENT '使用的core',
    used_bandwidth BIGINT                DEFAULT 0 COMMENT '使用的带宽, bps',
    create_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'最后更新时间',
    PRIMARY KEY (id),
    KEY (power_node_id),
	KEY (power_node_name)
) COMMENT = '本组织计算节点配置表 配置当前参与方的计算节点信息';;

-- 计算节点使用资源定时刷新数据
DROP TABLE IF EXISTS local_power_history;;
CREATE TABLE local_power_history
(
    id              INT NOT NULL AUTO_INCREMENT COMMENT '序号',
    power_node_id   VARCHAR(128)       COMMENT '发布后底层返回的host唯一ID',
	refresh_status  VARCHAR(2)         COMMENT '刷新时间标志（0：小时，1：天）',
    used_memory     BIGINT DEFAULT 0   COMMENT '使用的内存, 字节',
    used_core       INT DEFAULT 0      COMMENT '使用的core',
    used_bandwidth  BIGINT DEFAULT 0   COMMENT '使用的带宽, bps',
    create_time     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'最后更新时间',
    PRIMARY KEY (id),
    KEY (power_node_id)
) COMMENT = '计算节点使用资源定时刷新数据表（按时间段刷新）';;

-- 计算节点参与任务信息
DROP TABLE IF EXISTS local_power_join_task;;
CREATE TABLE local_power_join_task
(
    id                INT NOT NULL AUTO_INCREMENT COMMENT '序号',
	power_node_id     VARCHAR(128) COMMENT '计算节点ID',
    task_id           VARCHAR(128) COMMENT '任务id',
	task_name         VARCHAR(32)  COMMENT '任务名称',
    owner_identity_id VARCHAR(128) COMMENT '发起方身份',
    task_start_time   DATETIME     COMMENT '发起时间',
    result_side       VARCHAR(32)  COMMENT '结果方',
	coordinate_side   VARCHAR(32)  COMMENT '协作方',
	used_memory       BIGINT       DEFAULT 0 COMMENT '使用的内存, 字节（占此节点总算力比）',
    used_core         INT          DEFAULT 0 COMMENT '使用的core（占此节点总算力比）',
    used_bandwidth    BIGINT       DEFAULT 0 COMMENT '使用的带宽, bps（占此节点总算力比）',
    create_time       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'最后更新时间',
    PRIMARY KEY (id),
    KEY (power_node_id)
) COMMENT = '计算节点参与任务信息';;

-- 此表数据有管理台添加
DROP TABLE IF EXISTS local_data_node;;
CREATE TABLE local_data_node(
    id int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
    identity_id varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织身份ID',
    node_id varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布后底层返回的host唯一ID',
    host_Name varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点名称',
    internal_IP varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点内部IP',
    internal_Port int(11) DEFAULT NULL COMMENT '节点内部端口',
    external_IP varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点外部IP',
    external_Port int(11) DEFAULT NULL COMMENT '节点外部端口',
    conn_Status varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT '-1' COMMENT '节点连接状态 -1: 未被调度服务连接上; 0: 连接上;',
    conn_message varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点(连接失败)信息',
    conn_Time datetime DEFAULT NULL COMMENT '节点上一次连接时间',
    status varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT 'disabled' COMMENT '节点状态 enabled：可用, disabled:不可用',
    remarks varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点备注',
    rec_create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    rec_update_time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (id)
) COMMENT = '本组织数据节点配置表 配置数据节点相关信息';;


-- 此表数据有管理台添加
-- 是否需要个data_file_hash_id?
-- 数据上架后，回填meta_data_id
DROP TABLE IF EXISTS local_data_file;;
CREATE TABLE local_data_file(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    identity_id VARCHAR(128) NOT NULL COMMENT '组织身份ID',
    file_id varchar(128) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '源文件ID，上传文件成功后返回源文件ID',
    file_name VARCHAR(100) NOT NULL COMMENT '文件名称',
    file_path VARCHAR(100) NOT NULL COMMENT '文件存储路径',
    file_type VARCHAR(20) NOT NULL COMMENT '文件后缀/类型, csv',
    resource_name VARCHAR(100) NOT NULL COMMENT '资源名称',
    size BIGINT NOT NULL DEFAULT 0 COMMENT '文件大小(字节)',
    `rows` BIGINT NOT NULL DEFAULT 0  COMMENT '数据行数(不算title)',
    columns INT NOT NULL DEFAULT 0  COMMENT '数据列数',
    has_title BOOLEAN NOT NULL DEFAULT false comment '是否带标题',
    remarks VARCHAR(100) COMMENT '数据描述',
    status VARCHAR(20) NOT NULL DEFAULT 'created' COMMENT '数据的状态 (created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表)',
    meta_data_id VARCHAR(128) comment '元数据ID,hash',
    rec_create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
	PRIMARY KEY (id),
	UNIQUE KEY (meta_data_id),
	UNIQUE KEY (resource_name)
) COMMENT = '本组织数据文件表';;

-- 此表数据有管理台添加
-- 数据上架后，回填meta_data_id
DROP TABLE IF EXISTS local_meta_data_column;;
CREATE TABLE local_meta_data_column(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    meta_data_id VARCHAR(128)    COMMENT '元数据ID' ,
    column_idx INT    COMMENT '列索引' ,
    column_name VARCHAR(32)    COMMENT '列名' ,
    column_type VARCHAR(32)    COMMENT '列类型' ,
    size BIGINT    COMMENT '列大小（byte）' ,
    remarks VARCHAR(32)    COMMENT '列描述' ,
    visible VARCHAR(6)    COMMENT '是否对外可见 YES:可见，NO:不可见' ,
    rec_create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
    PRIMARY KEY (id),
    UNIQUE KEY (meta_data_id,column_idx)
) COMMENT = '本组织数据文件表列详细表，描述源文件中每一列的列信息';;

-- 此表数据调用调度服务的接口获取，rpc GetMetadataList(MetadataListRequest) returns (MetadataListResponse);
DROP TABLE IF EXISTS global_data_file;;
CREATE TABLE global_data_file(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    identity_id VARCHAR(128) NOT NULL COMMENT '组织身份ID',
    file_id varchar(128) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '源文件ID',
    file_name VARCHAR(100) NOT NULL COMMENT '文件名称',
    file_path VARCHAR(100) NOT NULL COMMENT '文件存储路径',
    file_type VARCHAR(20) NOT NULL COMMENT '文件后缀/类型, csv',
    resource_name VARCHAR(100) NOT NULL COMMENT '资源名称',
    size BIGINT NOT NULL DEFAULT 0 COMMENT '文件大小(字节)',
    `rows` BIGINT NOT NULL DEFAULT 0  COMMENT '数据行数(不算title)',
    columns INT NOT NULL DEFAULT 0  COMMENT '数据列数',
    has_title BOOLEAN NOT NULL DEFAULT false comment '是否带标题',
    remarks VARCHAR(100) COMMENT '数据描述',
    status VARCHAR(20) NOT NULL DEFAULT 'created' COMMENT '数据的状态 (created: 还未发布的新表; released: 已发布的表; revoked: 已撤销的表)',
    meta_data_id VARCHAR(128) comment '元数据ID,hash',
    rec_create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
	PRIMARY KEY (id),
	UNIQUE KEY (meta_data_id),
	UNIQUE KEY (identity_id, resource_name)
) COMMENT = '全网数据文件表';;

-- 此表数据调用调度服务的接口获取，rpc GetMetadataList(MetadataListRequest) returns (MetadataListResponse);
DROP TABLE IF EXISTS global_meta_data_column;;
CREATE TABLE global_meta_data_column(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    meta_data_id VARCHAR(128)    COMMENT '元数据ID' ,
    column_idx INT    COMMENT '列索引' ,
    column_name VARCHAR(32)    COMMENT '列名' ,
    column_type VARCHAR(32)    COMMENT '列类型' ,
    size BIGINT    COMMENT '列大小（byte）' ,
    remarks VARCHAR(32)    COMMENT '列描述' ,
    visible VARCHAR(6)    COMMENT '是否对外可见 YES:可见，NO:不可见' ,
    rec_create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '全网数据文件表列详细表，描述源文件中每一列的列信息';;

-- 此表数据调用调度服务接口获取，rpc ListTask(TaskListRequest) returns (TaskListResponse);
DROP TABLE IF EXISTS task;;
CREATE TABLE `task` (
  `id` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID',
  `task_Name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务名称',
  `owner_Identity_id` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务发起方身份',
  `create_At` datetime DEFAULT NULL COMMENT '任务发起时间',
  `start_At` datetime DEFAULT NULL COMMENT '任务启动时间',
  `auth_At` datetime DEFAULT NULL COMMENT '任务授权时间',
  `auth_Status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务授权状态: pending:等待授权、denied:授权未通过',
  `end_At` datetime DEFAULT NULL COMMENT '任务结束时间',
  `status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务状态 pending: 等在中; running: 计算中; failed: 失败; success: 成功)',
  `duration` datetime DEFAULT NULL COMMENT '任务声明计算时间',
  `cost_core` int(11) DEFAULT '0' COMMENT '任务声明所需CPU',
  `cost_Memory` bigint(20) DEFAULT '0' COMMENT '任务声明所需内存',
  `cost_Bandwidth` bigint(20) DEFAULT '0' COMMENT '任务声明所需带宽',
  `alg_Identity_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '算法提供方身份ID',
  `reviewed` tinyint(1) DEFAULT '0' COMMENT '任务是否被查看过，默认为false(0)',
  `rec_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='全网任务表 用于同步本地任务数据以及全网的相关数据';;

-- 此表数据调用调度服务接口获取，rpc ListTask(TaskListRequest) returns (TaskListResponse);
DROP TABLE IF EXISTS task_event;;
CREATE TABLE task_event (
    ID BIGINT auto_increment NOT NULL comment 'ID',
    task_id VARCHAR(128) NOT NULL comment '任务ID,hash',
    event_type VARCHAR(20) NOT NULL COMMENT '事件类型',
    identity_id VARCHAR(128) NOT NULL COMMENT '产生事件的组织身份ID',
    event_at DATETIME NOT NULL COMMENT '产生事件的时间',
    event_content VARCHAR(512) NOT NULL COMMENT '事件内容',
    rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '任务事件表';;

-- 此表数据调用调度服务接口获取，rpc ListTask(TaskListRequest) returns (TaskListResponse);
DROP TABLE IF EXISTS task_power_provider;;
CREATE TABLE task_power_provider(
    task_id VARCHAR(128) NOT NULL comment '任务ID,hash',
    identity_id VARCHAR(128) NOT NULL COMMENT '算力提供者组织身份ID',
    used_core INT    COMMENT '任务占用CPU信息' ,
    used_memory BIGINT    COMMENT '任务占用内存信息' ,
    used_Bandwidth BIGINT    COMMENT '任务占用带宽信息' ,
    rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
    PRIMARY KEY (task_ID, identity_id)
) COMMENT = '任务算力提供方表 任务数据提供方基础信息';;

-- 此表数据调用调度服务接口获取，rpc ListTask(TaskListRequest) returns (TaskListResponse);
DROP TABLE IF EXISTS task_data_provider;;
CREATE TABLE task_data_provider(
    task_id VARCHAR(128) NOT NULL comment '任务ID,hash',
    identity_id varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据提供者组织身份ID',
    meta_data_id VARCHAR(128) NOT NULL COMMENT '参与任务的元数据ID',
    meta_data_name VARCHAR(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '元数据名称',
    rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
    PRIMARY KEY (task_ID, meta_data_id)
) COMMENT = '任务数据提供方表 存储某个任务数据提供方的信息';;

-- 此表数据调用调度服务接口获取，rpc ListTask(TaskListRequest) returns (TaskListResponse);
DROP TABLE IF EXISTS task_result_consumer;;
CREATE TABLE task_result_consumer (
    task_id VARCHAR(128) NOT NULL comment '任务ID,hash',
    consumer_identity_id VARCHAR(128) NOT NULL COMMENT '结果消费者组织身份ID',
    producer_identity_id VARCHAR(128) NOT NULL COMMENT '结果产生者的组织身份ID',
    rec_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间' ,
    PRIMARY KEY (task_ID, consumer_identity_id, producer_identity_id)
) COMMENT = '任务结果接收方表 任务结果接收方信息';;


-- 此表数据调用调度服务从任务数据快照获取
DROP TABLE IF EXISTS task_org;;
CREATE TABLE `task_org` (
  `task_id` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务ID,hashv',
  `identity_id` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机构身份标识ID',
  `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构名称',
  `carrier_node_id` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织中调度服务的 nodeId',
  `rec_update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`task_id`,`identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务组织信息表，用于存储从调度服务获取的任务数据快照中组织信息数据';

;


-- 此表数据调用调度服务的接口获取，rpc GetPowerList(PowerListRequest) returns (PowerListResponse);
DROP TABLE IF EXISTS global_power;;
CREATE TABLE `global_power` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `identity_id` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '算力提供方身份标识',
  `total_core` int(11) NOT NULL DEFAULT '0' COMMENT '总CPU',
  `total_Memory` bigint(20) NOT NULL DEFAULT '0' COMMENT '总内存',
  `total_Bandwidth` bigint(20) NOT NULL DEFAULT '0' COMMENT '总带宽',
  `used_core` int(11) NOT NULL DEFAULT '0' COMMENT '已使用CPU信息',
  `used_Memory` bigint(20) NOT NULL DEFAULT '0' COMMENT '已使用内存',
  `used_Bandwidth` bigint(20) NOT NULL DEFAULT '0' COMMENT '已使用带宽',
  `rec_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `identity_id` (`identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='全网算力资源表 记录全网的算力资源信息';;


-- 用view来代替, 统计首页需要展示的本组织的相关数据。
CREATE
OR REPLACE VIEW v_local_stats AS SELECT
	carrierNode.carrier_conn_status,
	dataNode.data_node_count,
	powerNode.power_node_count,
	powerStats.total_core,
	powerStats.total_memory,
	powerStats.total_bandwidth,
	powerStats.used_core,
	powerStats.used_memory,
	powerStats.used_bandwidth,
	releasedFile.released_data_file_count,
	unreleasedFile.unreleased_data_file_count,
	runingTask.task_count
FROM
	-- 调度服务状态
	(
		SELECT
			carrier_conn_Status
		FROM
			local_org
	) carrierNode,
	-- 本地数据节点数
	(
		SELECT
			count(id) AS data_node_count
		FROM
			local_data_node
	) dataNode,
	-- 本地计算节点数
	(
		SELECT
			count(id) AS power_node_count
		FROM
			local_power_node
	) powerNode,
	-- 本地算力统计数
	(
		SELECT
			sum(core) AS total_core,
			sum(memory) AS total_memory,
			sum(bandwidth) AS total_bandwidth,
			sum(used_core) AS used_core,
			sum(used_memory) AS used_memory,
			sum(used_bandwidth) AS used_bandwidth
		FROM
			local_power_node
	) powerStats,
	-- 本地已上架数据文件数
	(
		SELECT
			count(id) AS released_data_file_count
		FROM
			local_data_file
		WHERE
			STATUS = 'released'
	) releasedFile,
	-- 本地未上架数据文件数
	(
		SELECT
			count(id) AS unreleased_data_file_count
		FROM
			local_data_file
		WHERE
			STATUS != 'released'
	) unreleasedFile,
	-- 本组织算力参与的正在运行的任务
	(
		SELECT
			count(task_id) task_count
		FROM
			local_power_join_task
		GROUP BY
			task_id
	) runingTask;

;

DELIMITER ;
