package com.platon.rosettanet.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.dao.TaskEventMapper;
import com.platon.rosettanet.admin.dao.TaskMapper;
import com.platon.rosettanet.admin.dao.entity.*;
import com.platon.rosettanet.admin.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

       @Resource
        private TaskMapper taskMapper;

       @Resource
       private TaskEventMapper taskEventMapper;

        @Override
        public Page<Task> listTask(String status, int role, Date startDate, Date endDate, int pageNumber, int pageSize) {

            Page<Task> taskPage = PageHelper.startPage(pageNumber, pageSize);
            List<Task> taskList = taskMapper.listTask(status,role,startDate,endDate);
            return taskPage;
        }

    @Override
    public Task selectTaskByTaskId(String taskId) {
        return taskMapper.selectTaskByTaskId(taskId);
    }

    @Override
    public List<TaskEvent> listTaskEvent(String taskId) {

        List<TaskEvent> taskEventList = new ArrayList<>();
        List<TaskEvent> tbTaskEventList = taskEventMapper.listTbTaskEventByTaskId(taskId);
        for (int i = 0; i < tbTaskEventList.size(); i++) {
            TaskEvent tbTaskEvent = tbTaskEventList.get(i);
            TaskEvent taskEvent = new TaskEvent();
            taskEvent.setId(tbTaskEvent.getId());
            taskEvent.setType(tbTaskEvent.getType());
            taskEvent.setTaskId(tbTaskEvent.getTaskId());
            taskEvent.setContent(tbTaskEvent.getContent());
            taskEvent.setCreateAt(tbTaskEvent.getCreateAt());

            //事件产生方身份信息
            Owner owner = new Owner();
            owner.setNodeIdentityId(taskEvent.getOwnerIdentity());
            owner.setNodeName(taskEvent.getOwnerName());
            taskEvent.setOwner(owner);

            taskEventList.add(taskEvent);
        }

        return taskEventList;
    }
}
