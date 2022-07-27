package com.platon.datum.admin.service.client;

import com.platon.datum.admin.service.client.config.FeignClientConfig;
import com.platon.datum.admin.service.client.dto.PinataPinJSONToIPFSReq;
import com.platon.datum.admin.service.client.dto.PinataPinResult;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestPart;
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
@Headers("Authorization: ${pinata-token}")
public interface PinataClient {

    @Headers("Content-Type: multipart/form-data")
    @RequestLine("POST /pinning/pinFileToIPFS")
    PinataPinResult pinFileToIPFS(@RequestPart(value = "file") MultipartFile file);

    @Headers("Content-Type: application/json")
    @RequestLine("POST /pinning/pinJSONToIPFS")
    PinataPinResult pinJSONToIPFS(PinataPinJSONToIPFSReq req);
}
