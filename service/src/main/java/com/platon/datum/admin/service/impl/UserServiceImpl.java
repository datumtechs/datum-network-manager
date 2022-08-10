package com.platon.datum.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.health.HealthServicesRequest;
import com.ecwid.consul.v1.health.model.HealthService;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.SysUserMapper;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dao.entity.SysUser;
import com.platon.datum.admin.dao.enums.CarrierConnStatusEnum;
import com.platon.datum.admin.grpc.client.DidClient;
import com.platon.datum.admin.grpc.client.YarnClient;
import com.platon.datum.admin.grpc.entity.YarnGetNodeInfoResp;
import com.platon.datum.admin.service.OrgService;
import com.platon.datum.admin.service.UserService;
import com.platon.datum.admin.service.web3j.Web3jManager;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.DefaultBlockParameterName;
import com.platon.protocol.core.methods.response.PlatonGetBalance;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:35
 * @Version
 * @Desc
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private OrgService orgService;

    @Resource
    private YarnClient yarnClient;

    @Value("${spring.cloud.consul.host}")
    private String consulHost;

    @Value("${spring.cloud.consul.port}")
    private Integer consulPort;

    @Value("${spring.cloud.consul.carrierServiceName}")
    private String carrierServiceName;

    @Resource
    private DidClient didClient;

    @Resource
    private Web3jManager web3jManager;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Org applyOrgIdentity() {
        //### 1.校验是否已存在组织信息
        Org org = orgService.select();
        if (org == null) {
            throw new BizException(Errors.OrgInfoNotFound);
        }
        Web3j web3j = web3jManager.getWeb3j();
        try {
            String walletAddress = org.getObserverProxyWalletAddress();
            PlatonGetBalance platonGetBalance = web3j.platonGetBalance(walletAddress, DefaultBlockParameterName.LATEST).send();
            BigInteger balance = platonGetBalance.getBalance();
            if (balance.compareTo(BigInteger.ZERO) <= 0) {
                throw new BizException(Errors.InsufficientWalletBalance, StrUtil.format("Insufficient wallet balance {} : {}", walletAddress, balance));
            }
        } catch (IOException e) {
            throw new BizException(Errors.SysException, e);
        }
        //TODO 判断did是否已经创建过
        String did = didClient.createDID(org.getCarrierIp(), org.getCarrierPort());
        log.debug("申请did：" + did);
        org.setIdentityId(did);
        Org org1 = orgService.updateSelective(org);
        return org1;
    }

    //获取调度服务信息
    private Org getCarrierInfo() {
        Org org = new Org();
        ConsulClient consulClient = new ConsulClient(consulHost, consulPort);
        // 从 consul 查询注册的carrier服务
        HealthServicesRequest request = HealthServicesRequest.newBuilder()
                .setPassing(true)
                .setTag("carrier")
                .setQueryParams(QueryParams.DEFAULT)
                .build();

        List<HealthService> serviceList = consulClient.getHealthServices(carrierServiceName, request).getValue();
        if (CollectionUtils.isEmpty(serviceList)) {
            throw new BizException(Errors.SysException, "No dispatching service information was queried");
        }
        String ip = serviceList.get(0).getService().getAddress();
        Integer port = serviceList.get(0).getService().getPort();
        if (StringUtils.isEmpty(ip) || port == null) {
            throw new BizException(Errors.SysException, "No dispatching service information was queried");
        }
        //记录查询到的carrier地址
        org.setCarrierIp(ip);
        org.setCarrierPort(port);
        //### 刷新调度服务状态和入网状态
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(org.getCarrierIp(), org.getCarrierPort());
        org.setCarrierStatus(nodeInfo.getState());
        org.setCarrierNodeId(nodeInfo.getNodeId());
        org.setCarrierConnStatus(CarrierConnStatusEnum.ENABLED.getStatus());
        org.setCarrierConnTime(LocalDateTimeUtil.now());
        org.setConnNodeCount(nodeInfo.getConnCount());
        org.setLocalBootstrapNode(nodeInfo.getLocalBootstrapNode());
        org.setLocalMultiAddr(nodeInfo.getLocalMultiAddr());
        return org;
    }

    @Override
    public SysUser getByAddress(String address) {
        return sysUserMapper.selectByAddress(address);
    }

    @Transactional
    @Override
    public SysUser createUser(String hexAddress) {
        SysUser user = new SysUser();
        user.setUserName(hexAddress);
        user.setAddress(hexAddress);
        user.setStatus(1);
        //设置角色，默认0是普通用户，默认1是管理员
        user.setIsAdmin(0);
        //1.新增用户
        int insert = sysUserMapper.insert(user);
        if (insert == 0) {
            throw new BizException(Errors.CreateNewUserFailed);
        } else {
            //2.尝试设置为管理员
            int update = sysUserMapper.updateSingleUserToAdmin();
            if (update > 0) {
                user.setIsAdmin(1);
            }
        }
        return user;
    }

    @Transactional
    @Override
    public void updateAdmin(SysUser oldAdmin, String newAddress) {
        SysUser user = new SysUser();
        user.setUserName(newAddress);
        user.setAddress(newAddress);
        user.setStatus(1);
        user.setIsAdmin(1);
        //1.新增用户
        sysUserMapper.insertOrUpdateToAdmin(user);

        oldAdmin.setIsAdmin(0);
        sysUserMapper.updateByAddress(oldAdmin);
    }

    /**
     * 设置组织名称
     *
     * @param orgName
     * @return
     */
    @Override
    public Org setOrgName(String orgName) {
        //### 1.校验是否已存在组织信息
        Org org = orgService.select();
        if (org != null) {
            throw new BizException(Errors.OrgInfoExists);
        }
        //1.1从注册中心获取调度服务的信息
        Org localOrg = getCarrierInfo();
        //### 1.2 调用调度服务接口生成见证人钱包
        String walletAddress = yarnClient.generateObServerProxyWalletAddress(localOrg.getCarrierIp(), localOrg.getCarrierPort());
        localOrg.setObserverProxyWalletAddress(walletAddress);
        //### 2.新建local org并入库
        localOrg.setName(orgName);
        localOrg.setStatus(Org.StatusEnum.NOT_CONNECT_NET.getCode());
        Org org1 = orgService.insertSelective(localOrg);
        return org1;
    }

}
