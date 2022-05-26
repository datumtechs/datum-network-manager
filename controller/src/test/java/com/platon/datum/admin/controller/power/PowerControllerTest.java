package com.platon.datum.admin.controller.power;

import com.platon.datum.admin.dto.req.PowerJoinTaskReq;
import com.platon.datum.admin.dto.req.PowerRevokeReq;
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
        req.setPowerNodeId("jobNode:0xaba47f171f3010999e4572c93df637c46cdbf655d0cd74aed43dc184c271e71c");
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/powernode/listRunningTaskByPowerNodeId", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }


    @Test
    public void listLocalPowerLoadSnapshotByPowerNodeId(){
        String powerNodeId = "jobNode:0xaba47f171f3010999e4572c93df637c46cdbf655d0cd74aed43dc184c271e71c";
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/powernode/listLocalPowerLoadSnapshotByPowerNodeId?powerNodeId={1}", String.class, powerNodeId);
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

