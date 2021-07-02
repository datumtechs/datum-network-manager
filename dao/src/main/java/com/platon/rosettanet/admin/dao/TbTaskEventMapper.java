package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TbTaskEvent;

public interface TbTaskEventMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbTaskEvent record);

    int insertSelective(TbTaskEvent record);

    TbTaskEvent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbTaskEvent record);

    int updateByPrimaryKey(TbTaskEvent record);
}