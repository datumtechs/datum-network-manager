package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.dto.StatsDataTrendDTO;
import com.platon.datum.admin.dao.dto.StatsPowerTrendDTO;

import java.util.List;

public interface StatsTrendMapper {

    List<StatsDataTrendDTO> listLocalDataFileStatsTrendMonthly();

    List<StatsPowerTrendDTO> listLocalPowerStatsTrendMonthly();
}
