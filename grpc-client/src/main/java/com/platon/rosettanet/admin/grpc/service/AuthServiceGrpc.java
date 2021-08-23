package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: carrier/api/auth_rpc_api.proto")
public final class AuthServiceGrpc {

  private AuthServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcapi.AuthService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityRequest,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityResponse> getApplyMetaDataAuthorityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ApplyMetaDataAuthority",
      requestType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityRequest,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityResponse> getApplyMetaDataAuthorityMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityRequest, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityResponse> getApplyMetaDataAuthorityMethod;
    if ((getApplyMetaDataAuthorityMethod = AuthServiceGrpc.getApplyMetaDataAuthorityMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getApplyMetaDataAuthorityMethod = AuthServiceGrpc.getApplyMetaDataAuthorityMethod) == null) {
          AuthServiceGrpc.getApplyMetaDataAuthorityMethod = getApplyMetaDataAuthorityMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityRequest, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ApplyMetaDataAuthority"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("ApplyMetaDataAuthority"))
              .build();
        }
      }
    }
    return getApplyMetaDataAuthorityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetaDataAuthorityRequest,
      com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> getAuditMetaDataAuthorityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AuditMetaDataAuthority",
      requestType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetaDataAuthorityRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetaDataAuthorityRequest,
      com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> getAuditMetaDataAuthorityMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetaDataAuthorityRequest, com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> getAuditMetaDataAuthorityMethod;
    if ((getAuditMetaDataAuthorityMethod = AuthServiceGrpc.getAuditMetaDataAuthorityMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getAuditMetaDataAuthorityMethod = AuthServiceGrpc.getAuditMetaDataAuthorityMethod) == null) {
          AuthServiceGrpc.getAuditMetaDataAuthorityMethod = getAuditMetaDataAuthorityMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetaDataAuthorityRequest, com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AuditMetaDataAuthority"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetaDataAuthorityRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("AuditMetaDataAuthority"))
              .build();
        }
      }
    }
    return getAuditMetaDataAuthorityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetaDataAuthorityListResponse> getGetMetaDataAuthorityListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMetaDataAuthorityList",
      requestType = com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams.class,
      responseType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetaDataAuthorityListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetaDataAuthorityListResponse> getGetMetaDataAuthorityListMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetaDataAuthorityListResponse> getGetMetaDataAuthorityListMethod;
    if ((getGetMetaDataAuthorityListMethod = AuthServiceGrpc.getGetMetaDataAuthorityListMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getGetMetaDataAuthorityListMethod = AuthServiceGrpc.getGetMetaDataAuthorityListMethod) == null) {
          AuthServiceGrpc.getGetMetaDataAuthorityListMethod = getGetMetaDataAuthorityListMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetaDataAuthorityListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMetaDataAuthorityList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetaDataAuthorityListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("GetMetaDataAuthorityList"))
              .build();
        }
      }
    }
    return getGetMetaDataAuthorityListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest,
      com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> getApplyIdentityJoinMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ApplyIdentityJoin",
      requestType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest,
      com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> getApplyIdentityJoinMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest, com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> getApplyIdentityJoinMethod;
    if ((getApplyIdentityJoinMethod = AuthServiceGrpc.getApplyIdentityJoinMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getApplyIdentityJoinMethod = AuthServiceGrpc.getApplyIdentityJoinMethod) == null) {
          AuthServiceGrpc.getApplyIdentityJoinMethod = getApplyIdentityJoinMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest, com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ApplyIdentityJoin"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("ApplyIdentityJoin"))
              .build();
        }
      }
    }
    return getApplyIdentityJoinMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> getRevokeIdentityJoinMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RevokeIdentityJoin",
      requestType = com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams.class,
      responseType = com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> getRevokeIdentityJoinMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> getRevokeIdentityJoinMethod;
    if ((getRevokeIdentityJoinMethod = AuthServiceGrpc.getRevokeIdentityJoinMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getRevokeIdentityJoinMethod = AuthServiceGrpc.getRevokeIdentityJoinMethod) == null) {
          AuthServiceGrpc.getRevokeIdentityJoinMethod = getRevokeIdentityJoinMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RevokeIdentityJoin"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("RevokeIdentityJoin"))
              .build();
        }
      }
    }
    return getRevokeIdentityJoinMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse> getGetNodeIdentityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetNodeIdentity",
      requestType = com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams.class,
      responseType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse> getGetNodeIdentityMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse> getGetNodeIdentityMethod;
    if ((getGetNodeIdentityMethod = AuthServiceGrpc.getGetNodeIdentityMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getGetNodeIdentityMethod = AuthServiceGrpc.getGetNodeIdentityMethod) == null) {
          AuthServiceGrpc.getGetNodeIdentityMethod = getGetNodeIdentityMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetNodeIdentity"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("GetNodeIdentity"))
              .build();
        }
      }
    }
    return getGetNodeIdentityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse> getGetIdentityListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetIdentityList",
      requestType = com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams.class,
      responseType = com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
      com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse> getGetIdentityListMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse> getGetIdentityListMethod;
    if ((getGetIdentityListMethod = AuthServiceGrpc.getGetIdentityListMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getGetIdentityListMethod = AuthServiceGrpc.getGetIdentityListMethod) == null) {
          AuthServiceGrpc.getGetIdentityListMethod = getGetIdentityListMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams, com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetIdentityList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams.getDefaultInstance()))
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
    public void applyMetaDataAuthority(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getApplyMetaDataAuthorityMethod(), responseObserver);
    }

    /**
     * <pre>
     * 数据授权审核
     * </pre>
     */
    public void auditMetaDataAuthority(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetaDataAuthorityRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuditMetaDataAuthorityMethod(), responseObserver);
    }

    /**
     * <pre>
     * 获取数据授权申请列表
     * </pre>
     */
    public void getMetaDataAuthorityList(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetaDataAuthorityListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMetaDataAuthorityListMethod(), responseObserver);
    }

    /**
     * <pre>
     * 申请准入网络
     * </pre>
     */
    public void applyIdentityJoin(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getApplyIdentityJoinMethod(), responseObserver);
    }

    /**
     * <pre>
     * 注销准入网络
     * </pre>
     */
    public void revokeIdentityJoin(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRevokeIdentityJoinMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查询自己组织的identity信息
     * </pre>
     */
    public void getNodeIdentity(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetNodeIdentityMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查询全网全部已发布的 身份信息
     * </pre>
     */
    public void getIdentityList(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetIdentityListMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getApplyMetaDataAuthorityMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityRequest,
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityResponse>(
                  this, METHODID_APPLY_META_DATA_AUTHORITY)))
          .addMethod(
            getAuditMetaDataAuthorityMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetaDataAuthorityRequest,
                com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode>(
                  this, METHODID_AUDIT_META_DATA_AUTHORITY)))
          .addMethod(
            getGetMetaDataAuthorityListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetaDataAuthorityListResponse>(
                  this, METHODID_GET_META_DATA_AUTHORITY_LIST)))
          .addMethod(
            getApplyIdentityJoinMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest,
                com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode>(
                  this, METHODID_APPLY_IDENTITY_JOIN)))
          .addMethod(
            getRevokeIdentityJoinMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
                com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode>(
                  this, METHODID_REVOKE_IDENTITY_JOIN)))
          .addMethod(
            getGetNodeIdentityMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
                com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse>(
                  this, METHODID_GET_NODE_IDENTITY)))
          .addMethod(
            getGetIdentityListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams,
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
    public void applyMetaDataAuthority(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getApplyMetaDataAuthorityMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 数据授权审核
     * </pre>
     */
    public void auditMetaDataAuthority(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetaDataAuthorityRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuditMetaDataAuthorityMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 获取数据授权申请列表
     * </pre>
     */
    public void getMetaDataAuthorityList(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetaDataAuthorityListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMetaDataAuthorityListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 申请准入网络
     * </pre>
     */
    public void applyIdentityJoin(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getApplyIdentityJoinMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 注销准入网络
     * </pre>
     */
    public void revokeIdentityJoin(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRevokeIdentityJoinMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询自己组织的identity信息
     * </pre>
     */
    public void getNodeIdentity(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetNodeIdentityMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询全网全部已发布的 身份信息
     * </pre>
     */
    public void getIdentityList(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request,
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
    public com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityResponse applyMetaDataAuthority(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getApplyMetaDataAuthorityMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 数据授权审核
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode auditMetaDataAuthority(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetaDataAuthorityRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuditMetaDataAuthorityMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 获取数据授权申请列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetaDataAuthorityListResponse getMetaDataAuthorityList(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMetaDataAuthorityListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 申请准入网络
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode applyIdentityJoin(com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getApplyIdentityJoinMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 注销准入网络
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode revokeIdentityJoin(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRevokeIdentityJoinMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询自己组织的identity信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse getNodeIdentity(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetNodeIdentityMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询全网全部已发布的 身份信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse getIdentityList(com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request) {
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
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityResponse> applyMetaDataAuthority(
        com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getApplyMetaDataAuthorityMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 数据授权审核
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> auditMetaDataAuthority(
        com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetaDataAuthorityRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuditMetaDataAuthorityMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 获取数据授权申请列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetaDataAuthorityListResponse> getMetaDataAuthorityList(
        com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMetaDataAuthorityListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 申请准入网络
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> applyIdentityJoin(
        com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getApplyIdentityJoinMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 注销准入网络
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode> revokeIdentityJoin(
        com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRevokeIdentityJoinMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询自己组织的identity信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse> getNodeIdentity(
        com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetNodeIdentityMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询全网全部已发布的 身份信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetIdentityListResponse> getIdentityList(
        com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetIdentityListMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_APPLY_META_DATA_AUTHORITY = 0;
  private static final int METHODID_AUDIT_META_DATA_AUTHORITY = 1;
  private static final int METHODID_GET_META_DATA_AUTHORITY_LIST = 2;
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
        case METHODID_APPLY_META_DATA_AUTHORITY:
          serviceImpl.applyMetaDataAuthority((com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyMetaDataAuthorityResponse>) responseObserver);
          break;
        case METHODID_AUDIT_META_DATA_AUTHORITY:
          serviceImpl.auditMetaDataAuthority((com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.AuditMetaDataAuthorityRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode>) responseObserver);
          break;
        case METHODID_GET_META_DATA_AUTHORITY_LIST:
          serviceImpl.getMetaDataAuthorityList((com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetMetaDataAuthorityListResponse>) responseObserver);
          break;
        case METHODID_APPLY_IDENTITY_JOIN:
          serviceImpl.applyIdentityJoin((com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.ApplyIdentityJoinRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode>) responseObserver);
          break;
        case METHODID_REVOKE_IDENTITY_JOIN:
          serviceImpl.revokeIdentityJoin((com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.CommonMessage.SimpleResponseCode>) responseObserver);
          break;
        case METHODID_GET_NODE_IDENTITY:
          serviceImpl.getNodeIdentity((com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.AuthRpcMessage.GetNodeIdentityResponse>) responseObserver);
          break;
        case METHODID_GET_IDENTITY_LIST:
          serviceImpl.getIdentityList((com.platon.rosettanet.admin.grpc.service.CommonMessage.EmptyGetParams) request,
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
              .addMethod(getApplyMetaDataAuthorityMethod())
              .addMethod(getAuditMetaDataAuthorityMethod())
              .addMethod(getGetMetaDataAuthorityListMethod())
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
