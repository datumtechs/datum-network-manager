package com.platon.metis.admin.service.task;

import com.alibaba.fastjson.JSON;
import com.platon.metis.admin.dao.*;
import com.platon.metis.admin.dao.entity.*;
import com.platon.metis.admin.dao.enums.TaskStatusEnum;
import com.platon.metis.admin.grpc.client.TaskClient;
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
        log.debug("定时获取本组织相关的任务列表...");
        try {
            Pair<List<Task>, Map<String, TaskOrg>> resp = taskClient.getLocalTaskList();

            if (resp == null || resp.getLeft() == null || resp.getLeft().size() == 0) {
                log.warn("RPC获取任务列表,任务数据为空");
                return;
            }


            //1、筛选出需要更新Task Data
            List<String> endTaskIds = taskMapper.selectListTaskByStatusWithSuccessAndFailed();
            for (String taskId: endTaskIds) {
                log.debug("本地显示已经结束的taskId:{}", taskId);

            }
            List<Task> allTaskList = resp.getLeft();

            Map<String, TaskOrg> allTaskOrgMap = resp.getRight();

            List<Task> tobeUpdateTaskList = allTaskList.stream().filter(new Predicate<Task>() {
                @Override
                public boolean test(Task task) {
                    return !endTaskIds.contains(task.getTaskId());
                }
            }).collect(Collectors.toList());


            List<String> toPrintEventsTaskIdList = new ArrayList<>();

            log.debug("从调度服务同步过来，并且需要更新task各信息的数量（带event）:{}", tobeUpdateTaskList.size());
            for (Task task: tobeUpdateTaskList) {
                if (task.getStatus()==3 || task.getStatus()==4){
                    toPrintEventsTaskIdList.add(task.getTaskId());
                    log.debug("从调度服务同步过来，并且刚刚结束的taskId:{}", task.getTaskId());
                }
            }

            //2、整理收集待持久化数据
            List<TaskAlgoProvider> algoProviderList = new ArrayList<>();
            List<TaskDataProvider> dataProviderList = new ArrayList<>();
            List<TaskPowerProvider> powerProviderList = new ArrayList<>();
            List<TaskResultConsumer> resultReceiverList = new ArrayList<>();


            if (!CollectionUtils.isEmpty(tobeUpdateTaskList)) {

                for (int i = 0; i < tobeUpdateTaskList.size(); i++) {
                    Task task = tobeUpdateTaskList.get(i);
                    //构造数据
                    algoProviderList.add(task.getAlgoSupplier());
                    dataProviderList.addAll(task.getDataSupplier());
                    powerProviderList.addAll(task.getPowerSupplier());
                    resultReceiverList.addAll(task.getReceivers());
                }
            }

            //3、批量更新DB
            if (checkDataValidity(tobeUpdateTaskList)) {
                taskMapper.insertBatch(tobeUpdateTaskList);
            }
            if (checkDataValidity(algoProviderList)) {
                taskAlgoProviderMapper.insertBatch(algoProviderList);
            }
            if (checkDataValidity(dataProviderList)) {
                taskDataProviderMapper.insertBatch(dataProviderList);
            }
            if (checkDataValidity(powerProviderList)) {
                taskPowerProviderMapper.insertBatch(powerProviderList);
            }
            if (checkDataValidity(resultReceiverList)) {
                taskResultConsumerMapper.insertBatch(resultReceiverList);
            }
            if (allTaskOrgMap.size() > 0) {
                taskOrgMapper.insertBatch(allTaskOrgMap.values());
            }

            //4、批量TaskEvent获取并更新DB
            if (checkDataValidity(tobeUpdateTaskList)) {
                List<String> taskIdList = tobeUpdateTaskList.stream().map(Task -> Task.getTaskId()).collect(Collectors.toList());
                List<TaskEvent> taskEventList = taskClient.getTaskEventListData(taskIdList);

                for (TaskEvent taskEvent: taskEventList) {
                    if (toPrintEventsTaskIdList.contains(taskEvent.getTaskId())){
                        log.debug("从调度服务同步过来，并且刚刚结束的任务(taskId:{})的最后的event:{}", taskEvent.getTaskId(), JSON.toJSONString(taskEvent));
                    }
                }
                if (taskEventList != null) {
                    taskEventMapper.deleteBatch(taskIdList);
                    taskEventMapper.insertBatch(taskEventList);
                }
            }
        }catch (Throwable e){
            log.error("定时获取本组织相关的任务列表出错", e);
        }
        log.debug("定时获取本组织相关的任务列表结束...");
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
