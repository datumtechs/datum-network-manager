package com.platon.rosettanet.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.dao.DataNodeMapper;
import com.platon.rosettanet.admin.dao.entity.DataNode;
import com.platon.rosettanet.admin.grpc.client.DataProviderClient;
import com.platon.rosettanet.admin.service.DataNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lyf
 * @Description TODO
 * @date 2021/7/9 9:43
 */
@Service
public class DataNodeServiceImpl implements DataNodeService {

    @Autowired
    private DataNodeMapper dataNodeMapper;

    @Override
    public Page<DataNode> listNode(Integer pageNumber, Integer pageSize, String keyword) {
        Page<DataNode> taskPage = PageHelper.startPage(pageNumber, pageSize);
        List<DataNode> dataNodes = dataNodeMapper.listNode(keyword);
        return taskPage;
    }

    @Override
    public int addDataNode(DataNode dataNode) {
        dataNode.setRecCreateTime(LocalDateTime.now());
        return dataNodeMapper.insert(dataNode);
    }

    @Override
    public boolean checkDataNodeName(DataNode dataNode) {
        String id = dataNodeMapper.getDataNodeIdByName(dataNode.getHostName());
        // todo 非空判断工具类
        if (id != null && !id.equals(dataNode.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public int updateDataNode(DataNode dataNode) {
        dataNode.setRecUpdateTime(LocalDateTime.now());
        return dataNodeMapper.updateByNodeId(dataNode);
    }

    @Override
    public int deleteDataNode(String nodeId) {
        return dataNodeMapper.deleteByNodeId(nodeId);
    }
}
