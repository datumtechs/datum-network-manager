package com.platon.rosettanet.admin.controller.task;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.Task;
import com.platon.rosettanet.admin.dao.entity.TaskEvent;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.TaskPageReq;
import com.platon.rosettanet.admin.dto.resp.TaskDataDetailResp;
import com.platon.rosettanet.admin.dto.resp.TaskDataPageResp;
import com.platon.rosettanet.admin.dto.resp.TaskDataResp;
import com.platon.rosettanet.admin.dto.resp.TaskEventListResp;
import com.platon.rosettanet.admin.enums.ResponseCodeEnum;
import com.platon.rosettanet.admin.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(value="计算任务controller",tags={"计算任务相关接口"})
@RestController
@RequestMapping("/api/v1/task/mytask")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //根据条件查询组织参与的任务列表
    @ApiOperation(value="条件查询组织参与的任务列表")
    @PostMapping("/taskListByQuery")
    public JsonResponse<TaskDataResp> listMyTask(@Validated @RequestBody TaskPageReq taskPageReq){
        //查询任务列表Data
        Page<Task> taskPage =  taskService.listTask(taskPageReq.getStatus(),taskPageReq.getRole(),taskPageReq.getStartTime(),taskPageReq.getEndTime(), taskPageReq.getKeyWord(), taskPageReq.getPageNumber(),taskPageReq.getPageSize());
        List<TaskDataPageResp> taskDataPageList = taskPage.getResult().stream().map(TaskDataPageResp::convert).collect(Collectors.toList());

        //查询任务数量
        Integer allTaskCount = taskService.selectAllTaskCount();
        Integer taskRunningCount = taskService.selectTaskRunningCount();
        Map taskCountData = new HashMap();
        taskCountData.put("totalTaskCount", allTaskCount);
        taskCountData.put("runningTaskCount", taskRunningCount);

        //封装响应数据
        TaskDataResp taskDataResp = new TaskDataResp();
        taskDataResp.setList(taskDataPageList);
        taskDataResp.setCountData(taskCountData);

        JsonResponse jsonResponse = JsonResponse.success(taskDataResp);
        jsonResponse.setPageNumber(taskPage.getPageNum());
        jsonResponse.setPageSize(taskPage.getPageSize());
        jsonResponse.setPageTotal(taskPage.getPages());
        jsonResponse.setTotal((int)taskPage.getTotal());
        return jsonResponse;
    }



    //查询组织参与的单个任务详情
    @GetMapping("/taskInfo")
    @ApiOperation(value="查询组织参与的单个任务详情")
    public JsonResponse<TaskDataDetailResp> getTaskDetails(@ApiParam(name = "taskId",value = "任务id", type = "String", required = true) @RequestParam String taskId) {
        Task task = taskService.getTaskDetails(taskId);
        TaskDataDetailResp taskDataDetailResp = TaskDataDetailResp.convert(task);
        return JsonResponse.success(taskDataDetailResp);
    }


    //单个任务事件日志列表
    @GetMapping("/taskEventList")
    @ApiOperation(value="单个任务事件日志列表")

    public JsonResponse<TaskEventListResp> taskEventList(@ApiParam(name = "taskId",value = "任务id", type = "String", required = true) @RequestParam(required = true) String taskId){
        List<TaskEvent> taskEventList = taskService.listTaskEvent(taskId);
        List<TaskEventListResp> taskEventListRespList = taskEventList.stream().map(TaskEventListResp::convert).collect(Collectors.toList());
        Task task = taskService.selectTaskByTaskId(taskId);
        return JsonResponse.success(taskEventListRespList);
    }



}
