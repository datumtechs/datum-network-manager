package com.platon.rosettanet.admin.controller.system;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.platon.rosettanet.admin.constant.ControllerConstants;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.dto.req.LoginReq;
import com.platon.rosettanet.admin.dto.req.LogoutReq;
import com.platon.rosettanet.admin.dto.req.UserApplyOrgNameReq;
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

    @PostMapping("login")
    public JsonResponse<String> login(HttpServletRequest request,@Validated @RequestBody LoginReq req){
        HttpSession session = request.getSession(true);
        //校验验证码
        String codeInSession = (String)session.getAttribute(ControllerConstants.VERIFICATION_CODE);
        if(!checkVerificationCode(codeInSession,req.getCode())){
            JsonResponse.fail("验证码错误");
        }
        //登录校验
        if("admin".equals(req.getUserName())
                && "123456".equals(req.getPasswd())){
            String userId = "1";
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

    @PostMapping("logout")
    public JsonResponse logout(HttpServletRequest request, @Validated @RequestBody LogoutReq req){
        HttpSession session = request.getSession();
        if(session != null){//将session致为失效
            session.invalidate();
        }
        return JsonResponse.success();
    }

    @PostMapping("applyOrgName")
    public JsonResponse<String> applyOrgName(@RequestBody UserApplyOrgNameReq req){
        String orgId = userService.applyOrgName(req.getOrgName());
        if(StrUtil.isBlank(orgId)){
            return JsonResponse.fail("申请身份标识失败");
        }
        return JsonResponse.success(orgId);
    }

    @GetMapping("verificationCode")
    public JsonResponse<String> getVerificationCode(HttpServletRequest request){
        int code = RandomUtil.randomInt(1000, 9999);
        //放入session中，方便后面登录校验验证码
        HttpSession session = request.getSession(true);
        session.setAttribute(ControllerConstants.VERIFICATION_CODE,String.valueOf(code));
        return JsonResponse.success(String.valueOf(code));
    }
}
