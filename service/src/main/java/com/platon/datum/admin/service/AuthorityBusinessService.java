package com.platon.datum.admin.service;

import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.entity.AuthorityBusiness;
import com.platon.datum.admin.dao.entity.Business;
import com.platon.datum.admin.dao.entity.Proposal;

/**
 * @Author liushuyu
 * @Date 2022/7/22 15:24
 * @Version
 * @Desc
 */
public interface AuthorityBusinessService {
    int getTodoCount(String identityId);

    Page<AuthorityBusiness> getTodoList(Integer pageNumber, Integer pageSize, String keyword);

    Business getDetail(int id);

    /**
     * @param id
     * @param result {@link AuthorityBusiness.ProcessStatusEnum}
     * @param remark
     */
    void processTodo(int id, AuthorityBusiness.ProcessStatusEnum result, String remark);

    Page<AuthorityBusiness> getDoneList(Integer pageNumber, Integer pageSize, String keyword);

}
