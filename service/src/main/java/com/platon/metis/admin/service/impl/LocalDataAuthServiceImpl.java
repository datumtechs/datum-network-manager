package com.platon.metis.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.metis.admin.common.exception.MetadataAuthorized;
import com.platon.metis.admin.common.exception.ObjectNotFound;
import com.platon.metis.admin.dao.LocalDataAuthMapper;
import com.platon.metis.admin.dao.LocalDataFileMapper;
import com.platon.metis.admin.dao.LocalMetaDataColumnMapper;
import com.platon.metis.admin.dao.LocalMetaDataMapper;
import com.platon.metis.admin.dao.entity.*;
import com.platon.metis.admin.dao.enums.DataAuthStatusEnum;
import com.platon.metis.admin.dao.enums.DataAuthTypeEnum;
import com.platon.metis.admin.grpc.client.AuthClient;
import com.platon.metis.admin.service.LocalDataAuthService;
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
            throw new ObjectNotFound();
        }

        LocalMetaData localMetaData = localMetaDataMapper.selectByMetaDataId(localDataAuth.getMetaDataId());
        if(localMetaData==null){
            throw new ObjectNotFound();
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
            throw new ArithmeticException();
        }
        if(localDataAuth.getStatus() != DataAuthStatusEnum.PENDING.getStatus()){
            throw new MetadataAuthorized();
        }

        int auditOption = DataAuthStatusEnum.AGREE.getStatus();
        String auditDesc = "";

        LocalMetaData localMetaData = localMetaDataMapper.selectByMetaDataId(localDataAuth.getMetaDataId());
        //过期了
        if(localDataAuth.getAuthType() == DataAuthTypeEnum.TIME_PERIOD.type && localDataAuth.getAuthValueEndAt().isBefore(LocalDateTime.now(ZoneOffset.UTC))){
            log.warn("data auth request is expired, just refuse it.");
            auditOption =  DataAuthStatusEnum.REFUSE.getStatus();
            auditDesc = "data auth request is expired, just refuse it.";
        } else if (localMetaData == null || localMetaData.getStatus() == 3){
            log.warn("meta data was revoked, just refuse it.");
            auditOption =  DataAuthStatusEnum.REFUSE.getStatus();
            auditDesc = "meta data was revoked, just refuse it.";
        }

        authClient.auditMetaData(localDataAuth.getAuthId(), auditOption, auditDesc);

        LocalDataAuth dataAuth = new LocalDataAuth();
        dataAuth.setAuthId(authId);
        dataAuth.setStatus(auditOption);
        dataAuth.setAuthAt(LocalDateTime.now());
        localDataAuthMapper.updateByPrimaryKeySelective(dataAuth);
    }

    @Override
    public void refuseAuth(String authId) {

        LocalDataAuth localDataAuth = localDataAuthMapper.selectByPrimaryKey(authId);
        if(Objects.isNull(localDataAuth)){
            throw new ArithmeticException();
        }
        if(localDataAuth.getStatus() != DataAuthStatusEnum.PENDING.getStatus()){
            log.warn("data auth request is processed already.");
            throw new MetadataAuthorized();
        }
        authClient.auditMetaData(localDataAuth.getAuthId(), DataAuthStatusEnum.REFUSE.getStatus(), "");

        LocalDataAuth dataAuth = new LocalDataAuth();
        dataAuth.setAuthId(authId);
        dataAuth.setStatus(DataAuthStatusEnum.REFUSE.getStatus());
        dataAuth.setAuthAt(LocalDateTime.now());
        localDataAuthMapper.updateByPrimaryKeySelective(dataAuth);
    }
}