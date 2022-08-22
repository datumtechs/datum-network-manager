package com.platon.datum.admin.grpc.impl;

import cn.hutool.json.JSONUtil;
import com.platon.bech32.Bech32;
import com.platon.crypto.ECKeyPair;
import com.platon.crypto.Keys;
import com.platon.crypto.Sign;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.exception.ValidateException;
import com.platon.datum.admin.common.util.DidUtil;
import com.platon.datum.admin.common.util.LocalDateTimeUtil;
import com.platon.datum.admin.common.util.WalletSignUtil;
import com.platon.datum.admin.dao.ApplyRecordMapper;
import com.platon.datum.admin.dao.AuthorityBusinessMapper;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.ApplyRecord;
import com.platon.datum.admin.dao.entity.AuthorityBusiness;
import com.platon.datum.admin.grpc.carrier.api.DidRpcApi;
import com.platon.datum.admin.grpc.carrier.api.VcServiceGrpc;
import com.platon.datum.admin.grpc.carrier.types.Common;
import com.platon.parameters.NetworkParameters;
import com.platon.utils.Numeric;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * @author liushuyu
 */
@Slf4j
@GrpcService
@Service
public class VcGrpc extends VcServiceGrpc.VcServiceImplBase {

    @Resource
    private ApplyRecordMapper applyRecordMapper;
    @Resource
    private AuthorityBusinessMapper authorityBusinessMapper;

    /**
     * <pre>
     * 委员会成员组织Carrier使用该接口，保存Vc信息
     * </pre>
     */
    @Transactional(rollbackFor = Throwable.class)
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
            //3.验证did格式
            if (!DidUtil.isValidDid(applicantDid)) {
                throw new ValidateException("ApplicantDid is invalid: " + applicantDid);
            }
            //4.验证是否存在有效或者待生效的申请
            List<ApplyRecord> validApplyRecords =
                    applyRecordMapper.selectByApplyOrgAndApproveOrg(applicantDid, issuerDid, ApplyRecord.StatusEnum.VALID.getStatus());
            if (!validApplyRecords.isEmpty()) {
                throw new ValidateException("Effective records are exist");
            }
            List<ApplyRecord> toBeEffectiveApplyRecords =
                    applyRecordMapper.selectByApplyOrgAndApproveOrg(applicantDid, issuerDid, ApplyRecord.StatusEnum.TO_BE_EFFECTIVE.getStatus());
            if (!toBeEffectiveApplyRecords.isEmpty()) {
                throw new ValidateException("Records are exist which to be effective");
            }

            //将请求转换成ApplyRecord对象
            ApplyRecord record = getApplyRecord(request);
            //新增一条申请VC的记录
            int count = applyRecordMapper.insertSelective(record);
            if (count <= 0) {
                throw new BizException(Errors.InsertSqlFailed, "Create apply record failed");
            }
            //新增一条委员会待处理的事务
            AuthorityBusiness authorityBusiness = getAuthorityBusiness(record);
            authorityBusinessMapper.insertSelectiveReturnId(authorityBusiness);
            if (authorityBusiness.getId() <= 0) {
                throw new BizException(Errors.InsertSqlFailed, "Create authority business failed");
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

    private AuthorityBusiness getAuthorityBusiness(ApplyRecord record) {
        AuthorityBusiness authorityBusiness = new AuthorityBusiness();
        authorityBusiness.setType(AuthorityBusiness.TypeEnum.APPLY_VC.getType());
        authorityBusiness.setRelationId(record.getId().toString());
        authorityBusiness.setApplyOrg(record.getApplyOrg());
        authorityBusiness.setSpecifyOrg(record.getApproveOrg());
        authorityBusiness.setStartTime(LocalDateTimeUtil.now());
        authorityBusiness.setProcessStatus(AuthorityBusiness.ProcessStatusEnum.TO_DO.getStatus());
        return authorityBusiness;
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
        issuerRecord.setProgress(ApplyRecord.ProgressEnum.APPLYING.getStatus());
        issuerRecord.setStatus(ApplyRecord.StatusEnum.TO_BE_EFFECTIVE.getStatus());
        return issuerRecord;
    }


    /**
     * <pre>
     * 委员会成员组织Carrier使用该接口，下载vc信息
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
            List<ApplyRecord> applyRecordList = applyRecordMapper.selectByApplyOrgAndApproveOrg(applicantDid,
                    issuerDid,
                    ApplyRecord.StatusEnum.VALID.getStatus());
            if (applyRecordList.isEmpty()) {
                throw new ValidateException("Apply record not exist!");
            }
            applyRecord = applyRecordList.get(0);
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

        //status = 0 表示已经处理该申请，已拒绝或则已同意
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
    private static void verifySign(String reqDigest, String reqSignature, String issuerAddress) throws ValidateException {
//        if (!WalletSignUtil.verifySign(reqDigest, reqSignature, issuerAddress)) {
//            throw new ValidateException("Verify sign failed");
//        }
//        try {
//            byte[] digest = Numeric.hexStringToByteArray(reqDigest);
//            System.out.println(Hex.encodeHex(digest));
//            byte[] goSignData = Numeric.hexStringToByteArray(reqSignature);
//            byte[] v = new byte[1];
//            byte[] r = new byte[32];
//            byte[] s = new byte[32];
//            System.arraycopy(goSignData, 0, r, 0, 32);
//            System.arraycopy(goSignData, 32, s, 0, 32);
//            System.arraycopy(goSignData, 64, v, 0, 1);
//            v[0] = (byte) (v[0] + 27);
//            Sign.SignatureData signatureData = new Sign.SignatureData(v, r, s);
//            BigInteger signPublicKey = Sign.signedMessageToKey(digest, signatureData);
//
//            String signPublicKeyStr = Numeric.toHexStringWithPrefix(signPublicKey);
//            String address = Keys.getAddress(signPublicKeyStr);
//
//            if (!address.equalsIgnoreCase(issuerAddress)) {
//                throw new ValidateException("Verify sign failed");
//            }
//        } catch (Exception exception) {
//            log.error(exception.getMessage(),exception);
//            throw new ValidateException(exception.getMessage());
//        }
    }

    public static void main(String[] args) {
        String req_digest = "0xebd5186e34a8a67b541cf27b17106bae3c77d11154d6e72c2a2f6f243bc04533";
        String req_signature = "0x036e119f9ddf6bdb7c41198b45790b6f81e9bcf35a95a3f47bcddfa1b6994e5a29d5b7a2c448c9c03336afe6858eba59860828a2a0c404d4f16dedc60f5ebb9b00";
        verifySign(req_digest,req_signature,"0xE665Bc266B63026F35aEAf54770443a87973dBe5");
    }
}
