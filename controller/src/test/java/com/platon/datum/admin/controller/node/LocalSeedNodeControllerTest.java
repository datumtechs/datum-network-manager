package com.platon.datum.admin.controller.node;

import com.platon.datum.admin.dto.req.seed.AddSeedNodeReq;
import com.platon.datum.admin.dto.req.seed.CheckSeedNodeIdReq;
import com.platon.datum.admin.dto.req.seed.ListSeedNodeReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocalSeedNodeControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void checkSeedNodeId(){
        CheckSeedNodeIdReq req  = new CheckSeedNodeIdReq();
        req.setSeedNodeId("test");
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/seednode/checkSeedNodeId", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    public void addSeedNode(){
        AddSeedNodeReq req  = new AddSeedNodeReq();
        req.setSeedNodeId("test");
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/seednode/addSeedNode", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void listSeedNode(){
        ListSeedNodeReq req  = new ListSeedNodeReq();
        req.setPageNumber(1);
        req.setPageSize(20);

        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/seednode/listSeedNode", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }
}
