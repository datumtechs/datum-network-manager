package com.platon.datum.admin.service.client;

import com.platon.datum.admin.service.client.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping(value = "/ipfs/{CID}", produces = "application/json")
    Object getJsonFromIpfs(@PathVariable("CID") String cid);
}
