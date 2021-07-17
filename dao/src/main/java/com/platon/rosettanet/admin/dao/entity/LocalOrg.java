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
    private String carrierIp;
    //调度服务端口号
    private Integer carrierPort;
    //连接状态 enabled：可用, disabled:不可用
    private String carrierConnStatus;
    //服务连接时间
    private String carrierConnTime;
    //调度服务的状态：active: 活跃; leave: 离开网络; join: 加入网络 unuseful: 不可用
    private String carrierStatus;
    //最后更新时间
    private Date recUpdateTime;

}