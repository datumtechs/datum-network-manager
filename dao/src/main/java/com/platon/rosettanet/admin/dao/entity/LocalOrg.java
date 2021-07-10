package com.platon.rosettanet.admin.dao.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class LocalOrg {
    //机构名称
    private String name;
    //机构身份标识ID
    private String identityId;
    //机构调度服务node id，入网后可以获取到
    private String carrierNodeId;
    //调度服务IP地址
    private String carrierIP;
    //调度服务端口号
    private int carrierPort;
    //连接状态 enabled：可用, disabled:不可用
    private String carrierConnStatus;
    //服务连接时间
    private String carrierConnTime;
    //服务状态 enabled：可用, disabled:不可用
    private String carrierStatus;
    //最后更新时间
    private Date recUpdateTime;

}