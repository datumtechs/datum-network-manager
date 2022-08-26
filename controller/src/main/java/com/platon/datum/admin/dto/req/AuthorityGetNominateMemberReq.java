package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author liushuyu
 * @date 2022/8/23 16:16
 * @desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class AuthorityGetNominateMemberReq {

    @ApiModelProperty(value = "根据组织名称模糊查询", required = false)
    private String keyword;
}
