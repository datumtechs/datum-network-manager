package com.platon.rosettanet.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.regex.Matcher;

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
    @NotBlank(message = "元数据ID不能为空")
    @ApiModelProperty(value = "元数据ID",required = true)
    private String metaDataId;
    //元数据上下架和删除动作 (-1: 删除; 0: 下架; 1: 上架)
    @ApiModelProperty(value = "元数据上下架和删除动作 (-1: 删除; 0: 下架; 1: 上架)",required = true)
    private String action;

}
