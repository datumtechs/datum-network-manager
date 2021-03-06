package com.platon.metis.admin.dto.req;

import com.platon.metis.admin.dto.CommonPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2021/7/15 16:33
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class GlobalDataMetaDataListByKeyWordReq extends CommonPageReq {

    @ApiModelProperty(value = "关键字",required = false)
    private String keyword;
}
