package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ## 任务 相关接口
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: carrier/api/task_rpc_api.proto")
public final class TaskServiceGrpc {

  private TaskServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcapi.TaskService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskDetailListResponse> getGetTaskDetailListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTaskDetailList",
      requestType = com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams.class,
      responseType = com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskDetailListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskDetailListResponse> getGetTaskDetailListMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskDetailListResponse> getGetTaskDetailListMethod;
    if ((getGetTaskDetailListMethod = TaskServiceGrpc.getGetTaskDetailListMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getGetTaskDetailListMethod = TaskServiceGrpc.getGetTaskDetailListMethod) == null) {
          TaskServiceGrpc.getGetTaskDetailListMethod = getGetTaskDetailListMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskDetailListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTaskDetailList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskDetailListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("GetTaskDetailList"))
              .build();
        }
      }
    }
    return getGetTaskDetailListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListRequest,
      com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListResponse> getGetTaskEventListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTaskEventList",
      requestType = com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListRequest,
      com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListResponse> getGetTaskEventListMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListRequest, com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListResponse> getGetTaskEventListMethod;
    if ((getGetTaskEventListMethod = TaskServiceGrpc.getGetTaskEventListMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getGetTaskEventListMethod = TaskServiceGrpc.getGetTaskEventListMethod) == null) {
          TaskServiceGrpc.getGetTaskEventListMethod = getGetTaskEventListMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListRequest, com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTaskEventList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("GetTaskEventList"))
              .build();
        }
      }
    }
    return getGetTaskEventListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareRequest,
      com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareResponse> getPublishTaskDeclareMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PublishTaskDeclare",
      requestType = com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareRequest,
      com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareResponse> getPublishTaskDeclareMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareRequest, com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareResponse> getPublishTaskDeclareMethod;
    if ((getPublishTaskDeclareMethod = TaskServiceGrpc.getPublishTaskDeclareMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getPublishTaskDeclareMethod = TaskServiceGrpc.getPublishTaskDeclareMethod) == null) {
          TaskServiceGrpc.getPublishTaskDeclareMethod = getPublishTaskDeclareMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareRequest, com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PublishTaskDeclare"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("PublishTaskDeclare"))
              .build();
        }
      }
    }
    return getPublishTaskDeclareMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TaskServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TaskServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TaskServiceStub>() {
        @java.lang.Override
        public TaskServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TaskServiceStub(channel, callOptions);
        }
      };
    return TaskServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TaskServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TaskServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TaskServiceBlockingStub>() {
        @java.lang.Override
        public TaskServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TaskServiceBlockingStub(channel, callOptions);
        }
      };
    return TaskServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TaskServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TaskServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TaskServiceFutureStub>() {
        @java.lang.Override
        public TaskServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TaskServiceFutureStub(channel, callOptions);
        }
      };
    return TaskServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ## 任务 相关接口
   * </pre>
   */
  public static abstract class TaskServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 查看全部任务详情列表
     * </pre>
     */
    public void getTaskDetailList(com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskDetailListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTaskDetailListMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查看某个任务的全部事件列表
     * </pre>
     */
    public void getTaskEventList(com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTaskEventListMethod(), responseObserver);
    }

    /**
     * <pre>
     * 发布任务
     * </pre>
     */
    public void publishTaskDeclare(com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPublishTaskDeclareMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetTaskDetailListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams,
                com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskDetailListResponse>(
                  this, METHODID_GET_TASK_DETAIL_LIST)))
          .addMethod(
            getGetTaskEventListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListRequest,
                com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListResponse>(
                  this, METHODID_GET_TASK_EVENT_LIST)))
          .addMethod(
            getPublishTaskDeclareMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareRequest,
                com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareResponse>(
                  this, METHODID_PUBLISH_TASK_DECLARE)))
          .build();
    }
  }

  /**
   * <pre>
   * ## 任务 相关接口
   * </pre>
   */
  public static final class TaskServiceStub extends io.grpc.stub.AbstractAsyncStub<TaskServiceStub> {
    private TaskServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TaskServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TaskServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查看全部任务详情列表
     * </pre>
     */
    public void getTaskDetailList(com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskDetailListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTaskDetailListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查看某个任务的全部事件列表
     * </pre>
     */
    public void getTaskEventList(com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTaskEventListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 发布任务
     * </pre>
     */
    public void publishTaskDeclare(com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPublishTaskDeclareMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * ## 任务 相关接口
   * </pre>
   */
  public static final class TaskServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<TaskServiceBlockingStub> {
    private TaskServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TaskServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TaskServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查看全部任务详情列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskDetailListResponse getTaskDetailList(com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTaskDetailListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查看某个任务的全部事件列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListResponse getTaskEventList(com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTaskEventListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 发布任务
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareResponse publishTaskDeclare(com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPublishTaskDeclareMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * ## 任务 相关接口
   * </pre>
   */
  public static final class TaskServiceFutureStub extends io.grpc.stub.AbstractFutureStub<TaskServiceFutureStub> {
    private TaskServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TaskServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TaskServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 查看全部任务详情列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskDetailListResponse> getTaskDetailList(
        com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTaskDetailListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查看某个任务的全部事件列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListResponse> getTaskEventList(
        com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTaskEventListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 发布任务
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareResponse> publishTaskDeclare(
        com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPublishTaskDeclareMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_TASK_DETAIL_LIST = 0;
  private static final int METHODID_GET_TASK_EVENT_LIST = 1;
  private static final int METHODID_PUBLISH_TASK_DECLARE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TaskServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TaskServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_TASK_DETAIL_LIST:
          serviceImpl.getTaskDetailList((com.platon.rosettanet.admin.grpc.CommonMessage.EmptyGetParams) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskDetailListResponse>) responseObserver);
          break;
        case METHODID_GET_TASK_EVENT_LIST:
          serviceImpl.getTaskEventList((com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.TaskRpcMessage.GetTaskEventListResponse>) responseObserver);
          break;
        case METHODID_PUBLISH_TASK_DECLARE:
          serviceImpl.publishTaskDeclare((com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.TaskRpcMessage.PublishTaskDeclareResponse>) responseObserver);
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

  private static abstract class TaskServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TaskServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.platon.rosettanet.admin.grpc.TaskRpcMessage.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TaskService");
    }
  }

  private static final class TaskServiceFileDescriptorSupplier
      extends TaskServiceBaseDescriptorSupplier {
    TaskServiceFileDescriptorSupplier() {}
  }

  private static final class TaskServiceMethodDescriptorSupplier
      extends TaskServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TaskServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (TaskServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TaskServiceFileDescriptorSupplier())
              .addMethod(getGetTaskDetailListMethod())
              .addMethod(getGetTaskEventListMethod())
              .addMethod(getPublishTaskDeclareMethod())
              .build();
        }
      }
    }
    return result;
  }
}
