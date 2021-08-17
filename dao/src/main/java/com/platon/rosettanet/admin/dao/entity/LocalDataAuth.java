package com.platon.rosettanet.admin.dao.entity;

import com.platon.rosettanet.admin.dao.BaseDomain;
import lombok.Data;

import java.util.Date;

@Data
public class LocalDataAuth extends BaseDomain {

    //序号
    private Integer id;
    //元数据ID
    private String metaDataId;
    //授权发起方身份标识
    private String ownerIdentityId;
    //授权发起方名称
    private String ownerIdentityName;
    //授权方式(1：时间，2：次数)
    private Boolean authType;
    //授权值(以授权次数)
    private Integer authValueAmount;
    //授权值开始时间
    private Date authValueStartAt;
    //授权值结束时间
    private Date authValueEndAt;
    //授权申请发起时间
    private Date createAt;
    //授权数据时间
    private Date authAt;
    //授权数据状态：1:同意， 2:拒绝 ，3:待授权，4:失效
    private int status;
    //创建时间
    private Date recCreateTime;
    //最后更新时间
    private Date recUpdateTime;


}