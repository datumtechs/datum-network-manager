package com.platon.datum.admin.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.DataHostExists;
import com.platon.datum.admin.dao.DataNodeMapper;
import com.platon.datum.admin.dao.entity.DataNode;
import com.platon.datum.admin.grpc.client.YarnClient;
import com.platon.datum.admin.grpc.entity.RegisteredNodeResp;
import com.platon.datum.admin.service.DataNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lyf
 * @Description TODO
 * @date 2021/7/9 9:43
 */
@Slf4j
@Service
public class DataNodeServiceImpl implements DataNodeService {

    @Resource
    private DataNodeMapper dataNodeMapper;
    @Resource
    private YarnClient yarnClient;


    /**
     * 根据关键字分页查询数据节点
     *
     * @param pageNumber 起始页号
     * @param pageSize   每页数据条数
     * @param keyword    搜索关键字
     * @return
     */
    @Override
    public Page<DataNode> listNode(Integer pageNumber, Integer pageSize, String keyword) {
        Page<DataNode> taskPage = PageHelper.startPage(pageNumber, pageSize);
        List<DataNode> dataNodes = dataNodeMapper.listNode(keyword);
        return taskPage;
    }

    /**
     * 新增数据节点
     *
     * @param dataNode
     * @return
     */
    @Override
    public int addDataNode(DataNode dataNode) {
        if (!checkDataNodeId(dataNode)) {
            throw new DataHostExists();
        }

        RegisteredNodeResp response = yarnClient.setDataNode(dataNode);

        dataNode.setNodeId(response.getNodeId());
        dataNode.setConnStatus(response.getConnStatus());
        //dataNode.setIdentityId(OrgCache.getLocalOrgIdentityId());
        dataNode.setRecCreateTime(LocalDateTime.now());
        return dataNodeMapper.insert(dataNode);
    }

    /**
     * 校验数据节点名称是否可用
     *
     * @param dataNode
     * @return true可用，false不可用
     */
    /*@Override
    public boolean checkDataNodeName(DataNode dataNode) {
        String dbNodeId = dataNodeMapper.getDataNodeIdByName(dataNode.getNodeName());
        if (!StringUtils.isBlank(dbNodeId) && !dbNodeId.equals(dataNode.getNodeId())) {
            return false;
        }
        return true;
    }*/

    /**
     * 修改数据节点
     *
     * @param dataNode
     * @return
     */
    @Override
    public int updateDataNode(DataNode dataNode) {
        if (!checkDataNodeId(dataNode)) {
            throw new DataHostExists();
        }
        RegisteredNodeResp response = yarnClient.updateDataNode(dataNode);
        dataNode.setConnStatus(response.getConnStatus());
        dataNode.setRecUpdateTime(LocalDateTime.now());
        return dataNodeMapper.update(dataNode);
    }

    /**
     * 删除数据节点
     *
     * @param nodeId
     * @return
     */
    @Override
    public int deleteDataNode(String nodeId) {
        yarnClient.deleteDataNode(nodeId);
        return dataNodeMapper.deleteByPrimaryKey(nodeId);
    }

    @Override
    public DataNode findLocalDataNodeByName(String nodeName) {
        return dataNodeMapper.findLocalDataNodeByName(nodeName);
    }

    @Override
    public void updateLocalDataNodeName(String nodeId, String nodeName) {
        dataNodeMapper.updateLocalDataNodeName(nodeId, nodeName);
    }

    /**
     * 校验具有相同属性的其他数据节点是否已存在
     *
     * @param queryDataNode 条件参数
     * @return true校验通过，不存在，false校验不通过，已存在
     */
    public boolean checkDataNodeId(DataNode queryDataNode) {
        DataNode dataNode = dataNodeMapper.selectByProperties(queryDataNode);
        //数据库存在符合条件的数据，且nodeId与当前数据不一致
        if (dataNode!=null && !dataNode.getNodeId().equals(queryDataNode.getNodeId())) {
            return false;
        }
        return true;
    }
}