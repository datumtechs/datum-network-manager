package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.ApplyRecord;

import java.util.List;

/**
 * @Author juzix
 * @Date 2022/7/21 12:02
 * @Version
 * @Desc ******************************
 */
public interface ApplyRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplyRecord record);

    int insertSelective(ApplyRecord record);

    int updateByPrimaryKeySelective(ApplyRecord record);

    int updateByPrimaryKey(ApplyRecord record);

    /**
     * 查询出属于我的认证申请列表，根据认证发起时间倒序
     *
     * @param applyOrg
     * @return
     */
    List<ApplyRecord> selectListByApplyOrg(String applyOrg);

    /**
     * 根据id查看认证详情
     *
     * @param id
     * @return
     */
    ApplyRecord selectById(Integer id);

    /**
     * 查询出本组织已获取的认证
     * @param applyOrg
     * @return
     */
    int selectPassCount(String applyOrg);

    /**
     * 查询出本组织已发起的申请
     * @param applyOrg
     * @return
     */
    int selectApplyCount(String applyOrg);

    /**
     * 查询出本组织已发出的信任证书
     */
    int selectApproveCount(String approveOrg);
}