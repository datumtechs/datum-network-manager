package com.platon.metis.admin.controller.node;

import com.platon.metis.admin.dto.req.NodePageReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NodeControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void connectNode(){
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("ip", "127.0.0.1");
        map.add("port", "8800");

        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/node/corenode/connectNode", map, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void listDataNode(){
        NodePageReq req  = new NodePageReq();
        req.setPageNumber(1);
        req.setPageSize(20);

        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/node/datanode/listNode", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }
}
