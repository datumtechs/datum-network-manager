package com.platon.datum.admin.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/7/21 18:32
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel
public class GeneralOrganizationHomeResp {

    @ApiModelProperty("组织ID")
    private String identityId;

    @ApiModelProperty("组织名称")
    private String identityName;

    @ApiModelProperty("已获取的证书数量")
    private int credentialsCount;

    @ApiModelProperty("已发出的申请数量")
    private int applyCount;

}
