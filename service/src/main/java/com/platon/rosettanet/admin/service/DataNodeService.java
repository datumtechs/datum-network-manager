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

    /**
     * 新增数据节点
     *
     * @param dataNode
     * @return
     * @throws Exception
     */
    int addDataNode(DataNode dataNode) throws Exception;

    /**
     * 校验数据节点名称是否可用
     *
     * @param dataNode
     * @return
     */
    boolean checkDataNodeName(DataNode dataNode);

    /**
     * 修改数据节点
     *
     * @param dataNode
     * @return
     * @throws Exception
     */
    int updateDataNode(DataNode dataNode) throws Exception;

    /**
     * 删除数据节点
     *
     * @param nodeId
     * @return
     * @throws Exception
     */
    int deleteDataNode(String nodeId) throws Exception;
}
