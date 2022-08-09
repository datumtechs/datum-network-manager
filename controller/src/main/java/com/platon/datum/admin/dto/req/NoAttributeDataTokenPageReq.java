package com.platon.datum.admin.dto.req;

import com.platon.datum.admin.dao.entity.DataToken;
import com.platon.datum.admin.dto.CommonPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Author liushuyu
 * @Date 2022/3/24 11:39
 * @Version
 * @Desc
 */

@ApiModel("获取数据凭证列表请求参数")
@Getter
@Setter
@ToString
public class NoAttributeDataTokenPageReq extends CommonPageReq {

    @ApiModelProperty(value = "按凭证名称查询",required = false)
    private String keyword;
}
