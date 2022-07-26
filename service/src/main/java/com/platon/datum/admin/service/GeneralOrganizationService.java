package com.platon.datum.admin.service;

import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.entity.ApplyRecord;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author liushuyu
 * @Date 2022/7/22 11:18
 * @Version
 * @Desc
 */
public interface GeneralOrganizationService {
    int getCredentialsCount();

    int getApplyCount();

    Page<ApplyRecord> getApplyList(Integer pageNumber, Integer pageSize);

    ApplyRecord getApplyDetail(Integer id);

    /**
     * 通过委员会成员调度服务下载证书
     * @param id
     * @param response
     */
    void download(Integer id, HttpServletResponse response);

    String uploadMaterial(MultipartFile file);

    void apply(String approve, String remark, String material, String desc);
}
