package com.platon.rosettanet.admin.service;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.LocalSeedNode;

import java.util.List;

/**
 * @author houz
 * 种子节点业务接口
 */
public interface LocalSeedNodeService {

    /**
     * 插入种子节点数据
     * @param localSeedNode
     * @return
     */
    void insertSeedNode(LocalSeedNode localSeedNode);

    /**
     * 修改种子节点
     * @param localSeedNode
     * @return
     */
    void updateSeedNode(LocalSeedNode localSeedNode);

    /**
     * 批量修改种子节点
     * @param localSeedNodeList
     * @return
     */
    void updateSeedNodeBatch(List<LocalSeedNode> localSeedNodeList);

    /**
     * 删除种子节点
     * @param seedNodeId
     * @return
     */
    void deleteSeedNode(String seedNodeId);

    /**
     * 查询种子节点详情
     * @param seedNodeId
     * @return
     */
    LocalSeedNode querySeedNodeDetails(String seedNodeId);

    /**
     * 查询种子节点列表
     * @param keyWord
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<LocalSeedNode> querySeedNodeList(String keyWord, int pageNumber, int pageSize);

    /**
     * 种子节点名称校验
     * @param seedNodeName
     * @return
     */
    void checkSeedNodeName(String seedNodeName);


}
