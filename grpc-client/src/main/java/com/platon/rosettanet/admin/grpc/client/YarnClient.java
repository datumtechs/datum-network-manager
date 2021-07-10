package com.platon.rosettanet.admin.grpc.client;

import cn.hutool.core.util.StrUtil;
import com.platon.rosettanet.admin.common.exception.ApplicationException;
import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.entity.AvailableDataNodeResp;
import com.platon.rosettanet.admin.grpc.service.CommonMessage;
import com.platon.rosettanet.admin.grpc.service.YarnRpcMessage;
import com.platon.rosettanet.admin.grpc.service.YarnServiceGrpc;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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


    /**
     * 根据需要上传的文件大小和类型，获取可用的数据节点信息
     * @param scheduleServerHost 调度服务的ip
     * @param scheduleServerPort 调度服务的port
     */
    public AvailableDataNodeResp getAvailableDataNode(String scheduleServerHost, int scheduleServerPort) {
        //1.获取rpc连接
        Channel channel = channelManager.buildChannel(scheduleServerHost, scheduleServerPort);
        //2.拼装request
        YarnRpcMessage.QueryAvailableDataNodeRequest request = YarnRpcMessage.QueryAvailableDataNodeRequest.newBuilder().build();
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
        AvailableDataNodeResp node = new AvailableDataNodeResp();
        node.setIp(response.getIp());
        node.setPort(Integer.parseInt(response.getPort()));
        return node;
    }
}
