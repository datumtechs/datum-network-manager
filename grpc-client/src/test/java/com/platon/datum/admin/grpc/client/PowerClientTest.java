package com.platon.datum.admin.grpc.client;

import com.alibaba.fastjson.JSON;
import com.platon.datum.admin.dao.LocalOrgMapper;
import com.platon.datum.admin.dao.cache.LocalOrgCache;
import com.platon.datum.admin.dao.entity.LocalOrg;
import com.platon.datum.admin.dao.entity.LocalPowerNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class PowerClientTest {

    @Autowired
    LocalOrgMapper localOrgMapper;

    @Autowired
    PowerClient powerClient;

    @BeforeAll
    public void init(){
        LocalOrg localOrg = localOrgMapper.select();
        LocalOrgCache.setLocalOrgInfo(localOrg);;
    }
    @Test
    public void getLocalPowerDetailList(){
        LocalOrg localOrg = localOrgMapper.select();
        LocalOrgCache.setLocalOrgInfo(localOrg);
        List<LocalPowerNode> localPowerNodeList = powerClient.getLocalPowerNodeList();
        log.info("localPowerNodeList：" + JSON.toJSON(localPowerNodeList));
    }
}