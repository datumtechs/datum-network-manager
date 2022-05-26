package com.platon.datum.admin.service;

import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.entity.Task;
import com.platon.datum.admin.dao.entity.TaskEvent;
import com.platon.datum.admin.dao.entity.TaskStatistics;

import java.util.List;

public interface TaskService {

    /**
     *
     * @param identityId
     * @param statusFilter
     * @param roleFilter
     * @param startTimestamp 精确到毫秒
     * @param endTimestamp      精确到毫秒
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Task> listTaskByIdentityIdWithRole(String identityId, Integer statusFilter, Integer roleFilter, Long startTimestamp, Long endTimestamp, int pageNumber, int pageSize);

    Page<Task> listTaskByIdentityIdAndMetaDataIdWithRole(String identityId, String metaDataId, int pageNumber, int pageSize);

    Page<Task> listRunningTaskByPowerNodeId(String powerNodeId, int pageNumber, int pageSize);

    Integer selectAllTaskCount();

    Integer selectTaskRunningCount();

    TaskStatistics taskStatistics();


    Task getTaskDetails(String taskId);


    Task selectTaskByTaskId(String taskId);


    List<TaskEvent> listTaskEventWithOrgName(String taskId);


}
