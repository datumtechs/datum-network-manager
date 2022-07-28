package com.platon.datum.admin.service.client;

import com.platon.datum.admin.service.ServiceTestApplication;
import com.platon.datum.admin.service.client.dto.PinataPinJSONToIPFSReq;
import com.platon.datum.admin.service.client.dto.PinataPinResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author liushuyu
 * @Date 2022/7/25 12:22
 * @Version
 * @Desc
 */

@SpringBootTest(classes = ServiceTestApplication.class)
@RunWith(SpringRunner.class)
public class PinnataClientTest {

    @Resource
    private PinataClient pinataClient;

    @Test
    public void testPinFileToIPFS() {
//        pinataClient.pinFileToIPFS();
    }

    @Test
    public void testPinJSONToIPFS() {
        PinataPinJSONToIPFSReq req = new PinataPinJSONToIPFSReq();
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        req.setPinataContent(map);
//        PinataPinResult pinataPinResult = pinataClient.pinJSONToIPFS("",req);
    }
}


