package com.platon.metis.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

/**
 * @Author liushuyu
 * @Date 2022/3/28 18:10
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel
public class NoAttributeDataTokenGetPublishConfigResp {


    @ApiModelProperty("合约工厂地址")
    private String dataTokenFactory;
    @ApiModelProperty("carrier钱包地址")
    private String agent;
    @ApiModelProperty("gasLimit")
    private String gas = "210000";
    @ApiModelProperty("gasPrice")
    private String gasPrice = "0";
}
