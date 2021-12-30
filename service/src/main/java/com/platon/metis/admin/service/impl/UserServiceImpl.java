package com.platon.metis.admin.service.impl;

import com.platon.metis.admin.common.exception.OrgInfoExists;
import com.platon.metis.admin.common.exception.UserAccountInvalid;
import com.platon.metis.admin.common.exception.UserAccountOrPwdError;
import com.platon.metis.admin.common.util.IDUtil;
import com.platon.metis.admin.dao.LocalOrgMapper;
import com.platon.metis.admin.dao.SysUserMapper;
import com.platon.metis.admin.dao.cache.LocalOrgCache;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.dao.entity.SysUser;
import com.platon.metis.admin.dao.enums.SysUserStatusEnum;
import com.platon.metis.admin.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
        localOrg.setRecUpdateTime(new Date());
        localOrgMapper.insertSelective(localOrg);

        //### 2.新建成功后，设置缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        return orgId;
    }

    @Override
    public String login(String userName, String passwd) {
        SysUser sysUser = sysUserMapper.selectByUserNameAndPwd(userName, passwd);
        if(sysUser == null){
            throw new UserAccountOrPwdError();
        }
        if(SysUserStatusEnum.DISABLED.getStatus().equals(sysUser.getStatus())){
            throw new UserAccountInvalid();
        }
        return sysUser.getId().toString();
    }

}
