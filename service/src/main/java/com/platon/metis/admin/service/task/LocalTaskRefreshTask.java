package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.*;
import com.platon.metis.admin.dao.entity.*;
import com.platon.metis.admin.dao.enums.TaskStatusEnum;
import com.platon.metis.admin.grpc.client.TaskClient;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.entity.TaskEventDataResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    private TaskAlgoProviderMapper  taskAlgoProviderMapper;

    @Resource
    private TaskDataProviderMapper taskDataProviderMapper;

    @Resource
    private TaskPowerProviderMapper taskPowerProviderMapper;

    @Resource
    private TaskResultConsumerMapper taskResultConsumerMapper;

    @Resource
    private TaskOrgMapper taskOrgMapper;

    @Resource
    private TaskEventMapper taskEventMapper;



    //@Scheduled(fixedDelay = 20000)
    @Scheduled(fixedDelayString = "${LocalTaskRefreshTask.fixedDelay}")
    public void task() {
        log.info("启动执行获取任务数据列表定时任务...........");
        Pair<List<Task>, Map<String, TaskOrg>> resp = taskClient.getLocalTaskList();

        if(resp==null || resp.getLeft()==null || resp.getLeft().size()==0){
            log.info("RPC获取任务列表,任务数据为空");
            return;
        }


        //1、筛选出需要更新Task Data
        log.info("1、筛选出需要更新Task Data");
        List<String> endTaskIds = taskMapper.selectListTaskByStatusWithSuccessAndFailed();
        List<Task> allTaskList =  resp.getLeft();
        Map<String, TaskOrg> allTaskOrgMap = resp.getRight();

        List<Task> tobeUpdateTaskList = allTaskList.stream().filter(new Predicate<Task>() {
                                                                @Override
                                                                public boolean test(Task task) {
                                                                    return !endTaskIds.contains(task.getTaskId());
                                                                }
                                                        }).collect(Collectors.toList());

        //2、整理收集待持久化数据
        log.info("2、整理收集待持久化数据");
        log.info("待持久化数据updateTaskList:" + tobeUpdateTaskList.size());

        List<TaskAlgoProvider> algoProviderList = new ArrayList<>();
        List<TaskDataProvider> dataReceiverList = new ArrayList<>();
        List<TaskPowerProvider> powerProviderList = new ArrayList<>();
        List<TaskResultConsumer> resultReceiverList = new ArrayList<>();


        if(!CollectionUtils.isEmpty(tobeUpdateTaskList)){

            for (int i = 0; i < tobeUpdateTaskList.size(); i++) {
                 Task task = tobeUpdateTaskList.get(i);
                //构造数据
                algoProviderList.add(task.getAlgoSupplier());
                 dataReceiverList.addAll(task.getDataSupplier());
                 powerProviderList.addAll(task.getPowerSupplier());
                 resultReceiverList.addAll(task.getReceivers());

            }
        }

        //3、批量更新DB
        log.info("3、批量更新DB");
        if (checkDataValidity(tobeUpdateTaskList)) {
            taskMapper.insertBatch(tobeUpdateTaskList);
        }
        if (checkDataValidity(algoProviderList)) {
            taskAlgoProviderMapper.insertBatch(algoProviderList);
        }
        if (checkDataValidity(dataReceiverList)) {
            taskDataProviderMapper.insertBatch(dataReceiverList);
        }
        if (checkDataValidity(powerProviderList)) {
            taskPowerProviderMapper.insertBatch(powerProviderList);
        }
        if (checkDataValidity(resultReceiverList)) {
            taskResultConsumerMapper.insertBatch(resultReceiverList);
        }
        if(allTaskOrgMap.size()>0){
            taskOrgMapper.insertBatch(allTaskOrgMap.values());
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
}
