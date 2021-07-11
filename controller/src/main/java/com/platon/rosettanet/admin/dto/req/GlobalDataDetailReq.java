package com.platon.rosettanet.admin.dto.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @Author liushuyu
 * @Date 2021/7/10 16:00
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class GlobalDataDetailReq {

    @NotBlank(message = "metaDataId不为空")
    //文件元数据ID
    private String metaDataId;
}
