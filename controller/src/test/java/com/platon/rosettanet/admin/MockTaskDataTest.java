package com.platon.rosettanet.admin;

import com.platon.rosettanet.admin.dao.*;
import com.platon.rosettanet.admin.dao.entity.*;
import com.platon.rosettanet.admin.dao.enums.TaskStatusEnum;
import com.platon.rosettanet.admin.grpc.constant.GrpcConstant;
import com.platon.rosettanet.admin.grpc.entity.TaskDataResp;
import com.platon.rosettanet.admin.grpc.entity.TaskEventDataResp;
import com.platon.rosettanet.admin.grpc.service.CommonMessage;
import com.platon.rosettanet.admin.grpc.service.TaskRpcMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional //这个有看需要，测试方法如果要作为一个整体事务，则加上
@Rollback(false) // 默认值：true, UT默认都会回滚数据库，不会增加新数据
public class MockTaskDataTest {


    private static final String NODE_NAME = "nodeName";
    private static final String NODE_ID = "nodeId";

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



    @Test
    public void test() {


        List<TaskRpcMessage.GetTaskDetailResponse> taskDetailList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            //owner
            CommonMessage.TaskOrganizationIdentityInfo ownerData = CommonMessage.TaskOrganizationIdentityInfo.newBuilder()
                    .setName("ownerName" + i)
                    .setIdentityId("ownerIdentityId" + i)
                    .setNodeId("ownerNodeId" + i)
                    .build();
            //algoSupplier
            CommonMessage.TaskOrganizationIdentityInfo algoSupplierData = CommonMessage.TaskOrganizationIdentityInfo.newBuilder()
                    .setName("algoSupplierName" + i)
                    .setIdentityId("algoSupplierIdentityId" + i)
                    .setNodeId("algoSupplierNodeId" + i)
                    .build();

            //dataSupplier
            CommonMessage.TaskOrganizationIdentityInfo memberInfo = CommonMessage.TaskOrganizationIdentityInfo.newBuilder()
                    .setName("memberInfoName" + i)
                    .setIdentityId("memberInfoIdentityId" + i)
                    .setNodeId("memberInfoNodeId" + i)
                    .build();
            TaskRpcMessage.TaskDataSupplierShow dataSupplierShow = TaskRpcMessage.TaskDataSupplierShow.newBuilder()
                    .setMetaDataId("dataSupplierMetaDataId" + i)
                    .setMetaDataName("dataSupplierMetaDataName" + i)
                    .setMemberInfo(memberInfo)
                    .build();

            //powerSupplierShow
            CommonMessage.ResourceUsedDetailShow resourceUsedDetailShow = CommonMessage.ResourceUsedDetailShow.newBuilder()
                    .setUsedBandwidth(10000)
                    .setUsedMem(100)
                    .setUsedProcessor(100)
                    .setTotalBandwidth(2000)
                    .setTotalMem(200)
                    .setTotalProcessor(300)
                    .build();
            TaskRpcMessage.TaskPowerSupplierShow powerSupplierShow = TaskRpcMessage.TaskPowerSupplierShow.newBuilder()
                    .setPowerInfo(resourceUsedDetailShow)
                    .setMemberInfo(memberInfo)
                    .build();


            //taskOperationCostDeclare
            TaskRpcMessage.TaskOperationCostDeclare taskOperationCostDeclare = TaskRpcMessage.TaskOperationCostDeclare.newBuilder()
                    .setCostBandwidth(666)
                    .setCostMem(555)
                    .setCostProcessor(333)
                    .setDuration(1626250561)
                    .build();

            //taskDetailShow
            TaskRpcMessage.TaskDetailShow taskDetailShow = TaskRpcMessage.TaskDetailShow.newBuilder()
                    .setTaskId("taskId" + i)
                    .setTaskName("taskName" + i)
                    .setOwner(ownerData)
                    .setAlgoSupplier(algoSupplierData)
                    .addDataSupplier(dataSupplierShow)
                    .addPowerSupplier(powerSupplierShow)
                    .addReceivers(memberInfo)
                    .setCreateAt(1626244939)
                    .setStartAt(1626244938)
                    .setEndAt(1626244938)
                    .setState("success")
                    .setOperationCost(taskOperationCostDeclare)
                    .build();
            TaskRpcMessage.GetTaskDetailResponse response = TaskRpcMessage.GetTaskDetailResponse.newBuilder().setInformation(taskDetailShow).build();
            taskDetailList.add(response);
        }


        //---------------------------    构造response data  --------------------------

        TaskDataResp taskDataResp = new TaskDataResp();
        taskDataResp.setStatus(0);
        taskDataResp.setMsg("Success");
        taskDataResp.setTaskList(dataConvertToTaskList(taskDetailList));


        //数据拆解并持计划DB
        List<Task> taskList = taskDataResp.getTaskList();
        List<TaskDataReceiver> dataReceiverList = new ArrayList<>();
        List<TaskPowerProvider> powerProviderList = new ArrayList<>();
        List<TaskResultReceiver> resultReceiverList = new ArrayList<>();
        List<TaskOrg> taskOrgList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(taskList)){
            for (int i = 0; i < taskList.size(); i++) {
                Task taskData = taskList.get(i);
                List<TaskDataReceiver> dataReceivers = taskData.getDataSupplier();
                List<TaskPowerProvider> powerProviders = taskData.getPowerSupplier();
                List<TaskResultReceiver> resultReceivers = taskData.getReceivers();
                List<TaskOrg> taskOrgs = getTaskOrgList(taskData);
                //构造数据
                dataReceiverList.addAll(dataReceivers);
                powerProviderList.addAll(powerProviders);
                resultReceiverList.addAll(resultReceivers);
                taskOrgList.addAll(taskOrgs);
            }

            //批量更新DB
            taskMapper.insertBatch(taskList);
            taskDataReceiverMapper.insertBatch(dataReceiverList);
            taskPowerProviderMapper.insertBatch(powerProviderList);
            taskResultReceiverMapper.insertBatch(resultReceiverList);
            taskOrgMapper.insertBatch(taskOrgList);
        }

        //批量TaskEvent获取并更新DB
        List<TaskEvent> taskEventList = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            List<TaskEvent> taskEvents = getRpcTaskEventByTaskId(taskList.get(i).getId());
            taskEventList.addAll(taskEvents);
        }
        taskEventMapper.insertBatch(taskEventList);
    }

    private List<TaskEvent> getRpcTaskEventByTaskId(String id) {

        List<TaskOrg> allTaskOrgList = taskOrgMapper.selectAllTaskOrg();

        List<TaskEvent> taskEventList = new ArrayList<>();
        //构造taskEvent集合
        for (int i = 0; i < 5; i++) {
            TaskEvent taskEvent = new TaskEvent();
            taskEvent.setTaskId(id);
            taskEvent.setEventAt(LocalDateTime.ofEpochSecond(1626335083,0, ZoneOffset.ofHours(8)));
            taskEvent.setEventContent("taskEventContent" + i);
            taskEvent.setEventType("taskEventType");
            String identityId = allTaskOrgList.get(i).getIdentityId();
            identityId = (identityId == null) ? "" : identityId;
            taskEvent.setIdentityId(identityId);
            //添加
            taskEventList.add(taskEvent);
        }
        return taskEventList;
    }


    private List<TaskOrg> getTaskOrgList(Task taskData){

        List<TaskOrg> taskOrgList = new ArrayList<>();

        List<TaskDataReceiver> dataReceivers = taskData.getDataSupplier();
        List<TaskPowerProvider> powerProviders = taskData.getPowerSupplier();
        List<TaskResultReceiver> resultReceivers = taskData.getReceivers();

        TaskOrg owner = taskData.getOwner();
        owner.setTaskId(taskData.getId());

        TaskOrg algoSupplier = taskData.getAlgoSupplier();
        algoSupplier.setTaskId(taskData.getId());

        for (TaskDataReceiver dataReceiver : dataReceivers) {
              String identityId = dataReceiver.getIdentityId();
              Map<String,String> dynamicFields = dataReceiver.getDynamicFields();
              String nodeName = dynamicFields.get(NODE_NAME);
              String nodeId = dynamicFields.get(NODE_ID);

              TaskOrg taskOrg = new TaskOrg();
              taskOrg.setTaskId(taskData.getId());
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
            taskOrg.setTaskId(taskData.getId());
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
            taskOrg.setTaskId(taskData.getId());
            taskOrg.setIdentityId(identityId);
            taskOrg.setCarrierNodeId(nodeId);
            taskOrg.setName(nodeName);
            taskOrgList.add(taskOrg);
        }

        taskOrgList.add(owner);
        taskOrgList.add(algoSupplier);
        return taskOrgList;
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
                //动态数据
                Map dynamicFields = new HashMap();
                dynamicFields.put(NODE_NAME, dataSupplier.getMemberInfo().getName());
                dynamicFields.put(NODE_ID, dataSupplier.getMemberInfo().getNodeId());
                receiver.setDynamicFields(dynamicFields);
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

                //动态数据
                Map dynamicFields = new HashMap();
                dynamicFields.put(NODE_NAME, taskPowerSupplierShow.getMemberInfo().getName());
                dynamicFields.put(NODE_ID, taskPowerSupplierShow.getMemberInfo().getNodeId());
                powerProvider.setDynamicFields(dynamicFields);
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

                //动态数据
                Map dynamicFields = new HashMap();
                dynamicFields.put(NODE_NAME, receiver.getName());
                dynamicFields.put(NODE_ID, receiver.getNodeId());
                resultReceiver.setDynamicFields(dynamicFields);
                receivers.add(resultReceiver);
            }
            task.setReceivers(receivers);

            task.setCreateAt(LocalDateTime.ofEpochSecond(createAt,0, ZoneOffset.ofHours(8)));
            task.setStartAt(LocalDateTime.ofEpochSecond(startAt,0, ZoneOffset.ofHours(8)));
            task.setEndAt(LocalDateTime.ofEpochSecond(endAt,0, ZoneOffset.ofHours(8)));
            task.setStatus(state);
            task.setAuthAt(LocalDateTime.ofEpochSecond(endAt,0, ZoneOffset.ofHours(8)));
            task.setOwnerIdentityId("123456789");

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
