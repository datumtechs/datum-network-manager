package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.LocalDateTime;

public interface LocalComputeNodeMapper {

    /**
     * 根据id查询计算节点详情
     * @param id
     * @return
     */
    LocalComputeNode selectComputeNodeById(Integer id);

    /**
     * 根据id删除计算节点
     * @param id
     * @return
     */
    int deleteComputeNodeById(Integer id);

    /**
     * 插入计算节点数据
     * @param record
     * @return
     */
    int insertComputeNode(LocalComputeNode localComputeNode);

    /**
     * 修改计算节点数据
     * @param record
     * @return
     */
    int updateComputeNode(LocalComputeNode localComputeNode);
}