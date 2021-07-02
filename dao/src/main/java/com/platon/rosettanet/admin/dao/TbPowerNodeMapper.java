package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbPowerNode;

import java.util.List;

public interface TbPowerNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbPowerNode record);

    int insertSelective(TbPowerNode record);

    TbPowerNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbPowerNode record);

    int updateByPrimaryKey(TbPowerNode record);

    /**
     * 查询出所有的计算节点id
     * @return
     */
    List<TbPowerNode> selectAll();
}