package com.platon.datum.admin.controller.user;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.common.util.WalletSignUtil;
import com.platon.datum.admin.constant.ControllerConstants;
import com.platon.datum.admin.dao.cache.OrgCache;
import com.platon.datum.admin.dao.entity.Org;
import com.platon.datum.admin.dao.entity.SysUser;
import com.platon.datum.admin.dto.JsonResponse;
import com.platon.datum.admin.dto.SignMessageDto;
import com.platon.datum.admin.dto.req.UpdateLocalOrgReq;
import com.platon.datum.admin.dto.req.UserApplyOrgIdentityReq;
import com.platon.datum.admin.dto.req.UserLoginReq;
import com.platon.datum.admin.dto.req.UserUpdateAdminReq;
import com.platon.datum.admin.dto.resp.LoginNonceResp;
import com.platon.datum.admin.dto.resp.LoginResp;
import com.platon.datum.admin.service.OrgService;
import com.platon.datum.admin.service.ResourceService;
import com.platon.datum.admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author liushuyu
 * @Date 2021/7/2 15:00
 * @Version
 * @Desc
 */

/**
 * 组织身份相关接口
 */

@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/api/v1/user/")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private OrgService orgService;

    @Resource
    private ResourceService resourceService;

    @GetMapping("/getLoginNonce")
    @ApiOperation(value = "获取登录Nonce", notes = "获取登录Nonce")
    public JsonResponse<LoginNonceResp> getLoginNonce(HttpSession session) {
        String nonce = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //记录到session中，方便后面校验签名
        session.setAttribute(ControllerConstants.NONCE, nonce);
        LoginNonceResp resp = new LoginNonceResp();
        resp.setNonce(nonce);
        return JsonResponse.success(resp);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public JsonResponse<LoginResp> login(HttpSession session, @RequestBody @Validated UserLoginReq req) {
        String hrpAddress = req.getHrpAddress();
        String hexAddress = req.getAddress();
        String message = req.getSignMessage();
        String sign = req.getSign();
        // 1.检查nonce
        checkNonceValidity(session, message);
        // 2.登录签名校验
        verifySign(hrpAddress, message, sign);
        //3.如果用户存在，则返回旧的用户信息，如果不存在则保存为新用户
        SysUser user = userService.getByAddress(hexAddress);
        if (user == null) {
            user = userService.createUser(hexAddress);
        }
        //4.设置用户会话
        session.setAttribute(ControllerConstants.USER_INFO, user);//将登录信息存入session中
        //5.获取用户权限,现在默认只有两个角色，一个是管理员，一个是非管理员，管理员默认有所有的权限
        Set<com.platon.datum.admin.dao.entity.Resource> resourceList = resourceService.getResourceListByUserId(user.getIsAdmin());
        //6.将用户权限存入session中
        List<String> urlList = resourceList.stream()
                .filter(resource -> resource.getType() == 1)
                .map(com.platon.datum.admin.dao.entity.Resource::getValue)
                .distinct()
                .collect(Collectors.toList());
        session.setAttribute(ControllerConstants.USER_URL_RESOURCE, urlList);//将登录信息存入session中

        LoginResp resp = new LoginResp();
        resp.setUserName(user.getUserName());
        resp.setAddress(user.getAddress());
        resp.setStatus(user.getStatus());
        resp.setIsAdmin(user.getIsAdmin());
        //7.将菜单和按钮的权限控制传递给前端
        List<com.platon.datum.admin.dao.entity.Resource> uiResourceList = resourceList.stream()
                .filter(resource -> resource.getType() == 2 || resource.getType() == 3)
                .distinct()
                .collect(Collectors.toList());
        resp.setResourceList(uiResourceList);

        Org org = orgService.getLocalOrg();
        if (org == null) {
            resp.setOrgInfoCompletionLevel(LoginResp.CompletionLevel.NEED_IDENTITY_ID.getLevel());
            resp.setConnectNetworkStatus(Org.Status.NOT_CONNECT_NET.getCode());
        } else {
            resp.setConnectNetworkStatus(org.getStatus());
            resp.setOrgInfoCompletionLevel(LoginResp.CompletionLevel.DONE.getLevel());
            if (StringUtils.isBlank(org.getIdentityId())) {
                resp.setOrgInfoCompletionLevel(LoginResp.CompletionLevel.NEED_IDENTITY_ID.getLevel());
                //resp.setConnectNetworkStatus(Org.Status.NOT_CONNECT_NET.getCode());
            } else if (StringUtils.isBlank(org.getImageUrl()) || StringUtils.isBlank(org.getProfile())) {
                resp.setOrgInfoCompletionLevel(LoginResp.CompletionLevel.NEED_PROFILE.getLevel());
            }
        }
        //登录成功，将nonce重置
        session.setAttribute(ControllerConstants.NONCE, null);
        return JsonResponse.success(resp);
    }

    private void checkNonceValidity(HttpSession session, String signMessage) {
        String nonce = (String) session.getAttribute(ControllerConstants.NONCE);
        if (StrUtil.isBlank(nonce)) {
            throw new BizException(Errors.NonceExpired);
        }
        SignMessageDto signMessageDto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            signMessageDto = objectMapper.readValue(signMessage, SignMessageDto.class);
        } catch (Exception e) {
            throw new BizException(Errors.SysException, e);
        }
        String key = signMessageDto.getMessage().getKey();
        if (!StrUtil.equals(nonce, key)) {
            throw new BizException(Errors.NonceIncorrect);
        }
    }

    private void verifySign(String hrpAddress, String message, String sign) {
        boolean flg;
        try {
            String signMessage = StrUtil.replace(message, "\\\"", "\"");
            flg = WalletSignUtil.verifyTypedDataV4(signMessage, sign, hrpAddress);
        } catch (Exception e) {
            throw new BizException(Errors.UserLoginSignError, e);
        }
        if (!flg) {
            throw new BizException(Errors.UserLoginSignError);
        }
    }

    /**
     * 退出登录状态
     *
     * @return
     */
    @ApiOperation(value = "退出登录状态")
    @PostMapping("/logout")
    public JsonResponse logout(HttpSession session) {
        if (session != null) {//将session致为失效
            session.invalidate();
        }
        return JsonResponse.success();
    }

    /**
     * 修改管理员钱包地址
     *
     * @return
     */
    @ApiOperation(value = "替换管理员")
    @PostMapping("/updateAdmin")
    public JsonResponse updateAdmin(HttpSession session, @RequestBody @Validated UserUpdateAdminReq req) {
        SysUser user = (SysUser) session.getAttribute(ControllerConstants.USER_INFO);
        //获取最新的用户信息
        user = userService.getByAddress(user.getAddress());
        if (user.getIsAdmin() != 1) {//不是管理员则提示错误
            return JsonResponse.fail(Errors.CurrentUserNotAdmin);
        }
        if (user.getAddress().toLowerCase().equals(req.getNewAddress().toLowerCase())) {//地址一样，则不修改
            return JsonResponse.fail(Errors.NewUserAlreadyAdmin);
        }
        userService.updateAdmin(user, req.getNewAddress());
        if (session != null) {//修改成功后，将session致为失效，让用户重新登录
            session.invalidate();
        }
        return JsonResponse.success();
    }

    /**
     * 申请身份标识，由系统生成后返回
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "申请身份标识")
    @PostMapping("/applyOrgIdentity")
    public JsonResponse<String> applyOrgIdentity(@RequestBody @Validated UserApplyOrgIdentityReq req) {
        String orgId = userService.applyOrgIdentity(req.getOrgName());
        return JsonResponse.success(orgId);
    }

    /**
     * 更新组织信息
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "更新组织信息（机构信息识别名称，头像链接，或者描述")
    @PostMapping("/updateLocalOrg")
    public JsonResponse<String> updateLocalOrg(@RequestBody UpdateLocalOrgReq req) {
        Org org = OrgCache.getLocalOrgInfo();
        //只有退网之后才能修改组织名称
        if (org.getStatus() == Org.Status.CONNECTED.getCode()) {
            return JsonResponse.fail(Errors.OrgInNetwork);
        }
        if (StringUtils.equals(req.getName(), org.getName())
                && StringUtils.equals(req.getImageUrl(), org.getImageUrl())
                && StringUtils.equals(req.getProfile(), org.getProfile())) {
            return JsonResponse.success();
        }
        org.setName(req.getName());
        org.setImageUrl(req.getImageUrl());
        org.setProfile(req.getProfile());

        orgService.updateLocalOrg(org);
        return JsonResponse.success();
    }


    /**
     * 登录后查询出当前组织信息
     *
     * @return
     */
    @ApiOperation(value = "查询出当前组织信息")
    @GetMapping("/findLocalOrgInfo")
    public JsonResponse<Org> findLocalOrgInfo() {
        Org org = orgService.getLocalOrg();
        return JsonResponse.success(org);
    }
}
