package com.platon.metis.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/4/2 18:12
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class SystemAddConfigReq {

    @ApiModelProperty("配置的键")
    private String key;

    @ApiModelProperty("配置的值")
    private String value;

    @ApiModelProperty("配置的描述")
    private String desc;
}
