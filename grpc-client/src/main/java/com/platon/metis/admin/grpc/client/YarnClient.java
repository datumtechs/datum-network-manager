package com.platon.metis.admin.grpc.client;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.protobuf.Empty;
import com.platon.metis.admin.common.exception.ApplicationException;
import com.platon.metis.admin.common.util.ExceptionUtil;
import com.platon.metis.admin.dao.entity.LocalDataNode;
import com.platon.metis.admin.dao.enums.FileTypeEnum;
import com.platon.metis.admin.grpc.channel.BaseChannelManager;
import com.platon.metis.admin.grpc.common.CommonBase;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.entity.*;
import com.platon.metis.admin.grpc.service.YarnRpcMessage;
import com.platon.metis.admin.grpc.service.YarnServiceGrpc;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
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
    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;

    /**
     * 调度新增数据节点
     * @param localDataNode           数据节点实体类
     * @return
     */
    public FormatSetDataNodeResp setDataNode(LocalDataNode localDataNode) {
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            YarnRpcMessage.SetDataNodeRequest setDataNodeRequest = YarnRpcMessage.SetDataNodeRequest.newBuilder().
                    setInternalIp(localDataNode.getInternalIp()).
                    setInternalPort(String.valueOf(localDataNode.getInternalPort())).
                    setExternalIp(localDataNode.getExternalIp()).
                    setExternalPort(String.valueOf(localDataNode.getExternalPort())).build();
            log.info("调用新增数据节点调度服务,参数:{}",setDataNodeRequest);
            //3.调用rpc,获取response
            YarnRpcMessage.SetDataNodeResponse setDataNodeResponse = YarnServiceGrpc.newBlockingStub(channel).setDataNode(setDataNodeRequest);
            log.info("调用新增数据节点调度服务,返回结果:{}",setDataNodeResponse);
            //4.处理response
            FormatSetDataNodeResp resp = new FormatSetDataNodeResp();
            resp.setStatus(setDataNodeResponse.getStatus());
            resp.setMsg(setDataNodeResponse.getMsg());
            if (GRPC_SUCCESS_CODE == setDataNodeResponse.getStatus()) {
                YarnRpcMessage.YarnRegisteredPeerDetail resDataNode = setDataNodeResponse.getNode();
                if (ObjectUtil.isNotNull(resDataNode)) {
                    RegisteredNodeResp nodeResp = new RegisteredNodeResp();
                    nodeResp.setNodeId(resDataNode.getId());
                    nodeResp.setConnStatus(resDataNode.getConnState().getNumber());
                    resp.setNodeResp(nodeResp);
                }
            }
            return resp;
        } finally {
            channelManager.closeChannel(channel);
        }
    }

    /**
     * 调度修改数据节点
     * @param localDataNode           数据节点实体类
     * @return
     */
    public FormatSetDataNodeResp updateDataNode(LocalDataNode localDataNode) {
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            YarnRpcMessage.UpdateDataNodeRequest request = YarnRpcMessage.UpdateDataNodeRequest
                    .newBuilder().setId(localDataNode.getNodeId()).
                            setInternalIp(localDataNode.getInternalIp()).
                            setInternalPort(String.valueOf(localDataNode.getInternalPort())).
                            setExternalIp(localDataNode.getExternalIp()).
                            setExternalPort(String.valueOf(localDataNode.getExternalPort())).build();
            log.info("调用修改数据节点调度服务,参数:{}",request);
            //3.调用rpc,获取response
            YarnRpcMessage.SetDataNodeResponse setDataNodeResponse = YarnServiceGrpc.newBlockingStub(channel).updateDataNode(request);
            log.info("调用修改数据节点调度服务,相应结果:{}",setDataNodeResponse);
            //4.处理response
            FormatSetDataNodeResp resp = new FormatSetDataNodeResp();
            resp.setStatus(setDataNodeResponse.getStatus());
            resp.setMsg(setDataNodeResponse.getMsg());
            if (GRPC_SUCCESS_CODE == setDataNodeResponse.getStatus()) {
                YarnRpcMessage.YarnRegisteredPeerDetail resDataNode = setDataNodeResponse.getNode();
                if (ObjectUtil.isNotNull(resDataNode)) {
                    RegisteredNodeResp nodeResp = new RegisteredNodeResp();
                    nodeResp.setNodeId(resDataNode.getId());
                    nodeResp.setConnStatus(resDataNode.getConnState().getNumber());
                    resp.setNodeResp(nodeResp);
                }
            }
            return resp;
        } finally {
            channelManager.closeChannel(channel);
        }
    }

    /**
     * 调度删除数据节点
     * @return
     */
    public CommonResp deleteDataNode(String id) {
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            YarnRpcMessage.DeleteRegisteredNodeRequest request = YarnRpcMessage.DeleteRegisteredNodeRequest.newBuilder().setId(id).build();
            log.info("调用删除数据节点调度服务,参数:{}",request);
            //3.调用rpc,获取response
            CommonBase.SimpleResponse simpleResponseCode = YarnServiceGrpc.newBlockingStub(channel).deleteDataNode(request);
            log.info("调用删除数据节点调度服务,响应结果:{}",simpleResponseCode);
            //4.处理response
            CommonResp resp = new CommonResp();
            resp.setMsg(simpleResponseCode.getMsg());
            resp.setStatus(simpleResponseCode.getStatus());
            return resp;
        } finally {
            channelManager.closeChannel(channel);
        }
    }

    /**
     * 调度获取数据节点列表
     * @return
     */
    public List<LocalDataNode> getLocalDataNodeList() {
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            Empty emptyGetParams = Empty.newBuilder().build();
            log.info("调用获取数据节点列表调度服务");
            //3.调用rpc,获取response
            YarnRpcMessage.GetRegisteredNodeListResponse response = YarnServiceGrpc.newBlockingStub(channel).getDataNodeList(emptyGetParams);
            log.info("调用获取数据节点列表调度服务,响应结果:{}",response);

            if(!Objects.isNull(response) && GrpcConstant.GRPC_SUCCESS_CODE == response.getStatus()){
                return convertToLocalDataNodeList(response.getNodesList());
            }else{
                log.error("同步本地数据节点出错, code:{}, errorMsg:{}", response.getStatus(), response.getMsg());
                return null;
            }
        } finally {
            channelManager.closeChannel(channel);
        }
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
    public YarnAvailableDataNodeResp getAvailableDataNode(long fileSize, FileTypeEnum fileType) throws ApplicationException{
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            YarnRpcMessage.QueryAvailableDataNodeRequest request = YarnRpcMessage.QueryAvailableDataNodeRequest
                    .newBuilder()
                    .setFileSize(fileSize)
                    .setFileTypeValue(fileType.getValue())
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
        } finally {
            channelManager.closeChannel(channel);
        }
    }

    /**
     * 查询需要下载的目标原始文件所在的 数据服务信息和文件的完整相对路径
     */
    public YarnQueryFilePositionResp queryFilePosition(String fileId) throws ApplicationException{
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
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
        } finally {
            channelManager.closeChannel(channel);
        }
    }


    /**
     * 尝试连接调度服务,连通则返回true，否则返回false
     */
    public boolean connectScheduleServer(String scheduleIP,int schedulePort){
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.buildChannel(scheduleIP, schedulePort);
            //2.拼装request
            Empty request = Empty.newBuilder().build();
            //3.调用rpc,获取response
            YarnServiceGrpc.newBlockingStub(channel).getNodeInfo(request);
        } catch (Throwable throwable) {
            Class[] exceptionClassArray = new Class[]{java.net.ConnectException.class,java.net.UnknownHostException.class};
            boolean result = ExceptionUtil.causeBy(throwable, exceptionClassArray);
            if(result){//如果是ConnectException或者UnknownHostException则表示连接不通调度服务
                return false;
            }
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
        YarnGetNodeInfoResp resp = new YarnGetNodeInfoResp();
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.buildChannel(scheduleIP, schedulePort);
            //2.拼装request
            Empty request = Empty.newBuilder().build();
            //3.调用rpc,获取response
            YarnRpcMessage.GetNodeInfoResponse nodeInfo = YarnServiceGrpc.newBlockingStub(channel).getNodeInfo(request);
            //4.处理response
            if (nodeInfo.getStatus() != GRPC_SUCCESS_CODE) {
                throw new ApplicationException(StrUtil.format("查看自身调度服务信息失败：status:{},message:{}",
                        nodeInfo.getStatus(),
                        nodeInfo.getMsg()));
            }
            /**
             * 由于调度服务rpc接口也在开发阶段，如果直接返回调度服务的response，一旦response发生变化，则调用该方法的地方都需要修改
             * 故将response转换后再放给service类使用
             */
            YarnRpcMessage.YarnNodeInfo information = nodeInfo.getInformation();
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
        } catch (Exception exception) {
            resp.setStatus(1);
            resp.setMsg(exception.getMessage());
        } finally {
            channelManager.closeChannel(channel);
        }
        return resp;
    }
}
