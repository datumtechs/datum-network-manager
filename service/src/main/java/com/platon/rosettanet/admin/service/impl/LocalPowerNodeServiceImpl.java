package com.platon.rosettanet.admin.service.impl;

import com.platon.rosettanet.admin.dao.LocalPowerNodeMapper;
import com.platon.rosettanet.admin.dao.entity.LocalPowerNode;
import com.platon.rosettanet.admin.service.LocalPowerNodeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author houz
 * 计算节点业务实现类
 */
@Service
public class LocalPowerNodeServiceImpl implements LocalPowerNodeService {

    @Resource
    LocalPowerNodeMapper localPowerNodeMapper;

    @Override
    public int insertPowerNode(LocalPowerNode localPowerNode) {
        return localPowerNodeMapper.insertPowerNode(localPowerNode);
    }

    @Override
    public int updatePowerNodeByNodeId(LocalPowerNode localPowerNode) {
        return localPowerNodeMapper.updatePowerNodeByNodeId(localPowerNode);
    }

    @Override
    public LocalPowerNode selectPowerDetailByNodeId(String nodeId) {
        return localPowerNodeMapper.selectPowerDetailByNodeId(nodeId);
    }

    @Override
    public int deletePowerNodeByNodeId(String id) {
        return localPowerNodeMapper.deletePowerNodeByNodeId(id);
    }
}
