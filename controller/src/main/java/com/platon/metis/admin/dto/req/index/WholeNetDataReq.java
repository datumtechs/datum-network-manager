package com.platon.metis.admin.dto.req.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author houz
 * 全网数据走势请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "全网数据走势请求参数")
public class WholeNetDataReq {

    @ApiModelProperty(value = "请求标志（1：数据，2：算力）", example = "1", required = true)
    @NotBlank(message = "请求标志不能为空")
    private String flag;
}
