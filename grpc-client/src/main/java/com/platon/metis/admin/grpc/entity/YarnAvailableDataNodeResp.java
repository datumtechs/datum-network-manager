package com.platon.metis.admin.grpc.entity;

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
public class YarnAvailableDataNodeResp {

    //数据节点ip
    private String ip;
    //数据节点port
    private int port;
}
