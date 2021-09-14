package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ## 调度服务 - 系统状态 接口
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: lib/api/sys_rpc_api.proto")
public final class YarnServiceGrpc {

  private YarnServiceGrpc() {}

  public static final String SERVICE_NAME = "rpcapi.YarnService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetNodeInfoResponse> getGetNodeInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetNodeInfo",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetNodeInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetNodeInfoResponse> getGetNodeInfoMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetNodeInfoResponse> getGetNodeInfoMethod;
    if ((getGetNodeInfoMethod = YarnServiceGrpc.getGetNodeInfoMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getGetNodeInfoMethod = YarnServiceGrpc.getGetNodeInfoMethod) == null) {
          YarnServiceGrpc.getGetNodeInfoMethod = getGetNodeInfoMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetNodeInfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetNodeInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetNodeInfoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("GetNodeInfo"))
              .build();
        }
      }
    }
    return getGetNodeInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersResponse> getGetRegisteredPeersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRegisteredPeers",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersResponse> getGetRegisteredPeersMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersResponse> getGetRegisteredPeersMethod;
    if ((getGetRegisteredPeersMethod = YarnServiceGrpc.getGetRegisteredPeersMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getGetRegisteredPeersMethod = YarnServiceGrpc.getGetRegisteredPeersMethod) == null) {
          YarnServiceGrpc.getGetRegisteredPeersMethod = getGetRegisteredPeersMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRegisteredPeers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("GetRegisteredPeers"))
              .build();
        }
      }
    }
    return getGetRegisteredPeersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse> getSetSeedNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetSeedNode",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse> getSetSeedNodeMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse> getSetSeedNodeMethod;
    if ((getSetSeedNodeMethod = YarnServiceGrpc.getSetSeedNodeMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getSetSeedNodeMethod = YarnServiceGrpc.getSetSeedNodeMethod) == null) {
          YarnServiceGrpc.getSetSeedNodeMethod = getSetSeedNodeMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetSeedNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("SetSeedNode"))
              .build();
        }
      }
    }
    return getSetSeedNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateSeedNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse> getUpdateSeedNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateSeedNode",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateSeedNodeRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateSeedNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse> getUpdateSeedNodeMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateSeedNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse> getUpdateSeedNodeMethod;
    if ((getUpdateSeedNodeMethod = YarnServiceGrpc.getUpdateSeedNodeMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getUpdateSeedNodeMethod = YarnServiceGrpc.getUpdateSeedNodeMethod) == null) {
          YarnServiceGrpc.getUpdateSeedNodeMethod = getUpdateSeedNodeMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateSeedNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateSeedNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateSeedNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("UpdateSeedNode"))
              .build();
        }
      }
    }
    return getUpdateSeedNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getDeleteSeedNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteSeedNode",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.SimpleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getDeleteSeedNodeMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse> getDeleteSeedNodeMethod;
    if ((getDeleteSeedNodeMethod = YarnServiceGrpc.getDeleteSeedNodeMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getDeleteSeedNodeMethod = YarnServiceGrpc.getDeleteSeedNodeMethod) == null) {
          YarnServiceGrpc.getDeleteSeedNodeMethod = getDeleteSeedNodeMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteSeedNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.SimpleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("DeleteSeedNode"))
              .build();
        }
      }
    }
    return getDeleteSeedNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetSeedNodeListResponse> getGetSeedNodeListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSeedNodeList",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetSeedNodeListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetSeedNodeListResponse> getGetSeedNodeListMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetSeedNodeListResponse> getGetSeedNodeListMethod;
    if ((getGetSeedNodeListMethod = YarnServiceGrpc.getGetSeedNodeListMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getGetSeedNodeListMethod = YarnServiceGrpc.getGetSeedNodeListMethod) == null) {
          YarnServiceGrpc.getGetSeedNodeListMethod = getGetSeedNodeListMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetSeedNodeListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSeedNodeList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetSeedNodeListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("GetSeedNodeList"))
              .build();
        }
      }
    }
    return getGetSeedNodeListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse> getSetDataNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetDataNode",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse> getSetDataNodeMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse> getSetDataNodeMethod;
    if ((getSetDataNodeMethod = YarnServiceGrpc.getSetDataNodeMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getSetDataNodeMethod = YarnServiceGrpc.getSetDataNodeMethod) == null) {
          YarnServiceGrpc.getSetDataNodeMethod = getSetDataNodeMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetDataNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("SetDataNode"))
              .build();
        }
      }
    }
    return getSetDataNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateDataNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse> getUpdateDataNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateDataNode",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateDataNodeRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateDataNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse> getUpdateDataNodeMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateDataNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse> getUpdateDataNodeMethod;
    if ((getUpdateDataNodeMethod = YarnServiceGrpc.getUpdateDataNodeMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getUpdateDataNodeMethod = YarnServiceGrpc.getUpdateDataNodeMethod) == null) {
          YarnServiceGrpc.getUpdateDataNodeMethod = getUpdateDataNodeMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateDataNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateDataNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateDataNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("UpdateDataNode"))
              .build();
        }
      }
    }
    return getUpdateDataNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getDeleteDataNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteDataNode",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.SimpleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getDeleteDataNodeMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse> getDeleteDataNodeMethod;
    if ((getDeleteDataNodeMethod = YarnServiceGrpc.getDeleteDataNodeMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getDeleteDataNodeMethod = YarnServiceGrpc.getDeleteDataNodeMethod) == null) {
          YarnServiceGrpc.getDeleteDataNodeMethod = getDeleteDataNodeMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteDataNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.SimpleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("DeleteDataNode"))
              .build();
        }
      }
    }
    return getDeleteDataNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse> getGetDataNodeListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDataNodeList",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse> getGetDataNodeListMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse> getGetDataNodeListMethod;
    if ((getGetDataNodeListMethod = YarnServiceGrpc.getGetDataNodeListMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getGetDataNodeListMethod = YarnServiceGrpc.getGetDataNodeListMethod) == null) {
          YarnServiceGrpc.getGetDataNodeListMethod = getGetDataNodeListMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDataNodeList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("GetDataNodeList"))
              .build();
        }
      }
    }
    return getGetDataNodeListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse> getSetJobNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetJobNode",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse> getSetJobNodeMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse> getSetJobNodeMethod;
    if ((getSetJobNodeMethod = YarnServiceGrpc.getSetJobNodeMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getSetJobNodeMethod = YarnServiceGrpc.getSetJobNodeMethod) == null) {
          YarnServiceGrpc.getSetJobNodeMethod = getSetJobNodeMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetJobNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("SetJobNode"))
              .build();
        }
      }
    }
    return getSetJobNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateJobNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse> getUpdateJobNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateJobNode",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateJobNodeRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateJobNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse> getUpdateJobNodeMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateJobNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse> getUpdateJobNodeMethod;
    if ((getUpdateJobNodeMethod = YarnServiceGrpc.getUpdateJobNodeMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getUpdateJobNodeMethod = YarnServiceGrpc.getUpdateJobNodeMethod) == null) {
          YarnServiceGrpc.getUpdateJobNodeMethod = getUpdateJobNodeMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateJobNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateJobNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateJobNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("UpdateJobNode"))
              .build();
        }
      }
    }
    return getUpdateJobNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getDeleteJobNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteJobNode",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.SimpleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getDeleteJobNodeMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse> getDeleteJobNodeMethod;
    if ((getDeleteJobNodeMethod = YarnServiceGrpc.getDeleteJobNodeMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getDeleteJobNodeMethod = YarnServiceGrpc.getDeleteJobNodeMethod) == null) {
          YarnServiceGrpc.getDeleteJobNodeMethod = getDeleteJobNodeMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteJobNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.SimpleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("DeleteJobNode"))
              .build();
        }
      }
    }
    return getDeleteJobNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse> getGetJobNodeListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetJobNodeList",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse> getGetJobNodeListMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse> getGetJobNodeListMethod;
    if ((getGetJobNodeListMethod = YarnServiceGrpc.getGetJobNodeListMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getGetJobNodeListMethod = YarnServiceGrpc.getGetJobNodeListMethod) == null) {
          YarnServiceGrpc.getGetJobNodeListMethod = getGetJobNodeListMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetJobNodeList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("GetJobNodeList"))
              .build();
        }
      }
    }
    return getGetJobNodeListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskEventRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getReportTaskEventMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReportTaskEvent",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskEventRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.SimpleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskEventRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getReportTaskEventMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskEventRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse> getReportTaskEventMethod;
    if ((getReportTaskEventMethod = YarnServiceGrpc.getReportTaskEventMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getReportTaskEventMethod = YarnServiceGrpc.getReportTaskEventMethod) == null) {
          YarnServiceGrpc.getReportTaskEventMethod = getReportTaskEventMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskEventRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReportTaskEvent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskEventRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.SimpleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("ReportTaskEvent"))
              .build();
        }
      }
    }
    return getReportTaskEventMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResourceExpenseRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getReportTaskResourceExpenseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReportTaskResourceExpense",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResourceExpenseRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.SimpleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResourceExpenseRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getReportTaskResourceExpenseMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResourceExpenseRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse> getReportTaskResourceExpenseMethod;
    if ((getReportTaskResourceExpenseMethod = YarnServiceGrpc.getReportTaskResourceExpenseMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getReportTaskResourceExpenseMethod = YarnServiceGrpc.getReportTaskResourceExpenseMethod) == null) {
          YarnServiceGrpc.getReportTaskResourceExpenseMethod = getReportTaskResourceExpenseMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResourceExpenseRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReportTaskResourceExpense"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResourceExpenseRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.SimpleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("ReportTaskResourceExpense"))
              .build();
        }
      }
    }
    return getReportTaskResourceExpenseMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportUpFileSummaryRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getReportUpFileSummaryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReportUpFileSummary",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportUpFileSummaryRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.SimpleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportUpFileSummaryRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getReportUpFileSummaryMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportUpFileSummaryRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse> getReportUpFileSummaryMethod;
    if ((getReportUpFileSummaryMethod = YarnServiceGrpc.getReportUpFileSummaryMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getReportUpFileSummaryMethod = YarnServiceGrpc.getReportUpFileSummaryMethod) == null) {
          YarnServiceGrpc.getReportUpFileSummaryMethod = getReportUpFileSummaryMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportUpFileSummaryRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReportUpFileSummary"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportUpFileSummaryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.SimpleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("ReportUpFileSummary"))
              .build();
        }
      }
    }
    return getReportUpFileSummaryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResultFileSummaryRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getReportTaskResultFileSummaryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReportTaskResultFileSummary",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResultFileSummaryRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.SimpleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResultFileSummaryRequest,
      com.platon.rosettanet.admin.grpc.service.SimpleResponse> getReportTaskResultFileSummaryMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResultFileSummaryRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse> getReportTaskResultFileSummaryMethod;
    if ((getReportTaskResultFileSummaryMethod = YarnServiceGrpc.getReportTaskResultFileSummaryMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getReportTaskResultFileSummaryMethod = YarnServiceGrpc.getReportTaskResultFileSummaryMethod) == null) {
          YarnServiceGrpc.getReportTaskResultFileSummaryMethod = getReportTaskResultFileSummaryMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResultFileSummaryRequest, com.platon.rosettanet.admin.grpc.service.SimpleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReportTaskResultFileSummary"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResultFileSummaryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.SimpleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("ReportTaskResultFileSummary"))
              .build();
        }
      }
    }
    return getReportTaskResultFileSummaryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeResponse> getQueryAvailableDataNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryAvailableDataNode",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeResponse> getQueryAvailableDataNodeMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeResponse> getQueryAvailableDataNodeMethod;
    if ((getQueryAvailableDataNodeMethod = YarnServiceGrpc.getQueryAvailableDataNodeMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getQueryAvailableDataNodeMethod = YarnServiceGrpc.getQueryAvailableDataNodeMethod) == null) {
          YarnServiceGrpc.getQueryAvailableDataNodeMethod = getQueryAvailableDataNodeMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryAvailableDataNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("QueryAvailableDataNode"))
              .build();
        }
      }
    }
    return getQueryAvailableDataNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionResponse> getQueryFilePositionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryFilePosition",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionResponse> getQueryFilePositionMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionResponse> getQueryFilePositionMethod;
    if ((getQueryFilePositionMethod = YarnServiceGrpc.getQueryFilePositionMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getQueryFilePositionMethod = YarnServiceGrpc.getQueryFilePositionMethod) == null) {
          YarnServiceGrpc.getQueryFilePositionMethod = getQueryFilePositionMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryFilePosition"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("QueryFilePosition"))
              .build();
        }
      }
    }
    return getQueryFilePositionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryResponse> getGetTaskResultFileSummaryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTaskResultFileSummary",
      requestType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryRequest,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryResponse> getGetTaskResultFileSummaryMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryResponse> getGetTaskResultFileSummaryMethod;
    if ((getGetTaskResultFileSummaryMethod = YarnServiceGrpc.getGetTaskResultFileSummaryMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getGetTaskResultFileSummaryMethod = YarnServiceGrpc.getGetTaskResultFileSummaryMethod) == null) {
          YarnServiceGrpc.getGetTaskResultFileSummaryMethod = getGetTaskResultFileSummaryMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryRequest, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTaskResultFileSummary"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("GetTaskResultFileSummary"))
              .build();
        }
      }
    }
    return getGetTaskResultFileSummaryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryListResponse> getGetTaskResultFileSummaryListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTaskResultFileSummaryList",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryListResponse> getGetTaskResultFileSummaryListMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryListResponse> getGetTaskResultFileSummaryListMethod;
    if ((getGetTaskResultFileSummaryListMethod = YarnServiceGrpc.getGetTaskResultFileSummaryListMethod) == null) {
      synchronized (YarnServiceGrpc.class) {
        if ((getGetTaskResultFileSummaryListMethod = YarnServiceGrpc.getGetTaskResultFileSummaryListMethod) == null) {
          YarnServiceGrpc.getGetTaskResultFileSummaryListMethod = getGetTaskResultFileSummaryListMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTaskResultFileSummaryList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new YarnServiceMethodDescriptorSupplier("GetTaskResultFileSummaryList"))
              .build();
        }
      }
    }
    return getGetTaskResultFileSummaryListMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static YarnServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<YarnServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<YarnServiceStub>() {
        @java.lang.Override
        public YarnServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new YarnServiceStub(channel, callOptions);
        }
      };
    return YarnServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static YarnServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<YarnServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<YarnServiceBlockingStub>() {
        @java.lang.Override
        public YarnServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new YarnServiceBlockingStub(channel, callOptions);
        }
      };
    return YarnServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static YarnServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<YarnServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<YarnServiceFutureStub>() {
        @java.lang.Override
        public YarnServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new YarnServiceFutureStub(channel, callOptions);
        }
      };
    return YarnServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ## 调度服务 - 系统状态 接口
   * </pre>
   */
  public static abstract class YarnServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Getter YarnNode ...
     * 查看自身调度服务信息
     * </pre>
     */
    public void getNodeInfo(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetNodeInfoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetNodeInfoMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查看自身调度服务的 peer注册信息
     * </pre>
     */
    public void getRegisteredPeers(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRegisteredPeersMethod(), responseObserver);
    }

    /**
     * <pre>
     * about seed
     * 新增种子节点信息
     * </pre>
     */
    public void setSeedNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetSeedNodeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 修改种子节点信息
     * </pre>
     */
    public void updateSeedNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateSeedNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateSeedNodeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 删除种子节点信息
     * </pre>
     */
    public void deleteSeedNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteSeedNodeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查询种子节点列表
     * </pre>
     */
    public void getSeedNodeList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetSeedNodeListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSeedNodeListMethod(), responseObserver);
    }

    /**
     * <pre>
     * about dataNode
     * 新增数据服务信息
     * </pre>
     */
    public void setDataNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetDataNodeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 修改数据服务信息
     * </pre>
     */
    public void updateDataNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateDataNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateDataNodeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 删除数据服务信息
     * </pre>
     */
    public void deleteDataNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteDataNodeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查询数据服务列表
     * </pre>
     */
    public void getDataNodeList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDataNodeListMethod(), responseObserver);
    }

    /**
     * <pre>
     * about jobNode
     * 新增计算服务信息
     * </pre>
     */
    public void setJobNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetJobNodeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 修改计算服务信息
     * </pre>
     */
    public void updateJobNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateJobNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateJobNodeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 删除计算服务信息
     * </pre>
     */
    public void deleteJobNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteJobNodeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查询计算服务列表
     * </pre>
     */
    public void getJobNodeList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetJobNodeListMethod(), responseObserver);
    }

    /**
     * <pre>
     * about report
     * 数据/计算服务 上报任务事件
     * </pre>
     */
    public void reportTaskEvent(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskEventRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReportTaskEventMethod(), responseObserver);
    }

    /**
     * <pre>
     * 数据/计算服务 上报资源使用实况
     * </pre>
     */
    public void reportTaskResourceExpense(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResourceExpenseRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReportTaskResourceExpenseMethod(), responseObserver);
    }

    /**
     * <pre>
     * 上报 成功上传的原始文件Id
     * </pre>
     */
    public void reportUpFileSummary(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportUpFileSummaryRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReportUpFileSummaryMethod(), responseObserver);
    }

    /**
     * <pre>
     * 上报 任务结果文件摘要
     * </pre>
     */
    public void reportTaskResultFileSummary(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResultFileSummaryRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReportTaskResultFileSummaryMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查询可用数据服务资源目标 ip:port 信息
     * </pre>
     */
    public void queryAvailableDataNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryAvailableDataNodeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查询需要下载的目标原始文件所在的 数据服务信息和文件的完整相对路径
     * </pre>
     */
    public void queryFilePosition(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryFilePositionMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查询 任务结果文件摘要
     * </pre>
     */
    public void getTaskResultFileSummary(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTaskResultFileSummaryMethod(), responseObserver);
    }

    /**
     * <pre>
     * 查询当前组织参与的所有任务结果文件摘要
     * </pre>
     */
    public void getTaskResultFileSummaryList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTaskResultFileSummaryListMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetNodeInfoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetNodeInfoResponse>(
                  this, METHODID_GET_NODE_INFO)))
          .addMethod(
            getGetRegisteredPeersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersRequest,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersResponse>(
                  this, METHODID_GET_REGISTERED_PEERS)))
          .addMethod(
            getSetSeedNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeRequest,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse>(
                  this, METHODID_SET_SEED_NODE)))
          .addMethod(
            getUpdateSeedNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateSeedNodeRequest,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse>(
                  this, METHODID_UPDATE_SEED_NODE)))
          .addMethod(
            getDeleteSeedNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest,
                com.platon.rosettanet.admin.grpc.service.SimpleResponse>(
                  this, METHODID_DELETE_SEED_NODE)))
          .addMethod(
            getGetSeedNodeListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetSeedNodeListResponse>(
                  this, METHODID_GET_SEED_NODE_LIST)))
          .addMethod(
            getSetDataNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeRequest,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse>(
                  this, METHODID_SET_DATA_NODE)))
          .addMethod(
            getUpdateDataNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateDataNodeRequest,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse>(
                  this, METHODID_UPDATE_DATA_NODE)))
          .addMethod(
            getDeleteDataNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest,
                com.platon.rosettanet.admin.grpc.service.SimpleResponse>(
                  this, METHODID_DELETE_DATA_NODE)))
          .addMethod(
            getGetDataNodeListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse>(
                  this, METHODID_GET_DATA_NODE_LIST)))
          .addMethod(
            getSetJobNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeRequest,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse>(
                  this, METHODID_SET_JOB_NODE)))
          .addMethod(
            getUpdateJobNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateJobNodeRequest,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse>(
                  this, METHODID_UPDATE_JOB_NODE)))
          .addMethod(
            getDeleteJobNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest,
                com.platon.rosettanet.admin.grpc.service.SimpleResponse>(
                  this, METHODID_DELETE_JOB_NODE)))
          .addMethod(
            getGetJobNodeListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse>(
                  this, METHODID_GET_JOB_NODE_LIST)))
          .addMethod(
            getReportTaskEventMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskEventRequest,
                com.platon.rosettanet.admin.grpc.service.SimpleResponse>(
                  this, METHODID_REPORT_TASK_EVENT)))
          .addMethod(
            getReportTaskResourceExpenseMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResourceExpenseRequest,
                com.platon.rosettanet.admin.grpc.service.SimpleResponse>(
                  this, METHODID_REPORT_TASK_RESOURCE_EXPENSE)))
          .addMethod(
            getReportUpFileSummaryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportUpFileSummaryRequest,
                com.platon.rosettanet.admin.grpc.service.SimpleResponse>(
                  this, METHODID_REPORT_UP_FILE_SUMMARY)))
          .addMethod(
            getReportTaskResultFileSummaryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResultFileSummaryRequest,
                com.platon.rosettanet.admin.grpc.service.SimpleResponse>(
                  this, METHODID_REPORT_TASK_RESULT_FILE_SUMMARY)))
          .addMethod(
            getQueryAvailableDataNodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeRequest,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeResponse>(
                  this, METHODID_QUERY_AVAILABLE_DATA_NODE)))
          .addMethod(
            getQueryFilePositionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionRequest,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionResponse>(
                  this, METHODID_QUERY_FILE_POSITION)))
          .addMethod(
            getGetTaskResultFileSummaryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryRequest,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryResponse>(
                  this, METHODID_GET_TASK_RESULT_FILE_SUMMARY)))
          .addMethod(
            getGetTaskResultFileSummaryListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryListResponse>(
                  this, METHODID_GET_TASK_RESULT_FILE_SUMMARY_LIST)))
          .build();
    }
  }

  /**
   * <pre>
   * ## 调度服务 - 系统状态 接口
   * </pre>
   */
  public static final class YarnServiceStub extends io.grpc.stub.AbstractAsyncStub<YarnServiceStub> {
    private YarnServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YarnServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new YarnServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Getter YarnNode ...
     * 查看自身调度服务信息
     * </pre>
     */
    public void getNodeInfo(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetNodeInfoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetNodeInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查看自身调度服务的 peer注册信息
     * </pre>
     */
    public void getRegisteredPeers(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRegisteredPeersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * about seed
     * 新增种子节点信息
     * </pre>
     */
    public void setSeedNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetSeedNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 修改种子节点信息
     * </pre>
     */
    public void updateSeedNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateSeedNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateSeedNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 删除种子节点信息
     * </pre>
     */
    public void deleteSeedNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteSeedNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询种子节点列表
     * </pre>
     */
    public void getSeedNodeList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetSeedNodeListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSeedNodeListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * about dataNode
     * 新增数据服务信息
     * </pre>
     */
    public void setDataNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetDataNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 修改数据服务信息
     * </pre>
     */
    public void updateDataNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateDataNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateDataNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 删除数据服务信息
     * </pre>
     */
    public void deleteDataNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteDataNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询数据服务列表
     * </pre>
     */
    public void getDataNodeList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDataNodeListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * about jobNode
     * 新增计算服务信息
     * </pre>
     */
    public void setJobNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetJobNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 修改计算服务信息
     * </pre>
     */
    public void updateJobNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateJobNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateJobNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 删除计算服务信息
     * </pre>
     */
    public void deleteJobNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteJobNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询计算服务列表
     * </pre>
     */
    public void getJobNodeList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetJobNodeListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * about report
     * 数据/计算服务 上报任务事件
     * </pre>
     */
    public void reportTaskEvent(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskEventRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReportTaskEventMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 数据/计算服务 上报资源使用实况
     * </pre>
     */
    public void reportTaskResourceExpense(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResourceExpenseRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReportTaskResourceExpenseMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 上报 成功上传的原始文件Id
     * </pre>
     */
    public void reportUpFileSummary(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportUpFileSummaryRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReportUpFileSummaryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 上报 任务结果文件摘要
     * </pre>
     */
    public void reportTaskResultFileSummary(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResultFileSummaryRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReportTaskResultFileSummaryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询可用数据服务资源目标 ip:port 信息
     * </pre>
     */
    public void queryAvailableDataNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryAvailableDataNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询需要下载的目标原始文件所在的 数据服务信息和文件的完整相对路径
     * </pre>
     */
    public void queryFilePosition(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryFilePositionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询 任务结果文件摘要
     * </pre>
     */
    public void getTaskResultFileSummary(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTaskResultFileSummaryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 查询当前组织参与的所有任务结果文件摘要
     * </pre>
     */
    public void getTaskResultFileSummaryList(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTaskResultFileSummaryListMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * ## 调度服务 - 系统状态 接口
   * </pre>
   */
  public static final class YarnServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<YarnServiceBlockingStub> {
    private YarnServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YarnServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new YarnServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Getter YarnNode ...
     * 查看自身调度服务信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetNodeInfoResponse getNodeInfo(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetNodeInfoMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查看自身调度服务的 peer注册信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersResponse getRegisteredPeers(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRegisteredPeersMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * about seed
     * 新增种子节点信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse setSeedNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetSeedNodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 修改种子节点信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse updateSeedNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateSeedNodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateSeedNodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 删除种子节点信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.SimpleResponse deleteSeedNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteSeedNodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询种子节点列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetSeedNodeListResponse getSeedNodeList(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSeedNodeListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * about dataNode
     * 新增数据服务信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse setDataNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetDataNodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 修改数据服务信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse updateDataNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateDataNodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateDataNodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 删除数据服务信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.SimpleResponse deleteDataNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteDataNodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询数据服务列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse getDataNodeList(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDataNodeListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * about jobNode
     * 新增计算服务信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse setJobNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetJobNodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 修改计算服务信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse updateJobNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateJobNodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateJobNodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 删除计算服务信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.SimpleResponse deleteJobNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteJobNodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询计算服务列表
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse getJobNodeList(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetJobNodeListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * about report
     * 数据/计算服务 上报任务事件
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.SimpleResponse reportTaskEvent(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskEventRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReportTaskEventMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 数据/计算服务 上报资源使用实况
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.SimpleResponse reportTaskResourceExpense(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResourceExpenseRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReportTaskResourceExpenseMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 上报 成功上传的原始文件Id
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.SimpleResponse reportUpFileSummary(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportUpFileSummaryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReportUpFileSummaryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 上报 任务结果文件摘要
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.SimpleResponse reportTaskResultFileSummary(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResultFileSummaryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReportTaskResultFileSummaryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询可用数据服务资源目标 ip:port 信息
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeResponse queryAvailableDataNode(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryAvailableDataNodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询需要下载的目标原始文件所在的 数据服务信息和文件的完整相对路径
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionResponse queryFilePosition(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryFilePositionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询 任务结果文件摘要
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryResponse getTaskResultFileSummary(com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTaskResultFileSummaryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 查询当前组织参与的所有任务结果文件摘要
     * </pre>
     */
    public com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryListResponse getTaskResultFileSummaryList(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTaskResultFileSummaryListMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * ## 调度服务 - 系统状态 接口
   * </pre>
   */
  public static final class YarnServiceFutureStub extends io.grpc.stub.AbstractFutureStub<YarnServiceFutureStub> {
    private YarnServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected YarnServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new YarnServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Getter YarnNode ...
     * 查看自身调度服务信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetNodeInfoResponse> getNodeInfo(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetNodeInfoMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查看自身调度服务的 peer注册信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersResponse> getRegisteredPeers(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRegisteredPeersMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * about seed
     * 新增种子节点信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse> setSeedNode(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetSeedNodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 修改种子节点信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse> updateSeedNode(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateSeedNodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateSeedNodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 删除种子节点信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.SimpleResponse> deleteSeedNode(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteSeedNodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询种子节点列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetSeedNodeListResponse> getSeedNodeList(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSeedNodeListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * about dataNode
     * 新增数据服务信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse> setDataNode(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetDataNodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 修改数据服务信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse> updateDataNode(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateDataNodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateDataNodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 删除数据服务信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.SimpleResponse> deleteDataNode(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteDataNodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询数据服务列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse> getDataNodeList(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDataNodeListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * about jobNode
     * 新增计算服务信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse> setJobNode(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetJobNodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 修改计算服务信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse> updateJobNode(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateJobNodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateJobNodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 删除计算服务信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.SimpleResponse> deleteJobNode(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteJobNodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询计算服务列表
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse> getJobNodeList(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetJobNodeListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * about report
     * 数据/计算服务 上报任务事件
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.SimpleResponse> reportTaskEvent(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskEventRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReportTaskEventMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 数据/计算服务 上报资源使用实况
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.SimpleResponse> reportTaskResourceExpense(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResourceExpenseRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReportTaskResourceExpenseMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 上报 成功上传的原始文件Id
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.SimpleResponse> reportUpFileSummary(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportUpFileSummaryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReportUpFileSummaryMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 上报 任务结果文件摘要
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.SimpleResponse> reportTaskResultFileSummary(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResultFileSummaryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReportTaskResultFileSummaryMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询可用数据服务资源目标 ip:port 信息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeResponse> queryAvailableDataNode(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryAvailableDataNodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询需要下载的目标原始文件所在的 数据服务信息和文件的完整相对路径
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionResponse> queryFilePosition(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryFilePositionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询 任务结果文件摘要
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryResponse> getTaskResultFileSummary(
        com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTaskResultFileSummaryMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 查询当前组织参与的所有任务结果文件摘要
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryListResponse> getTaskResultFileSummaryList(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTaskResultFileSummaryListMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_NODE_INFO = 0;
  private static final int METHODID_GET_REGISTERED_PEERS = 1;
  private static final int METHODID_SET_SEED_NODE = 2;
  private static final int METHODID_UPDATE_SEED_NODE = 3;
  private static final int METHODID_DELETE_SEED_NODE = 4;
  private static final int METHODID_GET_SEED_NODE_LIST = 5;
  private static final int METHODID_SET_DATA_NODE = 6;
  private static final int METHODID_UPDATE_DATA_NODE = 7;
  private static final int METHODID_DELETE_DATA_NODE = 8;
  private static final int METHODID_GET_DATA_NODE_LIST = 9;
  private static final int METHODID_SET_JOB_NODE = 10;
  private static final int METHODID_UPDATE_JOB_NODE = 11;
  private static final int METHODID_DELETE_JOB_NODE = 12;
  private static final int METHODID_GET_JOB_NODE_LIST = 13;
  private static final int METHODID_REPORT_TASK_EVENT = 14;
  private static final int METHODID_REPORT_TASK_RESOURCE_EXPENSE = 15;
  private static final int METHODID_REPORT_UP_FILE_SUMMARY = 16;
  private static final int METHODID_REPORT_TASK_RESULT_FILE_SUMMARY = 17;
  private static final int METHODID_QUERY_AVAILABLE_DATA_NODE = 18;
  private static final int METHODID_QUERY_FILE_POSITION = 19;
  private static final int METHODID_GET_TASK_RESULT_FILE_SUMMARY = 20;
  private static final int METHODID_GET_TASK_RESULT_FILE_SUMMARY_LIST = 21;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final YarnServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(YarnServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_NODE_INFO:
          serviceImpl.getNodeInfo((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetNodeInfoResponse>) responseObserver);
          break;
        case METHODID_GET_REGISTERED_PEERS:
          serviceImpl.getRegisteredPeers((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredPeersResponse>) responseObserver);
          break;
        case METHODID_SET_SEED_NODE:
          serviceImpl.setSeedNode((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse>) responseObserver);
          break;
        case METHODID_UPDATE_SEED_NODE:
          serviceImpl.updateSeedNode((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateSeedNodeRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetSeedNodeResponse>) responseObserver);
          break;
        case METHODID_DELETE_SEED_NODE:
          serviceImpl.deleteSeedNode((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse>) responseObserver);
          break;
        case METHODID_GET_SEED_NODE_LIST:
          serviceImpl.getSeedNodeList((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetSeedNodeListResponse>) responseObserver);
          break;
        case METHODID_SET_DATA_NODE:
          serviceImpl.setDataNode((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse>) responseObserver);
          break;
        case METHODID_UPDATE_DATA_NODE:
          serviceImpl.updateDataNode((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateDataNodeRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetDataNodeResponse>) responseObserver);
          break;
        case METHODID_DELETE_DATA_NODE:
          serviceImpl.deleteDataNode((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse>) responseObserver);
          break;
        case METHODID_GET_DATA_NODE_LIST:
          serviceImpl.getDataNodeList((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse>) responseObserver);
          break;
        case METHODID_SET_JOB_NODE:
          serviceImpl.setJobNode((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse>) responseObserver);
          break;
        case METHODID_UPDATE_JOB_NODE:
          serviceImpl.updateJobNode((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.UpdateJobNodeRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.SetJobNodeResponse>) responseObserver);
          break;
        case METHODID_DELETE_JOB_NODE:
          serviceImpl.deleteJobNode((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.DeleteRegisteredNodeRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse>) responseObserver);
          break;
        case METHODID_GET_JOB_NODE_LIST:
          serviceImpl.getJobNodeList((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetRegisteredNodeListResponse>) responseObserver);
          break;
        case METHODID_REPORT_TASK_EVENT:
          serviceImpl.reportTaskEvent((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskEventRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse>) responseObserver);
          break;
        case METHODID_REPORT_TASK_RESOURCE_EXPENSE:
          serviceImpl.reportTaskResourceExpense((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResourceExpenseRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse>) responseObserver);
          break;
        case METHODID_REPORT_UP_FILE_SUMMARY:
          serviceImpl.reportUpFileSummary((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportUpFileSummaryRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse>) responseObserver);
          break;
        case METHODID_REPORT_TASK_RESULT_FILE_SUMMARY:
          serviceImpl.reportTaskResultFileSummary((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.ReportTaskResultFileSummaryRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.SimpleResponse>) responseObserver);
          break;
        case METHODID_QUERY_AVAILABLE_DATA_NODE:
          serviceImpl.queryAvailableDataNode((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryAvailableDataNodeResponse>) responseObserver);
          break;
        case METHODID_QUERY_FILE_POSITION:
          serviceImpl.queryFilePosition((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.QueryFilePositionResponse>) responseObserver);
          break;
        case METHODID_GET_TASK_RESULT_FILE_SUMMARY:
          serviceImpl.getTaskResultFileSummary((com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryResponse>) responseObserver);
          break;
        case METHODID_GET_TASK_RESULT_FILE_SUMMARY_LIST:
          serviceImpl.getTaskResultFileSummaryList((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.GetTaskResultFileSummaryListResponse>) responseObserver);
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

  private static abstract class YarnServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    YarnServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.platon.rosettanet.admin.grpc.service.YarnRpcMessage.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("YarnService");
    }
  }

  private static final class YarnServiceFileDescriptorSupplier
      extends YarnServiceBaseDescriptorSupplier {
    YarnServiceFileDescriptorSupplier() {}
  }

  private static final class YarnServiceMethodDescriptorSupplier
      extends YarnServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    YarnServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (YarnServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new YarnServiceFileDescriptorSupplier())
              .addMethod(getGetNodeInfoMethod())
              .addMethod(getGetRegisteredPeersMethod())
              .addMethod(getSetSeedNodeMethod())
              .addMethod(getUpdateSeedNodeMethod())
              .addMethod(getDeleteSeedNodeMethod())
              .addMethod(getGetSeedNodeListMethod())
              .addMethod(getSetDataNodeMethod())
              .addMethod(getUpdateDataNodeMethod())
              .addMethod(getDeleteDataNodeMethod())
              .addMethod(getGetDataNodeListMethod())
              .addMethod(getSetJobNodeMethod())
              .addMethod(getUpdateJobNodeMethod())
              .addMethod(getDeleteJobNodeMethod())
              .addMethod(getGetJobNodeListMethod())
              .addMethod(getReportTaskEventMethod())
              .addMethod(getReportTaskResourceExpenseMethod())
              .addMethod(getReportUpFileSummaryMethod())
              .addMethod(getReportTaskResultFileSummaryMethod())
              .addMethod(getQueryAvailableDataNodeMethod())
              .addMethod(getQueryFilePositionMethod())
              .addMethod(getGetTaskResultFileSummaryMethod())
              .addMethod(getGetTaskResultFileSummaryListMethod())
              .build();
        }
      }
    }
    return result;
  }
}
