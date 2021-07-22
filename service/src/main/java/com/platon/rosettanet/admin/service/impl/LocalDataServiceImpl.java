package com.platon.rosettanet.admin.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
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
import java.util.stream.Collectors;

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
        List<LocalDataFileDetail> detailList = localDataFilePage.stream()
                .map(localDataFile -> {
                    List<LocalMetaDataColumn> columnList = localMetaDataColumnMapper.selectByMetaDataId(localDataFile.getMetaDataId());
                    LocalDataFileDetail detail = new LocalDataFileDetail();
                    BeanUtils.copyProperties(localDataFile, detail);
                    detail.setLocalMetaDataColumnList(columnList);
                    return detail;
                })
                .collect(Collectors.toList());
        localDataFilePage.clear();
        //重新赋值
        localDataFilePage.addAll(detailList);
        return localDataFilePage;
    }

    @Transactional
    @Override
    public LocalDataFileDetail uploadFile(MultipartFile file, boolean hasTitle) {
        //### 1.解析源文件信息
        LocalDataFileDetail detail = resolveFileInfo(file, hasTitle);
        //### 2.获取可用数据节点
        YarnAvailableDataNodeResp availableDataNode = yarnClient.getAvailableDataNode(
                detail.getSize(),
                detail.getFileType());
        //### 3.上传源文件到数据节点
        DataProviderUploadDataResp dataProviderUploadDataResp = null;
        try {
            dataProviderUploadDataResp = dataProviderClient.uploadData(
                    availableDataNode.getIp(),
                    availableDataNode.getPort(),
                    detail.getFileName(),
                    file.getBytes());
        } catch (IOException e) {
            throw new ServiceException("上传文件失败",e);
        }
        //### 4.补充源文件信息
        detail.setFileId(dataProviderUploadDataResp.getFileId());
        detail.setFilePath(dataProviderUploadDataResp.getFilePath());
        //### 5.解析完成之后，存数据库
        int count = localDataFileMapper.insertSelective(detail);
        count += localMetaDataColumnMapper.batchInsert(detail.getLocalMetaDataColumnList());
        return detail;
    }


    @Transactional
    @Override
    public int add(LocalDataFileDetail detail) {
        LocalDataFile localDataFile = localDataFileMapper.selectByMetaDataId(detail.getMetaDataId());
        //已发布状态的元数据不允许修改
        if(LocalDataFileStatusEnum.RELEASED.getStatus().equals(localDataFile.getStatus())){
            throw new ServiceException("已发布状态的元数据不允许修改");
        }
        Date operateDate = new Date();
        AtomicInteger count = new AtomicInteger();
        detail.setRecUpdateTime(operateDate);
        count.getAndAdd(localDataFileMapper.updateByMetaDataIdSelective(detail));

        /**
         * TODO 使用批量更新提升性能
         */
        List<LocalMetaDataColumn> localMetaDataColumnList = detail.getLocalMetaDataColumnList();
        localMetaDataColumnList.forEach(localMetaDataColumn -> {
            localMetaDataColumn.setRecUpdateTime(operateDate);
            count.getAndAdd(localMetaDataColumnMapper.updateByMetaDataIdAndIndexSelective(localMetaDataColumn));
        });

        return count.get();
    }

    @Transactional
    @Override
    public int delete(String metaDataId) {
        int count = localMetaDataColumnMapper.deleteByMetaDataId(metaDataId);
        if(count <= 0){
            return 0;
        }
        count += localDataFileMapper.deleteByMetaDataId(metaDataId);
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
    public int update(LocalDataFileDetail detail) {
        LocalDataFile localDataFile = localDataFileMapper.selectById(detail.getId());
        //已发布状态的元数据不允许修改
        if(LocalDataFileStatusEnum.RELEASED.getStatus().equals(localDataFile.getStatus())){
            throw new ServiceException("已发布状态的元数据不允许修改");
        }
        Date operateDate = new Date();
        String newMetaDataId = IDUtil.generate(METADATA_ID_PREFIX);
        AtomicInteger count = new AtomicInteger();
        //生成新的metaDataId
        detail.setMetaDataId(newMetaDataId);
        detail.setRecUpdateTime(operateDate);
        count.getAndAdd(localDataFileMapper.updateByIdSelective(detail));

        /**
         * TODO 使用批量更新提升性能
         */
        List<LocalMetaDataColumn> localMetaDataColumnList = detail.getLocalMetaDataColumnList();
        localMetaDataColumnList.forEach(localMetaDataColumn -> {
            localMetaDataColumn.setMetaDataId(newMetaDataId);
            localMetaDataColumn.setRecUpdateTime(operateDate);
            count.getAndAdd(localMetaDataColumnMapper.updateByIdSelective(localMetaDataColumn));
        });

        return count.get();
    }

    @Override
    public LocalDataFileDetail detail(String metaDataId) {
        LocalDataFile localDataFile = localDataFileMapper.selectByMetaDataId(metaDataId);
        if(localDataFile == null){
            return null;
        }
        List<LocalMetaDataColumn> columnList = localMetaDataColumnMapper.selectByMetaDataId(metaDataId);
        LocalDataFileDetail detail = new LocalDataFileDetail();
        BeanUtils.copyProperties(localDataFile,detail);
        detail.setLocalMetaDataColumnList(columnList);
        return detail;
    }

    @Override
    public int down(String metaDataId) {
        LocalDataFile localDataFile = localDataFileMapper.selectByMetaDataId(metaDataId);
        //已发布状态的元数据不允许修改
        if(!LocalDataFileStatusEnum.RELEASED.getStatus().equals(localDataFile.getStatus())){
            throw new ServiceException("元数据未上架");
        }
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
        //已发布状态的元数据不允许修改
        if(LocalDataFileStatusEnum.RELEASED.getStatus().equals(localDataFile.getStatus())){
            throw new ServiceException("元数据已上架");
        }
        List<LocalMetaDataColumn> columnList = localMetaDataColumnMapper.selectByMetaDataId(metaDataId);
        LocalDataFileDetail detail = new LocalDataFileDetail();
        BeanUtils.copyProperties(localDataFile,detail);
        detail.setLocalMetaDataColumnList(columnList);
        //调用grpc
        String publishMetaData = metaDataClient.publishMetaData(detail);
        if(StrUtil.isBlank(publishMetaData)){
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

    @Override
    public boolean isExistResourceName(String resourceName,String metaDataId) {
        LocalDataFile localDataFile = localDataFileMapper.selectByResourceName(resourceName,metaDataId);
        return localDataFile != null ? true : false;
    }

    /**
     * 解析列信息
     * @param file
     * @param hasTitle
     * @return
     */
    private LocalDataFileDetail resolveFileInfo(MultipartFile file,boolean hasTitle){
        Date operateDate = new Date();
        try {
            String fileName = file.getOriginalFilename();
            LocalDataFileDetail detail = new LocalDataFileDetail();
            detail.setFileName(fileName);
            //导入去掉.csv后缀的文件名称，保存前12个字符作为资源名称
            String resourceName = StrUtil.sub(FileUtil.getPrefix(fileName),0,12);
            //因为上层已做资源文件名称校验，故此处暂时不再做校验
            detail.setResourceName(resourceName);
            detail.setIdentityId(LocalOrgIdentityCache.getIdentityId());
            detail.setFileType(FileUtil.getSuffix(fileName));//获取文件类型
            detail.setSize(file.getSize());
            detail.setHasTitle(hasTitle);
            detail.setStatus(LocalDataFileStatusEnum.CREATED.getStatus());
            detail.setMetaDataId(IDUtil.generate(METADATA_ID_PREFIX));

            detail.setRecCreateTime(operateDate);
            detail.setRecCreateTime(operateDate);
            CsvReader reader = CsvUtil.getReader();
            /*byte[] srcBytes = file.getBytes();
            byte[] destBytes = new byte[srcBytes.length];
            System.arraycopy(srcBytes,0,destBytes,0,srcBytes.length);

            InputStream in = new ByteArrayInputStream(destBytes);*/
            InputStreamReader isr = new InputStreamReader(file.getInputStream());
            CsvData csvData = reader.read(isr);
            //### 1.先确定确定有多少行
            int rowCount = csvData.getRowCount();//TODO 如果行数太多了，超过Integer.max会报错
            detail.setRows(Long.valueOf(rowCount));
            //### 2.如果行数大于0则确定有多少列
            if(rowCount > 0){
                CsvRow header = csvData.getRow(0);
                //列数
                int columnCount = header.size();
                detail.setColumns(columnCount);
                List<LocalMetaDataColumn> columnList = new ArrayList<>();
                //### 3.判断是否有表头
                if(hasTitle){
                    for (int i = 0; i < header.size(); i++) {
                        //common
                        LocalMetaDataColumn metaDataColumn = new LocalMetaDataColumn();
                        metaDataColumn.setMetaDataId(detail.getMetaDataId());
                        metaDataColumn.setRecCreateTime(operateDate);
                        metaDataColumn.setRecUpdateTime(operateDate);
                        metaDataColumn.setVisible(LocalMetaDataColumnVisibleEnum.YES.getIsVisible());
                        //unique
                        metaDataColumn.setColumnIdx(i + 1);
                        metaDataColumn.setColumnName(header.get(i));
                        metaDataColumn.setColumnType("string");//TODO 向产品经理确认,数据类型默认为string
                        metaDataColumn.setSize(0L);//TODO 向产品经理确认
                        columnList.add(metaDataColumn);
                    }
                } else { //没有表头
                    for (int i = 0; i < header.size(); i++) {
                        //common
                        LocalMetaDataColumn metaDataColumn = new LocalMetaDataColumn();
                        metaDataColumn.setMetaDataId(detail.getMetaDataId());
                        metaDataColumn.setRecCreateTime(operateDate);
                        metaDataColumn.setRecUpdateTime(operateDate);
                        metaDataColumn.setVisible(LocalMetaDataColumnVisibleEnum.YES.getIsVisible());
                        //unique
                        metaDataColumn.setColumnIdx(i + 1);
                        metaDataColumn.setColumnName("");
                        metaDataColumn.setColumnType("");//TODO 向产品经理确认,数据类型默认为string
                        metaDataColumn.setSize(0L);//TODO 向产品经理确认
                        columnList.add(metaDataColumn);
                    }
                }
                detail.setLocalMetaDataColumnList(columnList);
                return detail;
            } else {
                throw new ServiceException("CSV文件为空文件");
            }
        } catch (ServiceException e){
            throw e;
        }catch (Exception exception){
            throw new ServiceException("解析CSV文件异常",exception);
        }
    }
}
