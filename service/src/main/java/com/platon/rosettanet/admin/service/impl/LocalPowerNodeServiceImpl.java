package com.platon.rosettanet.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.LocalPowerHistoryMapper;
import com.platon.rosettanet.admin.dao.LocalPowerJoinTaskMapper;
import com.platon.rosettanet.admin.dao.LocalPowerNodeMapper;
import com.platon.rosettanet.admin.dao.entity.LocalPowerHistory;
import com.platon.rosettanet.admin.dao.entity.LocalPowerJoinTask;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.grpc.client.PowerClient;
import com.platon.rosettanet.admin.grpc.service.YarnRpcMessage;
import com.platon.rosettanet.admin.service.LocalPowerNodeService;
import com.platon.rosettanet.admin.service.constant.ServiceConstant;
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
        // 判断是否有正在进行中的任务
        List powerTaskList = localPowerJoinTaskMapper.queryPowerJoinTaskList(powerNode.getPowerNodeId());
        if (null != powerTaskList && powerTaskList.size() > 0) {
            log.info("updatePowerNodeByNodeId--此节点有任务正在进行中:{}", powerTaskList.toString());
            return 0;
        }
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
        // 判断是否有正在进行中的任务
        List powerTaskList = localPowerJoinTaskMapper.queryPowerJoinTaskList(powerNodeId);
        if (null != powerTaskList && powerTaskList.size() > 0) {
            log.info("updatePowerNodeByNodeId--此节点有任务正在进行中:{}", powerTaskList.toString());
            return 0;
        }
        // 删除底层资源
        powerClient.deletePowerNode(powerNodeId);
        // 删除数据
        return localPowerNodeMapper.deletePowerNode(powerNodeId);
    }

    @Override
    public LocalPowerNode queryPowerNodeDetails(String powerNodeId) {
        return localPowerNodeMapper.queryPowerNodeDetails(powerNodeId);
    }

    @Override
    public Page<LocalPowerNode> queryPowerNodeList(String identityId, String keyword, int pageNumber, int pageSize) {
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
        localPowerNode.setConnStatus("0");
        localPowerNodeMapper.updatePowerNodeByNodeId(localPowerNode);
    }

    @Override
    public List<Map> queryPowerNodeUseHistory(String powerNodeId, String resourceType, String timeType) {
        // 24小时记录
        if (ServiceConstant.constant_1.equals(timeType)) {
            List<LocalPowerHistory> powerHistoryList = localPowerHistoryMapper.queryPowerHistory(powerNodeId, "0");
            if (powerHistoryList != null && powerHistoryList.size() > 0) {
                return this.historyMethod(powerHistoryList, resourceType, 24);
            }
        };
        // 7天记录
        if (ServiceConstant.constant_7.equals(timeType)) {
            List<LocalPowerHistory> powerHistoryList = localPowerHistoryMapper.queryPowerHistory(powerNodeId, "1");
            if (powerHistoryList != null && powerHistoryList.size() > 0) {
                return this.historyMethod(powerHistoryList, resourceType, 7);
            }
        }
        // 15天记录
        if (ServiceConstant.constant_15.equals(timeType)) {
            List<LocalPowerHistory> powerHistoryList = localPowerHistoryMapper.queryPowerHistory(powerNodeId, "1");
            if (powerHistoryList != null && powerHistoryList.size() > 0) {
                return this.historyMethod(powerHistoryList, resourceType, 15);
            }
        }
        // 30天记录
        if (ServiceConstant.constant_30.equals(timeType)) {
            List<LocalPowerHistory> powerHistoryList = localPowerHistoryMapper.queryPowerHistory(powerNodeId, "1");
            if (powerHistoryList != null && powerHistoryList.size() > 0) {
                return this.historyMethod(powerHistoryList, resourceType, 24);
            }
        }
        return new ArrayList<>();
    }

    /** 封装各个时间记录 */
    private List historyMethod(List<LocalPowerHistory> powerHistoryList, String resourceType, int timeType){
        List resourceList = new ArrayList();
        // cpu
        if (ServiceConstant.constant_1.equals(resourceType)) {
            for(LocalPowerHistory powerHistory : powerHistoryList) {
                resourceList.add(powerHistory.getUsedCore());
                if (resourceList.size() >= timeType) {
                    break;
                }
            }
        }
        // 内存
        if (ServiceConstant.constant_2.equals(resourceType)) {
            for(LocalPowerHistory powerHistory : powerHistoryList) {
                resourceList.add(powerHistory.getUsedMemory());
                if (resourceList.size() >= timeType) {
                    break;
                }
            }
        }
        // 带宽
        if (ServiceConstant.constant_3.equals(resourceType)) {
            for(LocalPowerHistory powerHistory : powerHistoryList) {
                resourceList.add(powerHistory.getUsedBandwidth());
                if (resourceList.size() >= timeType) {
                    break;
                }
            }
        }
        return resourceList;
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
