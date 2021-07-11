package com.platon.rosettanet.admin.dto.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author liushuyu
 * @Date 2021/7/10 15:34
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
public class LocalDataActionReq {

    //元数据Id
    @NotBlank(message = "元数据ID不能为空")
    private String metaDataId;
    //元数据上下架和删除动作 (-1: 删除; 0: 下架; 1: 上架)
    @Pattern(regexp = "^[0,-1,1]$", message = "-1: 删除; 0: 下架; 1: 上架")
    private int action;

}
