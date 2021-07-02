CREATE DATABASE  IF NOT EXISTS rosetta_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use rosetta_admin;
/*
Navicat MySQL Data Transfer

Source Server         : 192.168.9.191
Source Server Version : 50724
Source Host           : 192.168.9.191:3306
Source Database       : token_juweb

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2021-06-20 17:58:18
*/

SET FOREIGN_KEY_CHECKS=0;

DELIMITER ;;

DROP TABLE IF EXISTS tb_base_config;;/*SkipError*/
CREATE TABLE tb_base_config(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    name VARCHAR(32)    COMMENT '机构身份名称' ,
    identity_Id VARCHAR(32)    COMMENT '机构身份标识' ,
    node_Id VARCHAR(32)    COMMENT '调度服务ID' ,
    status CHAR(1)    COMMENT '调度服务状态 N已启动，D未启动' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '系统基本信息表 存储系统的基本数据信息';;

ALTER TABLE tb_base_config COMMENT '系统基本信息表';;
DROP TABLE IF EXISTS tb_sys_config;;/*SkipError*/
CREATE TABLE tb_sys_config(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    config_Key VARCHAR(32)    COMMENT '配置项' ,
    config_Value VARCHAR(32)    COMMENT '配置值' ,
    status VARCHAR(32) NOT NULL  DEFAULT 'N' COMMENT '配置状态 N可用，D不可用' ,
    `desc` VARCHAR(32) COMMENT '配置描述' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '系统配置表 管理系统相关配置项，使用k-v形式';;

ALTER TABLE tb_sys_config ADD UNIQUE idx_config_key(config_Key);;
ALTER TABLE tb_sys_config COMMENT '系统配置表';;
DROP TABLE IF EXISTS tb_sys_user;;/*SkipError*/
CREATE TABLE tb_sys_user(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    `user_name` VARCHAR(32) NOT NULL   COMMENT '用户名' ,
    password VARCHAR(32) NOT NULL   COMMENT '密码 MD5加密' ,
    status CHAR(1)    COMMENT '用户状态 N可用，D不可用' ,
    is_Master CHAR(1)    COMMENT '是否为管理员 1管理员，0非管理' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '系统用户表 管理系统用户登陆信息';;

ALTER TABLE tb_sys_user COMMENT '系统用户表';;
DROP TABLE IF EXISTS tb_power_node;;/*SkipError*/
CREATE TABLE tb_power_node(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    node_Id VARCHAR(32)    COMMENT '节点ID' ,
    node_Name VARCHAR(32)    COMMENT '节点名称 MD5加密' ,
    conn_Status VARCHAR(32)    COMMENT '节点状态 N连接成功、D连接失败' ,
    power_Status VARCHAR(32)    COMMENT '算力状态 N启用，D未启用' ,
    reason VARCHAR(32)    COMMENT '节点连接失败原因' ,
    internal_IP VARCHAR(32)    COMMENT '节点内网IP' ,
    internal_Port INT    COMMENT '节点内网端口' ,
    external_IP VARCHAR(32)    COMMENT '节点外网IP' ,
    external_Port INT    COMMENT '节点外网端口' ,
    start_Time DATETIME    COMMENT '节点启动时间' ,
    `desc` VARCHAR(32)    COMMENT '节点备注' ,
    create_Time DATETIME    COMMENT '创建时间' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '计算节点配置表 配置当前参与方的计算节点信息';;

ALTER TABLE tb_power_node COMMENT '计算节点配置表';;
DROP TABLE IF EXISTS tb_carrier_node;;/*SkipError*/
CREATE TABLE tb_carrier_node(
    Id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    name VARCHAR(32)    COMMENT '机构识别名称' ,
    identity_Id VARCHAR(32)    COMMENT '机构身份标识' ,
    node_Id VARCHAR(32)    COMMENT '机构节点ID' ,
    carrier_IP VARCHAR(32)    COMMENT '调度服务IP地址' ,
    carrier_Port INT    COMMENT '调度服务端口号 MD5加密' ,
    conn_Status CHAR(1)    COMMENT '连接状态 N已连接，D未连接' ,
    carrier_Status CHAR(1)    COMMENT '服务状态 N可用，D不可用' ,
    `desc` VARCHAR(32)    COMMENT '备注' ,
    conn_Time DATETIME    COMMENT '服务连接时间' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (Id)
) COMMENT = '调度服务配置表 存储调度服务的相关配置信息以及状态';;

ALTER TABLE tb_carrier_node COMMENT '调度服务配置表';;
DROP TABLE IF EXISTS tb_data_node;;/*SkipError*/
CREATE TABLE tb_data_node(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    node_Id VARCHAR(32)    COMMENT '节点ID' ,
    node_Name VARCHAR(32)    COMMENT '节点名称' ,
    internal_IP VARCHAR(32)    COMMENT '节点内部IP' ,
    internal_Port INT    COMMENT '节点内部端口' ,
    external_IP VARCHAR(32)    COMMENT '节点外部IP' ,
    external_Port INT    COMMENT '节点外部端口' ,
    conn_Status CHAR(1)    COMMENT '节点连接状态 N已连接，D未连接' ,
    status CHAR(1)   DEFAULT 'N' COMMENT '节点状态 N可用，D不可用' ,
    last_conn_Time DATETIME    COMMENT '节点上一次连接时间' ,
    `desc` VARCHAR(32)    COMMENT '节点备注' ,
    create_Time DATETIME    COMMENT '创建时间' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '数据节点配置表 配置数据节点相关信息';;

ALTER TABLE tb_data_node COMMENT '数据节点配置表';;
DROP TABLE IF EXISTS tb_bootstrap_node;;/*SkipError*/
CREATE TABLE tb_bootstrap_node(
     id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
     last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '种子节点配置表';;

ALTER TABLE tb_bootstrap_node COMMENT '种子节点配置表';;
DROP TABLE IF EXISTS tb_power_node_overview;;/*SkipError*/
CREATE TABLE tb_power_node_overview(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    node_Id VARCHAR(32)    COMMENT '节点ID' ,
    total_Processor INT    COMMENT '节点提供总CPU核数' ,
    total_Mem BIGINT    COMMENT '节点提供的总内存(byte)' ,
    total_Bandwidth BIGINT    COMMENT '节点提供的总带宽（byte）' ,
    `desc` VARCHAR(32)    COMMENT '节点备注' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '计算节点算力表 记录各计算节点的算力信息';;

ALTER TABLE tb_power_node_overview COMMENT '计算节点算力表';;
DROP TABLE IF EXISTS tb_local_metadata;;/*SkipError*/
CREATE TABLE tb_local_metadata(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    metadata_Id VARCHAR(32)    COMMENT '元数据ID' ,
    metadata_Name VARCHAR(32)    COMMENT '元数据名称' ,
    origin_Id VARCHAR(32)    COMMENT '源文件ID' ,
    origin_Name VARCHAR(32)    COMMENT '源文件名称' ,
    `state` VARCHAR(32)    COMMENT '元数据状态 create: 还未发布的新表; release: 已发布的表; revoke: 已撤销的表' ,
    file_Path VARCHAR(32)    COMMENT '源文件路径' ,
    `rows` INT    COMMENT '源文件行数' ,
    columns INT    COMMENT '源文件列数' ,
    `size` BIGINT    COMMENT '源文件大小' ,
    file_Type VARCHAR(32)    COMMENT '源文件类型' ,
    has_Title CHAR(1)    COMMENT '源是否包含标题 N是，D否' ,
    identity_Id VARCHAR(32)    COMMENT '元数据发布者身份ID' ,
    node_Name VARCHAR(32)    COMMENT '元数据发布者名称' ,
    `desc` VARCHAR(32)    COMMENT '元数据描述' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '元数据表 记录本地添加的元数据信息';;

ALTER TABLE tb_local_metadata COMMENT '元数据表';;
DROP TABLE IF EXISTS tb_local_metadata_column;;/*SkipError*/
CREATE TABLE tb_local_metadata_column(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    metadata_Id VARCHAR(32)    COMMENT '元数据ID' ,
    cindex INT    COMMENT '列索引' ,
    cname VARCHAR(32)    COMMENT '列名' ,
    ctype VARCHAR(32)    COMMENT '列类型' ,
    csize BIGINT    COMMENT '列大小（byte）' ,
    ccomment VARCHAR(32)    COMMENT '列描述' ,
    visible CHAR(1)    COMMENT '是否对外可见 N可见，D不可见' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '元数据列详细表 记录元数据对应的列信息';;

ALTER TABLE tb_local_metadata_column COMMENT '元数据列详细表';;
DROP TABLE IF EXISTS tb_origin_data;;/*SkipError*/
CREATE TABLE tb_origin_data(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    origin_Id VARCHAR(32)    COMMENT '源数据ID' ,
    origin_Name VARCHAR(32)    COMMENT '源数据名称' ,
    file_Path VARCHAR(32)    COMMENT '存储路径' ,
    file_Type VARCHAR(32)    COMMENT '源文件类型' ,
    `size` BIGINT    COMMENT '源文件大小' ,
    has_Title CHAR(1)    COMMENT '是否含有表头 N有，D没有' ,
    `desc` VARCHAR(32)    COMMENT '数据描述' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '源数据表 记录存储的源数据的信息';;

ALTER TABLE tb_origin_data COMMENT '源数据表';;
DROP TABLE IF EXISTS tb_task;;/*SkipError*/
CREATE TABLE tb_task(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    task_Id VARCHAR(32)    COMMENT '任务ID' ,
    task_Name VARCHAR(32)    COMMENT '任务名称' ,
    owner_Name VARCHAR(32)    COMMENT '任务发起方名称' ,
    owner_Identity VARCHAR(32)    COMMENT '任务发起方身份ID' ,
    create_At DATETIME    COMMENT '任务发起时间' ,
    start_At DATETIME    COMMENT '任务启动时间' ,
    auth_At DATETIME    COMMENT '任务授权时间' ,
    auth_Status VARCHAR(32)    COMMENT '任务授权状态 等待授权、授权未通过' ,
    end_At DATETIME    COMMENT '任务结束时间' ,
    `state` VARCHAR(32)    COMMENT '任务状态 pending: 等在中; running: 计算中; failed: 失败; success: 成功)' ,
    duration DATETIME    COMMENT '任务声明计算时间' ,
    cost_Processor INT    COMMENT '任务声明所需CPU' ,
    cost_Mem BIGINT    COMMENT '任务声明所需内存' ,
    cost_Bandwidth BIGINT    COMMENT '任务声明所需带宽' ,
    alog_Supplier_Identity VARCHAR(32)    COMMENT '算法提供方身份ID' ,
    alog_Supplier_Name VARCHAR(32)    COMMENT '算法提供方名称' ,
    seen CHAR(1)    COMMENT '任务是否被查看过 N看过，D未看过' ,
    create_Time DATETIME    COMMENT '创建时间' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '任务表 用于同步本地任务数据以及全网的相关数据';;

ALTER TABLE tb_task COMMENT '任务表';;
DROP TABLE IF EXISTS tb_task_event;;/*SkipError*/
CREATE TABLE tb_task_event(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    task_Id VARCHAR(32)    COMMENT '任务ID' ,
    task_Name VARCHAR(32)    COMMENT '任务名称' ,
    `type` VARCHAR(32)    COMMENT '事件类型' ,
    content VARCHAR(32)    COMMENT '事件内容' ,
    create_At DATETIME    COMMENT '事件产生时间' ,
    owner_Identity VARCHAR(32)    COMMENT '产生事件的节点身份ID' ,
    owner_Name VARCHAR(32)    COMMENT '产生事件的节点名称' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '任务事件表';;

ALTER TABLE tb_task_event COMMENT '任务事件表';;
DROP TABLE IF EXISTS tb_task_power_provider;;/*SkipError*/
CREATE TABLE tb_task_power_provider(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    task_Id VARCHAR(32)    COMMENT '任务ID' ,
    task_Name VARCHAR(32)    COMMENT '任务名称' ,
    owner_Name VARCHAR(32)    COMMENT '算力提供方名称' ,
    owner_Identity VARCHAR(32)    COMMENT '算力提供方身份标识' ,
    used_Processor INT    COMMENT '任务占用CPU信息' ,
    used_Mem BIGINT    COMMENT '任务占用内存信息' ,
    used_Bandwidth BIGINT    COMMENT '任务占用带宽信息' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '任务算力提供方表 任务数据提供方基础信息';;

ALTER TABLE tb_task_power_provider COMMENT '任务算力提供方表';;
DROP TABLE IF EXISTS tb_task_data_provider;;/*SkipError*/
CREATE TABLE tb_task_data_provider(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    task_Id VARCHAR(32)    COMMENT '任务ID' ,
    task_Name VARCHAR(32)    COMMENT '任务名称' ,
    owner_Identity VARCHAR(32)    COMMENT '数据提供方身份标识' ,
    owner_Name VARCHAR(32)    COMMENT '数据提供方名称' ,
    metadata_Id VARCHAR(32)    COMMENT '提供的元数据ID' ,
    metadata_Name VARCHAR(32)    COMMENT '提供的元数据名称' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '任务数据提供方表 存储某个任务数据提供方的信息';;

ALTER TABLE tb_task_data_provider COMMENT '任务数据提供方表';;
DROP TABLE IF EXISTS tb_task_result_receiver;;/*SkipError*/
CREATE TABLE tb_task_result_receiver(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    task_Id VARCHAR(32)    COMMENT '任务ID' ,
    task_Name VARCHAR(32)    COMMENT '任务名称' ,
    owner_Identity VARCHAR(32)    COMMENT '结果接收方身份标识' ,
    owner_Name VARCHAR(32)    COMMENT '结果接收方名称' ,
    receive_Time DATETIME    COMMENT '结果接收时间' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '任务结果接收方表 任务结果接收方信息';;

ALTER TABLE tb_task_result_receiver COMMENT '任务结果接收方表';;
DROP TABLE IF EXISTS tb_organization;;/*SkipError*/
CREATE TABLE tb_organization(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    name VARCHAR(32)    COMMENT '参与方名称' ,
    `identity` VARCHAR(32)    COMMENT '参与方身份标识' ,
    node_Id VARCHAR(32)    COMMENT '参与方服务节点ID' ,
    status VARCHAR(32)    COMMENT '参与方状态' ,
    auth_Info VARCHAR(32)    COMMENT '参与方鉴权信息' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '全网组织信息表 用于存储从全网同步过来的组织信息数据';;

ALTER TABLE tb_organization COMMENT '全网组织信息表';;
DROP TABLE IF EXISTS tb_global_metadata;;/*SkipError*/
CREATE TABLE tb_global_metadata(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    metadata_Id VARCHAR(32)    COMMENT '元数据ID' ,
    metadata_Name VARCHAR(32)    COMMENT '元数据名称' ,
    origin_Id VARCHAR(32)    COMMENT '源文件ID' ,
    status VARCHAR(32)    COMMENT '元数据状态 D未发布、N已发布' ,
    file_Path VARCHAR(32)    COMMENT '源文件路径' ,
    `rows` VARCHAR(32)    COMMENT '源文件行数' ,
    columns VARCHAR(32)    COMMENT '源文件列数' ,
    `size` VARCHAR(32)    COMMENT '源文件大小' ,
    file_Type VARCHAR(32)    COMMENT '源文件类型' ,
    has_Title VARCHAR(32)    COMMENT '源是否包含标题' ,
    identity_Id VARCHAR(32)    COMMENT '元数据发布者身份ID' ,
    node_Name VARCHAR(32)    COMMENT '元数据发布者名称' ,
    `desc` VARCHAR(32)    COMMENT '元数据描述' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '全网元数据表 记录全网添加的元数据信息';;

ALTER TABLE tb_global_metadata COMMENT '全网元数据表';;
DROP TABLE IF EXISTS tb_global_metadata_column;;/*SkipError*/
CREATE TABLE tb_global_metadata_column(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    metadata_Id VARCHAR(32)    COMMENT '元数据ID' ,
    cindex INT    COMMENT '列索引' ,
    cname VARCHAR(32)    COMMENT '列名' ,
    ctype VARCHAR(32)    COMMENT '列类型' ,
    csize BIGINT    COMMENT '列大小（byte）' ,
    ccomment VARCHAR(32)    COMMENT '列描述' ,
    visible CHAR(1)    COMMENT '是否对外可见 N可见，D不可见' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '全网元数据列详细表 记录全网元数据对应的列信息';;

ALTER TABLE tb_global_metadata_column COMMENT '全网元数据列详细表';;
DROP TABLE IF EXISTS tb_global_power;;/*SkipError*/
CREATE TABLE tb_global_power(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    identity_Id VARCHAR(32)    COMMENT '算力提供方身份标识' ,
    node_Name VARCHAR(32)    COMMENT '算力提供方名称' ,
    `state` VARCHAR(32)    COMMENT '算力状态 create: 还未发布的算力; release: 已发布的算力; revoke: 已撤销的算力' ,
    used_Processor INT    COMMENT '已使用CPU信息' ,
    used_Mem BIGINT    COMMENT '已使用内存' ,
    used_Bandwidth BIGINT    COMMENT '已使用带宽' ,
    total_Processor INT    COMMENT '总CPU' ,
    total_Mem BIGINT    COMMENT '总内存' ,
    total_Bandwidth BIGINT    COMMENT '总带宽' ,
    last_Update_Time DATETIME    COMMENT '最后更新时间' ,
    PRIMARY KEY (id)
) COMMENT = '全网算力资源表 记录全网的算力资源信息';;

ALTER TABLE tb_global_power COMMENT '全网算力资源表';;
DROP TABLE IF EXISTS tb_power_statistic;;/*SkipError*/
CREATE TABLE tb_power_statistic(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '序号' ,
    identity_Id VARCHAR(32)    COMMENT '算力提供方身份标识' ,
    used_Processor INT    COMMENT '已使用CPU信息' ,
    used_Mem BIGINT    COMMENT '已使用内存' ,
    used_Bandwidth BIGINT    COMMENT '已使用带宽' ,
    create_Time DATETIME    COMMENT '统计时间' ,
    PRIMARY KEY (id)
) COMMENT = '算力统计表 算力统计，详细记录每个时期使用的CPU/内存/带宽等数据。';;

ALTER TABLE tb_power_statistic COMMENT '算力统计表';;

DELIMITER ;
