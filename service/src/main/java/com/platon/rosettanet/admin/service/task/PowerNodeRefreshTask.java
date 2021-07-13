package com.platon.rosettanet.admin.service.task;

import com.platon.rosettanet.admin.dao.LocalOrgMapper;
import com.platon.rosettanet.admin.dao.LocalPowerDetailsMapper;
import com.platon.rosettanet.admin.dao.LocalPowerJoinTaskMapper;
import com.platon.rosettanet.admin.dao.entity.LocalPowerDetails;
import com.platon.rosettanet.admin.dao.entity.LocalPowerJoinTask;
import com.platon.rosettanet.admin.grpc.client.PowerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author houz
 * @Description 计算节点定时任务
 * @date 2021/7/10 17:07
 */
@Slf4j
@Component
public class PowerNodeRefreshTask {

    @Resource
    private LocalPowerDetailsMapper localPowerDetailsMapper;

    @Resource
    private LocalPowerJoinTaskMapper localPowerJoinTaskMapper;

    @Resource
    private PowerClient powerClient;
    @Resource
    private LocalOrgMapper localOrgMapper;

    /**
     * 每一小时执行一次
     */
    @Scheduled(cron="0 0 0-23 * * ? ")
    public void powerDetails(){
        // 定时任务开始
        long startTime = System.currentTimeMillis();
        String responseStr = powerClient.GetPowerSingleDetailList();
        if (responseStr == null) {
            log.info("获取计算节点详情失败,无返回数据");
            return;
        }
        log.info("获取计算节点详情, 返回数据:{}", responseStr);
        LocalPowerDetails localPowerDetails = new LocalPowerDetails();
        // 计算节点id
        localPowerDetails.setPowerNodeId("");
        // 刷新时间标志 0表示小时
        localPowerDetails.setRefreshStatus("0");
        // 已使用带宽
        localPowerDetails.setUsedBandwidth(0L);
        // 已使用核数
        localPowerDetails.setUsedCore(0);
        // 已使用内存
        localPowerDetails.setUsedMemory(0L);
        if (LocalDateTime.now().getHour() <= 1) {
            // 刷新时间标志 1表示天
            localPowerDetails.setRefreshStatus("1");
            // 按天保存计算节点资源使用情况
            localPowerDetailsMapper.insertPowerDetails(localPowerDetails);
        }
        // 按小时保存计算节点资源使用情况
        localPowerDetailsMapper.insertPowerDetails(localPowerDetails);
        // 定时任务结束
        long diffStart = System.currentTimeMillis() - startTime;
        log.info("执行时间:{}", diffStart);
    }

    /**
     * 执行完毕上次任务后，间隔3秒执行下一次任务
     */
    @Scheduled(fixedDelay = 3000)
    public void powerJoinTask(){
        // 定时任务开始
        long startTime = System.currentTimeMillis();
        // 查看所有组织单个算力详情 (包含 任务描述)
        String responseStr = powerClient.GetPowerSingleDetailList();
        if (responseStr == null) {
            log.info("获取计算节点详情失败,无返回数据");
            return;
        }
        log.info("获取计算节点详情, 返回数据:{}", responseStr);
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
        // 计算节点参与的任务列表
        localPowerJoinTaskMapper.insertPowerJoinTask(localPowerJoinTask);
        // 定时任务结束
        long diffStart = System.currentTimeMillis() - startTime;
        log.info("执行时间:{}", diffStart);
    }
}
