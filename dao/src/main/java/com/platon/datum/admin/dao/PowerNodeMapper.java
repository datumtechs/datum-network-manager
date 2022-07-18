package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.PowerNode;
import com.platon.datum.admin.dao.entity.PowerLoad;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author houz
 * 业务节点实体类
 */
@Repository
public interface PowerNodeMapper {

    /**
     * 插入计算节点数据
     * @param powerNode
     * @return
     */
    int insertPowerNode(PowerNode powerNode);

    /**
     * 根据节点id修改计算节点
     * @param powerNode
     * @return
     */
    int updatePowerNodeByNodeId(PowerNode powerNode);

    /**
     * 初始化新计算节点的基本信息，如果相同的key则更新基本信息（不包括节点名称）
     * @param powerNodeList
     * @return
     */
    int initNewPowerNodeBatch(List<PowerNode> powerNodeList);

    /**
     * 批量修改计算节点的资源信息
     * @param powerNodeList
     * @return
     */
    int updateResourceInfoBatchByNodeIdAndPowerId(List<PowerNode> powerNodeList);


    /**
     * 根据计算id删除计算节点
     * @param nodeId
     * @return
     */
    int deletePowerNode(@Param("nodeId") String nodeId);

    /**
     * 查询计算节点详情
     * @param nodeId
     * @return
     */
    PowerNode queryPowerNodeDetails(@Param("nodeId") String nodeId);

    /**
     * 查询计算节点列表
     * @param keyword
     * @return
     */
    List<PowerNode> queryPowerNodeList(@Param("keyword") String keyword);

    /**
     * 根据状态查询计算节点列表
     * @param status
     * @return
     */
    List<PowerNode> queryPowerNodeListByStatus(@Param("status") Integer status);


    PowerLoad getCurrentLocalPowerLoadByNodeId(@Param("nodeId") String nodeId);

    PowerNode findLocalPowerNodeByName(@Param("nodeName") String nodeName);

    void updateLocalPowerNodeName(@Param("nodeId") String nodeId, @Param("nodeName") String nodeName);
}