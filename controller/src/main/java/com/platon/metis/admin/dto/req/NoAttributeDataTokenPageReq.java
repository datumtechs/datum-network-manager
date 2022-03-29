package com.platon.metis.admin.dto.req;

import com.platon.metis.admin.dto.CommonPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @ApiModelProperty("定价状态：0-未定价，1-已定价")
    //状态：0-未定价，1-已定价，如果要已定价的，则返回已成功定价的，如果要未定价的，则返回(0-未定价，1-定价中，2-定价失败)供前端展示
    private int status;
}
