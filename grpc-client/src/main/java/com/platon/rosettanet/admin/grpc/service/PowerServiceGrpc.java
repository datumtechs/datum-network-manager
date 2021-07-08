package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ## 算力 相关接口
 * /           【注意】 算力和元数据 不一样, 对外面人来说, 算力只需要知道总的, 而元数据则需要知道单个单个的;
 *                    对自己来说, 算力和元数据都需要知道单个单个的.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: carrier/api/power_rpc_api.proto")
public final class PowerServiceGrpc {

  private PowerServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcapi.PowerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerTotalDetailListResponse> getGetPowerTotalDetailListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPowerTotalDetailList",
      requestType = com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams.class,
      responseType = com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerTotalDetailListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerTotalDetailListResponse> getGetPowerTotalDetailListMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerTotalDetailListResponse> getGetPowerTotalDetailListMethod;
    if ((getGetPowerTotalDetailListMethod = PowerServiceGrpc.getGetPowerTotalDetailListMethod) == null) {
      synchronized (PowerServiceGrpc.class) {
        if ((getGetPowerTotalDetailListMethod = PowerServiceGrpc.getGetPowerTotalDetailListMethod) == null) {
          PowerServiceGrpc.getGetPowerTotalDetailListMethod = getGetPowerTotalDetailListMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerTotalDetailListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPowerTotalDetailList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerTotalDetailListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PowerServiceMethodDescriptorSupplier("GetPowerTotalDetailList"))
              .build();
        }
      }
    }
    return getGetPowerTotalDetailListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerSingleDetailListResponse> getGetPowerSingleDetailListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPowerSingleDetailList",
      requestType = com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams.class,
      responseType = com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerSingleDetailListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerSingleDetailListResponse> getGetPowerSingleDetailListMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerSingleDetailListResponse> getGetPowerSingleDetailListMethod;
    if ((getGetPowerSingleDetailListMethod = PowerServiceGrpc.getGetPowerSingleDetailListMethod) == null) {
      synchronized (PowerServiceGrpc.class) {
        if ((getGetPowerSingleDetailListMethod = PowerServiceGrpc.getGetPowerSingleDetailListMethod) == null) {
          PowerServiceGrpc.getGetPowerSingleDetailListMethod = getGetPowerSingleDetailListMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerSingleDetailListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPowerSingleDetailList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerSingleDetailListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PowerServiceMethodDescriptorSupplier("GetPowerSingleDetailList"))
              .build();
        }
      }
    }
    return getGetPowerSingleDetailListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerRequest,
      com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerResponse> getPublishPowerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PublishPower",
      requestType = com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerRequest,
      com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerResponse> getPublishPowerMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerRequest, com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerResponse> getPublishPowerMethod;
    if ((getPublishPowerMethod = PowerServiceGrpc.getPublishPowerMethod) == null) {
      synchronized (PowerServiceGrpc.class) {
        if ((getPublishPowerMethod = PowerServiceGrpc.getPublishPowerMethod) == null) {
          PowerServiceGrpc.getPublishPowerMethod = getPublishPowerMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerRequest, com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PublishPower"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PowerServiceMethodDescriptorSupplier("PublishPower"))
              .build();
        }
      }
    }
    return getPublishPowerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.RevokePowerRequest,
      com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> getRevokePowerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RevokePower",
      requestType = com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.RevokePowerRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.RevokePowerRequest,
      com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> getRevokePowerMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.RevokePowerRequest, com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> getRevokePowerMethod;
    if ((getRevokePowerMethod = PowerServiceGrpc.getRevokePowerMethod) == null) {
      synchronized (PowerServiceGrpc.class) {
        if ((getRevokePowerMethod = PowerServiceGrpc.getRevokePowerMethod) == null) {
          PowerServiceGrpc.getRevokePowerMethod = getRevokePowerMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.RevokePowerRequest, com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RevokePower"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.RevokePowerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode.getDefaultInstance()))
              .setSchemaDescriptor(new PowerServiceMethodDescriptorSupplier("RevokePower"))
              .build();
        }
      }
    }
    return getRevokePowerMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PowerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PowerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PowerServiceStub>() {
        @java.lang.Override
        public PowerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PowerServiceStub(channel, callOptions);
        }
      };
    return PowerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PowerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PowerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PowerServiceBlockingStub>() {
        @java.lang.Override
        public PowerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PowerServiceBlockingStub(channel, callOptions);
        }
      };
    return PowerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PowerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PowerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PowerServiceFutureStub>() {
        @java.lang.Override
        public PowerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PowerServiceFutureStub(channel, callOptions);
        }
      };
    return PowerServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ## 算力 相关接口
   * /           【注意】 算力和元数据 不一样, 对外面人来说, 算力只需要知道总的, 而元数据则需要知道单个单个的;
   *                    对自己来说, 算力和元数据都需要知道单个单个的.
   * </pre>
   */
  public static abstract class PowerServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 查看各个节点的总算力详情列表
     * </pre>
     */
    public void getPowerTotalDetailList(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerTotalDetailListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPowerTotalDetailListMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查看某个节点各个单算力详情列表
     * </pre>
     */
    public void getPowerSingleDetailList(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerSingleDetailListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPowerSingleDetailListMethod(), responseObserver);
    }

    /**
     * <pre>
     * 启用算力 (发布算力)
     * </pre>
     */
    public void publishPower(com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPublishPowerMethod(), responseObserver);
    }

    /**
     * <pre>
     * 停用算力 (撤销算力)
     * </pre>
     */
    public void revokePower(com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.RevokePowerRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRevokePowerMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetPowerTotalDetailListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
                com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerTotalDetailListResponse>(
                  this, METHODID_GET_POWER_TOTAL_DETAIL_LIST)))
          .addMethod(
            getGetPowerSingleDetailListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
                com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerSingleDetailListResponse>(
                  this, METHODID_GET_POWER_SINGLE_DETAIL_LIST)))
          .addMethod(
            getPublishPowerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerRequest,
                com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerResponse>(
                  this, METHODID_PUBLISH_POWER)))
          .addMethod(
            getRevokePowerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.RevokePowerRequest,
                com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode>(
                  this, METHODID_REVOKE_POWER)))
          .build();
    }
  }

  /**
   * <pre>
   * ## 算力 相关接口
   * /           【注意】 算力和元数据 不一样, 对外面人来说, 算力只需要知道总的, 而元数据则需要知道单个单个的;
   *                    对自己来说, 算力和元数据都需要知道单个单个的.
   * </pre>
   */
  public static final class PowerServiceStub extends io.grpc.stub.AbstractAsyncStub<PowerServiceStub> {
    private PowerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PowerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PowerServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查看各个节点的总算力详情列表
     * </pre>
     */
    public void getPowerTotalDetailList(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerTotalDetailListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPowerTotalDetailListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查看某个节点各个单算力详情列表
     * </pre>
     */
    public void getPowerSingleDetailList(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerSingleDetailListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPowerSingleDetailListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 启用算力 (发布算力)
     * </pre>
     */
    public void publishPower(com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPublishPowerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 停用算力 (撤销算力)
     * </pre>
     */
    public void revokePower(com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.RevokePowerRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRevokePowerMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * ## 算力 相关接口
   * /           【注意】 算力和元数据 不一样, 对外面人来说, 算力只需要知道总的, 而元数据则需要知道单个单个的;
   *                    对自己来说, 算力和元数据都需要知道单个单个的.
   * </pre>
   */
  public static final class PowerServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<PowerServiceBlockingStub> {
    private PowerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PowerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PowerServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查看各个节点的总算力详情列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerTotalDetailListResponse getPowerTotalDetailList(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPowerTotalDetailListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查看某个节点各个单算力详情列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerSingleDetailListResponse getPowerSingleDetailList(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPowerSingleDetailListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 启用算力 (发布算力)
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerResponse publishPower(com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPublishPowerMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 停用算力 (撤销算力)
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode revokePower(com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.RevokePowerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRevokePowerMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * ## 算力 相关接口
   * /           【注意】 算力和元数据 不一样, 对外面人来说, 算力只需要知道总的, 而元数据则需要知道单个单个的;
   *                    对自己来说, 算力和元数据都需要知道单个单个的.
   * </pre>
   */
  public static final class PowerServiceFutureStub extends io.grpc.stub.AbstractFutureStub<PowerServiceFutureStub> {
    private PowerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PowerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PowerServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查看各个节点的总算力详情列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerTotalDetailListResponse> getPowerTotalDetailList(
        com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPowerTotalDetailListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查看某个节点各个单算力详情列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerSingleDetailListResponse> getPowerSingleDetailList(
        com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPowerSingleDetailListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 启用算力 (发布算力)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerResponse> publishPower(
        com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPublishPowerMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 停用算力 (撤销算力)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> revokePower(
        com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.RevokePowerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRevokePowerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_POWER_TOTAL_DETAIL_LIST = 0;
  private static final int METHODID_GET_POWER_SINGLE_DETAIL_LIST = 1;
  private static final int METHODID_PUBLISH_POWER = 2;
  private static final int METHODID_REVOKE_POWER = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PowerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PowerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_POWER_TOTAL_DETAIL_LIST:
          serviceImpl.getPowerTotalDetailList((com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerTotalDetailListResponse>) responseObserver);
          break;
        case METHODID_GET_POWER_SINGLE_DETAIL_LIST:
          serviceImpl.getPowerSingleDetailList((com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.GetPowerSingleDetailListResponse>) responseObserver);
          break;
        case METHODID_PUBLISH_POWER:
          serviceImpl.publishPower((com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.PublishPowerResponse>) responseObserver);
          break;
        case METHODID_REVOKE_POWER:
          serviceImpl.revokePower((com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.RevokePowerRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode>) responseObserver);
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

  private static abstract class PowerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PowerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.platon.rosettanet.admin.grpc.service.PowerRpcMessage.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PowerService");
    }
  }

  private static final class PowerServiceFileDescriptorSupplier
      extends PowerServiceBaseDescriptorSupplier {
    PowerServiceFileDescriptorSupplier() {}
  }

  private static final class PowerServiceMethodDescriptorSupplier
      extends PowerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PowerServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (PowerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PowerServiceFileDescriptorSupplier())
              .addMethod(getGetPowerTotalDetailListMethod())
              .addMethod(getGetPowerSingleDetailListMethod())
              .addMethod(getPublishPowerMethod())
              .addMethod(getRevokePowerMethod())
              .build();
        }
      }
    }
    return result;
  }
}
