package com.platon.metis.admin.dao.entity;


import lombok.Data;

import java.util.Date;

/**
 * @author houz
 */
@Data
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

    /** 连接的节点数量 */
    private String connNodeCount;
    //服务连接时间
    private Date carrierConnTime;
    //调度服务的状态：active: 活跃; leave: 离开网络; join: 加入网络 unuseful: 不可用
    private String carrierStatus;
    //最后更新时间
    private Date recUpdateTime;
    //0未入网，1已入网
    private Integer status;
}