package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ## 元数据 相关接口
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: carrier/api/metadata_rpc_api.proto")
public final class MetaDataServiceGrpc {

  private MetaDataServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcapi.MetaDataService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailRequest,
      com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailResponse> getGetMetaDataDetailMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMetaDataDetail",
      requestType = com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailRequest,
      com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailResponse> getGetMetaDataDetailMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailRequest, com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailResponse> getGetMetaDataDetailMethod;
    if ((getGetMetaDataDetailMethod = MetaDataServiceGrpc.getGetMetaDataDetailMethod) == null) {
      synchronized (MetaDataServiceGrpc.class) {
        if ((getGetMetaDataDetailMethod = MetaDataServiceGrpc.getGetMetaDataDetailMethod) == null) {
          MetaDataServiceGrpc.getGetMetaDataDetailMethod = getGetMetaDataDetailMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailRequest, com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMetaDataDetail"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MetaDataServiceMethodDescriptorSupplier("GetMetaDataDetail"))
              .build();
        }
      }
    }
    return getGetMetaDataDetailMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse> getGetMetaDataDetailListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMetaDataDetailList",
      requestType = com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams.class,
      responseType = com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse> getGetMetaDataDetailListMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse> getGetMetaDataDetailListMethod;
    if ((getGetMetaDataDetailListMethod = MetaDataServiceGrpc.getGetMetaDataDetailListMethod) == null) {
      synchronized (MetaDataServiceGrpc.class) {
        if ((getGetMetaDataDetailListMethod = MetaDataServiceGrpc.getGetMetaDataDetailListMethod) == null) {
          MetaDataServiceGrpc.getGetMetaDataDetailListMethod = getGetMetaDataDetailListMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMetaDataDetailList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MetaDataServiceMethodDescriptorSupplier("GetMetaDataDetailList"))
              .build();
        }
      }
    }
    return getGetMetaDataDetailListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListByOwnerRequest,
      com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse> getGetMetaDataDetailListByOwnerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMetaDataDetailListByOwner",
      requestType = com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListByOwnerRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListByOwnerRequest,
      com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse> getGetMetaDataDetailListByOwnerMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListByOwnerRequest, com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse> getGetMetaDataDetailListByOwnerMethod;
    if ((getGetMetaDataDetailListByOwnerMethod = MetaDataServiceGrpc.getGetMetaDataDetailListByOwnerMethod) == null) {
      synchronized (MetaDataServiceGrpc.class) {
        if ((getGetMetaDataDetailListByOwnerMethod = MetaDataServiceGrpc.getGetMetaDataDetailListByOwnerMethod) == null) {
          MetaDataServiceGrpc.getGetMetaDataDetailListByOwnerMethod = getGetMetaDataDetailListByOwnerMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListByOwnerRequest, com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMetaDataDetailListByOwner"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListByOwnerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MetaDataServiceMethodDescriptorSupplier("GetMetaDataDetailListByOwner"))
              .build();
        }
      }
    }
    return getGetMetaDataDetailListByOwnerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataRequest,
      com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataResponse> getPublishMetaDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PublishMetaData",
      requestType = com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataRequest,
      com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataResponse> getPublishMetaDataMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataRequest, com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataResponse> getPublishMetaDataMethod;
    if ((getPublishMetaDataMethod = MetaDataServiceGrpc.getPublishMetaDataMethod) == null) {
      synchronized (MetaDataServiceGrpc.class) {
        if ((getPublishMetaDataMethod = MetaDataServiceGrpc.getPublishMetaDataMethod) == null) {
          MetaDataServiceGrpc.getPublishMetaDataMethod = getPublishMetaDataMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataRequest, com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PublishMetaData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MetaDataServiceMethodDescriptorSupplier("PublishMetaData"))
              .build();
        }
      }
    }
    return getPublishMetaDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.RevokeMetaDataRequest,
      com.platon.rosettanet.admin.grpc.CommonMessage.SimpleResponseCode> getRevokeMetaDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RevokeMetaData",
      requestType = com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.RevokeMetaDataRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.CommonMessage.SimpleResponseCode.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.RevokeMetaDataRequest,
      com.platon.rosettanet.admin.grpc.CommonMessage.SimpleResponseCode> getRevokeMetaDataMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.RevokeMetaDataRequest, com.platon.rosettanet.admin.grpc.CommonMessage.SimpleResponseCode> getRevokeMetaDataMethod;
    if ((getRevokeMetaDataMethod = MetaDataServiceGrpc.getRevokeMetaDataMethod) == null) {
      synchronized (MetaDataServiceGrpc.class) {
        if ((getRevokeMetaDataMethod = MetaDataServiceGrpc.getRevokeMetaDataMethod) == null) {
          MetaDataServiceGrpc.getRevokeMetaDataMethod = getRevokeMetaDataMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.RevokeMetaDataRequest, com.platon.rosettanet.admin.grpc.CommonMessage.SimpleResponseCode>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RevokeMetaData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.RevokeMetaDataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.CommonMessage.SimpleResponseCode.getDefaultInstance()))
              .setSchemaDescriptor(new MetaDataServiceMethodDescriptorSupplier("RevokeMetaData"))
              .build();
        }
      }
    }
    return getRevokeMetaDataMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MetaDataServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MetaDataServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MetaDataServiceStub>() {
        @java.lang.Override
        public MetaDataServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MetaDataServiceStub(channel, callOptions);
        }
      };
    return MetaDataServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MetaDataServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MetaDataServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MetaDataServiceBlockingStub>() {
        @java.lang.Override
        public MetaDataServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MetaDataServiceBlockingStub(channel, callOptions);
        }
      };
    return MetaDataServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MetaDataServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MetaDataServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MetaDataServiceFutureStub>() {
        @java.lang.Override
        public MetaDataServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MetaDataServiceFutureStub(channel, callOptions);
        }
      };
    return MetaDataServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ## 元数据 相关接口
   * </pre>
   */
  public static abstract class MetaDataServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 查看单个元数据详情 (包含 列字段描述)
     * </pre>
     */
    public void getMetaDataDetail(com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMetaDataDetailMethod(), responseObserver);
    }

    /**
     */
    public void getMetaDataDetailList(com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMetaDataDetailListMethod(), responseObserver);
    }

    /**
     */
    public void getMetaDataDetailListByOwner(com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListByOwnerRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMetaDataDetailListByOwnerMethod(), responseObserver);
    }

    /**
     * <pre>
     * 发布元数据  (新增和编辑 都是发布新的元数据) &lt;底层根据 原始数据Id -- OriginId 来关联 新的MetaDataId&gt;
     * </pre>
     */
    public void publishMetaData(com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPublishMetaDataMethod(), responseObserver);
    }

    /**
     * <pre>
     * 撤销元数据 (从底层网络撤销)
     * </pre>
     */
    public void revokeMetaData(com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.RevokeMetaDataRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.CommonMessage.SimpleResponseCode> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRevokeMetaDataMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetMetaDataDetailMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailRequest,
                com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailResponse>(
                  this, METHODID_GET_META_DATA_DETAIL)))
          .addMethod(
            getGetMetaDataDetailListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams,
                com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse>(
                  this, METHODID_GET_META_DATA_DETAIL_LIST)))
          .addMethod(
            getGetMetaDataDetailListByOwnerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListByOwnerRequest,
                com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse>(
                  this, METHODID_GET_META_DATA_DETAIL_LIST_BY_OWNER)))
          .addMethod(
            getPublishMetaDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataRequest,
                com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataResponse>(
                  this, METHODID_PUBLISH_META_DATA)))
          .addMethod(
            getRevokeMetaDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.RevokeMetaDataRequest,
                com.platon.rosettanet.admin.grpc.CommonMessage.SimpleResponseCode>(
                  this, METHODID_REVOKE_META_DATA)))
          .build();
    }
  }

  /**
   * <pre>
   * ## 元数据 相关接口
   * </pre>
   */
  public static final class MetaDataServiceStub extends io.grpc.stub.AbstractAsyncStub<MetaDataServiceStub> {
    private MetaDataServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MetaDataServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MetaDataServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查看单个元数据详情 (包含 列字段描述)
     * </pre>
     */
    public void getMetaDataDetail(com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMetaDataDetailMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getMetaDataDetailList(com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMetaDataDetailListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getMetaDataDetailListByOwner(com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListByOwnerRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMetaDataDetailListByOwnerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 发布元数据  (新增和编辑 都是发布新的元数据) &lt;底层根据 原始数据Id -- OriginId 来关联 新的MetaDataId&gt;
     * </pre>
     */
    public void publishMetaData(com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPublishMetaDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 撤销元数据 (从底层网络撤销)
     * </pre>
     */
    public void revokeMetaData(com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.RevokeMetaDataRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.CommonMessage.SimpleResponseCode> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRevokeMetaDataMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * ## 元数据 相关接口
   * </pre>
   */
  public static final class MetaDataServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<MetaDataServiceBlockingStub> {
    private MetaDataServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MetaDataServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MetaDataServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查看单个元数据详情 (包含 列字段描述)
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailResponse getMetaDataDetail(com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMetaDataDetailMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse getMetaDataDetailList(com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMetaDataDetailListMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse getMetaDataDetailListByOwner(com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListByOwnerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMetaDataDetailListByOwnerMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 发布元数据  (新增和编辑 都是发布新的元数据) &lt;底层根据 原始数据Id -- OriginId 来关联 新的MetaDataId&gt;
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataResponse publishMetaData(com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPublishMetaDataMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 撤销元数据 (从底层网络撤销)
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.CommonMessage.SimpleResponseCode revokeMetaData(com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.RevokeMetaDataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRevokeMetaDataMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * ## 元数据 相关接口
   * </pre>
   */
  public static final class MetaDataServiceFutureStub extends io.grpc.stub.AbstractFutureStub<MetaDataServiceFutureStub> {
    private MetaDataServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MetaDataServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MetaDataServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查看单个元数据详情 (包含 列字段描述)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailResponse> getMetaDataDetail(
        com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMetaDataDetailMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse> getMetaDataDetailList(
        com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMetaDataDetailListMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse> getMetaDataDetailListByOwner(
        com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListByOwnerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMetaDataDetailListByOwnerMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 发布元数据  (新增和编辑 都是发布新的元数据) &lt;底层根据 原始数据Id -- OriginId 来关联 新的MetaDataId&gt;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataResponse> publishMetaData(
        com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPublishMetaDataMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 撤销元数据 (从底层网络撤销)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.CommonMessage.SimpleResponseCode> revokeMetaData(
        com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.RevokeMetaDataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRevokeMetaDataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_META_DATA_DETAIL = 0;
  private static final int METHODID_GET_META_DATA_DETAIL_LIST = 1;
  private static final int METHODID_GET_META_DATA_DETAIL_LIST_BY_OWNER = 2;
  private static final int METHODID_PUBLISH_META_DATA = 3;
  private static final int METHODID_REVOKE_META_DATA = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MetaDataServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MetaDataServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_META_DATA_DETAIL:
          serviceImpl.getMetaDataDetail((com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailResponse>) responseObserver);
          break;
        case METHODID_GET_META_DATA_DETAIL_LIST:
          serviceImpl.getMetaDataDetailList((com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse>) responseObserver);
          break;
        case METHODID_GET_META_DATA_DETAIL_LIST_BY_OWNER:
          serviceImpl.getMetaDataDetailListByOwner((com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListByOwnerRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.GetMetaDataDetailListResponse>) responseObserver);
          break;
        case METHODID_PUBLISH_META_DATA:
          serviceImpl.publishMetaData((com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.PublishMetaDataResponse>) responseObserver);
          break;
        case METHODID_REVOKE_META_DATA:
          serviceImpl.revokeMetaData((com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.RevokeMetaDataRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.CommonMessage.SimpleResponseCode>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class MetaDataServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MetaDataServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.platon.rosettanet.admin.grpc.MetaDataRpcMessage.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MetaDataService");
    }
  }

  private static final class MetaDataServiceFileDescriptorSupplier
      extends MetaDataServiceBaseDescriptorSupplier {
    MetaDataServiceFileDescriptorSupplier() {}
  }

  private static final class MetaDataServiceMethodDescriptorSupplier
      extends MetaDataServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MetaDataServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MetaDataServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MetaDataServiceFileDescriptorSupplier())
              .addMethod(getGetMetaDataDetailMethod())
              .addMethod(getGetMetaDataDetailListMethod())
              .addMethod(getGetMetaDataDetailListByOwnerMethod())
              .addMethod(getPublishMetaDataMethod())
              .addMethod(getRevokeMetaDataMethod())
              .build();
        }
      }
    }
    return result;
  }
}
