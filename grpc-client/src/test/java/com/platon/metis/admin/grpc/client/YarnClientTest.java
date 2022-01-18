package com.platon.metis.admin.grpc.client;

import com.alibaba.fastjson.JSON;
import com.platon.metis.admin.dao.enums.FileTypeEnum;
import com.platon.metis.admin.grpc.entity.YarnAvailableDataNodeResp;
import com.platon.metis.admin.grpc.entity.YarnGetNodeInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest(classes = TestApplication.class)
public class YarnClientTest extends BaseClientTest{

    @Resource
    YarnClient yarnClient;

    @Test
    public void testGetNodeInfo(){
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(scheduleServerIp, scheduleServerPort);
        log.info(JSON.toJSONString(nodeInfo));
    }

    @Test
    public void testConnectScheduleServer(){
        boolean b = yarnClient.connectScheduleServer(scheduleServerIp, scheduleServerPort);
        log.info("连接状态：" + b);
    }

    @Test
    public void testGetAvailableDataNode(){
//        YarnAvailableDataNodeResp resp = yarnClient.getAvailableDataNode(1000000000000000000L, "csv");
//        log.info(JSON.toJSONString(resp));

        long size = 10*1024*1024*1024*1024*1024;
        YarnAvailableDataNodeResp resp = yarnClient.getAvailableDataNode(size, FileTypeEnum.FILETYPE_CSV);
        log.info(JSON.toJSONString(resp));
    }



}
