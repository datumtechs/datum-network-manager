package com.platon.metis.admin.controller.task;

import com.platon.metis.admin.dao.entity.TaskOrg;
import com.platon.metis.admin.dto.req.TaskPageReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void listMyTask(){
        TaskPageReq req = new TaskPageReq();
        req.setPageNumber(1);
        req.setPageSize(10);
        req.setStartTime(0L);
        req.setEndTime(0L);
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/task/mytask/taskListByQuery", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void taskOrgTest(){
        Set<TaskOrg> taskOrgList = new HashSet<>();

        TaskOrg taskOrg = new TaskOrg();
        taskOrg.setIdentityId("identityId");
        taskOrg.setCarrierNodeId("nodeId");
        taskOrg.setName("nodeName");
        taskOrgList.add(taskOrg);

        TaskOrg taskOrg2 = new TaskOrg();
        taskOrg2.setIdentityId("identityId");
        taskOrg2.setCarrierNodeId("nodeId");
        taskOrg2.setName("nodeName");
        taskOrgList.add(taskOrg2);

        System.out.println(taskOrgList.size());
    }
}
