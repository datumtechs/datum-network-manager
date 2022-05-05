package com.platon.metis.admin.grpc.client;

import com.platon.metis.admin.common.exception.BizException;
import com.platon.metis.admin.common.exception.CallGrpcServiceFailed;
import com.platon.metis.admin.common.util.LocalDateTimeUtil;
import com.platon.metis.admin.dao.entity.LocalDataAuth;
import com.platon.metis.admin.grpc.channel.SimpleChannelManager;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.service.AuthRpcMessage;
import com.platon.metis.admin.grpc.service.AuthServiceGrpc;
import com.platon.metis.admin.grpc.types.Base;
import com.platon.metis.admin.grpc.types.Metadata;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
     *
     * @param identityId // 组织的身份标识Id
     * @param name       // 组织名称
     */
    public void applyIdentityJoin(String identityId, String name, String imageUrl, String profile) {
        log.debug("从carrier申请入网，identityId:{}", identityId);
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        Base.Organization orgInfo = Base.Organization
                .newBuilder()
                .setNodeName(name)
                .setIdentityId(identityId)
                .setImageUrl(StringUtils.trimToEmpty(imageUrl))
                .setDetails(StringUtils.trimToEmpty(profile))
                .build();
        AuthRpcMessage.ApplyIdentityJoinRequest joinRequest = AuthRpcMessage.ApplyIdentityJoinRequest
                .newBuilder()
                .setInformation(orgInfo)
                .build();

        //3.调用rpc,获取response
        Base.SimpleResponse response = AuthServiceGrpc.newBlockingStub(channel).applyIdentityJoin(joinRequest);
        //4.处理response

        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }

    /**
     * 注销准入网络
     */
    public void revokeIdentityJoin() throws BizException {
        log.debug("从carrier注销入网");
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        com.google.protobuf.Empty request = com.google.protobuf.Empty
                .newBuilder()
                .build();
        //3.调用rpc,获取response
        Base.SimpleResponse response = AuthServiceGrpc.newBlockingStub(channel).revokeIdentityJoin(request);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }


    /**
     * 获取数据授权申请列表
     *
     * @return
     */
    public List<LocalDataAuth> getMetaDataAuthorityList(LocalDateTime latestSynced) throws BizException {
        log.debug("从carrier查询数据授权申请列表，latestSynced：{}", latestSynced);
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
        } else if (response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        log.debug("从carrier查询数据授权申请列表，数量：{}", response.getMetadataAuthsList().size());
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
        } else if (response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        return dataConvertToAuthList(response);

    }

    /**
     * 数据授权审核
     *
     * @param metaDataAuthId ：元数据授权申请Id
     * @param auditOption    ：授权数据状态：0：等待授权审核，1:同意， 2:拒绝
     *                       param auditDesc
     * @return
     */
    public void auditMetaData(String metaDataAuthId, int auditOption, String auditDesc) throws BizException {
        log.debug("从carrier审核数据授权申请，metaDataAuthId：{}， auditOption：{}，auditDesc：{}", metaDataAuthId, auditOption, auditDesc);
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();

        //2.拼装request
        Base.AuditMetadataOption auditDataStatus = Base.AuditMetadataOption.forNumber(auditOption);
        AuthRpcMessage.AuditMetadataAuthorityRequest request = AuthRpcMessage
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
        } else if (response.getStatus() != GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

    }


    private List<LocalDataAuth> dataConvertToAuthList(AuthRpcMessage.GetMetadataAuthorityListResponse response) {
        List<LocalDataAuth> localDataAuthList = new ArrayList<>();
        List<Metadata.MetadataAuthorityDetail> metaDataAuthorityList = response.getMetadataAuthsList();
        if (!CollectionUtils.isEmpty(metaDataAuthorityList)) {
            for (Metadata.MetadataAuthorityDetail dataAuth : metaDataAuthorityList) {
                //元数据使用授权
                Metadata.MetadataAuthority metaDataAuthority = dataAuth.getAuth();
                Base.Organization identityInfo = metaDataAuthority.getOwner();
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
                localDataAuth.setCreateAt(LocalDateTimeUtil.getLocalDateTime(dataAuth.getApplyAt()));
                localDataAuth.setAuthAt(LocalDateTimeUtil.getLocalDateTime(dataAuth.getAuditAt()));
                /**
                 * {@link Base.AuditMetadataOption}
                 */
                if (dataAuth.getAuditOptionValue() == 0) { //等待审核
                    /**
                     * * {@link Base.MetadataAuthorityState}
                     * 数据授权信息的状态 (
                     * 0: 未知;
                     * 1: 还未发布的数据授权;
                     * 2: 已发布的数据授权;
                     * 3: 已撤销的数据授权 <失效前主动撤回的>;
                     * 4: 已经失效的数据授权 <过期or达到使用上限的or被拒绝的>;)
                     * **/
                    int stateValue = dataAuth.getStateValue();
                    switch (stateValue) {
                        case Base.MetadataAuthorityState.MAState_Unknown_VALUE:
                            localDataAuth.setStatus(0);
                            break;
                        case Base.MetadataAuthorityState.MAState_Created_VALUE:
                            localDataAuth.setStatus(0);
                            break;
                        case Base.MetadataAuthorityState.MAState_Released_VALUE:
                            localDataAuth.setStatus(0);
                            break;
                        case Base.MetadataAuthorityState.MAState_Revoked_VALUE:
                            localDataAuth.setStatus(2);
                            break;
                        case Base.MetadataAuthorityState.MAState_Invalid_VALUE:
                            localDataAuth.setStatus(3);
                            break;
                    }
                } else if (dataAuth.getAuditOptionValue() == 1 || dataAuth.getAuditOptionValue() == 2) {
                    //'授权数据状态：0：等待授权审核，1:同意， 2:拒绝，3:失效(auth_type=1且auth_value_end_at超时) ',
                    localDataAuth.setStatus(dataAuth.getAuditOptionValue());
                }

                localDataAuth.setRecUpdateTime(LocalDateTimeUtil.getLocalDateTime(dataAuth.getUpdateAt()));
                localDataAuth.setRecCreateTime(LocalDateTimeUtil.getLocalDateTime(dataAuth.getApplyAt()));

                localDataAuth.setAuthType(useAuthType);
                localDataAuth.setAuthValueStartAt(LocalDateTimeUtil.getLocalDateTime(startAt));
                localDataAuth.setAuthValueEndAt(LocalDateTimeUtil.getLocalDateTime(endAt));
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