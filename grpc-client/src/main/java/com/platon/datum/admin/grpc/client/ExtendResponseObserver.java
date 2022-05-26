package com.platon.datum.admin.grpc.client;

import io.grpc.stub.StreamObserver;

public interface ExtendResponseObserver<T> extends StreamObserver<T> {
    T getResponse();
}
