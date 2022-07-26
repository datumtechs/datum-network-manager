package com.platon.datum.admin.dto.req;

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
public class AttributeDataTokenBindMetaDataReq {

    @ApiModelProperty("dataToken的id")
    private Integer dataTokenId;

    @ApiModelProperty("用户签名")
    private String sign;
}
