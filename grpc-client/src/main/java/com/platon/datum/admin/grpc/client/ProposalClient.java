package com.platon.datum.admin.grpc.client;

import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.grpc.carrier.api.DidRpcApi;
import com.platon.datum.admin.grpc.carrier.api.DidRpcApi;
import com.platon.datum.admin.grpc.carrier.api.ProposalServiceGrpc;
import com.platon.datum.admin.grpc.channel.SimpleChannelManager;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2022/7/28 20:20
 * @Version
 * @Desc
 */

@Slf4j
@Component
public class ProposalClient {


    @Resource
    private SimpleChannelManager channelManager;

    /**
     * 发起提案
     *
     * @param proposalType        1:增加 2:踢出 3:主动退出
     * @param proposalUrl         提案材料ipfs地址
     * @param candidateAddress    被提名候选人的地址
     * @param candidateServiceUrl 被提名候选人的服务url
     * @return 提案id
     */
    public String submitProposal(int proposalType, String proposalUrl, String candidateAddress, String candidateServiceUrl) {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        DidRpcApi.SubmitProposalRequest request = DidRpcApi.SubmitProposalRequest.newBuilder()
                .setProposalType(proposalType)
                .setProposalUrl(proposalUrl)
                .setCandidateAddress(candidateAddress)
                .setCandidateServiceUrl(candidateServiceUrl)
                .build();
        log.debug("submitProposal,request:{}",request);
        //3.调用rpc,获取response
        DidRpcApi.SubmitProposalResponse response = ProposalServiceGrpc.newBlockingStub(channel)
                .submitProposal(request);
        log.debug("submitProposal,response:{}",response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getProposalId();
    }

    /**
     * 撤销提案
     *
     * @param proposalId 提案id
     * @return 结果
     */
    public boolean withdrawProposal(String proposalId) {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        DidRpcApi.WithdrawProposalRequest request = DidRpcApi.WithdrawProposalRequest.newBuilder()
                .setProposalId(proposalId)
                .build();
        log.debug("withdrawProposal,request:{}",request);
        //3.调用rpc,获取response
        DidRpcApi.WithdrawProposalResponse response = ProposalServiceGrpc.newBlockingStub(channel).withdrawProposal(request);
        log.debug("withdrawProposal,response:{}",response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getResult();
    }


    /**
     * 投票
     *
     * @param proposalId 提案id
     * @return 结果
     */
    public boolean voteProposal(String proposalId) {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        DidRpcApi.VoteProposalRequest request = DidRpcApi.VoteProposalRequest.newBuilder()
                .setProposalId(proposalId)
                .build();
        log.debug("voteProposal,request:{}",request);
        //3.调用rpc,获取response
        DidRpcApi.VoteProposalResponse response = ProposalServiceGrpc.newBlockingStub(channel).voteProposal(request);
        log.debug("voteProposal,response:{}",response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getResult();
    }

    /**
     * 提案生效
     *
     * @param proposalId 提案id
     * @return 结果
     */
    public boolean effectProposal(String proposalId) {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        DidRpcApi.EffectProposalRequest request = DidRpcApi.EffectProposalRequest.newBuilder()
                .setProposalId(proposalId)
                .build();
        log.debug("effectProposal,request:{}",request);
        //3.调用rpc,获取response
        DidRpcApi.EffectProposalResponse response = ProposalServiceGrpc.newBlockingStub(channel).effectProposal(request);
        log.debug("effectProposal,response:{}",response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            if("invalid proposal id".equalsIgnoreCase(response.getMsg())){
                throw new BizException(Errors.AlreadyEffectProposal);
            }
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getResult();
    }

}
