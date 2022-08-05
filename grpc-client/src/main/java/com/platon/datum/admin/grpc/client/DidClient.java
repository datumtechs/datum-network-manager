package com.platon.datum.admin.grpc.client;

import cn.hutool.json.JSONUtil;
import com.google.protobuf.Empty;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.dao.entity.ApplyRecord;
import com.platon.datum.admin.grpc.carrier.api.DIDServiceGrpc;
import com.platon.datum.admin.grpc.carrier.api.DidRpcApi;
import com.platon.datum.admin.grpc.carrier.api.VcServiceGrpc;
import com.platon.datum.admin.grpc.carrier.types.Common;
import com.platon.datum.admin.grpc.channel.SimpleChannelManager;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
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
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getDid();
    }

    /**
     * 申请VC
     */
//    public void applyVCLocal(ApplyRecord applicantRecord,) {
//        //1.获取rpc连接
//        ManagedChannel channel = channelManager.getCarrierChannel();
//        //2.拼装request
////        string          issuer_did = 1;
////        string          issuer_url = 2;
////        string          applicant_did = 3;
////        string          context = 4;          //option, "" means default
////        uint64          pct_id = 5;           //map定义
////        string          claim = 6;            //map的json string
////        string          expiration_date = 7;  //option, "" means default
////        string          ext_info = 8;         //扩展字段, JSON, 透传到issuer
////        string          req_digest = 9;       //请求摘要SHA3, option
////        string          req_signature = 10;    //对请求摘要的签名。从req_digest,req_signature能恢复出签名私钥对应的公钥, option
//        ApplyRecord applicantRecord = new ApplyRecord();
//        applicantRecord.setPctId();
//        applicantRecord.setApplyOrg();
//        applicantRecord.setApproveOrg();
//        applicantRecord.setContext();
//        applicantRecord.setClaim();
//        applicantRecord.setStartTime(applicantRecord.getStartTime());
//        applicantRecord.setApplyRemark(applicantRecord.getApplyRemark());
//        applicantRecord.setMaterial(applicantRecord.getMaterial());
//        applicantRecord.setMaterialDesc(applicantRecord.getMaterialDesc());
//        applicantRecord.setProgress(0);
//        applicantRecord.setStatus(0);
//        DidRpcApi.ApplyVCReq request = DidRpcApi.ApplyVCReq.newBuilder()
//                .setIssuerDid(applicantRecord.getApproveOrg())
//                .setIssuerUrl("")
//                .setApplicantDid(applicantRecord.getApplyOrg())
//                .setContext(applicantRecord.getContext())
//                .setPctId(applicantRecord.getPctId())
//                .setClaim(applicantRecord.getClaim())
//                .setExtInfo(JSONUtil.toJsonStr(applicantRecord))
//                .build();
//        //3.调用rpc,获取response
//        Common.SimpleResponse response = VcServiceGrpc.newBlockingStub(channel).applyVCLocal(request);
//        //4.处理response
//        if (response == null) {
//            throw new CallGrpcServiceFailed();
//        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
//            throw new CallGrpcServiceFailed(response.getMsg());
//        }
//    }

    /**
     * 下载VC
     */
    public String downloadVCLocal(String scheduleIP, int schedulePort) {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.buildChannel(scheduleIP, schedulePort);
        //2.拼装request
        //3.调用rpc,获取response
        DidRpcApi.CreateDIDResponse response = DIDServiceGrpc.newBlockingStub(channel).createDID(Empty.newBuilder().build());
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getDid();
    }

    /**
     * 创建VC
     */
    public String createVC(String scheduleIP, int schedulePort) {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.buildChannel(scheduleIP, schedulePort);
        //2.拼装request
        //3.调用rpc,获取response
        DidRpcApi.CreateDIDResponse response = DIDServiceGrpc.newBlockingStub(channel).createDID(Empty.newBuilder().build());
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return response.getDid();
    }


    /**
     * 申请VC
     *
     * @param ip         委员会成员调度服务ip
     * @param port       委员会成员调度服务端口
     * @param identityId 组织的身份标识Id
     * @param pctId      组织名称
     * @param claim      证书材料
     */
//    public String createVC(String ip, int port, String identityId, int pctId, String claim) {
//        log.debug("从carrier申请入网，identityId:{}", identityId);
//        //1.获取rpc连接
//        ManagedChannel channel = channelManager.buildChannel(ip, port);
//        //2.拼装request
//        DidRpcApi.CreateVCRequest request = DidRpcApi.CreateVCRequest.newBuilder()
//                .setDid(identityId)
//                .setPctId(pctId)
//                .setClaim(claim)
//                .build();
//
//        //3.调用rpc,获取response
//        DidRpcApi.CreateVCResponse response = DIDServiceGrpc.newBlockingStub(channel).createVC(request);
//        //4.处理response
//        if (response == null) {
//            throw new CallGrpcServiceFailed();
//        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
//            throw new CallGrpcServiceFailed(response.getMsg());
//        }
//        return response.getVc();
//    }

}
