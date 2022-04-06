package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.common.exception.SysException;
import com.platon.metis.admin.dao.SysConfigMapper;
import com.platon.metis.admin.dao.entity.SysConfig;
import com.platon.metis.admin.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2022/4/2 17:20
 * @Version
 * @Desc
 */

@Service
@Slf4j
public class SysConfigServiceImpl implements SysConfigService {

    @Resource
    private SysConfigMapper sysConfigMapper;


    @Override
    public SysConfig addConfig(SysConfig config) {
        sysConfigMapper.insertConfig(config);
        return sysConfigMapper.selectByKey(config.getKey());
    }

    @Override
    public void deleteConfig(String key) {
        boolean isSystemConfigKey = Arrays.stream(SysConfig.KeyEnum.values()).anyMatch(keyEnum -> keyEnum.getKey().equals(key));
        if(isSystemConfigKey){
            throw new SysException("The system configuration is not allowed to be changed!");
        }
        sysConfigMapper.deleteByKey(key);
    }

    @Override
    public SysConfig updateValueByKey(String key, String value) {
        sysConfigMapper.updateValueByKey(key,value);
        return sysConfigMapper.selectByKey(key);
    }

    @Override
    public List<SysConfig> getAllValidConfig() {
        return sysConfigMapper.selectAllByStatus(SysConfig.StatusEnum.VALID.getStatus());
    }

    @Override
    public Map<String,String> getSystemConfigKey() {
        return Arrays.stream(SysConfig.KeyEnum.values()).collect(Collectors.toMap(SysConfig.KeyEnum::getKey,SysConfig.KeyEnum::getDesc));
    }

    @Override
    public SysConfig getConfig(String key) {
        return sysConfigMapper.selectByKeyAndStatus(key,SysConfig.StatusEnum.VALID.getStatus());
    }
}
