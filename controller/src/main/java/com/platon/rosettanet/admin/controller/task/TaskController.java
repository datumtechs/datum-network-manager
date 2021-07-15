package com.platon.rosettanet.admin.controller.task;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.Task;
import com.platon.rosettanet.admin.dao.entity.TaskEvent;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.TaskPageReq;
import com.platon.rosettanet.admin.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="计算任务controller",tags={"计算任务相关接口"})
@RestController
@RequestMapping("/api/v1/task/mytask")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //根据条件查询组织参与的任务列表
    @ApiOperation(value="条件查询组织参与的任务列表")
    @PostMapping("/taskListByQuery")
    public JsonResponse listMyTask(@Validated @RequestBody TaskPageReq taskPageReq){
        Page<Task> taskPage =  taskService.listTask(taskPageReq.getStatus(),taskPageReq.getRole(),taskPageReq.getStartTime(),taskPageReq.getEndTime(), taskPageReq.getKeyWord(), taskPageReq.getPageNumber(),taskPageReq.getPageSize());
        return JsonResponse.page(taskPage);
    }


    //查询任务数、进行中任务总数
    @GetMapping("/taskTotalCount")
    public JsonResponse getTaskTotelCount(){
        Integer allTaskCount = taskService.selectAllTaskCount();
        Integer taskRunningCount = taskService.selectTaskRunningCount();
        Map data = new HashMap();
        data.put("totalTaskCount", allTaskCount);
        data.put("runningTaskCount", taskRunningCount);
        return JsonResponse.success(data);
    }




    //查询组织参与的单个任务详情
    @GetMapping("/taskInfo")
    public JsonResponse getTaskDetails(@RequestParam String taskId) {
        Task task = taskService.getTaskDetails(taskId);
        return JsonResponse.success(task);
    }


    //单个任务事件列表
    @GetMapping("/taskEventList")
    public JsonResponse taskEventList(@RequestParam(required = true) String taskId){

        List<TaskEvent> taskEventList =  taskService.listTaskEvent(taskId);
        Task task = taskService.selectTaskByTaskId(taskId);
        return JsonResponse.success(taskEventList);
    }



}
