package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.DataNode;
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
     * 根据节点名称查询nodeId(非主键id)
     *
     * @param hostName
     * @return
     */
    String getDataNodeIdByName(@Param(value = "hostName") String hostName);

    /**
     * 根据nodeId删除节点
     *
     * @param nodeId
     * @return
     */
    int deleteByNodeId(@Param(value = "nodeId") String nodeId);

    /**
     * 新增数据节点
     *
     * @param record
     * @return
     */
    int insert(DataNode record);

    DataNode selectByPrimaryKey(Integer id);

    /**
     * 根据nodeId修改节点
     *
     * @param record
     * @return
     */
    int updateByNodeId(DataNode record);

    /**
     * 批量更新数据节点
     * @param dataNodeList 数据节点服务列表
     * @return
     */
    int batchUpdate(List<DataNode> dataNodeList);


    /**
     * 根据nodeId查询数据节点
     * @param nodeId
     * @return
     */
    DataNode selectByNodeId(String nodeId);

}