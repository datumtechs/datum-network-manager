package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
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
     * 根据计算节点是否被占用
     * @param powerNodeId
     * @return
     */
    int queryPowerNodeUsing(String powerNodeId);

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
     * @return
     */
    List<LocalPowerNode> queryPowerNodeList(@Param(value = "identityId")String identityId, @Param(value = "keyword")String keyword);

    /**
     * 计算节点名称校验
     * @param powerNodeName
     * @return
     */
    int checkPowerNodeName(String powerNodeName);

}