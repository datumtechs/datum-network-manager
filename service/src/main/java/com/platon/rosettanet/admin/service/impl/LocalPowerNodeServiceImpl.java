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
        // status=1表示算例已启用
        localPowerNode.setConnStatus("1");
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
        // status=0表示算例未启用
        localPowerNode.setConnStatus("0");
        localPowerNodeMapper.updatePowerNodeByNodeId(localPowerNode);
    }

    @Override
    public List<Map> queryPowerNodeUseHistory(String powerNodeId, String resourceType, String timeType) {
        // 24小时记录
        if ("1".equals(timeType)) {
            List<LocalPowerHistory> powerHistoryList = localPowerHistoryMapper.queryPowerHistory(powerNodeId, "0");
            if (!powerHistoryList.isEmpty()) {
                return this.historyMethod(powerHistoryList, resourceType, 24);
            }
        }
        // 7天记录
        if ("7".equals(timeType)) {
            List<LocalPowerHistory> powerHistoryList = localPowerHistoryMapper.queryPowerHistory(powerNodeId, "1");
            if (!powerHistoryList.isEmpty()) {
                return this.historyMethod(powerHistoryList, resourceType, 7);
            }
        }
        // 15天记录
        if ("15".equals(timeType)) {
            List<LocalPowerHistory> powerHistoryList = localPowerHistoryMapper.queryPowerHistory(powerNodeId, "1");
            if (!powerHistoryList.isEmpty()) {
                return this.historyMethod(powerHistoryList, resourceType, 15);
            }
        }
        // 30天记录
        if ("24".equals(timeType)) {
            List<LocalPowerHistory> powerHistoryList = localPowerHistoryMapper.queryPowerHistory(powerNodeId, "1");
            if (!powerHistoryList.isEmpty()) {
                return this.historyMethod(powerHistoryList, resourceType, 24);
            }
        }
        return new ArrayList<>();
    }

    /** 封装各个时间记录 */
    private List historyMethod(List<LocalPowerHistory> powerHistoryList, String resourceType, int timeType){
        // cpu
        if ("1".equals(resourceType)) {
            List cupList = new ArrayList();
            for(LocalPowerHistory powerHistory : powerHistoryList) {
                cupList.add(powerHistory.getUsedCore());
                if (cupList.size() >= timeType) {
                    return cupList;
                }
            }
        }
        // 内存
        if ("2".equals(resourceType)) {
            List memoryList = new ArrayList();
            for(LocalPowerHistory powerHistory : powerHistoryList) {
                memoryList.add(powerHistory.getUsedMemory());
                if (memoryList.size() >= timeType) {
                    return memoryList;
                }
            }
        }
        // 带宽
        if ("3".equals(resourceType)) {
            List bandList = new ArrayList();
            for(LocalPowerHistory powerHistory : powerHistoryList) {
                // 内存
                bandList.add(powerHistory.getUsedMemory());
                if (bandList.size() >= timeType) {
                    return bandList;
                }
            }
        }
        return new ArrayList();
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
