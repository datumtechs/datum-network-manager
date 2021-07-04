package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TaskEvent;

public interface TaskEventMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaskEvent record);

    int insertSelective(TaskEvent record);

    TaskEvent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskEvent record);

    int updateByPrimaryKey(TaskEvent record);
}