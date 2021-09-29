package com.platon.metis.admin.controller.power;

import com.platon.metis.admin.dto.req.PowerQueryListReq;
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
    public void queryPowerNodeList(){
        PowerQueryListReq req = new PowerQueryListReq();
        req.setPageNumber(1);
        req.setPageSize(20);
        req.setIdentityId("identity_a3876b82060f4eafbca7257692f1b285");
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/node/powernode/queryPowerNodeList", req, String.class);
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

