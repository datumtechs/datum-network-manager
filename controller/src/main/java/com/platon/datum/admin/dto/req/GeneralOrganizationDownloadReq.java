package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Author liushuyu
 * @Date 2022/7/22 10:02
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class GeneralOrganizationDownloadReq {

    @ApiModelProperty(value = "申请记录表id", required = true)
    @NotNull
    private Integer id;
}
