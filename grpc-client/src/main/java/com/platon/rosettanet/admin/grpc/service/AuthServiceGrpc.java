package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: lib/api/auth_rpc_api.proto")
public final class AuthServiceGrpc {

  private AuthServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcapi.AuthService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityRequest,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityResponse> getApplyMetadataAuthorityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ApplyMetadataAuthority",
      requestType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityRequest,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityResponse> getApplyMetadataAuthorityMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityRequest, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityResponse> getApplyMetadataAuthorityMethod;
    if ((getApplyMetadataAuthorityMethod = AuthServiceGrpc.getApplyMetadataAuthorityMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getApplyMetadataAuthorityMethod = AuthServiceGrpc.getApplyMetadataAuthorityMethod) == null) {
          AuthServiceGrpc.getApplyMetadataAuthorityMethod = getApplyMetadataAuthorityMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityRequest, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ApplyMetadataAuthority"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("ApplyMetadataAuthority"))
              .build();
        }
      }
    }
    return getApplyMetadataAuthorityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityRequest,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityResponse> getAuditMetadataAuthorityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AuditMetadataAuthority",
      requestType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityRequest,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityResponse> getAuditMetadataAuthorityMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityRequest, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityResponse> getAuditMetadataAuthorityMethod;
    if ((getAuditMetadataAuthorityMethod = AuthServiceGrpc.getAuditMetadataAuthorityMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getAuditMetadataAuthorityMethod = AuthServiceGrpc.getAuditMetadataAuthorityMethod) == null) {
          AuthServiceGrpc.getAuditMetadataAuthorityMethod = getAuditMetadataAuthorityMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityRequest, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AuditMetadataAuthority"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("AuditMetadataAuthority"))
              .build();
        }
      }
    }
    return getAuditMetadataAuthorityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetadataAuthorityListResponse> getGetMetadataAuthorityListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMetadataAuthorityList",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetadataAuthorityListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetadataAuthorityListResponse> getGetMetadataAuthorityListMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetadataAuthorityListResponse> getGetMetadataAuthorityListMethod;
    if ((getGetMetadataAuthorityListMethod = AuthServiceGrpc.getGetMetadataAuthorityListMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getGetMetadataAuthorityListMethod = AuthServiceGrpc.getGetMetadataAuthorityListMethod) == null) {
          AuthServiceGrpc.getGetMetadataAuthorityListMethod = getGetMetadataAuthorityListMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetadataAuthorityListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMetadataAuthorityList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetadataAuthorityListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("GetMetadataAuthorityList"))
              .build();
        }
      }
    }
    return getGetMetadataAuthorityListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getApplyIdentityJoinMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ApplyIdentityJoin",
      requestType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.SimpleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getApplyIdentityJoinMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse> getApplyIdentityJoinMethod;
    if ((getApplyIdentityJoinMethod = AuthServiceGrpc.getApplyIdentityJoinMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getApplyIdentityJoinMethod = AuthServiceGrpc.getApplyIdentityJoinMethod) == null) {
          AuthServiceGrpc.getApplyIdentityJoinMethod = getApplyIdentityJoinMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ApplyIdentityJoin"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.SimpleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("ApplyIdentityJoin"))
              .build();
        }
      }
    }
    return getApplyIdentityJoinMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getRevokeIdentityJoinMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RevokeIdentityJoin",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.SimpleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getRevokeIdentityJoinMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.SimpleResponse> getRevokeIdentityJoinMethod;
    if ((getRevokeIdentityJoinMethod = AuthServiceGrpc.getRevokeIdentityJoinMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getRevokeIdentityJoinMethod = AuthServiceGrpc.getRevokeIdentityJoinMethod) == null) {
          AuthServiceGrpc.getRevokeIdentityJoinMethod = getRevokeIdentityJoinMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.SimpleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RevokeIdentityJoin"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.SimpleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("RevokeIdentityJoin"))
              .build();
        }
      }
    }
    return getRevokeIdentityJoinMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse> getGetNodeIdentityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetNodeIdentity",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse> getGetNodeIdentityMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse> getGetNodeIdentityMethod;
    if ((getGetNodeIdentityMethod = AuthServiceGrpc.getGetNodeIdentityMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getGetNodeIdentityMethod = AuthServiceGrpc.getGetNodeIdentityMethod) == null) {
          AuthServiceGrpc.getGetNodeIdentityMethod = getGetNodeIdentityMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetNodeIdentity"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("GetNodeIdentity"))
              .build();
        }
      }
    }
    return getGetNodeIdentityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse> getGetIdentityListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetIdentityList",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse> getGetIdentityListMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse> getGetIdentityListMethod;
    if ((getGetIdentityListMethod = AuthServiceGrpc.getGetIdentityListMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getGetIdentityListMethod = AuthServiceGrpc.getGetIdentityListMethod) == null) {
          AuthServiceGrpc.getGetIdentityListMethod = getGetIdentityListMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetIdentityList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("GetIdentityList"))
              .build();
        }
      }
    }
    return getGetIdentityListMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceStub>() {
        @java.lang.Override
        public AuthServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceStub(channel, callOptions);
        }
      };
    return AuthServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceBlockingStub>() {
        @java.lang.Override
        public AuthServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceBlockingStub(channel, callOptions);
        }
      };
    return AuthServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceFutureStub>() {
        @java.lang.Override
        public AuthServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceFutureStub(channel, callOptions);
        }
      };
    return AuthServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class AuthServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 数据授权申请
     * </pre>
     */
    public void applyMetadataAuthority(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getApplyMetadataAuthorityMethod(), responseObserver);
    }

    /**
     * <pre>
     * 数据授权审核
     * </pre>
     */
    public void auditMetadataAuthority(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuditMetadataAuthorityMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取数据授权申请列表
     * </pre>
     */
    public void getMetadataAuthorityList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetadataAuthorityListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMetadataAuthorityListMethod(), responseObserver);
    }

    /**
     * <pre>
     * 组织身份入网申请
     * </pre>
     */
    public void applyIdentityJoin(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getApplyIdentityJoinMethod(), responseObserver);
    }

    /**
     * <pre>
     * 注销准入网络
     * </pre>
     */
    public void revokeIdentityJoin(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRevokeIdentityJoinMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查询自己组织的identity信息
     * </pre>
     */
    public void getNodeIdentity(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetNodeIdentityMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查询全网组织的身份信息列表(已入网的)
     * </pre>
     */
    public void getIdentityList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetIdentityListMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getApplyMetadataAuthorityMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityRequest,
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityResponse>(
                  this, METHODID_APPLY_METADATA_AUTHORITY)))
          .addMethod(
            getAuditMetadataAuthorityMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityRequest,
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityResponse>(
                  this, METHODID_AUDIT_METADATA_AUTHORITY)))
          .addMethod(
            getGetMetadataAuthorityListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetadataAuthorityListResponse>(
                  this, METHODID_GET_METADATA_AUTHORITY_LIST)))
          .addMethod(
            getApplyIdentityJoinMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest,
                com.platon.rosettanet.admin.grpc.service.SimpleResponse>(
                  this, METHODID_APPLY_IDENTITY_JOIN)))
          .addMethod(
            getRevokeIdentityJoinMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.SimpleResponse>(
                  this, METHODID_REVOKE_IDENTITY_JOIN)))
          .addMethod(
            getGetNodeIdentityMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse>(
                  this, METHODID_GET_NODE_IDENTITY)))
          .addMethod(
            getGetIdentityListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse>(
                  this, METHODID_GET_IDENTITY_LIST)))
          .build();
    }
  }

  /**
   */
  public static final class AuthServiceStub extends io.grpc.stub.AbstractAsyncStub<AuthServiceStub> {
    private AuthServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 数据授权申请
     * </pre>
     */
    public void applyMetadataAuthority(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getApplyMetadataAuthorityMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 数据授权审核
     * </pre>
     */
    public void auditMetadataAuthority(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuditMetadataAuthorityMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取数据授权申请列表
     * </pre>
     */
    public void getMetadataAuthorityList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetadataAuthorityListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMetadataAuthorityListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 组织身份入网申请
     * </pre>
     */
    public void applyIdentityJoin(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getApplyIdentityJoinMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 注销准入网络
     * </pre>
     */
    public void revokeIdentityJoin(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRevokeIdentityJoinMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询自己组织的identity信息
     * </pre>
     */
    public void getNodeIdentity(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetNodeIdentityMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询全网组织的身份信息列表(已入网的)
     * </pre>
     */
    public void getIdentityList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetIdentityListMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AuthServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<AuthServiceBlockingStub> {
    private AuthServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 数据授权申请
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityResponse applyMetadataAuthority(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getApplyMetadataAuthorityMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 数据授权审核
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityResponse auditMetadataAuthority(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuditMetadataAuthorityMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取数据授权申请列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetadataAuthorityListResponse getMetadataAuthorityList(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMetadataAuthorityListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 组织身份入网申请
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.SimpleResponse applyIdentityJoin(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getApplyIdentityJoinMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 注销准入网络
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.SimpleResponse revokeIdentityJoin(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRevokeIdentityJoinMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询自己组织的identity信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse getNodeIdentity(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetNodeIdentityMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询全网组织的身份信息列表(已入网的)
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse getIdentityList(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetIdentityListMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AuthServiceFutureStub extends io.grpc.stub.AbstractFutureStub<AuthServiceFutureStub> {
    private AuthServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 数据授权申请
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityResponse> applyMetadataAuthority(
        com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getApplyMetadataAuthorityMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 数据授权审核
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityResponse> auditMetadataAuthority(
        com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuditMetadataAuthorityMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取数据授权申请列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetadataAuthorityListResponse> getMetadataAuthorityList(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMetadataAuthorityListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 组织身份入网申请
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.SimpleResponse> applyIdentityJoin(
        com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getApplyIdentityJoinMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 注销准入网络
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.SimpleResponse> revokeIdentityJoin(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRevokeIdentityJoinMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询自己组织的identity信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse> getNodeIdentity(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetNodeIdentityMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询全网组织的身份信息列表(已入网的)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse> getIdentityList(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetIdentityListMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_APPLY_METADATA_AUTHORITY = 0;
  private static final int METHODID_AUDIT_METADATA_AUTHORITY = 1;
  private static final int METHODID_GET_METADATA_AUTHORITY_LIST = 2;
  private static final int METHODID_APPLY_IDENTITY_JOIN = 3;
  private static final int METHODID_REVOKE_IDENTITY_JOIN = 4;
  private static final int METHODID_GET_NODE_IDENTITY = 5;
  private static final int METHODID_GET_IDENTITY_LIST = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AuthServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AuthServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_APPLY_METADATA_AUTHORITY:
          serviceImpl.applyMetadataAuthority((com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetadataAuthorityResponse>) responseObserver);
          break;
        case METHODID_AUDIT_METADATA_AUTHORITY:
          serviceImpl.auditMetadataAuthority((com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetadataAuthorityResponse>) responseObserver);
          break;
        case METHODID_GET_METADATA_AUTHORITY_LIST:
          serviceImpl.getMetadataAuthorityList((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetadataAuthorityListResponse>) responseObserver);
          break;
        case METHODID_APPLY_IDENTITY_JOIN:
          serviceImpl.applyIdentityJoin((com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse>) responseObserver);
          break;
        case METHODID_REVOKE_IDENTITY_JOIN:
          serviceImpl.revokeIdentityJoin((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse>) responseObserver);
          break;
        case METHODID_GET_NODE_IDENTITY:
          serviceImpl.getNodeIdentity((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse>) responseObserver);
          break;
        case METHODID_GET_IDENTITY_LIST:
          serviceImpl.getIdentityList((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse>) responseObserver);
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

  private static abstract class AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AuthServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AuthService");
    }
  }

  private static final class AuthServiceFileDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier {
    AuthServiceFileDescriptorSupplier() {}
  }

  private static final class AuthServiceMethodDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AuthServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (AuthServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthServiceFileDescriptorSupplier())
              .addMethod(getApplyMetadataAuthorityMethod())
              .addMethod(getAuditMetadataAuthorityMethod())
              .addMethod(getGetMetadataAuthorityListMethod())
              .addMethod(getApplyIdentityJoinMethod())
              .addMethod(getRevokeIdentityJoinMethod())
              .addMethod(getGetNodeIdentityMethod())
              .addMethod(getGetIdentityListMethod())
              .build();
        }
      }
    }
    return result;
  }
}
