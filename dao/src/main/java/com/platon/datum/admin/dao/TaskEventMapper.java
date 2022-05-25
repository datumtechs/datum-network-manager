package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.TaskEvent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TaskEventMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaskEvent record);

    TaskEvent selectByPrimaryKey(Long id);

    int updateByPrimaryKey(TaskEvent record);

    List<TaskEvent> listTaskEventWithOrgNameByTaskId(@Param("taskId") String taskId);

    int insertBatch(List<TaskEvent> taskEventList);

    int deleteBatch(List<String> taskIds);

    int insertUpdateBatch(Map map);

}