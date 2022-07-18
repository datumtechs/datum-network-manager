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
 * @Date 2022/7/12 10:14
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class AttributeDataTokenGetDataTokenInventoryPageReq extends CommonPageReq {

    @ApiModelProperty("凭证合约地址")
    private String dataTokenAddress;

    @ApiModelProperty("支持名字和tokenId模糊查询")
    private String keyword;

}
