package com.platon.rosettanet.admin.dto.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:56
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class SystemQueryBaseInfoResp {
    private String nodeName;//机构识别名称
    private String identityId;//机构身份标识
}
