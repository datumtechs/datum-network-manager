package com.platon.rosettanet.admin.grpc.client;

import com.google.protobuf.Empty;
import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.constant.GrpcConstant;
import com.platon.rosettanet.admin.grpc.service.*;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/8 18:29
 * @Version
 * @Desc 种子节点服务客户端
 * java服务类：YarnServiceGrpc
 * proto文件：sys_rpc_api.proto
 */

@Component
@Slf4j
public class SeedClient {

    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;

    /**
     * 新增种子节点返回nodeId
     */
    public YarnRpcMessage.SeedPeer addSeedNode(String internalIp, Integer internalPort){
        long startTime = System.currentTimeMillis();
        Channel channel = null;
        YarnRpcMessage.SetSeedNodeResponse seedNodeResponse;
        try {
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //2.拼装request
            YarnRpcMessage.SetSeedNodeRequest seedRequest = YarnRpcMessage.SetSeedNodeRequest.newBuilder()
                    .setInternalIp(internalIp).setInternalPort(String.valueOf(internalPort))
                    .build();
            //3.调用rpc,获取response
            seedNodeResponse  = YarnServiceGrpc.newBlockingStub(channel).setSeedNode(seedRequest);
            //4.处理response
            if (seedNodeResponse.getStatus() != 0 || !GrpcConstant.ok.equals(seedNodeResponse.getMsg())) {
                throw new RuntimeException("gRPC服务调用失败，请稍后重试！");
            }
        }finally {
            channelManager.closeChannel(channel);
        }
        long diffTime = System.currentTimeMillis() - startTime;
        log.info("新增计算节点, 响应时间:{}, 响应数据:{}", diffTime+"ms", seedNodeResponse);
        return seedNodeResponse.getNode();
    }

    /**
     * 修改种子节点返回nodeId
     */
    public YarnRpcMessage.SeedPeer updateSeedNode(String seedNodeId, String internalIp, Integer internalPort){
        long startTime = System.currentTimeMillis();
        Channel channel = null;
        YarnRpcMessage.SetSeedNodeResponse seedNodeResponse;
        try{
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //2.拼装request
            YarnRpcMessage.UpdateSeedNodeRequest seedRequest = YarnRpcMessage.UpdateSeedNodeRequest.newBuilder()
                    .setInternalIp(internalIp).setInternalPort(String.valueOf(internalPort))
                    .setId(seedNodeId)
                    .build();
            //3.调用rpc,获取response
            seedNodeResponse = YarnServiceGrpc.newBlockingStub(channel).updateSeedNode(seedRequest);
            //4.处理response
            if (seedNodeResponse.getStatus() != 0 || !GrpcConstant.ok.equals(seedNodeResponse.getMsg())) {
                throw new RuntimeException("gRPC服务调用失败，请稍后重试！");
            }
        } finally {
            channelManager.closeChannel(channel);
        }
        long diffTime = System.currentTimeMillis() - startTime;
        log.info("修改计算节点, 响应时间:{}, 响应数据:{}", diffTime+"ms", seedNodeResponse);
        return seedNodeResponse.getNode();
    }

    /**
     * 删除种子节点
     */
    public void deleteSeedNode(String seedNodeId){
        long startTime = System.currentTimeMillis();
        Channel channel = null;
        SimpleResponse simpleResponseCode;
        try{
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //2.拼装request
            YarnRpcMessage.DeleteRegisteredNodeRequest seedRequest = YarnRpcMessage.DeleteRegisteredNodeRequest.newBuilder()
                    .setId(seedNodeId)
                    .build();
            //3.调用rpc,获取response
            simpleResponseCode = YarnServiceGrpc.newBlockingStub(channel).deleteSeedNode(seedRequest);
            //4.处理response
            if (simpleResponseCode.getStatus() != 0 || !GrpcConstant.ok.equals(simpleResponseCode.getMsg())) {
                throw new RuntimeException("gRPC服务调用失败，请稍后重试！");
            }
        } finally {
            channelManager.closeChannel(channel);
        }
        long diffTime = System.currentTimeMillis() - startTime;
        log.info("删除计算节点, 响应时间:{}, 响应数据:{}", diffTime+"ms", simpleResponseCode);
    }

    /**
     * 查询种子服务列表
     */
    public YarnRpcMessage.GetSeedNodeListResponse getJobNodeList(){
        long startTime = System.currentTimeMillis();
        Channel channel = null;
        YarnRpcMessage.GetSeedNodeListResponse seedNodeListResponse;
        try{
            //1.获取rpc连接
            channel = channelManager.getScheduleServer();
            //2.拼装request
            Empty seedNodeListRequest = Empty.newBuilder().build();
            //3.调用rpc,获取response
            seedNodeListResponse = YarnServiceGrpc.newBlockingStub(channel).getSeedNodeList(seedNodeListRequest);
            //4.处理response
            if (seedNodeListResponse.getStatus() != 0 || !GrpcConstant.ok.equals(seedNodeListResponse.getMsg())) {
                throw new RuntimeException("gRPC服务调用失败，请稍后重试！");
            }
        } finally {
            channelManager.closeChannel(channel);
        }
        long diffTime = System.currentTimeMillis() - startTime;
        log.info("查询计算服务列表, 响应时间:{}, 响应数据:{}", diffTime+"ms", seedNodeListResponse);
        return seedNodeListResponse;
    }
}
