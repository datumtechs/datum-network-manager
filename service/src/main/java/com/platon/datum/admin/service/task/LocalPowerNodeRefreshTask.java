package com.platon.datum.admin.service.task;

import com.platon.datum.admin.dao.LocalPowerJoinTaskMapper;
import com.platon.datum.admin.dao.LocalPowerNodeMapper;
import com.platon.datum.admin.dao.entity.LocalPowerJoinTask;
import com.platon.datum.admin.dao.entity.LocalPowerNode;
import com.platon.datum.admin.grpc.client.PowerClient;
import com.platon.datum.admin.grpc.common.constant.CarrierEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
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
    public void task() {
        log.debug("刷新本地算力节点的基本信息、状态、拥有的资源、被使用的资源，以及正在执行的任务定时任务开始>>>");

        // 定时刷新本地算力节点的基本信息，IP、端口，以及和调度服务的连接情况
        this.refreshLocalPowerNodeBasicInfo();

        // 定时刷新本地算力节点的状态、拥有的资源、被使用的资源，以及正在执行的任务
        this.refreshLocalPowerNodeStatusAndResourceAndJoinTask();

        log.debug("刷新本地算力节点的基本信息、状态、拥有的资源、被使用的资源，以及正在执行的任务定时任务结束|||");
    }

    // 定时刷新本地算力节点的基本信息，IP、端口，以及和调度服务的连接情况
    private void refreshLocalPowerNodeBasicInfo() {
        List<LocalPowerNode> localPowerNodeList = powerClient.getLocalPowerNodeList();
        if (CollectionUtils.isEmpty(localPowerNodeList)) {
            return;
        }
        log.debug("本次更新算力节点数量：{}", localPowerNodeList.size());
        localPowerNodeMapper.initNewPowerNodeBatch(localPowerNodeList);
    }


    // 定时刷新本地算力节点的状态、拥有的资源、被使用的资源，以及正在执行的任务
    private void refreshLocalPowerNodeStatusAndResourceAndJoinTask() {
        Pair<List<LocalPowerNode>, List<LocalPowerJoinTask>> data = powerClient.getLocalPowerNodeListAndJoinTaskList();
        if (Objects.isNull(data)) {
            return;
        }
        // 修改计算节点信息
        List<LocalPowerNode> localPowerNodeList = data.getLeft();

        Map<String, LocalPowerNode> nodeIdMap = localPowerNodeList.stream().collect(Collectors.toMap(LocalPowerNode::getNodeId, power -> power));

        if (CollectionUtils.isNotEmpty(localPowerNodeList)) {
            //1.更新非撤销中的算力状态
            localPowerNodeMapper.updateResourceInfoBatchByNodeId(localPowerNodeList);

            //2.更新撤销中的数据
            List<LocalPowerNode> revokingPower = localPowerNodeMapper.queryPowerNodeListByStatus(6);
            //撤销中的数据如果接口一直有返回，则表示还未处理完成
            revokingPower.stream().filter(power -> !nodeIdMap.keySet().contains(power.getNodeId())).forEach(power -> {
                // 停用算力需把上次启动的算力id清空
                power.setPowerId("");
                power.setPowerStatus(CarrierEnum.PowerState.PowerState_Revoked_VALUE);
                localPowerNodeMapper.updatePowerNodeByNodeId(power);
            });

            //3.处理存在太久的进行中的数据
            processPublishingPowerAndRevokingPower(nodeIdMap);
        }


        // 新增计算节点参与的任务列表
        // 先清空表数据，如有任务再添加
        List<LocalPowerJoinTask> localPowerJoinTaskList = data.getRight();

        log.debug("节点正在执行的任务数量:{}", localPowerJoinTaskList.size());
        if (CollectionUtils.isNotEmpty(localPowerJoinTaskList)) {
            // 每次新增最新的数据
            localPowerJoinTaskMapper.insertBatch(localPowerJoinTaskList);
        }
    }

    //判断是否存在有存在太久的进行中的数据，将它作为操作失败处理 TODO 暂定10分钟的
    private void processPublishingPowerAndRevokingPower(Map<String, LocalPowerNode> powerNodeMap) {
        //发布中的数据
        List<LocalPowerNode> publishingPower = localPowerNodeMapper.queryPowerNodeListByStatus(5);
        //撤销中的数据
        List<LocalPowerNode> revokingPower = localPowerNodeMapper.queryPowerNodeListByStatus(6);

        //expire time 单位分钟
        long expireTime = 10;
        publishingPower.forEach(power -> {
            //expireTime分钟前的数据，判定为发布失败，将powerId回滚至之前的powerId（即”“）,并将算力状态改为撤销状态
            if (power.getUpdateTime().plusMinutes(expireTime).compareTo(LocalDateTime.now()) < 0) {
                power.setPowerId("");
                power.setPowerStatus(CarrierEnum.PowerState.PowerState_Revoked_VALUE);
                localPowerNodeMapper.updatePowerNodeByNodeId(power);
            }
        });

        revokingPower.forEach(power -> {
            //expireTime分钟前的数据，判定为撤销失败，将powerId回滚至之前的powerId,并将算力状态更新
            if (power.getUpdateTime().plusMinutes(expireTime).compareTo(LocalDateTime.now()) < 0) {
                LocalPowerNode localPowerNode = powerNodeMap.get(power.getNodeId());
                power.setPowerId(localPowerNode.getPowerId());
                power.setPowerStatus(localPowerNode.getPowerStatus());
                localPowerNodeMapper.updatePowerNodeByNodeId(power);
            }
        });
    }
}
