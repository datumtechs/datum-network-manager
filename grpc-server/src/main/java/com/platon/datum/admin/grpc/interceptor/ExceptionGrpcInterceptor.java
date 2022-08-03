package com.platon.datum.admin.grpc.interceptor;


import com.platon.datum.admin.common.exception.BizException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.apache.commons.lang3.StringUtils;


@Slf4j
@GrpcAdvice
public class ExceptionGrpcInterceptor {
    /*@GrpcExceptionHandler(BizException.class)
    public StatusException handleBizException(BizException e){
        Status status = Status.INTERNAL.withDescription(e.getMessage()).withCause(e);
        return status.asException();
    }

    @GrpcExceptionHandler(Exception.class)
    public Status handleException(Exception e){
        return Status.INTERNAL.withDescription(e.getMessage()).withCause(e);
    }*/

    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleException(Exception ex) {
        log.error("error:", ex);

        int code =  0;
        String message = "";
        if (ex instanceof BizException) {
            BizException bizEx = (BizException)ex;
            //code = bizEx.getErrorCode();
            code = Status.INTERNAL.getCode().value();
            message = bizEx.getMessage();
        }else{
            code = Status.INTERNAL.getCode().value();
            message = ex.getMessage();
        }

        com.google.rpc.Status status =
                com.google.rpc.Status.newBuilder()
                        .setCode(code)
                        .setMessage(StringUtils.trimToEmpty(message))
                         .build();

        return StatusProto.toStatusRuntimeException(status);
    }
}
