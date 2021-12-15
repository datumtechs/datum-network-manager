package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.dao.DataSyncMapper;
import com.platon.metis.admin.dao.entity.DataSync;
import com.platon.metis.admin.service.DataSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
            dataSync.setLatestSynced(LocalDateTime.MIN);
            dataSyncMapper.insert(dataSync);
        }
        return dataSync;
    }

    @Override
    public int updateDataSync(DataSync dataSync) {
        return dataSyncMapper.update(dataSync);
    }
}
