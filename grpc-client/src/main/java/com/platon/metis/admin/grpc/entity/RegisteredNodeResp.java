package com.platon.metis.admin.grpc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lyf
 * @Description TODO
 * @date 2021/7/10 14:05
 */
@Getter
@Setter
@ToString
public class RegisteredNodeResp {
    private String nodeId;

    private String connStatus;

    private String internalIp;

    private Integer internalPort;

    private String externalIp;

    private Integer externalPort;
}
