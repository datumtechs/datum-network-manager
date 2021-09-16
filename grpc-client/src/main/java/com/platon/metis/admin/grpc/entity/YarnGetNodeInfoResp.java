package com.platon.metis.admin.grpc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author liushuyu
 * @Date 2021/7/13 17:17
 * @Version
 * @Desc
 */

@Getter
@Setter
@ToString
public class YarnGetNodeInfoResp extends CommonResp{
//    // 调度服务的信息
//    message YarnNodeInfo {
//        string                      node_type     = 1;                             // 服务node的类型
//        string                      node_id       = 2;                               // 调度服务的 nodeId, 代表整个机构的 nodeId
//        string                      internal_ip   = 3;                           // 调度服务的内网 ip, 给 管理台用
//        string                      external_ip   = 4;                           // 调度服务的外网 ip, 给 外部做p2p用
//        string                      internal_port = 5;                         // 调度服务的内网 port, 给 管理台用
//        string                      external_port = 6;                         // 调度服务的外网 port, 给 外部做p2p用
//        string                      identity_type = 7;                         // 身份认证标识的类型 (ca 或者 did)
//        string                      identity_id   = 8;                           // 身份认证标识的id
//        ResourceUsedDetailShow      resource_used = 9;                         // 调度服务系统本身资源信息
//        repeated YarnRegisteredPeer peers         = 10;                                // 调度服务上被注册的 计算or数据服务节点信息
//        repeated SeedPeer           seed_peers    = 11;                           // 调度服务上被注册的种子节点信息
//        string                      state         = 12;                                // 调度服务自身的状态信息 (active: 活跃; leave: 离开网络; join: 加入网络 unuseful: 不可用)
//        string                      name          = 13;                                 // 组织的Name
//    }
    private String nodeType;     // 服务node的类型
    private String nodeId;       // 调度服务的 nodeId, 代表整个机构的 nodeId
    private String internalIp;   // 调度服务的内网 ip, 给 管理台用
    private String externalIp;   // 调度服务的外网 ip, 给 外部做p2p用
    private String internalPort; // 调度服务的内网 port, 给 管理台用
    private String externalPort; // 调度服务的外网 port, 给 外部做p2p用
    private String identityType; // 身份认证标识的类型 (ca 或者 did)
    private String identityId;   // 身份认证标识的id
    private String state;         // 调度服务自身的状态信息 (active: 活跃; leave: 离开网络; join: 加入网络 unuseful: 不可用)
    private String name;          // 组织的Name
}
