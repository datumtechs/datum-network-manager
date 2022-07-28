package com.platon.datum.admin.service.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/7/25 12:15
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class PinataPinResult {

    /**
     * CID
     */
    @JsonProperty("IpfsHash")
    private String IpfsHash;

    /**
     * 内容大小
     */
    @JsonProperty("PinSize")
    private long PinSize;

    /**
     * 上传的时间戳
     */
    @JsonProperty("Timestamp")
    private String Timestamp;

    /**
     * 上传的内容是否重复
     */
    @JsonProperty("isDuplicate")
    private Boolean isDuplicate;

}
