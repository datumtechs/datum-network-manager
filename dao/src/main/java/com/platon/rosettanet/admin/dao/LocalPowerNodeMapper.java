package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import org.springframework.stereotype.Repository;


/**
 * @author houz
 * 业务节点实体类
 */
@Repository
public interface LocalPowerNodeMapper {

    /**
     * 插入计算节点数据
     * @param localComputeNode
     * @return
     */
    int insertPowerNode(LocalPowerNode localComputeNode);

    /**
     * 根据节点id修改计算节点数据
     * @param localComputeNode
     * @return
     */
    int updatePowerNodeByNodeId(LocalPowerNode localComputeNode);

    /**
     * 根据id修改计算节点数据
     * @param localComputeNode
     * @return
     */
    int updatePowerNodeById(LocalPowerNode localComputeNode);


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