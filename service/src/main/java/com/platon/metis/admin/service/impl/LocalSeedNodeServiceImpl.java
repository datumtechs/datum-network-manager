package com.platon.metis.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.metis.admin.dao.LocalSeedNodeMapper;
import com.platon.metis.admin.dao.entity.LocalSeedNode;
import com.platon.metis.admin.grpc.client.SeedClient;
import com.platon.metis.admin.grpc.service.YarnRpcMessage;
import com.platon.metis.admin.service.LocalSeedNodeService;
import com.platon.metis.admin.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        // 调用grpc接口新增节点信息
        if (!verifySeedNodeId(seedNode.getSeedNodeId())) {
            throw new ServiceException("种子节点ID不符合规则！");
        }
        if (localSeedNodeMapper.querySeedNodeDetails(seedNode.getSeedNodeId()) != null) {
            throw new ServiceException("种子节点已经存在！");
        }

        YarnRpcMessage.SeedPeer seedResp = seedClient.addSeedNode(seedNode.getSeedNodeId());
        seedNode.setInitFlag(seedResp.getIsDefault());
        seedNode.setConnStatus(seedResp.getConnState().getNumber());
        int count = localSeedNodeMapper.insertSeedNode(seedNode);
        if (count == 0) {
            throw new ServiceException("新增失败！");
        }
    }

    /*@Override
    public void updateSeedNodeBatch(List<LocalSeedNode> localSeedNodeList) {

    }*/

    @Override
    public void deleteSeedNode(String seedNodeId) {

        LocalSeedNode localSeedNode = localSeedNodeMapper.querySeedNodeDetails(seedNodeId);
        if(localSeedNode==null){
            throw new ServiceException("种子节点信息没有找到！");
        }
        if(localSeedNode.getInitFlag()){//内置
            throw new ServiceException("内置的种子节点不能删除！");
        }

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
    public Page<LocalSeedNode> listSeedNode(int pageNumber, int pageSize) {
        Page<LocalSeedNode> page = PageHelper.startPage(pageNumber, pageSize);
        localSeedNodeMapper.listSeedNode();
        return page;
    }

    @Override
    public void checkSeedNodeId(String seedNodeId) {
        if (!verifySeedNodeId(seedNodeId)) {
            throw new ServiceException("种子节点ID不符合规则！");
        }
        if (localSeedNodeMapper.querySeedNodeDetails(seedNodeId) != null) {
            throw new ServiceException("种子节点已经存在！");
        }
    }


    ///ip4/192.168.9.155/tcp/18001/p2p/16Uiu2HAm291kstk4F64bEEuEQqNhLwnDhR4dCnYB4nQawqhixY9f
    private static boolean verifySeedNodeId(String seedNodeId) {
       return true;
    }
}
