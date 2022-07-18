package com.platon.datum.admin.service;

import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.entity.PowerLoadSnapshot;
import com.platon.datum.admin.dao.entity.PowerNode;
import com.platon.datum.admin.dao.entity.PowerLoad;

import java.util.List;

/**
 * @author houz
 * 计算节点业务接口
 */
public interface PowerNodeService {

    /**
     * 插入计算节点数据
     * @param powerNode
     * @return
     */
    void insertPowerNode(PowerNode powerNode);

    /**
     * 修改计算节点数据
     * @param powerNode
     * @return
     */
    void updatePowerNodeByNodeId(PowerNode powerNode);

    /**
     * 根据节点id删除计算节点
     * @param nodeId
     * @return
     */
    void deletePowerNodeByNodeId(String nodeId);

    /**
     * 查询计算节点详情
     * @param powerNodeId
     * @return
     */
    PowerNode findPowerNodeDetails(String powerNodeId);

    /**
     * 查询计算节点服务列表
     * @param identityId
     * @param keyword
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<PowerNode> listPowerNode(String identityId, String keyword, int pageNumber, int pageSize);

    /**
     * 启用算力
     * @param nodeId
     */
    void publishPower(String nodeId);

    /**
     * 停用算力
     * @param nodeId
     */
    void revokePower(String nodeId);

    List<PowerLoadSnapshot> listLocalPowerLoadSnapshotByNodeId(String nodeId, int hours);

    PowerLoad getCurrentLocalPowerLoadByNodeId(String nodeId);

    PowerNode findLocalPowerNodeByName(String nodeName);

    void updateLocalPowerNodeName(String nodeId, String nodeName);
}