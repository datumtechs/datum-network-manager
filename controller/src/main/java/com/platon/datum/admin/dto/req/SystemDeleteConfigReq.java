package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/4/2 18:16
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class SystemDeleteConfigReq {

    @ApiModelProperty("配置的键")
    private String key;
}
