package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.TaskEvent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskEventMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaskEvent record);

    int insertSelective(TaskEvent record);

    TaskEvent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskEvent record);

    int updateByPrimaryKey(TaskEvent record);

    List<TaskEvent> listTbTaskEventByTaskId(@Param("taskId") String taskId);

}