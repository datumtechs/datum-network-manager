package com.platon.metis.admin;


import com.platon.metis.admin.dao.LocalDataAuthMapper;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional //这个有看需要，测试方法如果要作为一个整体事务，则加上
@Rollback(false) // 默认值：true, UT默认都会回滚数据库，不会增加新数据
public class MockDataAuthTest {

    @Resource
    private LocalDataAuthMapper localDataAuthMapper;

    @Test
    public void addDataAuth(){

        LocalDataAuth localDataAuth = new LocalDataAuth();
        localDataAuth.setAuthId("001");
        localDataAuth.setMetaDataId("metadata:0x3426733d8fbd4a27ed26f06b35caa6ac63bca1fc09b98e56e1b262da9a357ffd");
        localDataAuth.setApplyUser("user_0bcc975ab55848bda315d7c14799bc17");
        localDataAuth.setUserType(1);
        localDataAuth.setAuthType(2);
        localDataAuth.setAuthValueAmount(2);
        localDataAuth.setCreateAt(new Date());
        localDataAuth.setRecCreateTime(new Date());
        localDataAuthMapper.insertSelective(localDataAuth);

        LocalDataAuth localDataAuth1 = new LocalDataAuth();
        localDataAuth1.setAuthId("002");
        localDataAuth1.setMetaDataId("metadata:0x2f3515dbe3103411ba3dc726058768f97eebe02e94bc41e4ebe5c99ed830d0b3");
        localDataAuth1.setApplyUser("user_0bcc975ab55848bda315d7c14799bc17");
        localDataAuth1.setUserType(1);
        localDataAuth1.setAuthType(1);
        localDataAuth1.setAuthValueStartAt(new Date());
        localDataAuth1.setAuthValueEndAt(new Date());
        localDataAuth1.setCreateAt(new Date());
        localDataAuth1.setRecCreateTime(new Date());
        localDataAuthMapper.insertSelective(localDataAuth1);


    }
}
