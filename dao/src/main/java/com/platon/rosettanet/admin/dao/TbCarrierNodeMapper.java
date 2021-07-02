package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbCarrierNode;

public interface TbCarrierNodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbCarrierNode record);

    int insertSelective(TbCarrierNode record);

    TbCarrierNode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbCarrierNode record);

    int updateByPrimaryKey(TbCarrierNode record);
}