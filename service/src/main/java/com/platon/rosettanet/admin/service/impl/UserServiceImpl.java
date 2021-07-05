package com.platon.rosettanet.admin.service.impl;

import com.platon.rosettanet.admin.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author liushuyu
 * @Date 2021/7/3 14:35
 * @Version
 * @Desc
 */

@Service
public class UserServiceImpl implements UserService {


    @Override
    public String applyOrgName(String orgName) {
        //########1.先申请入网
        String orgId = "";
        //组织中调度服务的 nodeId


        //###########2.入网成功后再入库

        return orgId;
    }
}
