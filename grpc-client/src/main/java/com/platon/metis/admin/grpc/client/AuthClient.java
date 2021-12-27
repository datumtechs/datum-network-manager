package com.platon.metis.admin.grpc.client;

import com.platon.metis.admin.common.exception.BizException;
import com.platon.metis.admin.common.exception.CallGrpcServiceFailed;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import com.platon.metis.admin.grpc.channel.SimpleChannelManager;
import com.platon.metis.admin.grpc.common.CommonBase;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.service.AuthRpcMessage;
import com.platon.metis.admin.grpc.service.AuthServiceGrpc;
import com.platon.metis.admin.grpc.types.Metadata;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.platon.metis.admin.grpc.constant.GrpcConstant.GRPC_SUCCESS_CODE;


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

    @Resource
    private SimpleChannelManager channelManager;


    /**
     * 申请准入网络
     * @param identityId // 组织的身份标识Id
     * @param name               // 组织名称
     */
    public void applyIdentityJoin(String identityId,String name, String imageUrl, String profile) {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        CommonBase.Organization orgInfo = CommonBase.Organization
                .newBuilder()
                .setNodeName(name)
                .setIdentityId(identityId)
                .setImageUrl(imageUrl)
                .setDetails(profile)
                .build();
        AuthRpcMessage.ApplyIdentityJoinRequest joinRequest = AuthRpcMessage.ApplyIdentityJoinRequest
                .newBuilder()
                .setMember(orgInfo)
                .build();

        //3.调用rpc,获取response
        CommonBase.SimpleResponse response = AuthServiceGrpc.newBlockingStub(channel).applyIdentityJoin(joinRequest);
        //4.处理response

        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }

    /**
     * 注销准入网络
     */
    public void revokeIdentityJoin() throws BizException {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        com.google.protobuf.Empty request = com.google.protobuf.Empty
                .newBuilder()
                .build();
        //3.调用rpc,获取response
        CommonBase.SimpleResponse response = AuthServiceGrpc.newBlockingStub(channel).revokeIdentityJoin(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }


    /**
     * 获取数据授权申请列表
     * @return
     */
    public List<LocalDataAuth> getMetaDataAuthorityList(LocalDateTime latestSynced) throws BizException {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        AuthRpcMessage.GetMetadataAuthorityListRequest request = AuthRpcMessage.GetMetadataAuthorityListRequest
                .newBuilder()
                .setLastUpdated(latestSynced.toInstant(ZoneOffset.UTC).toEpochMilli())
                .setPageSize(GrpcConstant.PageSize)
                .build();
        //3.调用rpc,获取response
        AuthRpcMessage.GetMetadataAuthorityListResponse response = AuthServiceGrpc.newBlockingStub(channel).getLocalMetadataAuthorityList(request);

        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return dataConvertToAuthList(response);
    }

    //可以单独设置每个grpc请求的超时
    public List<LocalDataAuth> getMetaDataAuthorityList(LocalDateTime latestSynced, long timeout) throws BizException {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        AuthRpcMessage.GetMetadataAuthorityListRequest request = AuthRpcMessage.GetMetadataAuthorityListRequest
                .newBuilder()
                .setLastUpdated(latestSynced.toInstant(ZoneOffset.UTC).toEpochMilli())
                .build();
        //3.调用rpc,获取response
        AuthRpcMessage.GetMetadataAuthorityListResponse response = AuthServiceGrpc
                .newBlockingStub(channel)
                .withDeadlineAfter(timeout, TimeUnit.SECONDS)
                .getLocalMetadataAuthorityList(request);

        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return dataConvertToAuthList(response);

    }

    /**
     * 数据授权审核
     * @param metaDataAuthId ：元数据授权申请Id
     * @param auditOption ：授权数据状态：0：等待授权审核，1:同意， 2:拒绝
     * param auditDesc
     * @return
     */
    public void auditMetaData(String metaDataAuthId, int auditOption, String auditDesc) throws BizException {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();

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
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        }else if(response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
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