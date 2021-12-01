package com.platon.metis.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.metis.admin.common.context.LocalOrgIdentityCache;
import com.platon.metis.admin.dao.LocalDataNodeMapper;
import com.platon.metis.admin.dao.entity.LocalDataNode;
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
    private LocalDataNodeMapper localDataNodeMapper;
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
    public Page<LocalDataNode> listNode(Integer pageNumber, Integer pageSize, String keyword) {
        Page<LocalDataNode> taskPage = PageHelper.startPage(pageNumber, pageSize);
        List<LocalDataNode> localDataNodes = localDataNodeMapper.listNode(keyword);
        return taskPage;
    }

    /**
     * 新增数据节点
     *
     * @param localDataNode
     * @return
     */
    @Override
    public int addDataNode(LocalDataNode localDataNode) {
        FormatSetDataNodeResp formatSetDataNodeResp = yarnClient.setDataNode(localDataNode);
        if (GrpcConstant.GRPC_SUCCESS_CODE != formatSetDataNodeResp.getStatus()) {
            throw new ServiceException("调度服务调用失败");
        }
        if (!checkDataNodeId(localDataNode)) {
            throw new ServiceException("相同属性数据节点已存在");
        }
        localDataNode.setNodeId(formatSetDataNodeResp.getNodeResp().getNodeId());
        localDataNode.setConnStatus(formatSetDataNodeResp.getNodeResp().getConnStatus());
        localDataNode.setIdentityId(LocalOrgIdentityCache.getIdentityId());
        localDataNode.setRecCreateTime(LocalDateTime.now());
        return localDataNodeMapper.insert(localDataNode);
    }

    /**
     * 校验数据节点名称是否可用
     *
     * @param localDataNode
     * @return true可用，false不可用
     */
    @Override
    public boolean checkDataNodeName(LocalDataNode localDataNode) {
        String dbNodeId = localDataNodeMapper.getDataNodeIdByName(localDataNode.getNodeName());
        if (!StrUtil.isBlank(dbNodeId) && !dbNodeId.equals(localDataNode.getNodeId())) {
            return false;
        }
        return true;
    }

    /**
     * 修改数据节点
     *
     * @param localDataNode
     * @return
     */
    @Override
    public int updateDataNode(LocalDataNode localDataNode) {
        if (!checkDataNodeId(localDataNode)) {
            throw new ServiceException("相同属性数据节点已存在");
        }
        FormatSetDataNodeResp formatSetDataNodeResp = yarnClient.updateDataNode(localDataNode);
        if (GrpcConstant.GRPC_SUCCESS_CODE != formatSetDataNodeResp.getStatus()) {
            throw new ServiceException("调度服务调用失败");
        }
        localDataNode.setConnStatus(formatSetDataNodeResp.getNodeResp().getConnStatus());
        localDataNode.setRecUpdateTime(LocalDateTime.now());
        return localDataNodeMapper.update(localDataNode);
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
        return localDataNodeMapper.deleteByPrimaryKey(nodeId);
    }

    /**
     * 校验具有相同属性的其他数据节点是否已存在
     *
     * @param queryLocalDataNode 条件参数
     * @return true校验通过，不存在，false校验不通过，已存在
     */
    public boolean checkDataNodeId(LocalDataNode queryLocalDataNode) {
        LocalDataNode localDataNode = localDataNodeMapper.selectByProperties(queryLocalDataNode);
        //数据库存在符合条件的数据，且nodeId与当前数据不一致
        if (ObjectUtil.isNotNull(localDataNode) && !localDataNode.getNodeId().equals(queryLocalDataNode.getNodeId())) {
            return false;
        }
        return true;
    }
}