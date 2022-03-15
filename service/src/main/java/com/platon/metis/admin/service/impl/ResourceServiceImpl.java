package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.dao.ResourceMapper;
import com.platon.metis.admin.dao.entity.Resource;
import com.platon.metis.admin.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author liushuyu
 * @Date 2022/3/16 5:16
 * @Version
 * @Desc
 */

@Service
public class ResourceServiceImpl implements ResourceService {

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> getResourceListByUserId(Integer role) {
        List<Resource> resources = resourceMapper.selectByRoleId(role);
        return resources;
    }
}
