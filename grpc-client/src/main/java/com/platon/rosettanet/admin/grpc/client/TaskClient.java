package com.platon.rosettanet.admin.grpc.client;

import com.platon.rosettanet.admin.dao.entity.*;
import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.constant.GrpcConstant;
import com.platon.rosettanet.admin.grpc.entity.QueryNodeResp;
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
import java.util.List;

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


    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;


    /**
     * 查看全部任务详情列表
     * @return
     */
    public TaskDataResp getTaskListData() {

        //1.获取rpc连接
        Channel channel = channelManager.getScheduleServer();
        //2.构造 request
        CommonMessage.EmptyGetParams request = CommonMessage.EmptyGetParams.newBuilder().build();
        //3.调用rpc服务接口
        TaskRpcMessage.GetTaskDetailListResponse taskDetailListResponse = TaskServiceGrpc.newBlockingStub(channel).getTaskDetailList(request);
        //4.处理response
        TaskDataResp taskDataResp = new TaskDataResp();
        if(taskDetailListResponse != null){
             taskDataResp.setStatus(taskDetailListResponse.getStatus());
             taskDataResp.setMsg(taskDetailListResponse.getMsg());
             List<TaskRpcMessage.GetTaskDetailResponse> taskDetailList = taskDetailListResponse.getTaskListList();

             if(GrpcConstant.GRPC_SUCCESS_CODE == taskDetailListResponse.getStatus()){
                 taskDataResp.setTaskList(dataConvertToTaskList(taskDetailList));
             }

         }
         return taskDataResp;
    }


    /**
     * 查看某个任务的全部事件列表
     * @param taskId
     * @return
     */
    public TaskEventDataResp getTaskEventListData(String taskId) {

        //1.获取rpc连接
        Channel channel = channelManager.getScheduleServer();
        //2.构造 request
        TaskRpcMessage.GetTaskEventListRequest request = TaskRpcMessage.GetTaskEventListRequest.newBuilder().setTaskId(taskId).build();
        //3.调用rpc服务接口
        TaskRpcMessage.GetTaskEventListResponse taskEventListResponse = TaskServiceGrpc.newBlockingStub(channel).getTaskEventList(request);
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
                        taskEvent.setEventAt(LocalDateTime.ofEpochSecond(taskEvenShow.getCreateAt(),0, ZoneOffset.ofHours(8)));
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
    }




    /**
     * 任务集合数据转换
     * @param taskDetailList
     * @return
     */
    private List<Task> dataConvertToTaskList(List<TaskRpcMessage.GetTaskDetailResponse> taskDetailList){

        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < taskDetailList.size(); i++) {
            TaskRpcMessage.TaskDetailShow  taskDetail = taskDetailList.get(i).getInformation();
            String taskId =  taskDetail.getTaskId();
            String taskName =  taskDetail.getTaskName();
            CommonMessage.TaskOrganizationIdentityInfo owner = taskDetail.getOwner();
            CommonMessage.TaskOrganizationIdentityInfo algoSupplier = taskDetail.getAlgoSupplier();
            List<TaskRpcMessage.TaskDataSupplierShow> dataSupplierList = taskDetail.getDataSupplierList();
            List<TaskRpcMessage.TaskPowerSupplierShow> powerSupplierList = taskDetail.getPowerSupplierList();
            List<CommonMessage.TaskOrganizationIdentityInfo> receiverList = taskDetail.getReceiversList();
            Long createAt = taskDetail.getCreateAt();
            Long startAt = taskDetail.getStartAt();
            Long endAt = taskDetail.getEndAt();
            String state = taskDetail.getState();
            TaskRpcMessage.TaskOperationCostDeclare operationCost = taskDetail.getOperationCost();

            //构造Task
            Task task = new Task();
            task.setId(taskId);
            task.setTaskName(taskName);

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
            for (TaskRpcMessage.TaskDataSupplierShow dataSupplier : dataSupplierList) {
                TaskDataReceiver receiver = new TaskDataReceiver();
                receiver.setTaskId(taskId);
                receiver.setMetaDataId(dataSupplier.getMetaDataId());
                receiver.setIdentityId(dataSupplier.getMemberInfo().getIdentityId());
                receiver.setMetaDataName(dataSupplier.getMetaDataName());
                taskDataReceiverList.add(receiver);
            }
            task.setDataSupplier(taskDataReceiverList);

            //算力提供方powerSupplierList
            List<TaskPowerProvider> taskPowerProviderList = new ArrayList<>();
            for (TaskRpcMessage.TaskPowerSupplierShow taskPowerSupplierShow : powerSupplierList) {

                TaskPowerProvider powerProvider = new TaskPowerProvider();
                powerProvider.setTaskId(taskId);
                powerProvider.setIdentityId(taskPowerSupplierShow.getMemberInfo().getIdentityId());
                powerProvider.setUsedCore(taskPowerSupplierShow.getPowerInfo().getUsedProcessor());
                powerProvider.setUsedMemory(taskPowerSupplierShow.getPowerInfo().getUsedMem());
                powerProvider.setUsedBandwidth(taskPowerSupplierShow.getPowerInfo().getUsedBandwidth());
                taskPowerProviderList.add(powerProvider);
            }
            task.setPowerSupplier(taskPowerProviderList);

            //任务结果方receiverList
            List<TaskResultReceiver> receivers = new ArrayList<>();
            for (CommonMessage.TaskOrganizationIdentityInfo receiver : receiverList) {
                TaskResultReceiver resultReceiver = new TaskResultReceiver();
                resultReceiver.setTaskId(taskId);
                resultReceiver.setConsumerIdentityId(receiver.getIdentityId());
                //resultReceiver.setProducerIdentityId();
                receivers.add(resultReceiver);
            }
            task.setReceivers(receivers);

            task.setCreateAt(LocalDateTime.ofEpochSecond(createAt,0, ZoneOffset.ofHours(8)));
            task.setStartAt(LocalDateTime.ofEpochSecond(startAt,0, ZoneOffset.ofHours(8)));
            task.setEndAt(LocalDateTime.ofEpochSecond(endAt,0, ZoneOffset.ofHours(8)));
            task.setStatus(state);

            //声明所需资源
            task.setCostCore(operationCost.getCostProcessor());
            task.setCostMemory(operationCost.getCostMem());
            task.setCostBandwidth(operationCost.getCostBandwidth());
            task.setDuration(LocalDateTime.ofEpochSecond(operationCost.getDuration(),0, ZoneOffset.ofHours(8)));

            //构造完毕添加数据
            taskList.add(task);
        }
        return taskList;
    }




}
