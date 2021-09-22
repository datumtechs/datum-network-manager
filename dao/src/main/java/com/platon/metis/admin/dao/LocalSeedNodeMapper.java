package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.LocalSeedNode;
import org.springframework.stereotype.Repository;

import java.util.List;


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
     * 修改种子节点
     * @param localSeedNode
     * @return
     */
    int updateSeedNode(LocalSeedNode localSeedNode);

    /**
     * 批量修改种子节点
     * @param localSeedNodeList
     * @return
     */
    int updateSeedNodeBatch(List<LocalSeedNode> localSeedNodeList);

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

    /**
     * 查询种子节点列表
     * @param keyWord
     * @return
     */
    List<LocalSeedNode> querySeedNodeList(String keyWord);

    /**
     * 种子节点名称校验
     * @param seedNodeName
     * @return
     */
    int checkSeedNodeName(String seedNodeName);

}