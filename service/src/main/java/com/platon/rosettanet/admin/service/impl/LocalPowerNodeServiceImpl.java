package com.platon.rosettanet.admin.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platon.rosettanet.admin.dao.LocalPowerHistoryMapper;
import com.platon.rosettanet.admin.dao.LocalPowerJoinTaskMapper;
import com.platon.rosettanet.admin.dao.LocalPowerNodeMapper;
import com.platon.rosettanet.admin.dao.entity.LocalPowerHistory;
import com.platon.rosettanet.admin.dao.entity.LocalPowerJoinTask;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.grpc.client.PowerClient;
import com.platon.rosettanet.admin.service.LocalPowerNodeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author houz
 * 计算节点业务实现类
 */
@Service
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
        // 调用grpc返回powerNodeId
//        String reposeStr = powerClient.addPowerNode(powerNode.getInternalIp(), powerNode.getExternalIp(),
//                powerNode.getInternalPort(), powerNode.getExternalPort());
        // 计算节点id
        powerNode.setIdentityId("100000001");
        powerNode.setPowerNodeId(UUID.randomUUID().toString());
        // 状态
        powerNode.setStatus("");
        powerNode.setStartTime(LocalDateTime.now());
        // 内存
        powerNode.setMemory(64L);
        // 核数
        powerNode.setCore(4);
        // 带宽
        powerNode.setBandwidth(128L);
        return localPowerNodeMapper.insertPowerNode(powerNode);
    }

    @Override
    public int updatePowerNodeByNodeId(LocalPowerNode powerNode) {
        // 调用grpc返回powerNodeId
//        String reposeStr = powerClient.updatePowerNode(powerNode.getInternalIp(), powerNode.getExternalIp(),
//                powerNode.getInternalPort(), powerNode.getExternalPort());
        // 状态
        powerNode.setStatus("");
        powerNode.setStartTime(LocalDateTime.now());
        // 内存
        powerNode.setMemory(32L);
        // 核数
        powerNode.setCore(8);
        // 带宽
        powerNode.setBandwidth(256L);
        return localPowerNodeMapper.updatePowerNodeByNodeId(powerNode);
    }

    @Override
    public int deletePowerNodeByNodeId(String powerNodeId) {
//        // 调用grpc删除计算节点
//        String resposeStr = powerClient.deletePowerNode(powerNodeId);
//        if () {
//            return 0 ;
//        }
        return localPowerNodeMapper.deletePowerNode(powerNodeId);

    }

    @Override
    public LocalPowerNode queryPowerNodeDetails(String powerNodeId) {
        return localPowerNodeMapper.queryPowerNodeDetails(powerNodeId);
    }

    @Override
    public PageInfo queryPowerNodeList(String identityId, String keyword, int pageNumber, int pageSize) {
        // 调用grpc查询计算节点服务列表
        PageHelper.startPage(pageNumber, pageSize);
        List<LocalPowerNode> list = localPowerNodeMapper.queryPowerNodeList(identityId, keyword);
        PageInfo<LocalPowerNode> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void publishPower(String powerNodeId) {
//        String reposeStr = powerClient.publishPower(powerNodeId, status);
        LocalPowerNode localPowerNode = new LocalPowerNode();
        localPowerNode.setPowerNodeId(powerNodeId);
        // status=2表示算例已启用
        localPowerNode.setStatus("2");
        localPowerNodeMapper.updatePowerNodeByNodeId(localPowerNode);
    }

    @Override
    public void revokePower(String powerNodeId) {
//        String reposeStr = powerClient.revokePower(powerNodeId, status);
        LocalPowerNode localPowerNode = new LocalPowerNode();
        localPowerNode.setPowerNodeId(powerNodeId);
        // status=1表示算例未启用
        localPowerNode.setStatus("1");
        localPowerNodeMapper.updatePowerNodeByNodeId(localPowerNode);
    }

    @Override
    public List queryPowerNodeUseHistory(String powerNodeId) {
        List<LocalPowerHistory>  powerHistoryList = localPowerHistoryMapper.queryPowerHistory(powerNodeId);
        List historyList = new ArrayList();
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
    public PageInfo queryPowerJoinTaskList(String powerNodeId, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<LocalPowerJoinTask> list = localPowerJoinTaskMapper.queryPowerJoinTaskList(powerNodeId);
        PageInfo<LocalPowerJoinTask> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int checkPowerNodeName(String powerNodeName) {
        int count = localPowerNodeMapper.checkPowerNodeName(powerNodeName);
        return count;
    }

}
