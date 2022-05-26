package com.platon.datum.admin.controller.metadata;

import com.platon.datum.admin.dto.req.auth.AuthDataActionReq;
import com.platon.datum.admin.dto.req.auth.AuthPageReq;
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
public class MetaDataAuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void auditAuth(){
        AuthDataActionReq req = new AuthDataActionReq();
        req.setAuthId("metadataAuth:0xeb5bcc7d08641605b7b44600b5181d1f94dc44635f3a04ac81e87de35c805ad4");
        req.setAction(1);
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/data/actionAuthData", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void authDataList(){
        AuthPageReq req = new AuthPageReq();
        req.setPageNumber(1);
        req.setPageSize(10);
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/data/authDataList", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void getDataAuthDetail(){

        String authId = "metadataAuth:0x625821102f107e89690848fddb44eca165ac6b5ec39cc9e7dd5118156616c12e";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("authId", authId);


        //ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/data/dataAuthDetail", formData, String.class);
        ResponseEntity<String> entity = restTemplate.getForEntity("/api/v1/data/dataAuthDetail?authId={1}", String.class, authId);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());

    }

    /*@Test
    public void postDataAuthDetail(){
        String authId = "metadataAuth:0x625821102f107e89690848fddb44eca165ac6b5ec39cc9e7dd5118156616c12e";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("authId", authId);
        //ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/data/dataAuthDetail", formData, String.class);
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/data/dataAuthDetail", authId, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());

    }*/
}

