package com.platon.datum.admin.grpc.impl;

import cn.hutool.json.JSONUtil;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.exception.ValidateException;
import com.platon.datum.admin.common.util.DidUtil;
import com.platon.datum.admin.common.util.WalletSignUtil;
import com.platon.datum.admin.dao.ApplyRecordMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.ApplyRecord;
import com.platon.datum.admin.grpc.carrier.api.DidRpcApi;
import com.platon.datum.admin.grpc.carrier.api.VcServiceGrpc;
import com.platon.datum.admin.grpc.carrier.types.Common;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@GrpcService
@Service
public class VcGrpc extends VcServiceGrpc.VcServiceImplBase {

    @Resource
    private ApplyRecordMapper applyRecordMapper;

    /**
     * <pre>
     * 保存Vc信息
     * </pre>
     */
    @Override
    public void applyVCRemote(DidRpcApi.ApplyVCReq request,
                              io.grpc.stub.StreamObserver<Common.SimpleResponse> responseObserver) {
        log.debug("applyVCRemote, request:{}", request);
        String errorMsg = "";
        int status = 0;
        try {
            //1.判断审批人是否是本组织
            String issuerDid = request.getIssuerDid();
            if (!issuerDid.equals(OrgCache.getLocalOrgIdentityId())) {
                throw new ValidateException("Issuer is not local org");
            }
            //2.验证签名
            verifySign(request.getReqDigest(), request.getReqSignature(), DidUtil.didToHexAddress(issuerDid));
            String applicantDid = request.getApplicantDid();
            //
            if (!DidUtil.isValidDid(applicantDid)) {
                throw new ValidateException("ApplicantDid is invalid: " + applicantDid);
            }
            ApplyRecord record = getApplyRecord(request);
            int count = applyRecordMapper.insertSelective(record);
            if (count <= 0) {
                throw new BizException(Errors.SysException, "Create apply record failed");
            }
        } catch (BizException exception) {
            status = 1;
            errorMsg = exception.getErrorMessage();
        } catch (Exception exception) {
            status = 1;
            errorMsg = exception.getMessage();
        }

        Common.SimpleResponse response = Common.SimpleResponse.newBuilder()
                .setStatus(status)
                .setMsg(errorMsg)
                .build();
        log.debug("applyVCRemote response:{}", response);

        // 返回
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private ApplyRecord getApplyRecord(DidRpcApi.ApplyVCReq request) {
        ApplyRecord applicantRecord = JSONUtil.toBean(request.getExtInfo(), ApplyRecord.class);
        ApplyRecord issuerRecord = new ApplyRecord();
        issuerRecord.setPctId((int) request.getPctId());
        issuerRecord.setApplyOrg(request.getApplicantDid());
        issuerRecord.setApproveOrg(request.getIssuerDid());
        issuerRecord.setExpirationDate(request.getExpirationDate());
        issuerRecord.setContext(request.getContext());
        issuerRecord.setClaim(request.getClaim());
        issuerRecord.setStartTime(applicantRecord.getStartTime());
        issuerRecord.setApplyRemark(applicantRecord.getApplyRemark());
        issuerRecord.setMaterial(applicantRecord.getMaterial());
        issuerRecord.setMaterialDesc(applicantRecord.getMaterialDesc());
        issuerRecord.setProgress(0);
        issuerRecord.setStatus(0);
        return issuerRecord;
    }


    /**
     * <pre>
     * 下载vc信息
     * </pre>
     */
    @Override
    public void downloadVCRemote(DidRpcApi.DownloadVCReq request,
                                 io.grpc.stub.StreamObserver<DidRpcApi.DownloadVCResponse> responseObserver) {
        log.debug("downloadVCRemote, request:{}", request);
        String errorMsg = "";
        int status = 0;
        ApplyRecord applyRecord = null;
        String vc = null;
        try {
            //1.判断审批人是否是本组织
            String issuerDid = request.getIssuerDid();
            if (!issuerDid.equals(OrgCache.getLocalOrgIdentityId())) {
                throw new ValidateException("Issuer is not local org");
            }
            //2.验证签名
            verifySign(request.getReqDigest(), request.getReqSignature(), DidUtil.didToHexAddress(issuerDid));
            String applicantDid = request.getApplicantDid();
            applyRecord = applyRecordMapper.selectEffectiveByApplyOrgAndApproveOrg(applicantDid, issuerDid);
            if (applyRecord == null) {
                throw new ValidateException("Apply record not exist!");
            }
            Integer progress = applyRecord.getProgress();
            switch (progress) {
                case 0:
                    log.debug("申请中");
                    status = 1;
                    errorMsg = "Applying";
                    break;
                case 1:
                    log.debug("通过");
                    status = 0;
                    errorMsg = "Agree!";
                    vc = applyRecord.getVc();
                    break;
                case 2:
                    log.debug("拒绝");
                    status = 0;
                    errorMsg = "Reject!";
                    break;
                default:
                    log.debug("error");
                    status = 1;
                    errorMsg = "error";
            }
        } catch (ValidateException exception) {
            status = 1;
            errorMsg = exception.getErrorMessage();
        } catch (Exception exception) {
            status = 1;
            errorMsg = exception.getMessage();
        }

        DidRpcApi.DownloadVCResponse response = DidRpcApi.DownloadVCResponse.newBuilder()
                .setStatus(status)
                .setMsg(errorMsg)
                .setVc(vc)
                .setExtInfo(JSONUtil.toJsonStr(applyRecord))
                .build();
        log.debug("downloadVCRemote response:{}", response);

        // 返回
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 验证签名
     *
     * @param reqDigest     请求摘要SHA3
     * @param reqSignature  对请求摘要的签名。从req_digest,req_signature能恢复出签名私钥对应的公钥
     * @param issuerAddress carrier钱包0x地址
     */
    private void verifySign(String reqDigest, String reqSignature, String issuerAddress) throws ValidateException {
        if (!WalletSignUtil.verifySign(reqDigest, reqSignature, issuerAddress)) {
            throw new ValidateException("Verify sign failed");
        }
    }
}
