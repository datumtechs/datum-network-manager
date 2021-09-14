package com.platon.rosettanet.admin.dao.entity;

import com.platon.rosettanet.admin.dao.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class LocalDataAuth extends BaseDomain {

    //序号
    private Integer id;
    //元数据授权申请Id
    private String authId;
    //元数据ID
    private String metaDataId;
    //发起任务的用户的信息 (task是属于用户的)
    private String applyUser;
    //发起任务用户类型 (0: 未定义; 1: 以太坊地址; 2: Alaya地址; 3: PlatON地址)
    private Integer userType;
    //授权方式(0：未定义，1：时间，2：次数)
    private Integer authType;
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
    //授权数据状态：0：等待授权审核，1:同意， 2:拒绝
    private Integer status;
    //元数据所属的组织信息，组织名称
    private String identityName;
    //元数据所属的组织信息,组织的身份标识Id
    private String identityId;
    //组织中调度服务的 nodeId
    private String identityNodeId;
    //创建时间
    private Date recCreateTime;
    //最后更新时间
    private Date recUpdateTime;


}