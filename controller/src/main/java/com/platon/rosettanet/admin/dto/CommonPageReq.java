package com.platon.rosettanet.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @Author liushuyu
 * @Date 2021/7/2 17:20
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class CommonPageReq {

    /**
     * 页码
     */
    @ApiModelProperty(name = "pageNumber", value = "页码", required = true)
    @NotNull(message = "起始页号不能为空")
    private Integer pageNumber;

    /**
     * 每页数据条数
     */
    @ApiModelProperty(name = "pageSize", value = "每页数据条数", required = true)
    @NotNull(message = "每页数据条数不能为空")
    private Integer pageSize;
}
