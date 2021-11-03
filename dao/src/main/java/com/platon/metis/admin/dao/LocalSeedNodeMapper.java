package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.LocalSeedNode;
import org.springframework.stereotype.Repository;


/**
 * @author houz
 * 种子节点mapper类
 */
@Repository
public interface LocalSeedNodeMapper {

    /**
     * 插入种子节点数据
     * @param localSeedNode
     * @return
     */
    int insertSeedNode(LocalSeedNode localSeedNode);


    /**
     * 删除种子节点
     * @param seedNodeId
     * @return
     */
    int deleteSeedNode(String seedNodeId);

    /**
     * 查询种子节点详情
     * @param seedNodeId
     * @return
     */
    LocalSeedNode querySeedNodeDetails(String seedNodeId);


    void listSeedNode();
}