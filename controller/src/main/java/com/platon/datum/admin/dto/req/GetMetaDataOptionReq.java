package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Author liushuyu
 * @Date 2022/8/3 14:23
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class GetMetaDataOptionReq {

    /**
     * 元数据Id
     */
    @NotNull(message = "元数据ID不能为空")
    @ApiModelProperty(value = "元数据ID(DB自增)", required = true)
    private Integer id;

}
