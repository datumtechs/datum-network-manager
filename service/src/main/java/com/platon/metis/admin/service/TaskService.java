package com.platon.metis.admin.service;

import com.github.pagehelper.Page;
import com.platon.metis.admin.dao.entity.Task;
import com.platon.metis.admin.dao.entity.TaskEvent;
import com.platon.metis.admin.dao.entity.TaskStatistics;

import java.util.List;

public interface TaskService {

    Page<Task> listTask(Integer status, Integer role, Long startTimestamp, Long endTimestamp, String keyWord, int pageNumber, int pageSize);

    Integer selectAllTaskCount();

    Integer selectTaskRunningCount();

    TaskStatistics selectTaskStatisticsCount();


    Task getTaskDetails(String taskId);


    Task selectTaskByTaskId(String taskId);


    List<TaskEvent> listTaskEvent(String taskId);

}
