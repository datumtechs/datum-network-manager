package com.platon.metis.admin.grpc.client;

import io.grpc.stub.StreamObserver;

public interface ExtendResponseObserver<T> extends StreamObserver<T> {
    T getResponse();
}
