package com.platon.rosettanet.admin.dto.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @Author liushuyu
 * @Date 2021/7/10 15:55
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class LocalDataDownLoadReq {

    //文件元数据ID
    @NotBlank(message = "metaDataId不为空")
    private String metaDataId;
}
