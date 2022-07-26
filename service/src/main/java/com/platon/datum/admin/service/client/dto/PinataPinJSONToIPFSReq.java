package com.platon.datum.admin.service.client.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/7/25 11:55
 * @Version
 * @Desc https://docs.pinata.cloud/pinata-api/pinning/pin-json
 */

@Getter
@Setter
@ToString
public class PinataPinJSONToIPFSReq {

    private Object pinataContent;

}
