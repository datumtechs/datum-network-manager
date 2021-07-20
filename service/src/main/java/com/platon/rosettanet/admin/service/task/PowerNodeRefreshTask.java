package com.platon.rosettanet.admin.service.task;

import com.platon.rosettanet.admin.dao.LocalOrgMapper;
import com.platon.rosettanet.admin.dao.LocalPowerHistoryMapper;
import com.platon.rosettanet.admin.dao.LocalPowerJoinTaskMapper;
import com.platon.rosettanet.admin.dao.entity.LocalPowerHistory;
import com.platon.rosettanet.admin.dao.entity.LocalPowerJoinTask;
import com.platon.rosettanet.admin.grpc.client.PowerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author houz
 * @Description 计算节点定时任务
 * @date 2021/7/10 17:07
 */
@Slf4j
//@Component
public class PowerNodeRefreshTask {

    @Resource
    private LocalPowerHistoryMapper localPowerHistoryMapper;

    @Resource
    private LocalPowerJoinTaskMapper localPowerJoinTaskMapper;

    @Resource
    private PowerClient powerClient;
    @Resource
    private LocalOrgMapper localOrgMapper;

    /**
     * 每一小时执行一次
     * 获取当前组织所有节点各个算力的详情
     */
    @Scheduled(cron="0 0 0-23 * * ? ")
    public void powerHistory(){
        // 定时任务开始
        long startTime = System.currentTimeMillis();
        powerClient.getPowerSingleDetailList();
//        if (responseStr == null) {
//            log.info("获取计算节点详情失败,无返回数据");
//            return;
//        }
//        log.info("获取当前组织所有节点各个算力的详情, 返回数据:{}", responseStr);
        List<LocalPowerHistory> localPowerHistoryList = new ArrayList<>();
//        for() {
            LocalPowerHistory localPowerHistory = new LocalPowerHistory();
            // 计算节点id
            localPowerHistory.setPowerNodeId("");
            // 刷新时间标志 0表示小时
            localPowerHistory.setRefreshStatus("0");
            // 已使用带宽
            localPowerHistory.setUsedBandwidth(0L);
            // 已使用核数
            localPowerHistory.setUsedCore(0);
            // 已使用内存
            localPowerHistory.setUsedMemory(0L);
            if (LocalDateTime.now().getHour() <= 1) {
                // 刷新时间标志 1表示天
                localPowerHistory.setRefreshStatus("1");
            }
        localPowerHistoryList.add(localPowerHistory);
//        }
        if (LocalDateTime.now().getHour() <= 1) {
            // 按天保存计算节点资源使用情况
            localPowerHistoryMapper.batchInsertPowerHistory(localPowerHistoryList);
        }
        // 按小时保存计算节点资源使用情况
        localPowerHistoryMapper.batchInsertPowerHistory(localPowerHistoryList);
        // 定时任务结束
        long diffStart = System.currentTimeMillis() - startTime;
        log.info("执行时间:{}", diffStart);
    }

    /**
     * 执行完毕上次任务后，间隔3秒执行下一次任务
     * 获取当前计算节点参与的任务
     */
    @Scheduled(fixedDelay = 3000)
    public void powerJoinTask(){
        // 定时任务开始
        long startTime = System.currentTimeMillis();
        // 查看所有组织单个算力详情 (包含 任务描述)
//        String responseStr = powerClient.getPowerSingleDetailList();
//        if (responseStr == null) {
//            log.info("获取计算节点详情失败,无返回数据");
//            return;
//        }
        List<LocalPowerJoinTask> localPowerJoinTaskList = new ArrayList();
//        log.info("获取计算节点详情, 返回数据:{}", responseStr);
//        for() {
            LocalPowerJoinTask localPowerJoinTask = new LocalPowerJoinTask();
            localPowerJoinTask.setPowerNodeId(null);
            localPowerJoinTask.setTaskId(null);
            localPowerJoinTask.setTaskName(null);
            localPowerJoinTask.setOwnerIdentityId(null);
            localPowerJoinTask.setTaskStartTime(null);
            localPowerJoinTask.setResultSide(null);
            localPowerJoinTask.setCoordinateSide(null);
            // 已使用带宽
            localPowerJoinTask.setUsedBandwidth(null);
            // 已使用核数
            localPowerJoinTask.setUsedCore(null);
            // 已使用内存
            localPowerJoinTask.setUsedMemory(null);

            localPowerJoinTaskList.add(localPowerJoinTask);
//        }
        // 计算节点参与的任务列表
        localPowerJoinTaskMapper.batchInsertPowerTask(localPowerJoinTaskList);
        // 定时任务结束
        long diffStart = System.currentTimeMillis() - startTime;
        log.info("执行时间:{}", diffStart);
    }
}
