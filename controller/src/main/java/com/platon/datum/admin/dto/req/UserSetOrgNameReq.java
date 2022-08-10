package com.platon.datum.admin.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:27
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
@ApiModel
public class UserSetOrgNameReq {


    /**
     * 命名规则：
     * <p>
     * 1. 设置后可以修改；
     * <p>
     * 2. 输入的字符类型无限制；
     * <p>
     * 3. 最少输入8个字符；
     * <p>
     * 4. 最多输入64个字符。
     */
    @Length(min = 8, max = 64)
    @NotBlank
    @ApiModelProperty(value = "组织名称", required = true)
    private String orgName;
}
