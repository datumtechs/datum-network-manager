package com.platon.metis.admin.controller.system;

import com.platon.metis.admin.dto.req.UserLoginReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void login(){
        UserLoginReq req = new UserLoginReq();
        req.setUserName("admin");
        req.setPasswd("admin");
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/system/user/login", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }


    @Test
    public void findLocalOrgInfo(){
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/user/findLocalOrgInfo", String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }
}
