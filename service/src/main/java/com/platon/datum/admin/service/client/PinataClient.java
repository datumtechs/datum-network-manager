package com.platon.datum.admin.service.client;

import com.platon.datum.admin.service.client.config.FeignClientConfig;
import com.platon.datum.admin.service.client.dto.PinataPinJSONToIPFSReq;
import com.platon.datum.admin.service.client.dto.PinataPinResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author liushuyu
 * @Date 2022/7/25 11:24
 * @Version
 * @Desc
 */


@FeignClient(name = "PinataClient",
        url = "${pinata-url}",
        configuration = FeignClientConfig.class)
public interface PinataClient {

    @PostMapping(path = "/pinning/pinFileToIPFS",
            consumes = "multipart/form-data",
            produces = "application/json")
    @ResponseBody
    PinataPinResult pinFileToIPFS(@RequestHeader("Authorization") String authorization, MultipartFile file);

    @PostMapping(value = "/pinning/pinJSONToIPFS",
            produces = "application/json")
    @ResponseBody
    PinataPinResult pinJSONToIPFS(@RequestHeader("Authorization") String authorization, @RequestBody PinataPinJSONToIPFSReq req);
}
