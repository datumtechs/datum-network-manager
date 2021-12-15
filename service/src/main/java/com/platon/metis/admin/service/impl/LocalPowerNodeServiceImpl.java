package com.platon.metis.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.metis.admin.common.context.LocalOrgIdentityCache;
import com.platon.metis.admin.common.util.NameUtil;
import com.platon.metis.admin.dao.LocalPowerLoadSnapshotMapper;
import com.platon.metis.admin.dao.LocalPowerNodeMapper;
import com.platon.metis.admin.dao.entity.LocalPowerLoadSnapshot;
import com.platon.metis.admin.dao.entity.LocalPowerNode;
import com.platon.metis.admin.dao.entity.PowerLoad;
import com.platon.metis.admin.grpc.client.PowerClient;
import com.platon.metis.admin.grpc.common.CommonBase;
import com.platon.metis.admin.grpc.service.YarnRpcMessage;
import com.platon.metis.admin.service.LocalPowerNodeService;
import com.platon.metis.admin.service.TaskService;
import com.platon.metis.admin.service.exception.ServiceException;
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


    /** 计算节点 */
    @Resource
    LocalPowerNodeMapper localPowerNodeMapper;

    /** 计算节点资源 */

    /** 计算节点资源 */
    @Resource
    TaskService taskService;

    @Resource
    PowerClient powerClient;

    @Resource
    LocalPowerLoadSnapshotMapper localPowerLoadSnapshotMapper;


    @Override
    public void insertPowerNode(LocalPowerNode powerNode) {
        // 校检名称
        if (!NameUtil.isValidName(powerNode.getPowerNodeName())) {
            throw new ServiceException("名称不符合命名规则！");
        }
        // 调用grpc接口增加算力，此时调度服务会连算力节点，如果正常返回，说明连接成功
        YarnRpcMessage.YarnRegisteredPeerDetail jobNode = null;
        try {
            jobNode = powerClient.addPowerNode(powerNode.getInternalIp(), powerNode.getExternalIp(),
                    powerNode.getInternalPort(), powerNode.getExternalPort());
        } catch (Exception e) {
            log.error("新增计算节点失败", e);
            throw new ServiceException("新增计算节点失败", e);

        }
        log.info("新增计算节点数据:{}", jobNode);
        // 计算节点id
        powerNode.setIdentityId(LocalOrgIdentityCache.getIdentityId());
        powerNode.setPowerNodeId(jobNode.getId());
        // 设置连接状态
        powerNode.setConnStatus(jobNode.getConnState().getNumber());
        // 设置算力状态（未发布）
        powerNode.setPowerStatus(CommonBase.PowerState.PowerState_Created_VALUE);
        // 内存
        powerNode.setMemory(0L);
        // 核数
        powerNode.setCore(0);
        // 带宽
        powerNode.setBandwidth(0L);
        int count = localPowerNodeMapper.insertPowerNode(powerNode);
        if (count == 0) {
            throw new ServiceException("新增失败！");
        }
    }

    @Override
    public void updatePowerNodeByNodeId(LocalPowerNode powerNode) throws Exception {
        // 判断是否有算力进行中
        LocalPowerNode localPowerNode = localPowerNodeMapper.queryPowerNodeDetails(powerNode.getPowerNodeId());


        if(localPowerNode.getConnStatus() == LocalPowerNode.ConnStatus.disconnected.getCode()){
            throw new ServiceException("算力节点不能连接");
        }
        //启用的不能修改
        if(localPowerNode.getPowerStatus() == CommonBase.PowerState.PowerState_Released_VALUE
                || localPowerNode.getPowerStatus() == CommonBase.PowerState.PowerState_Occupation_VALUE ){
            throw new ServiceException("算力节点已经启用");
        }

        // 判断是否有正在进行中的任务
        /*List powerTaskList = localPowerJoinTaskMapper.queryPowerJoinTaskList(powerNode.getPowerNodeId());
        if (null != powerTaskList && powerTaskList.size() > 0) {
            log.info("updatePowerNodeByNodeId--此节点有任务正在进行中:{}", powerTaskList.toString());
            throw new ServiceException("有任务进行中，无法修改此节点！");
        }*/
        // 调用grpc接口修改计算节点信息
        YarnRpcMessage.YarnRegisteredPeerDetail jobNode = powerClient.updatePowerNode(powerNode.getPowerNodeId(), powerNode.getInternalIp(), powerNode.getExternalIp(),
                powerNode.getInternalPort(), powerNode.getExternalPort());
        // 计算节点id
        powerNode.setPowerNodeId(jobNode.getId());
        // 设置连接状态
        powerNode.setConnStatus(jobNode.getConnState().getNumber());
        // 内存
        powerNode.setMemory(0L);
        // 核数
        powerNode.setCore(0);
        // 带宽
        powerNode.setBandwidth(0L);
        int count = localPowerNodeMapper.updatePowerNodeByNodeId(powerNode);
        if (count == 0) {
            throw new ServiceException("修改失败！");
        }
    }

    @Override
    public void deletePowerNodeByNodeId(String powerNodeId) throws Exception {
        // 判断是否有算力进行中
        LocalPowerNode localPowerNode = localPowerNodeMapper.queryPowerNodeDetails(powerNodeId);

        if(localPowerNode.getConnStatus() == LocalPowerNode.ConnStatus.disconnected.getCode()){
            throw new ServiceException("算力节点不能连接");
        }
        //启用的不能删除
        if(localPowerNode.getPowerStatus() == CommonBase.PowerState.PowerState_Released_VALUE
                || localPowerNode.getPowerStatus() == CommonBase.PowerState.PowerState_Occupation_VALUE ){
            throw new ServiceException("算力节点已经启用");
        }

        // 判断是否有正在进行中的任务
        /*List powerTaskList = localPowerJoinTaskMapper.queryPowerJoinTaskList(powerNodeId);
        if (null != powerTaskList && powerTaskList.size() > 0) {
            log.info("updatePowerNodeByNodeId--此节点有任务正在进行中:{}", powerTaskList.toString());
            throw new ServiceException("有任务进行中，无法删除此节点！");
        }*/
        // 删除底层资源
        powerClient.deletePowerNode(powerNodeId);
        // 删除数据
        int count = localPowerNodeMapper.deletePowerNode(powerNodeId);
        if (count == 0) {
            throw new ServiceException("删除失败！");
        }
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
    public void publishPower(String powerNodeId) {
        String powerId = powerClient.publishPower(powerNodeId);
        LocalPowerNode localPowerNode = new LocalPowerNode();
        localPowerNode.setPowerNodeId(powerNodeId);
        localPowerNode.setPowerId(powerId);
        localPowerNode.setPowerStatus(CommonBase.PowerState.PowerState_Released_VALUE);
        //todo：这个时间是本地时间，而不是数据中心时间
        localPowerNode.setStartTime(LocalDateTime.now());
        localPowerNodeMapper.updatePowerNodeByNodeId(localPowerNode);
    }

    @Override
    public void revokePower(String powerNodeId) {
        LocalPowerNode localPowerNode = localPowerNodeMapper.queryPowerNodeDetails(powerNodeId);
        if(localPowerNode==null || StringUtils.isEmpty(localPowerNode.getPowerId())){
            throw new ServiceException("power node not found");
        }
        //调用调度服务
        powerClient.revokePower(localPowerNode.getPowerId());


        localPowerNode.setPowerNodeId(powerNodeId);
        // 停用算力需把上次启动的算力id清空
        localPowerNode.setPowerId("");
        localPowerNode.setPowerStatus(CommonBase.PowerState.PowerState_Revoked_VALUE);
        localPowerNodeMapper.updatePowerNodeByNodeId(localPowerNode);
    }




    @Override
    public void checkPowerNodeName(String powerNodeName) {
        if (!NameUtil.isValidName(powerNodeName)) {
            throw new ServiceException("名称不符合命名规则！");
        }
        int count = localPowerNodeMapper.checkPowerNodeName(powerNodeName);
        if (count > 0) {
            throw new ServiceException("名称已存在！");
        }
    }

    @Override
    public List<LocalPowerLoadSnapshot> listLocalPowerLoadSnapshotByPowerNodeId(String powerNodeId, int hours) {
        return localPowerLoadSnapshotMapper.listLocalPowerLoadSnapshotByPowerNodeId(powerNodeId, hours);
    }

    @Override
    public PowerLoad getCurrentLocalPowerLoadByPowerNodeId(String powerNodeId) {
        return localPowerNodeMapper.getCurrentLocalPowerLoadByPowerNodeId(powerNodeId);
    }

}