package com.platon.metis.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/3/24 20:28
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel
public class NoAttributeDataTokenUpReq {

    @ApiModelProperty("dataToken的id")
    //dataToken的id
    private Integer dataTokenId;

    @ApiModelProperty("交易hash")
    //交易hash
    private String hash;

    @ApiModelProperty("交易nonce")
    //交易nonce
    private Integer nonce;
}
