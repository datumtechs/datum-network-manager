package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.TaskEvent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TaskEventMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaskEvent record);

    int insertSelective(TaskEvent record);

    TaskEvent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskEvent record);

    int updateByPrimaryKey(TaskEvent record);

    List<TaskEvent> listTaskEventByTaskId(@Param("taskId") String taskId);

    int insertBatch(List<TaskEvent> taskEventList);

    int deleteBatch(List<String> taskIds);

    int insertUpdateBatch(Map map);

}