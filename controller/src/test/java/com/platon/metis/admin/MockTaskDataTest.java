package com.platon.metis.admin;

import com.platon.metis.admin.dao.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional //这个有看需要，测试方法如果要作为一个整体事务，则加上
@Rollback(false) // 默认值：true, UT默认都会回滚数据库，不会增加新数据
public class MockTaskDataTest {


    private static final String NODE_NAME = "nodeName";
    private static final String NODE_ID = "nodeId";

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskDataProviderMapper taskDataProviderMapper;

    @Resource
    private TaskPowerProviderMapper taskPowerProviderMapper;

    @Resource
    private TaskResultConsumerMapper taskResultConsumerMapper;

    @Resource
    private TaskOrgMapper taskOrgMapper;

    @Resource
    private TaskEventMapper taskEventMapper;



    @Test
    public void test() {

    }

}
