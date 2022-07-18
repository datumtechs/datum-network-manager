package com.platon.datum.admin.grpc.client;

import com.alibaba.fastjson.JSON;
import com.platon.datum.admin.dao.DataSyncMapper;
import com.platon.datum.admin.dao.OrgMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.DataSync;
import com.platon.datum.admin.dao.entity.MetaData;
import com.platon.datum.admin.dao.entity.Org;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class MetaDataClientTest {

    @Autowired
    OrgMapper orgMapper;
    @Autowired
    DataSyncMapper dataSyncMapper;
    @Autowired
    MetaDataClient metaDataClient;

    @Test
    public void getLocalMetaDataList(){
        Org org = orgMapper.select();
        OrgCache.setLocalOrgInfo(org);

        DataSync dataSync = dataSyncMapper.findByPK(DataSync.DataType.LocalMetaData);

        List<MetaData> metaDataList = metaDataClient.getLocalMetaDataList(dataSync.getLatestSynced());
        log.info("metaDataList：" + JSON.toJSON(metaDataList));
    }

}

