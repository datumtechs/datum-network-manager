package com.platon.datum.admin.service.impl;

import com.platon.datum.admin.dao.ApplyRecordMapper;
import com.platon.datum.admin.dao.AuthorityMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.service.AuthorityService;
import com.platon.datum.admin.service.OrgService;
import com.platon.datum.admin.service.VoteContract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2022/7/22 15:31
 * @Version
 * @Desc
 */

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private AuthorityMapper authorityMapper;
    @Resource
    private ApplyRecordMapper applyRecordMapper;
    @Resource
    private VoteContract voteContract;
    @Resource
    private OrgService orgService;


    /**
     * @param identityId
     * @return
     */
    @Override
    public int getAuthorityCount(String identityId) {
        int count = authorityMapper.selectCount();
        return count;
    }

    /**
     * @param identityId
     * @return
     */
    @Override
    public int getApproveCount(String identityId) {
        int count = applyRecordMapper.selectApproveCount(identityId);
        return count;
    }

    /**
     * @param keyword
     * @return
     */
    @Override
    public List<Authority> getAuthorityList(String keyword) {
        List<Authority> authorityList = authorityMapper.selectList(keyword);
        return authorityList;
    }

    /**
     * 刷新委员会成员列表
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void refreshAuthority() {
        List<Authority> allAuthority = voteContract.getAllAuthority();
        //将原来的删除并将新的存进去
        authorityMapper.refresh(allAuthority);

        //判断本组织是否是委员会成员
        Authority authority = authorityMapper.selectByPrimaryKey(OrgCache.getLocalOrgIdentityId());
        if (authority == null) {
            orgService.updateIsAuthority(0);
        } else {
            orgService.updateIsAuthority(1);
        }
    }
}
