package com.platon.datum.admin.grpc.client;

import com.google.protobuf.Empty;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.dao.entity.DataFile;
import com.platon.datum.admin.dao.entity.DataNode;
import com.platon.datum.admin.grpc.carrier.api.SysRpcApi;
import com.platon.datum.admin.grpc.carrier.api.YarnServiceGrpc;
import com.platon.datum.admin.grpc.carrier.types.Common;
import com.platon.datum.admin.grpc.channel.SimpleChannelManager;
import com.platon.datum.admin.grpc.common.constant.CarrierEnum;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import com.platon.datum.admin.grpc.entity.RegisteredNodeResp;
import com.platon.datum.admin.grpc.entity.YarnAvailableDataNodeResp;
import com.platon.datum.admin.grpc.entity.YarnGetNodeInfoResp;
import com.platon.datum.admin.grpc.entity.YarnQueryFilePositionResp;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private SimpleChannelManager channelManager;

    /**
     * 调度新增数据节点
     *
     * @param dataNode 数据节点实体类
     * @return
     */
    public RegisteredNodeResp setDataNode(DataNode dataNode) {
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        SysRpcApi.SetDataNodeRequest setDataNodeRequest = SysRpcApi.SetDataNodeRequest.newBuilder().
                setInternalIp(dataNode.getInternalIp()).
                setInternalPort(String.valueOf(dataNode.getInternalPort())).
                setExternalIp(dataNode.getExternalIp()).
                setExternalPort(String.valueOf(dataNode.getExternalPort())).build();
        log.debug("setDataNode,request:{}", setDataNodeRequest);
        //3.调用rpc,获取response
        SysRpcApi.SetDataNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).setDataNode(setDataNodeRequest);
        log.debug("setDataNode,response:{}", response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        SysRpcApi.YarnRegisteredPeerDetail resDataNode = response.getNode();
        if (resDataNode != null) {
            RegisteredNodeResp nodeResp = new RegisteredNodeResp();
            nodeResp.setNodeId(resDataNode.getId());
            nodeResp.setConnStatus(resDataNode.getConnState().getNumber());
            return nodeResp;
        }
        return null;

    }

    /**
     * 调度修改数据节点
     *
     * @param dataNode 数据节点实体类
     * @return
     */
    public RegisteredNodeResp updateDataNode(DataNode dataNode) {
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        SysRpcApi.UpdateDataNodeRequest request = SysRpcApi.UpdateDataNodeRequest
                .newBuilder().setId(dataNode.getNodeId()).
                setInternalIp(dataNode.getInternalIp()).
                setInternalPort(String.valueOf(dataNode.getInternalPort())).
                setExternalIp(dataNode.getExternalIp()).
                setExternalPort(String.valueOf(dataNode.getExternalPort())).build();
        log.debug("updateDataNode,request:{}", request);
        //3.调用rpc,获取response
        SysRpcApi.SetDataNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).updateDataNode(request);
        log.debug("updateDataNode,response:{}", response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        SysRpcApi.YarnRegisteredPeerDetail resDataNode = response.getNode();
        if (resDataNode != null) {
            RegisteredNodeResp nodeResp = new RegisteredNodeResp();
            nodeResp.setNodeId(resDataNode.getId());
            nodeResp.setConnStatus(resDataNode.getConnState().getNumber());
            return nodeResp;
        }
        return null;

    }

    /**
     * 调度删除数据节点
     *
     * @return
     */
    public void deleteDataNode(String id) {
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        SysRpcApi.DeleteRegisteredNodeRequest request = SysRpcApi.DeleteRegisteredNodeRequest.newBuilder().setId(id).build();
        log.debug("deleteDataNode,request:{}", request);
        //3.调用rpc,获取response
        Common.SimpleResponse response = YarnServiceGrpc.newBlockingStub(channel).deleteDataNode(request);
        log.debug("deleteDataNode,response:{}", response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }

    /**
     * 调度获取数据节点列表
     *
     * @return
     */
    public List<DataNode> getLocalDataNodeList() {
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        Empty emptyGetParams = Empty.newBuilder().build();
        log.debug("getLocalDataNodeList,request:{}", emptyGetParams);
        //3.调用rpc,获取response
        SysRpcApi.GetRegisteredNodeListResponse response = YarnServiceGrpc.newBlockingStub(channel).getDataNodeList(emptyGetParams);
        log.debug("getLocalDataNodeList,response:{}", response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return convertToLocalDataNodeList(response.getNodesList());

    }

    private List<DataNode> convertToLocalDataNodeList(List<SysRpcApi.YarnRegisteredPeer> nodeList) {
        return nodeList.parallelStream().map(node -> {
            DataNode dataNode = new DataNode();
            dataNode.setNodeId(node.getNodeDetail().getId());
            dataNode.setNodeName("DataStorageNode_" + node.getNodeDetail().getInternalIp() + "_" + StringUtils.trimToEmpty(node.getNodeDetail().getInternalPort()));
            dataNode.setInternalIp(node.getNodeDetail().getInternalIp());
            dataNode.setInternalPort(StringUtils.isEmpty(node.getNodeDetail().getInternalPort()) ? null : Integer.valueOf(node.getNodeDetail().getInternalPort()));
            dataNode.setExternalIp(node.getNodeDetail().getExternalIp());
            dataNode.setExternalPort(StringUtils.isEmpty(node.getNodeDetail().getExternalPort()) ? null : Integer.valueOf(node.getNodeDetail().getExternalPort()));
            dataNode.setConnStatus(node.getNodeDetail().getConnState().getNumber());
            return dataNode;
        }).collect(Collectors.toList());
    }

    /**
     * 根据需要上传的文件大小和类型，获取可用的数据节点信息
     */
    public YarnAvailableDataNodeResp getAvailableDataNode(long fileSize, DataFile.FileTypeEnum fileType) {
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        SysRpcApi.QueryAvailableDataNodeRequest request = SysRpcApi.QueryAvailableDataNodeRequest
                .newBuilder()
                .setDataSize(fileSize)
                .setDataType(CarrierEnum.OrigindataType.forNumber(fileType.getCode()))
                .build();
        log.debug("getAvailableDataNode,request:{}", request);
        //3.调用rpc,获取response
        SysRpcApi.QueryAvailableDataNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).queryAvailableDataNode(request);
        log.debug("getAvailableDataNode,response:{}", response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (StringUtils.isBlank(response.getInformation().getIp()) || StringUtils.isBlank(response.getInformation().getPort())) {
            throw new CallGrpcServiceFailed("cannot find a available data node.");
        }
        /**
         * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
         * 故将response转换后再放给service类使用
         */
        YarnAvailableDataNodeResp node = new YarnAvailableDataNodeResp();
        node.setIp(response.getInformation().getIp());
        node.setPort(Integer.parseInt(response.getInformation().getPort()));
        return node;

    }

    /**
     * 查询需要下载的目标原始文件所在的 数据服务信息和文件的完整相对路径
     */
    public YarnQueryFilePositionResp queryFilePosition(String fileId) {
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        SysRpcApi.QueryFilePositionRequest request = SysRpcApi.QueryFilePositionRequest
                .newBuilder()
                .setOriginId(fileId)
                .build();
        log.debug("queryFilePosition,request:{}", request);
        //3.调用rpc,获取response
        SysRpcApi.QueryFilePositionResponse response = YarnServiceGrpc.newBlockingStub(channel).queryFilePosition(request);
        log.debug("queryFilePosition,response:{}", response);
        //4.处理response
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (StringUtils.isBlank(response.getInformation().getIp()) || StringUtils.isBlank(response.getInformation().getPort()) || StringUtils.isBlank(response.getInformation().getDataPath())) {
            throw new CallGrpcServiceFailed("cannot find the data node that file located.");
        }

        /**
         * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
         * 故将response转换后再放给service类使用
         */
        YarnQueryFilePositionResp resp = new YarnQueryFilePositionResp();
        resp.setIp(response.getInformation().getIp());
        resp.setPort(Integer.parseInt(response.getInformation().getPort()));
        resp.setFilePath(response.getInformation().getDataPath());
        return resp;

    }


    /**
     * 尝试连接调度服务,连通则返回true，否则返回false
     */
    public boolean connectScheduleServer(String scheduleIP, int schedulePort) {
        //1.获取rpc连接
        ManagedChannel channel = null;
        try {
            channel = channelManager.buildChannel(scheduleIP, schedulePort);
            //2.拼装request
            Empty request = Empty.newBuilder().build();
            log.debug("connectScheduleServer,request:{}", request);
            //3.调用rpc,获取response
            SysRpcApi.GetNodeInfoResponse response = YarnServiceGrpc.newBlockingStub(channel).getNodeInfo(request);
            log.debug("connectScheduleServer,response:{}", response);
            //4.处理response
            if (response == null) {
                throw new CallGrpcServiceFailed();
            } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
                throw new CallGrpcServiceFailed(response.getMsg());
            }

        } catch (Throwable throwable) {
            return false;
        } finally {
            channelManager.closeChannel(channel);
        }
        return true;

    }

    /**
     * 查看自身调度服务信息
     *
     * @param scheduleIP   调度服务ip
     * @param schedulePort 调度服务端口
     */
    public YarnGetNodeInfoResp getNodeInfo(String scheduleIP, int schedulePort) {
        //1.获取rpc连接
        ManagedChannel channel = null;
        try {
            channel = channelManager.buildChannel(scheduleIP, schedulePort);
            //2.拼装request
            Empty request = Empty.newBuilder().build();
            log.debug("getNodeInfo,request:{}", request);
            //3.调用rpc,获取response
            SysRpcApi.GetNodeInfoResponse response = YarnServiceGrpc.newBlockingStub(channel).getNodeInfo(request);
            log.debug("getNodeInfo,response:{}", response);
            //4.处理response
            if (response == null) {
                throw new CallGrpcServiceFailed();
            } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
                throw new CallGrpcServiceFailed(response.getMsg());
            }
            /**
             * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
             * 故将response转换后再放给service类使用
             */
            YarnGetNodeInfoResp resp = new YarnGetNodeInfoResp();
            SysRpcApi.YarnNodeInfo information = response.getInformation();
            resp.setNodeId(information.getNodeId());
            resp.setInternalIp(information.getInternalIp());
            resp.setInternalPort(information.getInternalPort());
            resp.setExternalIp(information.getExternalIp());
            resp.setExternalPort(information.getExternalPort());
            resp.setIdentityType(information.getIdentityType());
            resp.setIdentityId(information.getIdentityId());
            resp.setState(information.getState().getNumber());
            resp.setName(information.getName());
            resp.setLocalBootstrapNode(information.getLocalBootstrapNode());
            resp.setLocalMultiAddr(information.getLocalMultiAddr());
            resp.setStatus(GrpcConstant.GRPC_SUCCESS_CODE);
            resp.setConnCount(information.getRelatePeers());
            resp.setMsg("success");
            return resp;
        } finally {
            channelManager.closeChannel(channel);
        }
    }

    /**
     * v 0.4.0 生成当前组织内置系统钱包地址 (见证人代理钱包, 全局只有一个)
     *
     * @return 返回生成的钱包地址
     */
    public String generateObServerProxyWalletAddress(String scheduleIP, int schedulePort) {
        //1.获取rpc连接
        ManagedChannel channel = null;
        try {
            channel = channelManager.buildChannel(scheduleIP, schedulePort);
            //2.拼装request
            Empty request = Empty.newBuilder().build();
            log.debug("generateObServerProxyWalletAddress,request:{}", request);
            //3.调用rpc,获取response
            SysRpcApi.GenerateObServerProxyWalletAddressResponse response =
                    YarnServiceGrpc.newBlockingStub(channel).generateObServerProxyWalletAddress(request);
            log.debug("generateObServerProxyWalletAddress,response:{}", response);
            //4.处理response
            if (response == null) {
                throw new CallGrpcServiceFailed();
            } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
                throw new CallGrpcServiceFailed(response.getMsg());
            }
            /**
             * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
             * 故将response转换后再放给service类使用
             */
            String address = response.getAddress();
            return address;
        } finally {
            channelManager.closeChannel(channel);
        }
    }
}