package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.common.exception.OrgInfoExists;
import com.platon.metis.admin.common.util.IDUtil;
import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.SysUserMapper;
import com.platon.metis.admin.dao.cache.LocalOrgCache;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.dao.entity.SysUser;
import com.platon.metis.admin.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:35
 * @Version
 * @Desc
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private LocalOrgMapper localOrgMapper;

    @Override
    public String applyOrgIdentity(String orgName) {
        //### 1.校验是否已存在组织信息
        LocalOrg org = localOrgMapper.select();
        if(org != null){
            //LocalOrgCache.setLocalOrgInfo(org);
            throw new OrgInfoExists();
        }
        //### 2.新建local org并入库
        String orgId = IDUtil.generate(IDUtil.IDENTITY_ID_PREFIX);
        LocalOrg localOrg = new LocalOrg();
        localOrg.setIdentityId(orgId);
        localOrg.setName(orgName);
        localOrg.setStatus(LocalOrg.Status.NOT_CONNECT_NET.getCode());
        localOrgMapper.insert(localOrg);

        //### 2.新建成功后，设置缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        return orgId;
    }

    @Override
    public SysUser getByAddress(String address) {
        return sysUserMapper.selectByAddress(address);
    }

    @Transactional
    @Override
    public SysUser createUser(String hexAddress) {
        SysUser user = new SysUser();
        user.setUserName(hexAddress);
        user.setAddress(hexAddress);
        user.setStatus(1);
        user.setIsAdmin(0);
        //1.新增用户
        int insert = sysUserMapper.insert(user);
        if(insert == 0){
            //TODO 插入失败
        } else {
            //2.尝试设置为管理员
            int update = sysUserMapper.updateSingleUserToAdmin();
            if(update == 0){
                //TODO 更新失败
            } else {
                user.setIsAdmin(1);
            }
        }
        return user;
    }

    @Override
    public void updateByAddress(SysUser sysUser) {
        int update = sysUserMapper.updateByAddress(sysUser);
        if(update == 0){
            //TODO 更新失败
        }
    }


}
