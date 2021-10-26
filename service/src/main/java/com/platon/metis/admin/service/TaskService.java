package com.platon.metis.admin.service;

import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.Task;
import com.platon.metis.admin.dao.entity.TaskEvent;
import com.platon.metis.admin.dao.entity.TaskStatistics;

import java.util.List;

public interface TaskService {

    Page<Task> listTaskByIdentityIdWithRole(String identityId, Long startTimestamp, Long endTimestamp, int pageNumber, int pageSize);

    Integer selectAllTaskCount();

    Integer selectTaskRunningCount();

    TaskStatistics taskStatistics();


    Task getTaskDetails(String taskId);


    Task selectTaskByTaskId(String taskId);


    List<TaskEvent> listTaskEvent(String taskId);

}
