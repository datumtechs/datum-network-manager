package com.platon.metis.admin.grpc.client;

import com.platon.metis.admin.common.exception.ApplicationException;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import com.platon.metis.admin.grpc.channel.BaseChannelManager;
import com.platon.metis.admin.grpc.common.CommonBase;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.entity.CommonResp;
import com.platon.metis.admin.grpc.service.AuthRpcMessage;
import com.platon.metis.admin.grpc.service.AuthServiceGrpc;
import com.platon.metis.admin.grpc.types.Metadata;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
            CommonBase.Organization orgInfo = CommonBase.Organization
                    .newBuilder()
                    .setNodeName(name)
                    .setIdentityId(identityId)
                    .build();
            AuthRpcMessage.ApplyIdentityJoinRequest joinRequest = AuthRpcMessage.ApplyIdentityJoinRequest
                    .newBuilder()
                    .setMember(orgInfo)
                    .build();

            //3.调用rpc,获取response
            CommonBase.SimpleResponse response = AuthServiceGrpc.newBlockingStub(channel).applyIdentityJoin(joinRequest);
            //4.处理response
            CommonResp resp = new CommonResp();
            resp.setStatus(response.getStatus());
            resp.setMsg(response.getMsg());
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
            com.google.protobuf.Empty request = com.google.protobuf.Empty
                    .newBuilder()
                    .build();
            //3.调用rpc,获取response
            CommonBase.SimpleResponse response = AuthServiceGrpc.newBlockingStub(channel).revokeIdentityJoin(request);
            //4.处理response
            CommonResp resp = new CommonResp();
            resp.setStatus(response.getStatus());
            resp.setMsg(response.getMsg());
            return resp;
        }finally {
            channelManager.closeChannel(channel);
        }
    }


    /**
     * 获取数据授权申请列表
     * @return
     */
    public List<LocalDataAuth> getMetaDataAuthorityList(LocalDateTime latestSynced){
            //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            AuthRpcMessage.GetMetadataAuthorityListRequest request = AuthRpcMessage.GetMetadataAuthorityListRequest
                    .newBuilder()
                    .setLastUpdated(latestSynced.toInstant(ZoneOffset.UTC).toEpochMilli())
                    .build();
            //3.调用rpc,获取response
            AuthRpcMessage.GetMetadataAuthorityListResponse response = AuthServiceGrpc.newBlockingStub(channel).getLocalMetadataAuthorityList(request);
            //4.处理response
            if(!Objects.isNull(response) && GrpcConstant.GRPC_SUCCESS_CODE == response.getStatus()){
                return dataConvertToAuthList(response);
            }else{
                log.error("同步元数据授权申请出错, code:{}, errorMsg:{}", response.getStatus(), response.getMsg());
                return null;
            }

        }finally {
            channelManager.closeChannel(channel);
        }
    }


    /**
     * 数据授权审核
     * @param metaDataAuthId ：元数据授权申请Id
     * @param auditOption ：授权数据状态：0：等待授权审核，1:同意， 2:拒绝
     * param auditDesc
     * @return
     */
    public AuthRpcMessage.AuditMetadataAuthorityResponse auditMetaData(String metaDataAuthId, int auditOption, String auditDesc) throws ApplicationException {
        //1.获取rpc连接
        Channel channel = null;
        try{
            channel = channelManager.getScheduleServer();
            //2.拼装request
            CommonBase.AuditMetadataOption auditDataStatus =  CommonBase.AuditMetadataOption.forNumber(auditOption);
            AuthRpcMessage.AuditMetadataAuthorityRequest request =  AuthRpcMessage
                                                                        .AuditMetadataAuthorityRequest
                                                                        .newBuilder()
                                                                        .setMetadataAuthId(metaDataAuthId)
                                                                        .setAudit(auditDataStatus)
                                                                        .setSuggestion(auditDesc)
                                                                        .build();
            //3.调用rpc,获取response
            AuthRpcMessage.AuditMetadataAuthorityResponse response = AuthServiceGrpc.newBlockingStub(channel).auditMetadataAuthority(request);
            return response;
        }finally {
            channelManager.closeChannel(channel);
        }
    }


    private List<LocalDataAuth> dataConvertToAuthList(AuthRpcMessage.GetMetadataAuthorityListResponse response){
        List<LocalDataAuth> localDataAuthList = new ArrayList<>();
        List <AuthRpcMessage.GetMetadataAuthority> metaDataAuthorityList = response.getListList();
        if(!CollectionUtils.isEmpty(metaDataAuthorityList)){
            for (AuthRpcMessage.GetMetadataAuthority dataAuth : metaDataAuthorityList) {
                  //元数据使用授权
                  Metadata.MetadataAuthority metaDataAuthority =  dataAuth.getAuth();
                  CommonBase.Organization identityInfo = metaDataAuthority.getOwner();
                  String metaDataId = metaDataAuthority.getMetadataId();
                  Metadata.MetadataUsageRule metadataUsageRule = metaDataAuthority.getUsageRule();
                  long startAt = metadataUsageRule.getStartAt();
                  long endAt = metadataUsageRule.getEndAt();
                  int useAuthNumber = metadataUsageRule.getTimes();
                  int useAuthType = metadataUsageRule.getUsageTypeValue();

                  LocalDataAuth localDataAuth = new LocalDataAuth();
                  localDataAuth.setAuthId(dataAuth.getMetadataAuthId());
                  localDataAuth.setApplyUser(dataAuth.getUser());
                  localDataAuth.setUserType(dataAuth.getUserTypeValue());
                  localDataAuth.setCreateAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(dataAuth.getApplyAt()), ZoneOffset.UTC));
                  localDataAuth.setAuthAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(dataAuth.getAuditAt()), ZoneOffset.UTC));
                  localDataAuth.setStatus(dataAuth.getAuditOptionValue());
                  localDataAuth.setAuthType(useAuthType);
                  localDataAuth.setAuthValueStartAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(startAt), ZoneOffset.UTC));
                  localDataAuth.setAuthValueEndAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(endAt), ZoneOffset.UTC));
                  localDataAuth.setAuthValueAmount(useAuthNumber);
                  localDataAuth.setMetaDataId(metaDataId);
                  localDataAuth.setIdentityId(identityInfo.getIdentityId());
                  localDataAuth.setIdentityName(identityInfo.getNodeName());
                  localDataAuth.setIdentityNodeId(identityInfo.getNodeId());
                  localDataAuthList.add(localDataAuth);
            }
        }
        return localDataAuthList;
    }
}
