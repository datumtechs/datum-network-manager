package com.platon.metis.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.metis.admin.dao.LocalDataAuthMapper;
import com.platon.metis.admin.dao.LocalDataFileMapper;
import com.platon.metis.admin.dao.LocalMetaDataColumnMapper;
import com.platon.metis.admin.dao.LocalMetaDataMapper;
import com.platon.metis.admin.dao.entity.*;
import com.platon.metis.admin.dao.enums.DataAuthStatusEnum;
import com.platon.metis.admin.grpc.client.AuthClient;
import com.platon.metis.admin.grpc.constant.GrpcConstant;
import com.platon.metis.admin.grpc.entity.CommonResp;
import com.platon.metis.admin.service.LocalDataAuthService;
import com.platon.metis.admin.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

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
    public int selectFinishAuthCount() {
        return localDataAuthMapper.selectFinishAuthCount();
    }

    @Override
    public int selectUnfinishAuthCount() {
        return localDataAuthMapper.selectUnfinishAuthCount();
    }

    @Override
    public LocalDataAuthDetail detail(int id) {

        LocalDataAuth localDataAuth = localDataAuthMapper.selectByPrimaryKey(id);
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
    public int agreeAuth(int id) {

        LocalDataAuth localDataAuth = localDataAuthMapper.selectByPrimaryKey(id);
        if(Objects.isNull(localDataAuth)){
           throw new ServiceException("id可能无效,请检查");
        }
        if(localDataAuth.getStatus() != DataAuthStatusEnum.PENDING.getStatus()){
            throw new ServiceException("此数据已经授权过,不能重复授权");
        }
        CommonResp commonResp = authClient.auditMetaData(localDataAuth.getAuthId(), DataAuthStatusEnum.AGREE.getStatus());
        if(commonResp.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE){
           throw new ServiceException("调用rpc授权失败" + commonResp.getMsg());
        }

        LocalDataAuth dataAuth = new LocalDataAuth();
        dataAuth.setId(id);
        dataAuth.setStatus(DataAuthStatusEnum.AGREE.getStatus());
        dataAuth.setAuthAt(LocalDateTime.now());
        AtomicInteger count = new AtomicInteger();
        count.getAndAdd(localDataAuthMapper.updateByPrimaryKeySelective(dataAuth));
        return count.get();
    }

    @Override
    public int refuseAuth(int id) {

        LocalDataAuth localDataAuth = localDataAuthMapper.selectByPrimaryKey(id);
        if(Objects.isNull(localDataAuth)){
            throw new ServiceException("id可能无效,请检查");
        }
        if(localDataAuth.getStatus() != DataAuthStatusEnum.PENDING.getStatus()){
            throw new ServiceException("此数据已经授权过,不能重复授权");
        }
        CommonResp commonResp = authClient.auditMetaData(localDataAuth.getAuthId(), DataAuthStatusEnum.REFUSE.getStatus());
        if(commonResp.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE){
            throw new ServiceException("调用rpc授权失败" + commonResp.getMsg());
        }

        LocalDataAuth dataAuth = new LocalDataAuth();
        dataAuth.setId(id);
        dataAuth.setStatus(DataAuthStatusEnum.REFUSE.getStatus());
        dataAuth.setAuthAt(LocalDateTime.now());
        AtomicInteger count = new AtomicInteger();
        count.getAndAdd(localDataAuthMapper.updateByPrimaryKeySelective(dataAuth));
        return count.get();
    }
}
