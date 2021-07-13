package com.platon.rosettanet.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.*;
import com.platon.rosettanet.admin.dao.entity.*;
import com.platon.rosettanet.admin.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        public Page<Task> listTask(String status, Integer role, Long startTimestamp, Long endTimestamp, String keyWord, Integer pageNumber, Integer pageSize) {

            Page<Task> taskPage = PageHelper.startPage(pageNumber, pageSize);
            List<Task> taskList = taskMapper.listTask(status,role, new Timestamp(startTimestamp), new Timestamp(endTimestamp),keyWord, LocalOrgIdentityCache.getIdentityId());
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
}
