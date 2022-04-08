package com.platon.metis.admin.service;

import com.platon.metis.admin.dao.entity.SysConfig;

import java.util.List;
import java.util.Map;

/**
 * @Author liushuyu
 * @Date 2022/4/2 17:17
 * @Version
 * @Desc
 */
public interface SysConfigService {

    //增
    SysConfig addConfig(SysConfig config);

    //删
    void deleteConfig(String key);

    //改
    SysConfig updateValueByKey(String key,String value);

    //查
    List<SysConfig> getAllValidConfig();

    //获取所有系统配置key
    Map<String,String> getSystemConfigKey();

    SysConfig getConfig(String key);

    List<SysConfig> getMetaMaskConfig();
}
