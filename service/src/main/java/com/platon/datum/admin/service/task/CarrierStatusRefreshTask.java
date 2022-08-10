package com.platon.datum.admin.service.task;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.health.HealthServicesRequest;
import com.ecwid.consul.v1.health.model.HealthService;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dao.enums.CarrierConnStatusEnum;
import com.platon.datum.admin.grpc.client.YarnClient;
import com.platon.datum.admin.grpc.entity.YarnGetNodeInfoResp;
import com.platon.datum.admin.service.OrgService;
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
 * 定时刷新本组织的调度服务连接状态，调度服务状态和入网状态
 */

@Slf4j
@Configuration
public class CarrierStatusRefreshTask {

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

    @Transactional(rollbackFor = Throwable.class)
    @Scheduled(fixedDelayString = "${CarrierStatusRefreshTask.fixedDelay}")
    public void task() {
        log.debug("刷新本组织调度服务状态定时任务开始>>>");
        ConsulClient consulClient = new ConsulClient(consulHost, consulPort);
        Org org = orgService.select();
        if (org == null) {
            log.warn("请先申请身份标识");
            //刷新缓存
            OrgCache.setLocalOrgInfo(null);
            return;
        }

        // 从 consul 查询注册的carrier服务
        HealthServicesRequest request = HealthServicesRequest.newBuilder()
                .setPassing(true)
                .setTag("carrier")
                .setQueryParams(QueryParams.DEFAULT)
                .build();

        List<HealthService> serviceList = consulClient.getHealthServices(carrierServiceName, request).getValue();
        if (CollectionUtils.isEmpty(serviceList)) {
            log.warn("没有查询到调度服务信息");
            return;
        }
        //记录查询到的carrier地址
        org.setCarrierIp(serviceList.get(0).getService().getAddress());
        org.setCarrierPort(serviceList.get(0).getService().getPort());

        //刷新缓存
        OrgCache.setLocalOrgInfo(org);

        if (StringUtils.isEmpty(org.getCarrierIp()) || org.getCarrierPort() == null) {
            log.warn("查询到的调度服务信息错误");
            org.setCarrierConnStatus(CarrierConnStatusEnum.DISABLED.getStatus());
            org.setCarrierConnTime(LocalDateTimeUtil.now());
            org.setConnNodeCount(0);
        } else {
            //### 刷新调度服务状态和入网状态
            YarnGetNodeInfoResp nodeInfo = yarnClient.getNodeInfo(org.getCarrierIp(), org.getCarrierPort());
            org.setCarrierStatus(nodeInfo.getState());
            org.setCarrierNodeId(nodeInfo.getNodeId());
            org.setCarrierConnStatus(CarrierConnStatusEnum.ENABLED.getStatus());
            org.setCarrierConnTime(LocalDateTimeUtil.now());
            org.setConnNodeCount(nodeInfo.getConnCount());
            org.setLocalBootstrapNode(nodeInfo.getLocalBootstrapNode());
            org.setLocalMultiAddr(nodeInfo.getLocalMultiAddr());

            /*if(org.getIdentityId().equals(nodeInfo.getIdentityId())){//相同表示入网了
                org.setStatus(LocalOrgStatusEnum.JOIN.getStatus());
            } else {
                org.setStatus(LocalOrgStatusEnum.LEAVE.getStatus());
            }*/
        }
        orgService.updateSelective(org);
        log.debug("刷新本组织调度服务状态定时任务结束|||");
    }

}
