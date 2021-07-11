package com.platon.rosettanet.admin.service;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.GlobalPower;

/**
 * @Author liushuyu
 * @Date 2021/7/12 2:53
 * @Version
 * @Desc
 */
public interface GlobalPowerService {

    /**
     * 分页查询全网算力
     * @param pageNum
     * @param pageSize
     * @param keyword 关键字查询，暂时只支持名称查询
     * @return
     */
    Page<GlobalPower> listGlobalPower(int pageNum, int pageSize, String keyword);
}
