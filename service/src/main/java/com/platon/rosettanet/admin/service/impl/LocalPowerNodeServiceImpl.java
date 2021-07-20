package com.platon.rosettanet.admin.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.LocalPowerHistoryMapper;
import com.platon.rosettanet.admin.dao.LocalPowerJoinTaskMapper;
import com.platon.rosettanet.admin.dao.LocalPowerNodeMapper;
import com.platon.rosettanet.admin.dao.entity.DataNode;
import com.platon.rosettanet.admin.dao.entity.LocalPowerHistory;
import com.platon.rosettanet.admin.dao.entity.LocalPowerJoinTask;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.grpc.client.PowerClient;
import com.platon.rosettanet.admin.grpc.service.PowerRpcMessage;
import com.platon.rosettanet.admin.grpc.service.YarnRpcMessage;
import com.platon.rosettanet.admin.service.LocalPowerNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author houz
 * 计算节点业务实现类
 */
@Service
@Slf4j
public class LocalPowerNodeServiceImpl implements LocalPowerNodeService {


    /** 计算节点 */
    @Resource
    LocalPowerNodeMapper localPowerNodeMapper;

    /** 计算节点资源 */
    @Resource
    LocalPowerHistoryMapper localPowerHistoryMapper;

    /** 计算节点资源 */
    @Resource
    LocalPowerJoinTaskMapper localPowerJoinTaskMapper;

    @Resource
    PowerClient powerClient;

    @Override
    public int insertPowerNode(LocalPowerNode powerNode) {
        // 调用grpc接口修改计算节点信息
        YarnRpcMessage.YarnRegisteredPeerDetail jobNode = powerClient.addPowerNode(powerNode.getInternalIp(), powerNode.getExternalIp(),
                powerNode.getInternalPort(), powerNode.getExternalPort());
        log.info("新增计算节点数据:{}", jobNode);
        // 计算节点id
        powerNode.setIdentityId(LocalOrgIdentityCache.getIdentityId());
        powerNode.setPowerNodeId(jobNode.getId());
        // 状态1表示已连接，未启用
        powerNode.setConnStatus(String.valueOf(jobNode.getConnState()));
        // 内存
        powerNode.setMemory(0L);
        // 核数
        powerNode.setCore(0);
        // 带宽
        powerNode.setBandwidth(0L);
        return localPowerNodeMapper.insertPowerNode(powerNode);
    }

    @Override
    public int updatePowerNodeByNodeId(LocalPowerNode powerNode) {
        // 调用grpc接口修改计算节点信息
        YarnRpcMessage.YarnRegisteredPeerDetail jobNode = powerClient.updatePowerNode(powerNode.getPowerNodeId(), powerNode.getInternalIp(), powerNode.getExternalIp(),
                powerNode.getInternalPort(), powerNode.getExternalPort());
        // 计算节点id
        powerNode.setPowerNodeId(jobNode.getId());
        // 状态
        powerNode.setConnStatus(String.valueOf(jobNode.getConnState()));
        // 内存
        powerNode.setMemory(0L);
        // 核数
        powerNode.setCore(0);
        // 带宽
        powerNode.setBandwidth(0L);
        return localPowerNodeMapper.updatePowerNodeByNodeId(powerNode);
    }

    @Override
    public int deletePowerNodeByNodeId(String powerNodeId) {
        // gRPC接口返回1表示删除成功，否则表示删除失败
        if (1 != powerClient.deletePowerNode(powerNodeId)) {
            return 0;
        }
        return localPowerNodeMapper.deletePowerNode(powerNodeId);

    }

    @Override
    public LocalPowerNode queryPowerNodeDetails(String powerNodeId) {
        List<YarnRpcMessage.YarnRegisteredPeerDetail> list = powerClient.getJobNodeList();
        return localPowerNodeMapper.queryPowerNodeDetails(powerNodeId);
    }

    @Override
    public Page queryPowerNodeList(String identityId, String keyword, int pageNumber, int pageSize) {
        long startTime = System.currentTimeMillis();
        Page<LocalPowerNode> page = PageHelper.startPage(pageNumber, pageSize);
        localPowerNodeMapper.queryPowerNodeList(identityId, keyword);
        long diffTime = System.currentTimeMillis() - startTime;
        log.info("查询计算节点列表, 响应时间:{}, 响应数据:{}", diffTime+"ms", page.toString());
        return page;
    }

    @Override
    public void publishPower(String powerNodeId) {
        String powerId = powerClient.publishPower(powerNodeId);
        LocalPowerNode localPowerNode = new LocalPowerNode();
        localPowerNode.setPowerNodeId(powerNodeId);
        localPowerNode.setPowerId(powerId);
        // status=2表示算例已启用
        localPowerNode.setConnStatus("2");
        localPowerNode.setStartTime(LocalDateTime.now());
        localPowerNodeMapper.updatePowerNodeByNodeId(localPowerNode);
    }

    @Override
    public void revokePower(String powerNodeId) {
        powerClient.revokePower(powerNodeId);
        LocalPowerNode localPowerNode = new LocalPowerNode();
        localPowerNode.setPowerNodeId(powerNodeId);
        // 停用算力需把上次启动的算力id清空
        localPowerNode.setPowerId("");
        // status=1表示算例未启用
        localPowerNode.setConnStatus("1");
        localPowerNodeMapper.updatePowerNodeByNodeId(localPowerNode);
    }

    @Override
    public List<Map> queryPowerNodeUseHistory(String powerNodeId) {
        List<LocalPowerHistory>  powerHistoryList = localPowerHistoryMapper.queryPowerHistory(powerNodeId);
        List<Map> historyList = new ArrayList();
        if (!powerHistoryList.isEmpty()) {
            // 24小时
            historyList = this.hoursMethod(powerHistoryList, historyList);
            // 7天
            historyList = this.sevenMethod(powerHistoryList, historyList);
            // 15天
            historyList = this.fifteenMethod(powerHistoryList, historyList);
            // 30天记录
            historyList = this.thirtyMethod(powerHistoryList, historyList);
        }
        return historyList;
    }

    /** 24小时记录 */
    private List hoursMethod(List<LocalPowerHistory> powerHistoryList, List historyList){
        List cupList = new ArrayList();
        List memoryList = new ArrayList();
        List bandList = new ArrayList();
        List timeList = new ArrayList();
        Map cpuHoursMap = new HashMap(16);
        Map memoryHoursMap = new HashMap(16);
        Map bandHoursMap = new HashMap(16);
        for(LocalPowerHistory powerHistory : powerHistoryList) {
            if ("0".equals(powerHistory.getRefreshStatus())) {
                // 时间(小时)
                timeList.add(DateUtil.format(powerHistory.getCreateTime(), "yyyy-MM-dd HH"));
                // cpu
                cupList.add(powerHistory.getUsedCore());
                // 内存
                memoryList.add(powerHistory.getUsedMemory());
                // 带宽
                bandList.add(powerHistory.getUsedBandwidth());
            }
            if (timeList.size() >= 24) {
                break;
            }
        }
        cpuHoursMap.put("cupList", cupList);
        cpuHoursMap.put("hoursTime", timeList);
        memoryHoursMap.put("memoryList", memoryList);
        memoryHoursMap.put("hoursTime", timeList);
        bandHoursMap.put("bandList", bandList);
        bandHoursMap.put("hoursTime", timeList);
        historyList.add(cpuHoursMap);
        historyList.add(memoryHoursMap);
        historyList.add(bandHoursMap);
        return historyList;
    }

    /** 7天记录 */
    private List sevenMethod(List<LocalPowerHistory> powerHistoryList, List historyList){
        List cupList = new ArrayList();
        List memoryList = new ArrayList();
        List bandList = new ArrayList();
        List timeList = new ArrayList();
        Map cpuSevenMap = new HashMap(16);
        Map memorySevenMap = new HashMap(16);
        Map bandSevenMap = new HashMap(16);
        for(LocalPowerHistory powerHistory : powerHistoryList) {
            if ("1".equals(powerHistory.getRefreshStatus())) {
                // 时间NORM_DATE_FORMATTER
                timeList.add(DateUtil.format(powerHistory.getCreateTime(), DatePattern.NORM_DATE_PATTERN));
                // cpu
                cupList.add(powerHistory.getUsedCore());
                // 内存
                memoryList.add(powerHistory.getUsedMemory());
                // 带宽
                bandList.add(powerHistory.getUsedBandwidth());
            }
            if (timeList.size() >= 7) {
                break;
            }
        }
        cpuSevenMap.put("cupList", cupList);
        cpuSevenMap.put("sevenTime", timeList);
        memorySevenMap.put("memoryList", memoryList);
        memorySevenMap.put("sevenTime", timeList);
        bandSevenMap.put("bandList", bandList);
        bandSevenMap.put("sevenTime", timeList);
        historyList.add(cpuSevenMap);
        historyList.add(memorySevenMap);
        historyList.add(bandSevenMap);
        return historyList;
    }

    /** 15天记录 */
    private List fifteenMethod(List<LocalPowerHistory> powerHistoryList, List historyList){
        List cupList = new ArrayList();
        List memoryList = new ArrayList();
        List bandList = new ArrayList();
        List timeList = new ArrayList();
        Map cpuFifteenMap = new HashMap(16);
        Map memoryFifteenMap = new HashMap(16);
        Map bandFifteenMap = new HashMap(16);
        for(LocalPowerHistory powerHistory : powerHistoryList) {
            if ("1".equals(powerHistory.getRefreshStatus())) {
                // 时间(小时)
                timeList.add(DateUtil.format(powerHistory.getCreateTime(), DatePattern.NORM_DATE_PATTERN));
                // cpu
                cupList.add(powerHistory.getUsedCore());
                // 内存
                memoryList.add(powerHistory.getUsedMemory());
                // 带宽
                bandList.add(powerHistory.getUsedBandwidth());
            }
            if (timeList.size() >= 15) {
                break;
            }
        }
        cpuFifteenMap.put("cupList", cupList);
        cpuFifteenMap.put("fifteenTime", timeList);
        memoryFifteenMap.put("memoryList", memoryList);
        memoryFifteenMap.put("fifteenTime", timeList);
        bandFifteenMap.put("bandList", bandList);
        bandFifteenMap.put("fifteenTime", timeList);
        historyList.add(cpuFifteenMap);
        historyList.add(memoryFifteenMap);
        historyList.add(bandFifteenMap);
        return historyList;
    }
    /** 30天记录 */
    private List thirtyMethod(List<LocalPowerHistory> powerHistoryList, List historyList){
        List cupList = new ArrayList();
        List memoryList = new ArrayList();
        List bandList = new ArrayList();
        List timeList = new ArrayList();
        Map cpuThirtyMap = new HashMap(16);
        Map memoryThirtyMap = new HashMap(16);
        Map bandThirtyMap = new HashMap(16);
        for(LocalPowerHistory powerHistory : powerHistoryList) {
            if ("1".equals(powerHistory.getRefreshStatus())) {
                // 时间(小时)
                timeList.add(DateUtil.format(powerHistory.getCreateTime(), DatePattern.NORM_DATE_PATTERN));
                // cpu
                cupList.add(powerHistory.getUsedCore());
                // 内存
                memoryList.add(powerHistory.getUsedMemory());
                // 带宽
                bandList.add(powerHistory.getUsedBandwidth());
            }
            if (timeList.size() >= 30) {
                break;
            }
        }
        cpuThirtyMap.put("cupList", cupList);
        cpuThirtyMap.put("thirtyTime", timeList);
        memoryThirtyMap.put("memoryList", memoryList);
        memoryThirtyMap.put("thirtyTime", timeList);
        bandThirtyMap.put("bandList", bandList);
        bandThirtyMap.put("thirtyTime", timeList);
        historyList.add(cpuThirtyMap);
        historyList.add(memoryThirtyMap);
        historyList.add(bandThirtyMap);
        return historyList;
    }

    @Override
    public Page queryPowerJoinTaskList(String powerNodeId, int pageNumber, int pageSize) {
        Page<LocalPowerJoinTask> page = PageHelper.startPage(pageNumber, pageSize);
        localPowerJoinTaskMapper.queryPowerJoinTaskList(powerNodeId);
        return page;
    }

    @Override
    public int checkPowerNodeName(String powerNodeName) {
        int count = localPowerNodeMapper.checkPowerNodeName(powerNodeName);
        return count;
    }

}
