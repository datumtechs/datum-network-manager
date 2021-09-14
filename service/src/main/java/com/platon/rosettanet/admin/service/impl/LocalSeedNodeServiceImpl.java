package com.platon.rosettanet.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.common.util.NameUtil;
import com.platon.rosettanet.admin.dao.LocalSeedNodeMapper;
import com.platon.rosettanet.admin.dao.entity.LocalSeedNode;
import com.platon.rosettanet.admin.grpc.client.SeedClient;
import com.platon.rosettanet.admin.grpc.service.YarnRpcMessage;
import com.platon.rosettanet.admin.service.LocalSeedNodeService;
import com.platon.rosettanet.admin.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author houz
 * 计算节点业务实现类
 */
@Service
@Slf4j
public class LocalSeedNodeServiceImpl implements LocalSeedNodeService {


    /** 计算节点 */
    @Resource
    LocalSeedNodeMapper localSeedNodeMapper;

    @Resource
    SeedClient seedClient;

    @Override
    public void insertSeedNode(LocalSeedNode seedNode) {
        // 校检名称
        if (!NameUtil.isValidName(seedNode.getSeedNodeName())) {
            throw new ServiceException("名称不符合命名规则！");
        }
        // 调用grpc接口新增节点信息
        YarnRpcMessage.SeedPeer seedResp = seedClient.addSeedNode(seedNode.getInternalIp(),
                seedNode.getInternalPort());
        seedNode.setIdentityId(LocalOrgIdentityCache.getIdentityId());
        // 计算节点id
        seedNode.setSeedNodeId(seedResp.getId());
        seedNode.setConnStatus(seedResp.getConnState().getNumber());
        // 0表示初始节点
        seedNode.setInitFlag(0);
        int count = localSeedNodeMapper.insertSeedNode(seedNode);
        if (count == 0) {
            throw new ServiceException("新增失败！");
        }
    }

    @Override
    public void updateSeedNode(LocalSeedNode seedNode) {
        // 调用grpc接口修改计算节点信息
        YarnRpcMessage.SeedPeer seedResp = seedClient.updateSeedNode(seedNode.getSeedNodeId(), seedNode.getInternalIp(),
                seedNode.getInternalPort());
        // 种子节点id
        seedNode.setSeedNodeId(seedResp.getId());
        // 连接状态
        seedNode.setConnStatus(seedResp.getConnState().getNumber());
        // 1表示不是初始节点
        seedNode.setInitFlag(1);
        int count = localSeedNodeMapper.updateSeedNode(seedNode);
        if (count == 0) {
            throw new ServiceException("修改失败！");
        }
    }

    @Override
    public void updateSeedNodeBatch(List<LocalSeedNode> localSeedNodeList) {

    }

    @Override
    public void deleteSeedNode(String seedNodeId) {
        // 删除底层资源
        seedClient.deleteSeedNode(seedNodeId);
        // 删除数据
        int count = localSeedNodeMapper.deleteSeedNode(seedNodeId);
        if (count == 0) {
            throw new ServiceException("删除失败！");
        }
    }

    @Override
    public LocalSeedNode querySeedNodeDetails(String seedNodeId) {
        return localSeedNodeMapper.querySeedNodeDetails(seedNodeId);
    }

    @Override
    public Page<LocalSeedNode> querySeedNodeList(String keyword, int pageNumber, int pageSize) {
        Page<LocalSeedNode> page = PageHelper.startPage(pageNumber, pageSize);
        localSeedNodeMapper.querySeedNodeList(keyword);
        return page;
    }

    @Override
    public void checkSeedNodeName(String seedNodeName) {
        if (!NameUtil.isValidName(seedNodeName)) {
            throw new ServiceException("名称不符合命名规则！");
        }
        int count = localSeedNodeMapper.checkSeedNodeName(seedNodeName);
        if (count > 0) {
            throw new ServiceException("名称已存在！");
        }
    }

}
