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
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.platon.rosettanet.admin.grpc.client.TaskClient.NODE_ID;
import static com.platon.rosettanet.admin.grpc.client.TaskClient.NODE_NAME;

/**
 * 计算任务定时任务
 */
@Slf4j
//@Component
public class MyTaskRefreshTask {

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
    private TaskOrgMapper taskOrgMapper;

    @Resource
    private TaskEventMapper taskEventMapper;



    @Scheduled(fixedDelay = 20000)
    public void task() {
        log.info("启动执行获取任务数据列表定时任务...........");
        TaskDataResp resp = taskClient.getTaskListData();
        if (Objects.isNull(resp) || GrpcConstant.GRPC_SUCCESS_CODE != resp.getStatus()) {
            log.info("获取任务列表,调度服务调用失败");
            return;
        }
        if(!checkDataValidity(resp.getTaskList())){
            log.info("RPC获取任务列表,任务数据为空");
            return;
        }

        //1、筛选出需要更新Task Data
        log.info("1、筛选出需要更新Task Data");
        List<String> deleteTaskIds = taskMapper.selectListTaskByStatusWithSuccessAndFailed();
        List<Task> allTaskList =  resp.getTaskList();
        List<Task> updateTaskList = allTaskList.stream().filter(new Predicate<Task>() {
                                                                @Override
                                                                public boolean test(Task task) {
                                                                    return !deleteTaskIds.contains(task.getTaskId());
                                                                }
                                                        }).collect(Collectors.toList());

        //2、整理收集待持久化数据
        log.info("2、整理收集待持久化数据");
        log.info("待持久化数据updateTaskList:" + updateTaskList.size());

        List<TaskDataReceiver> dataReceiverList = new ArrayList<>();
        List<TaskPowerProvider> powerProviderList = new ArrayList<>();
        List<TaskResultReceiver> resultReceiverList = new ArrayList<>();
        List<TaskOrg> taskOrgList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(updateTaskList)){

            for (int i = 0; i < updateTaskList.size(); i++) {
                 Task taskData = updateTaskList.get(i);
                 List<TaskDataReceiver> dataReceivers = taskData.getDataSupplier();
                 List<TaskPowerProvider> powerProviders = taskData.getPowerSupplier();
                 List<TaskResultReceiver> resultReceivers = taskData.getReceivers();
                 List<TaskOrg> taskOrgs = getTaskOrgList(taskData);

                //构造数据
                 dataReceiverList.addAll(dataReceivers);
                 powerProviderList.addAll(powerProviders);
                 resultReceiverList.addAll(resultReceivers);
                 taskOrgList.addAll(taskOrgs);
            }
        }

        //3、批量更新DB
        log.info("3、批量更新DB");
        if (checkDataValidity(updateTaskList)) {
            taskMapper.insertBatch(updateTaskList);
        }
        if (checkDataValidity(dataReceiverList)) {
            taskDataReceiverMapper.insertBatch(dataReceiverList);
        }
        if (checkDataValidity(powerProviderList)) {
            taskPowerProviderMapper.insertBatch(powerProviderList);
        }
        if (checkDataValidity(resultReceiverList)) {
            taskResultReceiverMapper.insertBatch(resultReceiverList);
        }
        if(checkDataValidity(taskOrgList)){
            taskOrgMapper.insertBatch(taskOrgList);
        }


        //4、批量TaskEvent获取并更新DB
        log.info("4、批量TaskEvent获取并更新DB");
        List<String> taskIdList = updateTaskList.stream().map(Task -> Task.getTaskId()).collect(Collectors.toList());
        List<TaskEvent> taskEventList = new ArrayList<>();
        List<TaskEvent> taskEvents = getRpcTaskEventByTaskId(taskIdList);
        taskEventList.addAll(taskEvents);
        if (checkDataValidity(taskEventList)) {
            taskEventMapper.insertBatch(taskEventList);
        }
        log.info("结束执行获取任务数据列表定时任务...........");

    }


    private List<TaskEvent> getRpcTaskEventByTaskId(List<String> taskIds){

        TaskEventDataResp resp = taskClient.getTaskEventListData(taskIds);
        if (GrpcConstant.GRPC_SUCCESS_CODE != resp.getStatus()) {
            log.info("获取数据节点列表,调度服务调用失败");
            return null;
        }
        return resp.getTaskEventList();
    }


    /**
     * 检查更新DB数据有效性
     * @param data
     * @return
     */
    private boolean checkDataValidity(List data){
       return !Objects.isNull(data) && !CollectionUtils.isEmpty(data);
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


    /**
     * 组装TaskOrg Data
     * @param taskData
     * @return
     */
    private List<TaskOrg> getTaskOrgList(Task taskData){

        List<TaskOrg> taskOrgList = new ArrayList<>();

        List<TaskDataReceiver> dataReceivers = taskData.getDataSupplier();
        List<TaskPowerProvider> powerProviders = taskData.getPowerSupplier();
        List<TaskResultReceiver> resultReceivers = taskData.getReceivers();

        TaskOrg owner = taskData.getOwner();
        owner.setTaskId(taskData.getTaskId());

        TaskOrg algoSupplier = taskData.getAlgoSupplier();
        algoSupplier.setTaskId(taskData.getTaskId());

        for (TaskDataReceiver dataReceiver : dataReceivers) {
            String identityId = dataReceiver.getIdentityId();
            Map<String,String> dynamicFields = dataReceiver.getDynamicFields();
            String nodeName = dynamicFields.get(NODE_NAME);
            String nodeId = dynamicFields.get(NODE_ID);

            TaskOrg taskOrg = new TaskOrg();
            taskOrg.setTaskId(taskData.getTaskId());
            taskOrg.setIdentityId(identityId);
            taskOrg.setCarrierNodeId(nodeId);
            taskOrg.setName(nodeName);
            taskOrgList.add(taskOrg);
        }

        for (TaskPowerProvider taskPowerProvider : powerProviders) {
            String identityId = taskPowerProvider.getIdentityId();
            Map<String,String> dynamicFields = taskPowerProvider.getDynamicFields();
            String nodeName = dynamicFields.get(NODE_NAME);
            String nodeId = dynamicFields.get(NODE_ID);

            TaskOrg taskOrg = new TaskOrg();
            taskOrg.setTaskId(taskData.getTaskId());
            taskOrg.setIdentityId(identityId);
            taskOrg.setCarrierNodeId(nodeId);
            taskOrg.setName(nodeName);
            taskOrgList.add(taskOrg);
        }

        for (TaskResultReceiver taskResultReceiver : resultReceivers) {
            String identityId = taskResultReceiver.getConsumerIdentityId();
            Map<String,String> dynamicFields = taskResultReceiver.getDynamicFields();
            String nodeName = dynamicFields.get(NODE_NAME);
            String nodeId = dynamicFields.get(NODE_ID);

            TaskOrg taskOrg = new TaskOrg();
            taskOrg.setTaskId(taskData.getTaskId());
            taskOrg.setIdentityId(identityId);
            taskOrg.setCarrierNodeId(nodeId);
            taskOrg.setName(nodeName);
            taskOrgList.add(taskOrg);
        }

        taskOrgList.add(owner);
        taskOrgList.add(algoSupplier);
        return taskOrgList;
    }

}
