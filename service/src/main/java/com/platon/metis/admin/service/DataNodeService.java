package com.platon.metis.admin.service;

import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.LocalDataNode;

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
     * 校验数据节点名称是否可用
     *
     * @param localDataNode
     * @return
     */
    boolean checkDataNodeName(LocalDataNode localDataNode);

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
}
