package com.platon.datum.admin.dao;

import com.platon.datum.admin.dao.entity.ApplyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author juzix
 * @Date 2022/7/21 12:02
 * @Version
 * @Desc ******************************
 */
public interface ApplyRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ApplyRecord record);

    int updateByPrimaryKeySelective(ApplyRecord record);

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
     *
     * @param applyOrg
     * @return
     */
    int selectPassCount(String applyOrg);

    /**
     * 查询出本组织已发起的申请
     *
     * @param applyOrg
     * @return
     */
    int selectApplyCount(String applyOrg);

    /**
     * 查询出本组织已发出的信任证书
     */
    int selectApproveCount(String approveOrg);

    /**
     * 查询审批组织下对应状态的记录
     *
     * @param applyOrg
     * @param approveOrg
     * @return
     */
    List<ApplyRecord> selectByApplyOrgAndApproveOrg(@Param("applyOrg") String applyOrg,
                                                    @Param("approveOrg") String approveOrg,
                                                    @Param("status") Integer status);

    /**
     * 查询出组织已使用且有效的vc
     *
     * @param applyOrg
     * @return
     */
    List<ApplyRecord> selectByUsedAndValidVc(String applyOrg);

    /**
     * 查询出当前组织审批通过后还处于带生效的VC证书
     */
    List<ApplyRecord> selectByApproveOrgAndTobeEffective(@Param("approveOrg") String approveOrg);

    void removeUsed();

    List<ApplyRecord> selectByProgress(@Param("progress") Integer progress);

}