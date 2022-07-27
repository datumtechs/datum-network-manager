package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/7/22 14:54
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class AuthorityProcessTodoReq {

    @ApiModelProperty(value = "处理的事务id", required = true)
    private int id;

    @ApiModelProperty(value = "处理结果：1-同意，2-不同意", required = true)
    private int result;
}