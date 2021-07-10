package com.platon.rosettanet.admin.service.task;

import com.platon.rosettanet.admin.dao.DataNodeMapper;
import com.platon.rosettanet.admin.dao.LocalOrgMapper;
import com.platon.rosettanet.admin.dao.entity.DataNode;
import com.platon.rosettanet.admin.dao.entity.LocalOrg;
import com.platon.rosettanet.admin.grpc.client.YarnClient;
import com.platon.rosettanet.admin.grpc.constant.GrpcConstant;
import com.platon.rosettanet.admin.grpc.entity.QueryNodeResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lyf
 * @Description 数据节点定时任务
 * @date 2021/7/10 17:07
 */
@Slf4j
//@Component 暂时注释
public class DataNodeRefreshTask {
    @Resource
    private DataNodeMapper dataNodeMapper;
    @Resource
    private YarnClient yarnClient;
    @Resource
    private LocalOrgMapper localOrgMapper;
    @Scheduled(fixedDelay=2000)
    public void task(){
        long begin = System.currentTimeMillis();
        LocalOrg carrier = localOrgMapper.selectAvailableCarrier();
        if (carrier == null) {
            log.info("获取数据节点列表,无可用的调度服务");
            return;
        }
        log.info("获取数据节点列表,调度服务ip:" + carrier.getCarrierIP() + ",端口号：" + carrier.getCarrierPort());
        QueryNodeResp resp = yarnClient.getDataNodeList(carrier.getCarrierIP(), carrier.getCarrierPort());
        if (GrpcConstant.GRPC_SUCCESS_CODE != resp.getStatus()) {
            log.info("获取数据节点列表,调度服务调用失败");
            return;
        }
        List<DataNode> dataNodeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dataNodeList)) {
            resp.getNodeRespList().forEach(item -> {
                DataNode dataNode = new DataNode();
                BeanUtils.copyProperties(item, dataNode);
                dataNodeList.add(dataNode);
            });
            dataNodeMapper.batchUpdate(dataNodeList);
        }
        long end = System.currentTimeMillis();
        System.out.println("调度更新数据节点列表任务耗时="+(end-begin));
    }
}
