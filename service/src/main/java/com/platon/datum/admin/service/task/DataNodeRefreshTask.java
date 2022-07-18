package com.platon.datum.admin.service.task;

import com.platon.datum.admin.dao.DataNodeMapper;
import com.platon.datum.admin.dao.entity.DataNode;
import com.platon.datum.admin.grpc.client.YarnClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据节点定时任务
 * 定时从调度服务获取存储节点，以及存储节点和调度服务的连接状态
 *
 * 注意：存储节点启动时，向注册中心注册；调度服务从注册中心定时获取注册的存储节点，并视图连接这些存储节点。
 */
@Slf4j
@Configuration
public class DataNodeRefreshTask {
    @Resource
    private DataNodeMapper dataNodeMapper;
    @Resource
    private YarnClient yarnClient;

    @Transactional
    @Scheduled(fixedDelayString = "${DataNodeRefreshTask.fixedDelay}")
    public void task() {
        log.debug("刷新数据节点列表定时任务开始>>>");

        List<DataNode> dataNodeList = yarnClient.getLocalDataNodeList();
        if(CollectionUtils.isNotEmpty(dataNodeList)){
            log.debug("本次更新数据节点状态数量：{}", dataNodeList.size());
            dataNodeMapper.initNewDataNodeBatch(dataNodeList);
        }

        log.debug("刷新数据节点列表定时任务结束|||");
    }
}
