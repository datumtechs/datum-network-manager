package com.platon.metis.admin.dto.req;

import com.platon.metis.admin.common.util.NameUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

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
public class UserApplyOrgIdentityReq {

    @Pattern(regexp = NameUtil.NAME_REG_STR,message = "仅支持中英文与数字输入，最多12个字符")
    @ApiModelProperty(value = "组织名称",required = true)
    private String orgName;//身份标识名称
}
