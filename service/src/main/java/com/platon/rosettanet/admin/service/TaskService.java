package com.platon.rosettanet.admin.service;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.Task;
import com.platon.rosettanet.admin.dao.entity.TaskEvent;
import com.platon.rosettanet.admin.dao.entity.TaskStatistics;

import java.util.Date;
import java.util.List;

public interface TaskService {

    Page<Task> listTask(String status, Integer role, Long startTimestamp, Long endTimestamp, String keyWord, int pageNumber, int pageSize);

    Integer selectAllTaskCount();

    Integer selectTaskRunningCount();

    TaskStatistics selectTaskStatisticsCount();


    Task getTaskDetails(String taskId);


    Task selectTaskByTaskId(String taskId);


    List<TaskEvent> listTaskEvent(String taskId);

}
