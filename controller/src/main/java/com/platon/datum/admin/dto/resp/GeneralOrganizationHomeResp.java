package com.platon.datum.admin.dto.resp;

import com.platon.datum.admin.dao.entity.Org;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2022/7/21 18:32
 * @Version
 * @Desc
 */


@Getter
@Setter
@ToString
@ApiModel
public class GeneralOrganizationHomeResp extends Org {

    @ApiModelProperty("已获取的证书数量")
    private int credentialsCount;

    @ApiModelProperty("已发出的申请数量")
    private int applyCount;

    @ApiModelProperty("是否可信任组织")
    private boolean canTrusted;
}
