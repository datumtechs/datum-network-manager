package com.platon.datum.admin.controller.org;

import com.github.pagehelper.Page;
import com.platon.datum.admin.common.exception.ValidateException;
import com.platon.datum.admin.dao.BaseDomain;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.*;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.req.*;
import com.platon.datum.admin.dto.resp.AuthorityHomeResp;
import com.platon.datum.admin.service.AuthorityBusinessService;
import com.platon.datum.admin.service.AuthorityService;
import com.platon.datum.admin.service.ProposalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${pinata-gateway}")
    private String pinataGateway;

    /**
     * 主页内容
     *
     * @since 0.5.0
     */
    @ApiOperation("主页内容")
    @PostMapping("/home")
    public JsonResponse<AuthorityHomeResp> home() {
        Org localOrgInfo = OrgCache.getLocalOrgInfo();

        int authorityCount = authorityService.getAuthorityCount(localOrgInfo.getIdentityId());
        int approveCount = authorityService.getApproveCount(localOrgInfo.getIdentityId());
        int todoCount = authorityBusinessService.getTodoCount(localOrgInfo.getIdentityId());
        int proposalCount = proposalService.getProposalCount(localOrgInfo.getIdentityId());

        AuthorityHomeResp resp = new AuthorityHomeResp();
        BeanUtils.copyProperties(localOrgInfo, resp);
        resp.setAuthorityCount(authorityCount);
        resp.setApproveCount(approveCount);
        resp.setTodoCount(todoCount);
        resp.setProposalCount(proposalCount);
        return JsonResponse.success(resp);
    }

    /**
     * 委员会列表，不支持分页，根据组织名模糊查询
     *
     * @since 0.5.0
     */
    @ApiOperation("委员会列表")
    @PostMapping("/list")
    public JsonResponse<List<Authority>> list(@RequestBody @Validated AuthorityListReq req) {
        List<Authority> authorityList = authorityService.getAuthorityList(req.getKeyword());
        return JsonResponse.success(authorityList);
    }


    /**
     * 提名踢出
     *
     * @since 0.5.0
     */
    @ApiOperation("提名踢出")
    @PostMapping("/kickOut")
    public JsonResponse kickOut(@RequestBody @Validated AuthorityKickOutReq req) {
        proposalService.kickOut(req.getIdentityId(), req.getRemark(), req.getMaterial(), req.getMaterialDesc());
        return JsonResponse.success();
    }

    /**
     * 退出委员会
     *
     * @since 0.5.0
     */
    @ApiOperation("退出委员会")
    @PostMapping("/exit")
    public JsonResponse exit() {
        proposalService.exit();
        return JsonResponse.success();
    }

    /**
     * 提名委员会时上传图片
     *
     * @since 0.5.0
     */
    @ApiOperation("提名委员会时上传图片")
    @PostMapping("/upload")
    public JsonResponse upload(MultipartFile file) {
        String url = proposalService.upload(file);
        return JsonResponse.success(url);
    }

    /**
     * 获取可提名的成员
     *
     * @since 0.5.0
     */
    @ApiOperation("获取可提名的成员")
    @PostMapping("/getNominateMember")
    public JsonResponse<List<GlobalOrg>> getNominateMember(@RequestBody @Validated AuthorityGetNominateMemberReq req) {
        List<GlobalOrg> globalOrgList = proposalService.getNominateMember(req.getKeyword());
        return JsonResponse.success(globalOrgList);
    }

    /**
     * 提名成员,需要将部分内容上传ipfs
     *
     * @since 0.5.0
     */
    @ApiOperation("提名成员")
    @PostMapping("/nominate")
    public JsonResponse nominate(@RequestBody @Validated AuthorityNominateReq req) {
        proposalService.nominate(req.getIdentityId(), req.getIp(), req.getPort(), req.getRemark(), req.getMaterial(), req.getMaterialDesc());
        return JsonResponse.success();
    }

    /** ===========委员会事务================= **/

    /**
     * 我的待办，按组织名称模糊分页查询
     *
     * @since 0.5.0
     */
    @ApiOperation("我的待办列表")
    @PostMapping("/todoList")
    public JsonResponse<List<AuthorityBusiness>> todoList(@RequestBody @Validated AuthorityTodoListReq req) {
        Page<AuthorityBusiness> todoList = authorityBusinessService.getTodoList(req.getPageNumber(), req.getPageSize(), req.getKeyword());
        return JsonResponse.success(todoList);
    }

    /**
     * 我的待办详情
     *
     * @since 0.5.0
     */
    @ApiOperation("我的待办详情")
    @PostMapping("/todoDetail")
    public JsonResponse<Business> todoDetail(@RequestBody @Validated AuthorityTodoDetailReq req) {
        Business todoDetail = authorityBusinessService.getDetail(req.getId());
        if (todoDetail instanceof BaseDomain) {
            ((BaseDomain) todoDetail).getDynamicFields().put("pinataGateway", pinataGateway);
        }
        return JsonResponse.success(todoDetail);
    }

    /**
     * 处理我的待办
     *
     * @since 0.5.0
     */
    @ApiOperation("处理我的待办")
    @PostMapping("/processTodo")
    public JsonResponse processTodo(@RequestBody @Validated AuthorityProcessTodoReq req) {
        Integer result = req.getResult();
        AuthorityBusiness.ProcessStatusEnum processStatusEnum = AuthorityBusiness.ProcessStatusEnum.find(result);
        if (AuthorityBusiness.ProcessStatusEnum.find(result) == null) {
            throw new ValidateException("Unsupported process result : " + result);
        }
        authorityBusinessService.processTodo(req.getId(), processStatusEnum, req.getRemark());
        return JsonResponse.success();
    }

    /**
     * 我的已办，按组织名称模糊分页查询
     *
     * @since 0.5.0
     */
    @ApiOperation("我的已办列表")
    @PostMapping("/doneList")
    public JsonResponse<List<AuthorityBusiness>> doneList(@RequestBody @Validated AuthorityDoneListReq req) {
        Page<AuthorityBusiness> doneList = authorityBusinessService.getDoneList(req.getPageNumber(), req.getPageSize(), req.getKeyword());
        return JsonResponse.success(doneList);
    }

    /**
     * 我的已办详情
     *
     * @since 0.5.0
     */
    @ApiOperation("我的已办详情")
    @PostMapping("/doneDetail")
    public JsonResponse<Business> doneDetail(@RequestBody @Validated AuthorityDoneDetailReq req) {
        Business doneDetail = authorityBusinessService.getDetail(req.getId());
        if (doneDetail instanceof BaseDomain) {
            ((BaseDomain) doneDetail).getDynamicFields().put("pinataGateway", pinataGateway);
        }
        return JsonResponse.success(doneDetail);
    }

    /**
     * 我的提案，按组织名称模糊分页查询
     *
     * @since 0.5.0
     */
    @ApiOperation("我的提案列表")
    @PostMapping("/myProposalList")
    public JsonResponse<List<Proposal>> myProposalList(@RequestBody @Validated AuthorityProposalListReq req) {
        Page<Proposal> proposalList = proposalService.getMyProposalList(req.getPageNumber(), req.getPageSize(), req.getKeyword());
        return JsonResponse.page(proposalList);
    }

    /**
     * 我的提案详情
     *
     * @since 0.5.0
     */
    @ApiOperation("我的提案详情")
    @PostMapping("/proposalDetail")
    public JsonResponse<Proposal> proposalDetail(@RequestBody @Validated AuthorityProposalDetailReq req) {
        Proposal proposalDetail = proposalService.getProposalDetail(req.getId());
        proposalDetail.getDynamicFields().put("pinataGateway", pinataGateway);
        return JsonResponse.success(proposalDetail);
    }

    /**
     * 撤回提案
     *
     * @since 0.5.0
     */
    @ApiOperation("撤回提案")
    @PostMapping("/revokeProposal")
    public JsonResponse revokeProposal(@RequestBody @Validated AuthorityRevokeProposalReq req) {
        proposalService.revokeProposal(req.getId());
        return JsonResponse.success();
    }

}
