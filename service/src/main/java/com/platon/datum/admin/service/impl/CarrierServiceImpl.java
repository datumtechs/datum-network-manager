package com.platon.datum.admin.service.impl;

import com.platon.datum.admin.common.exception.ApplyIdentityIDFailed;
import com.platon.datum.admin.common.exception.IdentityIDApplied;
import com.platon.datum.admin.common.exception.OrgConnectNetworkAlready;
import com.platon.datum.admin.common.exception.OrgNotConnectNetwork;
import com.platon.datum.admin.dao.OrgMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dao.enums.CarrierConnStatusEnum;
import com.platon.datum.admin.grpc.client.AuthClient;
import com.platon.datum.admin.grpc.client.YarnClient;
import com.platon.datum.admin.grpc.entity.YarnGetNodeInfoResp;
import com.platon.datum.admin.service.CarrierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static com.platon.datum.admin.grpc.constant.GrpcConstant.GRPC_SUCCESS_CODE;

/**
 * @Author liushuyu
 * @Date 2021/7/13 16:26
 * @Version
 * @Desc
 */

@Slf4j
@Service
public class CarrierServiceImpl implements CarrierService {

    @Resource
    private OrgMapper orgMapper;
    @Resource
    private AuthClient authClient;
    @Resource
    private YarnClient yarnClient;

    @Override
    public CarrierConnStatusEnum connectNode(String ip, int port) {
        //尝试连接调度服务
        boolean success = yarnClient.connectScheduleServer(ip, port);
        if(!success){
            return CarrierConnStatusEnum.DISABLED;
        }
        Org org = (Org) OrgCache.getLocalOrgInfo();
        org.setRecUpdateTime(new Date());
        org.setCarrierIp(ip);
        org.setCarrierPort(port);
        org.setCarrierConnStatus(CarrierConnStatusEnum.ENABLED.getStatus());
        org.setCarrierConnTime(new Date());
        //入库
        int count = orgMapper.update(org);
        //更新缓存
        OrgCache.setLocalOrgInfo(org);
        return CarrierConnStatusEnum.ENABLED;
    }

    @Override
    public Integer applyJoinNetwork() {
        Org org = (Org) OrgCache.getLocalOrgInfo();

        if(org.getStatus()== Org.Status.CONNECTED.getCode()){
            throw new OrgConnectNetworkAlready ();
        }

        try {
            authClient.applyIdentityJoin(org.getIdentityId(), org.getName(), org.getImageUrl(), org.getProfile());
        }catch (Exception e){
            log.error("入网失败:" , e);
            throw new ApplyIdentityIDFailed();
        }

        YarnGetNodeInfoResp nodeInfo;
        try {
            nodeInfo = yarnClient.getNodeInfo(org.getCarrierIp(), org.getCarrierPort());
        }catch (Exception e){
            log.error("入网失败:" , e);
            throw new ApplyIdentityIDFailed();
        }

        if(!org.getIdentityId().equals(nodeInfo.getIdentityId())){
            throw new IdentityIDApplied();
        }

        //入网成功，刷新数据库
        org.setCarrierNodeId(nodeInfo.getNodeId());
        org.setCarrierStatus(nodeInfo.getState());
        org.setConnNodeCount(nodeInfo.getConnCount());
        org.setBootstrapNode(nodeInfo.getLocalBootstrapNode());
        org.setMultiAddr(nodeInfo.getLocalMultiAddr());

        org.setStatus(Org.Status.CONNECTED.getCode());
        orgMapper.update(org);
        //刷新缓存
        OrgCache.setLocalOrgInfo(org);
        return org.getStatus();


    }

    @Override
    public Integer cancelJoinNetwork() {
        Org org = (Org) OrgCache.getLocalOrgInfo();
        if(org.getStatus()!= Org.Status.CONNECTED.getCode()){
           throw new OrgNotConnectNetwork();
        }
        authClient.revokeIdentityJoin();

        //退网成功，刷新数据库
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(org.getCarrierIp(), org.getCarrierPort());
        if(nodeInfo.getStatus() != GRPC_SUCCESS_CODE){
            log.info("获取调度服务节点信息失败：" + nodeInfo.getMsg());
        } else {
            org.setCarrierStatus(nodeInfo.getState());
        }
        org.setStatus(Org.Status.LEFT_NET.getCode());
        org.setCarrierNodeId("");
        org.setConnNodeCount(0);
        orgMapper.update(org);
        //刷新缓存
        OrgCache.setLocalOrgInfo(org);
        return org.getStatus();
    }
}