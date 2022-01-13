package com.platon.metis.admin.grpc.interceptor;

import io.grpc.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Configuration
public class UploadFileTimeoutInterceptor implements ClientInterceptor {
    @Value("${grpc-client-upload-file-timeout}")
    private long timeout; //seconds

    public long getTimeout(){
        return this.timeout;
    }

    @Override
    public <ReqT,RespT> ClientCall<ReqT,RespT> interceptCall(
            MethodDescriptor<ReqT,RespT> method, CallOptions callOptions, Channel next) {

        callOptions = callOptions.withDeadlineAfter(timeout, TimeUnit.SECONDS);
        return next.newCall(method, callOptions);
    }
}
