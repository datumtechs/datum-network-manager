package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.Task;
import com.platon.datum.admin.dao.entity.TaskStatistics;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface TaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(Task record);

    Task selectByPrimaryKey(String id);

    int updateByPrimaryKey(Task record);

    int updateTaskReviewedById(@Param("taskId")String taskId, @Param("reviewed") boolean reviewed);

    List<Task> listTaskByIdentityIdWithRole(@Param("identityId")String identityId, @Param("statusFilter") Integer statusFilter, @Param("roleFilter") Integer roleFilter, @Param("startTimestamp") Timestamp startTimestamp, @Param("endTimestamp")Timestamp endTimestamp);
    List<Task> listTaskByIdentityIdAndMetaDataIdWithRole(@Param("identityId")String identityId, @Param("metaDataId")String metaDataId);


    List<String> selectListTaskByStatusWithSuccessAndFailed();

    Task selectTaskByTaskId(@Param("taskId")String taskId, @Param("keyword")String keyword);

    Map<String, Boolean> listRoleByTaskIdAndIdentityId(@Param("taskId")String taskId, @Param("identityId")String identityId);

    Integer selectAllTaskCount();

    Integer selectTaskRunningCount();

    TaskStatistics taskStatistics(@Param("identityId")String identityId);

    Integer batchUpdate(List<Task> taskList);

    Integer replaceBatch(List<Task> taskList);

    List<Task> listRunningTaskByPowerNodeId(@Param("nodeId") String nodeId);
}