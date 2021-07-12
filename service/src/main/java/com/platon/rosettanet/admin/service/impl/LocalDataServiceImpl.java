package com.platon.rosettanet.admin.service.impl;

import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.common.util.ExportFileUtil;
import com.platon.rosettanet.admin.common.util.IDUtil;
import com.platon.rosettanet.admin.dao.LocalDataFileMapper;
import com.platon.rosettanet.admin.dao.LocalMetaDataColumnMapper;
import com.platon.rosettanet.admin.dao.entity.LocalDataFile;
import com.platon.rosettanet.admin.dao.entity.LocalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import com.platon.rosettanet.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.rosettanet.admin.dao.enums.LocalMetaDataColumnVisibleEnum;
import com.platon.rosettanet.admin.grpc.client.DataProviderClient;
import com.platon.rosettanet.admin.grpc.client.MetaDataClient;
import com.platon.rosettanet.admin.grpc.client.YarnClient;
import com.platon.rosettanet.admin.grpc.entity.DataProviderUploadDataResp;
import com.platon.rosettanet.admin.grpc.entity.YarnAvailableDataNodeResp;
import com.platon.rosettanet.admin.grpc.entity.YarnQueryFilePositionResp;
import com.platon.rosettanet.admin.service.LocalDataService;
import com.platon.rosettanet.admin.service.exception.ServiceException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.platon.rosettanet.admin.common.util.IDUtil.METADATA_ID_PREFIX;

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
    private MetaDataClient metaDataClient;

    @Override
    public Page<LocalDataFile> listDataFile(int pageNo, int pageSize,String keyword) {
        Page<LocalDataFile> localDataFilePage = PageHelper.startPage(pageNo, pageSize);
        localDataFileMapper.listDataFile(keyword);
        return localDataFilePage;
    }

    @Transactional
    @Override
    public LocalDataFileDetail uploadFile(MultipartFile file, boolean hasTitle) {
        String fileName = file.getOriginalFilename();
        //### 1.解析源文件信息
        LocalDataFile localDataFile = new LocalDataFile();
        localDataFile.setIdentityId(LocalOrgIdentityCache.getIdentityId());
        localDataFile.setFileName(fileName);
        localDataFile.setResourceName(fileName.substring(0,12)); //保存前12个字符作为资源名称
        localDataFile.setFileType("csv");
        localDataFile.setSize(file.getSize());
        localDataFile.setHasTitle(hasTitle);
        localDataFile.setStatus(LocalDataFileStatusEnum.CREATED.getStatus());
        localDataFile.setMetaDataId(IDUtil.generate(METADATA_ID_PREFIX));
        localDataFile.setRecCreateTime(new Date());

        //### 2.解析列信息
        List<LocalMetaDataColumn> columnList = resolveFileInfo(file, hasTitle, localDataFile);
        YarnAvailableDataNodeResp availableDataNode = yarnClient.getAvailableDataNode(
                localDataFile.getSize(),
                localDataFile.getFileType());
        //### 3.上传源文件
        DataProviderUploadDataResp dataProviderUploadDataResp = null;

        try {
            dataProviderUploadDataResp = dataProviderClient.uploadData(
                    availableDataNode.getIp(),
                    availableDataNode.getPort(),
                    fileName,
                    file.getBytes());
        } catch (IOException e) {
            throw new ServiceException("上传文件失败",e);
        }
        //### 4.补充源文件信息
        localDataFile.setFileId(dataProviderUploadDataResp.getFileId());
        localDataFile.setFilePath(dataProviderUploadDataResp.getFilePath());
        //### 5.解析完成之后，存数据库
        int count = localDataFileMapper.insertSelective(localDataFile);
        count += localMetaDataColumnMapper.batchInsert(columnList);
        LocalDataFileDetail localDataFileDetail = new LocalDataFileDetail();
        BeanUtils.copyProperties(localDataFile, localDataFileDetail);
        localDataFileDetail.setLocalMetaDataColumnList(columnList);
        return localDataFileDetail;
    }


    @Transactional
    @Override
    public int add(LocalDataFileDetail req) {
        AtomicInteger count = new AtomicInteger();
        LocalDataFile localDataFile = new LocalDataFile();
        BeanUtils.copyProperties(req,localDataFile);
        localDataFile.setRecUpdateTime(new Date());
        count.getAndAdd(localDataFileMapper.updateByMetaDataIdSelective(localDataFile));

        /**
         * TODO 使用批量更新提升性能
         */
        List<LocalMetaDataColumn> localMetaDataColumnList = req.getLocalMetaDataColumnList();
        localMetaDataColumnList.forEach(localMetaDataColumn -> {
            localMetaDataColumn.setRecUpdateTime(new Date());
            count.getAndAdd(localMetaDataColumnMapper.updateByMetaDataIdAndIndexSelective(localMetaDataColumn));
        });

        return count.get();
    }

    @Transactional
    @Override
    public int delete(String metaDataId) {
        int count = localDataFileMapper.deleteByMetaDataId(metaDataId);
        if(count <= 0){
            return 0;
        }
        count += localMetaDataColumnMapper.deleteByMetaDataId(metaDataId);
        return count;
    }

    @SneakyThrows
    @Override
    public void downLoad(HttpServletResponse response, String metaDataId) {
        //### 1.获取文件路径
        LocalDataFile localDataFile = localDataFileMapper.selectByMetaDataId(metaDataId);
        //### 2.获取可用的数据节点
        YarnQueryFilePositionResp yarnQueryFilePositionResp = yarnClient.queryFilePosition(localDataFile.getFileId());
        byte[] bytes = dataProviderClient.downloadData(
                yarnQueryFilePositionResp.getIp(),
                yarnQueryFilePositionResp.getPort(),
                yarnQueryFilePositionResp.getFilePath());
        ExportFileUtil.exportCsv(localDataFile.getResourceName(),bytes,response);
    }

    @Transactional
    @Override
    public int update(LocalDataFileDetail req) {
        AtomicInteger count = new AtomicInteger();
        LocalDataFile localDataFile = new LocalDataFile();
        BeanUtils.copyProperties(req,localDataFile);
        localDataFile.setRecUpdateTime(new Date());
        count.getAndAdd(localDataFileMapper.updateByMetaDataIdSelective(localDataFile));

        /**
         * TODO 使用批量更新提升性能
         */
        List<LocalMetaDataColumn> localMetaDataColumnList = req.getLocalMetaDataColumnList();
        localMetaDataColumnList.forEach(localMetaDataColumn -> {
            localMetaDataColumn.setRecUpdateTime(new Date());
            count.getAndAdd(localMetaDataColumnMapper.updateByMetaDataIdAndIndexSelective(localMetaDataColumn));
        });

        return count.get();
    }

    @Override
    public LocalDataFileDetail detail(String metaDataId) {
        LocalDataFile localDataFile = localDataFileMapper.selectByMetaDataId(metaDataId);

        List<LocalMetaDataColumn> columnList = localMetaDataColumnMapper.selectByMetaDataId(metaDataId);
        LocalDataFileDetail detail = new LocalDataFileDetail();
        BeanUtils.copyProperties(localDataFile,detail);
        detail.setLocalMetaDataColumnList(columnList);
        return detail;
    }

    @Override
    public int down(String metaDataId) {
        metaDataClient.revokeMetaData(metaDataId);
        //修改文件发布信息
        LocalDataFile file = new LocalDataFile();
        file.setMetaDataId(metaDataId);
        file.setStatus(LocalDataFileStatusEnum.REVOKED.getStatus());
        file.setRecUpdateTime(new Date());
        int count = localDataFileMapper.updateByMetaDataIdSelective(file);
        return count;
    }

    @Override
    public int up(String metaDataId) {
        //获取文件元数据详情
        LocalDataFile localDataFile = localDataFileMapper.selectByMetaDataId(metaDataId);
        List<LocalMetaDataColumn> columnList = localMetaDataColumnMapper.selectByMetaDataId(metaDataId);
        LocalDataFileDetail detail = new LocalDataFileDetail();
        BeanUtils.copyProperties(localDataFile,detail);
        detail.setLocalMetaDataColumnList(columnList);
        //调用grpc
        String id = metaDataClient.publishMetaData(detail);
        if(StrUtil.isBlank(id)){
            return 0;
        }

        //修改文件发布信息
        LocalDataFile file = new LocalDataFile();
        file.setMetaDataId(metaDataId);
        file.setStatus(LocalDataFileStatusEnum.RELEASED.getStatus());
        file.setRecUpdateTime(new Date());
        int count = localDataFileMapper.updateByMetaDataIdSelective(file);
        return count;
    }

    /**
     * 解析列信息
     * @param file
     * @param hasTitle
     * @param localDataFile
     * @return
     */
    private List<LocalMetaDataColumn> resolveFileInfo(MultipartFile file,boolean hasTitle,LocalDataFile localDataFile){
        try {
            CsvReader reader = CsvUtil.getReader();
            /*byte[] srcBytes = file.getBytes();
            byte[] destBytes = new byte[srcBytes.length];
            System.arraycopy(srcBytes,0,destBytes,0,srcBytes.length);

            InputStream in = new ByteArrayInputStream(destBytes);*/
            InputStreamReader isr = new InputStreamReader(file.getInputStream());
            CsvData csvData = reader.read(isr);
            //### 1.先确定确定有多少行
            int rowCount = csvData.getRowCount();//TODO 如果行数太多了，超过Integer.max会报错
            localDataFile.setRows(Long.valueOf(rowCount));
            //### 2.如果行数大于0则确定有多少列
            if(rowCount > 0){
                CsvRow header = csvData.getRow(0);
                //列数
                int columnCount = header.size();
                localDataFile.setColumns(columnCount);
                List<LocalMetaDataColumn> columnList = new ArrayList<>();
                //### 3.判断是否有表头
                if(hasTitle){
                    for (int i = 0; i < header.size(); i++) {
                        //common
                        LocalMetaDataColumn metaDataColumn = new LocalMetaDataColumn();
                        metaDataColumn.setMetaDataId(localDataFile.getMetaDataId());
                        metaDataColumn.setRecCreateTime(new Date());
                        metaDataColumn.setRecUpdateTime(new Date());
                        metaDataColumn.setVisible(LocalMetaDataColumnVisibleEnum.YES.getIsVisible());
                        //unique
                        metaDataColumn.setColumnIdx(i + 1);
                        metaDataColumn.setColumnName(header.get(i));
                        metaDataColumn.setColumnType("string");//TODO 向产品经理确认
                        metaDataColumn.setSize(0L);//TODO 向产品经理确认
                        columnList.add(metaDataColumn);
                    }
                } else { //没有表头
                    for (int i = 0; i < header.size(); i++) {
                        //common
                        LocalMetaDataColumn metaDataColumn = new LocalMetaDataColumn();
                        metaDataColumn.setMetaDataId(localDataFile.getMetaDataId());
                        metaDataColumn.setRecCreateTime(new Date());
                        metaDataColumn.setRecUpdateTime(new Date());
                        metaDataColumn.setVisible(LocalMetaDataColumnVisibleEnum.YES.getIsVisible());
                        //unique
                        metaDataColumn.setColumnIdx(i + 1);
                        metaDataColumn.setColumnName("");
                        metaDataColumn.setColumnType("");//TODO 向产品经理确认
                        metaDataColumn.setSize(0L);//TODO 向产品经理确认
                        columnList.add(metaDataColumn);
                    }
                }
                return columnList;
            } else {
                throw new ServiceException("CSV文件为空文件");
            }
        } catch (Exception exception){
            throw new ServiceException("解析CSV文件异常",exception);
        }
    }
}
