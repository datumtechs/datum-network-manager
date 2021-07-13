package com.platon.rosettanet.admin.dto.resp;

import com.platon.rosettanet.admin.dao.entity.LocalOrg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

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

    //机构名称
    private String name;
    //机构身份标识ID
    private String identityId;

    public static SystemQueryBaseInfoResp from(LocalOrg localOrg){
        SystemQueryBaseInfoResp resp = new SystemQueryBaseInfoResp();
        BeanUtils.copyProperties(localOrg,resp);
        return resp;
    }
}
