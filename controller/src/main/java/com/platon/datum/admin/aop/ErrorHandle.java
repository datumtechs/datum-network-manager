package com.platon.datum.admin.aop;

import com.platon.datum.admin.common.exception.BizException;
import com.platon.datum.admin.common.exception.Errors;
import com.platon.datum.admin.dto.JsonResponse;
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
    public JsonResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("handleMethodArgumentNotValidException", exception);
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        StringJoiner errorMessage = new StringJoiner(",");
        for (FieldError error : fieldErrors) {

            errorMessage.add(error.getField() + "_" + error.getDefaultMessage());
        }
        return JsonResponse.fail(Errors.MethodParamInvalid);
    }

    @ExceptionHandler(SQLException.class)
    public JsonResponse handleSQLException(SQLException exception) {
        log.error("handleSQLException", exception);
        return JsonResponse.fail(Errors.SQLERROR);
    }


    @ExceptionHandler(BizException.class)
    public JsonResponse handleBizException(BizException exception) {
        log.error("handleBizException", exception);
        return JsonResponse.fail(exception);
    }

    @ExceptionHandler(Exception.class)
    public JsonResponse handleException(Exception exception) {
        log.error("handleException", exception);
        return JsonResponse.fail(Errors.SysException);
    }
}