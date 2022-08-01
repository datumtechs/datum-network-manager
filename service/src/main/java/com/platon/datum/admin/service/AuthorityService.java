package com.platon.datum.admin.service;

import com.platon.datum.admin.dao.entity.Authority;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author liushuyu
 * @Date 2022/7/22 12:20
 * @Version
 * @Desc
 */
public interface AuthorityService {
    int getAuthorityCount(String identityId);

    int getApproveCount(String identityId);

    List<Authority> getAuthorityList(String keyword);

    void kickOut(String identityId);

    void exit();

    String upload(MultipartFile file);

    void nominate(String identityId, String ip, int port, String remark, String material, String materialDesc);
}
