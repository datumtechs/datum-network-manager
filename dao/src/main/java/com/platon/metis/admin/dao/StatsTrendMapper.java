package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.dto.StatsPowerTrendDTO;

import java.util.List;

public interface StatsTrendMapper {
    List<StatsPowerTrendDTO> listGlobalDataFileStatsTrendMonthly();

    List<StatsPowerTrendDTO> listLocalDataFileStatsTrendMonthly();

    List<StatsPowerTrendDTO> listGlobalDataFileStatsTrendDaily();

    List<StatsPowerTrendDTO> listGlobalPowerStatsTrendMonthly();

    List<StatsPowerTrendDTO> listLocalPowerStatsTrendMonthly();
}
