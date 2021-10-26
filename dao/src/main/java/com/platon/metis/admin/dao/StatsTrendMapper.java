package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.dto.StatsTrendDTO;

import java.util.List;

public interface StatsTrendMapper {
    List<StatsTrendDTO> listGlobalDataFileStatsTrendMonthly();

    List<StatsTrendDTO> listLocalDataFileStatsTrendMonthly();

    List<StatsTrendDTO> listGlobalDataFileStatsTrendDaily();

    List<StatsTrendDTO> listGlobalPowerStatsTrendMonthly();

    List<StatsTrendDTO> listLocalPowerStatsTrendMonthly();
}
