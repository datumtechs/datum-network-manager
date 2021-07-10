package com.platon.rosettanet.admin.service;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.DataNode;

/**
 * @author lyf
 * @Description 数据节点service层
 * @date 2021/7/8 17:29
 */

public interface DataNodeService {
    /**
     * @param pageNumber 起始页号
     * @param pageSize   每页数据条数
     * @param keyword    搜索关键字
     * @return DataNode
     */
    Page<DataNode> listNode(Integer pageNumber, Integer pageSize, String keyword);

    int addDataNode(DataNode dataNode);

    boolean checkDataNodeName(DataNode dataNode);

    int updateDataNode(DataNode dataNode);

    int deleteDataNode(String nodeId);
}
