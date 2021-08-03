package com.platon.rosettanet.admin.service;

import com.platon.rosettanet.admin.dao.enums.CarrierConnStatusEnum;

/**
 * @Author liushuyu
 * @Date 2021/7/13 16:26
 * @Version
 * @Desc
 */
public interface NodeService {

    /**
     * 连接调度节点
     * @param ip
     * @param port
     * @return
     */
    CarrierConnStatusEnum connectNode(String ip, int port);

    /**
     * 通知调度服务，申请准入网络
     */
    Integer applyJoinNetwork();

    /**
     * 调用该接口后，其对应的调度服务从网络中退出，无法继续参与隐私网络中的相关任务项
     */
    void cancelJoinNetwork();
}
