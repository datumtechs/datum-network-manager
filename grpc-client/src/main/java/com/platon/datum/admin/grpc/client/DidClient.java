package com.platon.datum.admin.grpc.client;

import cn.hutool.json.JSONUtil;
import com.google.protobuf.Empty;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.common.util.DidUtil;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.ApplyRecord;
import com.platon.datum.admin.grpc.carrier.api.DIDServiceGrpc;
import com.platon.datum.admin.grpc.carrier.api.DidRpcApi;
import com.platon.datum.admin.grpc.carrier.api.VcServiceGrpc;
import com.platon.datum.admin.grpc.carrier.types.Common;
import com.platon.datum.admin.grpc.channel.SimpleChannelManager;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2022/7/28 18:32
 * @Version
 * @Desc
 */

@Slf4j
@Component
public class DidClient {

    @Resource
    private SimpleChannelManager channelManager;


    /**
     * 申请did
     */
    public String createDID(String scheduleIP, int schedulePort) {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.buildChannel(scheduleIP, schedulePort);
        //2.拼装request
        //3.调用rpc,获取response
        DidRpcApi.CreateDIDResponse response = DIDServiceGrpc.newBlockingStub(channel).createDID(Empty.newBuilder().build());
        log.debug("createDID,response:{}", response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != 15002) {
            //did已经创建
            String observerProxyWalletAddress = OrgCache.getLocalOrgInfo().getObserverProxyWalletAddress();
            return DidUtil.latAddressToDid(observerProxyWalletAddress);
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getDid();
    }

    /**
     * 申请VC
     */
    public void applyVCLocal(ApplyRecord applicantRecord, String approveOrgUrl) {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        DidRpcApi.ApplyVCReq request = DidRpcApi.ApplyVCReq.newBuilder()
                .setIssuerDid(applicantRecord.getApproveOrg())
                .setIssuerUrl(approveOrgUrl)
                .setApplicantDid(applicantRecord.getApplyOrg())
                .setContext(applicantRecord.getContext())
                .setPctId(applicantRecord.getPctId())
                .setClaim(applicantRecord.getClaim())
                .setExtInfo(JSONUtil.toJsonStr(applicantRecord))
                .build();
        log.debug("applyVCLocal,request:{}", request);
        //3.调用rpc,获取response
        Common.SimpleResponse response = VcServiceGrpc.newBlockingStub(channel).applyVCLocal(request);
        log.debug("applyVCLocal,response:{}", response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }

    /**
     * 普通组织使用该接口，下载VC
     */
    public String downloadVCLocal(String approveOrg, String approveOrgUrl, String applyOrg) {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        DidRpcApi.DownloadVCReq request = DidRpcApi.DownloadVCReq.newBuilder()
                .setIssuerDid(approveOrg)
                .setIssuerUrl(approveOrgUrl)
                .setApplicantDid(applyOrg)
                .build();
        log.debug("downloadVCLocal,request:{}", request);
        //3.调用rpc,获取response
        DidRpcApi.DownloadVCResponse response = VcServiceGrpc.newBlockingStub(channel).downloadVCLocal(request);
        log.debug("downloadVCLocal,response:{}", response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE
                && response.getExtInfo() == null) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getExtInfo();
    }

    /**
     * 委员会成员组织使用该接口,创建VC
     */
    public Pair<String, DidRpcApi.TxInfo> createVC(ApplyRecord applyRecord) {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        DidRpcApi.CreateVCRequest request = DidRpcApi.CreateVCRequest.newBuilder()
                .setApplicantDid(applyRecord.getApplyOrg())
                .setContext(applyRecord.getContext())
                .setPctId(applyRecord.getPctId())
                .setClaim(applyRecord.getClaim())
                .setExpirationDate(LocalDateTimeUtil.now().plusYears(100).toString())//TODO 暂时设置100年有效期
                .build();
        log.debug("createVC,request:{}", request);
        //3.调用rpc,获取response
        DidRpcApi.CreateVCResponse response = VcServiceGrpc.newBlockingStub(channel).createVC(request);
        log.debug("createVC,response:{}", response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        String vc = response.getVc();
        DidRpcApi.TxInfo txInfo = response.getTxInfo();
        return Pair.of(vc, txInfo);
    }
}
