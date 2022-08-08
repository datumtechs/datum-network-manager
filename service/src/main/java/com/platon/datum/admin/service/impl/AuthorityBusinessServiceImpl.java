package com.platon.datum.admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.exception.ValidateException;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.dao.ApplyRecordMapper;
import com.platon.datum.admin.dao.AuthorityBusinessMapper;
import com.platon.datum.admin.dao.entity.ApplyRecord;
import com.platon.datum.admin.dao.entity.AuthorityBusiness;
import com.platon.datum.admin.grpc.carrier.api.DidRpcApi;
import com.platon.datum.admin.grpc.client.DidClient;
import com.platon.datum.admin.service.AuthorityBusinessService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author liushuyu
 * @Date 2022/7/22 16:26
 * @Version
 * @Desc
 */

@Service
public class AuthorityBusinessServiceImpl implements AuthorityBusinessService {

    @Resource
    private AuthorityBusinessMapper authorityBusinessMapper;
    @Resource
    private ApplyRecordMapper applyRecordMapper;
    @Resource
    private DidClient didClient;

    /**
     * @param identityId
     * @return
     */
    @Override
    public int getTodoCount(String identityId) {
        return authorityBusinessMapper.selectTodoCount();
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param keyword
     * @return
     */
    @Override
    public Page<AuthorityBusiness> getTodoList(Integer pageNumber, Integer pageSize, String keyword) {
        Page<AuthorityBusiness> authorityBusinesses = PageHelper.startPage(pageNumber, pageSize);
        authorityBusinessMapper.selectTodoList(keyword);
        return authorityBusinesses;
    }

    /**
     * @param id
     */
    @Override
    public AuthorityBusiness getDetail(int id) {
        return authorityBusinessMapper.selectById(id);
    }

    /**
     * @param id
     * @param result
     */
    @Override
    public void processTodo(int id, int result, String remark) {
        AuthorityBusiness authorityBusiness = authorityBusinessMapper.selectById(id);
        if (authorityBusiness == null) {
            throw new BizException(Errors.QueryRecordNotExist, "Authority business record not exist");
        }
        Integer type = authorityBusiness.getType();
        switch (type) {
            case 1://签发证书
                ApplyRecord applyRecord = applyRecordMapper.selectByAuthorityBusinessId(id);
                processVc(applyRecord, result, remark);
                //1.调用调度服务处理
                break;
            case 101://默认为提案
                //1.调用调度服务处理
                break;
            case 102:
                break;
            default:
                throw new ValidateException("Unsupported business type:" + type);
        }
        //修改business表状态为处理完
        authorityBusinessMapper.updateProcessStatusById(id, result);
    }

    private void processVc(ApplyRecord applyRecord, int result, String remark) {
        applyRecord.setApproveRemark(remark);
        applyRecord.setEndTime(LocalDateTimeUtil.now());
        int processStatus = AuthorityBusiness.ProcessStatusEnum.TO_DO.getStatus();
        if (result == 1) {//同意
            applyRecord.setProgress(ApplyRecord.ProgressEnum.AGREE.getStatus());
            applyRecord.setStatus(ApplyRecord.StatusEnum.TO_BE_EFFECTIVE.getStatus());
            //调用创建vc的接口
            Pair<String, DidRpcApi.TxInfo> vcPair = didClient.createVC(applyRecord);
            applyRecord.setVc(vcPair.getLeft());
            applyRecord.setTxHash(vcPair.getRight().getTxHash());
            processStatus = AuthorityBusiness.ProcessStatusEnum.AGREE.getStatus();
        } else {//拒绝
            applyRecord.setProgress(ApplyRecord.ProgressEnum.REJECT.getStatus());
            applyRecord.setStatus(ApplyRecord.StatusEnum.INVALID.getStatus());
            processStatus = AuthorityBusiness.ProcessStatusEnum.DISAGREE.getStatus();
        }
        int count = applyRecordMapper.updateByPrimaryKeySelective(applyRecord);
        if (count <= 0) {
            throw new BizException(Errors.UpdateSqlFailed, "Update apply record failed");
        }
        count = authorityBusinessMapper.updateProcessStatusById(applyRecord.getAuthorityBusinessId(), processStatus);
        if (count <= 0) {
            throw new BizException(Errors.UpdateSqlFailed, "Update authority business failed");
        }
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param keyword
     * @return
     */
    @Override
    public Page<AuthorityBusiness> getDoneList(Integer pageNumber, Integer pageSize, String keyword) {
        Page<AuthorityBusiness> authorityBusinesses = PageHelper.startPage(pageNumber, pageSize);
        authorityBusinessMapper.selectDoneList(keyword);
        return authorityBusinesses;
    }

}
