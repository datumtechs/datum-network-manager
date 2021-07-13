package com.platon.rosettanet.admin.service.impl;

import com.platon.rosettanet.admin.dao.SysUserMapper;
import com.platon.rosettanet.admin.dao.entity.SysUser;
import com.platon.rosettanet.admin.dao.enums.SysUserStatusEnum;
import com.platon.rosettanet.admin.service.UserService;
import com.platon.rosettanet.admin.service.exception.ServiceException;
import org.springframework.stereotype.Service;

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

    @Override
    public String applyOrgName(String orgName) {
        //########1.先申请入网
        String orgId = "";
        //组织中调度服务的 nodeId


        //###########2.入网成功后再入库

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
}
