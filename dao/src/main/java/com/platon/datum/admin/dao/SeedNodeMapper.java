package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.SeedNode;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author houz
 * 种子节点mapper类
 */
@Repository
public interface SeedNodeMapper {

    /**
     * 插入种子节点数据
     * @param seedNode
     * @return
     */
    int insertSeedNode(SeedNode seedNode);


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
    SeedNode querySeedNodeDetails(String seedNodeId);


    List<SeedNode> listSeedNode();
    void deleteNotInitialized();
    void insertBatch(List<SeedNode> seedNodeList);
}