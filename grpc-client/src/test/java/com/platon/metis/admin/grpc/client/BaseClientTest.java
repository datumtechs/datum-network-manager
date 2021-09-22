package com.platon.metis.admin.grpc.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

/**
 * @Author liushuyu
 * @Date 2021/7/19 11:26
 * @Version
 * @Desc
 */

@Slf4j
public class BaseClientTest {

    protected static final String scheduleServerIp = "192.168.21.164";
    protected static final int scheduleServerPort = 4444;


    /**
     * 初始化部分全局变量
     */
    @Before
    public void init(){
        log.error("初始化......");
    }
}
