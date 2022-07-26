package com.platon.datum.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.*;
import com.platon.datum.admin.common.util.ExportFileUtil;
import com.platon.datum.admin.dao.DataFileMapper;
import com.platon.datum.admin.dao.MetaDataColumnMapper;
import com.platon.datum.admin.dao.MetaDataMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.DataFile;
import com.platon.datum.admin.dao.entity.MetaData;
import com.platon.datum.admin.dao.entity.MetaDataColumn;
import com.platon.datum.admin.dao.enums.DataFileStatusEnum;
import com.platon.datum.admin.grpc.client.DataProviderClient;
import com.platon.datum.admin.grpc.client.MetaDataClient;
import com.platon.datum.admin.grpc.client.YarnClient;
import com.platon.datum.admin.grpc.common.constant.CarrierEnum;
import com.platon.datum.admin.grpc.entity.YarnAvailableDataNodeResp;
import com.platon.datum.admin.grpc.entity.YarnQueryFilePositionResp;
import com.platon.datum.admin.grpc.fighter.api.data.DataSvc;
import com.platon.datum.admin.service.MetaDataService;
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
public class MetaDataServiceImpl implements MetaDataService {

    @Resource
    private DataFileMapper dataFileMapper;
    @Resource
    private MetaDataColumnMapper metaDataColumnMapper;
    @Resource
    private MetaDataMapper metaDataMapper;
    @Resource
    private YarnClient yarnClient;
    @Resource
    private DataProviderClient dataProviderClient;
    @Resource
    private MetaDataClient metaDataClient;


    @Override
    public Page<MetaData> listMetaData(int pageNo, int pageSize, String keyword, String userAddress, int status) {
        Page<MetaData> localDataMetaPage = PageHelper.startPage(pageNo, pageSize);
        metaDataMapper.listMetaData(keyword, userAddress, status);
        return localDataMetaPage;
    }


    @Transactional
    @Override
    public DataFile uploadFile(MultipartFile file, boolean hasTitle) {
        //### 1.解析源文件信息
        DataFile dataFile = resolveUploadFile(file, hasTitle);
        //### 2.获取可用数据节点
        YarnAvailableDataNodeResp availableDataNode = yarnClient.getAvailableDataNode(
                dataFile.getSize(),
                DataFile.FileTypeEnum.CSV);
        //### 3.上传源文件到数据节点
        DataSvc.UploadReply response = dataProviderClient.uploadData(
                availableDataNode.getIp(),
                availableDataNode.getPort(),
                dataFile.getFileName(),
                file);
        //### 4.补充源文件信息
        dataFile.setFileId(response.getDataId());
        dataFile.setFilePath(response.getDataPath());
        dataFile.setDataHash(response.getDataHash());
        dataFile.setLocationType(CarrierEnum.DataLocationType.DataLocationType_Local_VALUE);
        //### 5.解析完成之后，存数据库
        dataFileMapper.insert(dataFile);
        return dataFile;
    }


    @Transactional
    @Override
    public int addLocalMetaData(MetaData metaData) {
        Integer count = metaDataMapper.insert(metaData);
        if (!CollectionUtils.isEmpty(metaData.getMetaDataColumnList())) {
            metaData.getMetaDataColumnList().parallelStream().forEach(column -> column.setMetaDataDbId(metaData.getId()));
            metaDataColumnMapper.batchInsert(metaData.getMetaDataColumnList());
        }
        return count;
    }


    @Transactional
    @Override
    public int delete(Integer id, String sign) {
        MetaData metaData = metaDataMapper.selectById(id);
        if (Objects.isNull(metaData)) {
            throw new ArgumentException();
        }
        if (DataFileStatusEnum.RELEASED.getStatus() == metaData.getStatus()) {
            throw new CannotDeletePublishedFile();
        } else if (DataFileStatusEnum.RELEASING.getStatus() == metaData.getStatus()
                || DataFileStatusEnum.REVOKING.getStatus() == metaData.getStatus()) {
            throw new CannotOpsData();
        }
        return metaDataMapper.deleteById(id);
    }

    @Override
    public void downLoad(HttpServletResponse response, Integer id) {
        MetaData metaData = metaDataMapper.selectById(id);
        if (metaData == null) {
            throw new ObjectNotFound();
        }
        //### 1.获取文件路径
        DataFile dataFile = dataFileMapper.selectByFileId(metaData.getFileId());
        if (dataFile == null) {
            throw new ObjectNotFound();
        }
        //### 2.获取可用的数据节点
        YarnQueryFilePositionResp yarnQueryFilePositionResp = yarnClient.queryFilePosition(dataFile.getFileId());
        byte[] bytes = dataProviderClient.downloadData(
                yarnQueryFilePositionResp.getIp(),
                yarnQueryFilePositionResp.getPort(),
                yarnQueryFilePositionResp.getFilePath());
        try {
            ExportFileUtil.exportCsv(metaData.getMetaDataName(), bytes, response);
        } catch (IOException e) {
            throw new BizException(Errors.ExportCSVFileError, e);
        }
    }

    @Transactional
    @Override
    public int update(MetaData metaData) {
        MetaData existing = metaDataMapper.selectById(metaData.getId());
        if (existing == null) {
            throw new ObjectNotFound();
        }
        //已发布状态的元数据不允许修改
        if (DataFileStatusEnum.RELEASED.getStatus() == existing.getStatus()) {
            throw new CannotEditPublishedFile();
        } else if (DataFileStatusEnum.RELEASING.getStatus() == existing.getStatus()
                || DataFileStatusEnum.REVOKING.getStatus() == existing.getStatus()) {
            throw new CannotOpsData();
        }
        //只允许修改这两个值
        existing.setDesc(metaData.getDesc());
        existing.setIndustry(metaData.getIndustry());

        int count = metaDataMapper.updateById(existing);

        //删除meta_data_column信息
        metaDataColumnMapper.deleteByLocalMetaDataDbId(existing.getId());

        //重新添加meta_data_column信息
        if (!CollectionUtils.isEmpty(metaData.getMetaDataColumnList())) {
            metaData.getMetaDataColumnList().parallelStream().forEach(column -> column.setMetaDataDbId(existing.getId()));
            //TODO:或者updateBatch，这样就不需要先删除了
            metaDataColumnMapper.batchInsert(metaData.getMetaDataColumnList());
        }
        return count;
    }

    @Override
    public void down(Integer id, String sign) {
        MetaData metaData = metaDataMapper.selectById(id);
        if (metaData == null) {
            throw new ObjectNotFound();
        }
        //下架只能针对上架的数据
        if (DataFileStatusEnum.RELEASED.getStatus() != metaData.getStatus()) {
            throw new CannotWithdrawData();
        } else if (DataFileStatusEnum.RELEASING.getStatus() == metaData.getStatus()
                || DataFileStatusEnum.REVOKING.getStatus() == metaData.getStatus()) {
            throw new CannotOpsData();
        }
        metaDataClient.revokeMetaData(metaData.getMetaDataId());

        //修改文件发布信息
        metaDataMapper.updateStatusById(metaData.getId(), DataFileStatusEnum.REVOKING.getStatus());

    }

    @Override
    public int up(Integer id, String sign) {
        //获取文件元数据详情
        MetaData metaData = metaDataMapper.selectById(id);
        if (metaData == null) {
            throw new ObjectNotFound();
        }
        //已发布状态的元数据不允许修改
        if (DataFileStatusEnum.RELEASED.getStatus() == metaData.getStatus()) {
            throw new CannotPublishData();
        } else if (DataFileStatusEnum.RELEASING.getStatus() == metaData.getStatus()
                || DataFileStatusEnum.REVOKING.getStatus() == metaData.getStatus()) {
            throw new CannotOpsData();
        }
        List<MetaDataColumn> columnList = metaDataColumnMapper.selectByLocalMetaDataDbIdToPublish(metaData.getId());
        metaData.setMetaDataColumnList(columnList);
        metaData.setSign(sign);
        //调用grpc
        String publishMetaDataId = metaDataClient.publishMetaData(metaData);
        //String publishMetaDataId = "metadata:0x3426733d8fbd4a27ed26f06b35caa6ac63bca1fc09b98e56e1b262da9a357ffd";

        metaData.setMetaDataId(publishMetaDataId);
        metaData.setStatus(DataFileStatusEnum.RELEASING.getStatus());
        metaData.setPublishTime(LocalDateTime.now(ZoneOffset.UTC));
        return metaDataMapper.updateById(metaData);
    }

    @Override
    public boolean isExistResourceName(String resourceName, String address) {
        MetaData metaData = metaDataMapper.checkResourceName(resourceName, address);
        return metaData != null ? true : false;
    }

    @Override
    public List<MetaData> listMetaDataUnPublishDataToken(String keyword, String userAddress) {
        List<MetaData> list = metaDataMapper.listMetaDataUnPublishDataToken(keyword, userAddress);
        return list;
    }

    @Override
    public List<MetaData> listMetaDataUnPublishAttributeDataToken(String keyword, String userAddress) {
        List<MetaData> list = metaDataMapper.listMetaDataUnPublishAttributeDataToken(keyword, userAddress);
        return list;
    }

    /**
     * 解析列信息
     *
     * @param file
     * @param hasTitle
     * @return
     */
    private DataFile resolveUploadFile(MultipartFile file, boolean hasTitle) {
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

            DataFile dataFile = new DataFile();

            //第一行就是表头
            List<String> headerList = headerRecord.get().toList();
            dataFile.setColumns(headerList.size());


            //流式统计后续的行数。注意：前面指针移动过1条记录了
            long rows = csvParser.stream().count();


            dataFile.setFileName(file.getOriginalFilename());
            dataFile.setIdentityId(OrgCache.getLocalOrgIdentityId());
            dataFile.setFileType(DataFile.FileTypeEnum.CSV.getCode());//todo 目前只有csv类型
            dataFile.setSize(file.getSize());
            dataFile.setHasTitle(hasTitle);

            List<MetaDataColumn> columnList = new ArrayList<>();
            if (hasTitle) {
                //有表头，后续行数就是有效数据行数
                dataFile.setRows(new Long(rows).intValue());

                for (int i = 0; i < headerList.size(); i++) {
                    //common
                    MetaDataColumn column = new MetaDataColumn();
                    //unique
                    column.setColumnIdx(i + 1);
                    column.setColumnName(headerList.get(i));
                    column.setColumnType("string");         //TODO 向产品经理确认,数据类型默认为string
                    column.setSize(0);                      //TODO 向产品经理确认
                    columnList.add(column);
                }
            } else {
                //没有表头，后续行数+1，才是有效数据行数
                dataFile.setRows(new Long(rows).intValue() + 1);

                for (int i = 0; i < headerList.size(); i++) {
                    //common
                    MetaDataColumn column = new MetaDataColumn();
                    //unique
                    column.setColumnIdx(i + 1);
                    column.setColumnName("");
                    column.setColumnType("");               //TODO 向产品经理确认,数据类型默认为string
                    column.setSize(0);                      //TODO 向产品经理确认
                    columnList.add(column);
                }
            }
            dataFile.setMetaDataColumnList(columnList);
            return dataFile;
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


