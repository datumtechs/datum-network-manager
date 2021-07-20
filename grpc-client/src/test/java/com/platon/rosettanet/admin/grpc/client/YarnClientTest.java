package com.platon.rosettanet.admin.grpc.client;

import cn.hutool.json.JSONUtil;
import com.platon.rosettanet.admin.grpc.entity.CommonResp;
import com.platon.rosettanet.admin.grpc.entity.YarnAvailableDataNodeResp;
import com.platon.rosettanet.admin.grpc.entity.YarnGetNodeInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/19 14:35
 * @Version
 * @Desc
 */

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = TestApplication.class)
public class YarnClientTest extends BaseClientTest{

    @Resource
    YarnClient yarnClient;

    @Test
    public void testGetNodeInfo(){
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(scheduleServerIp, scheduleServerPort);
        log.info(JSONUtil.toJsonStr(nodeInfo));
    }

    @Test
    public void testConnectScheduleServer(){
        boolean b = yarnClient.connectScheduleServer(scheduleServerIp, scheduleServerPort);
        log.info("连接状态：" + b);
    }

    @Test
    public void testGetAvailableDataNode(){
//        YarnAvailableDataNodeResp resp = yarnClient.getAvailableDataNode(1000000000000000000L, "csv");
//        log.info(JSONUtil.toJsonStr(resp));

        long size = 10*1024*1024*1024*1024*1024;
        YarnAvailableDataNodeResp resp = yarnClient.getAvailableDataNode(size, "csv");
        log.info(JSONUtil.toJsonStr(resp));
    }


}
