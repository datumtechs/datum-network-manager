package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.DataNodeMapper;
import com.platon.metis.admin.dao.entity.DataNode;
import com.platon.metis.admin.grpc.client.YarnClient;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.entity.QueryNodeResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
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
@Configuration
public class DataNodeRefreshTask {
    @Resource
    private DataNodeMapper dataNodeMapper;
    @Resource
    private YarnClient yarnClient;

    //@Scheduled(fixedDelay = 2000)
    @Scheduled(fixedDelayString = "${DataNodeRefreshTask.fixedDelay}")
    public void task() {
        log.debug("定时刷新数据节点的和调度服务的连接状态...");

        QueryNodeResp resp = yarnClient.getDataNodeList();
        if (GrpcConstant.GRPC_SUCCESS_CODE != resp.getStatus()) {
            log.error("调度服务调用失败");
            return;
        }
        List<DataNode> dataNodeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(resp.getNodeRespList())) {
            resp.getNodeRespList().forEach(item -> {
                DataNode dataNode = new DataNode();
                BeanUtils.copyProperties(item, dataNode);
                dataNodeList.add(dataNode);
            });
            //批量更新
            dataNodeMapper.batchUpdate(dataNodeList);
        }

        log.debug("定时刷新数据节点的和调度服务的连接状态结束...");
    }
}
