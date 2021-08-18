package com.platon.rosettanet.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.*;
import com.platon.rosettanet.admin.dao.entity.*;
import com.platon.rosettanet.admin.dao.enums.RoleEnum;
import com.platon.rosettanet.admin.dao.enums.TaskStatusEnum;
import com.platon.rosettanet.admin.service.TaskService;
import com.platon.rosettanet.admin.service.constant.ServiceConstant;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Consumer;

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

            String roleParam = RoleEnum.getMessageByCode(role);
            Timestamp startTimestampParam = (startTimestamp == 0) ? null : new Timestamp(startTimestamp);
            Timestamp endTimestampParam = (endTimestamp == 0) ? null : new Timestamp(endTimestamp);

            Page<Task> taskPage = PageHelper.startPage(pageNumber, pageSize);
            List<Task> taskList = taskMapper.listTask(status, roleParam, startTimestampParam, endTimestampParam, keyWord);
            taskList.forEach(task -> {
                if(checkEndAtOver72Hour(task)){
                    task.setReviewed(true);
                }
            });
            /**
             * 排序规则：
             * 1、任务已完成（”成功“/”失败“）但从未被查看，则将其置顶，并标记为“新”；
             * 2、进行中任务，即”等待中“和”计算中“的任务置顶，按照发起时间排列；
             * 3、已结束任务，即“成功”和“失败”的任务按照发起时间排列。
             */
            Collections.sort(taskList, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    Boolean value1 = isTaskSucceeFailUnRead(o1);
                    Boolean value2 = isTaskSucceeFailUnRead(o2);
                    //对描述1场景，进行排序
                    if(0 < value1.compareTo(value2)){
                        return -1;
                    }else if(0 > value1.compareTo(value2)){
                        return 1;
                    }else if(0 == value1.compareTo(value2)){
                         //对描述2、3场景，进行排序
                         Boolean value3 = isTaskStatusPendAndRunning(o1);
                         Boolean value4 = isTaskStatusPendAndRunning(o2);
                         if(0 < value3.compareTo(value4)){
                              return -1;
                         }else if(0 > value3.compareTo(value4)){
                             return 1;
                         }
                    }
                    return 0;
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
    public TaskStatistics selectTaskStatisticsCount() {
        return taskMapper.selectTaskStatisticsCount();
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
        //更新task查看状态
        taskMapper.updateTaskReviewedById(taskId,true);
        //是否任务结束超过72小时，非最新(已查看)
        if(checkEndAtOver72Hour(task)){
            task.setReviewed(true);
        }
        return task;
    }

    @Override
    public Task selectTaskByTaskId(String taskId) {
        return taskMapper.selectTaskByTaskId(taskId);
    }

    @Override
    public List<TaskEvent> listTaskEvent(String taskId) {
        List<TaskEvent> taskEventList = taskEventMapper.listTaskEventByTaskId(taskId);
        if(!CollectionUtils.isEmpty(taskEventList)){
            //更新task查看状态
            taskMapper.updateTaskReviewedById(taskId,true);
        }
        return taskEventList;
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

    /**
     * 任务状态是否为PENDING/RUNNING
     * @param task
     * @return
     */
    private boolean isTaskStatusPendAndRunning(Task task){
        String status = task.getStatus();
        return TaskStatusEnum.PENDING.getStatus().equals(status) || TaskStatusEnum.RUNNING.getStatus().equals(status);
    }

    /**
     * 检查任务结束时间是否超过72小时
     * @param task
     * @return
     */
    private boolean checkEndAtOver72Hour(Task task){
        boolean isOver = false;
        if(!Objects.isNull(task.getStatus()) && isTaskSucceeFailUnRead(task)){
            long currentTime = new Date().getTime();
            long endAtTime = task.getEndAt().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            isOver = (currentTime - endAtTime) > ServiceConstant.TIME_HOUR_72;
            return isOver;
        }
        return isOver;
    }
}
