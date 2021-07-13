package com.platon.rosettanet.admin.controller.system;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.platon.rosettanet.admin.constant.ControllerConstants;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.LoginReq;
import com.platon.rosettanet.admin.dto.req.UserApplyOrgIdentityReq;
import com.platon.rosettanet.admin.service.UserService;
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

@RestController
@RequestMapping("/api/v1/system/user/")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登陆接口，用于登陆系统，获取会话
     * @param request
     * @param req
     * @return
     */
    @PostMapping("login")
    public JsonResponse<String> login(HttpServletRequest request,@Validated @RequestBody LoginReq req){
        HttpSession session = request.getSession(true);
        //校验验证码
        String codeInSession = (String)session.getAttribute(ControllerConstants.VERIFICATION_CODE);
        //TODO 现阶段可以不传验证码，如果传了，则必须传对的，否则报错
        if(!checkVerificationCode(codeInSession,req.getCode())){
            JsonResponse.fail("验证码错误");
        }
        //登录校验 TODO 密码进行加盐+hash操作
        String userId = userService.login(req.getUserName(),req.getPasswd());
        if(StrUtil.isNotBlank(userId)){
            session.setAttribute(ControllerConstants.USER_ID,userId);//将登录信息存入session中
            return JsonResponse.success(userId);
        }
        return JsonResponse.fail("用户名或密码错误");
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
    @PostMapping("logout")
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
    @PostMapping("applyOrgIdentity")
    public JsonResponse<String> applyOrgIdentity(@RequestBody @Validated UserApplyOrgIdentityReq req){
        String orgId = userService.applyOrgIdentity(req.getOrgName());
        if(StrUtil.isBlank(orgId)){
            return JsonResponse.fail("申请身份标识失败");
        }
        return JsonResponse.success(orgId);
    }

    /**
     * 获取验证码
     * @param request
     * @return
     */
    @GetMapping("verificationCode")
    public JsonResponse<String> getVerificationCode(HttpServletRequest request){
        int code = RandomUtil.randomInt(1000, 9999);
        //放入session中，方便后面登录校验验证码
        HttpSession session = request.getSession(true);
        session.setAttribute(ControllerConstants.VERIFICATION_CODE,String.valueOf(code));
        return JsonResponse.success(String.valueOf(code));
    }
}
