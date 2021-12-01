package com.platon.metis.admin.service;

import com.platon.metis.admin.dao.entity.DataSync;

public interface DataSyncService {
    DataSync findDataSync(String dataType);
    int updateDataSync(DataSync dataSync);
}
