package com.platon.metis.admin.controller.user;

import com.platon.metis.admin.common.exception.VerificationCodeError;
import com.platon.metis.admin.constant.ControllerConstants;
import com.platon.metis.admin.dao.entity.LocalOrg;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.dto.req.UserApplyOrgIdentityReq;
import com.platon.metis.admin.dto.req.UserLoginReq;
import com.platon.metis.admin.dto.req.index.OrgNameReq;
import com.platon.metis.admin.service.LocalOrgService;
import com.platon.metis.admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    /**
     * 登陆接口，用于登陆系统，获取会话
     * @param request
     * @param req
     * @return
     */
    @ApiOperation(value = "登陆")
    @PostMapping("/login")
    public JsonResponse<String> login(HttpServletRequest request,@Validated @RequestBody UserLoginReq req){
        HttpSession session = request.getSession(true);
        //校验验证码
        String codeInSession = (String)session.getAttribute(ControllerConstants.VERIFICATION_CODE);
        //TODO 现阶段可以不传验证码，如果传了，则必须传对的，否则报错
        if(!checkVerificationCode(codeInSession,req.getCode())){
            throw new VerificationCodeError();
        }
        //登录校验 TODO 密码进行加盐+hash操作
        String userId = userService.login(req.getUserName(),req.getPasswd());
        return JsonResponse.success(userId);

    }

    /**
     * 校验验证码
     * @param codeInSession 之前请求验证码接口后存在session中的验证码
     * @param code 用户填的验证码
     * @return
     */
    private boolean checkVerificationCode(String codeInSession,String code){
        if(codeInSession !=null && !codeInSession.equals(code)){
            return false;
        }
        return true;
    }

    /**
     * 退出登录状态
     * @param request
     * @return
     */
    @ApiOperation(value = "退出登录状态")
    @PostMapping("/logout")
    public JsonResponse logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session != null){//将session致为失效
            session.invalidate();
        }
        return JsonResponse.success();
    }

    /**
     * 申请身份标识，由系统生成后返回
     * @param req
     * @return
     */
    @ApiOperation(value = "申请身份标识")
    @PostMapping("/applyOrgIdentity")
    public JsonResponse<String> applyOrgIdentity(@RequestBody @Validated UserApplyOrgIdentityReq req){
        String orgId = userService.applyOrgIdentity(req.getOrgName());
        return JsonResponse.success(orgId);

    }

    /**
     * 获取验证码
     * @param request
     * @return
     */
    @ApiOperation(value = "获取验证码")
    @GetMapping("/verificationCode")
    public JsonResponse<String> getVerificationCode(HttpServletRequest request){
        int code = RandomUtils.nextInt(1000, 9999);
        //放入session中，方便后面登录校验验证码
        HttpSession session = request.getSession(true);
        session.setAttribute(ControllerConstants.VERIFICATION_CODE,String.valueOf(code));
        return JsonResponse.success(String.valueOf(code));
    }

    @ApiOperation(value = "修改机构识别名称", response = JsonResponse.class)
    @GetMapping("/updateOrgName")
    public JsonResponse updateOrgName(@RequestBody @Validated OrgNameReq orgNameReq){
        userService.updateOrgName(orgNameReq.getIdentityId(), orgNameReq.getIdentityName());
        return JsonResponse.success();
    }


    @Resource
    private LocalOrgService localOrgService;

    /**
     * 登录后查询出当前组织信息
     * @return
     */
    @ApiOperation(value = "查询出当前组织信息")
    @GetMapping("/findLocalOrgInfo")
    public JsonResponse<LocalOrg> findLocalOrgInfo(){
        LocalOrg localOrg = localOrgService.getLocalOrg();
        return JsonResponse.success(localOrg);
    }
}
