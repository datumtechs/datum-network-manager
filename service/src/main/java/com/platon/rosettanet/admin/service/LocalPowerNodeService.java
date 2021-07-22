package com.platon.rosettanet.admin.service;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;

import java.util.List;

/**
 * @author houz
 * 计算节点业务接口
 */
public interface LocalPowerNodeService {

    /**
     * 插入计算节点数据
     * @param localPowerNode
     * @return
     */
    int insertPowerNode(LocalPowerNode localPowerNode);

    /**
     * 修改计算节点数据
     * @param localPowerNode
     * @return
     */
    int updatePowerNodeByNodeId(LocalPowerNode localPowerNode);

    /**
     * 根据节点id删除计算节点
     * @param powerNodeId
     * @return
     */
    int deletePowerNodeByNodeId(String powerNodeId);

    /**
     * 查询计算节点详情
     * @param powerNodeId
     * @return
     */
    LocalPowerNode queryPowerNodeDetails(String powerNodeId);

    /**
     * 查询计算节点服务列表
     * @param identityId
     * @param keyword
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page queryPowerNodeList(String identityId, String keyword, int pageNumber, int pageSize);

    /**
     * 启用算力
     * @param powerNodeId
     */
    void publishPower(String powerNodeId);

    /**
     * 停用算力
     * @param powerNodeId
     */
    void revokePower(String powerNodeId);

    /**
     * 查询计算节点使用历史记录
     * @param powerNodeId
     * @return
     */
    List queryPowerNodeUseHistory(String powerNodeId, String resourceType, String timeType);

    /**
     * 查询计算节点参数的任务列表
     * @param powerNodeId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page queryPowerJoinTaskList(String powerNodeId, int pageNumber, int pageSize);


    /**
     * 计算节点名称校验
     * @param powerNodeName
     * @return
     */
    int checkPowerNodeName(String powerNodeName);


}
