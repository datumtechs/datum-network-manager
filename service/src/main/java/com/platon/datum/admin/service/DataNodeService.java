package com.platon.datum.admin.service;

import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.entity.LocalDataNode;

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
    Page<LocalDataNode> listNode(Integer pageNumber, Integer pageSize, String keyword);

    /**
     * 新增数据节点
     *
     * @param localDataNode
     * @return
     * @throws Exception
     */
    int addDataNode(LocalDataNode localDataNode) throws Exception;

    /**
     * 修改数据节点
     *
     * @param localDataNode
     * @return
     * @throws Exception
     */
    int updateDataNode(LocalDataNode localDataNode) throws Exception;

    /**
     * 删除数据节点
     *
     * @param nodeId
     * @return
     * @throws Exception
     */
    int deleteDataNode(String nodeId) throws Exception;


    LocalDataNode findLocalDataNodeByName(String nodeName);

    void updateLocalDataNodeName(String nodeId, String nodeName);

}
