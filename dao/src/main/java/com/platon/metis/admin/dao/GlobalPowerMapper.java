package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.GlobalPower;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GlobalPowerMapper {


    /**
     * 获取全网算力信息
     * @param keyword
     */
    List<GlobalPower> listGlobalPower(String keyword);

    /**
     * 根据powerId查询出指定的数据
     * @param powerId
     * @return
     */
    GlobalPower selectByPrimaryKey(String powerId);

    /**
     * 查询数据库中的所有PowerId
     * @return
     */
    List<String> selectAllPowerId();

    /**
     * 批量保存数据，存在时更新，否则新增
     * @param globalPowerList
     * @return
     */
    int batchInsert(@Param("globalPowerList") List<GlobalPower> globalPowerList);

    /**
     * 批量保存数据，存在时更新，否则新增
     * @param globalPowerList
     * @return
     */
    int batchUpdate(@Param("globalPowerList") List<GlobalPower> globalPowerList);
}