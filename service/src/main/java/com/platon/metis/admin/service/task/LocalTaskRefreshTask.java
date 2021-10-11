package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.*;
import com.platon.metis.admin.dao.entity.*;
import com.platon.metis.admin.dao.enums.TaskStatusEnum;
import com.platon.metis.admin.grpc.client.TaskClient;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.entity.TaskDataResp;
import com.platon.metis.admin.grpc.entity.TaskEventDataResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.platon.metis.admin.grpc.client.TaskClient.NODE_ID;
import static com.platon.metis.admin.grpc.client.TaskClient.NODE_NAME;

/**
 * 计算任务定时任务
 */
@Slf4j
@Configuration
public class LocalTaskRefreshTask {

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



    //@Scheduled(fixedDelay = 20000)
    @Scheduled(fixedDelayString = "${LocalTaskRefreshTask.fixedDelay}")
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
        List<String> endTaskIds = taskMapper.selectListTaskByStatusWithSuccessAndFailed();
        List<Task> allTaskList =  resp.getTaskList();
        List<Task> tobeUpdateTaskList = allTaskList.stream().filter(new Predicate<Task>() {
                                                                @Override
                                                                public boolean test(Task task) {
                                                                    return !endTaskIds.contains(task.getTaskId());
                                                                }
                                                        }).collect(Collectors.toList());

        //2、整理收集待持久化数据
        log.info("2、整理收集待持久化数据");
        log.info("待持久化数据updateTaskList:" + tobeUpdateTaskList.size());

        List<TaskDataReceiver> dataReceiverList = new ArrayList<>();
        List<TaskPowerProvider> powerProviderList = new ArrayList<>();
        List<TaskResultReceiver> resultReceiverList = new ArrayList<>();
        Set<TaskOrg> taskOrgList = new HashSet<>();

        if(!CollectionUtils.isEmpty(tobeUpdateTaskList)){

            for (int i = 0; i < tobeUpdateTaskList.size(); i++) {
                 Task task = tobeUpdateTaskList.get(i);
                 List<TaskDataReceiver> dataReceivers = task.getDataSupplier();
                 List<TaskPowerProvider> powerProviders = task.getPowerSupplier();
                 List<TaskResultReceiver> resultReceivers = task.getReceivers();
                 Set<TaskOrg> taskOrgSet = getTaskOrgList(task);
                 if(taskOrgSet.size()>0) {
                     taskOrgList.addAll(taskOrgSet);
                 }

                //构造数据
                 dataReceiverList.addAll(dataReceivers);
                 powerProviderList.addAll(powerProviders);
                 resultReceiverList.addAll(resultReceivers);

            }
        }

        //3、批量更新DB
        log.info("3、批量更新DB");
        if (checkDataValidity(tobeUpdateTaskList)) {
            taskMapper.insertBatch(tobeUpdateTaskList);
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
        if(taskOrgList.size()>0){
            taskOrgMapper.insertBatch(taskOrgList);
        }


        //4、批量TaskEvent获取并更新DB
        if(checkDataValidity(tobeUpdateTaskList)){
            log.info("4、批量TaskEvent获取并更新DB");
            List<String> taskIdList = tobeUpdateTaskList.stream().map(Task -> Task.getTaskId()).collect(Collectors.toList());
            List<TaskEvent> taskEvents = getRpcTaskEventByTaskId(taskIdList);
            if(taskEvents!=null){
                List<TaskEvent> taskEventList = new ArrayList<>(taskEvents);
                taskEventMapper.deleteBatch(taskIdList);
                taskEventMapper.insertBatch(taskEventList);
            }
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
     * 组装TaskOrg Data，从各个参与方中获取组织信息(记得去重)
     * @param taskData
     * @return
     */
    private Set<TaskOrg> getTaskOrgList(Task taskData){

        Set<TaskOrg> taskOrgList = new HashSet<>();

        List<TaskDataReceiver> dataReceivers = taskData.getDataSupplier();
        List<TaskPowerProvider> powerProviders = taskData.getPowerSupplier();
        List<TaskResultReceiver> resultReceivers = taskData.getReceivers();

        TaskOrg owner = taskData.getOwner();
        TaskOrg algoSupplier = taskData.getAlgoSupplier();

        for (TaskDataReceiver dataReceiver : dataReceivers) {
            String identityId = dataReceiver.getIdentityId();
            Map<String,String> dynamicFields = dataReceiver.getDynamicFields();
            String nodeName = dynamicFields.get(NODE_NAME);
            String nodeId = dynamicFields.get(NODE_ID);

            TaskOrg taskOrg = new TaskOrg();
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
