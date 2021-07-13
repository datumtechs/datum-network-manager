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

    /**
     * 批量新增数据，一次建议最多更新1000条
     * @param globalPowerList
     * @return
     */
    int batchAddSelective(List<GlobalPower> globalPowerList);

    /**
     * 批量更新数据，一次建议最多更新1000条
     */
    int batchUpdateByIdentityIdSelective(List<GlobalPower> globalPowerList);

    /**
     * 批量删除数据，一次建议最多删除1000条
     */
    int batchDeleteByIdentityId(List<String> identityIdList);

    /**
     * 获取所有已存在数据库中的IdentityId
     * @return
     */
    List<String> selectAllIdentityId();
}