package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.LocalPowerNode;
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
     * 批量修改计算节点
     * @param localPowerNodeList
     * @return
     */
    int batchUpdatePowerNode(List<LocalPowerNode> localPowerNodeList);

    /**
     * 根据id修改计算节点数据
     * @param localPowerNode
     * @return
     */
    int updatePowerNodeById(LocalPowerNode localPowerNode);

    /**
     * 根据计算id删除计算节点
     * @param powerNodeId
     * @return
     */
    int deletePowerNode(String powerNodeId);

    /**
     * 查询计算节点详情
     * @param powerNodeId
     * @return
     */
    LocalPowerNode queryPowerNodeDetails(String powerNodeId);

    /**
     * 查询计算节点列表
     * @param keyword
     * @return
     */
    List<LocalPowerNode> queryPowerNodeList(String keyword);

    /**
     * 计算节点名称校验
     * @param powerNodeName
     * @return
     */
    int checkPowerNodeName(String powerNodeName);

}