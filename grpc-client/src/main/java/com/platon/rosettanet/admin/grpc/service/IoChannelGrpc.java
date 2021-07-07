package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: fighter/io_channel.proto")
public final class IoChannelGrpc {

  private IoChannelGrpc() {}

  public static final String SERVICE_NAME = "io_channel.IoChannel";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.SendRequest,
      com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.RetCode> getSendMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Send",
      requestType = com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.SendRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.RetCode.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.SendRequest,
      com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.RetCode> getSendMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.SendRequest, com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.RetCode> getSendMethod;
    if ((getSendMethod = IoChannelGrpc.getSendMethod) == null) {
      synchronized (IoChannelGrpc.class) {
        if ((getSendMethod = IoChannelGrpc.getSendMethod) == null) {
          IoChannelGrpc.getSendMethod = getSendMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.SendRequest, com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.RetCode>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Send"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.SendRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.RetCode.getDefaultInstance()))
              .setSchemaDescriptor(new IoChannelMethodDescriptorSupplier("Send"))
              .build();
        }
      }
    }
    return getSendMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static IoChannelStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IoChannelStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IoChannelStub>() {
        @java.lang.Override
        public IoChannelStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IoChannelStub(channel, callOptions);
        }
      };
    return IoChannelStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static IoChannelBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IoChannelBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IoChannelBlockingStub>() {
        @java.lang.Override
        public IoChannelBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IoChannelBlockingStub(channel, callOptions);
        }
      };
    return IoChannelBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static IoChannelFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IoChannelFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IoChannelFutureStub>() {
        @java.lang.Override
        public IoChannelFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IoChannelFutureStub(channel, callOptions);
        }
      };
    return IoChannelFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class IoChannelImplBase implements io.grpc.BindableService {

    /**
     */
    public void send(com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.SendRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.RetCode> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.SendRequest,
                com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.RetCode>(
                  this, METHODID_SEND)))
          .build();
    }
  }

  /**
   */
  public static final class IoChannelStub extends io.grpc.stub.AbstractAsyncStub<IoChannelStub> {
    private IoChannelStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IoChannelStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IoChannelStub(channel, callOptions);
    }

    /**
     */
    public void send(com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.SendRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.RetCode> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class IoChannelBlockingStub extends io.grpc.stub.AbstractBlockingStub<IoChannelBlockingStub> {
    private IoChannelBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IoChannelBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IoChannelBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.RetCode send(com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.SendRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class IoChannelFutureStub extends io.grpc.stub.AbstractFutureStub<IoChannelFutureStub> {
    private IoChannelFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IoChannelFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IoChannelFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.RetCode> send(
        com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.SendRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final IoChannelImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(IoChannelImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND:
          serviceImpl.send((com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.SendRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.RetCode>) responseObserver);
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

  private static abstract class IoChannelBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    IoChannelBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.platon.rosettanet.admin.grpc.IoChannelRpcMessage.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("IoChannel");
    }
  }

  private static final class IoChannelFileDescriptorSupplier
      extends IoChannelBaseDescriptorSupplier {
    IoChannelFileDescriptorSupplier() {}
  }

  private static final class IoChannelMethodDescriptorSupplier
      extends IoChannelBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    IoChannelMethodDescriptorSupplier(String methodName) {
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
      synchronized (IoChannelGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new IoChannelFileDescriptorSupplier())
              .addMethod(getSendMethod())
              .build();
        }
      }
    }
    return result;
  }
}
