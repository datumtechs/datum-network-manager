package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * @Author liushuyu
 * @Date 2022/3/24 20:07
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class NoAttributeDataTokenPublishReq {

    /********************** 合约信息 ************************/

    @ApiModelProperty("名称，2-64个大小写英文及数字")
    //名称
    @NotBlank
    private String name;

    @ApiModelProperty("符号，2-64个大小写英文及数字")
    //符号
    @NotBlank
    private String symbol;

    @ApiModelProperty("初始发行量，1. 输入必须为大于0的整数，2. 最大支持18位")
    //初始发行量
    @NotBlank
    private String init;

    @ApiModelProperty("总发行量，1. 输入必须为大于0的整数，2. 最大支持18位")
    //总发行量
    @NotBlank
    private String total;

    @ApiModelProperty("价值证明描述")
    //价值证明描述
    private String desc;

    @ApiModelProperty("对应的metaDataDbId")
    //对应的metaDataDbId
    @NotNull
    private Integer metaDataDbId;
    @ApiModelProperty("明文费用")
    private String plaintextFee;
    @ApiModelProperty("密文费用")
    private String ciphertextFee;

    /****************************交易hash*******************************/

    @ApiModelProperty("交易hash")
    //交易hash
    @NotBlank
    private String hash;

    @ApiModelProperty("交易nonce")
    //交易nonce
    @NotNull
    private Integer nonce;
}
