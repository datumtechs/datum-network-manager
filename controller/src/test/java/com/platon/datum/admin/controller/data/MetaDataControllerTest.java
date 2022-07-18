package com.platon.datum.admin.controller.data;

import com.platon.datum.admin.dto.req.DataJoinTaskListReq;
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
public class MetaDataControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void auditAuth(){
        ResponseEntity<String> entity  = restTemplate.getForEntity("/api/v1/data/localMetaDataInfo?id={1}", String.class, 5);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }


    @Test
    public void listTaskByMetaDataId(){
        String metaDataId = "metadata:0xec2d3aa112c59fe6127605bde1784c0d10af2af47a4d10de6cf0719a7aa245c9";
        DataJoinTaskListReq req = new DataJoinTaskListReq();
        req.setMetaDataId(metaDataId);
        req.setPageNumber(1);
        req.setPageSize(10);
        ResponseEntity<String> entity  = restTemplate.postForEntity("/api/v1/data/listTaskByMetaDataId", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void checkResourceName(){
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("resourceName", "151保险训练中数据集A");

        ResponseEntity<String> entity  = restTemplate.postForEntity("/api/v1/data/checkResourceName", map, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }
}
