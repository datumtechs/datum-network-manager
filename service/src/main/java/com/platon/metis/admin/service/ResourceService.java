package com.platon.metis.admin.service;

import com.platon.metis.admin.dao.entity.Resource;

import java.util.List;

/**
 * @Author liushuyu
 * @Date 2022/3/16 5:15
 * @Version
 * @Desc
 */
public interface ResourceService {

    /**
     * 获取资源列表
     * @return
     */
    List<Resource> getResourceListByUserId(Integer userId);
}
