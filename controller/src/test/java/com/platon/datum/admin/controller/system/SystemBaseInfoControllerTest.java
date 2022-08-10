package com.platon.datum.admin.controller.system;

import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.service.OrgService;
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
public class SystemBaseInfoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    OrgService orgService;

    @Before
    public void init() {
        Org org = orgService.select();
        OrgCache.setLocalOrgInfo(org);
        ;
    }

    @Test
    public void queryBaseInfo() {
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/system/queryBaseInfo", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

}
