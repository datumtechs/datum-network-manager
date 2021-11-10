package com.platon.metis.admin.service;

import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.Task;
import com.platon.metis.admin.dao.entity.TaskEvent;
import com.platon.metis.admin.dao.entity.TaskStatistics;

import java.util.List;

public interface TaskService {

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
