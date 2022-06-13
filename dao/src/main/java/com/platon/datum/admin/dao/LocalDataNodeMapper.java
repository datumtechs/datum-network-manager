package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.LocalDataNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author lyf
 * @Description 数据节点mapper接口
 * @date 2021/7/8 17:29
 */
public interface LocalDataNodeMapper {
    /**
     * 根据关键字模糊查询节点名称相关节点
     *
     * @param keyword
     * @return
     */
    List<LocalDataNode> listNode(@Param(value = "keyword") String keyword);

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
    int insert(LocalDataNode record);


    /**
     * 根据nodeId修改节点
     *
     * @param record
     * @return
     */
    int update(LocalDataNode record);

    /**
     * 初始化新数据节点的基本信息，如果相同的key则更新基本信息（不包括节点名称）
     * @param localDataNodeList 数据节点列表
     * @return
     */
    int initNewDataNodeBatch(List<LocalDataNode> localDataNodeList);


    /**
     * 根据nodeId查询数据节点
     * @param nodeId
     * @return
     */
    LocalDataNode selectByPrimaryKey(String nodeId);

    /**
     * 根据数据节点属性查询nodeId
     * @param localDataNode
     * @return
     */
    LocalDataNode selectByProperties(LocalDataNode localDataNode);

    LocalDataNode findLocalDataNodeByName(@Param("nodeName")  String nodeName);

    void updateLocalDataNodeName(@Param("nodeId") String nodeId, @Param("nodeName") String nodeName);
}