package com.platon.datum.admin.service.impl;

import com.platon.datum.admin.dao.DataSyncMapper;
import com.platon.datum.admin.dao.entity.DataSync;
import com.platon.datum.admin.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class DataSyncServiceImpl implements DataSyncService {

    @Resource
    private DataSyncMapper dataSyncMapper;

    @Override
    public DataSync findDataSync(String dataType) {
        DataSync dataSync = dataSyncMapper.findByPK(dataType);

        if(dataSync==null){
            dataSync = new DataSync();
            dataSync.setDataType(dataType);
            dataSync.setLatestSynced(LocalDateTime.parse("1970-01-01 00:00:00",  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            dataSyncMapper.insert(dataSync);
        }
        return dataSync;
    }

    @Override
    public int updateDataSync(DataSync dataSync) {
        return dataSyncMapper.update(dataSync);
    }
}
