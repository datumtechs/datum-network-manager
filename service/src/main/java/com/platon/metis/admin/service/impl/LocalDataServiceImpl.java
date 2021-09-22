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
import com.platon.metis.admin.dao.entity.*;
import com.platon.metis.admin.dao.enums.FileTypeEnum;
import com.platon.metis.admin.dao.enums.LocalDataFileStatusEnum;
import com.platon.metis.admin.dao.enums.LocalMetaDataColumnVisibleEnum;
import com.platon.metis.admin.dao.enums.TaskStatusEnum;
import com.platon.metis.admin.grpc.client.DataProviderClient;
import com.platon.metis.admin.grpc.client.MetaDataClient;
import com.platon.metis.admin.grpc.client.YarnClient;
import com.platon.metis.admin.grpc.entity.DataProviderUploadDataResp;
import com.platon.metis.admin.grpc.entity.YarnAvailableDataNodeResp;
import com.platon.metis.admin.grpc.entity.YarnQueryFilePositionResp;
import com.platon.metis.admin.service.LocalDataService;
import com.platon.metis.admin.service.constant.ServiceConstant;
import com.platon.metis.admin.service.exception.ServiceException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Resource
    private TaskDataReceiverMapper taskDataReceiverMapper;
    @Resource
    private TaskMapper taskMapper;


    @Override
    public Page<LocalMetaDataItem> listDataFile(int pageNo, int pageSize,String keyword) {
        Page<LocalMetaDataItem> localDataMetaPage = PageHelper.startPage(pageNo, pageSize);
        localMetaDataMapper.listMetaData(keyword);
        localDataMetaPage.stream().forEach(new Consumer<LocalMetaDataItem>() {
            @Override
            public void accept(LocalMetaDataItem localMetaDataItem) {
                List<String> dataJoinTaskIdList = taskDataReceiverMapper.selectTaskByMetaDataId(localMetaDataItem.getMetaDataId());
                localMetaDataItem.setDataJoinTaskCount(CollectionUtils.isEmpty(dataJoinTaskIdList) ? 0 : dataJoinTaskIdList.size());
            }
        });
        return localDataMetaPage;
    }

    @Override
    public Page<Task> listDataJoinTask(int pageNo, int pageSize, String metaDataId, String keyword) {
        List<String> taskIdList = taskDataReceiverMapper.selectTaskByMetaDataId(metaDataId);

        Page<Task> dataJoinTaskPage = PageHelper.startPage(pageNo, pageSize);
        List<Task> taskList = taskIdList.stream().map(new Function<String, Task>() {
            @Override
            public Task apply(String taskId) {
                return taskMapper.selectTaskByTaskId(taskId,keyword);
            }
        }).collect(Collectors.toList());

       /* Page<Task> dataJoinTaskPage = new Page<Task>();
        dataJoinTaskPage.setTotal(dataJoinTaskIdPage.getTotal());
        dataJoinTaskPage.setPageNum(dataJoinTaskIdPage.getPageNum());
        dataJoinTaskPage.setPageSize(dataJoinTaskIdPage.getPageSize());
        dataJoinTaskPage.setPages(dataJoinTaskIdPage.getPages());
        dataJoinTaskPage.addAll(taskList);*/



        /**
         * 排序规则：
         * 1、进行中任务，即”等待中“和”计算中“的任务置顶，按照发起时间排列；
         * 2、已结束任务，即“成功”和“失败”任务按照发起时间排列
         */
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                Boolean value1 = isTaskStatusPendAndRunning(o1);
                Boolean value2 = isTaskStatusPendAndRunning(o2);
                //对描述1场景，进行排序
                if(0 < value1.compareTo(value2)){
                    return -1;
                }else if(0 > value1.compareTo(value2)){
                    return 1;
                }
                return o1.getCreateAt().compareTo(o2.getCreateAt());
            }
        });

        return dataJoinTaskPage;
    }




    @Transactional
    @Override
    public LocalDataFileDetail uploadFile(MultipartFile file, boolean hasTitle) {
        //### 1.解析源文件信息
        LocalDataFileDetail detail = resolveFileInfo(file, hasTitle);
        //### 2.获取可用数据节点
        YarnAvailableDataNodeResp availableDataNode = yarnClient.getAvailableDataNode(
                detail.getSize(),
                detail.getFileType() == FileTypeEnum.FILETYPE_UNKONW.getValue() ? FileTypeEnum.FILETYPE_UNKONW : FileTypeEnum.FILETYPE_CSV);
        //### 3.上传源文件到数据节点
        DataProviderUploadDataResp dataProviderUploadDataResp = null;
        try {
            dataProviderUploadDataResp = dataProviderClient.uploadData(
                    availableDataNode.getIp(),
                    availableDataNode.getPort(),
                    detail.getFileName(),
                    file);
        } catch (Exception e) {
            throw new ServiceException("上传文件失败",e);
        }
        if(StringUtils.isEmpty(dataProviderUploadDataResp.getFileId()) || StringUtils.isEmpty(dataProviderUploadDataResp.getFilePath())) {
            throw new ServiceException("上传文件失败文件ID或路径缺失");
        }
        //### 4.补充源文件信息
        detail.setFileId(dataProviderUploadDataResp.getFileId());
        detail.setFilePath(dataProviderUploadDataResp.getFilePath());
        //### 5.解析完成之后，存数据库
        int count = localDataFileMapper.insertSelective(detail);
        LocalMetaData localMetaData = convertLocalMetaData(detail);
        int id = localMetaDataMapper.insert(localMetaData);
        detail.getLocalMetaDataColumnList().stream().forEach(localMetaDataColumn -> {
            //关联ID
            localMetaDataColumn.setMetaId(localMetaData.getId());
        });
        count += localMetaDataColumnMapper.batchInsert(detail.getLocalMetaDataColumnList());
        //### 6.返回数据补充metaData主Id
        detail.getDynamicFields().put(ServiceConstant.LOCAL_DATA_METADATA_PK_ID, localMetaData.getId());
        return detail;
    }


    @Transactional
    @Override
    public int add(LocalDataFileDetail detail, LocalMetaData metaData) {
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(metaData.getId());
        //已发布状态的元数据不允许修改
        if(LocalDataFileStatusEnum.RELEASED.getStatus().equals(localMetaData.getStatus())){
            throw new ServiceException("已发布状态的元数据不允许修改");
        }
        Date operateDate = new Date();
        AtomicInteger count = new AtomicInteger();
        metaData.setRecUpdateTime(operateDate);
        count.getAndAdd(localMetaDataMapper.updateByPrimaryKeySelective(metaData));

        /**
         * TODO 使用批量更新提升性能
         */
        List<LocalMetaDataColumn> localMetaDataColumnList = detail.getLocalMetaDataColumnList();
        localMetaDataColumnList.forEach(localMetaDataColumn -> {
            localMetaDataColumn.setRecUpdateTime(operateDate);
            count.getAndAdd(localMetaDataColumnMapper.updateByIdSelective(localMetaDataColumn));
        });
        return count.get();
    }

    @Transactional
    @Override
    public int addAgain(LocalDataFileDetail detail, LocalMetaData metaData) {
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(metaData.getId());
        AtomicInteger count = new AtomicInteger();
        //1、持久化元数据  localMetaData
        localMetaData.setDataFileId(localMetaData.getDataFileId());
        localMetaData.setRemarks(metaData.getRemarks());
        localMetaData.setMetaDataName(metaData.getMetaDataName());
        localMetaData.setIndustry(metaData.getIndustry());
        localMetaData.setStatus(metaData.getStatus());
        localMetaData.setHasOtherSave(metaData.getHasOtherSave());
        localMetaData.setMetaDataId(null);
        localMetaData.setPublishTime(null);
        localMetaData.setId(null);
        count.getAndAdd(localMetaDataMapper.insertSelective(localMetaData));
        //3、持久化 LocalMetaDataColumn
        Date operateDate = new Date();
        /**
         * TODO 使用批量更新提升性能
         */
        List<LocalMetaDataColumn> localMetaDataColumnList = detail.getLocalMetaDataColumnList();
        localMetaDataColumnList.forEach(localMetaDataColumn -> {
            localMetaDataColumn.setMetaId(localMetaData.getId());//关联表local_meta_data
            localMetaDataColumn.setRecCreateTime(operateDate);
            localMetaDataColumn.setRecUpdateTime(operateDate);
        });
        count.getAndAdd(localMetaDataColumnMapper.batchInsert(localMetaDataColumnList));
        return count.get();
    }

    @Transactional
    @Override
    public int delete(Integer id) {
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);
        if(Objects.isNull(localMetaData)){
            throw new ServiceException("输入的id有误");
        }
        if(LocalDataFileStatusEnum.RELEASED.getStatus().equals(localMetaData.getStatus())){
            throw new ServiceException("已发布状态的数据不能删除");
        }
        LocalMetaData metaData = new LocalMetaData();
        metaData.setId(localMetaData.getId());
        metaData.setStatus(LocalDataFileStatusEnum.DELETED.getStatus());
        metaData.setRecUpdateTime(new Date());
        AtomicInteger count = new AtomicInteger();
        count.getAndAdd(localMetaDataMapper.updateByPrimaryKeySelective(metaData));
        return count.get();
    }

    @SneakyThrows
    @Override
    public void downLoad(HttpServletResponse response, Integer id) {
        //### 1.获取文件路径
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);
        LocalDataFile localDataFile = localDataFileMapper.selectById(localMetaData.getDataFileId());
        //### 2.获取可用的数据节点
        YarnQueryFilePositionResp yarnQueryFilePositionResp = yarnClient.queryFilePosition(localDataFile.getFileId());
        byte[] bytes = dataProviderClient.downloadData(
                yarnQueryFilePositionResp.getIp(),
                yarnQueryFilePositionResp.getPort(),
                yarnQueryFilePositionResp.getFilePath());
        ExportFileUtil.exportCsv(localMetaData.getMetaDataName(),bytes,response);
    }

    @Transactional
    @Override
    public int update(LocalDataFileDetail detail, LocalMetaData metaData) {
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(metaData.getId());
        //已发布状态的元数据不允许修改
        if(LocalDataFileStatusEnum.RELEASED.getStatus().equals(localMetaData.getStatus())){
            throw new ServiceException("已发布状态的元数据不允许修改");
        }
        Date operateDate = new Date();
        AtomicInteger count = new AtomicInteger();

        //修改元数据区分情况：1、数据下架修改 2、数据从未发布过修改
        if(LocalDataFileStatusEnum.REVOKED.getStatus().equals(localMetaData.getStatus())){
            Date operateDateCreate = new Date();
            LocalMetaData localMetaDataCreate = new LocalMetaData();
            localMetaDataCreate.setDataFileId(localMetaData.getDataFileId());
            localMetaDataCreate.setMetaDataName(localMetaData.getMetaDataName());
            localMetaDataCreate.setStatus(LocalDataFileStatusEnum.CREATED.getStatus());
            localMetaDataCreate.setHasOtherSave(true);
            localMetaDataCreate.setRemarks(metaData.getRemarks());
            localMetaDataCreate.setIndustry(metaData.getIndustry());
            localMetaDataCreate.setRecCreateTime(operateDateCreate);
            localMetaDataCreate.setRecUpdateTime(operateDateCreate);
            //旧数据另存状态重置，并更新新数据为另外状态
            LocalMetaData oldLocalMetaData = new LocalMetaData();
            oldLocalMetaData.setId(metaData.getId());
            oldLocalMetaData.setHasOtherSave(false);
            count.getAndAdd(localMetaDataMapper.updateByPrimaryKeySelective(oldLocalMetaData));
            count.getAndAdd(localMetaDataMapper.insert(localMetaDataCreate));
            detail.getLocalMetaDataColumnList().stream().forEach(localMetaDataColumn -> {
                //关联ID
                localMetaDataColumn.setMetaId(localMetaDataCreate.getId());
            });
            count.getAndAdd(localMetaDataColumnMapper.batchInsert(detail.getLocalMetaDataColumnList()));
        }else if(LocalDataFileStatusEnum.CREATED.getStatus().equals(localMetaData.getStatus())){
            metaData.setRecUpdateTime(operateDate);
            metaData.setId(localMetaData.getId());
            count.getAndAdd(localMetaDataMapper.updateByPrimaryKeySelective(metaData));
            List<LocalMetaDataColumn> localMetaDataColumnList = detail.getLocalMetaDataColumnList();
            localMetaDataColumnList.forEach(localMetaDataColumn -> {
                localMetaDataColumn.setRecUpdateTime(operateDate);
                count.getAndAdd(localMetaDataColumnMapper.updateByIdSelective(localMetaDataColumn));
            });
        }

        return count.get();
    }

    @Override
    public LocalDataFileDetail detail(Integer id) {
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);
        if(Objects.isNull(localMetaData)){
            throw new ServiceException("查询为空，请检查metaDataPKId是否正确");
        }
        LocalDataFile localDataFile = localDataFileMapper.selectById(localMetaData.getDataFileId());
        if(Objects.isNull(localDataFile)){
            throw new ServiceException("查询为空，请检查dataFileId是否正确");
        }
        List<LocalMetaDataColumn> columnList = localMetaDataColumnMapper.selectByMetaId(localMetaData.getId());
        List<String> taskIdList = taskDataReceiverMapper.selectTaskByMetaDataId(localMetaData.getMetaDataId());

        LocalDataFileDetail detail = new LocalDataFileDetail();
        BeanUtils.copyProperties(localDataFile,detail);
        detail.setLocalMetaDataColumnList(columnList);
        //数据状态及metaDataId
        Map dynamicFields = new HashMap<>();
        dynamicFields.put(ServiceConstant.LOCAL_DATA_STATUS, localMetaData.getStatus());
        dynamicFields.put(ServiceConstant.LOCAL_DATA_METADATA_ID, localMetaData.getMetaDataId());
        dynamicFields.put(ServiceConstant.LOCAL_DATA_REMARKS, localMetaData.getRemarks());
        dynamicFields.put(ServiceConstant.LOCAL_DATA_INDUSTRY, localMetaData.getIndustry());
        dynamicFields.put(ServiceConstant.LOCAL_DATA_RESOURCE_NAME, localMetaData.getMetaDataName());
        dynamicFields.put(ServiceConstant.LOCAL_DATA_METADATA_PK_ID, localMetaData.getId());
        dynamicFields.put(ServiceConstant.LOCAL_DATA_ATTEND_TASK_COUNT, CollectionUtils.isEmpty(taskIdList) ? 0 : taskIdList.size());
        detail.setDynamicFields(dynamicFields);
        return detail;
    }

    @Override
    public int down(Integer id) {
        Date operateDate = new Date();
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);

        //已发布状态的元数据不允许修改
        if(!LocalDataFileStatusEnum.RELEASED.getStatus().equals(localMetaData.getStatus())){
            throw new ServiceException("元数据未上架");
        }
        metaDataClient.revokeMetaData(localMetaData.getMetaDataId());
        //修改文件发布信息
        LocalMetaData metaData = new LocalMetaData();
        metaData.setId(localMetaData.getId());
        metaData.setStatus(LocalDataFileStatusEnum.REVOKED.getStatus());
        metaData.setPublishTime(null);
        metaData.setRecUpdateTime(operateDate);
        AtomicInteger count = new AtomicInteger();
        count.getAndAdd(localMetaDataMapper.updateByPrimaryKeySelective(metaData));
        return count.get();
    }

    @Override
    public int up(Integer id) {
        Date operateDate = new Date();
        //获取文件元数据详情
        LocalMetaData localMetaData = localMetaDataMapper.selectByPrimaryKey(id);
        LocalDataFile localDataFile = localDataFileMapper.selectById(localMetaData.getDataFileId());
        //已发布状态的元数据不允许修改
        if(LocalDataFileStatusEnum.RELEASED.getStatus().equals(localMetaData.getStatus())){
            throw new ServiceException("元数据已上架");
        }
        List<LocalMetaDataColumn> columnList = localMetaDataColumnMapper.selectByMetaId(localMetaData.getId());
        LocalDataFileDetail detail = new LocalDataFileDetail();
        BeanUtils.copyProperties(localDataFile,detail);
        detail.setLocalMetaDataColumnList(columnList);
        Map dynamicMap = new HashMap();
        dynamicMap.put(ServiceConstant.LOCAL_DATA_RESOURCE_NAME, localMetaData.getMetaDataName());
        detail.setDynamicFields(dynamicMap);
        //调用grpc
        String publishMetaDataId = metaDataClient.publishMetaData(detail, localMetaData);
        //String publishMetaDataId = "metadata:0x3426733d8fbd4a27ed26f06b35caa6ac63bca1fc09b98e56e1b262da9a357ffd";
        if(StrUtil.isBlank(publishMetaDataId)){
            throw new ServiceException("调度服务未返回元数据ID");
        }
        //修改文件发布信息
        LocalMetaData meteData = new LocalMetaData();
        meteData.setId(localMetaData.getId());
        meteData.setMetaDataId(publishMetaDataId);
        meteData.setStatus(LocalDataFileStatusEnum.RELEASED.getStatus());
        meteData.setPublishTime(new Date());
        meteData.setRecUpdateTime(operateDate);
        AtomicInteger count = new AtomicInteger();
        count.getAndAdd(localMetaDataMapper.updateByPrimaryKeySelective(meteData));
        return count.get();
    }

    @Override
    public boolean isExistResourceName(String resourceName,Integer id) {
        LocalMetaData localMetaData = localMetaDataMapper.selectByResourceName(resourceName,id);
        return localMetaData != null ? true : false;
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
            detail.setIdentityId(LocalOrgIdentityCache.getIdentityId());
            detail.setFileType(FileTypeEnum.FILETYPE_CSV.getValue());//todo 目前只有csv类型
            detail.setSize(file.getSize());
            detail.setHasTitle(hasTitle);
            //导入去掉.csv后缀的文件名称，保存前12个字符作为资源名称
            String resourceName = StrUtil.sub(FileUtil.getPrefix(fileName),0,12);
            //因为上层已做资源文件名称校验，故此处暂时不再做校验
            Map dynamicMap = new HashMap();
            dynamicMap.put(ServiceConstant.LOCAL_DATA_RESOURCE_NAME, resourceName);
            detail.setDynamicFields(dynamicMap);

            detail.setRecCreateTime(operateDate);
            detail.setRecUpdateTime(operateDate);
            CsvReader reader = CsvUtil.getReader();
            InputStreamReader isr = new InputStreamReader(file.getInputStream());
            CsvData csvData = reader.read(isr);
            //### 1.先确定确定有多少行
            int rowCount = csvData.getRowCount();//TODO 如果行数太多了，超过Integer.max会报错
            detail.setRows(rowCount);
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


    /**
     * 转换数据对象LocalMetaData
     * @param detail
     * @return
     */
    private LocalMetaData convertLocalMetaData(LocalDataFileDetail detail){
        Date operateDate = new Date();
        LocalMetaData localMetaData = new LocalMetaData();
        localMetaData.setDataFileId(detail.getId());
        localMetaData.setStatus(LocalDataFileStatusEnum.ENTERED.getStatus());
        localMetaData.setHasOtherSave(false);
        localMetaData.setRecCreateTime(operateDate);
        localMetaData.setRecUpdateTime(operateDate);
        localMetaData.setMetaDataName((String) detail.getDynamicFields().get(ServiceConstant.LOCAL_DATA_RESOURCE_NAME));
        return localMetaData;
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
