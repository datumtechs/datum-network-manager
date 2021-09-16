package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.common.context.LocalOrgCache;
import com.platon.metis.admin.common.context.LocalOrgIdentityCache;
import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.dao.enums.CarrierConnStatusEnum;
import com.platon.metis.admin.dao.enums.LocalOrgStatusEnum;
import com.platon.metis.admin.grpc.client.AuthClient;
import com.platon.metis.admin.grpc.client.YarnClient;
import com.platon.metis.admin.grpc.entity.CommonResp;
import com.platon.metis.admin.grpc.entity.YarnGetNodeInfoResp;
import com.platon.metis.admin.service.NodeService;
import com.platon.metis.admin.service.exception.ServiceException;
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
public class NodeServiceImpl implements NodeService {

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
        int count = localOrgMapper.updateSelective(localOrg);
        //更新缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        LocalOrgIdentityCache.setIdentityId(localOrg.getIdentityId());
        return CarrierConnStatusEnum.ENABLED;
    }

    @Override
    public Integer applyJoinNetwork() {
        LocalOrg localOrg = (LocalOrg)LocalOrgCache.getLocalOrgInfo();
        CommonResp resp = authClient.applyIdentityJoin(localOrg.getIdentityId(), localOrg.getName());
        if(resp.getStatus() != GRPC_SUCCESS_CODE){
            throw new ServiceException("入网失败：" + resp.getMsg());
        }

        //入网成功，刷新数据库
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(localOrg.getCarrierIp(), localOrg.getCarrierPort());
        if(nodeInfo.getStatus() != GRPC_SUCCESS_CODE){
            log.info("获取调度服务节点信息失败：" + nodeInfo.getMsg());
        } else {
            if(!localOrg.getIdentityId().equals(nodeInfo.getIdentityId())){
                throw new ServiceException("入网失败：其他组织已通过该调度服务入网");
            }
            localOrg.setCarrierNodeId(nodeInfo.getNodeId());
            localOrg.setCarrierStatus(nodeInfo.getState());
        }
        localOrg.setStatus(LocalOrgStatusEnum.JOIN.getStatus());
        localOrgMapper.updateSelective(localOrg);
        //刷新缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        LocalOrgIdentityCache.setIdentityId(localOrg.getIdentityId());
        return localOrg.getStatus();
    }

    @Override
    public Integer cancelJoinNetwork() {
        LocalOrg localOrg = (LocalOrg)LocalOrgCache.getLocalOrgInfo();
        CommonResp resp = authClient.revokeIdentityJoin();
        if(resp.getStatus() != GRPC_SUCCESS_CODE){
            throw new ServiceException("退网失败：" + resp.getMsg());
        }

        //退网成功，刷新数据库
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(localOrg.getCarrierIp(), localOrg.getCarrierPort());
        if(nodeInfo.getStatus() != GRPC_SUCCESS_CODE){
            log.info("获取调度服务节点信息失败：" + nodeInfo.getMsg());
        } else {
            localOrg.setCarrierStatus(nodeInfo.getState());
        }
        localOrg.setStatus(LocalOrgStatusEnum.LEAVE.getStatus());
        localOrg.setCarrierNodeId("");
        localOrgMapper.updateSelective(localOrg);
        //刷新缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        LocalOrgIdentityCache.setIdentityId(localOrg.getIdentityId());
        return localOrg.getStatus();
    }
}
