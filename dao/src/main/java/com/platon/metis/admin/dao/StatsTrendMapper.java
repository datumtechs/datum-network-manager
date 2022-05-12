package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.dto.StatsDataTrendDTO;
import com.platon.metis.admin.dao.dto.StatsPowerTrendDTO;

import java.util.List;

public interface StatsTrendMapper {

    List<StatsDataTrendDTO> listLocalDataFileStatsTrendMonthly();

    List<StatsPowerTrendDTO> listLocalPowerStatsTrendMonthly();
}
