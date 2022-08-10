package com.platon.datum.admin.controller.task;

import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dao.entity.TaskOrg;
import com.platon.datum.admin.dto.req.TaskPageReq;
import com.platon.datum.admin.grpc.common.constant.CarrierEnum;
import com.platon.datum.admin.service.OrgService;
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
    OrgService orgService;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public void init() {
        Org org = orgService.select();
        OrgCache.setLocalOrgInfo(org);
        ;
    }

    @Test
    public void listMyTask() {
        TaskPageReq req = new TaskPageReq();
        req.setPageNumber(1);
        req.setPageSize(10);
        req.setStartTime(0L);
        req.setEndTime(0L);
        req.setStatus(CarrierEnum.TaskState.TaskState_Succeed.getNumber());
        req.setRole(2);
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/task/listMyTask", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void myTaskStatistics() {
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/task/myTaskStatistics", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void listTaskEvent() {
        String taskId = "task:0x159405505b7ba76f183813547725b6e679447d7f353d11cf4b52702c7d7bc1ab";
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/task/listTaskEvent?taskId={1}", String.class, taskId);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void taskInfo() {
        String taskId = "task:0x18e84ffff0c37c93321978ebad97e3ae5674a124cbd047aff30168b8b586f091";
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/task/taskInfo?taskId={1}", String.class, taskId);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void taskOrgTest() {
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
