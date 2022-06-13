package com.platon.datum.admin.grpc.client;

import com.alibaba.fastjson.JSON;
import com.platon.datum.admin.dao.DataSyncMapper;
import com.platon.datum.admin.dao.LocalOrgMapper;
import com.platon.datum.admin.dao.cache.LocalOrgCache;
import com.platon.datum.admin.dao.entity.DataSync;
import com.platon.datum.admin.dao.entity.LocalMetaData;
import com.platon.datum.admin.dao.entity.LocalOrg;
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
    LocalOrgMapper localOrgMapper;
    @Autowired
    DataSyncMapper dataSyncMapper;
    @Autowired
    MetaDataClient metaDataClient;

    @Test
    public void getLocalMetaDataList(){
        LocalOrg localOrg = localOrgMapper.select();
        LocalOrgCache.setLocalOrgInfo(localOrg);

        DataSync dataSync = dataSyncMapper.findByPK(DataSync.DataType.LocalMetaData);

        List<LocalMetaData> localMetaDataList = metaDataClient.getLocalMetaDataList(dataSync.getLatestSynced());
        log.info("localMetaDataList：" + JSON.toJSON(localMetaDataList));
    }

}
