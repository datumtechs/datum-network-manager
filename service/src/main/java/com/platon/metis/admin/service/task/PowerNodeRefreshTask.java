package com.platon.metis.admin.service.task;

import cn.hutool.core.date.DateUtil;
import com.platon.metis.admin.dao.LocalPowerHistoryMapper;
import com.platon.metis.admin.dao.LocalPowerJoinTaskMapper;
import com.platon.metis.admin.dao.LocalPowerNodeMapper;
import com.platon.metis.admin.dao.entity.LocalPowerHistory;
import com.platon.metis.admin.dao.entity.LocalPowerJoinTask;
import com.platon.metis.admin.dao.entity.LocalPowerNode;
import com.platon.metis.admin.grpc.client.PowerClient;
import com.platon.metis.admin.grpc.common.CommonBase;
import com.platon.metis.admin.grpc.common.CommonData;
import com.platon.metis.admin.grpc.service.PowerRpcMessage;
import com.platon.metis.admin.grpc.service.YarnRpcMessage;
import com.platon.metis.admin.grpc.types.Resourcedata;
import com.platon.metis.admin.service.constant.ServiceConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author houz
 * @Description 计算节点定时任务
 * @date 2021/7/10 17:07
 */
@Slf4j
@Configuration
public class PowerNodeRefreshTask {

    @Resource
    private LocalPowerNodeMapper localPowerNodeMapper;

    @Resource
    private LocalPowerHistoryMapper localPowerHistoryMapper;

    @Resource
    private LocalPowerJoinTaskMapper localPowerJoinTaskMapper;

    @Resource
    private PowerClient powerClient;

    /**
     * 1.定时刷新算力节点的和调度服务的连接状态
     * 2 定时查询算力节点参与的正在执行的任务
     * 间隔60秒执行下一次任务
     */
    //@Scheduled(fixedDelay = 60000)
    @Scheduled(fixedDelayString = "${PowerNodeRefreshTask.fixedDelay}")
    public void refreshPowerData(){
        long startTime = System.currentTimeMillis();

        // 定时刷新节点列表数据
        this.refreshPowerNodeList();

        // 定时刷新节点及节点任务数据
        this.refreshPowerNodeAndTask();

        long diffStart = System.currentTimeMillis() - startTime;
        log.info("refreshPowerData--定时任务执行结束, 执行时间:{}", diffStart+"ms");
    }
    /** 定时刷新算力节点的和调度服务的连接状态 */
    private void refreshPowerNodeList(){
        YarnRpcMessage.GetRegisteredNodeListResponse powerNodeResponse = powerClient.getJobNodeList();
        if(null != powerNodeResponse && !powerNodeResponse.getNodesList().isEmpty()) {
            List localPowerNodeList = new ArrayList();
            powerNodeResponse.getNodesList().parallelStream().forEach(powerNode -> {
                LocalPowerNode localPowerNode = new LocalPowerNode();
                YarnRpcMessage.YarnRegisteredPeerDetail  powerDetails = powerNode.getNodeDetail();
                if (null != powerDetails) {
                    localPowerNode.setPowerNodeId(powerDetails.getId());
                    localPowerNode.setConnStatus(powerDetails.getConnState().getNumber());
                    localPowerNodeList.add(localPowerNode);
                }
            });
            // 修改计算节点信息
            if(!localPowerNodeList.isEmpty()) {
                localPowerNodeMapper.batchUpdatePowerNode(localPowerNodeList);
            }
        }
    }


    /** 定时查询算力节点参与的正在执行的任务 */
    private void refreshPowerNodeAndTask(){
        long startTime = System.currentTimeMillis();
        PowerRpcMessage.GetLocalPowerDetailListResponse getSingleDetailListResponse = powerClient.getLocalPowerDetailList();
        if (Objects.isNull(getSingleDetailListResponse)) {
            log.info("获取计算节点详情失败,无返回数据");
            return;
        }
        List<PowerRpcMessage.GetLocalPowerDetailResponse> detailsList = getSingleDetailListResponse.getPowerListList();
        if (detailsList == null || detailsList.size() == 0) {
            log.info("获取计算节点详情失败,无返回数据");
            return;
        }
        // 节点资源历史记录
        List<LocalPowerHistory> localPowerHistoryList = new ArrayList<>();
        // 计算节点
        List<LocalPowerNode> localPowerNodeList = new ArrayList<>();
        // 计算节点参数参与的任务
        List<LocalPowerJoinTask> localPowerJoinTaskList = new ArrayList<>();
        for(PowerRpcMessage.GetLocalPowerDetailResponse localPowerDetailResponse : detailsList) {

            // 保存计算节点历史数据信息, 判断当前时间是否是整点
            this.savePowerHistory(localPowerDetailResponse, localPowerHistoryList);

            // 保存当前节点算力信息
            this.saveLocalPowerNode(localPowerDetailResponse, localPowerNodeList);

            // 保存计算节点参与的任务列表
             this.savePowerTaskList(localPowerDetailResponse, localPowerJoinTaskList);
        }
        // 新增计算节点资源历史记录
        if (!localPowerHistoryList.isEmpty()) {
            localPowerHistoryMapper.batchInsertPowerHistory(localPowerHistoryList);
        }
        // 修改计算节点信息
        if(!localPowerNodeList.isEmpty()) {
            localPowerNodeMapper.batchUpdatePowerNode(localPowerNodeList);
        }
        // 新增计算节点参与的任务列表
        // 先清空表数据，如有任务再添加
        localPowerJoinTaskMapper.truncateTable();
        if (!localPowerJoinTaskList.isEmpty()) {
            // 每次新增最新的数据
            localPowerJoinTaskMapper.batchInsertPowerTask(localPowerJoinTaskList);
        }
        long diffStart = System.currentTimeMillis() - startTime;
        log.info("refreshPowerNodeAndTask--定时刷新节点及节点任务数据结束, 执行时间:{}", diffStart+"ms");
    }


    /** 判断当前时间是否是整点, 是则保存计算节点历史数据信息，否不保存数据 */
    private void savePowerHistory(PowerRpcMessage.GetLocalPowerDetailResponse powerSingleDetail, List<LocalPowerHistory> localPowerHistoryList){
        // 算力实况
        Resourcedata.PowerUsageDetail detail = powerSingleDetail.getPower();
        if(detail.getCurrentTaskCount()==0){
            //没有当前任务，所以资源情况也不用记录
            return;
        }
        Resourcedata.ResourceUsageOverview resourceUsedDetailShow = detail.getInformation();
        if(System.currentTimeMillis() % ServiceConstant.int_3600000 < ServiceConstant.int_60000){
            LocalPowerHistory localPowerHistory = new LocalPowerHistory();
            // 计算节点id
            localPowerHistory.setPowerNodeId(powerSingleDetail.getJobNodeId());
            // 已使用内存
            localPowerHistory.setUsedMemory(resourceUsedDetailShow.getUsedMem());
            // 已使用核数
            localPowerHistory.setUsedCore(Integer.parseInt(String.valueOf(resourceUsedDetailShow.getUsedProcessor())));
            // 已使用带宽
            localPowerHistory.setUsedBandwidth(resourceUsedDetailShow.getUsedBandwidth());
            // 当天0点
            if (LocalDateTime.now().getHour() == 0) {
                // 刷新时间标志 1表示天
                int year = LocalDateTime.now().getYear();
                int month = LocalDateTime.now().getMonthValue();
                int day = LocalDateTime.now().getDayOfMonth() - 1;
                StringBuilder sb = new StringBuilder();
                String yearMonthDay =  sb.append(year).append((month < 10 ? "0"+ month : month)).append((day < 10 ? "0" + day : day)).toString();
                Map dayMap = localPowerHistoryMapper.queryPowerHistoryDay(localPowerHistory.getPowerNodeId(), "0", yearMonthDay);
                if(dayMap != null && dayMap.size() > 0) {
                    localPowerHistory.setUsedCore(Integer.valueOf(dayMap.get("usedCore").toString()));
                    localPowerHistory.setUsedMemory(Long.valueOf(dayMap.get("usedMemory").toString()));
                    localPowerHistory.setUsedBandwidth(Long.valueOf(dayMap.get("usedBandwidth").toString()));

                }
                localPowerHistory.setRefreshStatus("1");
                localPowerHistoryList.add(localPowerHistory);
            }
            // 刷新时间标志 0表示小时
            localPowerHistory.setRefreshStatus("0");
            localPowerHistoryList.add(localPowerHistory);
        }
    }

    /** 保存当前节点算力信息 */
    private void saveLocalPowerNode(PowerRpcMessage.GetLocalPowerDetailResponse powerSingleDetail, List<LocalPowerNode> localPowerNodeList ){
        // 算力实况
        Resourcedata.PowerUsageDetail detail = powerSingleDetail.getPower();

        Resourcedata.ResourceUsageOverview resourceUsedDetailShow = detail.getInformation();
        // 保存计算节点算力信息开始
        LocalPowerNode localPowerNode = new LocalPowerNode();
        localPowerNode.setPowerNodeId(powerSingleDetail.getJobNodeId());
        localPowerNode.setPowerId(powerSingleDetail.getPowerId());
        localPowerNode.setPowerStatus(detail.getState().getNumber());
        localPowerNode.setMemory(resourceUsedDetailShow.getTotalMem());
        localPowerNode.setCore(resourceUsedDetailShow.getTotalProcessor());
        localPowerNode.setBandwidth(resourceUsedDetailShow.getTotalBandwidth());
        localPowerNode.setUsedMemory(resourceUsedDetailShow.getUsedMem());
        localPowerNode.setUsedCore(resourceUsedDetailShow.getUsedProcessor());
        localPowerNode.setUsedBandwidth(resourceUsedDetailShow.getUsedBandwidth());

        //更新算力状态
        localPowerNode.setPowerStatus(detail.getState().getNumber());

        localPowerNodeList.add(localPowerNode);
    }

    /** 保存节点参与的任务列表 */
    private void savePowerTaskList(PowerRpcMessage.GetLocalPowerDetailResponse powerSingleDetail, List<LocalPowerJoinTask> localPowerJoinTaskList){
        // 参与的任务
        Resourcedata.PowerUsageDetail detail = powerSingleDetail.getPower();
        List<Resourcedata.PowerTask> powerTaskList = detail.getTasksList();
        if (powerTaskList != null && powerTaskList.size() > 0) {
            for(Resourcedata.PowerTask powerTask : powerTaskList) {
                CommonData.TaskResourceCostDeclare taskOperationCostDeclare = powerTask.getOperationCost();
                LocalPowerJoinTask localPowerJoinTask = new LocalPowerJoinTask();
                // 节点id
                localPowerJoinTask.setPowerNodeId(powerSingleDetail.getJobNodeId());
                // 任务id
                localPowerJoinTask.setTaskId(powerTask.getTaskId());
                // 任务名称
                localPowerJoinTask.setTaskName(powerTask.getTaskName());
                // 发起时间
                localPowerJoinTask.setTaskStartTime(DateUtil.date(powerTask.getCreateAt()));
                // 已使用内存
                localPowerJoinTask.setUsedMemory(taskOperationCostDeclare.getMemory());
                // 已使用核数
                localPowerJoinTask.setUsedCore(taskOperationCostDeclare.getProcessor());
                // 已使用带宽
                localPowerJoinTask.setUsedBandwidth(taskOperationCostDeclare.getBandwidth());
                // 保存发起方、结果方、协作方
                localPowerJoinTask = this.saveOwnerAndReceiversAndCoordinate(powerTask, localPowerJoinTask);
                localPowerJoinTaskList.add(localPowerJoinTask);
            }
        }
    }

    /** 保存发起方、结果方、协作方 */
    private LocalPowerJoinTask saveOwnerAndReceiversAndCoordinate(Resourcedata.PowerTask powerTask, LocalPowerJoinTask localPowerJoinTask){
        // 任务发起方身份信息
        CommonBase.Organization ownerIdentityInfo = powerTask.getOwner();
        // 任务结果方
        List<CommonBase.Organization> receiversIdentityInfoList = powerTask.getReceiversList();
        // 任务协作方
        List<CommonBase.Organization> patnersIdentityInfoList = powerTask.getPartnersList();
        // 发起方
        localPowerJoinTask.setOwnerIdentityId(ownerIdentityInfo.getIdentityId());
        localPowerJoinTask.setOwnerIdentityName(ownerIdentityInfo.getNodeName());
        // 结果方
        if (receiversIdentityInfoList != null && receiversIdentityInfoList.size() > 0) {
            List idList = new ArrayList();
            List nameList = new ArrayList();
            for(CommonBase.Organization organizationIdentityInfo : receiversIdentityInfoList) {
                idList.add(organizationIdentityInfo.getIdentityId());
                nameList.add(organizationIdentityInfo.getNodeName());
            }
            localPowerJoinTask.setResultSideId(StringUtils.collectionToDelimitedString(idList, ","));
            localPowerJoinTask.setResultSideName(StringUtils.collectionToDelimitedString(nameList,","));
        }
        // 协作方
        if (patnersIdentityInfoList != null && patnersIdentityInfoList.size() > 0) {
            List idList = new ArrayList();
            List nameList = new ArrayList();
            for(CommonBase.Organization organizationIdentityInfo : patnersIdentityInfoList) {
                idList.add(organizationIdentityInfo.getIdentityId());
                nameList.add(organizationIdentityInfo.getNodeName());
            }
            localPowerJoinTask.setCoordinateSideId(StringUtils.collectionToDelimitedString(idList,","));
            localPowerJoinTask.setCoordinateSideName(StringUtils.collectionToDelimitedString(nameList,","));
        }
        return localPowerJoinTask;
    }
}
