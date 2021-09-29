package com.platon.metis.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Author liushuyu
 * @Date 2021/7/10 15:34
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel
public class LocalDataActionReq {

    //元数据Id
    @NotNull(message = "元数据ID不能为空")
    @ApiModelProperty(value = "元数据ID(DB自增)",required = true)
    private Integer id;
    //元数据上下架和删除动作 (-1: 删除; 0: 下架; 1: 上架)
    @ApiModelProperty(value = "元数据上下架和删除动作 (-1: 删除; 0: 下架; 1: 上架)",required = true)
    private String action;

}
