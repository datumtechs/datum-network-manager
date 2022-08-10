package com.platon.datum.admin.service.impl;

import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.dao.OrgMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.service.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2021/7/10 10:36
 * @Version
 * @Desc
 */

@Slf4j
@Service
public class OrgServiceImpl implements OrgService {


    @Resource
    private OrgMapper orgMapper;

    @Override
    public Org select() {
        Org org = orgMapper.select();
        return org;
    }

    @Override
    public String selectIdentityId() {
        return orgMapper.selectIdentityId();
    }

    @Override
    public Org insertSelective(Org record) {
        int i = orgMapper.insertSelective(record);
        if (i <= 0) {
            throw new BizException(Errors.InsertSqlFailed, "Insert org failed");
        }
        Org select = orgMapper.select();
        OrgCache.setLocalOrgInfo(select);
        return select;
    }

    @Override
    public Org updateSelective(Org org) {
        int i = orgMapper.updateSelective(org);
        if (i <= 0) {
            throw new BizException(Errors.UpdateSqlFailed, "Update org failed");
        }
        Org select = orgMapper.select();
        OrgCache.setLocalOrgInfo(select);
        return select;
    }

    @Override
    public Org update(Org org) {
        int i = orgMapper.update(org);
        if (i <= 0) {
            throw new BizException(Errors.UpdateSqlFailed, "Update org failed");
        }
        Org select = orgMapper.select();
        OrgCache.setLocalOrgInfo(select);
        return select;
    }

    @Override
    public Org updateIsAuthority(Integer isAuthority) {
        int i = orgMapper.updateIsAuthority(isAuthority);
        if (i <= 0) {
            throw new BizException(Errors.UpdateSqlFailed, "Update org failed");
        }
        Org select = orgMapper.select();
        OrgCache.setLocalOrgInfo(select);
        return select;
    }

    /**
     * @param credential
     */
    @Override
    public Org updateCredential(String credential) {
        int i = orgMapper.updateCredential(credential);
        if (i <= 0) {
            throw new BizException(Errors.UpdateSqlFailed, "Update org failed");
        }
        Org select = orgMapper.select();
        OrgCache.setLocalOrgInfo(select);
        return select;
    }
}
