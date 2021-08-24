package com.platon.rosettanet.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.platon.rosettanet.admin.common.context.LocalOrgCache;
import com.platon.rosettanet.admin.common.context.LocalOrgIdentityCache;
import com.platon.rosettanet.admin.common.util.IDUtil;
import com.platon.rosettanet.admin.dao.LocalOrgMapper;
import com.platon.rosettanet.admin.dao.SysUserMapper;
import com.platon.rosettanet.admin.dao.entity.LocalOrg;
import com.platon.rosettanet.admin.dao.entity.SysUser;
import com.platon.rosettanet.admin.dao.enums.SysUserStatusEnum;
import com.platon.rosettanet.admin.service.UserService;
import com.platon.rosettanet.admin.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
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
            LocalOrgCache.setLocalOrgInfo(org);
            LocalOrgIdentityCache.setIdentityId(org.getIdentityId());
            throw new ServiceException(StrUtil.format("已存在组织信息orgId:{},orgName:{}",org.getIdentityId(),org.getName()));
        }
        //### 2.新建local org并入库
        String orgId = IDUtil.generate(IDUtil.IDENTITY_ID_PREFIX);
        LocalOrg localOrg = new LocalOrg();
        localOrg.setIdentityId(orgId);
        localOrg.setName(orgName);
        localOrg.setRecUpdateTime(new Date());
        int count = localOrgMapper.insertSelective(localOrg);
        if(count <= 0){
            throw new ServiceException("申请失败");
        }
        //### 2.新建成功后，设置缓存
        LocalOrgCache.setLocalOrgInfo(localOrg);
        LocalOrgIdentityCache.setIdentityId(localOrg.getIdentityId());
        return orgId;
    }

    @Override
    public String login(String userName, String passwd) {
        SysUser sysUser = sysUserMapper.selectByUserNameAndPwd(userName, passwd);
        if(sysUser == null){
            return null;
        }
        if(SysUserStatusEnum.DISABLED.getStatus().equals(sysUser.getStatus())){
            throw new ServiceException("用户账号状态异常");
        }
        return sysUser.getId().toString();
    }

    @Override
    public void updateOrgName(String identityId, String identityName) {
        sysUserMapper.updateOrgName(identityId, identityName);
    }

}
