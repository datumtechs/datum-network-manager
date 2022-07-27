package com.platon.datum.admin.controller.org;

import com.github.pagehelper.Page;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.ApplyRecord;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dto.CommonPageReq;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.req.GeneralOrganizationApplyDetailReq;
import com.platon.datum.admin.dto.req.GeneralOrganizationApplyReq;
import com.platon.datum.admin.dto.req.GeneralOrganizationDownloadReq;
import com.platon.datum.admin.dto.resp.GeneralOrganizationHomeResp;
import com.platon.datum.admin.service.GeneralOrganizationService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2022/7/21 12:14
 * @Version
 * @Desc
 * @since 0.5.0
 */

@Api(tags = "普通组织")
@RequestMapping("/api/v1/generalOrganization/")
@RestController
public class GeneralOrganizationController {

    @Resource
    private GeneralOrganizationService generalOrganizationService;

    /**
     * 主页内容
     *
     * @since 0.5.0
     */
    @PostMapping("/home")
    public JsonResponse<GeneralOrganizationHomeResp> home() {
        Org localOrgInfo = OrgCache.getLocalOrgInfo();

        int credentialsCount = generalOrganizationService.getCredentialsCount();
        int applyCount = generalOrganizationService.getApplyCount();

        GeneralOrganizationHomeResp resp = new GeneralOrganizationHomeResp();
        resp.setIdentityId(localOrgInfo.getIdentityId());
        resp.setIdentityName(localOrgInfo.getName());
        resp.setCredentialsCount(credentialsCount);
        resp.setApplyCount(applyCount);
        return JsonResponse.success(resp);
    }

    /**
     * 我的申请列表
     *
     * @since 0.5.0
     */
    @PostMapping("/applyList")
    public JsonResponse<List<ApplyRecord>> applyList(@RequestBody @Validated CommonPageReq req) {
        Page<ApplyRecord> applyRecordPage = generalOrganizationService.getApplyList(req.getPageNumber(), req.getPageSize());
        return JsonResponse.page(applyRecordPage);
    }

    /**
     * 查看申请详情
     *
     * @since 0.5.0
     */
    @PostMapping("/applyDetail")
    public JsonResponse<ApplyRecord> applyDetail(@RequestBody @Validated GeneralOrganizationApplyDetailReq req) {
        ApplyRecord applyRecord = generalOrganizationService.getApplyDetail(req.getId());
        return JsonResponse.success(applyRecord);
    }

    /**
     * 下载证书
     *
     * @since 0.5.0
     * TODO
     */
    @PostMapping("/download")
    public void download(@RequestBody @Validated GeneralOrganizationDownloadReq req, HttpServletResponse response) {
        generalOrganizationService.download(req.getId(), response);
    }

    /**
     * 上传资料
     *
     * @since 0.5.0
     */
    @PostMapping("/uploadmMaterial")
    public JsonResponse<String> uploadMaterial(MultipartFile file) {
        String url = generalOrganizationService.uploadMaterial(file);
        return JsonResponse.success(url);
    }

    /**
     * 申请认证
     *
     * @since 0.5.0
     * TODO
     */
    @PostMapping("/apply")
    public JsonResponse apply(@RequestBody @Validated GeneralOrganizationApplyReq req) {
        generalOrganizationService.apply(req.getApprove(), req.getRemark(), req.getMaterial(), req.getDesc());
        return JsonResponse.success();
    }

}