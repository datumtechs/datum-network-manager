package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Author liushuyu
 * @Date 2022/3/24 11:39
 * @Version
 * @Desc
 */

@ApiModel
@Getter
@Setter
@ToString
public class NoAttributeDataTokenGetDataTokenStatusReq {

    @ApiModelProperty("dataToken的数据库主键id")
    @NotNull
    private Integer id;
}
