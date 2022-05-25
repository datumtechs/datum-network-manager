package com.platon.datum.admin.dto.req;

import com.platon.datum.admin.dto.CommonPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2021/7/15 15:48
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class LocalDataMetaDataListByKeyWordReq extends CommonPageReq {

    @ApiModelProperty(value = "0-查询所有状态，1-查询已发凭证，2-查询未发凭证")
    private int status = 0;

    @ApiModelProperty(value = "关键字",required = false)
    private String keyword;
}
