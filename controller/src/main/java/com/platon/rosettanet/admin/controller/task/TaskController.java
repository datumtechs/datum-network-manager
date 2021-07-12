package com.platon.rosettanet.admin.controller.task;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.Task;
import com.platon.rosettanet.admin.dao.entity.TaskEvent;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.TaskPageReq;
import com.platon.rosettanet.admin.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task/mytask")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //根据条件查询组织参与的任务列表
    @PostMapping("/taskListByQuery")
    public JsonResponse listMyTask(@Validated @RequestBody TaskPageReq taskPageReq){

        Page<Task> taskPage =  taskService.listTask(taskPageReq.getStatus(),taskPageReq.getRole(),taskPageReq.getStartTimestamp(),taskPageReq.getEndTimestamp(), taskPageReq.getKeyWord(), taskPageReq.getPageNumber(),taskPageReq.getPageSize());
        Integer allTaskCount = taskService.selectAllTaskCount();
        Integer taskRunningCount = taskService.selectTaskRunningCount();

        return JsonResponse.page(taskPage);
    }


    //查询组织参与的单个任务详情
    @GetMapping("/taskInfo")
    public JsonResponse<Task> getTaskDetails(@RequestParam String taskId) {
        Task task = taskService.getTaskDetails(taskId);
        return JsonResponse.success(task);
    }


    @GetMapping("/taskEventList")
    public JsonResponse taskEventList(@RequestParam(required = true) String taskId){

        List<TaskEvent> taskEventList =  taskService.listTaskEvent(taskId);
        Task tbTask = taskService.selectTaskByTaskId(taskId);
        return JsonResponse.success(null, taskEventList);
    }



}
