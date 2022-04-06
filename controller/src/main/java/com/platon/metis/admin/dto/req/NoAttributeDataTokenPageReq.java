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
    /**
     * {@link com.platon.metis.admin.dao.entity.DataToken.StatusEnum}
     * 状态：0-未发布，1-发布中，2-发布失败，3-发布成功，4-定价中，5-定价失败，6-定价成功
     * 前端传0-未定价：0-未发布，1-发布中，2-发布失败，3-发布成功，4-定价中，5-定价失败，
     * 前端传6-定价成功
     */
    private int status;
}
