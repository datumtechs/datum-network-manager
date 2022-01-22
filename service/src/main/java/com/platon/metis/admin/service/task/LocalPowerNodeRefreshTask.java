package com.platon.metis.admin.service.task;

import com.platon.metis.admin.dao.LocalPowerJoinTaskMapper;
import com.platon.metis.admin.dao.LocalPowerNodeMapper;
import com.platon.metis.admin.dao.entity.LocalPowerJoinTask;
import com.platon.metis.admin.dao.entity.LocalPowerNode;
import com.platon.metis.admin.grpc.client.PowerClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 *
 * @Description 计算节点定时任务
 * 1.定时刷新算力节点的和调度服务的连接状态
 * 2 定时查询算力节点的资源使用情况，以及参与的正在执行的任务
 * 间隔60秒执行下一次任务
 */
@Slf4j
@Configuration
public class LocalPowerNodeRefreshTask {

    @Resource
    private LocalPowerNodeMapper localPowerNodeMapper;

    @Resource
    private LocalPowerJoinTaskMapper localPowerJoinTaskMapper;

    @Resource
    private PowerClient powerClient;

    @Transactional
    @Scheduled(fixedDelayString = "${LocalPowerNodeRefreshTask.fixedDelay}")
    public void task(){
        log.debug("刷新本地算力节点的基本信息、状态、拥有的资源、被使用的资源，以及正在执行的任务定时任务开始>>>");

        // 定时刷新本地算力节点的基本信息，IP、端口，以及和调度服务的连接情况
        this.refreshLocalPowerNodeBasicInfo();

        // 定时刷新本地算力节点的状态、拥有的资源、被使用的资源，以及正在执行的任务
        this.refreshLocalPowerNodeStatusAndResourceAndJoinTask();

        log.debug("刷新本地算力节点的基本信息、状态、拥有的资源、被使用的资源，以及正在执行的任务定时任务结束|||");
    }

    // 定时刷新本地算力节点的基本信息，IP、端口，以及和调度服务的连接情况
    private void refreshLocalPowerNodeBasicInfo(){
        List<LocalPowerNode> localPowerNodeList = powerClient.getLocalPowerNodeList();
        if (CollectionUtils.isEmpty(localPowerNodeList)) {
            return;
        }
        log.debug("本次更新算力节点数量：{}", localPowerNodeList.size());
        localPowerNodeMapper.initNewPowerNodeBatch(localPowerNodeList);
    }


    // 定时刷新本地算力节点的状态、拥有的资源、被使用的资源，以及正在执行的任务
    private void refreshLocalPowerNodeStatusAndResourceAndJoinTask(){
        Pair<List<LocalPowerNode>, List<LocalPowerJoinTask>> data = powerClient.getLocalPowerNodeListAndJoinTaskList();
        if (Objects.isNull(data)) {
            return;
        }
        // 修改计算节点信息
        List<LocalPowerNode> localPowerNodeList = data.getLeft();

        if(CollectionUtils.isNotEmpty(localPowerNodeList)) {
            localPowerNodeMapper.updateResourceInfoBatchByNodeId(localPowerNodeList);
        }
        // 新增计算节点参与的任务列表
        // 先清空表数据，如有任务再添加
        List<LocalPowerJoinTask> localPowerJoinTaskList = data.getRight();

        log.debug("节点正在执行的任务数量:{}", localPowerJoinTaskList.size());
        if(CollectionUtils.isNotEmpty(localPowerJoinTaskList)) {
            // 每次新增最新的数据
            localPowerJoinTaskMapper.insertBatch(localPowerJoinTaskList);
        }
    }
}
