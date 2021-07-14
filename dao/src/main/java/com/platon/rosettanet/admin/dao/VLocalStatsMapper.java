package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.VLocalStats;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VLocalStatsMapper {

    VLocalStats selectLocalStats();
}