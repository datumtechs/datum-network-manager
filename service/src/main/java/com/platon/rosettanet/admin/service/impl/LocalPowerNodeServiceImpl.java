package com.platon.rosettanet.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platon.rosettanet.admin.dao.LocalPowerDetailsMapper;
import com.platon.rosettanet.admin.dao.LocalPowerJoinTaskMapper;
import com.platon.rosettanet.admin.dao.LocalPowerNodeMapper;
import com.platon.rosettanet.admin.dao.entity.LocalPowerDetails;
import com.platon.rosettanet.admin.dao.entity.LocalPowerJoinTask;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.grpc.client.PowerClient;
import com.platon.rosettanet.admin.service.LocalPowerNodeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author houz
 * 计算节点业务实现类
 */
@Service
public class LocalPowerNodeServiceImpl implements LocalPowerNodeService {

    private static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat ymdh = new SimpleDateFormat("yyyy-MM-dd HH");

    /** 计算节点 */
    @Resource
    LocalPowerNodeMapper localPowerNodeMapper;

    /** 计算节点资源 */
    @Resource
    LocalPowerDetailsMapper localPowerDetailsMapper;

    /** 计算节点资源 */
    @Resource
    LocalPowerJoinTaskMapper localPowerJoinTaskMapper;

    @Resource
    PowerClient powerClient;

    @Override
    public int insertPowerNode(LocalPowerNode powerNode) {
        // 调用grpc返回powerNodeId
        String reposeStr = powerClient.addPowerNode(powerNode.getInternalIp(), powerNode.getExternalIp(),
                powerNode.getInternalPort(), powerNode.getExternalPort());
        // 计算节点id
        powerNode.setPowerNodeId("");
        // 状态
        powerNode.setStatus(null);
        // 内存
        powerNode.setMemory(null);
        // 核数
        powerNode.setCore(null);
        // 带宽
        powerNode.setBandwidth(null);
        return localPowerNodeMapper.insertPowerNode(powerNode);
    }

    @Override
    public int updatePowerNodeByNodeId(LocalPowerNode powerNode) {
        // 调用grpc返回powerNodeId
        String reposeStr = powerClient.updatePowerNode(powerNode.getInternalIp(), powerNode.getExternalIp(),
                powerNode.getInternalPort(), powerNode.getExternalPort());
        // 状态
        powerNode.setStatus(null);
        // 内存
        powerNode.setMemory(null);
        // 核数
        powerNode.setCore(null);
        // 带宽
        powerNode.setBandwidth(null);
        return localPowerNodeMapper.updatePowerNodeByNodeId(powerNode);
    }

    @Override
    public int deletePowerNodeByNodeId(String powerNodeId) {
        // 调用grpc删除计算节点
        String resposeStr = powerClient.deletePowerNode(powerNodeId);
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
    }

    @Override
    public void revokePower(String powerNodeId) {
//        String reposeStr = powerClient.revokePower(powerNodeId, status);
    }

    @Override
    public List queryPowerNodeUseResource(String powerNodeId) {
        List<LocalPowerDetails>  powerDetailsList = localPowerDetailsMapper.queryPowerDetails(powerNodeId);
        List detailsList = new ArrayList();
        if (!powerDetailsList.isEmpty()) {
            // 24小时
            detailsList = this.hoursMethod(powerDetailsList, detailsList);
            // 7天
            detailsList = this.sevenMethod(powerDetailsList, detailsList);
            // 15天
            detailsList = this.fifteenMethod(powerDetailsList, detailsList);
            // 30天记录
            detailsList = this.thirtyMethod(powerDetailsList, detailsList);
        }
        return detailsList;
    }

    /** 24小时记录 */
    private List hoursMethod(List<LocalPowerDetails> powerDetailsList, List detailsList){
        List cupList = new ArrayList();
        List memoryList = new ArrayList();
        List bandList = new ArrayList();
        List timeList = new ArrayList();
        Map cpuHoursMap = new HashMap(16);
        Map memoryHoursMap = new HashMap(16);
        Map bandHoursMap = new HashMap(16);
        for(LocalPowerDetails powerDetails : powerDetailsList) {
            if ("0".equals(powerDetails.getRefreshStatus())) {
                // 时间(小时)
                timeList.add(ymdh.format(powerDetails.getCreateTime()));
                // cpu
                cupList.add(powerDetails.getUsedCore());
                // 内存
                memoryList.add(powerDetails.getUsedMemory());
                // 带宽
                bandList.add(powerDetails.getUsedBandwidth());
            }
            if (timeList.size() >= 24) {
                break;
            }
        }
        cpuHoursMap.put("resource", cupList);
        cpuHoursMap.put("hoursTime", timeList);
        memoryHoursMap.put("resource", memoryList);
        memoryHoursMap.put("hoursTime", timeList);
        bandHoursMap.put("resource", bandList);
        bandHoursMap.put("hoursTime", timeList);
        detailsList.add(cpuHoursMap);
        detailsList.add(memoryHoursMap);
        detailsList.add(bandHoursMap);
        return detailsList;
    }

    /** 7天记录 */
    private List sevenMethod(List<LocalPowerDetails> powerDetailsList, List detailsList){
        List cupList = new ArrayList();
        List memoryList = new ArrayList();
        List bandList = new ArrayList();
        List timeList = new ArrayList();
        Map cpuSevenMap = new HashMap(16);
        Map memorySevenMap = new HashMap(16);
        Map bandSevenMap = new HashMap(16);
        for(LocalPowerDetails powerDetails : powerDetailsList) {
            if ("1".equals(powerDetails.getRefreshStatus())) {
                // 时间(小时)
                timeList.add(ymd.format(powerDetails.getCreateTime()));
                // cpu
                cupList.add(powerDetails.getUsedCore());
                // 内存
                memoryList.add(powerDetails.getUsedMemory());
                // 带宽
                bandList.add(powerDetails.getUsedBandwidth());
            }
            if (timeList.size() >= 7) {
                break;
            }
        }
        cpuSevenMap.put("resource", cupList);
        cpuSevenMap.put("sevenTime", timeList);
        memorySevenMap.put("resource", memoryList);
        memorySevenMap.put("sevenTime", timeList);
        bandSevenMap.put("resource", bandList);
        bandSevenMap.put("sevenTime", timeList);
        detailsList.add(cpuSevenMap);
        detailsList.add(memorySevenMap);
        detailsList.add(bandSevenMap);
        return detailsList;
    }

    /** 15天记录 */
    private List fifteenMethod(List<LocalPowerDetails> powerDetailsList, List detailsList){
        List cupList = new ArrayList();
        List memoryList = new ArrayList();
        List bandList = new ArrayList();
        List timeList = new ArrayList();
        Map cpuFifteenMap = new HashMap(16);
        Map memoryFifteenMap = new HashMap(16);
        Map bandFifteenMap = new HashMap(16);
        for(LocalPowerDetails powerDetails : powerDetailsList) {
            if ("1".equals(powerDetails.getRefreshStatus())) {
                // 时间(小时)
                timeList.add(ymd.format(powerDetails.getCreateTime()));
                // cpu
                cupList.add(powerDetails.getUsedCore());
                // 内存
                memoryList.add(powerDetails.getUsedMemory());
                // 带宽
                bandList.add(powerDetails.getUsedBandwidth());
            }
            if (timeList.size() >= 15) {
                break;
            }
        }
        cpuFifteenMap.put("resource", cupList);
        cpuFifteenMap.put("fifteenTime", timeList);
        memoryFifteenMap.put("resource", memoryList);
        memoryFifteenMap.put("fifteenTime", timeList);
        bandFifteenMap.put("resource", bandList);
        bandFifteenMap.put("fifteenTime", timeList);
        detailsList.add(cpuFifteenMap);
        detailsList.add(memoryFifteenMap);
        detailsList.add(bandFifteenMap);
        return detailsList;
    }
    /** 30天记录 */
    private List thirtyMethod(List<LocalPowerDetails> powerDetailsList, List detailsList){
        List cupList = new ArrayList();
        List memoryList = new ArrayList();
        List bandList = new ArrayList();
        List timeList = new ArrayList();
        Map cpuThirtyMap = new HashMap(16);
        Map memoryThirtyMap = new HashMap(16);
        Map bandThirtyMap = new HashMap(16);
        for(LocalPowerDetails powerDetails : powerDetailsList) {
            if ("1".equals(powerDetails.getRefreshStatus())) {
                // 时间(小时)
                timeList.add(ymd.format(powerDetails.getCreateTime()));
                // cpu
                cupList.add(powerDetails.getUsedCore());
                // 内存
                memoryList.add(powerDetails.getUsedMemory());
                // 带宽
                bandList.add(powerDetails.getUsedBandwidth());
            }
            if (timeList.size() >= 30) {
                break;
            }
        }
        cpuThirtyMap.put("resource", cupList);
        cpuThirtyMap.put("thirtyTime", timeList);
        memoryThirtyMap.put("resource", memoryList);
        memoryThirtyMap.put("thirtyTime", timeList);
        bandThirtyMap.put("resource", bandList);
        bandThirtyMap.put("thirtyTime", timeList);
        detailsList.add(cpuThirtyMap);
        detailsList.add(memoryThirtyMap);
        detailsList.add(bandThirtyMap);
        return detailsList;
    }

    @Override
    public PageInfo queryPowerJoinTaskList(String powerNodeId, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<LocalPowerJoinTask> list = localPowerJoinTaskMapper.queryPowerJoinTaskList(powerNodeId);
        PageInfo<LocalPowerJoinTask> pageInfo = new PageInfo(list);
        return pageInfo;
    }

}
