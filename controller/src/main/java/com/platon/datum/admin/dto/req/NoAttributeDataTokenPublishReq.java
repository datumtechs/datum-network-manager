package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    @ApiModelProperty("拥有者")
    @NotBlank
    private String owner;

    @ApiModelProperty("名称，2-64个大小写英文及数字")
    @Length(min = 2, max = 64)
    @NotBlank
    private String name;

    @ApiModelProperty("符号，2-64个大小写英文及数字")
    @Length(min = 2, max = 64)
    @NotBlank
    private String symbol;

    @ApiModelProperty("初始发行量，1. 输入必须为大于0的整数，2. 最大支持18位")
    @NotBlank
    @Length(min = 19, max = 36)
    private String init;

    @ApiModelProperty("总发行量，1. 输入必须为大于0的整数，2. 最大支持18位")
    @NotBlank
    @Length(min = 19, max = 36)
    private String total;

    @ApiModelProperty("价值证明描述")
    private String desc;

    @ApiModelProperty("对应的metaDataDbId")
    @NotNull
    private Integer metaDataDbId;

    @ApiModelProperty("明文费用")
    @Length(min = 19, max = 36)
    private String plaintextFee;

    @ApiModelProperty("密文费用")
    @Length(min = 19, max = 36)
    private String ciphertextFee;

    /****************************交易hash*******************************/

    @ApiModelProperty("交易hash")
    @NotBlank
    private String hash;

    @ApiModelProperty("交易nonce")
    @NotNull
    private Integer nonce;

}
