package com.platon.metis.admin.aop;

import cn.hutool.core.util.StrUtil;
import com.platon.metis.admin.common.exception.ApplicationException;
import com.platon.metis.admin.dto.JsonResponse;
import com.platon.metis.admin.enums.ResponseCodeEnum;
import com.platon.metis.admin.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

/**
 * 全局异常的处理
 */
@RestControllerAdvice
@ResponseStatus(code = HttpStatus.OK)
@Slf4j
public class ErrorHandle {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        log.error("handleMethodArgumentNotValidException",exception);
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        StringJoiner errorMessage = new StringJoiner(",");
        for (FieldError error : fieldErrors) {
            errorMessage.add(StrUtil.format("{}={}",error.getField(), error.getDefaultMessage()));
        }
        return JsonResponse.fail(ResponseCodeEnum.FAIL, "method argument invalid");
    }

    @ExceptionHandler(SQLException.class)
    public JsonResponse handleException(SQLException exception){
        return JsonResponse.fail(ResponseCodeEnum.FAIL, "SQL error");
    }

    @ExceptionHandler(ServiceException.class)
    public JsonResponse handleServiceException(ServiceException exception){
        log.error("handleServiceException",exception);
        return JsonResponse.fail(exception.getErrorMsg());
    }

    @ExceptionHandler(ApplicationException.class)
    public JsonResponse handleApplicationException(ApplicationException exception){
        log.error("handleApplicationException",exception);
        ApplicationException.ApplicationErrorEnum errorCode = exception.getErrorCode();
        switch (errorCode){
            case CARRIER_INFO_NOT_CONFIGURED:
                return JsonResponse.fail(ResponseCodeEnum.CARRIER_INFO_NOT_CONFIGURED);
            case IDENTITY_ID_MISSING:
                return JsonResponse.fail(ResponseCodeEnum.IDENTITY_ID_MISSING);
            default:
                return JsonResponse.fail(exception.getErrorMsg());
        }
    }

    @ExceptionHandler(Exception.class)
    public JsonResponse handleException(Exception exception){
        log.error("handleException",exception);
        return JsonResponse.fail(ResponseCodeEnum.FAIL, "System internal error");
    }
}