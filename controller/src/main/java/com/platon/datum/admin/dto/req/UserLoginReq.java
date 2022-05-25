package com.platon.datum.admin.dto.req;

import com.platon.datum.admin.common.annotation.CheckAddress;
import com.platon.datum.admin.common.util.AddressChangeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author liushuyu
 * @Date 2022/3/16 1:42
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel("登录请求参数")
public class UserLoginReq {

    @ApiModelProperty(value = "用户钱包地址", required = true, notes = "address", example = "501eb3eeb2a40e6f2ff6f481302435e6e8af3666")
    @CheckAddress(message = "{user.address.format}")
    private String address;

    @ApiModelProperty(value = "签名明文(json格式字符串)", required = true, example = "{\"domain\":{\"name\":\"Metis\"},\"message\":{\"key\":\"26e65a54b17e44b896a7f9a0353856d6\",\"desc\":\"Welcome to Metis!\"},\"primaryType\":\"Login\",\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"}],\"Login\":[{\"name\":\"key\",\"type\":\"string\"},{\"name\":\"desc\",\"type\":\"string\"}]}}")
    @NotNull(message = "{user.sign.plaintext.notBlank}")
    private String signMessage;

    @ApiModelProperty(value = "签名", required = true, notes = "sign", example = "HPXfBL0ZYeSMt6GcG8h8zOlPtlA8+LIQvF1AhEq4YZQLNfsgujDFDCzCSr/4ayfw4USAffxxA9OL0xMCVgE5Eg4=")
    @NotBlank(message = "{user.sign.notBlank}")
    private String sign;

    /**
     * @return : 返回0x地址
     */
    public String getAddress() {
        return AddressChangeUtil.convert0xAddress(address);
    }

    /**
     * @return : 返回hrp地址
     */
    public String getHrpAddress() {
        return address;
    }

}
