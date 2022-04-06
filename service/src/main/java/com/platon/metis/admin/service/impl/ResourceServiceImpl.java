package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.dao.ResourceMapper;
import com.platon.metis.admin.dao.entity.Resource;
import com.platon.metis.admin.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<Resource> getResourceListByUserId(Integer role) {
        //1.根据角色获取资源
        List<Resource> resources = resourceMapper.selectByRoleId(role);
        //2.为了防止可能漏了url资源，获取菜单和按钮对应的url资源id
        List<Integer> urlResourceId = resources.stream()
                .filter(resource -> resource.getType() == 2 || resource.getType() == 3)
                .filter(resource -> resource.getUrlResourceId() != null)
                .map(Resource::getUrlResourceId)
                .distinct()
                .collect(Collectors.toList());
        List<Resource> urlResources = new ArrayList<>();
        if(urlResourceId.size() > 0){
            urlResources = resourceMapper.selectByResourceIdList(urlResourceId);
        }

        Set<Resource> set = new HashSet();
        set.addAll(resources);
        set.addAll(urlResources);
        return set;
    }
}
