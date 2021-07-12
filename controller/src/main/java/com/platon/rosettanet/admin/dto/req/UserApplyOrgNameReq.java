package com.platon.rosettanet.admin.dto.req;

import com.platon.rosettanet.admin.common.util.NameUtil;
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
public class UserApplyOrgNameReq {

    @Pattern(regexp = NameUtil.NAME_REG_STR)
    private String orgName;//身份标识名称
}
