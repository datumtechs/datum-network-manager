package com.platon.datum.admin.controller.resource;

import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.MetaDataColumn;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dto.CommonPageReq;
import com.platon.datum.admin.dto.req.AddLocalMetaDataReq;
import com.platon.datum.admin.dto.req.DataJoinTaskListReq;
import com.platon.datum.admin.dto.req.MetaDataMetaDataListByKeyWordReq;
import com.platon.datum.admin.dto.req.MetaDataUpdateReq;
import com.platon.datum.admin.service.OrgService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MetaMetaDataControllerTest {
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
    public void addLocalMetaData() {
        AddLocalMetaDataReq req = new AddLocalMetaDataReq();
        req.setAddType(1);
        req.setIndustry(2);
        req.setDesc("testing remarks");
        req.setResourceName("myResource");
        req.setFileId("myFileId");

        MetaDataColumn column1 = new MetaDataColumn();
        column1.setColumnIdx(1);
        column1.setColumnType("string");
        column1.setColumnName("user_name");
        column1.setSize(100);
        column1.setVisible(true);

        MetaDataColumn column2 = new MetaDataColumn();
        column2.setColumnIdx(2);
        column2.setColumnType("string");
        column2.setColumnName("user_age");
        column2.setSize(3);
        column2.setVisible(true);
        List<MetaDataColumn> metaDataColumnList = new ArrayList<>();
        metaDataColumnList.add(column1);
        metaDataColumnList.add(column2);
        req.setMetaDataColumnList(metaDataColumnList);

        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/resource/mydata/addLocalMetaData", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void updateMetaData() {
        MetaDataUpdateReq req = new MetaDataUpdateReq();
        req.setId(30);
        req.setIndustry(4);
        req.setDesc("new remarks");

        MetaDataColumn column1 = new MetaDataColumn();
        column1.setColumnIdx(1);
        column1.setColumnType("string");
        column1.setColumnName("user_name");
        column1.setSize(100);
        column1.setRemarks("col_1");
        column1.setVisible(true);

        MetaDataColumn column2 = new MetaDataColumn();
        column2.setColumnIdx(2);
        column2.setColumnType("string");
        column2.setColumnName("user_age");
        column2.setSize(3);
        column2.setRemarks("col_2");
        column2.setVisible(false);
        List<MetaDataColumn> metaDataColumnList = new ArrayList<>();
        metaDataColumnList.add(column1);
        metaDataColumnList.add(column2);
        req.setMetaDataColumnList(metaDataColumnList);

        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/resource/mydata/updateMetaData", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void listMetaData() {
        CommonPageReq req = new CommonPageReq();
        req.setPageNumber(1);
        req.setPageSize(20);
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/resource/mydata/metaDataList", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void metaDataListByKeyWord() {
        MetaDataMetaDataListByKeyWordReq req = new MetaDataMetaDataListByKeyWordReq();
        req.setPageNumber(1);
        req.setPageSize(20);
        req.setKeyword("new51");
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/resource/mydata/metaDataListByKeyWord", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void checkResourceName() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("resourceName", "44");
        map.add("fileId", "5adc085c654b60511b6da2ea9e9d19f964dd4f4e3a87ebcf3e81a3d6223c376d");

        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/resource/mydata/checkResourceName", map, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }

    @Test
    public void queryDataJoinTaskList() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("resourceName", "44");
        map.add("fileId", "5adc085c654b60511b6da2ea9e9d19f964dd4f4e3a87ebcf3e81a3d6223c376d");


        DataJoinTaskListReq req = new DataJoinTaskListReq();
        req.setMetaDataId("metadata:0xf283518d022079f68462e26ba2610c08dff03540b2bdd031df6198a9c14f9766");
        req.setPageSize(20);
        req.setPageNumber(1);
        ResponseEntity<String> entity = restTemplate.postForEntity("/api/v1/resource/mydata/queryDataJoinTaskList", req, String.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getBody());
    }


}
