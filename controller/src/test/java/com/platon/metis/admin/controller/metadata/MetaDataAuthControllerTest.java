package com.platon.metis.admin.controller.metadata;

import com.platon.metis.admin.dto.req.auth.AuthDataActionReq;
import com.platon.metis.admin.dto.req.auth.AuthPageReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MetaDataAuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void auditAuth(){
        AuthDataActionReq req = new AuthDataActionReq();
        req.setAuthId("metadataAuth:0xeb5bcc7d08641605b7b44600b5181d1f94dc44635f3a04ac81e87de35c805ad4");
        req.setAction(1);
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/resource/auth/actionAuthData", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void authDataList(){
        AuthPageReq req = new AuthPageReq();
        req.setPageNumber(1);
        req.setPageSize(10);
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/resource/auth/authDataList", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }
}

