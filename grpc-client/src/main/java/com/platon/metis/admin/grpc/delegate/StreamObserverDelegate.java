package com.platon.metis.admin.grpc.delegate;

import com.google.protobuf.Message;
import io.grpc.stub.StreamObserver;


public class StreamObserverDelegate<ReqT extends Message, RespT extends Message> implements StreamObserver<RespT> {
    @Override
    public void onNext(RespT respT) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {

    }

    /*private static final Logger logger = LoggerFactory.getLogger(StreamObserverDelegate.class);

    private StreamObserver<RespT> originResponseObserver;

    public StreamObserverDelegate(StreamObserver<RespT> originResponseObserver) {
        Assert.notNull(originResponseObserver, "originResponseObserver must not null!");
        this.originResponseObserver = originResponseObserver;
    }

    @Override
    public void onNext(RespT value) {
        this.originResponseObserver.onNext(value);
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof ApplicationException) {
            // 业务异常，返回错误码和默认文案到客户端
            BizException bizException = (BizException) t;
            Metadata trailers = new Metadata();
            ErrorInfo errorInfo = ErrorInfo.newBuilder()
                    .setErrorCode(bizException.getErrorCode())
                    .setMessage(bizException.getMessage())
                    .build();
            Metadata.Key<ErrorInfo> ERROR_INFO_TRAILER_KEY = ProtoUtils.keyForProto(errorInfo);
            trailers.put(ERROR_INFO_TRAILER_KEY, errorInfo);
            this.originResponseObserver.onError(Status.UNKNOWN
                    .withCause(bizException)
                    .asRuntimeException(trailers));
        } else {
            // 非业务异常，返回异常详情到客户端。
            this.originResponseObserver.onError(Status.UNKNOWN
                    // 这里就是我们的自定义异常信息
                    .withDescription(t.getMessage())
                    .withCause(t)
                    .asRuntimeException());
        }
        // 抛出异常让上层业务感知(比如事务回滚等可能要用到)
        throw new RuntimeException(t);
    }

    @Override
    public void onCompleted() {
        if (originResponseObserver != null) {
            originResponseObserver.onCompleted();
        }
    }

    *//**
     * 执行业务(自动处理异常)
     *
     * @author masaiqi
     * @date 2021/4/12 18:11
     *//*
    public RespT executeWithException(Function<ReqT, RespT> function, ReqT request) {
        RespT response = null;
        try {
            response = function.apply(request);
        } catch (Throwable e) {
            this.onError(e);
        }
        return response;
    }

    *//**
     * 执行业务(自动处理异常)
     *
     * @author masaiqi
     * @date 2021/4/12 18:11
     *//*
    public RespT executeWithException(Supplier<RespT> supplier) {
        RespT response = null;
        try {
            response = supplier.get();
        } catch (Throwable e) {
            this.onError(e);
        }
        return response;
    }*/
}

