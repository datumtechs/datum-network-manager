package com.platon.rosettanet.admin.service.task;

import com.platon.rosettanet.admin.dao.LocalOrgMapper;
import com.platon.rosettanet.admin.dao.LocalPowerHistoryMapper;
import com.platon.rosettanet.admin.dao.LocalPowerJoinTaskMapper;
import com.platon.rosettanet.admin.dao.LocalPowerNodeMapper;
import com.platon.rosettanet.admin.dao.entity.LocalPowerHistory;
import com.platon.rosettanet.admin.dao.entity.LocalPowerJoinTask;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.grpc.client.PowerClient;
import com.platon.rosettanet.admin.grpc.service.CommonMessage;
import com.platon.rosettanet.admin.grpc.service.PowerRpcMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author houz
 * @Description 计算节点定时任务
 * @date 2021/7/10 17:07
 */
@Slf4j
//@Component
public class PowerNodeRefreshTask {

    @Resource
    private LocalPowerNodeMapper localPowerNodeMapper;

    @Resource
    private LocalPowerHistoryMapper localPowerHistoryMapper;

    @Resource
    private LocalPowerJoinTaskMapper localPowerJoinTaskMapper;

    @Resource
    private PowerClient powerClient;
    @Resource
    private LocalOrgMapper localOrgMapper;

    /**
     *
     * 执行完毕上次任务后，间隔60秒执行下一次任务
     */
    @Scheduled(fixedDelay = 60000)
    public void powerHistory(){
        // 定时任务开始
        long startTime = System.currentTimeMillis();
        PowerRpcMessage.GetPowerSingleDetailListResponse getSingleDetailListResponse = powerClient.getPowerSingleDetailList();
        if (Objects.isNull(getSingleDetailListResponse)) {
            log.info("获取计算节点详情失败,无返回数据");
            return;
        }
        log.info("获取当前组织所有节点各个算力的详情, 返回数据:{}", getSingleDetailListResponse.toString());
        List<PowerRpcMessage.GetPowerSingleDetailResponse> detailsList = getSingleDetailListResponse.getPowerListList();
        if (detailsList.isEmpty()) {
            log.info("获取计算节点详情失败,无返回数据");
            return;
        }
        // 节点资源历史记录
        List<LocalPowerHistory> localPowerHistoryList = new ArrayList<>();
        // 计算节点
        List<LocalPowerNode> localPowerNodeList = new ArrayList<>();
        // 计算节点参数参与的任务
        List<LocalPowerJoinTask> localPowerJoinTaskList = new ArrayList();
        for(PowerRpcMessage.GetPowerSingleDetailResponse powerSingleDetail : detailsList) {
            // 单个算力详情
            PowerRpcMessage.PowerSingleDetail detail = powerSingleDetail.getPower();
            // 算力实况
            CommonMessage.ResourceUsedDetailShow resourceUsedDetailShow = detail.getInformation();
            // 参与的任务
            List<PowerRpcMessage.PowerTask> powerTaskList = detail.getTasksList();
            // 保存计算节点历史数据信息
            // 判断当前时间是否是整点
            if(System.currentTimeMillis()%3600000<60000){
                LocalPowerHistory localPowerHistory = new LocalPowerHistory();
                // 计算节点id
                localPowerHistory.setPowerNodeId(detail.getJobNodeId());
                // 已使用内存
                localPowerHistory.setUsedMemory(resourceUsedDetailShow.getUsedMem());
                // 已使用核数
                localPowerHistory.setUsedCore(Integer.parseInt(String.valueOf(resourceUsedDetailShow.getUsedProcessor())));
                // 已使用带宽
                localPowerHistory.setUsedBandwidth(resourceUsedDetailShow.getTotalBandwidth());
                // 当天0点
                if (LocalDateTime.now().getHour() == 0) {
                    // 刷新时间标志 1表示天
                    localPowerHistory.setRefreshStatus("1");
                    localPowerHistoryList.add(localPowerHistory);
                }
                // 刷新时间标志 0表示小时
                localPowerHistory.setRefreshStatus("0");
                localPowerHistoryList.add(localPowerHistory);
            }
            // 保存计算节点算力信息开始
            LocalPowerNode localPowerNode = new LocalPowerNode();
            localPowerNode.setPowerNodeId(detail.getJobNodeId());
            localPowerNode.setMemory(resourceUsedDetailShow.getTotalMem());
            localPowerNode.setCore(Integer.parseInt(String.valueOf(resourceUsedDetailShow.getTotalProcessor())));
            localPowerNode.setBandwidth(resourceUsedDetailShow.getTotalBandwidth());
            localPowerNode.setUsedMemory(resourceUsedDetailShow.getUsedMem());
            localPowerNode.setUsedCore(Integer.parseInt(String.valueOf(resourceUsedDetailShow.getUsedProcessor())));
            localPowerNode.setUsedBandwidth(resourceUsedDetailShow.getUsedBandwidth());
            localPowerNodeList.add(localPowerNode);

            // 保存计算节点参与的任务列表
            if (!powerTaskList.isEmpty()) {
                for(PowerRpcMessage.PowerTask powerTask : powerTaskList) {
                    LocalPowerJoinTask localPowerJoinTask = new LocalPowerJoinTask();
                    localPowerJoinTask.setPowerNodeId(detail.getJobNodeId());
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
                }
            }
        }
        /** 保存计算节点资源历史记录 */
        if (!localPowerHistoryList.isEmpty()) {
            localPowerHistoryMapper.batchInsertPowerHistory(localPowerHistoryList);
        }
        /** 保存计算节点信息 */
        localPowerNodeMapper.batchUpdatePowerNode(localPowerNodeList);

        /** 保存计算节点参与的任务列表 */
        localPowerJoinTaskMapper.batchInsertPowerTask(localPowerJoinTaskList);

        // 定时任务结束
        long diffStart = System.currentTimeMillis() - startTime;
        log.info("定时任务结束, 执行时间:{}", diffStart+"ms");
    }

}
