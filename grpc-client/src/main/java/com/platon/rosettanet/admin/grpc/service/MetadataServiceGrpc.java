package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ## 元数据 相关接口
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: lib/api/metadata_rpc_api.proto")
public final class MetadataServiceGrpc {

  private MetadataServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcapi.MetadataService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailRequest,
      com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailResponse> getGetMetadataDetailMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMetadataDetail",
      requestType = com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailRequest,
      com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailResponse> getGetMetadataDetailMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailRequest, com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailResponse> getGetMetadataDetailMethod;
    if ((getGetMetadataDetailMethod = MetadataServiceGrpc.getGetMetadataDetailMethod) == null) {
      synchronized (MetadataServiceGrpc.class) {
        if ((getGetMetadataDetailMethod = MetadataServiceGrpc.getGetMetadataDetailMethod) == null) {
          MetadataServiceGrpc.getGetMetadataDetailMethod = getGetMetadataDetailMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailRequest, com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMetadataDetail"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MetadataServiceMethodDescriptorSupplier("GetMetadataDetail"))
              .build();
        }
      }
    }
    return getGetMetadataDetailMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse> getGetMetadataDetailListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMetadataDetailList",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse> getGetMetadataDetailListMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse> getGetMetadataDetailListMethod;
    if ((getGetMetadataDetailListMethod = MetadataServiceGrpc.getGetMetadataDetailListMethod) == null) {
      synchronized (MetadataServiceGrpc.class) {
        if ((getGetMetadataDetailListMethod = MetadataServiceGrpc.getGetMetadataDetailListMethod) == null) {
          MetadataServiceGrpc.getGetMetadataDetailListMethod = getGetMetadataDetailListMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMetadataDetailList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MetadataServiceMethodDescriptorSupplier("GetMetadataDetailList"))
              .build();
        }
      }
    }
    return getGetMetadataDetailListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListByOwnerRequest,
      com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse> getGetMetadataDetailListByOwnerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMetadataDetailListByOwner",
      requestType = com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListByOwnerRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListByOwnerRequest,
      com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse> getGetMetadataDetailListByOwnerMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListByOwnerRequest, com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse> getGetMetadataDetailListByOwnerMethod;
    if ((getGetMetadataDetailListByOwnerMethod = MetadataServiceGrpc.getGetMetadataDetailListByOwnerMethod) == null) {
      synchronized (MetadataServiceGrpc.class) {
        if ((getGetMetadataDetailListByOwnerMethod = MetadataServiceGrpc.getGetMetadataDetailListByOwnerMethod) == null) {
          MetadataServiceGrpc.getGetMetadataDetailListByOwnerMethod = getGetMetadataDetailListByOwnerMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListByOwnerRequest, com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMetadataDetailListByOwner"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListByOwnerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MetadataServiceMethodDescriptorSupplier("GetMetadataDetailListByOwner"))
              .build();
        }
      }
    }
    return getGetMetadataDetailListByOwnerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataRequest,
      com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataResponse> getPublishMetadataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PublishMetadata",
      requestType = com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataRequest,
      com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataResponse> getPublishMetadataMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataRequest, com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataResponse> getPublishMetadataMethod;
    if ((getPublishMetadataMethod = MetadataServiceGrpc.getPublishMetadataMethod) == null) {
      synchronized (MetadataServiceGrpc.class) {
        if ((getPublishMetadataMethod = MetadataServiceGrpc.getPublishMetadataMethod) == null) {
          MetadataServiceGrpc.getPublishMetadataMethod = getPublishMetadataMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataRequest, com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PublishMetadata"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MetadataServiceMethodDescriptorSupplier("PublishMetadata"))
              .build();
        }
      }
    }
    return getPublishMetadataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.RevokeMetadataRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getRevokeMetadataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RevokeMetadata",
      requestType = com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.RevokeMetadataRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.SimpleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.RevokeMetadataRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getRevokeMetadataMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.RevokeMetadataRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse> getRevokeMetadataMethod;
    if ((getRevokeMetadataMethod = MetadataServiceGrpc.getRevokeMetadataMethod) == null) {
      synchronized (MetadataServiceGrpc.class) {
        if ((getRevokeMetadataMethod = MetadataServiceGrpc.getRevokeMetadataMethod) == null) {
          MetadataServiceGrpc.getRevokeMetadataMethod = getRevokeMetadataMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.RevokeMetadataRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RevokeMetadata"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.RevokeMetadataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.SimpleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MetadataServiceMethodDescriptorSupplier("RevokeMetadata"))
              .build();
        }
      }
    }
    return getRevokeMetadataMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MetadataServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MetadataServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MetadataServiceStub>() {
        @java.lang.Override
        public MetadataServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MetadataServiceStub(channel, callOptions);
        }
      };
    return MetadataServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MetadataServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MetadataServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MetadataServiceBlockingStub>() {
        @java.lang.Override
        public MetadataServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MetadataServiceBlockingStub(channel, callOptions);
        }
      };
    return MetadataServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MetadataServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MetadataServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MetadataServiceFutureStub>() {
        @java.lang.Override
        public MetadataServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MetadataServiceFutureStub(channel, callOptions);
        }
      };
    return MetadataServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ## 元数据 相关接口
   * </pre>
   */
  public static abstract class MetadataServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 查看单个元数据详情 (包含 列字段描述)
     * </pre>
     */
    public void getMetadataDetail(com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMetadataDetailMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查看全网元数据列表
     * </pre>
     */
    public void getMetadataDetailList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMetadataDetailListMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查看某个组织元数据列表
     * </pre>
     */
    public void getMetadataDetailListByOwner(com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListByOwnerRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMetadataDetailListByOwnerMethod(), responseObserver);
    }

    /**
     * <pre>
     * 发布元数据  (新增和编辑 都是发布新的元数据) &lt;底层根据 原始数据Id -- OriginId 来关联 新的MetaDataId&gt;
     * </pre>
     */
    public void publishMetadata(com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPublishMetadataMethod(), responseObserver);
    }

    /**
     * <pre>
     * 撤销元数据 (从底层网络撤销)
     * </pre>
     */
    public void revokeMetadata(com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.RevokeMetadataRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRevokeMetadataMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetMetadataDetailMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailRequest,
                com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailResponse>(
                  this, METHODID_GET_METADATA_DETAIL)))
          .addMethod(
            getGetMetadataDetailListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse>(
                  this, METHODID_GET_METADATA_DETAIL_LIST)))
          .addMethod(
            getGetMetadataDetailListByOwnerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListByOwnerRequest,
                com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse>(
                  this, METHODID_GET_METADATA_DETAIL_LIST_BY_OWNER)))
          .addMethod(
            getPublishMetadataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataRequest,
                com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataResponse>(
                  this, METHODID_PUBLISH_METADATA)))
          .addMethod(
            getRevokeMetadataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.RevokeMetadataRequest,
                com.platon.rosettanet.admin.grpc.service.SimpleResponse>(
                  this, METHODID_REVOKE_METADATA)))
          .build();
    }
  }

  /**
   * <pre>
   * ## 元数据 相关接口
   * </pre>
   */
  public static final class MetadataServiceStub extends io.grpc.stub.AbstractAsyncStub<MetadataServiceStub> {
    private MetadataServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MetadataServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MetadataServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查看单个元数据详情 (包含 列字段描述)
     * </pre>
     */
    public void getMetadataDetail(com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMetadataDetailMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查看全网元数据列表
     * </pre>
     */
    public void getMetadataDetailList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMetadataDetailListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查看某个组织元数据列表
     * </pre>
     */
    public void getMetadataDetailListByOwner(com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListByOwnerRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMetadataDetailListByOwnerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 发布元数据  (新增和编辑 都是发布新的元数据) &lt;底层根据 原始数据Id -- OriginId 来关联 新的MetaDataId&gt;
     * </pre>
     */
    public void publishMetadata(com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPublishMetadataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 撤销元数据 (从底层网络撤销)
     * </pre>
     */
    public void revokeMetadata(com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.RevokeMetadataRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRevokeMetadataMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * ## 元数据 相关接口
   * </pre>
   */
  public static final class MetadataServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<MetadataServiceBlockingStub> {
    private MetadataServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MetadataServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MetadataServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查看单个元数据详情 (包含 列字段描述)
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailResponse getMetadataDetail(com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMetadataDetailMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查看全网元数据列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse getMetadataDetailList(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMetadataDetailListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查看某个组织元数据列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse getMetadataDetailListByOwner(com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListByOwnerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMetadataDetailListByOwnerMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 发布元数据  (新增和编辑 都是发布新的元数据) &lt;底层根据 原始数据Id -- OriginId 来关联 新的MetaDataId&gt;
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataResponse publishMetadata(com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPublishMetadataMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 撤销元数据 (从底层网络撤销)
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.SimpleResponse revokeMetadata(com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.RevokeMetadataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRevokeMetadataMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * ## 元数据 相关接口
   * </pre>
   */
  public static final class MetadataServiceFutureStub extends io.grpc.stub.AbstractFutureStub<MetadataServiceFutureStub> {
    private MetadataServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MetadataServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MetadataServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查看单个元数据详情 (包含 列字段描述)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailResponse> getMetadataDetail(
        com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMetadataDetailMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查看全网元数据列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse> getMetadataDetailList(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMetadataDetailListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查看某个组织元数据列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse> getMetadataDetailListByOwner(
        com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListByOwnerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMetadataDetailListByOwnerMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 发布元数据  (新增和编辑 都是发布新的元数据) &lt;底层根据 原始数据Id -- OriginId 来关联 新的MetaDataId&gt;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataResponse> publishMetadata(
        com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPublishMetadataMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 撤销元数据 (从底层网络撤销)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.SimpleResponse> revokeMetadata(
        com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.RevokeMetadataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRevokeMetadataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_METADATA_DETAIL = 0;
  private static final int METHODID_GET_METADATA_DETAIL_LIST = 1;
  private static final int METHODID_GET_METADATA_DETAIL_LIST_BY_OWNER = 2;
  private static final int METHODID_PUBLISH_METADATA = 3;
  private static final int METHODID_REVOKE_METADATA = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MetadataServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MetadataServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_METADATA_DETAIL:
          serviceImpl.getMetadataDetail((com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailResponse>) responseObserver);
          break;
        case METHODID_GET_METADATA_DETAIL_LIST:
          serviceImpl.getMetadataDetailList((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse>) responseObserver);
          break;
        case METHODID_GET_METADATA_DETAIL_LIST_BY_OWNER:
          serviceImpl.getMetadataDetailListByOwner((com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListByOwnerRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.GetMetadataDetailListResponse>) responseObserver);
          break;
        case METHODID_PUBLISH_METADATA:
          serviceImpl.publishMetadata((com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.PublishMetadataResponse>) responseObserver);
          break;
        case METHODID_REVOKE_METADATA:
          serviceImpl.revokeMetadata((com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.RevokeMetadataRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse>) responseObserver);
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

  private static abstract class MetadataServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MetadataServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.platon.rosettanet.admin.grpc.service.MetaDataRpcMessage.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MetadataService");
    }
  }

  private static final class MetadataServiceFileDescriptorSupplier
      extends MetadataServiceBaseDescriptorSupplier {
    MetadataServiceFileDescriptorSupplier() {}
  }

  private static final class MetadataServiceMethodDescriptorSupplier
      extends MetadataServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MetadataServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (MetadataServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MetadataServiceFileDescriptorSupplier())
              .addMethod(getGetMetadataDetailMethod())
              .addMethod(getGetMetadataDetailListMethod())
              .addMethod(getGetMetadataDetailListByOwnerMethod())
              .addMethod(getPublishMetadataMethod())
              .addMethod(getRevokeMetadataMethod())
              .build();
        }
      }
    }
    return result;
  }
}
