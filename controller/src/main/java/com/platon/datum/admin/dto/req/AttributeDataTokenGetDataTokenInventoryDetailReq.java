package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/7/12 10:17
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel
public class AttributeDataTokenGetDataTokenInventoryDetailReq {

    @ApiModelProperty("凭证合约地址")
    private String dataTokenAddress;

    @ApiModelProperty("tokenId")
    private String tokenId;
}
