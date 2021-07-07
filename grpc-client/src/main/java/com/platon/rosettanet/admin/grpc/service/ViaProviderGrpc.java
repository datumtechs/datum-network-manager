package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: fighter/via_svc.proto")
public final class ViaProviderGrpc {

  private ViaProviderGrpc() {}

  public static final String SERVICE_NAME = "viasvc.ViaProvider";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq,
      com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns> getExposeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Expose",
      requestType = com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq.class,
      responseType = com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq,
      com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns> getExposeMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq, com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns> getExposeMethod;
    if ((getExposeMethod = ViaProviderGrpc.getExposeMethod) == null) {
      synchronized (ViaProviderGrpc.class) {
        if ((getExposeMethod = ViaProviderGrpc.getExposeMethod) == null) {
          ViaProviderGrpc.getExposeMethod = getExposeMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq, com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Expose"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns.getDefaultInstance()))
              .setSchemaDescriptor(new ViaProviderMethodDescriptorSupplier("Expose"))
              .build();
        }
      }
    }
    return getExposeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq,
      com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns> getOffMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Off",
      requestType = com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq.class,
      responseType = com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq,
      com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns> getOffMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq, com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns> getOffMethod;
    if ((getOffMethod = ViaProviderGrpc.getOffMethod) == null) {
      synchronized (ViaProviderGrpc.class) {
        if ((getOffMethod = ViaProviderGrpc.getOffMethod) == null) {
          ViaProviderGrpc.getOffMethod = getOffMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq, com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Off"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns.getDefaultInstance()))
              .setSchemaDescriptor(new ViaProviderMethodDescriptorSupplier("Off"))
              .build();
        }
      }
    }
    return getOffMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ViaProviderStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ViaProviderStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ViaProviderStub>() {
        @java.lang.Override
        public ViaProviderStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ViaProviderStub(channel, callOptions);
        }
      };
    return ViaProviderStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ViaProviderBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ViaProviderBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ViaProviderBlockingStub>() {
        @java.lang.Override
        public ViaProviderBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ViaProviderBlockingStub(channel, callOptions);
        }
      };
    return ViaProviderBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ViaProviderFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ViaProviderFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ViaProviderFutureStub>() {
        @java.lang.Override
        public ViaProviderFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ViaProviderFutureStub(channel, callOptions);
        }
      };
    return ViaProviderFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ViaProviderImplBase implements io.grpc.BindableService {

    /**
     */
    public void expose(com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExposeMethod(), responseObserver);
    }

    /**
     */
    public void off(com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOffMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getExposeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq,
                com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns>(
                  this, METHODID_EXPOSE)))
          .addMethod(
            getOffMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq,
                com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns>(
                  this, METHODID_OFF)))
          .build();
    }
  }

  /**
   */
  public static final class ViaProviderStub extends io.grpc.stub.AbstractAsyncStub<ViaProviderStub> {
    private ViaProviderStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ViaProviderStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ViaProviderStub(channel, callOptions);
    }

    /**
     */
    public void expose(com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExposeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void off(com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOffMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ViaProviderBlockingStub extends io.grpc.stub.AbstractBlockingStub<ViaProviderBlockingStub> {
    private ViaProviderBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ViaProviderBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ViaProviderBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns expose(com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExposeMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns off(com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOffMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ViaProviderFutureStub extends io.grpc.stub.AbstractFutureStub<ViaProviderFutureStub> {
    private ViaProviderFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ViaProviderFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ViaProviderFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns> expose(
        com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExposeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns> off(
        com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOffMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_EXPOSE = 0;
  private static final int METHODID_OFF = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ViaProviderImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ViaProviderImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_EXPOSE:
          serviceImpl.expose((com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns>) responseObserver);
          break;
        case METHODID_OFF:
          serviceImpl.off((com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeReq) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.ExposeAns>) responseObserver);
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

  private static abstract class ViaProviderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ViaProviderBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.platon.rosettanet.admin.grpc.ViaProviderRpcMessage.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ViaProvider");
    }
  }

  private static final class ViaProviderFileDescriptorSupplier
      extends ViaProviderBaseDescriptorSupplier {
    ViaProviderFileDescriptorSupplier() {}
  }

  private static final class ViaProviderMethodDescriptorSupplier
      extends ViaProviderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ViaProviderMethodDescriptorSupplier(String methodName) {
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
      synchronized (ViaProviderGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ViaProviderFileDescriptorSupplier())
              .addMethod(getExposeMethod())
              .addMethod(getOffMethod())
              .build();
        }
      }
    }
    return result;
  }
}
