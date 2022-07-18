package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @Author liushuyu
 * @Date 2022/7/15 16:31
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel
public class AttributeDataTokenRefreshInventoryByTokenIdReq {

    @ApiModelProperty("凭证合约地址")
    @NotBlank
    private String tokenAddress;

    @ApiModelProperty("凭证TokenId")
    @NotBlank
    private String tokenId;
}
