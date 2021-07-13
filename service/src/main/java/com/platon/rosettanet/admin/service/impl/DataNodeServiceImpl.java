package com.platon.rosettanet.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.DataNodeMapper;
import com.platon.rosettanet.admin.dao.entity.DataNode;
import com.platon.rosettanet.admin.grpc.client.YarnClient;
import com.platon.rosettanet.admin.grpc.constant.GrpcConstant;
import com.platon.rosettanet.admin.grpc.entity.CommonResp;
import com.platon.rosettanet.admin.grpc.entity.FormatSetDataNodeResp;
import com.platon.rosettanet.admin.service.DataNodeService;
import com.platon.rosettanet.admin.service.exception.ServiceException;
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
}