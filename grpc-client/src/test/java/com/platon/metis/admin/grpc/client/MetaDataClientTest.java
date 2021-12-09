package com.platon.metis.admin.grpc.client;

import com.alibaba.fastjson.JSON;
import com.platon.metis.admin.common.context.LocalOrgCache;
import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.entity.GlobalDataFile;
import com.platon.metis.admin.dao.entity.LocalOrg;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = TestApplication.class)
public class MetaDataClientTest {

    @Autowired
    LocalOrgMapper localOrgMapper;

    @Autowired
    MetaDataClient metaDataClient;

    @Test
    public void getGlobalMetaDataList() throws Exception {
        LocalOrg localOrg = localOrgMapper.select();
        LocalOrgCache.setLocalOrgInfo(localOrg);;

        List<GlobalDataFile> metaDataList = metaDataClient.getGlobalMetaDataList();
        log.info("metaDataList：" + JSON.toJSON(metaDataList));
    }

}


