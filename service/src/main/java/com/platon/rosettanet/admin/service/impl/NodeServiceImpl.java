package com.platon.rosettanet.admin.service.impl;

import com.platon.rosettanet.admin.common.context.LocalOrgCache;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.LocalOrgMapper;
import com.platon.rosettanet.admin.dao.entity.LocalOrg;
import com.platon.rosettanet.admin.dao.enums.CarrierConnStatusEnum;
import com.platon.rosettanet.admin.dao.enums.CarrierStatusEnum;
import com.platon.rosettanet.admin.grpc.client.AuthClient;
import com.platon.rosettanet.admin.grpc.client.YarnClient;
import com.platon.rosettanet.admin.grpc.entity.CommonResp;
import com.platon.rosettanet.admin.grpc.entity.YarnGetNodeInfoResp;
import com.platon.rosettanet.admin.service.NodeService;
import com.platon.rosettanet.admin.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;

import static com.platon.rosettanet.admin.grpc.constant.GrpcConstant.GRPC_SUCCESS_CODE;

/**
 * @Author liushuyu
 * @Date 2021/7/13 16:26
 * @Version
 * @Desc
 */

@Slf4j
@Service
public class NodeServiceImpl implements NodeService {

    @Resource
    private LocalOrgMapper localOrgMapper;
    @Resource
    private AuthClient authClient;
    @Resource
    private YarnClient yarnClient;

    @Override
    public String connectNode(String ip, int port) {
        //### 1.尝试连接调度服务
        boolean success = yarnClient.connectScheduleServer(ip, port);
        if(!success){
            return "N";
        }
        LocalOrg localOrg = localOrgMapper.select();
        if(localOrg == null){
            throw new ServiceException("请先申请身份标识");
        }
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(ip, port);
        localOrg.setRecUpdateTime(new Date());
        localOrg.setCarrierIp(ip);
        localOrg.setCarrierPort(port);
        localOrg.setCarrierConnStatus(CarrierConnStatusEnum.ENABLED.getStatus());
        localOrg.setCarrierStatus(nodeInfo.getState());
        localOrg.setCarrierConnTime(new Date().toString());
        //入库
        int count = localOrgMapper.updateSelective(localOrg);
        //更新缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        LocalOrgIdentityCache.setIdentityId(localOrg.getIdentityId());
        return "Y";
    }

    @Override
    public void applyJoinNetwork() {
        LocalOrg localOrg = localOrgMapper.select();
        if(localOrg == null){
            throw new ServiceException("请先申请身份标识");
        }
        CommonResp resp = authClient.applyIdentityJoin(localOrg.getIdentityId(), localOrg.getName());
        if(resp.getStatus() != GRPC_SUCCESS_CODE){
            throw new ServiceException("入网失败：" + resp.getMsg());
        }

        //入网成功，刷新数据库
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(localOrg.getCarrierIp(), localOrg.getCarrierPort());
        if(nodeInfo.getStatus() != GRPC_SUCCESS_CODE){
            log.info("获取调度服务节点信息失败：" + nodeInfo.getMsg());
        } else {
            localOrg.setCarrierNodeId(nodeInfo.getNodeId());
            localOrg.setCarrierStatus(nodeInfo.getState());
            localOrgMapper.updateSelective(localOrg);
        }
        //刷新缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        LocalOrgIdentityCache.setIdentityId(localOrg.getIdentityId());
    }

    @Override
    public void cancelJoinNetwork() {
        LocalOrg localOrg = localOrgMapper.select();
        if(localOrg == null){
            throw new ServiceException("请先申请身份标识");
        }
        if(CarrierStatusEnum.LEAVE.getStatus().equals(localOrg.getCarrierStatus())){//已离开网络
            throw new ServiceException("还未入网，请先入网后再退网");
        }
        CommonResp resp = authClient.revokeIdentityJoin();
        if(resp.getStatus() != GRPC_SUCCESS_CODE){
            throw new ServiceException("退网失败：" + resp.getMsg());
        }

        //退网成功，刷新数据库
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(localOrg.getCarrierIp(), localOrg.getCarrierPort());
        if(nodeInfo.getStatus() != GRPC_SUCCESS_CODE){
            log.info("获取调度服务节点信息失败：" + nodeInfo.getMsg());
        } else {
            localOrg.setCarrierNodeId("");
            localOrg.setCarrierStatus(nodeInfo.getState());
            localOrgMapper.updateSelective(localOrg);
        }
        //刷新缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        LocalOrgIdentityCache.setIdentityId(localOrg.getIdentityId());
    }
}
