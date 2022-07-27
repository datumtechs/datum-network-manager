package com.platon.datum.admin;


import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.DataAuthMapper;
import com.platon.datum.admin.dao.entity.DataAuth;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional //这个有看需要，测试方法如果要作为一个整体事务，则加上
@Rollback(false) // 默认值：true, UT默认都会回滚数据库，不会增加新数据
@Slf4j
public class MockDataAuthTest {

    @Resource
    private DataAuthMapper dataAuthMapper;

    @Test
    public void addDataAuth() {

        DataAuth dataAuth = new DataAuth();
        dataAuth.setAuthId("001");
        dataAuth.setMetaDataId("metadata:0x3426733d8fbd4a27ed26f06b35caa6ac63bca1fc09b98e56e1b262da9a357ffd");
        dataAuth.setApplyUser("user_0bcc975ab55848bda315d7c14799bc17");
        dataAuth.setUserType(1);
        dataAuth.setAuthType(2);
        dataAuth.setAuthValueAmount(2);
        dataAuth.setCreateAt(LocalDateTimeUtil.now());
        dataAuthMapper.insert(dataAuth);

        DataAuth dataAuth1 = new DataAuth();
        dataAuth1.setAuthId("002");
        dataAuth1.setMetaDataId("metadata:0x2f3515dbe3103411ba3dc726058768f97eebe02e94bc41e4ebe5c99ed830d0b3");
        dataAuth1.setApplyUser("user_0bcc975ab55848bda315d7c14799bc17");
        dataAuth1.setUserType(1);
        dataAuth1.setAuthType(1);
        dataAuth1.setAuthValueStartAt(LocalDateTimeUtil.now());
        dataAuth1.setAuthValueEndAt(LocalDateTimeUtil.now());
        dataAuth1.setCreateAt(LocalDateTimeUtil.now());
        dataAuthMapper.insert(dataAuth1);


    }

    @Test
    public void listDataAuth() {
        List<DataAuth> dataAuthList = dataAuthMapper.selectDataAuthList(0, null);
        log.info("dataAuthList().size:{}", dataAuthList.size());
    }

    // 注入StringEncryptor
    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void printUserPwd() {
        // 加密数据库用户名：testusername
        String username = encryptor.encrypt("platon");
        System.out.println(username);         // r7TjsmsdBlasIs/WeqhftWM/4YEbMKym

        // 加密数据库密码：testpassword
        String password = encryptor.encrypt("platon");
        System.out.println(password);         // fG1fPPsdfdff0faZpYYZd8FtC9KYwmFc
    }

}
