package com.platon.metis.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.metis.admin.common.exception.*;
import com.platon.metis.admin.common.util.NameUtil;
import com.platon.metis.admin.dao.LocalPowerLoadSnapshotMapper;
import com.platon.metis.admin.dao.LocalPowerNodeMapper;
import com.platon.metis.admin.dao.entity.LocalPowerLoadSnapshot;
import com.platon.metis.admin.dao.entity.LocalPowerNode;
import com.platon.metis.admin.dao.entity.PowerLoad;
import com.platon.metis.admin.grpc.carrier.api.SysRpcApi;
import com.platon.metis.admin.grpc.client.PowerClient;
import com.platon.metis.admin.grpc.common.constant.CarrierEnum;
import com.platon.metis.admin.service.LocalPowerNodeService;
import com.platon.metis.admin.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author houz
 * 计算节点业务实现类
 */
@Service
@Slf4j
public class LocalPowerNodeServiceImpl implements LocalPowerNodeService {


    /**
     * 计算节点
     */
    @Resource
    LocalPowerNodeMapper localPowerNodeMapper;

    /** 计算节点资源 */

    /**
     * 计算节点资源
     */
    @Resource
    TaskService taskService;

    @Resource
    PowerClient powerClient;

    @Resource
    LocalPowerLoadSnapshotMapper localPowerLoadSnapshotMapper;


    @Override
    public void insertPowerNode(LocalPowerNode powerNode) {
        // 校检名称
        if (!NameUtil.isValidName(powerNode.getNodeName())) {
            log.error("power node name error");
            throw new ArgumentException();
        }
        // 调用grpc接口增加算力，此时调度服务会连算力节点，如果正常返回，说明连接成功
        SysRpcApi.YarnRegisteredPeerDetail jobNode = powerClient.addPowerNode(powerNode.getInternalIp(), powerNode.getExternalIp(),
                powerNode.getInternalPort(), powerNode.getExternalPort());

        log.info("新增计算节点数据:{}", jobNode);
        // 计算节点id
        powerNode.setNodeId(jobNode.getId());
        // 设置连接状态
        powerNode.setConnStatus(jobNode.getConnState().getNumber());
        // 设置算力状态（未发布）
        powerNode.setPowerStatus(CarrierEnum.PowerState.PowerState_Created_VALUE);
        // 内存
        powerNode.setMemory(0L);
        // 核数
        powerNode.setCore(0);
        // 带宽
        powerNode.setBandwidth(0L);
        localPowerNodeMapper.insertPowerNode(powerNode);
    }

    @Override
    public void updatePowerNodeByNodeId(LocalPowerNode powerNode) {
        // 判断是否有算力进行中
        LocalPowerNode localPowerNode = localPowerNodeMapper.queryPowerNodeDetails(powerNode.getNodeId());


        if (localPowerNode.getConnStatus() == LocalPowerNode.ConnStatus.disconnected.getCode()) {
            throw new CannotConnectPowerNode();
        }
        //启用的不能修改
        if (localPowerNode.getPowerStatus() == CarrierEnum.PowerState.PowerState_Released_VALUE
                || localPowerNode.getPowerStatus() == CarrierEnum.PowerState.PowerState_Occupation_VALUE) {
            throw new CannotEditPowerNode();
        } else if (localPowerNode.getPowerStatus() == 5 || localPowerNode.getPowerStatus() == 6) {
            throw new CannotOpsPowerNode();
        }

        // 判断是否有正在进行中的任务
        /*List powerTaskList = localPowerJoinTaskMapper.queryPowerJoinTaskList(powerNode.getPowerNodeId());
        if (null != powerTaskList && powerTaskList.size() > 0) {
            log.info("updatePowerNodeByNodeId--此节点有任务正在进行中:{}", powerTaskList.toString());
            throw new ServiceException("有任务进行中，无法修改此节点！");
        }*/
        // 调用grpc接口修改计算节点信息
        SysRpcApi.YarnRegisteredPeerDetail jobNode = powerClient.updatePowerNode(powerNode.getNodeId(), powerNode.getInternalIp(), powerNode.getExternalIp(),
                powerNode.getInternalPort(), powerNode.getExternalPort());
        // 计算节点id
        powerNode.setNodeId(jobNode.getId());
        // 设置连接状态
        powerNode.setConnStatus(jobNode.getConnState().getNumber());
        // 内存
        powerNode.setMemory(0L);
        // 核数
        powerNode.setCore(0);
        // 带宽
        powerNode.setBandwidth(0L);
        localPowerNodeMapper.updatePowerNodeByNodeId(powerNode);
    }

    @Override
    public void deletePowerNodeByNodeId(String nodeId) {
        // 判断是否有算力进行中
        LocalPowerNode localPowerNode = localPowerNodeMapper.queryPowerNodeDetails(nodeId);

        if (localPowerNode.getConnStatus() == LocalPowerNode.ConnStatus.disconnected.getCode()) {
            throw new CannotConnectPowerNode();
        }
        //启用的不能删除
        if (localPowerNode.getPowerStatus() == CarrierEnum.PowerState.PowerState_Released_VALUE
                || localPowerNode.getPowerStatus() == CarrierEnum.PowerState.PowerState_Occupation_VALUE) {
            throw new CannotEditPowerNode();
        } else if (localPowerNode.getPowerStatus() == 5 || localPowerNode.getPowerStatus() == 6) {
            throw new CannotOpsPowerNode();
        }

        // 判断是否有正在进行中的任务
        /*List powerTaskList = localPowerJoinTaskMapper.queryPowerJoinTaskList(powerNodeId);
        if (null != powerTaskList && powerTaskList.size() > 0) {
            log.info("updatePowerNodeByNodeId--此节点有任务正在进行中:{}", powerTaskList.toString());
            throw new ServiceException("有任务进行中，无法删除此节点！");
        }*/
        // 删除底层资源
        powerClient.deletePowerNode(nodeId);
        // 删除数据
        localPowerNodeMapper.deletePowerNode(nodeId);
    }

    @Override
    public LocalPowerNode findPowerNodeDetails(String powerNodeId) {
        return localPowerNodeMapper.queryPowerNodeDetails(powerNodeId);
    }

    @Override
    public Page<LocalPowerNode> listPowerNode(String identityId, String keyword, int pageNumber, int pageSize) {
        Page<LocalPowerNode> page = PageHelper.startPage(pageNumber, pageSize);
        localPowerNodeMapper.queryPowerNodeList(keyword);
        return page;
    }

    @Override
    public void publishPower(String nodeId) {
        LocalPowerNode oldPowerNode = localPowerNodeMapper.queryPowerNodeDetails(nodeId);
        if (oldPowerNode.getPowerStatus() == 1 || oldPowerNode.getPowerStatus() == 4) {
            String powerId = powerClient.publishPower(nodeId);
            LocalPowerNode localPowerNode = new LocalPowerNode();
            localPowerNode.setNodeId(nodeId);
            localPowerNode.setPowerId(powerId);
            localPowerNode.setPowerStatus(5);
            //todo：这个时间是本地时间，而不是数据中心时间
            localPowerNode.setStartTime(LocalDateTime.now());
            localPowerNodeMapper.updatePowerNodeByNodeId(localPowerNode);
        } else {
            throw new CannotOpsPowerNode("only unpublished and revoked nodes can be published");
        }

    }

    @Override
    public void revokePower(String nodeId) {
        LocalPowerNode localPowerNode = localPowerNodeMapper.queryPowerNodeDetails(nodeId);
        if (localPowerNode == null || StringUtils.isEmpty(localPowerNode.getPowerId())) {
            log.error("power node not found");
            throw new ObjectNotFound();
        }

        if (localPowerNode.getPowerStatus() == 2) {
            //调用调度服务
            powerClient.revokePower(localPowerNode.getPowerId());
            localPowerNode.setNodeId(nodeId);
            // 停用算力需把上次启动的算力id清空
//            localPowerNode.setPowerId("");
            localPowerNode.setPowerStatus(6);
            localPowerNodeMapper.updatePowerNodeByNodeId(localPowerNode);
        } else {
            throw new CannotOpsPowerNode("only published nodes can be revoked.");
        }

    }


    @Override
    public List<LocalPowerLoadSnapshot> listLocalPowerLoadSnapshotByNodeId(String nodeId, int hours) {
        return localPowerLoadSnapshotMapper.listLocalPowerLoadSnapshotByNodeId(nodeId, hours);
    }

    @Override
    public PowerLoad getCurrentLocalPowerLoadByNodeId(String nodeId) {
        return localPowerNodeMapper.getCurrentLocalPowerLoadByNodeId(nodeId);
    }

    @Override
    public LocalPowerNode findLocalPowerNodeByName(String nodeName) {
        return localPowerNodeMapper.findLocalPowerNodeByName(nodeName);
    }

    @Override
    public void updateLocalPowerNodeName(String nodeId, String nodeName) {
        localPowerNodeMapper.updateLocalPowerNodeName(nodeId, nodeName);
    }

}