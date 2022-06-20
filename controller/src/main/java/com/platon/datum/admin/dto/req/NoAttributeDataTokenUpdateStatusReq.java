package com.platon.datum.admin.dto.req;

import com.platon.datum.admin.dao.entity.DataToken;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/3/24 20:28
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel
public class NoAttributeDataTokenUpdateStatusReq {

    @ApiModelProperty("dataToken的id")
    //dataToken的id
    private Integer dataTokenId;

    /**
     * 状态：0-未发布，1-发布中，2-发布失败，3-发布成功，4-定价中，5-定价失败，6-定价成功
     * {@link DataToken.StatusEnum}
     */
    @ApiModelProperty("状态：0-未发布，1-发布中，2-发布失败，3-发布成功，4-定价中，5-定价失败，6-定价成功")
    private int status;
}
