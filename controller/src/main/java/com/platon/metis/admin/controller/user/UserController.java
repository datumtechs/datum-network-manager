package com.platon.metis.admin.controller.user;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platon.metis.admin.common.util.WalletSignUtil;
import com.platon.metis.admin.constant.ControllerConstants;
import com.platon.metis.admin.dao.cache.LocalOrgCache;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.dao.entity.SysUser;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.SignMessageDto;
import com.platon.metis.admin.dto.req.UpdateLocalOrgReq;
import com.platon.metis.admin.dto.req.UserApplyOrgIdentityReq;
import com.platon.metis.admin.dto.req.UserLoginReq;
import com.platon.metis.admin.dto.req.UserUpdateAdminReq;
import com.platon.metis.admin.dto.resp.LoginNonceResp;
import com.platon.metis.admin.dto.resp.LoginResp;
import com.platon.metis.admin.enums.ResponseCodeEnum;
import com.platon.metis.admin.service.LocalOrgService;
import com.platon.metis.admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

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
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private LocalOrgService localOrgService;


    @GetMapping("getLoginNonce")
    @ApiOperation(value = "获取登录Nonce", notes = "获取登录Nonce")
    public JsonResponse<LoginNonceResp> getLoginNonce(HttpSession session) {
        String nonce = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //记录到session中，方便后面校验签名
        session.setAttribute(ControllerConstants.NONCE, nonce);
        LoginNonceResp resp = new LoginNonceResp();
        resp.setNonce(nonce);
        return JsonResponse.success(resp);
    }

    @PostMapping("login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public JsonResponse<LoginResp> login(HttpSession session, @RequestBody @Validated UserLoginReq req) {
        String hrpAddress = req.getHrpAddress();
        String hexAddress = req.getAddress();
        String message = req.getSignMessage();
        String sign = req.getSign();
        // 检查nonce
        checkNonceValidity(session,message);
        // 登录签名校验
        verifySign(hrpAddress, message, sign);
        //如果用户存在，则返回旧的用户信息，如果不存在则保存为新用户
        SysUser user = userService.getByAddress(hexAddress);
        if (user == null) {
            user = new SysUser();
            user.setUserName(hrpAddress);
            user.setAddress(hexAddress);
            user.setStatus(1);
            user.setIsAdmin(0);
            //保存数据
            userService.save(user);
            //查询新数据
            user = userService.getByAddress(hexAddress);
        }
        // 设置用户会话
        session.setAttribute(ControllerConstants.USER_ADDRESS, user.getAddress());//将登录信息存入session中

        LoginResp resp = new LoginResp();
        resp.setUserName(user.getUserName());
        resp.setAddress(user.getAddress());
        resp.setStatus(user.getStatus());
        resp.setIsAdmin(user.getIsAdmin());

        //TODO 此部分是否是只有管理员才需要这些信息
        LocalOrg localOrg = localOrgService.getLocalOrg();
        if (localOrg == null) {
            resp.setOrgInfoCompletionLevel(LoginResp.CompletionLevel.NEED_IDENTITY_ID.getLevel());
            resp.setConnectNetworkStatus(LocalOrg.Status.NOT_CONNECT_NET.getCode());
        } else {
            resp.setConnectNetworkStatus(localOrg.getStatus());
            resp.setOrgInfoCompletionLevel(LoginResp.CompletionLevel.DONE.getLevel());
            if (StringUtils.isBlank(localOrg.getIdentityId())) {
                resp.setOrgInfoCompletionLevel(LoginResp.CompletionLevel.NEED_IDENTITY_ID.getLevel());
                //resp.setConnectNetworkStatus(LocalOrg.Status.NOT_CONNECT_NET.getCode());
            } else if (StringUtils.isBlank(localOrg.getImageUrl()) || StringUtils.isBlank(localOrg.getProfile())) {
                resp.setOrgInfoCompletionLevel(LoginResp.CompletionLevel.NEED_PROFILE.getLevel());
            }
        }
        return JsonResponse.success(resp);
    }

    private void checkNonceValidity(HttpSession session, String signMessage) {
        String nonce = (String) session.getAttribute(ControllerConstants.NONCE);
        if (StrUtil.isBlank(nonce)) {
            //TODO 请重新获取nonce，nonce已过期
            throw new RuntimeException();
        }
        SignMessageDto signMessageDto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            signMessageDto = objectMapper.readValue(signMessage, SignMessageDto.class);
        } catch (Exception e) {
            throw new RuntimeException();
//            log.error(ErrorMsg.PARAM_ERROR.getMsg(), e);
//            throw new BusinessException(RespCodeEnum.PARAM_ERROR, ErrorMsg.PARAM_ERROR.getMsg());
        }
        String key = signMessageDto.getMessage().getKey();
        if (!StrUtil.equals(nonce, key)) {
            //TODO 请重新获取nonce，nonce不正确
            throw new RuntimeException();
        }
    }

    private void verifySign(String hrpAddress, String message, String sign) {
        boolean flg;
        try {
            String signMessage = StrUtil.replace(message, "\\\"", "\"");
            flg = WalletSignUtil.verifyTypedDataV4(signMessage, sign, hrpAddress);
        } catch (Exception e) {
            log.error("User login signature error,error msg:{}", e.getMessage(), e);
            //TODO
            throw new RuntimeException();
//            throw new BusinessException(RespCodeEnum.BIZ_FAILED, ErrorMsg.USER_SIGN_ERROR.getMsg());
        }
        if (!flg) {
            //TODO 校验签名错误，请重新签名
            log.error("User login signature error");
//            throw new BusinessException(RespCodeEnum.BIZ_FAILED, ErrorMsg.USER_SIGN_ERROR.getMsg());
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
     * @param request
     * @return
     */
    @ApiOperation(value = "替换管理员")
    @PostMapping("/updateAdmin")
    public JsonResponse updateAdmin(HttpServletRequest request, @RequestBody @Validated UserUpdateAdminReq req) {
        HttpSession session = request.getSession();
        String address = (String)session.getAttribute(ControllerConstants.USER_ADDRESS);
        SysUser user = userService.getByAddress(address);
        if(user.getIsAdmin() != 1){//不是管理员则提示错误
            return JsonResponse.fail(ResponseCodeEnum.FAIL, "当前用户不是管理员，替换失败");
        }
        user.setAddress(req.getNewAddress());
        userService.updateByAddress(user);
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
        LocalOrg localOrg = LocalOrgCache.getLocalOrgInfo();
        //只有退网之后才能修改组织名称
        if (localOrg.getStatus() == LocalOrg.Status.CONNECTED.getCode()) {
            return JsonResponse.fail(ResponseCodeEnum.FAIL, "组织未退网，不能修改组织信息");
        }
        if (StringUtils.equals(req.getName(), localOrg.getName())
                && StringUtils.equals(req.getImageUrl(), localOrg.getImageUrl())
                && StringUtils.equals(req.getProfile(), localOrg.getProfile())) {
            return JsonResponse.success();
        }
        localOrg.setName(req.getName());
        localOrg.setImageUrl(req.getImageUrl());
        localOrg.setProfile(req.getProfile());

        localOrgService.updateLocalOrg(localOrg);
        return JsonResponse.success();
    }


    /**
     * 登录后查询出当前组织信息
     *
     * @return
     */
    @ApiOperation(value = "查询出当前组织信息")
    @GetMapping("/findLocalOrgInfo")
    public JsonResponse<LocalOrg> findLocalOrgInfo() {
        LocalOrg localOrg = localOrgService.getLocalOrg();
        return JsonResponse.success(localOrg);
    }
}
