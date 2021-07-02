package com.platon.rosettanet.admin.service;

import com.github.pagehelper.Page;
import com.platon.rosettanet.admin.dao.entity.TbPowerNode;

/**
 * @Author liushuyu
 * @Date 2021/7/2 17:10
 * @Version
 * @Desc
 */
public interface IndexService {

    Page<TbPowerNode> nodeList(int pageNumber, int pageSize);
}
