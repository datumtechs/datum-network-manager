package com.platon.metis.admin.dao;

import com.platon.metis.admin.dao.entity.SysConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author liushuyu
 * @Date 2022/4/2 16:59
 * @Version
 * @Desc
 */
public interface SysConfigMapper {

    SysConfig selectByKeyAndStatus(@Param("key") String key,@Param("status") int status);

    List<SysConfig> selectAllByStatus(int status);

    SysConfig selectByKey(String key);

    int insertConfig(SysConfig config);

    int updateValueByKey(@Param("key") String key, @Param("value") String value);

    int deleteByKey(String key);
}
