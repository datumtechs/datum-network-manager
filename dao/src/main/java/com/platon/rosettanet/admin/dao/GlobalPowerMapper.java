package com.platon.rosettanet.admin.dao;

import com.platon.rosettanet.admin.dao.entity.GlobalPower;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GlobalPowerMapper {


    /**
     * 获取全网算力信息
     * @param keyword
     */
    List<GlobalPower> listGlobalPower(String keyword);
}