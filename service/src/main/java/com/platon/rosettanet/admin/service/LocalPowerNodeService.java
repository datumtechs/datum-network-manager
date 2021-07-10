package com.platon.rosettanet.admin.service;

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
     * 根据nodeId查询计算节点详情
     * @param powerNodeId
     * @return
     */
    LocalPowerNode selectPowerDetailByNodeId(String powerNodeId);

    /**
     * 根据组织机构id查询计算节点服务列表
     * @param identityId
     * @return
     */
    List<LocalPowerNode> selectPowerListByIdentityId(String identityId);




}
