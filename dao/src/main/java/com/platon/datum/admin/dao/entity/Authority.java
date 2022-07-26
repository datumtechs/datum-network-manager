package com.platon.datum.admin.dao.entity;

import com.platon.datum.admin.dao.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author juzix
 * @Date 2022/7/21 12:02
 * @Version
 * @Desc ******************************
 */
@Getter
@Setter
@ToString
@ApiModel
public class Authority extends BaseDomain {
    /**
     * 组织的地址
     */
    @ApiModelProperty("组织的地址")
    private String identityId;

    /**
     * 组织的url
     */
    @ApiModelProperty("组织的url")
    private String url;

    /**
     * 加入委员会的时间
     */
    @ApiModelProperty("加入委员会的时间")
    private LocalDateTime joinTime;

    /**
     * 是否是初始成员：0-否，1-是
     */
    @ApiModelProperty("是否是初始成员：0-否，1-是")
    private Integer isAdmin;
}