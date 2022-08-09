package com.platon.datum.admin.grpc.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;


public class ExceptionInterceptor implements ServerInterceptor {
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call, Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {
        ServerCall.Listener<ReqT> reqTListener = next.startCall(call, headers);
        return new ExceptionListener(reqTListener, call);
    }
}

class ExceptionListener <ReqT, RespT> extends ServerCall.Listener {
    private ServerCall.Listener<ReqT> delegate;
    private ServerCall<ReqT, RespT> call;

    public ExceptionListener(ServerCall.Listener<ReqT> delegate, ServerCall<ReqT, RespT> call) {
        this.delegate = delegate;
        this.call = call;
    }

    public void onHalfClose() {
        try {
            this.delegate.onHalfClose();
        } catch (Throwable t) {
           /* // 统一处理异常
            ExtendedStatusRuntimeException exception = StatusEnum.fromThrowable(t);
            // 调用 call.close() 发送 StatusEnum 和 metadata
            // 这个方式和 onError()本质是一样的
            call.close(exception.getStatus(), exception.getTrailers());*/
        }
    }
}

