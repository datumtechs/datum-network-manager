package com.platon.rosettanet.admin.service;

import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;

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
     * 根据nodeId查询计算节点详情
     * @param nodeId
     * @return
     */
    LocalPowerNode selectPowerDetailByNodeId(String nodeId);

    /**
     * 根据id删除计算节点
     * @param id
     * @return
     */
    int deletePowerNodeByNodeId(String id);


}
