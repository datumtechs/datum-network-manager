package com.platon.metis.admin.controller.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocalDataControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void auditAuth(){
        ResponseEntity<String> entity  = restTemplate.getForEntity("/api/v1/data/localMetaDataInfo?id={1}", String.class, 5);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }
}
