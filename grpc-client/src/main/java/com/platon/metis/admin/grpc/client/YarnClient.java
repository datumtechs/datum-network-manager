package com.platon.metis.admin.grpc.client;

import com.google.protobuf.Empty;
import com.platon.metis.admin.common.exception.CallGrpcServiceFailed;
import com.platon.metis.admin.dao.entity.LocalDataFile;
import com.platon.metis.admin.dao.entity.LocalDataNode;
import com.platon.metis.admin.grpc.channel.SimpleChannelManager;
import com.platon.metis.admin.grpc.entity.RegisteredNodeResp;
import com.platon.metis.admin.grpc.entity.YarnAvailableDataNodeResp;
import com.platon.metis.admin.grpc.entity.YarnGetNodeInfoResp;
import com.platon.metis.admin.grpc.entity.YarnQueryFilePositionResp;
import com.platon.metis.admin.grpc.service.YarnRpcMessage;
import com.platon.metis.admin.grpc.service.YarnServiceGrpc;
import com.platon.metis.admin.grpc.types.Base;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.platon.metis.admin.grpc.constant.GrpcConstant.GRPC_SUCCESS_CODE;

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
     * @param dataNode           数据节点实体类
     * @return
     */
    public RegisteredNodeResp setDataNode(LocalDataNode dataNode) {
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        YarnRpcMessage.SetDataNodeRequest setDataNodeRequest = YarnRpcMessage.SetDataNodeRequest.newBuilder().
                setInternalIp(dataNode.getInternalIp()).
                setInternalPort(String.valueOf(dataNode.getInternalPort())).
                setExternalIp(dataNode.getExternalIp()).
                setExternalPort(String.valueOf(dataNode.getExternalPort())).build();
        log.debug("调用新增数据节点调度服务,参数:{}",setDataNodeRequest);
        //3.调用rpc,获取response
        YarnRpcMessage.SetDataNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).setDataNode(setDataNodeRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        YarnRpcMessage.YarnRegisteredPeerDetail resDataNode = response.getNode();
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
     * @param dataNode           数据节点实体类
     * @return
     */
    public RegisteredNodeResp updateDataNode(LocalDataNode dataNode) {
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        YarnRpcMessage.UpdateDataNodeRequest request = YarnRpcMessage.UpdateDataNodeRequest
                .newBuilder().setId(dataNode.getNodeId()).
                        setInternalIp(dataNode.getInternalIp()).
                        setInternalPort(String.valueOf(dataNode.getInternalPort())).
                        setExternalIp(dataNode.getExternalIp()).
                        setExternalPort(String.valueOf(dataNode.getExternalPort())).build();
        log.debug("调用修改数据节点调度服务,参数:{}",request);
        //3.调用rpc,获取response
        YarnRpcMessage.SetDataNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).updateDataNode(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        YarnRpcMessage.YarnRegisteredPeerDetail resDataNode = response.getNode();
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
     * @return
     */
    public void deleteDataNode(String id) {
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        YarnRpcMessage.DeleteRegisteredNodeRequest request = YarnRpcMessage.DeleteRegisteredNodeRequest.newBuilder().setId(id).build();
        log.debug("调用删除数据节点调度服务,参数:{}",request);
        //3.调用rpc,获取response
        Base.SimpleResponse response = YarnServiceGrpc.newBlockingStub(channel).deleteDataNode(request);
        log.debug("调用删除数据节点调度服务,响应结果:{}",response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }

    /**
     * 调度获取数据节点列表
     * @return
     */
    public List<LocalDataNode> getLocalDataNodeList() {
        log.debug("从carrier查询数据节点列表");

        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        Empty emptyGetParams = Empty.newBuilder().build();

        //3.调用rpc,获取response
        YarnRpcMessage.GetRegisteredNodeListResponse response = YarnServiceGrpc.newBlockingStub(channel).getDataNodeList(emptyGetParams);

        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        log.debug("从carrier查询数据节点列表，数量：{}", response.getNodesList().size());
        return convertToLocalDataNodeList(response.getNodesList());

    }

    private List<LocalDataNode> convertToLocalDataNodeList(List<YarnRpcMessage.YarnRegisteredPeer> nodeList) {
        return nodeList.parallelStream().map(node -> {
            LocalDataNode localDataNode = new LocalDataNode();
            localDataNode.setNodeId(node.getNodeDetail().getId());
            localDataNode.setNodeName("DataStorageNode_"+ node.getNodeDetail().getInternalIp() + "_" + StringUtils.trimToEmpty(node.getNodeDetail().getInternalPort()));
            localDataNode.setInternalIp(node.getNodeDetail().getInternalIp());
            localDataNode.setInternalPort(StringUtils.isEmpty(node.getNodeDetail().getInternalPort()) ? null : Integer.valueOf(node.getNodeDetail().getInternalPort()));
            localDataNode.setExternalIp(node.getNodeDetail().getExternalIp());
            localDataNode.setExternalPort(StringUtils.isEmpty(node.getNodeDetail().getExternalPort()) ? null : Integer.valueOf(node.getNodeDetail().getExternalPort()));
            localDataNode.setConnStatus(node.getNodeDetail().getConnState().getNumber());
            return localDataNode;
        }).collect(Collectors.toList());
    }

    /**
     * 根据需要上传的文件大小和类型，获取可用的数据节点信息
     */
    public YarnAvailableDataNodeResp getAvailableDataNode(long fileSize, LocalDataFile.FileTypeEnum fileType){

        log.debug("从carrier查询可用的数据节点");

        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        YarnRpcMessage.QueryAvailableDataNodeRequest request = YarnRpcMessage.QueryAvailableDataNodeRequest
                .newBuilder()
                .setFileSize(fileSize)
                .setFileTypeValue(fileType.getCode())
                .build();
        //3.调用rpc,获取response
        YarnRpcMessage.QueryAvailableDataNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).queryAvailableDataNode(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if (StringUtils.isBlank(response.getInformation().getIp()) || StringUtils.isBlank(response.getInformation().getPort())) {
            log.error("cannot find a available data node.");
            throw new CallGrpcServiceFailed();
        }
        /**
         * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
         * 故将response转换后再放给service类使用
         */
        YarnAvailableDataNodeResp node = new YarnAvailableDataNodeResp();
        node.setIp(response.getInformation().getIp());
        node.setPort(Integer.parseInt(response.getInformation().getPort()));

        log.debug("从carrier查询可用的数据节点， node:{}", node);
        return node;

    }

    /**
     * 查询需要下载的目标原始文件所在的 数据服务信息和文件的完整相对路径
     */
    public YarnQueryFilePositionResp queryFilePosition(String fileId){
        log.debug("从carrier查询文件路径， fileId:{}", fileId);
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();
        //2.拼装request
        YarnRpcMessage.QueryFilePositionRequest request = YarnRpcMessage.QueryFilePositionRequest
                .newBuilder()
                .setOriginId(fileId)
                .build();
        //3.调用rpc,获取response
        YarnRpcMessage.QueryFilePositionResponse response = YarnServiceGrpc.newBlockingStub(channel).queryFilePosition(request);
        //4.处理response
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if (StringUtils.isBlank(response.getInformation().getIp()) || StringUtils.isBlank(response.getInformation().getPort()) || StringUtils.isBlank(response.getInformation().getDataPath())) {
            log.error("cannot find the data node that file located.");
            throw new CallGrpcServiceFailed();
        }

        /**
         * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
         * 故将response转换后再放给service类使用
         */
        YarnQueryFilePositionResp resp = new YarnQueryFilePositionResp();
        resp.setIp(response.getInformation().getIp());
        resp.setPort(Integer.parseInt(response.getInformation().getPort()));
        resp.setFilePath(response.getInformation().getDataPath());

        log.debug("从carrier查询文件路径， fileInfo:{}", resp);
        return resp;

    }


    /**
     * 尝试连接调度服务,连通则返回true，否则返回false
     */
    public boolean connectScheduleServer(String scheduleIP,int schedulePort){
        //1.获取rpc连接
        ManagedChannel channel = null;
        try{
            channel = channelManager.buildChannel(scheduleIP, schedulePort);
            //2.拼装request
            Empty request = Empty.newBuilder().build();
            //3.调用rpc,获取response
            YarnRpcMessage.GetNodeInfoResponse response = YarnServiceGrpc.newBlockingStub(channel).getNodeInfo(request);

            //4.处理response
            if (response == null) {
                throw new CallGrpcServiceFailed();
            }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
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
     * @param scheduleIP 调度服务ip
     * @param schedulePort 调度服务端口
     */
    public YarnGetNodeInfoResp getNodeInfo(String scheduleIP,int schedulePort){
        log.debug("从carrier查询调度服务本身状态， ip{}:port{}", scheduleIP,schedulePort);
        //1.获取rpc连接
        ManagedChannel channel = null;
        try{
            channel = channelManager.buildChannel(scheduleIP, schedulePort);
            //2.拼装request
            Empty request = Empty.newBuilder().build();
            //3.调用rpc,获取response
            YarnRpcMessage.GetNodeInfoResponse response = YarnServiceGrpc.newBlockingStub(channel).getNodeInfo(request);

            //4.处理response
            if (response == null) {
                throw new CallGrpcServiceFailed();
            }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
                throw new CallGrpcServiceFailed(response.getMsg());
            }
            /**
             * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
             * 故将response转换后再放给service类使用
             */
            YarnGetNodeInfoResp resp = new YarnGetNodeInfoResp();
            YarnRpcMessage.YarnNodeInfo information = response.getInformation();
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
            resp.setStatus(GRPC_SUCCESS_CODE);
            resp.setConnCount(information.getRelatePeers());
            resp.setMsg("成功");
            return resp;
        } finally {
            channelManager.closeChannel(channel);
        }
    }

    /**
     * v 0.4.0 生成当前组织内置系统钱包地址 (见证人代理钱包, 全局只有一个)
     * @return 返回生成的钱包地址
     */
    public String generateObServerProxyWalletAddress(String scheduleIP,int schedulePort){
        //1.获取rpc连接
        ManagedChannel channel = null;
        try{
            channel = channelManager.buildChannel(scheduleIP, schedulePort);
            //2.拼装request
            Empty request = Empty.newBuilder().build();
            //3.调用rpc,获取response
            YarnRpcMessage.GenerateObServerProxyWalletAddressResponse response =
                    YarnServiceGrpc.newBlockingStub(channel).generateObServerProxyWalletAddress(request);

            //4.处理response
            if (response == null) {
                throw new CallGrpcServiceFailed();
            }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
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