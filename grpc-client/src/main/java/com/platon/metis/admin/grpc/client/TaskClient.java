package com.platon.metis.admin.grpc.client;

import com.google.protobuf.Empty;
import com.platon.metis.admin.common.exception.CallGrpcServiceFailed;
import com.platon.metis.admin.dao.entity.*;
import com.platon.metis.admin.grpc.channel.SimpleChannelManager;
import com.platon.metis.admin.grpc.common.CommonBase;
import com.platon.metis.admin.grpc.common.CommonData;
import com.platon.metis.admin.grpc.service.TaskRpcMessage;
import com.platon.metis.admin.grpc.service.TaskServiceGrpc;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.platon.metis.admin.grpc.constant.GrpcConstant.GRPC_SUCCESS_CODE;

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
     * 查看全部任务详情列表
     * @return
     */
    public Pair<List<Task>, Map<String, TaskOrg>> getLocalTaskList() {

        Channel channel = channelManager.getCarrierChannel();

        //2.构造 request
        Empty request = Empty.newBuilder().build();
        //3.调用rpc服务接口
        TaskRpcMessage.GetTaskDetailListResponse response = TaskServiceGrpc.newBlockingStub(channel).getTaskDetailList(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return dataConvertToTaskList(response.getTaskListList());
    }


    /**
     * 批量获取多个任务的全部事件列表
     * @param taskIds
     * @return
     */
    public List<TaskEvent> getTaskEventListData(List<String> taskIds) {

        Channel channel = channelManager.getCarrierChannel();

        //2.构造 request
        TaskRpcMessage.GetTaskEventListByTaskIdsRequest request = TaskRpcMessage.GetTaskEventListByTaskIdsRequest.newBuilder().addAllTaskIds(taskIds).build();
        //3.调用rpc服务接口
        TaskRpcMessage.GetTaskEventListResponse response = TaskServiceGrpc.newBlockingStub(channel).getTaskEventListByTaskIds(request);

        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        List<TaskEvent> taskEventList = new ArrayList<>();
        //构造taskEvent集合
        for (TaskRpcMessage.TaskEventShow taskEvenShow : response.getTaskEventListList()) {
            TaskEvent taskEvent = new TaskEvent();
            taskEvent.setTaskId(taskEvenShow.getTaskId());
            taskEvent.setEventAt(LocalDateTime.ofEpochSecond(taskEvenShow.getCreateAt()/1000,0, ZoneOffset.ofHours(8)));
            taskEvent.setEventContent(taskEvenShow.getContent());
            taskEvent.setEventType(taskEvenShow.getType());
            taskEvent.setIdentityId(taskEvenShow.getOwner().getIdentityId());
            taskEvent.setPartyId(taskEvenShow.getPartyId());
            //添加
            taskEventList.add(taskEvent);
        }
        return taskEventList;

    }


    /**
     * 任务集合数据转换
     * 并搜集所涉及的参与方信息，放入task_org中。因此，管理台只看到自己相关任务所涉及的其它组织的信息。
     * @param taskDetailList
     * @return
     */
    private Pair<List<Task>, Map<String, TaskOrg>> dataConvertToTaskList(List<TaskRpcMessage.GetTaskDetailResponse> taskDetailList){

        //key:identityId
        Map<String, TaskOrg> taskOrgMap = new HashMap<>();

        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < taskDetailList.size(); i++) {
            TaskRpcMessage.TaskDetailShow  taskDetail = taskDetailList.get(i).getInformation();
            //CommonBase.TaskRole role = taskDetailList.get(i).getRole();
            String taskId =  taskDetail.getTaskId();
            String taskName =  taskDetail.getTaskName();

            Long createAt = taskDetail.getCreateAt();
            Long startAt = taskDetail.getStartAt();
            Long endAt = taskDetail.getEndAt();
            CommonBase.TaskState state = taskDetail.getState();
            CommonData.TaskResourceCostDeclare operationCost = taskDetail.getOperationCost();
            String desc = taskDetail.getDesc();

            //构造Task
            Task task = new Task();
            task.setTaskId(taskId);
            task.setTaskName(taskName);
            task.setCreateAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(createAt), ZoneOffset.UTC));
            task.setStartAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(startAt), ZoneOffset.UTC));
            task.setEndAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(endAt), ZoneOffset.UTC));
            task.setStatus(state.getNumber());
            task.setAuthAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(endAt), ZoneOffset.UTC));
            task.setOwnerIdentityId(taskDetail.getSender().getIdentityId());
            task.setOwnerPartyId(taskDetail.getSender().getPartyId());
            task.setCostCore(operationCost.getProcessor());
            task.setCostMemory(operationCost.getMemory());
            task.setCostBandwidth(operationCost.getBandwidth());
            task.setDuration(operationCost.getDuration());
            task.setApplyUser(taskDetail.getUser()); // 发起任务的用户的信息
            task.setUserType(taskDetail.getUserType().getNumber()); //发起任务的用户类型 (0: 未定义; 1: 第二地址; 2: 测试网地址; 3: 主网地址)


            //搜集taskOrg，任务发起者
            taskOrgMap.put(taskDetail.getSender().getIdentityId(), new TaskOrg(taskDetail.getSender().getIdentityId(), taskDetail.getSender().getNodeName(), taskDetail.getSender().getNodeId()));

            // 算法提供方algoSupplier
            CommonBase.TaskOrganization algoSupplier = taskDetail.getAlgoSupplier();
            TaskAlgoProvider algoProvider = new TaskAlgoProvider();
            algoProvider.setTaskId(taskId);
            algoProvider.setIdentityId(algoSupplier.getIdentityId());
            algoProvider.setPartyId(algoSupplier.getPartyId());
            task.setAlgoSupplier(algoProvider);

            //搜集taskOrg， 算法提供方
            taskOrgMap.put(taskDetail.getAlgoSupplier().getIdentityId(), new TaskOrg(taskDetail.getAlgoSupplier().getIdentityId(), taskDetail.getAlgoSupplier().getNodeName(), taskDetail.getAlgoSupplier().getNodeId()));

            //数据提供方dataSupplierList
            List<TaskDataProvider> taskDataProviderList = new ArrayList<>();
            for (TaskRpcMessage.TaskDataSupplierShow dataSupplier : taskDetail.getDataSuppliersList()) {
                TaskDataProvider dataProvider = new TaskDataProvider();
                dataProvider.setTaskId(taskId);
                dataProvider.setMetaDataId(dataSupplier.getMetadataId());
                dataProvider.setMetaDataName(dataSupplier.getMetadataName());
                dataProvider.setIdentityId(dataSupplier.getOrganization().getIdentityId());
                dataProvider.setPartyId(dataSupplier.getOrganization().getPartyId());
                //dataProvider.setMetaDataName(dataSupplier.getMetadataName());
                //dataProvider.setDynamicFields(getDynamicFields(dataSupplier.getOrganization().getNodeName(), dataSupplier.getOrganization().getNodeId()));
                taskDataProviderList.add(dataProvider);
                //搜集taskOrg， 数据提供方
                taskOrgMap.put(dataSupplier.getOrganization().getIdentityId(), new TaskOrg(dataSupplier.getOrganization().getIdentityId(), dataSupplier.getOrganization().getNodeName(), dataSupplier.getOrganization().getNodeId()));
            }
            task.setDataSupplier(taskDataProviderList);

            //算力提供方powerSupplierList
            List<TaskPowerProvider> taskPowerProviderList = new ArrayList<>();
            for (TaskRpcMessage.TaskPowerSupplierShow powerSupplier : taskDetail.getPowerSuppliersList()) {
                TaskPowerProvider powerProvider = new TaskPowerProvider();
                powerProvider.setTaskId(taskId);
                powerProvider.setIdentityId(powerSupplier.getOrganization().getIdentityId());
                powerProvider.setPartyId(powerSupplier.getOrganization().getPartyId());
                powerProvider.setUsedCore(powerSupplier.getPowerInfo().getUsedProcessor());
                powerProvider.setTotalCore(powerSupplier.getPowerInfo().getTotalProcessor());
                powerProvider.setUsedMemory(powerSupplier.getPowerInfo().getUsedMem());
                powerProvider.setTotalMemory(powerSupplier.getPowerInfo().getTotalMem());
                powerProvider.setUsedBandwidth(powerSupplier.getPowerInfo().getUsedBandwidth());
                powerProvider.setTotalBandwidth(powerSupplier.getPowerInfo().getTotalBandwidth());
                //powerProvider.setDynamicFields(getDynamicFields(powerSupplier.getOrganization().getNodeName(), taskPowerSupplierShow.getOrganization().getNodeId()));
                taskPowerProviderList.add(powerProvider);

                //搜集taskOrg， 算力提供方
                taskOrgMap.put(powerSupplier.getOrganization().getIdentityId(), new TaskOrg(powerSupplier.getOrganization().getIdentityId(), powerSupplier.getOrganization().getNodeName(), powerSupplier.getOrganization().getNodeId()));

            }
            task.setPowerSupplier(taskPowerProviderList);

            //任务结果方receiverList
            List<TaskResultConsumer> resultConsumerList = new ArrayList<>();

            for (CommonBase.TaskOrganization receiver : taskDetail.getReceiversList()) {
                TaskResultConsumer resultConsumer = new TaskResultConsumer();
                resultConsumer.setTaskId(taskId);
                resultConsumer.setConsumerIdentityId(receiver.getIdentityId());
                resultConsumer.setConsumerPartyId(receiver.getPartyId());
                //待商榷？？
                //resultConsumer.setProducerIdentityId(receiver.getIdentityId());
                //resultConsumer.setDynamicFields(getDynamicFields(receiver.getNodeName(), receiver.getNodeId()));
                resultConsumerList.add(resultConsumer);

                //搜集taskOrg， 算力提供方
                taskOrgMap.put(receiver.getIdentityId(), new TaskOrg(receiver.getIdentityId(), receiver.getNodeName(), receiver.getNodeId()));

            }
            task.setReceivers(resultConsumerList);

            //构造完毕添加数据
            taskList.add(task);
        }
        return new ImmutablePair<>(taskList, taskOrgMap);
    }

    /**
     * 动态数据
     * @param nodeName
     * @param nodeId
     * @return
     */
    private Map getDynamicFields(String nodeName, String nodeId){
        Map dynamicFields = new HashMap();
        dynamicFields.put(NODE_NAME, nodeName);
        dynamicFields.put(NODE_ID, nodeId);
        return dynamicFields;
    }









}
