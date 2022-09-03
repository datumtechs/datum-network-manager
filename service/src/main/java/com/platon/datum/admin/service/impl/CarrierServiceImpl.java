package com.platon.datum.admin.service.impl;

import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dao.enums.CarrierConnStatusEnum;
import com.platon.datum.admin.grpc.client.AuthClient;
import com.platon.datum.admin.grpc.client.YarnClient;
import com.platon.datum.admin.grpc.entity.YarnGetNodeInfoResp;
import com.platon.datum.admin.service.AuthorityService;
import com.platon.datum.admin.service.CarrierService;
import com.platon.datum.admin.service.OrgService;
import com.platon.datum.admin.service.VoteContract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    private OrgService orgService;
    @Resource
    private AuthClient authClient;
    @Resource
    private YarnClient yarnClient;
    @Resource
    private AuthorityService authorityService;
    @Resource
    private VoteContract voteContract;

    @Override
    public CarrierConnStatusEnum connectNode(String ip, int port) {
        //尝试连接调度服务
        boolean success = yarnClient.connectScheduleServer(ip, port);
        if (!success) {
            return CarrierConnStatusEnum.DISABLED;
        }
        Org org = OrgCache.getLocalOrgInfo();
        org.setRecUpdateTime(LocalDateTimeUtil.now());
        org.setCarrierIp(ip);
        org.setCarrierPort(port);
        org.setCarrierConnStatus(CarrierConnStatusEnum.ENABLED.getStatus());
        org.setCarrierConnTime(LocalDateTimeUtil.now());
        //入库
        orgService.updateSelective(org);
        return CarrierConnStatusEnum.ENABLED;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Integer applyJoinNetwork() {
        Org org = OrgCache.getLocalOrgInfo();

        if (org.getStatus() == Org.StatusEnum.CONNECTED.getCode()) {
            throw new BizException(Errors.OrgConnectNetworkAlready);
        }

        try {
            authClient.applyIdentityJoin(org.getIdentityId(), org.getName(), org.getImageUrl(), org.getProfile());
        } catch (BizException exception) {
            //已经注册过了
            if (exception.getErrorCode() == Errors.OrgConnectNetworkAlready.getCode()) {
                //do nothing
            } else {
                throw new BizException(Errors.ApplyIdentityIDFailed, exception);
            }
        } catch (Exception e) {
            throw new BizException(Errors.ApplyIdentityIDFailed, e);
        }

        YarnGetNodeInfoResp nodeInfo;
        try {
            nodeInfo = yarnClient.getNodeInfo(org.getCarrierIp(), org.getCarrierPort());
        } catch (Exception e) {
            throw new BizException(Errors.ApplyIdentityIDFailed, e);
        }

        if (!org.getIdentityId().equals(nodeInfo.getIdentityId())) {
            throw new BizException(Errors.IdentityIDApplied);
        }

        //刷新委员会列表
//        authorityService.refreshAuthority();

        //入网成功，刷新数据库
        org.setCarrierNodeId(nodeInfo.getNodeId());
        org.setCarrierStatus(nodeInfo.getState());
        org.setConnNodeCount(nodeInfo.getConnCount());
        org.setLocalBootstrapNode(nodeInfo.getLocalBootstrapNode());
        org.setLocalMultiAddr(nodeInfo.getLocalMultiAddr());
        org.setStatus(Org.StatusEnum.CONNECTED.getCode());
        Org org1 = orgService.updateSelective(org);
        return org1.getStatus();
    }

    @Override
    public Integer cancelJoinNetwork() {
        Org org = OrgCache.getLocalOrgInfo();
        if (org.getStatus() != Org.StatusEnum.CONNECTED.getCode()) {
            throw new BizException(Errors.OrgNotConnectNetwork);
        }

        //实时的去获取委员会成员列表
        List<Authority> allAuthority = voteContract.getAllAuthority();
        allAuthority.forEach(authority -> {
            if (authority.getIdentityId().equalsIgnoreCase(org.getIdentityId())) {
                //委员会成员需要先退出委员会才能退网
                throw new BizException(Errors.AuthorityCantExitNetwork);
            }
        });

        authClient.revokeIdentityJoin();

        //退网成功，刷新数据库
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(org.getCarrierIp(), org.getCarrierPort());
        if (nodeInfo.getStatus() != GRPC_SUCCESS_CODE) {
            log.info("Get node info failed：" + nodeInfo.getMsg());
        } else {
            org.setCarrierStatus(nodeInfo.getState());
        }
        org.setStatus(Org.StatusEnum.LEFT_NET.getCode());
        org.setCarrierNodeId("");
        org.setConnNodeCount(0);
        Org org1 = orgService.updateSelective(org);
        return org1.getStatus();
    }
}