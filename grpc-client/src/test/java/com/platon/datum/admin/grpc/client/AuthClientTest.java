package com.platon.datum.admin.grpc.client;

import com.platon.datum.admin.dao.entity.Org;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest(classes = TestApplication.class)
public class AuthClientTest extends BaseClientTest {

    @Resource
    AuthClient authClient;

    @Test
    public void testApplyIdentityJoin() {
        String identityId = "identityId_000001";
        String name = "orgName_000001";
        String url = "http://url";
        String profile = "node profile";
        Org org = new Org();
        org.setIdentityId(identityId);
        org.setName(name);
        org.setImageUrl(url);
        org.setProfile(profile);
        authClient.applyIdentityJoin(org);

    }

    @Test
    public void testRevokeIdentityJoin() {
        authClient.revokeIdentityJoin();
    }
}