package com.platon.rosettanet.admin.service;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.VLocalStats;

/**
 * @Author liushuyu
 * @Date 2021/7/2 17:10
 * @Version
 * @Desc
 */
public interface IndexService {

    /**
     * 获取本组织的统计数据
     * @return
     */
    VLocalStats getOverview();

    /**
     * 获取本组织计算节点列表信息
     */
    void getPowerNodeList();
}
