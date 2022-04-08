package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.common.exception.OrgInfoExists;
import com.platon.metis.admin.common.exception.SysException;
import com.platon.metis.admin.common.util.IDUtil;
import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.SysUserMapper;
import com.platon.metis.admin.dao.cache.LocalOrgCache;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.dao.entity.SysUser;
import com.platon.metis.admin.dao.enums.CarrierConnStatusEnum;
import com.platon.metis.admin.grpc.client.YarnClient;
import com.platon.metis.admin.grpc.entity.YarnGetNodeInfoResp;
import com.platon.metis.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;

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
    private LocalOrgMapper localOrgMapper;

    @Resource
    private YarnClient yarnClient;

    @Value("${spring.cloud.consul.host}")
    private String consulHost;

    @Value("${spring.cloud.consul.port}")
    private Integer consulPort;

    @Value("${spring.cloud.consul.carrierServiceName}")
    private String carrierServiceName;

    @Transactional
    @Override
    public String applyOrgIdentity(String orgName) {
        //### 1.校验是否已存在组织信息
        LocalOrg org = localOrgMapper.select();
        if (org != null) {
            throw new OrgInfoExists();
        }

        //1.1从注册中心获取调度服务的信息
        LocalOrg localOrg = getCarrierInfo();
        //### 1.2 调用调度服务接口生成见证人钱包
        String walletAddress = yarnClient.generateObServerProxyWalletAddress(localOrg.getCarrierIp(), localOrg.getCarrierPort());
        localOrg.setCarrierWallet(walletAddress);
        //### 2.新建local org并入库
        String orgId = IDUtil.generate(IDUtil.IDENTITY_ID_PREFIX);
        localOrg.setIdentityId(orgId);
        localOrg.setName(orgName);
        localOrg.setStatus(LocalOrg.Status.NOT_CONNECT_NET.getCode());
        localOrgMapper.insert(localOrg);

        //### 2.新建成功后，设置缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        return orgId;
    }

    //获取调度服务信息
    private LocalOrg getCarrierInfo() {
        LocalOrg localOrg = new LocalOrg();
        ConsulClient consulClient = new ConsulClient(consulHost, consulPort);
        // 从 consul 查询注册的carrier服务
        HealthServicesRequest request = HealthServicesRequest.newBuilder()
                .setPassing(true)
                .setTag("carrier")
                .setQueryParams(QueryParams.DEFAULT)
                .build();

        List<HealthService> serviceList = consulClient.getHealthServices(carrierServiceName, request).getValue();
        if (CollectionUtils.isEmpty(serviceList)) {
            throw new SysException("No dispatching service information was queried");
        }
        String ip = serviceList.get(0).getService().getAddress();
        Integer port = serviceList.get(0).getService().getPort();
        if (StringUtils.isEmpty(ip) || port == null) {
            throw new SysException("No dispatching service information was queried");
        }
        //记录查询到的carrier地址
        localOrg.setCarrierIp(ip);
        localOrg.setCarrierPort(port);
        //### 刷新调度服务状态和入网状态
        YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(localOrg.getCarrierIp(), localOrg.getCarrierPort());
        localOrg.setCarrierStatus(nodeInfo.getState());
        localOrg.setCarrierNodeId(nodeInfo.getNodeId());
        localOrg.setCarrierConnStatus(CarrierConnStatusEnum.ENABLED.getStatus());
        localOrg.setCarrierConnTime(new Date());
        localOrg.setConnNodeCount(nodeInfo.getConnCount());
        localOrg.setLocalBootstrapNode(nodeInfo.getLocalBootstrapNode());
        localOrg.setLocalMultiAddr(nodeInfo.getLocalMultiAddr());
        return localOrg;
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
            throw new SysException("Insert new user failed!");
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

}
