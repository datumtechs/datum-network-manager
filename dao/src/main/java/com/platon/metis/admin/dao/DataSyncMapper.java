package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.DataSync;

public interface DataSyncMapper {
    int insert(DataSync dataSync);
    DataSync findByPK(String dataType);
    int update(DataSync dataSync);
}
