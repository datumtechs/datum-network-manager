package com.platon.metis.admin.controller.task;

import com.github.pagehelper.Page;
import com.platon.metis.admin.common.context.LocalOrgIdentityCache;
import com.platon.metis.admin.dao.entity.Task;
import com.platon.metis.admin.dao.entity.TaskEvent;
import com.platon.metis.admin.dao.entity.TaskStatistics;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.TaskPageReq;
import com.platon.metis.admin.dto.resp.TaskDataResp;
import com.platon.metis.admin.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="计算任务controller",tags={"计算任务相关接口"})
@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @Autowired
    private TaskService taskService;



    @ApiOperation(value="我参与的任务情况统计")
    @GetMapping("/myTaskStatistics")
    public JsonResponse<TaskStatistics> myTaskStatistics(){
        //查询任务数量
        TaskStatistics taskStatistics = taskService.taskStatistics();
        return JsonResponse.success(taskStatistics);
    }

    //根据条件查询组织参与的任务列表
    @ApiOperation(value="条件查询组织参与的任务列表", notes="返回数据的每个task中，包含如下的动态字段：</br>taskSponsor, powerProvider, dataProvider, resultConsumer, algoProvider </br>当这些字段值等于1时表示本组织在任务中的相应角色")
    @PostMapping("/listMyTask")
    public JsonResponse<TaskDataResp> listMyTask(@Validated @RequestBody TaskPageReq taskPageReq){
        //查询任务列表Data

        Page<Task> taskPage =  taskService.listTaskByIdentityIdWithRole(LocalOrgIdentityCache.getIdentityId(), taskPageReq.getStartTime(),taskPageReq.getEndTime(), taskPageReq.getPageNumber(),taskPageReq.getPageSize());

        //封装响应数据
        //TaskDataResp taskDataResp = TaskDataResp.from(taskDataPageList);
        JsonResponse jsonResponse = JsonResponse.success(taskPage);
        jsonResponse.setPageNumber(taskPage.getPageNum());
        jsonResponse.setPageSize(taskPage.getPageSize());
        jsonResponse.setPageTotal(taskPage.getPages());
        jsonResponse.setTotal((int)taskPage.getTotal());
        return jsonResponse;
    }



    //查询组织参与的单个任务详情
    @GetMapping("/taskInfo")
    @ApiOperation(value="查询组织参与的单个任务详情")
    public JsonResponse<Task> taskInfo(@ApiParam(name = "taskId",value = "任务id", type = "String", required = true) @RequestParam String taskId) {
        Task task = taskService.getTaskDetails(taskId);
        return JsonResponse.success(task);
    }


    //单个任务事件日志列表
    @GetMapping("/listTaskEvent")
    @ApiOperation(value="单个任务事件日志列表")

    public JsonResponse<List<TaskEvent>> listTaskEvent(@ApiParam(name = "taskId",value = "任务id", type = "String", required = true) @RequestParam(required = true) String taskId){
        List<TaskEvent> taskEventList = taskService.listTaskEventWithOrgName(taskId);
        return JsonResponse.success(taskEventList);
    }



}
