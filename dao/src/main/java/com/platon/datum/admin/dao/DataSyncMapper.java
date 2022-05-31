package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.DataSync;

public interface DataSyncMapper {
    int insert(DataSync dataSync);
    DataSync findByPK(String dataType);
    int update(DataSync dataSync);
}
