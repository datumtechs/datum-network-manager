package com.platon.datum.admin.service.task;

import com.platon.datum.admin.dao.*;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.*;
import com.platon.datum.admin.dao.enums.TaskStatusEnum;
import com.platon.datum.admin.grpc.client.TaskClient;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import com.platon.datum.admin.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 定时刷新本组织相关的任务
 */
@Slf4j
@Configuration
public class TaskRefreshTask {

    @Resource
    private TaskClient taskClient;

    @Resource
    private TaskMapper taskMapper;

    @Resource

    private TaskAlgoProviderMapper taskAlgoProviderMapper;

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

    @Resource
    private DataSyncService dataSyncService;

    @Transactional(rollbackFor = Exception.class)
    @Scheduled(fixedDelayString = "${TaskRefreshTask.fixedDelay}")
    public void task() {
        if(OrgCache.localOrgNotFound()){
            return;
        }
        log.debug("刷新本组织相关任务详情定时任务开始>>>");
        while (true) {
            DataSync dataSync = dataSyncService.findDataSync(DataSync.DataType.LocalTask);

            Pair<List<Task>, Map<String, TaskOrg>> resp = taskClient.getLocalTaskList(dataSync.getLatestSynced());

            if (resp == null || CollectionUtils.isEmpty(resp.getLeft())) {
                break;
            }


            List<Task> localTaskList = resp.getLeft();
            log.debug("本次更新任务数量：{}", localTaskList.size());

            Map<String, TaskOrg> allTaskOrgMap = resp.getRight();

            //收集本次需要更新的taskId（已经结束的）,用来获取这些task的event列表（正在running的task在列表头部）
            List<String> endTaskIdList = new ArrayList<>();

            List<TaskAlgoProvider> algoProviderList = new ArrayList<>();
            List<TaskDataProvider> dataProviderList = new ArrayList<>();
            List<TaskPowerProvider> powerProviderList = new ArrayList<>();
            List<TaskResultConsumer> resultReceiverList = new ArrayList<>();

            localTaskList.forEach(task ->{
                algoProviderList.add(task.getAlgoSupplier());
                dataProviderList.addAll(task.getDataSupplier());
                powerProviderList.addAll(task.getPowerSupplier());
                resultReceiverList.addAll(task.getReceivers());
                if(task.getStatus() != null && (TaskStatusEnum.SUCCESS.getValue().intValue() == task.getStatus().intValue() || TaskStatusEnum.FAILED.getValue().intValue() == task.getStatus().intValue())){
                    endTaskIdList.add(task.getTaskId());
                }
              });

            //3、批量更新DB
            if (CollectionUtils.isNotEmpty(localTaskList)) {
                taskMapper.replaceBatch(localTaskList);
            }
            if (CollectionUtils.isNotEmpty(algoProviderList)) {
                taskAlgoProviderMapper.replaceBatch(algoProviderList);
            }
            if (CollectionUtils.isNotEmpty(dataProviderList)) {
                taskDataProviderMapper.replaceBatch(dataProviderList);
            }
            if (CollectionUtils.isNotEmpty(powerProviderList)) {
                taskPowerProviderMapper.replaceBatch(powerProviderList);
            }
            if (CollectionUtils.isNotEmpty(resultReceiverList)) {
                taskResultConsumerMapper.replaceBatch(resultReceiverList);
            }
            if (allTaskOrgMap.size() > 0) {
                taskOrgMapper.replaceBatch(allTaskOrgMap.values());
            }

            //4、批量TaskEvent获取并更新DB
            if(endTaskIdList.size()>0){
                List<TaskEvent> taskEventList = taskClient.getTaskEventListData(endTaskIdList);
                if (CollectionUtils.isNotEmpty(taskEventList)) {
                    taskEventMapper.deleteBatch(endTaskIdList);
                    taskEventMapper.insertBatch(taskEventList);
                }
            }

            Task task = localTaskList.stream()
                    .sorted(Comparator.comparing(Task::getUpdateAt).reversed())
                    .findFirst()
                    .get();

            //可能返回的列表都是正在running的任务，此时不需要更新LatestSynced
            if(task.getUpdateAt().isAfter(dataSync.getLatestSynced())){
                dataSync.setLatestSynced(task.getUpdateAt());
                //把最近更新时间update到数据库
                dataSyncService.updateDataSync(dataSync);
            }

            if(endTaskIdList.size() < GrpcConstant.PageSize){
                break;
            }
        }
        log.debug("刷新本组织相关任务详情定时任务结束|||");
    }
}
