package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ## 任务 相关接口
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: lib/api/task_rpc_api.proto")
public final class TaskServiceGrpc {

  private TaskServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcapi.TaskService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse> getGetTaskDetailListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTaskDetailList",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse> getGetTaskDetailListMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse> getGetTaskDetailListMethod;
    if ((getGetTaskDetailListMethod = TaskServiceGrpc.getGetTaskDetailListMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getGetTaskDetailListMethod = TaskServiceGrpc.getGetTaskDetailListMethod) == null) {
          TaskServiceGrpc.getGetTaskDetailListMethod = getGetTaskDetailListMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTaskDetailList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("GetTaskDetailList"))
              .build();
        }
      }
    }
    return getGetTaskDetailListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListRequest,
      com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse> getGetTaskEventListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTaskEventList",
      requestType = com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListRequest,
      com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse> getGetTaskEventListMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListRequest, com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse> getGetTaskEventListMethod;
    if ((getGetTaskEventListMethod = TaskServiceGrpc.getGetTaskEventListMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getGetTaskEventListMethod = TaskServiceGrpc.getGetTaskEventListMethod) == null) {
          TaskServiceGrpc.getGetTaskEventListMethod = getGetTaskEventListMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListRequest, com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTaskEventList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("GetTaskEventList"))
              .build();
        }
      }
    }
    return getGetTaskEventListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListByTaskIdsRequest,
      com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse> getGetTaskEventListByTaskIdsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTaskEventListByTaskIds",
      requestType = com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListByTaskIdsRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListByTaskIdsRequest,
      com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse> getGetTaskEventListByTaskIdsMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListByTaskIdsRequest, com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse> getGetTaskEventListByTaskIdsMethod;
    if ((getGetTaskEventListByTaskIdsMethod = TaskServiceGrpc.getGetTaskEventListByTaskIdsMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getGetTaskEventListByTaskIdsMethod = TaskServiceGrpc.getGetTaskEventListByTaskIdsMethod) == null) {
          TaskServiceGrpc.getGetTaskEventListByTaskIdsMethod = getGetTaskEventListByTaskIdsMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListByTaskIdsRequest, com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTaskEventListByTaskIds"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListByTaskIdsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("GetTaskEventListByTaskIds"))
              .build();
        }
      }
    }
    return getGetTaskEventListByTaskIdsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareRequest,
      com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareResponse> getPublishTaskDeclareMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PublishTaskDeclare",
      requestType = com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareRequest,
      com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareResponse> getPublishTaskDeclareMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareRequest, com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareResponse> getPublishTaskDeclareMethod;
    if ((getPublishTaskDeclareMethod = TaskServiceGrpc.getPublishTaskDeclareMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getPublishTaskDeclareMethod = TaskServiceGrpc.getPublishTaskDeclareMethod) == null) {
          TaskServiceGrpc.getPublishTaskDeclareMethod = getPublishTaskDeclareMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareRequest, com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PublishTaskDeclare"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("PublishTaskDeclare"))
              .build();
        }
      }
    }
    return getPublishTaskDeclareMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.TerminateTaskRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getTerminateTaskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TerminateTask",
      requestType = com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.TerminateTaskRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.SimpleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.TerminateTaskRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getTerminateTaskMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.TerminateTaskRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse> getTerminateTaskMethod;
    if ((getTerminateTaskMethod = TaskServiceGrpc.getTerminateTaskMethod) == null) {
      synchronized (TaskServiceGrpc.class) {
        if ((getTerminateTaskMethod = TaskServiceGrpc.getTerminateTaskMethod) == null) {
          TaskServiceGrpc.getTerminateTaskMethod = getTerminateTaskMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.TerminateTaskRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TerminateTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.TerminateTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.SimpleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TaskServiceMethodDescriptorSupplier("TerminateTask"))
              .build();
        }
      }
    }
    return getTerminateTaskMethod;
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
     * 查看本组织参与过的全部任务详情列表
     * </pre>
     */
    public void getTaskDetailList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTaskDetailListMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查看某个任务的全部事件列表
     * </pre>
     */
    public void getTaskEventList(com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTaskEventListMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查看多个任务的全部事件列表
     * </pre>
     */
    public void getTaskEventListByTaskIds(com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListByTaskIdsRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTaskEventListByTaskIdsMethod(), responseObserver);
    }

    /**
     * <pre>
     * 发布任务
     * </pre>
     */
    public void publishTaskDeclare(com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPublishTaskDeclareMethod(), responseObserver);
    }

    /**
     * <pre>
     * 终止任务
     * </pre>
     */
    public void terminateTask(com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.TerminateTaskRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTerminateTaskMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetTaskDetailListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse>(
                  this, METHODID_GET_TASK_DETAIL_LIST)))
          .addMethod(
            getGetTaskEventListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListRequest,
                com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse>(
                  this, METHODID_GET_TASK_EVENT_LIST)))
          .addMethod(
            getGetTaskEventListByTaskIdsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListByTaskIdsRequest,
                com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse>(
                  this, METHODID_GET_TASK_EVENT_LIST_BY_TASK_IDS)))
          .addMethod(
            getPublishTaskDeclareMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareRequest,
                com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareResponse>(
                  this, METHODID_PUBLISH_TASK_DECLARE)))
          .addMethod(
            getTerminateTaskMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.TerminateTaskRequest,
                com.platon.rosettanet.admin.grpc.service.SimpleResponse>(
                  this, METHODID_TERMINATE_TASK)))
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
     * 查看本组织参与过的全部任务详情列表
     * </pre>
     */
    public void getTaskDetailList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTaskDetailListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查看某个任务的全部事件列表
     * </pre>
     */
    public void getTaskEventList(com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTaskEventListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查看多个任务的全部事件列表
     * </pre>
     */
    public void getTaskEventListByTaskIds(com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListByTaskIdsRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTaskEventListByTaskIdsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 发布任务
     * </pre>
     */
    public void publishTaskDeclare(com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPublishTaskDeclareMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 终止任务
     * </pre>
     */
    public void terminateTask(com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.TerminateTaskRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTerminateTaskMethod(), getCallOptions()), request, responseObserver);
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
     * 查看本组织参与过的全部任务详情列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse getTaskDetailList(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTaskDetailListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查看某个任务的全部事件列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse getTaskEventList(com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTaskEventListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查看多个任务的全部事件列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse getTaskEventListByTaskIds(com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListByTaskIdsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTaskEventListByTaskIdsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 发布任务
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareResponse publishTaskDeclare(com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPublishTaskDeclareMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 终止任务
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.SimpleResponse terminateTask(com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.TerminateTaskRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTerminateTaskMethod(), getCallOptions(), request);
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
     * 查看本组织参与过的全部任务详情列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse> getTaskDetailList(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTaskDetailListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查看某个任务的全部事件列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse> getTaskEventList(
        com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTaskEventListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查看多个任务的全部事件列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse> getTaskEventListByTaskIds(
        com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListByTaskIdsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTaskEventListByTaskIdsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 发布任务
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareResponse> publishTaskDeclare(
        com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPublishTaskDeclareMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 终止任务
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.SimpleResponse> terminateTask(
        com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.TerminateTaskRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTerminateTaskMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_TASK_DETAIL_LIST = 0;
  private static final int METHODID_GET_TASK_EVENT_LIST = 1;
  private static final int METHODID_GET_TASK_EVENT_LIST_BY_TASK_IDS = 2;
  private static final int METHODID_PUBLISH_TASK_DECLARE = 3;
  private static final int METHODID_TERMINATE_TASK = 4;

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
          serviceImpl.getTaskDetailList((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskDetailListResponse>) responseObserver);
          break;
        case METHODID_GET_TASK_EVENT_LIST:
          serviceImpl.getTaskEventList((com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse>) responseObserver);
          break;
        case METHODID_GET_TASK_EVENT_LIST_BY_TASK_IDS:
          serviceImpl.getTaskEventListByTaskIds((com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListByTaskIdsRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.GetTaskEventListResponse>) responseObserver);
          break;
        case METHODID_PUBLISH_TASK_DECLARE:
          serviceImpl.publishTaskDeclare((com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.PublishTaskDeclareResponse>) responseObserver);
          break;
        case METHODID_TERMINATE_TASK:
          serviceImpl.terminateTask((com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.TerminateTaskRequest) request,
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

  private static abstract class TaskServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TaskServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.platon.rosettanet.admin.grpc.service.TaskRpcMessage.getDescriptor();
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
              .addMethod(getGetTaskEventListByTaskIdsMethod())
              .addMethod(getPublishTaskDeclareMethod())
              .addMethod(getTerminateTaskMethod())
              .build();
        }
      }
    }
    return result;
  }
}
