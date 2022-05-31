package com.platon.datum.admin.service;

import com.platon.datum.admin.dao.entity.DataSync;

public interface DataSyncService {
    DataSync findDataSync(String dataType);
    int updateDataSync(DataSync dataSync);
}
