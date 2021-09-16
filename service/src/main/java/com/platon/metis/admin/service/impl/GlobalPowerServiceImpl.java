package com.platon.metis.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.metis.admin.dao.GlobalPowerMapper;
import com.platon.metis.admin.dao.entity.GlobalPower;
import com.platon.metis.admin.service.GlobalPowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/12 2:53
 * @Version
 * @Desc
 */

@Service
@Slf4j
public class GlobalPowerServiceImpl implements GlobalPowerService {

    @Resource
    private GlobalPowerMapper globalPowerMapper;


    @Override
    public Page<GlobalPower> listGlobalPower(int pageNum, int pageSize, String keyword) {
        Page<GlobalPower> globalPowerPage = PageHelper.startPage(pageNum, pageSize);
        globalPowerMapper.listGlobalPower(keyword);
        return globalPowerPage;
    }
}
