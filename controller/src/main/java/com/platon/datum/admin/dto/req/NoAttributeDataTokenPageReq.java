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

    @ApiModelProperty("定价状态：0-未定价，1-已定价")
    /**
     * {@link DataToken.StatusEnum}
     * 所有状态：0-未发布，1-发布中，2-发布失败，3-发布成功，4-定价中，5-定价失败，6-定价成功
     * 前端传0-未定价则返回：1-发布中，3-发布成功
     * 前端传1-已定价则返回4-定价中，6-定价成功
     */
    @NotNull
    private Integer status;
}