package com.platon.rosettanet.admin.service;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.Task;
import com.platon.rosettanet.admin.dao.entity.TaskEvent;
import java.util.Date;
import java.util.List;

public interface TaskService {

    Page<Task> listTask(String status, int role, Date startDate, Date endDate, int pageNumber, int pageSize);

    Task selectTaskByTaskId(String taskId);


    List<TaskEvent> listTaskEvent(String taskId);

}
