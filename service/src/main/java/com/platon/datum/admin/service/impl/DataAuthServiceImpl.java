package com.platon.datum.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.DataAuthMapper;
import com.platon.datum.admin.dao.DataFileMapper;
import com.platon.datum.admin.dao.MetaDataColumnMapper;
import com.platon.datum.admin.dao.MetaDataMapper;
import com.platon.datum.admin.dao.entity.*;
import com.platon.datum.admin.dao.enums.DataAuthStatusEnum;
import com.platon.datum.admin.dao.enums.DataAuthTypeEnum;
import com.platon.datum.admin.grpc.client.AuthClient;
import com.platon.datum.admin.service.DataAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class DataAuthServiceImpl implements DataAuthService {

    @Resource
    private DataAuthMapper dataAuthMapper;
    @Resource
    private MetaDataMapper metaDataMapper;
    @Resource
    private DataFileMapper dataFileMapper;
    @Resource
    private MetaDataColumnMapper metaDataColumnMapper;
    @Resource
    private AuthClient authClient;

    @Override
    public Page<DataAuth> listLocalDataAuth(int pageNo, int pageSize, int status, String keyword) {

        Page<DataAuth> dataAuthPage = PageHelper.startPage(pageNo, pageSize);
        dataAuthMapper.selectDataAuthList(status, keyword);
        return dataAuthPage;
    }

    @Override
    public List<DataAuth> listAll() {
        return dataAuthMapper.listAll();
    }

    @Override
    public int selectFinishAuthCount() {
        return dataAuthMapper.selectFinishAuthCount();
    }

    @Override
    public int selectUnfinishAuthCount() {
        return dataAuthMapper.selectUnfinishAuthCount();
    }

    @Override
    public DataAuthDetail detail(String authId) {

        DataAuth dataAuth = dataAuthMapper.selectByPrimaryKey(authId);
        if (Objects.isNull(dataAuth) || Objects.isNull(dataAuth.getMetaDataId()) || "".equals(dataAuth.getMetaDataId())) {
            throw new BizException(Errors.QueryRecordNotExist);
        }

        MetaData metaData = metaDataMapper.selectByMetaDataId(dataAuth.getMetaDataId());
        if (metaData == null) {
            throw new BizException(Errors.QueryRecordNotExist);
        }
        List<MetaDataColumn> metaDataColumnList = metaDataColumnMapper.selectByLocalMetaDataDbId(metaData.getId());

        DataFile dataFile = dataFileMapper.selectByFileId(metaData.getFileId());


        DataAuthDetail dataAuthDetail = new DataAuthDetail();
        dataAuthDetail.setDataAuth(dataAuth);
        dataAuthDetail.setDataFile(dataFile);
        dataAuthDetail.setMetaData(metaData);
        dataAuthDetail.setMetaDataColumnList(metaDataColumnList);

        return dataAuthDetail;
    }

    @Override
    public void agreeAuth(String authId) {

        DataAuth localDataAuth = dataAuthMapper.selectByPrimaryKey(authId);
        if (Objects.isNull(localDataAuth)) {
            throw new ArithmeticException();
        }
        if (localDataAuth.getStatus() != DataAuthStatusEnum.PENDING.getStatus()) {
            throw new BizException(Errors.MetadataAuthorized);
        }

        int auditOption = DataAuthStatusEnum.AGREE.getStatus();
        String auditDesc = "";

        MetaData metaData = metaDataMapper.selectByMetaDataId(localDataAuth.getMetaDataId());
        //过期了
        if (localDataAuth.getAuthType() == DataAuthTypeEnum.TIME_PERIOD.type && localDataAuth.getAuthValueEndAt().isBefore(LocalDateTime.now(ZoneOffset.UTC))) {
            log.warn("data auth request is expired, just refuse it.");
            auditOption = DataAuthStatusEnum.REFUSE.getStatus();
            auditDesc = "data auth request is expired, just refuse it.";
        } else if (metaData == null || metaData.getStatus() == 3) {
            log.warn("meta data was revoked, just refuse it.");
            auditOption = DataAuthStatusEnum.REFUSE.getStatus();
            auditDesc = "meta data was revoked, just refuse it.";
        }

        authClient.auditMetaData(localDataAuth.getAuthId(), auditOption, auditDesc);

        DataAuth dataAuth = new DataAuth();
        dataAuth.setAuthId(authId);
        dataAuth.setStatus(auditOption);
        dataAuth.setAuthAt(LocalDateTimeUtil.now());
        dataAuthMapper.updateByPrimaryKeySelective(dataAuth);
    }

    @Override
    public void refuseAuth(String authId) {

        DataAuth localDataAuth = dataAuthMapper.selectByPrimaryKey(authId);
        if (Objects.isNull(localDataAuth)) {
            throw new ArithmeticException();
        }
        if (localDataAuth.getStatus() != DataAuthStatusEnum.PENDING.getStatus()) {
            throw new BizException(Errors.MetadataAuthorized, "Data auth request is processed already.");
        }
        authClient.auditMetaData(localDataAuth.getAuthId(), DataAuthStatusEnum.REFUSE.getStatus(), "");

        DataAuth dataAuth = new DataAuth();
        dataAuth.setAuthId(authId);
        dataAuth.setStatus(DataAuthStatusEnum.REFUSE.getStatus());
        dataAuth.setAuthAt(LocalDateTimeUtil.now());
        dataAuthMapper.updateByPrimaryKeySelective(dataAuth);
    }
}