package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.LocalPowerLoadSnapshot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalPowerLoadSnapshotMapper {
    List<LocalPowerLoadSnapshot> listLocalPowerLoadSnapshotByNodeId(@Param("nodeId") String nodeId, @Param("hours") Integer hours);
}
