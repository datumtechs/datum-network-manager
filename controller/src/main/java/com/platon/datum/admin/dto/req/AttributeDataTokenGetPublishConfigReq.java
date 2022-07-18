package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/7/11 18:24
 * @Version
 * @Desc
 */
@Getter
@Setter
@ToString
@ApiModel
public class AttributeDataTokenGetPublishConfigReq {

    @ApiModelProperty("元数据对应的dataTokenId，可以为空，为空则返回空数据")
    private Integer dataTokenId;
}