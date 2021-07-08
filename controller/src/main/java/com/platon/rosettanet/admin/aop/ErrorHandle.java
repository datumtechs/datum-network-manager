package com.platon.rosettanet.admin.aop;

import com.platon.rosettanet.admin.common.exception.ApplicationException;
import com.platon.rosettanet.admin.dto.JsonResponse;
import com.platon.rosettanet.admin.enums.ResponseCodeEnum;
import com.platon.rosettanet.admin.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, String> errorMessage = new HashMap<>();
        for (FieldError error : fieldErrors) {
            errorMessage.put(error.getField(), error.getDefaultMessage());
        }
        return JsonResponse.fail(ResponseCodeEnum.FAIL, errorMessage.toString());
    }

    @ExceptionHandler(ServiceException.class)
    public JsonResponse handleServiceException(ServiceException exception){
        log.error("handleServiceException",exception);
        return JsonResponse.fail(exception.getErrorMsg());
    }

    @ExceptionHandler(ApplicationException.class)
    public JsonResponse handleApplicationException(ApplicationException exception){
        log.error("handleApplicationException",exception);
        return JsonResponse.fail(exception.getErrorMsg());
    }

    @ExceptionHandler(Exception.class)
    public JsonResponse handleException(Exception exception){
        log.error("handleException",exception);
        return JsonResponse.fail(ResponseCodeEnum.FAIL, exception.getMessage());
    }
}