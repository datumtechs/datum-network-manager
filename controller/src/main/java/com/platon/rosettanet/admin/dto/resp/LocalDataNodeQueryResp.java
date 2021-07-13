package com.platon.rosettanet.admin.dto.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lyf
 * @Description 数据节点查询返回实体
 * @date 2021/7/13 13:50
 */
@Getter
@Setter
@ToString
public class LocalDataNodeQueryResp {
    private Integer id;

    private String nodeId;

    private String nodeName;

    private String connStatus;

    private String internalIp;

    private Integer internalPort;

    private String externalIp;

    private Integer externalPort;

}
