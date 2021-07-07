package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: fighter/compute_svc.proto")
public final class ComputeProviderGrpc {

  private ComputeProviderGrpc() {}

  public static final String SERVICE_NAME = "computesvc.ComputeProvider";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetStatusReply> getGetStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStatus",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetStatusReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetStatusReply> getGetStatusMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetStatusReply> getGetStatusMethod;
    if ((getGetStatusMethod = ComputeProviderGrpc.getGetStatusMethod) == null) {
      synchronized (ComputeProviderGrpc.class) {
        if ((getGetStatusMethod = ComputeProviderGrpc.getGetStatusMethod) == null) {
          ComputeProviderGrpc.getGetStatusMethod = getGetStatusMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetStatusReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetStatusReply.getDefaultInstance()))
              .setSchemaDescriptor(new ComputeProviderMethodDescriptorSupplier("GetStatus"))
              .build();
        }
      }
    }
    return getGetStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReq,
      com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReply> getGetTaskDetailsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTaskDetails",
      requestType = com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReq.class,
      responseType = com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReq,
      com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReply> getGetTaskDetailsMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReq, com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReply> getGetTaskDetailsMethod;
    if ((getGetTaskDetailsMethod = ComputeProviderGrpc.getGetTaskDetailsMethod) == null) {
      synchronized (ComputeProviderGrpc.class) {
        if ((getGetTaskDetailsMethod = ComputeProviderGrpc.getGetTaskDetailsMethod) == null) {
          ComputeProviderGrpc.getGetTaskDetailsMethod = getGetTaskDetailsMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReq, com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTaskDetails"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReply.getDefaultInstance()))
              .setSchemaDescriptor(new ComputeProviderMethodDescriptorSupplier("GetTaskDetails"))
              .build();
        }
      }
    }
    return getGetTaskDetailsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReq,
      com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply> getUploadShardMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadShard",
      requestType = com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReq.class,
      responseType = com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReq,
      com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply> getUploadShardMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReq, com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply> getUploadShardMethod;
    if ((getUploadShardMethod = ComputeProviderGrpc.getUploadShardMethod) == null) {
      synchronized (ComputeProviderGrpc.class) {
        if ((getUploadShardMethod = ComputeProviderGrpc.getUploadShardMethod) == null) {
          ComputeProviderGrpc.getUploadShardMethod = getUploadShardMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReq, com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UploadShard"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply.getDefaultInstance()))
              .setSchemaDescriptor(new ComputeProviderMethodDescriptorSupplier("UploadShard"))
              .build();
        }
      }
    }
    return getUploadShardMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.TaskReadyGoReq,
      com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply> getHandleTaskReadyGoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandleTaskReadyGo",
      requestType = com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.TaskReadyGoReq.class,
      responseType = com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.TaskReadyGoReq,
      com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply> getHandleTaskReadyGoMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.TaskReadyGoReq, com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply> getHandleTaskReadyGoMethod;
    if ((getHandleTaskReadyGoMethod = ComputeProviderGrpc.getHandleTaskReadyGoMethod) == null) {
      synchronized (ComputeProviderGrpc.class) {
        if ((getHandleTaskReadyGoMethod = ComputeProviderGrpc.getHandleTaskReadyGoMethod) == null) {
          ComputeProviderGrpc.getHandleTaskReadyGoMethod = getHandleTaskReadyGoMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.TaskReadyGoReq, com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandleTaskReadyGo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.TaskReadyGoReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply.getDefaultInstance()))
              .setSchemaDescriptor(new ComputeProviderMethodDescriptorSupplier("HandleTaskReadyGo"))
              .build();
        }
      }
    }
    return getHandleTaskReadyGoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ComputeProviderStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ComputeProviderStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ComputeProviderStub>() {
        @java.lang.Override
        public ComputeProviderStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ComputeProviderStub(channel, callOptions);
        }
      };
    return ComputeProviderStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ComputeProviderBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ComputeProviderBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ComputeProviderBlockingStub>() {
        @java.lang.Override
        public ComputeProviderBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ComputeProviderBlockingStub(channel, callOptions);
        }
      };
    return ComputeProviderBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ComputeProviderFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ComputeProviderFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ComputeProviderFutureStub>() {
        @java.lang.Override
        public ComputeProviderFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ComputeProviderFutureStub(channel, callOptions);
        }
      };
    return ComputeProviderFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ComputeProviderImplBase implements io.grpc.BindableService {

    /**
     */
    public void getStatus(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetStatusReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStatusMethod(), responseObserver);
    }

    /**
     */
    public void getTaskDetails(com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReq request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTaskDetailsMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReq> uploadShard(
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getUploadShardMethod(), responseObserver);
    }

    /**
     */
    public void handleTaskReadyGo(com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.TaskReadyGoReq request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleTaskReadyGoMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetStatusMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetStatusReply>(
                  this, METHODID_GET_STATUS)))
          .addMethod(
            getGetTaskDetailsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReq,
                com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReply>(
                  this, METHODID_GET_TASK_DETAILS)))
          .addMethod(
            getUploadShardMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReq,
                com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply>(
                  this, METHODID_UPLOAD_SHARD)))
          .addMethod(
            getHandleTaskReadyGoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.TaskReadyGoReq,
                com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply>(
                  this, METHODID_HANDLE_TASK_READY_GO)))
          .build();
    }
  }

  /**
   */
  public static final class ComputeProviderStub extends io.grpc.stub.AbstractAsyncStub<ComputeProviderStub> {
    private ComputeProviderStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ComputeProviderStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ComputeProviderStub(channel, callOptions);
    }

    /**
     */
    public void getStatus(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetStatusReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTaskDetails(com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReq request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTaskDetailsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReq> uploadShard(
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getUploadShardMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void handleTaskReadyGo(com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.TaskReadyGoReq request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleTaskReadyGoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ComputeProviderBlockingStub extends io.grpc.stub.AbstractBlockingStub<ComputeProviderBlockingStub> {
    private ComputeProviderBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ComputeProviderBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ComputeProviderBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetStatusReply getStatus(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStatusMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReply getTaskDetails(com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTaskDetailsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply handleTaskReadyGo(com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.TaskReadyGoReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleTaskReadyGoMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ComputeProviderFutureStub extends io.grpc.stub.AbstractFutureStub<ComputeProviderFutureStub> {
    private ComputeProviderFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ComputeProviderFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ComputeProviderFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetStatusReply> getStatus(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStatusMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReply> getTaskDetails(
        com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTaskDetailsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply> handleTaskReadyGo(
        com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.TaskReadyGoReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleTaskReadyGoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_STATUS = 0;
  private static final int METHODID_GET_TASK_DETAILS = 1;
  private static final int METHODID_HANDLE_TASK_READY_GO = 2;
  private static final int METHODID_UPLOAD_SHARD = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ComputeProviderImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ComputeProviderImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_STATUS:
          serviceImpl.getStatus((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetStatusReply>) responseObserver);
          break;
        case METHODID_GET_TASK_DETAILS:
          serviceImpl.getTaskDetails((com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReq) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.GetTaskDetailsReply>) responseObserver);
          break;
        case METHODID_HANDLE_TASK_READY_GO:
          serviceImpl.handleTaskReadyGo((com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.TaskReadyGoReq) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply>) responseObserver);
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
        case METHODID_UPLOAD_SHARD:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.uploadShard(
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.UploadShardReply>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ComputeProviderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ComputeProviderBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.platon.rosettanet.admin.grpc.ComputeProviderRpcMessage.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ComputeProvider");
    }
  }

  private static final class ComputeProviderFileDescriptorSupplier
      extends ComputeProviderBaseDescriptorSupplier {
    ComputeProviderFileDescriptorSupplier() {}
  }

  private static final class ComputeProviderMethodDescriptorSupplier
      extends ComputeProviderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ComputeProviderMethodDescriptorSupplier(String methodName) {
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
      synchronized (ComputeProviderGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ComputeProviderFileDescriptorSupplier())
              .addMethod(getGetStatusMethod())
              .addMethod(getGetTaskDetailsMethod())
              .addMethod(getUploadShardMethod())
              .addMethod(getHandleTaskReadyGoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
