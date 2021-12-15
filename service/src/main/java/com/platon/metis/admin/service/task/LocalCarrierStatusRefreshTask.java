package com.platon.metis.admin.service.task;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.health.HealthServicesRequest;
import com.ecwid.consul.v1.health.model.HealthService;
import com.platon.metis.admin.common.context.LocalOrgCache;
import com.platon.metis.admin.common.context.LocalOrgIdentityCache;
import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.dao.enums.CarrierConnStatusEnum;
import com.platon.metis.admin.dao.enums.LocalOrgStatusEnum;
import com.platon.metis.admin.grpc.client.YarnClient;
import com.platon.metis.admin.grpc.entity.YarnGetNodeInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * 定时刷新本组织的调度服务连接状态，调度服务状态和入网状态
 */

@Slf4j
@Configuration
public class LocalCarrierStatusRefreshTask {

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

    //private final ConsulClient consulClient = new ConsulClient(consulHost, consulPort);


    @Transactional
    @Scheduled(fixedDelayString = "${LocalCarrierStatusRefreshTask.fixedDelay}")
    public void task(){
        log.debug("刷新本组织调度服务状态定时任务开始>>>");
        ConsulClient consulClient = new ConsulClient(consulHost, consulPort);
        LocalOrg localOrg = localOrgMapper.select();
        if(localOrg == null){
            log.warn("请先申请身份标识");
            //刷新缓存
            LocalOrgCache.setLocalOrgInfo(null);
            LocalOrgIdentityCache.setIdentityId(null);
            return;
        }

        // 从 consul 查询注册的carrier服务
        HealthServicesRequest request = HealthServicesRequest.newBuilder()
                .setPassing(true)
                .setTag("carrier")
                .setQueryParams(QueryParams.DEFAULT)
                .build();

        List<HealthService> serviceList =  consulClient.getHealthServices(carrierServiceName, request).getValue();
        if(CollectionUtils.isEmpty(serviceList)){
            log.warn("没有查询到调度服务信息");
            return;
        }
        //记录查询到的carrier地址
        localOrg.setCarrierIp(serviceList.get(0).getService().getAddress());
        localOrg.setCarrierPort(serviceList.get(0).getService().getPort());

        //刷新缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        LocalOrgIdentityCache.setIdentityId(localOrg.getIdentityId());

        if( StringUtils.isEmpty(localOrg.getCarrierIp()) || localOrg.getCarrierPort()==null){
            log.warn("查询到的调度服务信息错误");
            localOrg.setCarrierConnStatus(CarrierConnStatusEnum.DISABLED.getStatus());
            localOrg.setStatus(LocalOrgStatusEnum.LEAVE.getStatus());
            localOrg.setConnNodeCount(0);
        }else{
            //### 刷新调度服务状态和入网状态
            YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(localOrg.getCarrierIp(), localOrg.getCarrierPort());
            localOrg.setCarrierStatus(nodeInfo.getState());
            localOrg.setCarrierNodeId(nodeInfo.getNodeId());
            localOrg.setCarrierConnStatus(CarrierConnStatusEnum.ENABLED.getStatus());
            localOrg.setConnNodeCount(nodeInfo.getConnCount());
            localOrg.setLocalBootstrapNode(nodeInfo.getLocalBootstrapNode());
            localOrg.setLocalMultiAddr(nodeInfo.getLocalMultiAddr());

            if(localOrg.getIdentityId().equals(nodeInfo.getIdentityId())){//相同表示入网了
                localOrg.setStatus(LocalOrgStatusEnum.JOIN.getStatus());
            } else {
                localOrg.setStatus(LocalOrgStatusEnum.LEAVE.getStatus());
            }
        }
        localOrgMapper.update(localOrg);
        //刷新缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        LocalOrgIdentityCache.setIdentityId(localOrg.getIdentityId());
        log.debug("刷新本组织调度服务状态定时任务结束|||");
    }

}
