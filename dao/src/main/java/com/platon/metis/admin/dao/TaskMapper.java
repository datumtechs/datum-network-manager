package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.Task;
import com.platon.metis.admin.dao.entity.TaskStatistics;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(Task record);

    Task selectByPrimaryKey(String id);

    int updateByPrimaryKey(Task record);

    int updateTaskReviewedById(@Param("taskId")String taskId, @Param("reviewed") boolean reviewed);

    List<Task> listTaskByIdentityIdWithRole(@Param("identityId")String identityId, @Param("startTimestamp") Timestamp startTimestamp, @Param("endTimestamp")Timestamp endTimestamp);

    List<String> selectListTaskByStatusWithSuccessAndFailed();

    Task selectTaskByTaskId(@Param("taskId")String taskId, @Param("keyword")String keyword);

    Integer selectAllTaskCount();

    Integer selectTaskRunningCount();

    TaskStatistics taskStatistics();

    Integer batchUpdate(List<Task> taskList);

    Integer insertBatch(List<Task> taskList);

    void listRunningTaskByPowerNodeId(@Param("powerNodeId") String powerNodeId);
}