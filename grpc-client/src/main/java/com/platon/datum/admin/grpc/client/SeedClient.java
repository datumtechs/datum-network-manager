package com.platon.datum.admin.grpc.client;

import com.google.protobuf.Empty;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.dao.entity.SeedNode;
import com.platon.datum.admin.grpc.carrier.api.SysRpcApi;
import com.platon.datum.admin.grpc.carrier.api.YarnServiceGrpc;
import com.platon.datum.admin.grpc.carrier.types.Common;
import com.platon.datum.admin.grpc.channel.SimpleChannelManager;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.platon.datum.admin.grpc.constant.GrpcConstant.GRPC_SUCCESS_CODE;

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

    @Resource
    private SimpleChannelManager channelManager;

    /**
     * 新增种子节点返回nodeId
     */
    public SysRpcApi.SeedPeer addSeedNode(String address) {
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        SysRpcApi.SetSeedNodeRequest seedRequest = SysRpcApi.SetSeedNodeRequest.newBuilder()
                .setAddr(address)
                .build();
        //3.调用rpc,获取response
        SysRpcApi.SetSeedNodeResponse response = YarnServiceGrpc.newBlockingStub(channel).setSeedNode(seedRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        return response.getNode();
    }


    /**
     * 删除种子节点
     */
    public void deleteSeedNode(String address) {

        log.debug("从carrier删除种子节点，address:{}", address);
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        SysRpcApi.DeleteSeedNodeRequest seedRequest = SysRpcApi.DeleteSeedNodeRequest.newBuilder()
                .setAddr(address)
                .build();
        //3.调用rpc,获取response
        Common.SimpleResponse response = YarnServiceGrpc.newBlockingStub(channel).deleteSeedNode(seedRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }

    /**
     * 查询种子服务列表
     */
    public List<SeedNode> getSeedNodeList() {
        log.debug("从carrier查询种子节点列表");
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        Empty seedNodeListRequest = Empty.newBuilder().build();
        //3.调用rpc,获取response
        SysRpcApi.GetSeedNodeListResponse response = YarnServiceGrpc.newBlockingStub(channel).getSeedNodeList(seedNodeListRequest);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        log.debug("从carrier查询种子节点列表，数量：{}", response.getNodesList().size());
        return convertToLocalSeedNodeList(response.getNodesList());
    }

    private List<SeedNode> convertToLocalSeedNodeList(List<SysRpcApi.SeedPeer> seedNodeList) {
        return seedNodeList.parallelStream().map(seedNode -> {
            SeedNode localSeedNode = new SeedNode();
            localSeedNode.setSeedNodeId(seedNode.getAddr());
            localSeedNode.setInitFlag(seedNode.getIsDefault());
            localSeedNode.setConnStatus(seedNode.getConnState().getNumber());
            return localSeedNode;
        }).collect(Collectors.toList());
    }
}