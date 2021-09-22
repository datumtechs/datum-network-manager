package com.platon.metis.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.metis.admin.common.context.LocalOrgIdentityCache;
import com.platon.metis.admin.dao.DataNodeMapper;
import com.platon.metis.admin.dao.entity.DataNode;
import com.platon.metis.admin.grpc.client.YarnClient;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.entity.CommonResp;
import com.platon.metis.admin.grpc.entity.FormatSetDataNodeResp;
import com.platon.metis.admin.service.DataNodeService;
import com.platon.metis.admin.service.exception.ServiceException;
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
        FormatSetDataNodeResp formatSetDataNodeResp = yarnClient.setDataNode(dataNode);
        if (GrpcConstant.GRPC_SUCCESS_CODE != formatSetDataNodeResp.getStatus()) {
            throw new ServiceException("调度服务调用失败");
        }
        if (!checkDataNodeId(dataNode)) {
            throw new ServiceException("相同属性数据节点已存在");
        }
        dataNode.setNodeId(formatSetDataNodeResp.getNodeResp().getNodeId());
        dataNode.setConnStatus(formatSetDataNodeResp.getNodeResp().getConnStatus());
        dataNode.setIdentityId(LocalOrgIdentityCache.getIdentityId());
        dataNode.setRecCreateTime(LocalDateTime.now());
        return dataNodeMapper.insert(dataNode);
    }

    /**
     * 校验数据节点名称是否可用
     *
     * @param dataNode
     * @return true可用，false不可用
     */
    @Override
    public boolean checkDataNodeName(DataNode dataNode) {
        String dbNodeId = dataNodeMapper.getDataNodeIdByName(dataNode.getHostName());
        if (!StrUtil.isBlank(dbNodeId) && !dbNodeId.equals(dataNode.getNodeId())) {
            return false;
        }
        return true;
    }

    /**
     * 修改数据节点
     *
     * @param dataNode
     * @return
     */
    @Override
    public int updateDataNode(DataNode dataNode) {
        if (!checkDataNodeId(dataNode)) {
            throw new ServiceException("相同属性数据节点已存在");
        }
        FormatSetDataNodeResp formatSetDataNodeResp = yarnClient.updateDataNode(dataNode);
        if (GrpcConstant.GRPC_SUCCESS_CODE != formatSetDataNodeResp.getStatus()) {
            throw new ServiceException("调度服务调用失败");
        }
        dataNode.setConnStatus(formatSetDataNodeResp.getNodeResp().getConnStatus());
        dataNode.setRecUpdateTime(LocalDateTime.now());
        return dataNodeMapper.updateByNodeId(dataNode);
    }

    /**
     * 删除数据节点
     *
     * @param nodeId
     * @return
     */
    @Override
    public int deleteDataNode(String nodeId) {
        CommonResp commonResp = yarnClient.deleteDataNode(nodeId);
        if (GrpcConstant.GRPC_SUCCESS_CODE != commonResp.getStatus()) {
            throw new ServiceException("调度服务调用失败");
        }
        return dataNodeMapper.deleteByNodeId(nodeId);
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
        if (ObjectUtil.isNotNull(dataNode) && !dataNode.getNodeId().equals(queryDataNode.getNodeId())) {
            return false;
        }
        return true;
    }
}