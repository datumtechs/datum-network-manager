package com.platon.datum.admin.aop;

import cn.hutool.json.JSONUtil;
import com.platon.datum.admin.dto.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * 拦截器,目前用于统计每个接口请求耗时和日志
 */
@Slf4j
@Aspect
@Component
public class ControllerInterceptor {

    @Pointcut("execution(public * com.platon.datum.admin.controller..*(..))")
    public void ControllerInterceptor() {
    }

    //对Controller下面的方法执行前进行切入，初始化开始时间
    @Before("ControllerInterceptor()")
    public void beforeMethod(JoinPoint joinPoint) {
        try {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            // 记录下请求内容
            log.info("request url: " + request.getRequestURL().toString());
            log.info("request method : " + request.getMethod());
            log.info("request class : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());


            if (ArrayUtils.isNotEmpty(joinPoint.getArgs())) {
                Object[] args = joinPoint.getArgs();
                for (int i = 0; i < args.length; i++) {
                    Object parameter = args[i];
                    if (parameter == null) {
                        return;
                    }
                    if (parameter.getClass().isPrimitive()) {
                        log.info("request param-{}: {}", i, parameter);
                    } else if (parameter instanceof ServletResponse) {
                        log.info("request param-{}: {}", i, "ServletResponse");
                    } else if (parameter instanceof ServletRequest) {
                        log.info("request param-{}: {}", i, "ServletRequest");
                    } else if (parameter instanceof MultipartFile) {
                        log.info("request param-{}: {}", i, "MultipartFile");
                    } else {
                        log.info("request param-{}: {}", i, JSONUtil.toJsonStr(parameter));
                    }
                }
            }
        } catch (Exception exception) {
            //undo不应因记日志而影响系统运行
            log.warn("拦截请求日志失败", exception);
        }

    }

    //对Controller下面的方法执行后进行切入，统计方法执行的次数和耗时情况
    @AfterReturning(returning = "ret", pointcut = "ControllerInterceptor()")
    public void afterMethod(JoinPoint joinPoint, Object ret) {
        try {
            log.info("response class : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            if (ret instanceof JsonResponse) {
                log.info("response value : " + JSONUtil.toJsonStr(ret));
            }
            if (ret instanceof byte[]) {
                log.info("response value : " + new String((byte[]) ret));
            }
        } catch (Exception exception) {
            //undo不应因记日志而影响系统运行
            log.warn("拦截响应日志失败", exception);
        }
    }
}
