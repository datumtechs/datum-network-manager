package com.platon.datum.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.*;
import com.platon.datum.admin.dao.SeedNodeMapper;
import com.platon.datum.admin.dao.entity.SeedNode;
import com.platon.datum.admin.grpc.carrier.api.SysRpcApi;
import com.platon.datum.admin.grpc.client.SeedClient;
import com.platon.datum.admin.service.SeedNodeService;
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
public class SeedNodeServiceImpl implements SeedNodeService {


    /**
     * 计算节点
     */
    @Resource
    SeedNodeMapper seedNodeMapper;

    @Resource
    SeedClient seedClient;

    @Override
    public void insertSeedNode(SeedNode seedNode) {
        // 调用grpc接口新增节点信息
        if (!verifySeedNodeId(seedNode.getSeedNodeId())) {
            log.error("seed node ID error");
            throw new ArgumentException();
        }
        if (seedNodeMapper.querySeedNodeDetails(seedNode.getSeedNodeId()) != null) {
            throw new SeedNodeExists();
        }

        SysRpcApi.SeedPeer seedResp = seedClient.addSeedNode(seedNode.getSeedNodeId());
        seedNode.setInitFlag(seedResp.getIsDefault());
        seedNode.setConnStatus(seedResp.getConnState().getNumber());
        seedNodeMapper.insertSeedNode(seedNode);
    }

    /*@Override
    public void updateSeedNodeBatch(List<SeedNode> localSeedNodeList) {

    }*/

    @Override
    public void deleteSeedNode(String seedNodeId) {

        SeedNode seedNode = seedNodeMapper.querySeedNodeDetails(seedNodeId);
        if (seedNode == null) {
            throw new BizException(Errors.QueryRecordNotExist);
        }
        if (seedNode.getInitFlag()) {//内置
            throw new CannotDeleteInitSeedNode();
        }

        // 删除底层资源
        seedClient.deleteSeedNode(seedNodeId);
        // 删除数据
        seedNodeMapper.deleteSeedNode(seedNodeId);
    }

    @Override
    public SeedNode querySeedNodeDetails(String seedNodeId) {
        return seedNodeMapper.querySeedNodeDetails(seedNodeId);
    }

    @Override
    public Page<SeedNode> listSeedNode(int pageNumber, int pageSize) {
        Page<SeedNode> page = PageHelper.startPage(pageNumber, pageSize);
        seedNodeMapper.listSeedNode();
        return page;
    }

    @Override
    public void checkSeedNodeId(String seedNodeId) {
        if (!verifySeedNodeId(seedNodeId)) {
            throw new ArgumentException();
        }
        if (seedNodeMapper.querySeedNodeDetails(seedNodeId) != null) {
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
