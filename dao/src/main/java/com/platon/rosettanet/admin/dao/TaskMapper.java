package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.Task;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

    List<Task> listTask(@Param("status")String status, @Param("role")int role, @Param("startTimestamp") Timestamp startTimestamp, @Param("endTimestamp")Timestamp endTimestamp, @Param("keyWord") String keyWord, @Param("localIdentityId") String localIdentityId);

    Task selectTaskByTaskId(@Param("taskId")String taskId);

    Integer selectAllTaskCount();

    Integer selectTaskRunningCount();

    Integer selectTaskRole(@Param("taskId")String taskId);

    Integer batchUpdate(List<Task> taskList);

    Integer insertBatch(List<Task> taskList);

}