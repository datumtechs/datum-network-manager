package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.common.exception.ApplyIdentityIDFailed;
import com.platon.metis.admin.common.exception.IdentityIDApplied;
import com.platon.metis.admin.common.exception.OrgConnectNetworkAlready;
import com.platon.metis.admin.common.exception.OrgNotConnectNetwork;
import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.cache.LocalOrgCache;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.dao.enums.CarrierConnStatusEnum;
import com.platon.metis.admin.grpc.client.AuthClient;
import com.platon.metis.admin.grpc.client.YarnClient;
import com.platon.metis.admin.grpc.entity.YarnGetNodeInfoResp;
import com.platon.metis.admin.service.CarrierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static com.platon.metis.admin.grpc.constant.GrpcConstant.GRPC_SUCCESS_CODE;

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
    private LocalOrgMapper localOrgMapper;
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
        LocalOrg localOrg = (LocalOrg)LocalOrgCache.getLocalOrgInfo();
        localOrg.setRecUpdateTime(new Date());
        localOrg.setCarrierIp(ip);
        localOrg.setCarrierPort(port);
        localOrg.setCarrierConnStatus(CarrierConnStatusEnum.ENABLED.getStatus());
        localOrg.setCarrierConnTime(new Date());
        //入库
        int count = localOrgMapper.update(localOrg);
        //更新缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        return CarrierConnStatusEnum.ENABLED;
    }

    @Override
    public Integer applyJoinNetwork() {
        LocalOrg localOrg = (LocalOrg)LocalOrgCache.getLocalOrgInfo();

        if(localOrg.getStatus()==LocalOrg.Status.CONNECTED.getCode()){
            throw new OrgConnectNetworkAlready ();
        }

        try {
            authClient.applyIdentityJoin(localOrg.getIdentityId(), localOrg.getName(), localOrg.getImageUrl(), localOrg.getProfile());
        }catch (Exception e){
            log.error("入网失败:" , e);
            throw new ApplyIdentityIDFailed();
        }

        YarnGetNodeInfoResp nodeInfo;
        try {
            nodeInfo = yarnClient.getNodeInfo(localOrg.getCarrierIp(), localOrg.getCarrierPort());
        }catch (Exception e){
            log.error("入网失败:" , e);
            throw new ApplyIdentityIDFailed();
        }

        if(!localOrg.getIdentityId().equals(nodeInfo.getIdentityId())){
            throw new IdentityIDApplied();
        }

        //入网成功，刷新数据库
        localOrg.setCarrierNodeId(nodeInfo.getNodeId());
        localOrg.setCarrierStatus(nodeInfo.getState());
        localOrg.setConnNodeCount(nodeInfo.getConnCount());
        localOrg.setLocalBootstrapNode(nodeInfo.getLocalBootstrapNode());
        localOrg.setLocalMultiAddr(nodeInfo.getLocalMultiAddr());

        localOrg.setStatus(LocalOrg.Status.CONNECTED.getCode());
        localOrgMapper.update(localOrg);
        //刷新缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        return localOrg.getStatus();


    }

    @Override
    public Integer cancelJoinNetwork() {
        LocalOrg localOrg = (LocalOrg)LocalOrgCache.getLocalOrgInfo();
        if(localOrg.getStatus()!=LocalOrg.Status.CONNECTED.getCode()){
           throw new OrgNotConnectNetwork();
        }
        authClient.revokeIdentityJoin();

        //退网成功，刷新数据库
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(localOrg.getCarrierIp(), localOrg.getCarrierPort());
        if(nodeInfo.getStatus() != GRPC_SUCCESS_CODE){
            log.info("获取调度服务节点信息失败：" + nodeInfo.getMsg());
        } else {
            localOrg.setCarrierStatus(nodeInfo.getState());
        }
        localOrg.setStatus(LocalOrg.Status.LEFT_NET.getCode());
        localOrg.setCarrierNodeId("");
        localOrg.setConnNodeCount(0);
        localOrgMapper.update(localOrg);
        //刷新缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        return localOrg.getStatus();
    }
}