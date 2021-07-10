package com.platon.rosettanet.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.dao.DataNodeMapper;
import com.platon.rosettanet.admin.dao.LocalOrgMapper;
import com.platon.rosettanet.admin.dao.entity.DataNode;
import com.platon.rosettanet.admin.dao.entity.LocalOrg;
import com.platon.rosettanet.admin.grpc.client.YarnClient;
import com.platon.rosettanet.admin.grpc.constant.GrpcConstant;
import com.platon.rosettanet.admin.grpc.entity.CommonResp;
import com.platon.rosettanet.admin.grpc.entity.FormatSetDataNodeResp;
import com.platon.rosettanet.admin.grpc.entity.QueryNodeResp;
import com.platon.rosettanet.admin.service.DataNodeService;
import com.platon.rosettanet.admin.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Resource
    private LocalOrgMapper localOrgMapper;


    @Override
    public Page<DataNode> listNode(Integer pageNumber, Integer pageSize, String keyword) {
        Page<DataNode> taskPage = PageHelper.startPage(pageNumber, pageSize);
        List<DataNode> dataNodes = dataNodeMapper.listNode(keyword);
        return taskPage;
    }

    @Override
    public int addDataNode(DataNode dataNode) {
        LocalOrg carrier = localOrgMapper.selectAvailableCarrier();
        if (carrier == null) {
            throw new ServiceException("无可用的调度服务");
        }
        log.info("新增数据节点，调度服务ip:" + carrier.getCarrierIP() + ",端口号：" + carrier.getCarrierPort());
        FormatSetDataNodeResp formatSetDataNodeResp = yarnClient.setDataNode(carrier.getCarrierIP(), carrier.getCarrierPort(), dataNode);
        if (GrpcConstant.GRPC_SUCCESS_CODE != formatSetDataNodeResp.getStatus()) {
            throw new ServiceException("调度服务调用失败");
        }
        dataNode.setNodeId(formatSetDataNodeResp.getNodeResp().getNodeId());
        dataNode.setConnStatus(formatSetDataNodeResp.getNodeResp().getConnStatus());
        dataNode.setIdentityId(LocalOrgIdentityCache.getIdentityId());
        dataNode.setRecCreateTime(LocalDateTime.now());
        return dataNodeMapper.insert(dataNode);
    }

    @Override
    public boolean checkDataNodeName(DataNode dataNode) {
        String id = dataNodeMapper.getDataNodeIdByName(dataNode.getHostName());
        if (StrUtil.isBlank(id) && !id.equals(dataNode.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public int updateDataNode(DataNode dataNode) {
        LocalOrg carrier = localOrgMapper.selectAvailableCarrier();
        if (carrier == null) {
            throw new ServiceException("无可用的调度服务");
        }
        FormatSetDataNodeResp formatSetDataNodeResp = yarnClient.updateDataNode(carrier.getCarrierIP(), carrier.getCarrierPort(), dataNode);
        log.info("修改数据节点，调度服务ip:" + carrier.getCarrierIP() + ",端口号：" + carrier.getCarrierPort());
        if (GrpcConstant.GRPC_SUCCESS_CODE != formatSetDataNodeResp.getStatus()) {
            throw new ServiceException("调度服务调用失败");
        }
        dataNode.setConnStatus(formatSetDataNodeResp.getNodeResp().getConnStatus());
        dataNode.setRecUpdateTime(LocalDateTime.now());
        return dataNodeMapper.updateByNodeId(dataNode);
    }

    @Override
    public int deleteDataNode(String nodeId) throws Exception {
        LocalOrg carrier = localOrgMapper.selectAvailableCarrier();
        if (carrier == null) {
            throw new ServiceException("无可用的调度服务");
        }
        log.info("删除数据节点,调度服务ip:" + carrier.getCarrierIP() + ",端口号：" + carrier.getCarrierPort());
        CommonResp commonResp = yarnClient.deleteDataNode(carrier.getCarrierIP(), carrier.getCarrierPort(), nodeId);
        if (GrpcConstant.GRPC_SUCCESS_CODE != commonResp.getStatus()) {
            throw new ServiceException("调度服务调用失败");
        }
        return dataNodeMapper.deleteByNodeId(nodeId);
    }

    public void refreshDataCodeTable() {
        LocalOrg carrier = localOrgMapper.selectAvailableCarrier();
        if (carrier == null) {
            log.info("获取数据节点列表,无可用的调度服务");
            return;
        }
        log.info("获取数据节点列表,调度服务ip:" + carrier.getCarrierIP() + ",端口号：" + carrier.getCarrierPort());
        QueryNodeResp resp = yarnClient.getDataNodeList(carrier.getCarrierIP(), carrier.getCarrierPort());
        if (GrpcConstant.GRPC_SUCCESS_CODE != resp.getStatus()) {
            log.info("获取数据节点列表,调度服务调用失败");
            return;
        }
        List<DataNode> dataNodeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dataNodeList)) {
            resp.getNodeRespList().forEach(item -> {
                DataNode dataNode = new DataNode();
                BeanUtils.copyProperties(item, dataNode);
                dataNodeList.add(dataNode);
            });
            dataNodeMapper.batchUpdate(dataNodeList);
        }

    }
}