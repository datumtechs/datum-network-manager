package com.platon.rosettanet.admin.grpc.client;

import com.platon.rosettanet.admin.common.exception.ApplicationException;
import com.platon.rosettanet.admin.dao.entity.*;
import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.constant.GrpcConstant;
import com.platon.rosettanet.admin.grpc.entity.TaskDataResp;
import com.platon.rosettanet.admin.grpc.entity.TaskEventDataResp;
import com.platon.rosettanet.admin.grpc.service.CommonMessage;
import com.platon.rosettanet.admin.grpc.service.TaskRpcMessage;
import com.platon.rosettanet.admin.grpc.service.TaskServiceGrpc;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
    public TaskDataResp getTaskListData() {


        try {
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //channel = channelManager.getChannel(SERVER_HOST,SERVER_IP);
            //2.构造 request
            CommonMessage.EmptyGetParams request = CommonMessage.EmptyGetParams.newBuilder().build();
            //3.调用rpc服务接口
            TaskRpcMessage.GetTaskDetailListResponse taskDetailListResponse = TaskServiceGrpc.newBlockingStub(channel).getTaskDetailList(request);
            log.debug("====> RPC客户端请求响应 [获取任务列表数据: getTaskDetailList]:" + taskDetailListResponse.getMsg() + " ,  taskList Size:" + taskDetailListResponse.getTaskListList().size());
            //4.处理response
            TaskDataResp taskDataResp = new TaskDataResp();
            if(taskDetailListResponse != null){
                 taskDataResp.setStatus(taskDetailListResponse.getStatus());
                 taskDataResp.setMsg(taskDetailListResponse.getMsg());
                 List<TaskRpcMessage.GetTaskDetailResponse> taskDetailList = taskDetailListResponse.getTaskListList();
                 //log.debug("====> RPC客户端请求响应 [获取任务列表数据: taskDetailList]:" + taskDetailList.toString());
                 if(GrpcConstant.GRPC_SUCCESS_CODE == taskDetailListResponse.getStatus()){
                     taskDataResp.setTaskList(dataConvertToTaskList(taskDetailList));
                 }
             }
            return taskDataResp;
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
                List<TaskRpcMessage.TaskEvent> taskEventShowList = taskEventListResponse.getTaskEventListList();

                if(GrpcConstant.GRPC_SUCCESS_CODE == taskEventListResponse.getStatus()){
                    List<TaskEvent> taskEventList = new ArrayList<>();
                    //构造taskEvent集合
                    for (TaskRpcMessage.TaskEvent taskEvenShow : taskEventShowList) {
                            TaskEvent taskEvent = new TaskEvent();
                            taskEvent.setTaskId(taskEvenShow.getTaskId());
                            taskEvent.setEventAt(LocalDateTime.ofEpochSecond(taskEvenShow.getCreateAt()/1000,0, ZoneOffset.ofHours(8)));
                            taskEvent.setEventContent(taskEvenShow.getContent());
                            taskEvent.setEventType(taskEvenShow.getType());
                            taskEvent.setIdentityId(taskEvenShow.getOwner().getIdentityId());
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
     * @param taskDetailList
     * @return
     */
    private List<Task> dataConvertToTaskList(List<TaskRpcMessage.GetTaskDetailResponse> taskDetailList){

        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < taskDetailList.size(); i++) {
            TaskRpcMessage.TaskDetail  taskDetail = taskDetailList.get(i).getInformation();
            String role = taskDetailList.get(i).getRole();
            String taskId =  taskDetail.getTaskId();
            String taskName =  taskDetail.getTaskName();
            CommonMessage.TaskOrganizationIdentityInfo owner = taskDetail.getSender();
            CommonMessage.TaskOrganizationIdentityInfo algoSupplier = taskDetail.getAlgoSupplier();
            List<TaskRpcMessage.TaskDataSupplier> dataSupplierList = taskDetail.getDataSupplierList();
            List<TaskRpcMessage.TaskPowerSupplier> powerSupplierList = taskDetail.getPowerSupplierList();
            List<CommonMessage.TaskOrganizationIdentityInfo> receiverList = taskDetail.getReceiversList();
            Long createAt = taskDetail.getCreateAt();
            Long startAt = taskDetail.getStartAt();
            Long endAt = taskDetail.getEndAt();
            String state = taskDetail.getState();
            TaskRpcMessage.TaskOperationCostDeclare operationCost = taskDetail.getOperationCost();

            //构造Task
            Task task = new Task();
            task.setTaskId(taskId);
            task.setTaskName(taskName);
            task.setRole(role);
            task.setCreateAt(LocalDateTime.ofEpochSecond(createAt/1000,0, ZoneOffset.ofHours(8)));
            task.setStartAt(LocalDateTime.ofEpochSecond(startAt/1000,0, ZoneOffset.ofHours(8)));
            task.setEndAt(LocalDateTime.ofEpochSecond(endAt/1000,0, ZoneOffset.ofHours(8)));
            task.setStatus(state);
            task.setAuthAt(LocalDateTime.ofEpochSecond(endAt/1000,0, ZoneOffset.ofHours(8)));
            task.setOwnerIdentityId(owner.getIdentityId());
            task.setAlgIdentityId(algoSupplier.getIdentityId());
            task.setCostCore(operationCost.getCostProcessor());
            task.setCostMemory(operationCost.getCostMem());
            task.setCostBandwidth(operationCost.getCostBandwidth());
            task.setDuration(LocalDateTime.ofEpochSecond(operationCost.getDuration()/1000,0, ZoneOffset.ofHours(8)));

            //任务发起发owner
            TaskOrg ownerData = new TaskOrg();
            ownerData.setName(owner.getName());
            ownerData.setCarrierNodeId(owner.getNodeId());
            ownerData.setIdentityId(owner.getIdentityId());
            task.setOwner(ownerData);

            // 算法提供方algoSupplier
            TaskOrg algoSupplierData = new TaskOrg();
            algoSupplierData.setName(algoSupplier.getName());
            algoSupplierData.setCarrierNodeId(algoSupplier.getNodeId());
            algoSupplierData.setIdentityId(algoSupplier.getIdentityId());
            task.setAlgoSupplier(algoSupplierData);

            //数据提供方dataSupplierList
            List<TaskDataReceiver> taskDataReceiverList = new ArrayList<>();
            for (TaskRpcMessage.TaskDataSupplier dataSupplier : dataSupplierList) {
                TaskDataReceiver receiver = new TaskDataReceiver();
                receiver.setTaskId(taskId);
                receiver.setMetaDataId(dataSupplier.getMetaDataId());
                receiver.setIdentityId(dataSupplier.getMemberInfo().getIdentityId());
                receiver.setMetaDataName(dataSupplier.getMetaDataName());
                receiver.setDynamicFields(getDynamicFields(dataSupplier.getMemberInfo().getName(), dataSupplier.getMemberInfo().getNodeId()));
                taskDataReceiverList.add(receiver);
            }
            task.setDataSupplier(taskDataReceiverList);

            //算力提供方powerSupplierList
            List<TaskPowerProvider> taskPowerProviderList = new ArrayList<>();
            for (TaskRpcMessage.TaskPowerSupplier taskPowerSupplierShow : powerSupplierList) {
                TaskPowerProvider powerProvider = new TaskPowerProvider();
                powerProvider.setTaskId(taskId);
                powerProvider.setIdentityId(taskPowerSupplierShow.getMemberInfo().getIdentityId());
                powerProvider.setUsedCore(taskPowerSupplierShow.getPowerInfo().getUsedProcessor());
                powerProvider.setTotalCore(taskPowerSupplierShow.getPowerInfo().getTotalProcessor());
                powerProvider.setUsedMemory(taskPowerSupplierShow.getPowerInfo().getUsedMem());
                powerProvider.setTotalMemory(taskPowerSupplierShow.getPowerInfo().getTotalMem());
                powerProvider.setUsedBandwidth(taskPowerSupplierShow.getPowerInfo().getUsedBandwidth());
                powerProvider.setTotalBandwidth(taskPowerSupplierShow.getPowerInfo().getTotalBandwidth());
                powerProvider.setDynamicFields(getDynamicFields(taskPowerSupplierShow.getMemberInfo().getName(), taskPowerSupplierShow.getMemberInfo().getNodeId()));
                taskPowerProviderList.add(powerProvider);
            }
            task.setPowerSupplier(taskPowerProviderList);

            //任务结果方receiverList
            List<TaskResultReceiver> receivers = new ArrayList<>();
            for (CommonMessage.TaskOrganizationIdentityInfo receiver : receiverList) {
                TaskResultReceiver resultReceiver = new TaskResultReceiver();
                resultReceiver.setTaskId(taskId);
                resultReceiver.setConsumerIdentityId(receiver.getIdentityId());
                //待商榷？？
                resultReceiver.setProducerIdentityId(receiver.getIdentityId());
                resultReceiver.setDynamicFields(getDynamicFields(receiver.getName(), receiver.getNodeId()));
                receivers.add(resultReceiver);
            }
            task.setReceivers(receivers);

            //构造完毕添加数据
            taskList.add(task);
        }
        return taskList;
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
