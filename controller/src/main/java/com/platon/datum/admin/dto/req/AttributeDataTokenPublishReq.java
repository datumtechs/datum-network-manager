package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author liushuyu
 * @Date 2022/7/12 10:12
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class AttributeDataTokenPublishReq {

    /********************** 合约信息 ************************/

    @ApiModelProperty("拥有者")
    //拥有者
    @NotBlank
    private String owner;

    @ApiModelProperty("名称，2-64个大小写英文及数字")
    //名称
    @NotBlank
    private String name;

    @ApiModelProperty("符号，2-64个大小写英文及数字")
    //符号
    @NotBlank
    private String symbol;

    @ApiModelProperty("对应的metaData表的Id")
    //对应的metaData表的Id
    @NotNull
    private Integer metaDataDbId;

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
