package com.platon.datum.admin.dto.req;

import com.platon.datum.admin.dao.entity.AttributeDataToken;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/7/12 10:12
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class AttributeDataTokenUpdateStatusReq {

    @ApiModelProperty("dataToken的id")
    //dataToken的id
    private Integer dataTokenId;

    /**
     * 状态：0-未发布，1-发布中，2-发布失败，3-发布成功
     * {@link AttributeDataToken.StatusEnum}
     */
    @ApiModelProperty("状态：0-未发布，1-发布中，2-发布失败，3-发布成功")
    private int status;
}
