package com.platon.rosettanet.admin.service.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.platon.rosettanet.admin.dao.LocalDataFileMapper;
import com.platon.rosettanet.admin.dao.LocalMetaDataColumnMapper;
import com.platon.rosettanet.admin.dao.entity.GlobalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.LocalDataFileDetail;
import com.platon.rosettanet.admin.dao.entity.LocalMetaDataColumn;
import com.platon.rosettanet.admin.dao.enums.LocalMetaDataColumnVisibleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/24 11:54
 * @Version
 * @Desc
 */

/**
 * 刷新本组织数据状态
 */

@Slf4j
//@Component
public class LocalDataRefreshTask implements ApplicationRunner {

    @Resource
    private LocalDataFileMapper localDataFileMapper;
    @Resource
    private LocalMetaDataColumnMapper localMetaDataColumnMapper;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        while(true){
            GlobalDataFileDetail globalDataFileDetail = null;
            //获取并移除此队列的头部，如果没有元素则等待（阻塞）
            try {
                globalDataFileDetail = GlobalDataRefreshTask.abq.take();
            } catch (InterruptedException e) {
                log.error("获取本组织数据失败",e);
                continue;
            }
            LocalDataFileDetail dataFile = convert(globalDataFileDetail);
            localDataFileMapper.updateByFileIdSelective(dataFile);
            dataFile.getLocalMetaDataColumnList().forEach(localMetaDataColumn -> {
                localMetaDataColumnMapper.updateByFileIdAndCindexSelective(localMetaDataColumn);
            });
        }
    }


    private LocalDataFileDetail convert(GlobalDataFileDetail globalDataFileDetail){
        Date operateDate = new Date();
        if(globalDataFileDetail == null){
            return null;
        }
        LocalDataFileDetail dataFileDetail = new LocalDataFileDetail();
        dataFileDetail.setIdentityId(globalDataFileDetail.getIdentityId());
        dataFileDetail.setFileId(globalDataFileDetail.getFileId());
        dataFileDetail.setFileName(globalDataFileDetail.getFileName());
        dataFileDetail.setFilePath(globalDataFileDetail.getFilePath());
        dataFileDetail.setFileType(globalDataFileDetail.getFileType());
        dataFileDetail.setResourceName(globalDataFileDetail.getResourceName());
        dataFileDetail.setSize(globalDataFileDetail.getSize());
        dataFileDetail.setRows(globalDataFileDetail.getRows());
        dataFileDetail.setColumns(globalDataFileDetail.getColumns());
        dataFileDetail.setHasTitle(globalDataFileDetail.getHasTitle());
        /*dataFileDetail.setRemarks(globalDataFileDetail.getRemarks());
        dataFileDetail.setStatus(globalDataFileDetail.getStatus());
        dataFileDetail.setMetaDataId(globalDataFileDetail.getMetaDataId());*/
        dataFileDetail.setRecUpdateTime(operateDate);
        //设置列信息
        List<LocalMetaDataColumn> localMetaDataColumnList = dataFileDetail.getLocalMetaDataColumnList();
        globalDataFileDetail.getMetaDataColumnList().forEach(globalMetaDataColumn -> {
            LocalMetaDataColumn column = new LocalMetaDataColumn();
            //column.setFileId(dataFileDetail.getFileId());
            column.setColumnIdx(globalMetaDataColumn.getColumnIdx());
            column.setColumnName(globalMetaDataColumn.getColumnName());
            column.setColumnType(globalMetaDataColumn.getColumnType());
            column.setSize(globalMetaDataColumn.getSize());
            column.setRemarks(globalMetaDataColumn.getRemarks());
            column.setVisible(LocalMetaDataColumnVisibleEnum.YES.getIsVisible());
            column.setRecUpdateTime(operateDate);
            localMetaDataColumnList.add(column);
        });
        return dataFileDetail;
    }

}
