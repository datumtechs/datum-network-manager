package com.platon.rosettanet.admin.service.impl;

import com.platon.rosettanet.admin.dao.LocalPowerNodeMapper;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.grpc.client.PowerClient;
import com.platon.rosettanet.admin.service.LocalPowerNodeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author houz
 * 计算节点业务实现类
 */
@Service
public class LocalPowerNodeServiceImpl implements LocalPowerNodeService {

    @Resource
    LocalPowerNodeMapper localPowerNodeMapper;

    @Resource
    PowerClient powerClient;

    @Override
    public int insertPowerNode(LocalPowerNode powerNode) {
        // 调用grpc返回powerNodeId
        String reposeStr = powerClient.addPowerNode(powerNode.getInternalIp(), powerNode.getExternalIp(),
                powerNode.getInternalPort(), powerNode.getExternalPort());
        // 计算节点id
        powerNode.setPowerNodeId("");
        return localPowerNodeMapper.insertPowerNode(powerNode);
    }

    @Override
    public int updatePowerNodeByNodeId(LocalPowerNode powerNode) {
        // 调用grpc返回powerNodeId
        String reposeStr = powerClient.updatePowerNode(powerNode.getInternalIp(), powerNode.getExternalIp(),
                powerNode.getInternalPort(), powerNode.getExternalPort());
        // 计算节点id
        powerNode.setPowerNodeId("");
        return localPowerNodeMapper.updatePowerNodeByNodeId(powerNode);
    }

    @Override
    public int deletePowerNodeByNodeId(String powerNodeId) {
        // 调用grpc返回powerNodeId
        String resposeStr = powerClient.deletePowerNode(powerNodeId);
//        if () {
//            return 0 ;
//        }
        return localPowerNodeMapper.deletePowerNode(powerNodeId);

    }

    @Override
    public LocalPowerNode selectPowerDetailByNodeId(String powerNodeId) {
        return localPowerNodeMapper.selectPowerDetailByNodeId(powerNodeId);
    }

    @Override
    public List<LocalPowerNode> selectPowerListByIdentityId(String identityId) {
        // 调用grpc查询计算节点服务列表
        String reposeStr = powerClient.GetJobNodeList(identityId);
        return localPowerNodeMapper.selectPowerListByIdentityId(identityId);
    }

}
