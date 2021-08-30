package com.platon.rosettanet.admin.grpc.client;

import com.platon.rosettanet.admin.common.exception.ApplicationException;
import com.platon.rosettanet.admin.dao.entity.LocalDataAuth;
import com.platon.rosettanet.admin.grpc.channel.BaseChannelManager;
import com.platon.rosettanet.admin.grpc.constant.GrpcConstant;
import com.platon.rosettanet.admin.grpc.entity.CommonResp;
import com.platon.rosettanet.admin.grpc.entity.DataAuthResp;
import com.platon.rosettanet.admin.grpc.service.AuthRpcMessage;
import com.platon.rosettanet.admin.grpc.service.AuthServiceGrpc;
import com.platon.rosettanet.admin.grpc.service.CommonMessage;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author liushuyu
 * @Date 2021/7/7 10:24
 * @Version
 * @Desc 身份信息服务客户端
 * java服务类：AuthServiceGrpc
 * proto文件：auth_rpc_api.proto
 */

@Component
@Slf4j
public class AuthClient {

    @Resource(name = "simpleChannelManager")
    private BaseChannelManager channelManager;


    /**
     * 申请准入网络
     * @param identityId // 组织的身份标识Id
     * @param name               // 组织名称
     */
    public CommonResp applyIdentityJoin(String identityId,String name){
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            CommonMessage.OrganizationIdentityInfo orgInfo = CommonMessage.OrganizationIdentityInfo
                    .newBuilder()
                    .setName(name)
                    .setIdentityId(identityId)
                    .build();
            AuthRpcMessage.ApplyIdentityJoinRequest joinRequest = AuthRpcMessage.ApplyIdentityJoinRequest
                    .newBuilder()
                    .setMember(orgInfo)
                    .build();
            //3.调用rpc,获取response
            CommonMessage.SimpleResponseCode responseCode = AuthServiceGrpc.newBlockingStub(channel).applyIdentityJoin(joinRequest);
            //4.处理response
            CommonResp resp = new CommonResp();
            resp.setStatus(responseCode.getStatus());
            resp.setMsg(responseCode.getMsg());
            return resp;
        }finally {
            channelManager.closeChannel(channel);
        }

    }

    /**
     * 注销准入网络
     */
    public CommonResp revokeIdentityJoin(){
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            CommonMessage.EmptyGetParams request = CommonMessage.EmptyGetParams
                    .newBuilder()
                    .build();
            //3.调用rpc,获取response
            CommonMessage.SimpleResponseCode responseCode = AuthServiceGrpc.newBlockingStub(channel).revokeIdentityJoin(request);
            //4.处理response
            CommonResp resp = new CommonResp();
            resp.setStatus(responseCode.getStatus());
            resp.setMsg(responseCode.getMsg());
            return resp;
        }finally {
            channelManager.closeChannel(channel);
        }
    }


    /**
     * 获取数据授权申请列表
     * @return
     */
    public DataAuthResp getMetaDataAuthorityList(){
            //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            CommonMessage.EmptyGetParams request = CommonMessage.EmptyGetParams.newBuilder().build();
            //3.调用rpc,获取response
            AuthRpcMessage.GetMetaDataAuthorityListResponse response = AuthServiceGrpc.newBlockingStub(channel).getMetaDataAuthorityList(request);
            log.debug("====> RPC客户端 dataAuthResponse:" + response.getMsg() +" , dataAuthList Size:"+ response.getListList().size());
            //4.处理response
            DataAuthResp dataAuthResp = new DataAuthResp();
            if(!Objects.isNull(response)){
                dataAuthResp.setStatus(response.getStatus());
                dataAuthResp.setMsg(response.getMsg());
                if(GrpcConstant.GRPC_SUCCESS_CODE == response.getStatus()){
                    dataAuthResp.setDataAuthList(dataConvertToAuthList(response));
                }
            }
            return dataAuthResp;
        }finally {
            channelManager.closeChannel(channel);
        }
    }


    /**
     * 数据授权审核
     * @param metaDataAuthId ：元数据授权申请Id
     * @param authStatus ：授权数据状态：0：等待授权审核，1:同意， 2:拒绝
     * @return
     */
    public CommonResp auditMetaData(String metaDataAuthId, int authStatus) throws ApplicationException {
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            AuthRpcMessage.AuditMetaDataOption auditDataStatus =  AuthRpcMessage.AuditMetaDataOption.forNumber(authStatus);
            AuthRpcMessage.AuditMetaDataAuthorityRequest request =  AuthRpcMessage
                                                                        .AuditMetaDataAuthorityRequest
                                                                        .newBuilder()
                                                                        .setMetaDataAuthId(metaDataAuthId)
                                                                        .setAudit(auditDataStatus)
                                                                        .build();
            //3.调用rpc,获取response
            CommonMessage.SimpleResponseCode response = AuthServiceGrpc.newBlockingStub(channel).auditMetaDataAuthority(request);
            log.debug("====> RPC客户端 auditAuthResponse:" + response.getMsg());
            //4.处理response
            CommonResp resp = new CommonResp();
            resp.setStatus(response.getStatus());
            resp.setMsg(response.getMsg());
            return resp;
        }finally {
            channelManager.closeChannel(channel);
        }
    }





    private List<LocalDataAuth> dataConvertToAuthList(AuthRpcMessage.GetMetaDataAuthorityListResponse response){
        List<LocalDataAuth> localDataAuthList = new ArrayList<>();
        List <AuthRpcMessage.GetMetaDataAuthority> metaDataAuthorityList = response.getListList();
        if(!CollectionUtils.isEmpty(metaDataAuthorityList)){
            for (AuthRpcMessage.GetMetaDataAuthority dataAuth : metaDataAuthorityList) {
                  //元数据使用授权
                  AuthRpcMessage.MetaDataAuthority metaDataAuthority =  dataAuth.getAuth();
                  CommonMessage.OrganizationIdentityInfo  identityInfo = metaDataAuthority.getOwner();
                  String metaDataId = metaDataAuthority.getMetaDataId();
                  AuthRpcMessage.MetaDataUsage  metaDataUsage = metaDataAuthority.getUsage();
                  long startAt = metaDataUsage.getStartAt();
                  long endAt = metaDataUsage.getEndAt();
                  int useAuthNumber = metaDataUsage.getTimes();
                  int useAuthType = metaDataUsage.getUsageTypeValue();

                  LocalDataAuth localDataAuth = new LocalDataAuth();
                  localDataAuth.setAuthId(dataAuth.getMetaDataAuthId());
                  localDataAuth.setApplyUser(dataAuth.getUser());
                  localDataAuth.setUserType(dataAuth.getUserTypeValue());
                  localDataAuth.setCreateAt(new Date(dataAuth.getApplyAt()));
                  localDataAuth.setAuthAt(new Date(dataAuth.getAuditAt()));
                  localDataAuth.setStatus(dataAuth.getAuditValue());
                  localDataAuth.setAuthType(useAuthType);
                  localDataAuth.setAuthValueStartAt(new Date(startAt));
                  localDataAuth.setAuthValueEndAt(new Date(endAt));
                  localDataAuth.setAuthValueAmount(useAuthNumber);
                  localDataAuth.setMetaDataId(metaDataId);
                  localDataAuth.setIdentityId(identityInfo.getIdentityId());
                  localDataAuth.setIdentityName(identityInfo.getName());
                  localDataAuth.setIdentityNodeId(identityInfo.getNodeId());
                  localDataAuthList.add(localDataAuth);
            }
        }
        return localDataAuthList;
    }


}
