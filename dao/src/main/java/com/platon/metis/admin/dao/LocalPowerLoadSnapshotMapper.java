package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.LocalPowerLoadSnapshot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalPowerLoadSnapshotMapper {
    List<LocalPowerLoadSnapshot> listLocalPowerLoadSnapshot(@Param("hours") String powerId, @Param("hours") Integer hours);
}
