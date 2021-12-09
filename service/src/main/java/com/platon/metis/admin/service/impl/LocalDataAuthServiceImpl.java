package com.platon.metis.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.metis.admin.common.exception.BizException;
import com.platon.metis.admin.dao.LocalDataAuthMapper;
import com.platon.metis.admin.dao.LocalDataFileMapper;
import com.platon.metis.admin.dao.LocalMetaDataColumnMapper;
import com.platon.metis.admin.dao.LocalMetaDataMapper;
import com.platon.metis.admin.dao.entity.*;
import com.platon.metis.admin.dao.enums.DataAuthStatusEnum;
import com.platon.metis.admin.dao.enums.DataAuthTypeEnum;
import com.platon.metis.admin.grpc.client.AuthClient;
import com.platon.metis.admin.service.LocalDataAuthService;
import com.platon.metis.admin.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class LocalDataAuthServiceImpl implements LocalDataAuthService {

    @Resource
    private LocalDataAuthMapper localDataAuthMapper;
    @Resource
    private LocalMetaDataMapper localMetaDataMapper;
    @Resource
    private LocalDataFileMapper localDataFileMapper;
    @Resource
    private LocalMetaDataColumnMapper localMetaDataColumnMapper;
    @Resource
    private AuthClient authClient;

    @Override
    public Page<LocalDataAuth> listLocalDataAuth(int pageNo, int pageSize, int status, String keyword) {

        Page<LocalDataAuth> dataAuthPage = PageHelper.startPage(pageNo, pageSize);
        localDataAuthMapper.selectDataAuthList(status, keyword);
        return dataAuthPage;
    }

    @Override
    public List<LocalDataAuth> listAll() {
        return localDataAuthMapper.listAll();
    }

    @Override
    public int selectFinishAuthCount() {
        return localDataAuthMapper.selectFinishAuthCount();
    }

    @Override
    public int selectUnfinishAuthCount() {
        return localDataAuthMapper.selectUnfinishAuthCount();
    }

    @Override
    public LocalDataAuthDetail detail(String authId) {

        LocalDataAuth localDataAuth = localDataAuthMapper.selectByPrimaryKey(authId);
        if(Objects.isNull(localDataAuth) || Objects.isNull(localDataAuth.getMetaDataId()) || "".equals(localDataAuth.getMetaDataId())){
             throw new ServiceException("metaDataID异常，不可为空");
        }

        LocalMetaData localMetaData = localMetaDataMapper.selectByMetaDataId(localDataAuth.getMetaDataId());
        if(localMetaData==null){
            throw new ServiceException("metadata not found");
        }
        List<LocalMetaDataColumn> localMetaDataColumnList =  localMetaDataColumnMapper.selectByLocalMetaDataDbId(localMetaData.getId());

        LocalDataFile localDataFile = localDataFileMapper.selectByFileId(localMetaData.getFileId());


        LocalDataAuthDetail localDataAuthDetail = new LocalDataAuthDetail();
        localDataAuthDetail.setLocalDataAuth(localDataAuth);
        localDataAuthDetail.setLocalDataFile(localDataFile);
        localDataAuthDetail.setLocalMetaData(localMetaData);
        localDataAuthDetail.setLocalMetaDataColumnList(localMetaDataColumnList);

        return localDataAuthDetail;
    }

    @Override
    public void agreeAuth(String authId) {

        LocalDataAuth localDataAuth = localDataAuthMapper.selectByPrimaryKey(authId);
        if(Objects.isNull(localDataAuth)){
           throw new ServiceException("id可能无效,请检查");
        }
        if(localDataAuth.getStatus() != DataAuthStatusEnum.PENDING.getStatus()){
            throw new ServiceException("此数据已经授权过,不能重复授权");
        }

        int auditOption = DataAuthStatusEnum.AGREE.getStatus();
        String auditDesc = "";
        //过期了
        if(localDataAuth.getAuthType() == DataAuthTypeEnum.TIME_PERIOD.type && localDataAuth.getAuthValueEndAt().isBefore(LocalDateTime.now(ZoneOffset.UTC))){
            log.warn("data auth request is expired, just refuse it.");
            auditOption =  DataAuthStatusEnum.REFUSE.getStatus();
            auditDesc = "data auth request is expired, just refuse it.";
        }

        try {
            authClient.auditMetaData(localDataAuth.getAuthId(), auditOption, auditDesc);
        } catch (BizException e) {
            log.error("审批元数据授权申请失败:" , e);
            throw new ServiceException("审批元数据授权申请失败：" + e.getMessage());
        }

        LocalDataAuth dataAuth = new LocalDataAuth();
        dataAuth.setAuthId(authId);
        dataAuth.setStatus(auditOption);
        dataAuth.setAuthAt(LocalDateTime.now());
        localDataAuthMapper.updateByPrimaryKeySelective(dataAuth);
    }

    @Override
    public boolean autoApproveDataAuth(LocalDataAuth localDataAuth) {
        if(localDataAuth.getStatus() != DataAuthStatusEnum.PENDING.getStatus()){
            log.warn("dataAuthRequest will be ignored because it is audited. authId:{}", localDataAuth.getAuthId());
            return false;
        }
        int auditOption = DataAuthStatusEnum.AGREE.getStatus();
        String auditDesc = "";
        //过期了
        if(localDataAuth.getAuthType() == DataAuthTypeEnum.TIME_PERIOD.type && localDataAuth.getAuthValueEndAt().isBefore(LocalDateTime.now(ZoneOffset.UTC))){
            log.warn("dataAuthRequest is refused because it is expired. authId:{}", localDataAuth.getAuthId());
            auditOption =  DataAuthStatusEnum.REFUSE.getStatus();
            auditDesc = "data auth request is expired.";
        }
        AuthRpcMessage.AuditMetadataAuthorityResponse response = authClient.auditMetaData(localDataAuth.getAuthId(), auditOption, auditDesc);

        if(response.getStatus()!=GrpcConstant.GRPC_SUCCESS_CODE){
            log.warn("Carrier audit dataAuthRequest error. authId:{}", localDataAuth.getAuthId());
        }
        //设置最后结果是：同意，还是拒绝
        localDataAuth.setStatus(auditOption);
        localDataAuth.setAuthAt(LocalDateTime.now());
        //上面设置的最后结果，只有在return true时才有效
        return response.getStatus() == GrpcConstant.GRPC_SUCCESS_CODE;
    }

    @Override
    public void refuseAuth(String authId) {

        LocalDataAuth localDataAuth = localDataAuthMapper.selectByPrimaryKey(authId);
        if(Objects.isNull(localDataAuth)){
            throw new ServiceException("id可能无效,请检查");
        }
        if(localDataAuth.getStatus() != DataAuthStatusEnum.PENDING.getStatus()){
            log.warn("data auth request is processed already.");
            throw new ServiceException("此数据已经授权过,不能重复授权");
        }
        try {
            authClient.auditMetaData(localDataAuth.getAuthId(), DataAuthStatusEnum.REFUSE.getStatus(), "");
        } catch (BizException e) {
            log.error("拒绝元数据授权申请失败:" , e);
            throw new ServiceException("拒绝元数据授权申请失败：" + e.getMessage());
        }

        LocalDataAuth dataAuth = new LocalDataAuth();
        dataAuth.setAuthId(authId);
        dataAuth.setStatus(DataAuthStatusEnum.REFUSE.getStatus());
        dataAuth.setAuthAt(LocalDateTime.now());
        localDataAuthMapper.updateByPrimaryKeySelective(dataAuth);
    }
}
