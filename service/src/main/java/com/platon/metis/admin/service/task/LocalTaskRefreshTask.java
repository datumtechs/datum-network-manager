package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.*;
import com.platon.metis.admin.dao.entity.*;
import com.platon.metis.admin.grpc.client.TaskClient;
import com.platon.metis.admin.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 定时刷新本组织相关的任务
 */
@Slf4j
@Configuration
public class LocalTaskRefreshTask {

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

    @Transactional
    @Scheduled(fixedDelayString = "${LocalTaskRefreshTask.fixedDelay}")
    public void task() {
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

            //收集本次更新的taskId,用来获取这些task的event列表
            List<String> taskIdList = new ArrayList<>();

            List<TaskAlgoProvider> algoProviderList = new ArrayList<>();
            List<TaskDataProvider> dataProviderList = new ArrayList<>();
            List<TaskPowerProvider> powerProviderList = new ArrayList<>();
            List<TaskResultConsumer> resultReceiverList = new ArrayList<>();

            localTaskList.forEach(task ->{
                algoProviderList.add(task.getAlgoSupplier());
                dataProviderList.addAll(task.getDataSupplier());
                powerProviderList.addAll(task.getPowerSupplier());
                resultReceiverList.addAll(task.getReceivers());
                taskIdList.add(task.getTaskId());
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
            List<TaskEvent> taskEventList = taskClient.getTaskEventListData(taskIdList);
            if (CollectionUtils.isNotEmpty(taskEventList)) {
                taskEventMapper.deleteBatch(taskIdList);
                taskEventMapper.insertBatch(taskEventList);
            }

            Task task = localTaskList.get(localTaskList.size() - 1);
            dataSync.setLatestSynced(task.getUpdateAt());
            //把最近更新时间update到数据库
            dataSyncService.updateDataSync(dataSync);
        }
        log.debug("刷新本组织相关任务详情定时任务结束|||");
    }
}
