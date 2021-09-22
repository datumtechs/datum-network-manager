package com.platon.metis.admin.grpc.client;

import cn.hutool.json.JSONUtil;
import com.platon.metis.admin.grpc.entity.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/19 11:30
 * @Version
 * @Desc
 */

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = TestApplication.class)
public class AuthClientTest extends BaseClientTest {

    @Resource
    AuthClient authClient;

    @Test
    public void testApplyIdentityJoin(){
        String identityId = "identityId_000001";
        String name = "orgName_000001";
        CommonResp resp = authClient.applyIdentityJoin(identityId, name);
        log.error(JSONUtil.toJsonStr(resp));
    }

    @Test
    public void testRevokeIdentityJoin(){
        CommonResp resp = authClient.revokeIdentityJoin();
        log.error(JSONUtil.toJsonStr(resp));
    }
}
