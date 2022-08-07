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

    /**
     * 获取当前已使用的VC
     * @return
     */
    ApplyRecord getCurrentUsingVc();

    Page<ApplyRecord> getApplyList(Integer pageNumber, Integer pageSize);

    ApplyRecord getApplyDetail(Integer id);

    /**
     * 通过委员会成员调度服务下载证书
     * @param id
     * @param response
     */
    void download(Integer id, HttpServletResponse response);

    /**
     * 上传材料
     * @param file 上传的文件
     * @return ipfs的url
     */
    String uploadMaterial(MultipartFile file);

    /**
     * @param approve 审批组织
     * @param remark 申请的备注
     * @param material 申请材料的ipfsUrl
     * @param desc 申请材料的描述
     */
    void apply(String approve, String remark, String material, String desc);

    void use(Integer id);
}
