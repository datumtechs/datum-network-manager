package com.platon.datum.admin.controller.system;

import com.platon.datum.admin.dao.OrgMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import org.junit.Before;
import org.junit.Test;
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
    OrgMapper orgMapper;

    @Before
    public void init(){
        Org org = orgMapper.select();
        OrgCache.setLocalOrgInfo(org);;
    }


    @Test
    public void listLocalPowerStatsTrendMonthly(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/overview/localPowerStatsTrendMonthly", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void listLocalDataFileStatsTrendMonthly(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/overview/localDataFileStatsTrendMonthly", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }


    @Test
    public void queryMyCalculateTaskStats(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/overview/myTaskOverview",String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());

    }


    /*@Test
    public void listGlobalPowerStatsTrendMonthly(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/overview/listGlobalPowerStatsTrendMonthly", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void listGlobalDataFileStatsTrendMonthly(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/overview/listGlobalDataFileStatsTrendMonthly", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }*/

}
