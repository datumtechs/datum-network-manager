package com.platon.rosettanet.admin.controller.task;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.Task;
import com.platon.rosettanet.admin.dao.entity.TaskEvent;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task/mytask")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/taskList")
    public JsonResponse listMyTask(@RequestParam(required = false) String status, @RequestParam(required = false) int role, @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate, @RequestParam int pageNumber, @RequestParam  int pageSize){

        Page<Task> taskPage =  taskService.listTask(status,role,startDate,endDate,pageNumber,pageSize);
        return JsonResponse.page(taskPage);
    }


    @GetMapping("/taskEventList")
    public JsonResponse taskEventList(@RequestParam(required = true) String taskId){

        List<TaskEvent> taskEventList =  taskService.listTaskEvent(taskId);
        Task tbTask = taskService.selectTaskByTaskId(taskId);
        return JsonResponse.success(null, taskEventList);
    }

}
