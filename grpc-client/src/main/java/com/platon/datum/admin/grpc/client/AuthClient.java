package com.platon.datum.admin.grpc.client;

import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.CallGrpcServiceFailed;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.entity.DataAuth;
import com.platon.datum.admin.dao.entity.GlobalOrg;
import com.platon.datum.admin.grpc.carrier.api.AuthRpcApi;
import com.platon.datum.admin.grpc.carrier.api.AuthServiceGrpc;
import com.platon.datum.admin.grpc.carrier.types.Common;
import com.platon.datum.admin.grpc.carrier.types.IdentityData;
import com.platon.datum.admin.grpc.carrier.types.Metadata;
import com.platon.datum.admin.grpc.channel.SimpleChannelManager;
import com.platon.datum.admin.grpc.common.constant.CarrierEnum;
import com.platon.datum.admin.grpc.constant.GrpcConstant;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


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
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        IdentityData.Organization orgInfo = IdentityData.Organization
                .newBuilder()
                .setNodeName(name)
                .setIdentityId(identityId)
                .setImageUrl(StringUtils.trimToEmpty(imageUrl))
                .setDetails(StringUtils.trimToEmpty(profile))
                .build();
        AuthRpcApi.ApplyIdentityJoinRequest joinRequest = AuthRpcApi.ApplyIdentityJoinRequest
                .newBuilder()
                .setInformation(orgInfo)
                .build();
        log.debug("applyIdentityJoin,request:{}",joinRequest);
        //3.调用rpc,获取response
        Common.SimpleResponse response = AuthServiceGrpc.newBlockingStub(channel).applyIdentityJoin(joinRequest);
        log.debug("applyIdentityJoin,response:{}",response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
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
        log.debug("revokeIdentityJoin,request:{}",request);
        //3.调用rpc,获取response
        Common.SimpleResponse response = AuthServiceGrpc.newBlockingStub(channel).revokeIdentityJoin(request);
        log.debug("revokeIdentityJoin,request:{}",response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
    }


    /**
     * 获取数据授权申请列表
     *
     * @return
     */
    public List<DataAuth> getMetaDataAuthorityList(LocalDateTime latestSynced) throws BizException {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        AuthRpcApi.GetMetadataAuthorityListRequest request = AuthRpcApi.GetMetadataAuthorityListRequest
                .newBuilder()
                .setLastUpdated(LocalDateTimeUtil.getTimestamp(latestSynced))
                .setPageSize(GrpcConstant.PageSize)
                .build();
        log.debug("getMetaDataAuthorityList,request:{}",request);
        //3.调用rpc,获取response
        AuthRpcApi.GetMetadataAuthorityListResponse response = AuthServiceGrpc.newBlockingStub(channel).getLocalMetadataAuthorityList(request);
        log.debug("getMetaDataAuthorityList,response:{}",response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }
        log.debug("从carrier查询数据授权申请列表，数量：{}", response.getMetadataAuthsList().size());
        return dataConvertToAuthList(response);
    }

    //可以单独设置每个grpc请求的超时
    public List<DataAuth> getMetaDataAuthorityList(LocalDateTime latestSynced, long timeout) throws BizException {
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();
        //2.拼装request
        AuthRpcApi.GetMetadataAuthorityListRequest request = AuthRpcApi.GetMetadataAuthorityListRequest
                .newBuilder()
                .setLastUpdated(LocalDateTimeUtil.getTimestamp(latestSynced))
                .build();
        //3.调用rpc,获取response
        AuthRpcApi.GetMetadataAuthorityListResponse response = AuthServiceGrpc
                .newBlockingStub(channel)
                .withDeadlineAfter(timeout, TimeUnit.SECONDS)
                .getLocalMetadataAuthorityList(request);

        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
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
        //1.获取rpc连接
        ManagedChannel channel = channelManager.getCarrierChannel();

        //2.拼装request
        CarrierEnum.AuditMetadataOption auditDataStatus = CarrierEnum.AuditMetadataOption.forNumber(auditOption);
        AuthRpcApi.AuditMetadataAuthorityRequest request = AuthRpcApi
                .AuditMetadataAuthorityRequest
                .newBuilder()
                .setMetadataAuthId(metaDataAuthId)
                .setAudit(auditDataStatus)
                .setSuggestion(auditDesc)
                .build();
        log.debug("auditMetaData,request:{}",request);
        //3.调用rpc,获取response
        AuthRpcApi.AuditMetadataAuthorityResponse response = AuthServiceGrpc.newBlockingStub(channel).auditMetadataAuthority(request);
        log.debug("auditMetaData,response:{}",response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

    }

    public List<GlobalOrg> getIdentityList(LocalDateTime latestSynced) {
        //1.获取rpc连接
        Channel channel = channelManager.getCarrierChannel();

        //2.拼装request
        AuthRpcApi.GetIdentityListRequest request = AuthRpcApi.GetIdentityListRequest
                .newBuilder()
                .setLastUpdated(LocalDateTimeUtil.getTimestamp(latestSynced))
                .setPageSize(GrpcConstant.PageSize)
                .build();
        log.debug("getIdentityList,request:{}",request);
        //3.调用rpc,获取response
        AuthRpcApi.GetIdentityListResponse response = AuthServiceGrpc.newBlockingStub(channel).getIdentityList(request);
        log.debug("getIdentityList,response:{}",response);
        //4.处理response
        if (response == null) {
            throw new CallGrpcServiceFailed();
        } else if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            throw new CallGrpcServiceFailed(response.getMsg());
        }

        log.debug("从carrier查询全部组织列表，数量:{}", response.getIdentitysList().size());

        return identityListConvertToGlobalOrg(response.getIdentitysList());
    }

    private List<GlobalOrg> identityListConvertToGlobalOrg(List<IdentityData.Organization> identitysList) {
        List<GlobalOrg> orgList = identitysList.stream()
                .map(organization -> {
                    GlobalOrg globalOrg = new GlobalOrg();
                    globalOrg.setIdentityId(organization.getIdentityId());
                    globalOrg.setIdentityType(organization.getIdentityTypeValue());
                    globalOrg.setNodeId(organization.getNodeId());
                    globalOrg.setNodeName(organization.getNodeName());
                    globalOrg.setStatus(organization.getStatusValue());
                    globalOrg.setImageUrl(organization.getImageUrl());
                    globalOrg.setDetails(organization.getDetails());
                    globalOrg.setRecUpdateTime(LocalDateTimeUtil.getLocalDateTime(organization.getUpdateAt()));
                    return globalOrg;
                })
                .collect(Collectors.toList());
        return orgList;
    }


    private List<DataAuth> dataConvertToAuthList(AuthRpcApi.GetMetadataAuthorityListResponse response) {
        List<DataAuth> dataAuthList = new ArrayList<>();
        List<Metadata.MetadataAuthorityDetail> metaDataAuthorityList = response.getMetadataAuthsList();
        if (!CollectionUtils.isEmpty(metaDataAuthorityList)) {
            for (Metadata.MetadataAuthorityDetail dataAuth : metaDataAuthorityList) {
                //元数据使用授权
                Metadata.MetadataAuthority metaDataAuthority = dataAuth.getAuth();
                IdentityData.Organization identityInfo = metaDataAuthority.getOwner();
                String metaDataId = metaDataAuthority.getMetadataId();
                Metadata.MetadataUsageRule metadataUsageRule = metaDataAuthority.getUsageRule();
                long startAt = metadataUsageRule.getStartAt();
                long endAt = metadataUsageRule.getEndAt();
                int useAuthNumber = metadataUsageRule.getTimes();
                int useAuthType = metadataUsageRule.getUsageTypeValue();

                DataAuth localDataAuth = new DataAuth();
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
                        case CarrierEnum.MetadataAuthorityState.MAState_Unknown_VALUE:
                            localDataAuth.setStatus(0);
                            break;
                        case CarrierEnum.MetadataAuthorityState.MAState_Created_VALUE:
                            localDataAuth.setStatus(0);
                            break;
                        case CarrierEnum.MetadataAuthorityState.MAState_Released_VALUE:
                            localDataAuth.setStatus(0);
                            break;
                        case CarrierEnum.MetadataAuthorityState.MAState_Revoked_VALUE:
                            localDataAuth.setStatus(2);
                            break;
                        case CarrierEnum.MetadataAuthorityState.MAState_Invalid_VALUE:
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

                dataAuthList.add(localDataAuth);
            }
        }
        return dataAuthList;
    }
}