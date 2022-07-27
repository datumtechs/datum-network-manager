package com.platon.datum.admin.controller.node;

import com.platon.datum.admin.dao.OrgMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dto.req.PowerUpdateReq;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PowerNodeControllerTest {
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

    @Resource
    OrgMapper orgMapper;

    @BeforeAll
    public void init(){
        Org org = orgMapper.select();
        OrgCache.setLocalOrgInfo(org);;
    }


    @Test
    public void updateName(){
        PowerUpdateReq req = new PowerUpdateReq();
        req.setNodeName("ahhaha");
        req.setNodeId("jobService_192.168.10.150_8801");
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/powernode/updateNodeName?powerNodeId={1}", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }
}