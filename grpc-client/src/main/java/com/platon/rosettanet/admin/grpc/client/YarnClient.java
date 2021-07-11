package com.platon.rosettanet.admin.grpc.client;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platon.rosettanet.admin.common.exception.ApplicationException;
import com.platon.rosettanet.admin.dao.entity.DataNode;
import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.constant.GrpcConstant;
import com.platon.rosettanet.admin.grpc.entity.*;
import com.platon.rosettanet.admin.grpc.service.CommonMessage;
import com.platon.rosettanet.admin.grpc.service.YarnRpcMessage;
import com.platon.rosettanet.admin.grpc.service.YarnServiceGrpc;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/8 18:27
 * @Version
 * @Desc 系统服务客户端
 * java服务类：YarnServiceGrpc
 * proto文件：sys_rpc_api.proto
 */

@Component
@Slf4j
public class YarnClient {
    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;

    /**
     * 调度新增数据节点
     *
     * @param scheduleServerHost 调度服务ip
     * @param scheduleServerPort 调度服务端口号
     * @param dataNode           数据节点实体类
     * @return
     */
    public FormatSetDataNodeResp setDataNode(String scheduleServerHost, int scheduleServerPort, DataNode dataNode) {
        //1.获取rpc连接
        Channel channel = channelManager.buildChannel("localhost", 50051);
        //2.拼装request
        YarnRpcMessage.SetDataNodeRequest setDataNodeRequest = YarnRpcMessage.SetDataNodeRequest.newBuilder().
                setInternalIp(dataNode.getInternalIp()).
                setInternalPort(String.valueOf(dataNode.getInternalPort())).
                setExternalIp(dataNode.getExternalIp()).
                setExternalPort(String.valueOf(dataNode.getExternalPort())).build();
        //3.调用rpc,获取response
        YarnRpcMessage.SetDataNodeResponse setDataNodeResponse = YarnServiceGrpc.newBlockingStub(channel).setDataNode(setDataNodeRequest);
        //4.处理response
        FormatSetDataNodeResp resp = new FormatSetDataNodeResp();
        resp.setStatus(setDataNodeResponse.getStatus());
        resp.setMsg(setDataNodeResponse.getMsg());
        if (GrpcConstant.GRPC_SUCCESS_CODE == setDataNodeResponse.getStatus()) {
            YarnRpcMessage.YarnRegisteredPeerDetail resDataNode = setDataNodeResponse.getDataNode();
            if (ObjectUtil.isNotNull(resDataNode)) {
                RegisteredNodeResp nodeResp = new RegisteredNodeResp();
                nodeResp.setNodeId(resDataNode.getId());
                nodeResp.setConnStatus(String.valueOf(resDataNode.getConnState()));
                resp.setNodeResp(nodeResp);
            }
        }
        return resp;

    }

    /**
     * 调度修改数据节点
     *
     * @param scheduleServerHost 调度服务ip
     * @param scheduleServerPort 调度服务端口号
     * @param dataNode           数据节点实体类
     * @return
     */
    public FormatSetDataNodeResp updateDataNode(String scheduleServerHost, int scheduleServerPort, DataNode dataNode) {
        //1.获取rpc连接
        Channel channel = channelManager.buildChannel(scheduleServerHost, scheduleServerPort);
        //2.拼装request
        YarnRpcMessage.UpdateDataNodeRequest request = YarnRpcMessage.UpdateDataNodeRequest
                .newBuilder().setId(dataNode.getNodeId()).
                        setInternalIp(dataNode.getInternalIp()).
                        setInternalPort(String.valueOf(dataNode.getInternalPort())).
                        setExternalIp(dataNode.getExternalIp()).
                        setExternalPort(String.valueOf(dataNode.getExternalPort())).build();
        //3.调用rpc,获取response
        YarnRpcMessage.SetDataNodeResponse setDataNodeResponse = YarnServiceGrpc.newBlockingStub(channel).updateDataNode(request);
        //4.处理response
        FormatSetDataNodeResp resp = new FormatSetDataNodeResp();
        resp.setStatus(setDataNodeResponse.getStatus());
        resp.setMsg(setDataNodeResponse.getMsg());
        if (GrpcConstant.GRPC_SUCCESS_CODE == setDataNodeResponse.getStatus()) {
            YarnRpcMessage.YarnRegisteredPeerDetail resDataNode = setDataNodeResponse.getDataNode();
            if (ObjectUtil.isNotNull(resDataNode)) {
                RegisteredNodeResp nodeResp = new RegisteredNodeResp();
                nodeResp.setNodeId(resDataNode.getId());
                nodeResp.setConnStatus(String.valueOf(resDataNode.getConnState()));
                resp.setNodeResp(nodeResp);
            }
        }
        return resp;
    }

    /**
     * 调度删除数据节点
     *
     * @param scheduleServerHost 调度服务ip
     * @param scheduleServerPort 调度服务端口号
     * @return
     */
    public CommonResp deleteDataNode(String scheduleServerHost, int scheduleServerPort, String id) {
        //1.获取rpc连接
        Channel channel = channelManager.buildChannel(scheduleServerHost, scheduleServerPort);
        //2.拼装request
        CommonMessage.DeleteRegisteredNodeRequest request = CommonMessage.DeleteRegisteredNodeRequest.newBuilder().setId(id).build();
        //3.调用rpc,获取response
        CommonMessage.SimpleResponseCode simpleResponseCode = YarnServiceGrpc.newBlockingStub(channel).deleteDataNode(request);
        //4.处理response
        CommonResp resp = new CommonResp();
        resp.setMsg(simpleResponseCode.getMsg());
        resp.setStatus(simpleResponseCode.getStatus());
        return resp;
    }

    /**
     * 调度获取数据节点列表
     *
     * @param scheduleServerHost 调度服务ip
     * @param scheduleServerPort 调度服务端口号
     * @return
     */
    public QueryNodeResp getDataNodeList(String scheduleServerHost, int scheduleServerPort) {
        //1.获取rpc连接
        Channel channel = channelManager.buildChannel(scheduleServerHost, scheduleServerPort);
        //2.拼装request
        CommonMessage.EmptyGetParams emptyGetParams = CommonMessage.EmptyGetParams.getDefaultInstance();
        //3.调用rpc,获取response
        YarnRpcMessage.GetRegisteredNodeListResponse dataNodeListResp = YarnServiceGrpc.newBlockingStub(channel).getDataNodeList(emptyGetParams);
        //4.处理response
        QueryNodeResp queryNodeResp = new QueryNodeResp();
        queryNodeResp.setMsg(dataNodeListResp.getMsg());
        queryNodeResp.setStatus(dataNodeListResp.getStatus());
        List<RegisteredNodeResp> nodeRespList = new ArrayList<>();
        if (GrpcConstant.GRPC_SUCCESS_CODE == dataNodeListResp.getStatus()) {
            dataNodeListResp.getNodesList().forEach(item -> {
                RegisteredNodeResp nodeResp = new RegisteredNodeResp();
                nodeResp.setNodeId(item.getNodeDetail().getId());
                nodeResp.setInternalIp(item.getNodeDetail().getInternalIp());
                String internalPort = item.getNodeDetail().getInternalPort();
                nodeResp.setInternalPort(internalPort == null ? null : Integer.valueOf(internalPort));
                nodeResp.setExternalIp(item.getNodeDetail().getExternalIp());
                String externalPort = item.getNodeDetail().getExternalPort();
                nodeResp.setExternalPort(externalPort == null ? null : Integer.valueOf(externalPort));
                nodeResp.setConnStatus(String.valueOf(item.getNodeDetail().getConnState()));
                nodeRespList.add(nodeResp);
            });
        }
        return queryNodeResp;
    }


    /**
     * 根据需要上传的文件大小和类型，获取可用的数据节点信息
     */
    public YarnAvailableDataNodeResp getAvailableDataNode(long fileSize,String fileType) {
        if(StrUtil.isBlank(fileType)){
            fileType = "csv";
        }
        //1.获取rpc连接
        Channel channel = channelManager.getScheduleServer();
        //2.拼装request
        YarnRpcMessage.QueryAvailableDataNodeRequest request = YarnRpcMessage.QueryAvailableDataNodeRequest
                .newBuilder()
                .setFileSize(fileSize)
                .setFileType(fileType)
                .build();
        //3.调用rpc,获取response
        YarnRpcMessage.QueryAvailableDataNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).queryAvailableDataNode(request);
        //4.处理response
        if (StrUtil.isBlank(response.getIp()) || StrUtil.isBlank(response.getPort())) {
            throw new ApplicationException(StrUtil.format("获取可用数据节点信息失败：ip:{},port:{}",
                    response.getIp(),
                    response.getPort()));
        }
        /**
         * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
         * 故将response转换后再放给service类使用
         */
        YarnAvailableDataNodeResp node = new YarnAvailableDataNodeResp();
        node.setIp(response.getIp());
        node.setPort(Integer.parseInt(response.getPort()));
        return node;
    }

    /**
     * 查询需要下载的目标原始文件所在的 数据服务信息和文件的完整相对路径
     */
    public YarnQueryFilePositionResp queryFilePosition(String fileId){
        //1.获取rpc连接
        Channel channel = channelManager.getScheduleServer();
        //2.拼装request
        YarnRpcMessage.QueryFilePositionRequest request = YarnRpcMessage.QueryFilePositionRequest
                .newBuilder()
                .setOriginId(fileId)
                .build();
        //3.调用rpc,获取response
        YarnRpcMessage.QueryFilePositionResponse response = YarnServiceGrpc.newBlockingStub(channel).queryFilePosition(request);
        //4.处理response
        if (StrUtil.isBlank(response.getIp())
                || StrUtil.isBlank(response.getPort())
                || StrUtil.isBlank(response.getFilePath())) {
            throw new ApplicationException(StrUtil.format("获取可用数据节点信息失败：ip:{},port:{},filePath:{}",
                    response.getIp(),
                    response.getPort(),
                    response.getFilePath()));
        }
        /**
         * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
         * 故将response转换后再放给service类使用
         */
        YarnQueryFilePositionResp resp = new YarnQueryFilePositionResp();
        resp.setIp(response.getIp());
        resp.setPort(Integer.parseInt(response.getPort()));
        resp.setFilePath(response.getFilePath());
        return resp;
    }
}
