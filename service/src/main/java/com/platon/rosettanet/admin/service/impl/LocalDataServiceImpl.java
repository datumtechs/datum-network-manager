package com.platon.rosettanet.admin.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.dao.LocalDataFileMapper;
import com.platon.rosettanet.admin.dao.LocalMetaDataColumnMapper;
import com.platon.rosettanet.admin.dao.LocalOrgMapper;
import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import com.platon.rosettanet.admin.dao.entity.LocalOrg;
import com.platon.rosettanet.admin.grpc.client.DataProviderClient;
import com.platon.rosettanet.admin.grpc.client.YarnClient;
import com.platon.rosettanet.admin.grpc.entity.AvailableDataNodeResp;
import com.platon.rosettanet.admin.grpc.entity.UploadDataResp;
import com.platon.rosettanet.admin.service.LocalDataService;
import com.platon.rosettanet.admin.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Service
public class LocalDataServiceImpl implements LocalDataService {

    @Resource
    private LocalDataFileMapper localDataFileMapper;

    @Resource
    private LocalMetaDataColumnMapper localMetaDataColumnMapper;
    @Resource
    private YarnClient yarnClient;
    @Resource
    private DataProviderClient dataProviderClient;
    @Resource
    private LocalOrgMapper localOrgMapper;

    @Override
    public Page<LocalDataFile> listDataFile(int pageNo, int pageSize) {
        Page<LocalDataFile> localDataFilePage = PageHelper.startPage(pageNo, pageSize);
        localDataFileMapper.listDataFile();
        return localDataFilePage;
    }

    @Transactional
    @Override
    public void uploadFile(MultipartFile file,boolean hasTitle) {
        //### 1.从调度服务获取数据节点的连接
        LocalOrg carrier = localOrgMapper.selectAvailableCarrier();
        if(carrier == null){
            new ServiceException("无可用的调度服务");
        }
        AvailableDataNodeResp availableDataNode = yarnClient.getAvailableDataNode(carrier.getCarrierIP(), carrier.getCarrierPort());
        //### 2.上传源文件
        UploadDataResp uploadDataResp = null;
        String fileName = file.getOriginalFilename();
        try {
            uploadDataResp = dataProviderClient.uploadData(
                    availableDataNode.getIp(),
                    availableDataNode.getPort(),
                    fileName,
                    file.getBytes());
        } catch (IOException e) {
            throw new ServiceException("上传文件失败",e);
        }
        //### 3.将源文件信息存库
        LocalDataFile localDataFile = new LocalDataFile();
        /*localDataFile.setIdentityId();
        localDataFile.setFileId(uploadDataResp.getFileId());
        localDataFile.setFileName(fileName);
        localDataFile.setFilePath(uploadDataResp.getFilePath());
        localDataFile.setFileType("csv");
        localDataFile.setResourceName();
        localDataFile.setSize(file.getSize());
        localDataFile.setRows();
        localDataFile.setColumns();
        localDataFile.setHasTitle();
        localDataFile.setStatus(LocalDataFileStatusEnum.CREATED.getStatus());
        localDataFile.setMetaDataId();*/
        localDataFile.setRecCreateTime(LocalDateTimeUtil.now());
        int count = localDataFileMapper.insertSelective(localDataFile);
    }
}
