package com.platon.datum.admin.dto.req;

import com.platon.datum.admin.dto.CommonPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/7/12 10:13
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class AttributeDataTokenPageReq extends CommonPageReq {

    @ApiModelProperty("根据凭证名称模糊查询")
    private String keyword;
}

