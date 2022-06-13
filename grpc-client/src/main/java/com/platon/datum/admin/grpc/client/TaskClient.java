package com.platon.datum.admin.grpc.client;

import cn.hutool.json.JSONUtil;
import com.google.protobuf.ProtocolStringList;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.entity.*;
import com.platon.datum.admin.grpc.carrier.api.TaskRpcApi;
import com.platon.datum.admin.grpc.carrier.api.TaskServiceGrpc;
import com.platon.datum.admin.grpc.carrier.types.ResourceData;
import com.platon.datum.admin.grpc.carrier.types.TaskData;
import com.platon.datum.admin.grpc.channel.SimpleChannelManager;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import com.platon.datum.admin.grpc.entity.template.*;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2021/7/8 18:30
 * @Version
 * @Desc 任务服务客户端
 * java服务类：TaskServiceGrpc
 * proto文件：task_rpc_api.proto
 */

@Component
@Slf4j
public class TaskClient {

    public static String NODE_NAME = "nodeName";
    public static String NODE_ID = "nodeId";

    @Resource
    private SimpleChannelManager channelManager;


    /**
     * 查询本组织参与的任务
     *
     * @return
     */
    public Pair<List<Task>, Map<String, TaskOrg>> getLocalTaskList(LocalDateTime latestSynced) {

        log.debug("从carrier查询本组织参与的任务, latestSynced:{}", latestSynced);

        Channel channel = channelManager.getCarrierChannel();

        //2.构造 request
        TaskRpcApi.GetTaskDetailListRequest request = TaskRpcApi.GetTaskDetailListRequest
                .newBuilder()
                .setLastUpdated(latestSynced.toInstant(ZoneOffset.UTC).toEpochMilli())
                .setPageSize(GrpcConstant.PageSize)
                .build();

        //3.调用rpc服务接口
        TaskRpcApi.GetTaskDetailListResponse response = TaskServiceGrpc.newBlockingStub(channel).getLocalTaskDetailList(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        log.debug("从carrier查询本组织参与的任务, 数量:{}", response.getTasksList().size(), response.getTasksList());

        List<TaskData.TaskDetail> tasksList = response.getTasksList();
        return dataConvertToTaskList(tasksList);
    }


    /**
     * 批量获取多个任务的全部事件列表
     *
     * @param taskIds
     * @return
     */
    public List<TaskEvent> getTaskEventListData(List<String> taskIds) {

        log.debug("从carrier批量获取多个任务的全部事件列表, taskIds:{}", taskIds);

        Channel channel = channelManager.getCarrierChannel();

        //2.构造 request
        TaskRpcApi.GetTaskEventListByTaskIdsRequest request = TaskRpcApi.GetTaskEventListByTaskIdsRequest.newBuilder().addAllTaskIds(taskIds).build();
        //3.调用rpc服务接口
        TaskRpcApi.GetTaskEventListResponse response = TaskServiceGrpc.newBlockingStub(channel).getTaskEventListByTaskIds(request);

        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        log.debug("从carrier批量获取多个任务的全部事件列表, 数量:{}", response.getTaskEventsList().size());

        List<TaskEvent> taskEventList = new ArrayList<>();
        //构造taskEvent集合
        for (TaskData.TaskEvent taskEvenShow : response.getTaskEventsList()) {
            TaskEvent taskEvent = new TaskEvent();
            taskEvent.setTaskId(taskEvenShow.getTaskId());
            taskEvent.setEventAt(LocalDateTimeUtil.getLocalDateTime(taskEvenShow.getCreateAt()));
            taskEvent.setEventContent(taskEvenShow.getContent());
            taskEvent.setEventType(taskEvenShow.getType());
            taskEvent.setIdentityId(taskEvenShow.getIdentityId());
            taskEvent.setPartyId(taskEvenShow.getPartyId());
            //添加
            taskEventList.add(taskEvent);
        }
        return taskEventList;

    }


    /**
     * 任务集合数据转换
     * 并搜集所涉及的参与方信息，放入task_org中。因此，管理台只看到自己相关任务所涉及的其它组织的信息。
     *
     * @param taskDetailList
     * @return
     */
    private Pair<List<Task>, Map<String, TaskOrg>> dataConvertToTaskList(List<TaskData.TaskDetail> taskDetailList) {

        //key:identityId
        Map<String, TaskOrg> taskOrgMap = new HashMap<>();

        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < taskDetailList.size(); i++) {
            TaskData.TaskDetailSummary taskDetail = taskDetailList.get(i).getInformation();

            //构造Task
            Task task = toTask(taskDetail);

            //任务发起方
            processSender(taskDetail, taskOrgMap, task);

            //算法提供方
            processAlgoSupplier(taskDetail, taskOrgMap, task);

            //数据提供方dataSupplierList
            processDataSupplierList(taskDetail, taskOrgMap, task);

            //算力提供方powerSupplierList
            processPowerSupplierList(taskDetail, taskOrgMap, task);

            //任务结果方receiverList
            processReceiverList(taskDetail, taskOrgMap, task);

            //构造完毕添加数据
            taskList.add(task);
        }
        return new ImmutablePair<>(taskList, taskOrgMap);
    }

    private Task toTask(TaskData.TaskDetailSummary taskDetail) {
        Task task = new Task();
        task.setTaskId(taskDetail.getTaskId());
        task.setTaskName(taskDetail.getTaskName());
        task.setCreateAt(LocalDateTimeUtil.getLocalDateTime(taskDetail.getCreateAt()));
        task.setStartAt(LocalDateTimeUtil.getLocalDateTime(taskDetail.getStartAt()));
        task.setEndAt(LocalDateTimeUtil.getLocalDateTime(taskDetail.getEndAt()));
        task.setStatus(taskDetail.getStateValue());

        ResourceData.TaskResourceCostDeclare operationCost = taskDetail.getOperationCost();
        task.setCostCore(operationCost.getProcessor());
        task.setCostMemory(operationCost.getMemory());
        task.setCostBandwidth(operationCost.getBandwidth());
        task.setDuration(operationCost.getDuration());
        task.setApplyUser(taskDetail.getUser()); //发起任务的用户的信息 (task是属于用户的)
        task.setUserType(taskDetail.getUserTypeValue()); //发起任务的用户类型 (0: 未定义; 1: 第二地址; 2: 测试网地址; 3: 主网地址)
        task.setUpdateAt(LocalDateTimeUtil.getLocalDateTime(taskDetail.getUpdateAt()));
        return task;
    }

    private void processSender(TaskData.TaskDetailSummary taskDetail, Map<String, TaskOrg> taskOrgMap, Task task) {
        TaskData.TaskOrganization sender = taskDetail.getSender();
        //搜集taskOrg，任务发起方
        taskOrgMap.put(sender.getIdentityId(), new TaskOrg(sender.getIdentityId(), sender.getNodeName(), sender.getNodeId()));
        task.setOwnerIdentityId(sender.getIdentityId());
        task.setOwnerPartyId(sender.getPartyId());
    }

    private void processAlgoSupplier(TaskData.TaskDetailSummary taskDetail, Map<String, TaskOrg> taskOrgMap, Task task) {
        TaskData.TaskOrganization algoSupplier = taskDetail.getAlgoSupplier();
        //算法提供方algoSupplier
        TaskAlgoProvider algoProvider = new TaskAlgoProvider();
        algoProvider.setTaskId(taskDetail.getTaskId());
        algoProvider.setIdentityId(algoSupplier.getIdentityId());
        algoProvider.setPartyId(algoSupplier.getPartyId());
        task.setAlgoSupplier(algoProvider);
        //搜集taskOrg， 算法提供方
        taskOrgMap.put(algoSupplier.getIdentityId(),
                new TaskOrg(algoSupplier.getIdentityId(),
                        algoSupplier.getNodeName(),
                        algoSupplier.getNodeId()));
    }

    private void processReceiverList(TaskData.TaskDetailSummary taskDetail, Map<String, TaskOrg> taskOrgMap, Task task) {
        List<TaskResultConsumer> resultConsumerList = new ArrayList<>();

        for (TaskData.TaskOrganization receiver : taskDetail.getReceiversList()) {
            TaskResultConsumer resultConsumer = new TaskResultConsumer();
            resultConsumer.setTaskId(taskDetail.getTaskId());
            resultConsumer.setConsumerIdentityId(receiver.getIdentityId());
            resultConsumer.setConsumerPartyId(receiver.getPartyId());
            resultConsumerList.add(resultConsumer);

            //搜集taskOrg， 算力提供方
            taskOrgMap.put(receiver.getIdentityId(), new TaskOrg(receiver.getIdentityId(), receiver.getNodeName(), receiver.getNodeId()));

        }
        task.setReceivers(resultConsumerList);
    }

    private void processPowerSupplierList(TaskData.TaskDetailSummary taskDetail, Map<String, TaskOrg> taskOrgMap, Task task) {
        List<TaskPowerProvider> taskPowerProviderList = new ArrayList<>();
        Map<String, ResourceData.ResourceUsageOverview> resourceUsageOverviewMap = taskDetail.getPowerResourceOptionsList().stream()
                .collect(Collectors.toMap(
                        TaskData.TaskPowerResourceOption::getPartyId,
                        TaskData.TaskPowerResourceOption::getResourceUsedOverview));
        for (TaskData.TaskOrganization powerSupplier : taskDetail.getPowerSuppliersList()) {
            TaskPowerProvider powerProvider = new TaskPowerProvider();
            powerProvider.setTaskId(taskDetail.getTaskId());
            powerProvider.setIdentityId(powerSupplier.getIdentityId());

            String partyId = powerSupplier.getPartyId();
            powerProvider.setPartyId(partyId);
            ResourceData.ResourceUsageOverview resourceUsageOverview = resourceUsageOverviewMap.get(partyId);

            powerProvider.setUsedCore(resourceUsageOverview.getUsedProcessor());
            powerProvider.setTotalCore(resourceUsageOverview.getTotalProcessor());
            powerProvider.setUsedMemory(resourceUsageOverview.getUsedMem());
            powerProvider.setTotalMemory(resourceUsageOverview.getTotalMem());
            powerProvider.setUsedBandwidth(resourceUsageOverview.getUsedBandwidth());
            powerProvider.setTotalBandwidth(resourceUsageOverview.getTotalBandwidth());
            taskPowerProviderList.add(powerProvider);

            //搜集taskOrg， 算力提供方
            taskOrgMap.put(powerSupplier.getIdentityId(),
                    new TaskOrg(powerSupplier.getIdentityId(),
                            powerSupplier.getNodeName(),
                            powerSupplier.getNodeId()));

        }
        task.setPowerSupplier(taskPowerProviderList);
    }

    private void processDataSupplierList(TaskData.TaskDetailSummary taskDetail, Map<String, TaskOrg> taskOrgMap, Task task) {
        String taskId = taskDetail.getTaskId();
        List<TaskDataProvider> taskDataProviderList = new ArrayList<>();

        List<Integer> dataPolicyTypesList = taskDetail.getDataPolicyTypesList();
        ProtocolStringList dataPolicyOptionsList = taskDetail.getDataPolicyOptionsList();
        List<TaskData.TaskOrganization> dataSuppliersList = taskDetail.getDataSuppliersList();
        for (int i = 0; i < dataPolicyOptionsList.size(); i++) {
            Integer type = dataPolicyTypesList.get(i);
            String option = dataPolicyOptionsList.get(i);
            String metaDataId = "";
            String metaDataName = "";
            String hash = taskId + i;
            switch (type) {
                case 0:
                    DataPolicyOption0 dataPolicyOption0 = JSONUtil.toBean(option, DataPolicyOption0.class);
                    metaDataId = dataPolicyOption0.getMetadataId();
                    metaDataName = dataPolicyOption0.getMetadataName();
                    hash = taskId + dataPolicyOption0.getHash(i);
                    break;
                case 1:
                    DataPolicyOption1 dataPolicyOption1 = JSONUtil.toBean(option, DataPolicyOption1.class);
                    metaDataId = dataPolicyOption1.getMetadataId();
                    metaDataName = dataPolicyOption1.getMetadataName();
                    hash = taskId + dataPolicyOption1.getHash(i);
                    break;
                case 2:
                    DataPolicyOption2 dataPolicyOption2 = JSONUtil.toBean(option, DataPolicyOption2.class);
                    metaDataId = dataPolicyOption2.getMetadataId();
                    metaDataName = dataPolicyOption2.getMetadataName();
                    hash = taskId + dataPolicyOption2.getHash(i);
                    break;
                case 3:
                    DataPolicyOption3 dataPolicyOption3 = JSONUtil.toBean(option, DataPolicyOption3.class);
                    metaDataId = dataPolicyOption3.getMetadataId();
                    metaDataName = dataPolicyOption3.getMetadataName();
                    hash = taskId + dataPolicyOption3.getHash(i);
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                    break;
                case 30001:
                    DataPolicyOption30001 dataPolicyOption30001 = JSONUtil.toBean(option, DataPolicyOption30001.class);
                    hash = taskId + dataPolicyOption30001.getHash(i);
                    break;
                case 30002:
                case 30003:
                case 30004:
                case 30005:
                case 30006:
                case 30007:
                    break;
                default:
                    log.error("taskId : {},unknown data policy type : {}", taskDetail.getTaskId(), type);
            }

            TaskData.TaskOrganization dataSupplier = dataSuppliersList.get(i);
            TaskDataProvider dataProvider = new TaskDataProvider();
            dataProvider.setHash(hash);
            dataProvider.setTaskId(taskId);
            dataProvider.setMetaDataId(metaDataId);
            dataProvider.setMetaDataName(metaDataName);
            dataProvider.setIdentityId(dataSupplier.getIdentityId());
            dataProvider.setPartyId(dataSupplier.getPartyId());
            taskDataProviderList.add(dataProvider);
            //搜集taskOrg， 数据提供方
            taskOrgMap.put(dataSupplier.getIdentityId(),
                    new TaskOrg(dataSupplier.getIdentityId(),
                            dataSupplier.getNodeName(),
                            dataSupplier.getNodeId()));
        }
        task.setDataSupplier(taskDataProviderList);
    }

}