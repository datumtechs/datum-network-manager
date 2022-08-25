package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.dto.StatsDataTrendDTO;
import com.platon.datum.admin.dao.dto.StatsPowerTrendDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatsTrendMapper {

    List<StatsDataTrendDTO> listLocalDataFileStatsTrendMonthly(@Param("userAddress") String userAddress);

    List<StatsPowerTrendDTO> listLocalPowerStatsTrendMonthly();
}
