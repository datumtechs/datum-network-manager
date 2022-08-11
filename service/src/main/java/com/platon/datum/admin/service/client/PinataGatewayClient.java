package com.platon.datum.admin.service.client;

import com.platon.datum.admin.service.client.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author liushuyu
 * @Date 2022/7/25 11:24
 * @Version
 * @Desc
 */


@FeignClient(name = "PinataGatewayClient",
        url = "${pinata-gateway}",
        configuration = FeignClientConfig.class)
public interface PinataGatewayClient {

    @PostMapping(value = "/ipfs/{CID}",produces = "application/json")
    Object getJsonFromIpfs(@PathVariable("CID") String cid);
}
