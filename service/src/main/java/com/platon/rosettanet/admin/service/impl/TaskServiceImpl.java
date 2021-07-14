package com.platon.rosettanet.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.*;
import com.platon.rosettanet.admin.dao.entity.*;
import com.platon.rosettanet.admin.dao.enums.TaskStatusEnum;
import com.platon.rosettanet.admin.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {

       @Resource
        private TaskMapper taskMapper;

       @Resource
       private TaskEventMapper taskEventMapper;

       @Resource
       private TaskOrgMapper taskOrgMapper;

       @Resource
       private TaskResultReceiverMapper taskResultReceiverMapper;

       @Resource
       private TaskDataReceiverMapper taskDataReceiverMapper;

       @Resource
       private TaskPowerProviderMapper taskPowerProviderMapper;



        @Override
        public Page<Task> listTask(String status, Integer role, Long startTimestamp, Long endTimestamp, String keyWord, int pageNumber, int pageSize) {

            Integer roleParam = (role == null) ? -1 : role;
            Timestamp startTimestampParam = (startTimestamp == null) ? null : new Timestamp(startTimestamp);
            Timestamp endTimestampParam = (endTimestamp == null) ? null : new Timestamp(endTimestamp);

            Page<Task> taskPage = PageHelper.startPage(pageNumber, pageSize);
            List<Task> taskList = taskMapper.listTask(status, roleParam, startTimestampParam, endTimestampParam, keyWord);
            /**
             * 排序规则：
             * 1、任务已完成（成功/失败）但从未被查看，则将其置顶
             * 2、其他任务按照“发起时间”排列
             */
            Collections.sort(taskList, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    Boolean value1 = isTaskSucceeFailUnRead(o1);
                    Boolean value2 = isTaskSucceeFailUnRead(o2);
                    if(1 == value1.compareTo(value2)){
                        return 1;
                    }else if(-1 == value1.compareTo(value2)){
                        return -1;
                    }else{
                        return o1.getCreateAt().compareTo(o2.getCreateAt());
                    }
                    //return value1.compareTo(value2);
                }
            });

            return taskPage;
        }

    @Override
    public Integer selectAllTaskCount() {
        return taskMapper.selectAllTaskCount();
    }

    @Override
    public Integer selectTaskRunningCount() {
        return taskMapper.selectTaskRunningCount();
    }

    @Override
    public Task getTaskDetails(String taskId) {
        Task task = taskMapper.selectTaskByTaskId(taskId);
        task = (task == null) ? new Task() : task;
        //任务发起方身份信息
        TaskOrg owner = taskOrgMapper.selectTaskOrgByIdentityId(task.getOwnerIdentityId());
        task.setOwner(owner);
        //算法提供方
        TaskOrg algoSupplier = taskOrgMapper.selectTaskOrgByIdentityId(task.getAlgIdentityId());
        task.setAlgoSupplier(algoSupplier);
        //结果接收方
        List<TaskResultReceiver> resultReceiverList = taskResultReceiverMapper.selectTaskResultWithOrgByTaskId(taskId);
        task.setReceivers(resultReceiverList);
        //数据提供方
        List<TaskDataReceiver> dataSupplierList = taskDataReceiverMapper.selectTaskDataWithOrgByTaskId(taskId);
        task.setDataSupplier(dataSupplierList);
        //算力提供方
        List<TaskPowerProvider> powerSupplierList = taskPowerProviderMapper.selectTaskPowerWithOrgByTaskId(taskId);
        task.setPowerSupplier(powerSupplierList);
         //角色
        Integer role = taskMapper.selectTaskRole(taskId);
        task.setRole(role);
        //更新task查看状态
        taskMapper.updateTaskReviewedById(taskId,true);
        return task;
    }

    @Override
    public Task selectTaskByTaskId(String taskId) {
        return taskMapper.selectTaskByTaskId(taskId);
    }

    @Override
    public List<TaskEvent> listTaskEvent(String taskId) {
        return taskEventMapper.listTaskEventByTaskId(taskId);
    }


    /**
     * 是否未查看task
     * @param task
     * @return
     */
    private boolean isTaskSucceeFailUnRead(Task task){
        String status = task.getStatus();
        return (TaskStatusEnum.SUCCESS.getStatus().equals(status) || TaskStatusEnum.FAILED.getStatus().equals(status)) && !task.getReviewed();
    }
}
