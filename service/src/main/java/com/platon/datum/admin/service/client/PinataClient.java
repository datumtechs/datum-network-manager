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
        url = "https://api.pinata.cloud",
        configuration = FeignClientConfig.class)
//@Headers("Authorization=${token}")
@Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySW5mb3JtYXRpb24iOnsiaWQiOiIyOTg0OTg5Ni1iYzE2LTRiYjctOTUzZi02MDU2MDVjOTZhN2EiLCJlbWFpbCI6ImNoZW54aWFvZGFpQDEyNi5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwicGluX3BvbGljeSI6eyJyZWdpb25zIjpbeyJpZCI6Ik5ZQzEiLCJkZXNpcmVkUmVwbGljYXRpb25Db3VudCI6MX1dLCJ2ZXJzaW9uIjoxfSwibWZhX2VuYWJsZWQiOmZhbHNlLCJzdGF0dXMiOiJBQ1RJVkUifSwiYXV0aGVudGljYXRpb25UeXBlIjoic2NvcGVkS2V5Iiwic2NvcGVkS2V5S2V5IjoiMzA5NTVjODYyM2M0OGNjZjYyNGYiLCJzY29wZWRLZXlTZWNyZXQiOiIzYTU5NzA0ZDY1ZDhiOGMxMWRiZGM2NDE2ZGZiZTI3NDY5M2QyYzAxYjRmYTAyYTc3NzAwZGRlMTg2OGZlMzdkIiwiaWF0IjoxNjU1MzQ1MzYzfQ.8TsNh3LR60owivZYcSBV144BBW3DuWocL4O_du4XNyY")
public interface PinataClient {

    @Headers("Content-Type: multipart/form-data")
    @RequestLine("POST /pinning/pinFileToIPFS")
    PinataPinResult pinFileToIPFS(@RequestPart(value = "file") MultipartFile file);

    @Headers("Content-Type: application/json")
    @RequestLine("POST /pinning/pinJSONToIPFS")
    PinataPinResult pinJSONToIPFS(PinataPinJSONToIPFSReq req);
}
