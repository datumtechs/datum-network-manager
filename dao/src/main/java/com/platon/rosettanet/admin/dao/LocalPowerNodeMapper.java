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
     * 根据id删除计算节点
     * @param powerNodeId
     * @return
     */
    int deletePowerNode(String powerNodeId);

    /**
     * 根据powerNodeId查询计算节点详情
     * @param powerNodeId
     * @return
     */
    LocalPowerNode selectPowerDetailByNodeId(String powerNodeId);

    /**
     * 查询计算节点服务列表
     * @param identityId
     * @param keyword
     * @return
     */
    List<LocalPowerNode> queryPowerNodeList(@Param(value = "identityId")String identityId, @Param(value = "keyword")String keyword);


}