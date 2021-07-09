package com.platon.rosettanet.admin.grpc.client;

import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.service.CommonMessage;
import com.platon.rosettanet.admin.grpc.service.YarnRpcMessage;
import com.platon.rosettanet.admin.grpc.service.YarnServiceGrpc;
import io.grpc.Channel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/8 18:27
 * @Version
 * @Desc 系统服务客户端
 * java服务类：YarnServiceGrpc
 * proto文件：sys_rpc_api.proto
 */

@Component
public class YarnClient {
    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;

    public void setDataNode(String internalIp, String internalPort, String externalIp, String externalPort) {
        //1.获取rpc连接
        Channel channel = channelManager.buildChannel("localhost", 50051);
        //2.拼装request
        YarnRpcMessage.SetDataNodeRequest setDataNodeRequest = YarnRpcMessage.SetDataNodeRequest
                .newBuilder().setInternalIp(internalIp).setInternalPort(internalPort).setExternalIp(externalIp).setExternalPort(externalPort).build();
        //3.调用rpc,获取response
        YarnRpcMessage.SetDataNodeResponse setDataNodeResponse = YarnServiceGrpc.newBlockingStub(channel).setDataNode(setDataNodeRequest);
        //4.处理response
        System.out.println("###############" + setDataNodeResponse.getMsg());
        System.out.println("111111111");
    }

    public void UpdateDataNode(String id, String internalIp, String internalPort, String externalIp, String externalPort) {
        //1.获取rpc连接
        Channel channel = channelManager.buildChannel("localhost", 50051);
        //2.拼装request
        YarnRpcMessage.UpdateDataNodeRequest request = YarnRpcMessage.UpdateDataNodeRequest
                .newBuilder().setId(id).setInternalIp(internalIp).setInternalPort(internalPort).setExternalIp(externalIp).setExternalPort(externalPort).build();
        //3.调用rpc,获取response
        YarnRpcMessage.SetDataNodeResponse setDataNodeResponse = YarnServiceGrpc.newBlockingStub(channel).updateDataNode(request);
        //4.处理response
        System.out.println("###############" + setDataNodeResponse.getMsg());
        System.out.println("111111111");
    }

    public void DeleteDataNode(String id) {
        //1.获取rpc连接
        Channel channel = channelManager.buildChannel("localhost", 50051);
        //2.拼装request
        CommonMessage.DeleteRegisteredNodeRequest request = CommonMessage.DeleteRegisteredNodeRequest.newBuilder().setId(id).build();
        //3.调用rpc,获取response
        CommonMessage.SimpleResponseCode simpleResponseCode = YarnServiceGrpc.newBlockingStub(channel).deleteDataNode(request);
        //4.处理response
        System.out.println("111111111");
    }

    public void GetDataNodeList() {
        //1.获取rpc连接
        Channel channel = channelManager.buildChannel("localhost", 50051);
        //2.拼装request
        CommonMessage.EmptyGetParams emptyGetParams = CommonMessage.EmptyGetParams.getDefaultInstance();
        //3.调用rpc,获取response
        YarnRpcMessage.GetRegisteredNodeListResponse dataNodeList = YarnServiceGrpc.newBlockingStub(channel).getDataNodeList(emptyGetParams);
        //4.处理response
        System.out.println("###############" + dataNodeList);
    }
}
