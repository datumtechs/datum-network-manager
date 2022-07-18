package com.platon.datum.admin.service;

import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.entity.SeedNode;

/**
 * @author houz
 * 种子节点业务接口
 */
public interface SeedNodeService {

    /**
     * 插入种子节点数据
     * @param seedNode
     * @return
     */
    void insertSeedNode(SeedNode seedNode);

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
    SeedNode querySeedNodeDetails(String seedNodeId);

    /**
     * 查询种子节点列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<SeedNode> listSeedNode(int pageNumber, int pageSize);

    /**
     * 种子节点名称校验
     * @param seedNodeId
     * @return
     */
    void checkSeedNodeId(String seedNodeId);


}
