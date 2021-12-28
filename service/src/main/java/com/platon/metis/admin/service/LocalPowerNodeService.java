package com.platon.metis.admin.service;

import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.LocalPowerLoadSnapshot;
import com.platon.metis.admin.dao.entity.LocalPowerNode;
import com.platon.metis.admin.dao.entity.PowerLoad;

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
    void insertPowerNode(LocalPowerNode localPowerNode);

    /**
     * 修改计算节点数据
     * @param localPowerNode
     * @return
     */
    void updatePowerNodeByNodeId(LocalPowerNode localPowerNode);

    /**
     * 根据节点id删除计算节点
     * @param powerNodeId
     * @return
     */
    void deletePowerNodeByNodeId(String powerNodeId);

    /**
     * 查询计算节点详情
     * @param powerNodeId
     * @return
     */
    LocalPowerNode findPowerNodeDetails(String powerNodeId);

    /**
     * 查询计算节点服务列表
     * @param identityId
     * @param keyword
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<LocalPowerNode> listPowerNode(String identityId, String keyword, int pageNumber, int pageSize);

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
     * 计算节点名称校验
     * @param powerNodeName
     * @return
     */
    void checkPowerNodeName(String powerNodeName);


    List<LocalPowerLoadSnapshot> listLocalPowerLoadSnapshotByPowerNodeId(String powerNodeId, int hours);

    PowerLoad getCurrentLocalPowerLoadByPowerNodeId(String powerNodeId);
}