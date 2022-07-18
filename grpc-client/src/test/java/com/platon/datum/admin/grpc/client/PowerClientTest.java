package com.platon.datum.admin.grpc.client;

import com.alibaba.fastjson.JSON;
import com.platon.datum.admin.dao.OrgMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dao.entity.PowerNode;
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
    OrgMapper orgMapper;

    @Autowired
    PowerClient powerClient;

    @BeforeAll
    public void init(){
        Org org = orgMapper.select();
        OrgCache.setLocalOrgInfo(org);;
    }
    @Test
    public void getLocalPowerDetailList(){
        Org org = orgMapper.select();
        OrgCache.setLocalOrgInfo(org);
        List<PowerNode> powerNodeList = powerClient.getLocalPowerNodeList();
        log.info("powerNodeList：" + JSON.toJSON(powerNodeList));
    }
}
