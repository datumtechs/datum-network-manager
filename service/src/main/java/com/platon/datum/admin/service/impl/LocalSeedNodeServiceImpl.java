package com.platon.datum.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.ArgumentException;
import com.platon.datum.admin.common.exception.CannotDeleteInitSeedNode;
import com.platon.datum.admin.common.exception.ObjectNotFound;
import com.platon.datum.admin.common.exception.SeedNodeExists;
import com.platon.datum.admin.dao.LocalSeedNodeMapper;
import com.platon.datum.admin.dao.entity.LocalSeedNode;
import com.platon.datum.admin.grpc.carrier.api.SysRpcApi;
import com.platon.datum.admin.grpc.client.SeedClient;
import com.platon.datum.admin.service.LocalSeedNodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author houz
 * 计算节点业务实现类
 */
@Service
@Slf4j
public class LocalSeedNodeServiceImpl implements LocalSeedNodeService {


    /**
     * 计算节点
     */
    @Resource
    LocalSeedNodeMapper localSeedNodeMapper;

    @Resource
    SeedClient seedClient;

    @Override
    public void insertSeedNode(LocalSeedNode seedNode) {
        // 调用grpc接口新增节点信息
        if (!verifySeedNodeId(seedNode.getSeedNodeId())) {
            log.error("seed node ID error");
            throw new ArgumentException();
        }
        if (localSeedNodeMapper.querySeedNodeDetails(seedNode.getSeedNodeId()) != null) {
            throw new SeedNodeExists();
        }

        SysRpcApi.SeedPeer seedResp = seedClient.addSeedNode(seedNode.getSeedNodeId());
        seedNode.setInitFlag(seedResp.getIsDefault());
        seedNode.setConnStatus(seedResp.getConnState().getNumber());
        localSeedNodeMapper.insertSeedNode(seedNode);
    }

    /*@Override
    public void updateSeedNodeBatch(List<LocalSeedNode> localSeedNodeList) {

    }*/

    @Override
    public void deleteSeedNode(String seedNodeId) {

        LocalSeedNode localSeedNode = localSeedNodeMapper.querySeedNodeDetails(seedNodeId);
        if (localSeedNode == null) {
            throw new ObjectNotFound();
        }
        if (localSeedNode.getInitFlag()) {//内置
            throw new CannotDeleteInitSeedNode();
        }

        // 删除底层资源
        seedClient.deleteSeedNode(seedNodeId);
        // 删除数据
        localSeedNodeMapper.deleteSeedNode(seedNodeId);
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
            throw new ArgumentException();
        }
        if (localSeedNodeMapper.querySeedNodeDetails(seedNodeId) != null) {
            throw new SeedNodeExists();
        }
    }


    ///ip4/192.168.9.155/tcp/18001/p2p/16Uiu2HAm291kstk4F64bEEuEQqNhLwnDhR4dCnYB4nQawqhixY9f
    private static boolean verifySeedNodeId(String seedNodeId) {
        //长度校验
        if (StringUtils.length(seedNodeId) > 256) {
            return false;
        }
        return true;
    }
}
