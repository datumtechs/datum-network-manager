package com.platon.datum.admin.service.impl;

import com.platon.datum.admin.dao.ApplyRecordMapper;
import com.platon.datum.admin.dao.AuthorityMapper;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.service.AuthorityService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * @param identityId
     * @return
     */
    @Override
    public int getAuthorityCount(String identityId) {
        int i = authorityMapper.selectCount();
        return i;
    }

    /**
     * @param identityId
     * @return
     */
    @Override
    public int getApproveCount(String identityId) {
        int i = applyRecordMapper.selectApproveCount(identityId);
        return i;
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
     * @param id
     */
    @Override
    public void kickOut(int id) {

    }

    /**
     *
     */
    @Override
    public void exit() {

    }

    /**
     * @param file
     */
    @Override
    public void upload(MultipartFile file) {

    }

    /**
     * @param identityId
     * @param ip
     * @param port
     * @param remark
     * @param material
     * @param materialDesc
     */
    @Override
    public void nominate(String identityId, String ip, int port, String remark, String material, String materialDesc) {

    }
}
