package com.platon.metis.admin.controller.node;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocalPowerNodeControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void listLocalPowerLoadSnapshotByPowerNodeId(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/powernode/listLocalPowerLoadSnapshotByPowerNodeId?powerNodeId={1}", String.class, "jobNode:0xec83cddbda811a83a43ac8745ca0749a48f41b6af8f35a8a19ee37178af97ba5");
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void getCurrentLocalPowerLoadByPowerNodeId(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/powernode/getCurrentLocalPowerLoadByPowerNodeId?powerNodeId={1}", String.class, "jobNode:0xec83cddbda811a83a43ac8745ca0749a48f41b6af8f35a8a19ee37178af97ba5");
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }
}
