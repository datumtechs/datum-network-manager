package com.platon.metis.admin.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.metis.admin.common.context.LocalOrgIdentityCache;
import com.platon.metis.admin.common.util.ExportFileUtil;
import com.platon.metis.admin.dao.*;
import com.platon.metis.admin.dao.entity.LocalDataFile;
import com.platon.metis.admin.dao.entity.LocalMetaData;
import com.platon.metis.admin.dao.entity.LocalMetaDataColumn;
import com.platon.metis.admin.dao.entity.Task;
import com.platon.metis.admin.dao.enums.FileTypeEnum;
import com.platon.metis.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.metis.admin.dao.enums.TaskStatusEnum;
import com.platon.metis.admin.grpc.client.DataProviderClient;
import com.platon.metis.admin.grpc.client.MetaDataClient;
import com.platon.metis.admin.grpc.client.YarnClient;
import com.platon.metis.admin.grpc.entity.DataProviderUploadDataResp;
import com.platon.metis.admin.grpc.entity.YarnAvailableDataNodeResp;
import com.platon.metis.admin.grpc.entity.YarnQueryFilePositionResp;
import com.platon.metis.admin.service.LocalDataService;
import com.platon.metis.admin.service.exception.ServiceException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class LocalDataServiceImpl implements LocalDataService {

    @Resource
    private LocalDataFileMapper localDataFileMapper;
    @Resource
    private LocalDataFileColumnMapper localDataFileColumnMapper;
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
    @Resource
    private TaskDataProviderMapper taskDataProviderMapper;
    @Resource
    private TaskMapper taskMapper;


    @Override
    public Page<LocalMetaData> listMetaData(int pageNo, int pageSize,String keyword) {
        Page<LocalMetaData> localDataMetaPage = PageHelper.startPage(pageNo, pageSize);
        localMetaDataMapper.listMetaData(keyword);
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
                localDataFile.getFileType() == FileTypeEnum.FILETYPE_UNKONW.getValue() ? FileTypeEnum.FILETYPE_UNKONW : FileTypeEnum.FILETYPE_CSV);
        //### 3.上传源文件到数据节点
        DataProviderUploadDataResp dataProviderUploadDataResp = null;
        try {
            dataProviderUploadDataResp = dataProviderClient.uploadData(
                    availableDataNode.getIp(),
                    availableDataNode.getPort(),
                    localDataFile.getFileName(),
                    file);
        } catch (Exception e) {
            log.error("上传文件到数据节点失败，数据节点地址：{}:{}", availableDataNode.getIp(), availableDataNode.getPort(), e);
            throw new ServiceException("上传文件到数据节点失败", e);
        }
        if(StringUtils.isEmpty(dataProviderUploadDataResp.getFileId()) || StringUtils.isEmpty(dataProviderUploadDataResp.getFilePath())) {
            throw new ServiceException("上传文件到数据节点失败（文件ID或路径缺失）");
        }
        //### 4.补充源文件信息
        localDataFile.setFileId(dataProviderUploadDataResp.getFileId());
        localDataFile.setFilePath(dataProviderUploadDataResp.getFilePath());
        //localDataFile.getLocalDataFileColumnList().parallelStream().forEach(column -> column.setFileId(localDataFile.getFileId()));



        //### 5.解析完成之后，存数据库
        localDataFileMapper.insert(localDataFile);
        //localDataFileColumnMapper.insertBatch(localDataFile.getLocalDataFileColumnList());

       /* LocalMetaData localMetaData = convertLocalMetaData(detail);
        int id = localMetaDataMapper.insert(localMetaData);
        detail.getLocalMetaDataColumnList().stream().forEach(localMetaDataColumn -> {
            //关联ID
            localMetaDataColumn.setMetaId(localMetaData.getId());
        });
        count += localMetaDataColumnMapper.batchInsert(detail.getLocalMetaDataColumnList());
        //### 6.返回数据补充metaData主Id
        detail.getDynamicFields().put(ServiceConstant.LOCAL_DATA_METADATA_PK_ID, localMetaData.getId());*/
        return localDataFile;
    }


    @Transactional
    @Override
    public int addLocalMetaData(LocalMetaData localMetaData) {
        Integer count = localMetaDataMapper.insert(localMetaData);
        if(!CollectionUtils.isEmpty(localMetaData.getLocalMetaDataColumnList())){
            localMetaData.getLocalMetaDataColumnList().parallelStream().forEach(column->column.setLocalMetaDataDbId(localMetaData.getId()));
            localMetaDataColumnMapper.batchInsert(localMetaData.getLocalMetaDataColumnList());
        }
        return count;
    }


    @Transactional
    @Override
    public int delete(Integer id) {
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);
        if(Objects.isNull(localMetaData)){
            throw new ServiceException("输入的id有误");
        }
        if(LocalDataFileStatusEnum.RELEASED.getStatus()==localMetaData.getStatus()){
            throw new ServiceException("已发布状态的数据不能删除");
        }
        return localMetaDataMapper.deleteByPrimaryKey(id);
    }

    @SneakyThrows
    @Override
    public void downLoad(HttpServletResponse response, Integer id) {
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);
        if(localMetaData==null){
            throw new ServiceException("元数据没有找到");
        }
        //### 1.获取文件路径
        LocalDataFile localDataFile = localDataFileMapper.selectByFileId(localMetaData.getFileId());
        if(localDataFile==null){
            throw new ServiceException("元数据的源文件没有找到");
        }
        //### 2.获取可用的数据节点
        YarnQueryFilePositionResp yarnQueryFilePositionResp = yarnClient.queryFilePosition(localDataFile.getFileId());
        byte[] bytes = dataProviderClient.downloadData(
                yarnQueryFilePositionResp.getIp(),
                yarnQueryFilePositionResp.getPort(),
                yarnQueryFilePositionResp.getFilePath());
        ExportFileUtil.exportCsv(localMetaData.getMetaDataName(), bytes,response);
    }

    @Transactional
    @Override
    public int update(LocalMetaData localMetaData) {
        LocalMetaData existing = localMetaDataMapper.selectByPrimaryKey(localMetaData.getId());
        if (existing == null ){
            throw new ServiceException("metadata not found");
        }
        //已发布状态的元数据不允许修改
        if(LocalDataFileStatusEnum.RELEASED.getStatus()==existing.getStatus()){
            throw new ServiceException("已发布状态的元数据不允许修改");
        }
        //只允许修改这两个值
        existing.setRemarks(localMetaData.getRemarks());
        existing.setIndustry(localMetaData.getIndustry());

        int count = localMetaDataMapper.updateByPrimaryKey(existing);

        //删除meta_data_column信息
        localMetaDataColumnMapper.deleteByLocalMetaDataDbId(existing.getId());

        //重新添加meta_data_column信息
        if(!CollectionUtils.isEmpty(localMetaData.getLocalMetaDataColumnList())){
            localMetaData.getLocalMetaDataColumnList().parallelStream().forEach(column -> column.setLocalMetaDataDbId(existing.getId()));
            //todo:或者updateBatch，这样就不需要先删除了
            localMetaDataColumnMapper.batchInsert(localMetaData.getLocalMetaDataColumnList());
        }


        return count;
    }

      @Override
    public void down(Integer id) {
        Date operateDate = new Date();
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);

        //已发布状态的元数据不允许修改
        if(LocalDataFileStatusEnum.RELEASED.getStatus()!=localMetaData.getStatus()){
            throw new ServiceException("元数据未上架");
        }
        metaDataClient.revokeMetaData(localMetaData.getMetaDataId());
    }

    @Override
    public int up(Integer id) {
        //获取文件元数据详情
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);
        //已发布状态的元数据不允许修改
        if(LocalDataFileStatusEnum.RELEASED.getStatus()==localMetaData.getStatus()){
            throw new ServiceException("元数据已上架");
        }
        List<LocalMetaDataColumn> columnList = localMetaDataColumnMapper.selectByLocalMetaDataDbIdToPublish(localMetaData.getId());
        localMetaData.setLocalMetaDataColumnList(columnList);
        LocalDataFile localDataFile = localDataFileMapper.selectByFileId(localMetaData.getFileId());

        //调用grpc
        String metaDataId = metaDataClient.publishMetaData(localDataFile, localMetaData);
        if(StrUtil.isEmpty(metaDataId)){
            throw new ServiceException("调度服务未返回元数据ID");
        }

        localMetaData.setMetaDataId(metaDataId);
        //localMetaData.setStatus(LocalDataFileStatusEnum.RELEASED.getStatus());
        //localMetaData.setPublishTime(LocalDateTime.now(ZoneOffset.UTC));
        return localMetaDataMapper.updateByPrimaryKey(localMetaData);
    }

    @Override
    public boolean isExistResourceName(String resourceName, String fileId) {
        LocalMetaData localMetaData = localMetaDataMapper.checkResourceName(resourceName,fileId);
        return localMetaData != null ? true : false;
    }

    /**
     * 解析列信息
     * @param file
     * @param hasTitle
     * @return
     */
    private LocalDataFile resolveUploadFile(MultipartFile file, boolean hasTitle){
        Date operateDate = new Date();
        try {
            String fileName = file.getOriginalFilename();
            LocalDataFile localDataFile = new LocalDataFile();
            localDataFile.setFileName(fileName);
            localDataFile.setIdentityId(LocalOrgIdentityCache.getIdentityId());
            localDataFile.setFileType(FileTypeEnum.FILETYPE_CSV.getValue());//todo 目前只有csv类型
            localDataFile.setSize(file.getSize());
            localDataFile.setHasTitle(hasTitle);

            localDataFile.setRecCreateTime(operateDate);
            localDataFile.setRecUpdateTime(operateDate);
            CsvReader reader = CsvUtil.getReader();
            InputStreamReader isr = new InputStreamReader(file.getInputStream());
            CsvData csvData = reader.read(isr);
            //### 1.先确定确定有多少行
            int rowCount = csvData.getRowCount();//TODO 如果行数太多了，超过Integer.max会报错
            localDataFile.setRows(rowCount);
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
                        LocalMetaDataColumn column = new LocalMetaDataColumn();
                        //unique
                        column.setColumnIdx(i + 1);
                        column.setColumnName(header.get(i));
                        column.setColumnType("string");//TODO 向产品经理确认,数据类型默认为string
                        column.setSize(0);//TODO 向产品经理确认
                        columnList.add(column);
                    }
                } else { //没有表头
                    for (int i = 0; i < header.size(); i++) {
                        //common
                        LocalMetaDataColumn column = new LocalMetaDataColumn();
                        //unique
                        column.setColumnIdx(i + 1);
                        column.setColumnName("");
                        column.setColumnType("");//TODO 向产品经理确认,数据类型默认为string
                        column.setSize(0);//TODO 向产品经理确认
                        columnList.add(column);
                    }
                }
                localDataFile.setLocalMetaDataColumnList(columnList);
                return localDataFile;
            } else {
                throw new ServiceException("CSV文件为空文件");
            }
        } catch (ServiceException e){
            throw e;
        }catch (Exception exception){
            throw new ServiceException("解析CSV文件异常",exception);
        }
    }


    private String getResourceName(String fileName){
        //导入去掉.csv后缀的文件名称，保存前12个字符作为资源名称
        String resourceName = StrUtil.sub(FileUtil.getPrefix(fileName),0,12);
        //因为上层已做资源文件名称校验，故此处暂时不再做校验
       return resourceName;
    }


    /**
     * 任务状态是否为PENDING/RUNNING
     * @param task
     * @return
     */
    private boolean isTaskStatusPendAndRunning(Task task){
        Integer status = task.getStatus();
        return TaskStatusEnum.PENDING.getValue() == status || TaskStatusEnum.RUNNING.getValue() == status;
    }

}
