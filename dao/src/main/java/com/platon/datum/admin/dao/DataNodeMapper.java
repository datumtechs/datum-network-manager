package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.DataNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author lyf
 * @Description 数据节点mapper接口
 * @date 2021/7/8 17:29
 */
public interface DataNodeMapper {
    /**
     * 根据关键字模糊查询节点名称相关节点
     *
     * @param keyword
     * @return
     */
    List<DataNode> listNode(@Param(value = "keyword") String keyword);

    /**
     * 根据nodeId删除节点
     *
     * @param nodeId
     * @return
     */
    int deleteByPrimaryKey(@Param(value = "nodeId") String nodeId);

    /**
     * 新增数据节点
     *
     * @param record
     * @return
     */
    int insert(DataNode record);


    /**
     * 根据nodeId修改节点
     *
     * @param record
     * @return
     */
    int update(DataNode record);

    /**
     * 初始化新数据节点的基本信息，如果相同的key则更新基本信息（不包括节点名称）
     * @param dataNodeList 数据节点列表
     * @return
     */
    int initNewDataNodeBatch(List<DataNode> dataNodeList);


    /**
     * 根据nodeId查询数据节点
     * @param nodeId
     * @return
     */
    DataNode selectByPrimaryKey(String nodeId);

    /**
     * 根据数据节点属性查询nodeId
     * @param dataNode
     * @return
     */
    DataNode selectByProperties(DataNode dataNode);

    DataNode findLocalDataNodeByName(@Param("nodeName")  String nodeName);

    void updateLocalDataNodeName(@Param("nodeId") String nodeId, @Param("nodeName") String nodeName);
}