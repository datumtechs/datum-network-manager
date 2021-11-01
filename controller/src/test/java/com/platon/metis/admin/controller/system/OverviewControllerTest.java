package com.platon.metis.admin.controller.system;

import com.platon.metis.admin.common.context.LocalOrgCache;
import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.entity.LocalOrg;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OverviewControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    LocalOrgMapper localOrgMapper;

    @BeforeAll
    public void init(){
        LocalOrg localOrg = localOrgMapper.select();
        LocalOrgCache.setLocalOrgInfo(localOrg);;
    }


    @Test
    public void listLocalPowerStatsTrendMonthly(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/system/index/listLocalPowerStatsTrendMonthly", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void listLocalDataFileStatsTrendMonthly(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/system/index/listLocalDataFileStatsTrendMonthly", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void listGlobalPowerStatsTrendMonthly(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/system/index/listGlobalPowerStatsTrendMonthly", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void listGlobalDataFileStatsTrendMonthly(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/system/index/listGlobalDataFileStatsTrendMonthly", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

}
