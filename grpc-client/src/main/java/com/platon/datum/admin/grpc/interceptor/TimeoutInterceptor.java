package com.platon.datum.admin.grpc.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@Configuration
@Slf4j
public class TimeoutInterceptor implements ClientInterceptor {
    @Value("${grpc-client-timeout:30}")
    private long timeout; //seconds

    @Override
    public <ReqT,RespT> ClientCall<ReqT,RespT> interceptCall(
        MethodDescriptor<ReqT,RespT> method, CallOptions callOptions, Channel next) {

        callOptions = callOptions.withDeadlineAfter(timeout, TimeUnit.SECONDS);
        return next.newCall(method, callOptions);
    }
}