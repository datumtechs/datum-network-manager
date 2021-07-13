package com.platon.rosettanet.admin.service.task;

import com.platon.rosettanet.admin.dao.*;
import com.platon.rosettanet.admin.dao.entity.*;
import com.platon.rosettanet.admin.dao.enums.TaskStatusEnum;
import com.platon.rosettanet.admin.grpc.client.TaskClient;
import com.platon.rosettanet.admin.grpc.client.YarnClient;
import com.platon.rosettanet.admin.grpc.constant.GrpcConstant;
import com.platon.rosettanet.admin.grpc.entity.QueryNodeResp;
import com.platon.rosettanet.admin.grpc.entity.TaskDataResp;
import com.platon.rosettanet.admin.grpc.entity.TaskEventDataResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 计算任务定时任务
 */
@Slf4j
//@Component
public class TaskRefreshTask {

    @Resource
    private TaskClient taskClient;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskDataReceiverMapper taskDataReceiverMapper;

    @Resource
    private TaskPowerProviderMapper taskPowerProviderMapper;

    @Resource
    private TaskResultReceiverMapper taskResultReceiverMapper;

    @Resource
    private TaskEventMapper taskEventMapper;



    @Scheduled(fixedDelay = 2000)
    public void task() {

        TaskDataResp resp = taskClient.getTaskListData("localhost", 80);
        if (GrpcConstant.GRPC_SUCCESS_CODE != resp.getStatus()) {
            log.info("获取任务列表,调度服务调用失败");
            return;
        }

        List<Task> taskList = filterTaskListPendingAndRunning(resp.getTaskList());
        List<TaskDataReceiver> dataReceiverList = new ArrayList<>();
        List<TaskPowerProvider> powerProviderList = new ArrayList<>();
        List<TaskResultReceiver> resultReceiverList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(taskList)){
            for (int i = 0; i < taskList.size(); i++) {
                 Task taskData = taskList.get(i);
                 List<TaskDataReceiver> dataReceivers = taskData.getDataSupplier();
                 List<TaskPowerProvider> powerProviders = taskData.getPowerSupplier();
                 List<TaskResultReceiver> resultReceivers = taskData.getReceivers();
                 //构造数据
                 dataReceiverList.addAll(dataReceivers);
                 powerProviderList.addAll(powerProviders);
                 resultReceiverList.addAll(resultReceivers);
            }
            //批量更新DB
            taskMapper.insertBatch(taskList);
            taskDataReceiverMapper.insertBatch(dataReceiverList);
            taskPowerProviderMapper.insertBatch(powerProviderList);
            taskResultReceiverMapper.insertBatch(resultReceiverList);
        }

        //批量TaskEvent获取并更新DB
        List<TaskEvent> taskEventList = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
              List<TaskEvent> taskEvents = getRpcTaskEventByTaskId("localhost", 80, taskList.get(i).getId());
              taskEventList.addAll(taskEvents);
        }
        taskEventMapper.insertBatch(taskEventList);


    }


    private List<TaskEvent> getRpcTaskEventByTaskId(String rpcServerHost, int rpcServerPort, String taskId){

        log.info("insertOrUpdateTaskEvent,调度服务ip:" + rpcServerHost + ",端口号：" + rpcServerPort);
        TaskEventDataResp resp = taskClient.getTaskEventListData(rpcServerHost, rpcServerPort, taskId);
        if (GrpcConstant.GRPC_SUCCESS_CODE != resp.getStatus()) {
            log.info("获取数据节点列表,调度服务调用失败");
            return null;
        }
        return resp.getTaskEventList();
    }


    /**
     * 过滤任务状态为pending、running
     * @param taskList
     * @return
     */
    private List<Task> filterTaskListPendingAndRunning(List<Task> taskList){
        List<Task> newTaskList = new ArrayList<>();
        if(taskList == null) return newTaskList;
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if(TaskStatusEnum.PENDING.getStatus().equals(task.getStatus()) || TaskStatusEnum.RUNNING.getStatus().equals(task.getStatus())){
                newTaskList.add(task);
            }
        }
        return newTaskList;
    }
}
