package com.platon.datum.admin.dto.req;

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
public class NoAttributeDataTokenUpdateFeeReq {

    @ApiModelProperty("dataToken的id")
    //dataToken的id
    private Integer dataTokenId;

    @ApiModelProperty("想要修改成的明文费用")
    private String newPlaintextFee;
    @ApiModelProperty("想要修改成的密文费用")
    private String newCiphertextFee;

    @ApiModelProperty("用户签名")
    private String sign;
}
