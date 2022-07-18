package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.PowerLoadSnapshot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PowerLoadSnapshotMapper {
    List<PowerLoadSnapshot> listLocalPowerLoadSnapshotByNodeId(@Param("nodeId") String nodeId, @Param("hours") Integer hours);
}
