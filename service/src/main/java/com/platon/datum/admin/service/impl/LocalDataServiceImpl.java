package com.platon.datum.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.*;
import com.platon.datum.admin.common.util.ExportFileUtil;
import com.platon.datum.admin.dao.LocalDataFileMapper;
import com.platon.datum.admin.dao.LocalMetaDataColumnMapper;
import com.platon.datum.admin.dao.LocalMetaDataMapper;
import com.platon.datum.admin.dao.cache.LocalOrgCache;
import com.platon.datum.admin.dao.entity.LocalDataFile;
import com.platon.datum.admin.dao.entity.LocalMetaData;
import com.platon.datum.admin.dao.entity.LocalMetaDataColumn;
import com.platon.datum.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.datum.admin.grpc.client.DataProviderClient;
import com.platon.datum.admin.grpc.client.MetaDataClient;
import com.platon.datum.admin.grpc.client.YarnClient;
import com.platon.datum.admin.grpc.common.constant.CarrierEnum;
import com.platon.datum.admin.grpc.entity.YarnAvailableDataNodeResp;
import com.platon.datum.admin.grpc.entity.YarnQueryFilePositionResp;
import com.platon.datum.admin.grpc.fighter.api.data.DataSvc;
import com.platon.datum.admin.service.LocalDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
public class LocalDataServiceImpl implements LocalDataService {

    @Resource
    private LocalDataFileMapper localDataFileMapper;
    @Resource
    private LocalMetaDataColumnMapper localMetaDataColumnMapper;
    @Resource
    private LocalMetaDataMapper localMetaDataMapper;
    @Resource
    private YarnClient yarnClient;
    @Resource
    private DataProviderClient dataProviderClient;
    @Resource
    private MetaDataClient metaDataClient;


    @Override
    public Page<LocalMetaData> listMetaData(int pageNo, int pageSize, String keyword, String userAddress, int status) {
        Page<LocalMetaData> localDataMetaPage = PageHelper.startPage(pageNo, pageSize);
        localMetaDataMapper.listMetaData(keyword, userAddress, status);
        return localDataMetaPage;
    }


    @Transactional
    @Override
    public LocalDataFile uploadFile(MultipartFile file, boolean hasTitle) {
        //### 1.解析源文件信息
        LocalDataFile localDataFile = resolveUploadFile(file, hasTitle);
        //### 2.获取可用数据节点
        YarnAvailableDataNodeResp availableDataNode = yarnClient.getAvailableDataNode(
                localDataFile.getSize(),
                LocalDataFile.FileTypeEnum.CSV);
        //### 3.上传源文件到数据节点
        DataSvc.UploadReply response = dataProviderClient.uploadData(
                availableDataNode.getIp(),
                availableDataNode.getPort(),
                localDataFile.getFileName(),
                file);
        //### 4.补充源文件信息
        localDataFile.setFileId(response.getDataId());
        localDataFile.setFilePath(response.getDataPath());
        localDataFile.setDataHash(response.getDataHash());
        localDataFile.setLocationType(CarrierEnum.DataLocationType.DataLocationType_Local_VALUE);
        //### 5.解析完成之后，存数据库
        localDataFileMapper.insert(localDataFile);
        return localDataFile;
    }


    @Transactional
    @Override
    public int addLocalMetaData(LocalMetaData localMetaData) {
        Integer count = localMetaDataMapper.insert(localMetaData);
        if (!CollectionUtils.isEmpty(localMetaData.getLocalMetaDataColumnList())) {
            localMetaData.getLocalMetaDataColumnList().parallelStream().forEach(column -> column.setLocalMetaDataDbId(localMetaData.getId()));
            localMetaDataColumnMapper.batchInsert(localMetaData.getLocalMetaDataColumnList());
        }
        return count;
    }


    @Transactional
    @Override
    public int delete(Integer id) {
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);
        if (Objects.isNull(localMetaData)) {
            throw new ArgumentException();
        }
        if (LocalDataFileStatusEnum.RELEASED.getStatus() == localMetaData.getStatus()) {
            throw new CannotDeletePublishedFile();
        } else if (LocalDataFileStatusEnum.RELEASING.getStatus() == localMetaData.getStatus()
                || LocalDataFileStatusEnum.REVOKING.getStatus() == localMetaData.getStatus()) {
            throw new CannotOpsData();
        }
        return localMetaDataMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void downLoad(HttpServletResponse response, Integer id) {
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);
        if (localMetaData == null) {
            throw new ObjectNotFound();
        }
        //### 1.获取文件路径
        LocalDataFile localDataFile = localDataFileMapper.selectByFileId(localMetaData.getFileId());
        if (localDataFile == null) {
            throw new ObjectNotFound();
        }
        //### 2.获取可用的数据节点
        YarnQueryFilePositionResp yarnQueryFilePositionResp = yarnClient.queryFilePosition(localDataFile.getFileId());
        byte[] bytes = dataProviderClient.downloadData(
                yarnQueryFilePositionResp.getIp(),
                yarnQueryFilePositionResp.getPort(),
                yarnQueryFilePositionResp.getFilePath());
        try {
            ExportFileUtil.exportCsv(localMetaData.getMetaDataName(), bytes, response);
        } catch (IOException e) {
            throw new BizException(Errors.ExportCSVFileError, e);
        }
    }

    @Transactional
    @Override
    public int update(LocalMetaData localMetaData) {
        LocalMetaData existing = localMetaDataMapper.selectByPrimaryKey(localMetaData.getId());
        if (existing == null) {
            throw new ObjectNotFound();
        }
        //已发布状态的元数据不允许修改
        if (LocalDataFileStatusEnum.RELEASED.getStatus() == existing.getStatus()) {
            throw new CannotEditPublishedFile();
        } else if (LocalDataFileStatusEnum.RELEASING.getStatus() == existing.getStatus()
                || LocalDataFileStatusEnum.REVOKING.getStatus() == existing.getStatus()) {
            throw new CannotOpsData();
        }
        //只允许修改这两个值
        existing.setRemarks(localMetaData.getRemarks());
        existing.setIndustry(localMetaData.getIndustry());

        int count = localMetaDataMapper.updateByPrimaryKey(existing);

        //删除meta_data_column信息
        localMetaDataColumnMapper.deleteByLocalMetaDataDbId(existing.getId());

        //重新添加meta_data_column信息
        if (!CollectionUtils.isEmpty(localMetaData.getLocalMetaDataColumnList())) {
            localMetaData.getLocalMetaDataColumnList().parallelStream().forEach(column -> column.setLocalMetaDataDbId(existing.getId()));
            //TODO:或者updateBatch，这样就不需要先删除了
            localMetaDataColumnMapper.batchInsert(localMetaData.getLocalMetaDataColumnList());
        }
        return count;
    }

    @Override
    public void down(Integer id) {
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);
        if (localMetaData == null) {
            throw new ObjectNotFound();
        }
        //下架只能针对上架的数据
        if (LocalDataFileStatusEnum.RELEASED.getStatus() != localMetaData.getStatus()) {
            throw new CannotWithdrawData();
        } else if (LocalDataFileStatusEnum.RELEASING.getStatus() == localMetaData.getStatus()
                || LocalDataFileStatusEnum.REVOKING.getStatus() == localMetaData.getStatus()) {
            throw new CannotOpsData();
        }
        metaDataClient.revokeMetaData(localMetaData.getMetaDataId());

        //修改文件发布信息
        localMetaDataMapper.updateStatusById(localMetaData.getId(), LocalDataFileStatusEnum.REVOKING.getStatus());

    }

    @Override
    public int up(Integer id) {
        //获取文件元数据详情
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);
        if (localMetaData == null) {
            throw new ObjectNotFound();
        }
        //已发布状态的元数据不允许修改
        if (LocalDataFileStatusEnum.RELEASED.getStatus() == localMetaData.getStatus()) {
            throw new CannotPublishData();
        } else if (LocalDataFileStatusEnum.RELEASING.getStatus() == localMetaData.getStatus()
                || LocalDataFileStatusEnum.REVOKING.getStatus() == localMetaData.getStatus()) {
            throw new CannotOpsData();
        }
        List<LocalMetaDataColumn> columnList = localMetaDataColumnMapper.selectByLocalMetaDataDbIdToPublish(localMetaData.getId());
        localMetaData.setLocalMetaDataColumnList(columnList);
        LocalDataFile localDataFile = localDataFileMapper.selectByFileId(localMetaData.getFileId());

        //调用grpc
        String publishMetaDataId = metaDataClient.publishMetaData(localDataFile, localMetaData);
        //String publishMetaDataId = "metadata:0x3426733d8fbd4a27ed26f06b35caa6ac63bca1fc09b98e56e1b262da9a357ffd";

        localMetaData.setMetaDataId(publishMetaDataId);
        localMetaData.setStatus(LocalDataFileStatusEnum.RELEASING.getStatus());
        localMetaData.setPublishTime(LocalDateTime.now(ZoneOffset.UTC));
        return localMetaDataMapper.updateByPrimaryKey(localMetaData);
    }

    @Override
    public boolean isExistResourceName(String resourceName, String address) {
        LocalMetaData localMetaData = localMetaDataMapper.checkResourceName(resourceName, address);
        return localMetaData != null ? true : false;
    }

    @Override
    public List<LocalMetaData> listUnBindMetaData(String keyword, String userAddress) {
        List<LocalMetaData> list = localMetaDataMapper.listUnBindMetaData(keyword, userAddress);
        return list;
    }

//    @Transactional
//    @Override
//    public void saveAndUp(LocalMetaData localMetaData) {
//        //1.先保存之后返回数据id
//        int id = this.addLocalMetaData(localMetaData);
//        //2.上架元数据到数据中心
//        this.up(id);
//    }

    /**
     * 解析列信息
     *
     * @param file
     * @param hasTitle
     * @return
     */
    private LocalDataFile resolveUploadFile(MultipartFile file, boolean hasTitle) {
        if (file.isEmpty()) {
            log.error("file is empty");
            throw new FileEmpty();
        }

        //校验文件名
        try (InputStreamReader isr = new InputStreamReader(file.getInputStream())) {

            CSVParser csvParser = CSVFormat.DEFAULT.parse(isr);

            //流式读取第一行
            Optional<CSVRecord> headerRecord = csvParser.stream().findFirst();
            if (!headerRecord.isPresent()) {
                log.error("file has 0 line");
                throw new FileEmpty();
            }

            LocalDataFile localDataFile = new LocalDataFile();

            //第一行就是表头
            List<String> headerList = headerRecord.get().toList();
            localDataFile.setColumns(headerList.size());


            //流式统计后续的行数。注意：前面指针移动过1条记录了
            long rows = csvParser.stream().count();


            localDataFile.setFileName(file.getOriginalFilename());
            localDataFile.setIdentityId(LocalOrgCache.getLocalOrgIdentityId());
            localDataFile.setFileType(LocalDataFile.FileTypeEnum.CSV.getCode());//todo 目前只有csv类型
            localDataFile.setSize(file.getSize());
            localDataFile.setHasTitle(hasTitle);

            List<LocalMetaDataColumn> columnList = new ArrayList<>();
            if (hasTitle) {
                //有表头，后续行数就是有效数据行数
                localDataFile.setRows(new Long(rows).intValue());

                for (int i = 0; i < headerList.size(); i++) {
                    //common
                    LocalMetaDataColumn column = new LocalMetaDataColumn();
                    //unique
                    column.setColumnIdx(i + 1);
                    column.setColumnName(headerList.get(i));
                    column.setColumnType("string");         //TODO 向产品经理确认,数据类型默认为string
                    column.setSize(0);                      //TODO 向产品经理确认
                    columnList.add(column);
                }
            } else {
                //没有表头，后续行数+1，才是有效数据行数
                localDataFile.setRows(new Long(rows).intValue() + 1);

                for (int i = 0; i < headerList.size(); i++) {
                    //common
                    LocalMetaDataColumn column = new LocalMetaDataColumn();
                    //unique
                    column.setColumnIdx(i + 1);
                    column.setColumnName("");
                    column.setColumnType("");               //TODO 向产品经理确认,数据类型默认为string
                    column.setSize(0);                      //TODO 向产品经理确认
                    columnList.add(column);
                }
            }
            localDataFile.setLocalMetaDataColumnList(columnList);
            return localDataFile;
        } catch (IOException e) {
            throw new BizException(Errors.ReadFileContentError, e);
        }
    }


    public static void main(String[] args) {


        File file = new File("C:\\Users\\mrlvx\\Downloads\\保险训练大数据集.csv");

        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
            CSVParser csvParser = CSVFormat.DEFAULT.parse(isr);
            Optional<CSVRecord> headerRecord = csvParser.stream().findFirst();
            long rows = csvParser.stream().count();
            if (!headerRecord.isPresent() || rows == 0) {
                log.error("file has 0 line");
                throw new FileEmpty();
            }
            List<String> headerList = headerRecord.get().toList();

            System.out.println("rows:" + rows);
            for (String header : headerList) {
                System.out.println("header:" + header);
            }


        } catch (IOException e) {
            log.error("read file content error", e);
            throw new BizException(Errors.SysException);
        }
    }

}

