package com.platon.rosettanet.admin.grpc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2021/7/9 14:29
 * @Version
 * @Desc
 */

/**
 * 可用的数据节点的信息
 */
@Getter
@Setter
@ToString
public class AvailableDataNodeResp {

    //数据节点ip
    public String ip;
    //数据节点port
    public int port;
}
