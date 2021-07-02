package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbTaskResultReceiver;

public interface TbTaskResultReceiverMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbTaskResultReceiver record);

    int insertSelective(TbTaskResultReceiver record);

    TbTaskResultReceiver selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbTaskResultReceiver record);

    int updateByPrimaryKey(TbTaskResultReceiver record);
}