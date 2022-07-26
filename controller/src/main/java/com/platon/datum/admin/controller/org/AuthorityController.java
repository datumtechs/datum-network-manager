package com.platon.datum.admin.controller.org;

import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Authority;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.req.*;
import com.platon.datum.admin.dto.resp.AuthorityHomeResp;
import com.platon.datum.admin.service.AuthorityBusinessService;
import com.platon.datum.admin.service.AuthorityService;
import com.platon.datum.admin.service.ProposalService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author liushuyu
 * @Date 2022/7/21 12:10
 * @Version
 * @Desc
 * @since 0.5.0
 */

@Api(tags = "委员会组织")
@RequestMapping("/api/v1/authority/")
@RestController
public class AuthorityController {

    @Resource
    private AuthorityService authorityService;
    @Resource
    private AuthorityBusinessService authorityBusinessService;
    @Resource
    private ProposalService proposalService;

    /**
     * 主页内容
     * @since 0.5.0
     */
    @PostMapping("/home")
    public JsonResponse<AuthorityHomeResp> home() {
        Org localOrgInfo = OrgCache.getLocalOrgInfo();

        int authorityCount = authorityService.getAuthorityCount(localOrgInfo.getIdentityId());
        int approveCount = authorityService.getApproveCount(localOrgInfo.getIdentityId());
        int todoCount = authorityBusinessService.getTodoCount(localOrgInfo.getIdentityId());
        int proposalCount = proposalService.getProposalCount(localOrgInfo.getIdentityId());

        AuthorityHomeResp resp = new AuthorityHomeResp();
        resp.setIdentityId(localOrgInfo.getIdentityId());
        resp.setIdentityName(localOrgInfo.getName());
        resp.setAuthorityCount(authorityCount);
        resp.setApproveCount(approveCount);
        resp.setTodoCount(todoCount);
        resp.setProposalCount(proposalCount);
        return JsonResponse.success(resp);
    }

    /**
     * 委员会列表，不支持分页，根据组织名模糊查询
     * @since 0.5.0
     */
    @PostMapping("/list")
    public JsonResponse<List<Authority>> list(@RequestBody @Validated AuthorityListReq req) {
        List<Authority> authorityList = authorityService.getAuthorityList(req.getKeyword());
        return JsonResponse.success(authorityList);
    }


    /**
     * 提名踢出
     * @since 0.5.0
     */
    @PostMapping("/kickOut")
    public JsonResponse kickOut(AuthorityKickOutReq req) {
        authorityService.kickOut(req.getId());
        return JsonResponse.success();
    }

    /**
     * 退出委员会
     * @since 0.5.0
     */
    @PostMapping("/exit")
    public JsonResponse exit() {
        authorityService.exit();
        return JsonResponse.success();
    }

    /**
     * 上传资料
     * @since 0.5.0
     */
    @PostMapping("/upload")
    public JsonResponse upload(MultipartFile file) {
        authorityService.upload(file);
        return JsonResponse.success();
    }

    /**
     * 提名成员
     * @since 0.5.0
     */
    @PostMapping("/nominate")
    public JsonResponse nominate(AuthorityNominateReq req) {
        authorityService.nominate(req.getIdentityId(), req.getIp(), req.getPort(), req.getRemark(), req.getMaterial(), req.getMaterialDesc());
        return JsonResponse.success();
    }

    /** ===========委员会事务================= **/

    /**
     * 我的待办，按组织名称模糊分页查询
     * @since 0.5.0
     */
    @PostMapping("/todoList")
    public JsonResponse todoList(AuthorityTodoListReq req) {
        authorityBusinessService.getTodoList(req.getPageNumber(), req.getPageSize(), req.getKeyword());
        return JsonResponse.success();
    }

    /**
     * 我的待办详情
     * @since 0.5.0
     */
    @PostMapping("/todoDetail")
    public JsonResponse todoDetail(AuthorityTodoDetailReq req) {
        authorityBusinessService.getTodoDetail(req.getId());
        return JsonResponse.success();
    }

    /**
     * 处理我的待办
     * @since 0.5.0
     */
    @PostMapping("/processTodo")
    public JsonResponse processTodo(AuthorityProcessTodoReq req) {
        authorityBusinessService.processTodo(req.getId(), req.getResult());
        return JsonResponse.success();
    }

    /**
     * 我的已办，按组织名称模糊分页查询
     * @since 0.5.0
     */
    @PostMapping("/doneList")
    public JsonResponse doneList(AuthorityDoneListReq req) {
        authorityBusinessService.getDoneList(req.getPageNumber(), req.getPageSize(), req.getKeyword());
        return JsonResponse.success();
    }

    /**
     * 我的已办详情
     * @since 0.5.0
     */
    @PostMapping("/doneDetail")
    public JsonResponse doneDetail(AuthorityDoneDetailReq req) {
        authorityBusinessService.getDoneDetail(req.getId());
        return JsonResponse.success();
    }

    /**
     * 我的提案，按组织名称模糊分页查询
     * @since 0.5.0
     */
    @PostMapping("/proposalList")
    public JsonResponse proposalList(AuthorityProposalListReq req) {
        proposalService.getProposalList(req.getPageNumber(), req.getPageSize(), req.getKeyword());
        return JsonResponse.success();
    }

    /**
     * 我的提案详情
     * @since 0.5.0
     */
    @PostMapping("/proposalDetail")
    public JsonResponse proposalDetail(AuthorityProposalDetailReq req) {
        proposalService.getProposalDetail(req.getId());
        return JsonResponse.success();
    }

    /**
     * 撤回提案
     * @since 0.5.0
     */
    @PostMapping("/revokeProposal")
    public JsonResponse revokeProposal(AuthorityRevokeProposalReq req) {
        proposalService.revokeProposal(req.getId());
        return JsonResponse.success();
    }

}
