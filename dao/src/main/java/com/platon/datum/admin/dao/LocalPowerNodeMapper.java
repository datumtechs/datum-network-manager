package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.LocalPowerNode;
import com.platon.datum.admin.dao.entity.PowerLoad;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author houz
 * 业务节点实体类
 */
@Repository
public interface LocalPowerNodeMapper {

    /**
     * 插入计算节点数据
     * @param localPowerNode
     * @return
     */
    int insertPowerNode(LocalPowerNode localPowerNode);

    /**
     * 根据节点id修改计算节点
     * @param localPowerNode
     * @return
     */
    int updatePowerNodeByNodeId(LocalPowerNode localPowerNode);

    /**
     * 初始化新计算节点的基本信息，如果相同的key则更新基本信息（不包括节点名称）
     * @param localPowerNodeList
     * @return
     */
    int initNewPowerNodeBatch(List<LocalPowerNode> localPowerNodeList);

    /**
     * 批量修改计算节点的资源信息
     * @param localPowerNodeList
     * @return
     */
    int updateResourceInfoBatchByNodeId(List<LocalPowerNode> localPowerNodeList);


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
    LocalPowerNode queryPowerNodeDetails(@Param("nodeId") String nodeId);

    /**
     * 查询计算节点列表
     * @param keyword
     * @return
     */
    List<LocalPowerNode> queryPowerNodeList(@Param("keyword") String keyword);

    /**
     * 根据状态查询计算节点列表
     * @param status
     * @return
     */
    List<LocalPowerNode> queryPowerNodeListByStatus(@Param("status") Integer status);


    PowerLoad getCurrentLocalPowerLoadByNodeId(@Param("nodeId") String nodeId);

    LocalPowerNode findLocalPowerNodeByName(@Param("nodeName") String nodeName);

    void updateLocalPowerNodeName(@Param("nodeId") String nodeId, @Param("nodeName") String nodeName);
}