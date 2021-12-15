package com.platon.metis.admin.grpc.client;

import com.alibaba.fastjson.JSON;
import com.platon.metis.admin.common.context.LocalOrgCache;
import com.platon.metis.admin.dao.DataSyncMapper;
import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.entity.DataSync;
import com.platon.metis.admin.dao.entity.LocalMetaData;
import com.platon.metis.admin.dao.entity.LocalOrg;
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

