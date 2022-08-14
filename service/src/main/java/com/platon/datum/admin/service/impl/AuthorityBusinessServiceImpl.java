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
import com.platon.datum.admin.dao.entity.AuthorityBusiness.TypeEnum;
import com.platon.datum.admin.dao.entity.Business;
import com.platon.datum.admin.dao.entity.Proposal;
import com.platon.datum.admin.grpc.carrier.api.DidRpcApi;
import com.platon.datum.admin.grpc.client.DidClient;
import com.platon.datum.admin.service.AuthorityBusinessService;
import com.platon.datum.admin.service.ProposalService;
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
    private ProposalService proposalService;
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
        authorityBusinesses.forEach(authorityBusiness -> {
            if (authorityBusiness.getType() != TypeEnum.APPLY_VC.getType()) {
                //查询提案状态
                Proposal proposalDetail = proposalService.getProposalDetail(authorityBusiness.getRelationId());
                authorityBusiness.getDynamicFields().put("proposalStatus", proposalDetail.getStatus());
            }
        });
        return authorityBusinesses;
    }

    /**
     * @param id
     */
    @Override
    public Business getDetail(int id) {
        AuthorityBusiness authorityBusiness = authorityBusinessMapper.selectById(id);
        if (authorityBusiness == null) {
            throw new BizException(Errors.QueryRecordNotExist, "Authority business record not exist");
        }

        Business business = null;
        String relationId = authorityBusiness.getRelationId();
        if (authorityBusiness.getType() == TypeEnum.APPLY_VC.getType()) {
            business = applyRecordMapper.selectById(Integer.parseInt(relationId));
        } else {
            business = proposalService.getProposalDetail(relationId);
        }
        return business;
    }

    /**
     * @param id
     * @param result
     */
    @Override
    public void processTodo(int id, AuthorityBusiness.ProcessStatusEnum result, String remark) {
        AuthorityBusiness authorityBusiness = authorityBusinessMapper.selectById(id);
        if (authorityBusiness == null) {
            throw new BizException(Errors.QueryRecordNotExist, "Authority business record not exist");
        }
        TypeEnum type = TypeEnum.find(authorityBusiness.getType());
        String relationId = authorityBusiness.getRelationId();
        switch (type) {
            case APPLY_VC://签发证书
                ApplyRecord applyRecord = applyRecordMapper.selectById(Integer.parseInt(relationId));
                processVc(id, applyRecord, result.getStatus(), remark);
                //1.调用调度服务处理
                break;
            case JOIN_PROPOSAL://默认为提案
                //1.调用调度服务处理
            case KICK_PROPOSAL:
                if (result == AuthorityBusiness.ProcessStatusEnum.AGREE) {
                    proposalService.vote(relationId);
                }
                break;
            default:
                throw new ValidateException("Unsupported business type:" + type);
        }
        //修改business表状态为处理完
        authorityBusinessMapper.updateProcessStatusById(id, result.getStatus());
    }

    private void processVc(int id, ApplyRecord applyRecord, int result, String remark) {
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
        count = authorityBusinessMapper.updateProcessStatusById(id, processStatus);
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
