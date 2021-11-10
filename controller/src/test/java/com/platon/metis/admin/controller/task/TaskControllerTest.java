package com.platon.metis.admin.controller.task;

import com.platon.metis.admin.common.context.LocalOrgCache;
import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.dao.entity.TaskOrg;
import com.platon.metis.admin.dto.req.TaskPageReq;
import com.platon.metis.admin.grpc.common.CommonBase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
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
    LocalOrgMapper localOrgMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public void init(){
        LocalOrg localOrg = localOrgMapper.select();
        LocalOrgCache.setLocalOrgInfo(localOrg);;
    }

    @Test
    public void listMyTask(){
        TaskPageReq req = new TaskPageReq();
        req.setPageNumber(1);
        req.setPageSize(10);
        req.setStartTime(0L);
        req.setEndTime(0L);
        req.setStatus(CommonBase.TaskState.TaskState_Succeed.getNumber());
        req.setRole(2);
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/task/listMyTask", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void myTaskStatistics(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/task/myTaskStatistics", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void listTaskEvent(){
        String taskId = "task:0x159405505b7ba76f183813547725b6e679447d7f353d11cf4b52702c7d7bc1ab";
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/task/listTaskEvent?taskId={1}", String.class, taskId);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void taskInfo(){
        String taskId = "task:0xf42b15a1ce6759ab27e4a40a71a614680067ac91d5e1706f05cbff58d87eae44";
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/task/taskInfo?taskId={1}", String.class, taskId);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void taskOrgTest(){
        Set<TaskOrg> taskOrgList = new HashSet<>();

        TaskOrg taskOrg = new TaskOrg();
        taskOrg.setIdentityId("identityId");
        taskOrg.setCarrierNodeId("nodeId");
        taskOrg.setOrgName("orgName");
        taskOrgList.add(taskOrg);

        TaskOrg taskOrg2 = new TaskOrg();
        taskOrg2.setIdentityId("identityId");
        taskOrg2.setCarrierNodeId("nodeId");
        taskOrg2.setOrgName("orgName");
        taskOrgList.add(taskOrg2);

        System.out.println(taskOrgList.size());
    }
}
