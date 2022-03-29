package com.platon.metis.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/3/28 21:29
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel
public class NoAttributeDataTokenGetUpConfigResp {

    @ApiModelProperty("dex的路由合约地址")
    private String routerToken;
    @ApiModelProperty("gasLimit")
    private String gas = "210000";
    @ApiModelProperty("gasPrice")
    private String gasPrice = "10000000000";
}
