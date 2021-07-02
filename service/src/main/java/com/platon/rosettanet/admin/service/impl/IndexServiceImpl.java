package com.platon.rosettanet.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.rosettanet.admin.dao.TbPowerNodeMapper;
import com.platon.rosettanet.admin.dao.entity.TbPowerNode;
import com.platon.rosettanet.admin.service.IndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2021/7/2 17:11
 * @Version
 * @Desc
 */

@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private TbPowerNodeMapper tbPowerNodeMapper;

    @Override
    public Page<TbPowerNode> nodeList(int pageNumber, int pageSize) {
        Page<TbPowerNode> nodePage = PageHelper.startPage(pageNumber, pageSize);
        List<TbPowerNode> tbPowerNodes = tbPowerNodeMapper.selectAll();
        return nodePage;
    }
}
