package com.platon.metis.admin.controller.power;

import com.platon.metis.admin.dto.req.PowerJoinTaskReq;
import com.platon.metis.admin.dto.req.PowerRevokeReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PowerControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void listRunningTaskByPowerNodeId(){
        PowerJoinTaskReq req = new PowerJoinTaskReq();
        req.setPageNumber(1);
        req.setPageSize(20);
        req.setPowerNodeId("jobNode:0x032d54d4fb1cfa625ecc19ff2cf2a0596b2ad87a32379c455708c41c66ad0318");
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/powernode/listRunningTaskByPowerNodeId", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void revokePower(){
        PowerRevokeReq req = new PowerRevokeReq();
        req.setPowerNodeId("power:0x3dfa6a183e117c1900208bbb5f399397d7e000e1ee94d42700cbfd56e2ebd2b9");
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/node/powernode/revokePower", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }
}

