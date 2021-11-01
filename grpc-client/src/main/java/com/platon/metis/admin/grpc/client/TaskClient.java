package com.platon.metis.admin.grpc.client;

import com.google.protobuf.Empty;
import com.platon.metis.admin.common.exception.ApplicationException;
import com.platon.metis.admin.dao.entity.*;
import com.platon.metis.admin.grpc.channel.BaseChannelManager;
import com.platon.metis.admin.grpc.common.CommonBase;
import com.platon.metis.admin.grpc.common.CommonData;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.entity.TaskEventDataResp;
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

    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;

    public static  final String SERVER_HOST = "192.168.21.164";
    public static  final int SERVER_IP = 4444;
    private Channel channel;


    /**
     * 查看全部任务详情列表
     * @return
     */
    public Pair<List<Task>, Map<String, TaskOrg>> getLocalTaskList() {


        try {
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //channel = channelManager.getChannel(SERVER_HOST,SERVER_IP);
            //2.构造 request
            Empty request = Empty.newBuilder().build();
            //3.调用rpc服务接口
            TaskRpcMessage.GetTaskDetailListResponse taskDetailListResponse = TaskServiceGrpc.newBlockingStub(channel).getTaskDetailList(request);
            log.debug("====> RPC客户端请求响应 [获取任务列表数据: getTaskDetailList]:" + taskDetailListResponse.getMsg() + " ,  taskList Size:" + taskDetailListResponse.getTaskListList().size());
            //4.处理response
            if(GrpcConstant.GRPC_SUCCESS_CODE == taskDetailListResponse.getStatus()){
                return dataConvertToTaskList(taskDetailListResponse.getTaskListList());
            }else{
                log.error("获取任务列表,调度服务调用失败");
                return null;
            }
        } catch (ApplicationException e) {
                 e.printStackTrace();
        } finally {
            //channelManager.closeChannel(channel);
        }
        return null;
    }


    /**
     * 批量获取多个任务的全部事件列表
     * @param taskIds
     * @return
     */
    public TaskEventDataResp getTaskEventListData(List<String> taskIds) {

        try {
            //1.获取rpc连接
            if(channel == null){
                 channel = channelManager.getScheduleServer();
            }
            //2.构造 request
            TaskRpcMessage.GetTaskEventListByTaskIdsRequest request = TaskRpcMessage.GetTaskEventListByTaskIdsRequest.newBuilder().addAllTaskIds(taskIds).build();
            //3.调用rpc服务接口
            TaskRpcMessage.GetTaskEventListResponse taskEventListResponse = TaskServiceGrpc.newBlockingStub(channel).getTaskEventListByTaskIds(request);
            log.debug("====> RPC客户端 taskEventListResponse:" + taskEventListResponse.getMsg() +" , taskEventList Size:"+ taskEventListResponse.getTaskEventListList().size());

            //4.处理response
            TaskEventDataResp taskEventDataResp = new TaskEventDataResp();
            if(taskEventListResponse != null){
                taskEventDataResp.setStatus(taskEventListResponse.getStatus());
                taskEventDataResp.setMsg(taskEventListResponse.getMsg());
                List<TaskRpcMessage.TaskEventShow> taskEventShowList = taskEventListResponse.getTaskEventListList();

                if(GrpcConstant.GRPC_SUCCESS_CODE == taskEventListResponse.getStatus()){
                    List<TaskEvent> taskEventList = new ArrayList<>();
                    //构造taskEvent集合
                    for (TaskRpcMessage.TaskEventShow taskEvenShow : taskEventShowList) {
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
                    taskEventDataResp.setTaskEventList(taskEventList);
                }
            }
            return taskEventDataResp;
        } catch (ApplicationException e) {
            e.printStackTrace();
        } finally {
            channelManager.closeChannel(channel);
        }
        return null;
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
            task.setApplyUser(taskDetail.getTaskId()); //todo 待确认是否删除
            task.setUserType(taskDetail.getUserType().getNumber()); //todo 待确认是否删除


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
                //powerProvider.setTotalCore(powerSupplier.getPowerInfo().getTotalProcessor());
                powerProvider.setUsedMemory(powerSupplier.getPowerInfo().getUsedMem());
                //powerProvider.setTotalMemory(powerSupplier.getPowerInfo().getTotalMem());
                powerProvider.setUsedBandwidth(powerSupplier.getPowerInfo().getUsedBandwidth());
                //powerProvider.setTotalBandwidth(powerSupplier.getPowerInfo().getTotalBandwidth());
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