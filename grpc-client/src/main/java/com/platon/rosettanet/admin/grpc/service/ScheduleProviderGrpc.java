package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: fighter/schedule_svc.proto")
public final class ScheduleProviderGrpc {

  private ScheduleProviderGrpc() {}

  public static final String SERVICE_NAME = "schedulesvc.ScheduleProvider";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.EventRecordReq,
      com.google.protobuf.Empty> getHandleEventRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandleEventRecord",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.EventRecordReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.EventRecordReq,
      com.google.protobuf.Empty> getHandleEventRecordMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.EventRecordReq, com.google.protobuf.Empty> getHandleEventRecordMethod;
    if ((getHandleEventRecordMethod = ScheduleProviderGrpc.getHandleEventRecordMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getHandleEventRecordMethod = ScheduleProviderGrpc.getHandleEventRecordMethod) == null) {
          ScheduleProviderGrpc.getHandleEventRecordMethod = getHandleEventRecordMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.EventRecordReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandleEventRecord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.EventRecordReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("HandleEventRecord"))
              .build();
        }
      }
    }
    return getHandleEventRecordMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.DataNodeReportReq,
      com.google.protobuf.Empty> getHandleDataNodeReportStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandleDataNodeReportStatus",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.DataNodeReportReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.DataNodeReportReq,
      com.google.protobuf.Empty> getHandleDataNodeReportStatusMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.DataNodeReportReq, com.google.protobuf.Empty> getHandleDataNodeReportStatusMethod;
    if ((getHandleDataNodeReportStatusMethod = ScheduleProviderGrpc.getHandleDataNodeReportStatusMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getHandleDataNodeReportStatusMethod = ScheduleProviderGrpc.getHandleDataNodeReportStatusMethod) == null) {
          ScheduleProviderGrpc.getHandleDataNodeReportStatusMethod = getHandleDataNodeReportStatusMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.DataNodeReportReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandleDataNodeReportStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.DataNodeReportReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("HandleDataNodeReportStatus"))
              .build();
        }
      }
    }
    return getHandleDataNodeReportStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComputeNodeReportReq,
      com.google.protobuf.Empty> getHandleComputeNodeReportStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandleComputeNodeReportStatus",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComputeNodeReportReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComputeNodeReportReq,
      com.google.protobuf.Empty> getHandleComputeNodeReportStatusMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComputeNodeReportReq, com.google.protobuf.Empty> getHandleComputeNodeReportStatusMethod;
    if ((getHandleComputeNodeReportStatusMethod = ScheduleProviderGrpc.getHandleComputeNodeReportStatusMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getHandleComputeNodeReportStatusMethod = ScheduleProviderGrpc.getHandleComputeNodeReportStatusMethod) == null) {
          ScheduleProviderGrpc.getHandleComputeNodeReportStatusMethod = getHandleComputeNodeReportStatusMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComputeNodeReportReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandleComputeNodeReportStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComputeNodeReportReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("HandleComputeNodeReportStatus"))
              .build();
        }
      }
    }
    return getHandleComputeNodeReportStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.TransmitShareDataDoneReq,
      com.google.protobuf.Empty> getHandleTransmitShareDataDoneMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandleTransmitShareDataDone",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.TransmitShareDataDoneReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.TransmitShareDataDoneReq,
      com.google.protobuf.Empty> getHandleTransmitShareDataDoneMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.TransmitShareDataDoneReq, com.google.protobuf.Empty> getHandleTransmitShareDataDoneMethod;
    if ((getHandleTransmitShareDataDoneMethod = ScheduleProviderGrpc.getHandleTransmitShareDataDoneMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getHandleTransmitShareDataDoneMethod = ScheduleProviderGrpc.getHandleTransmitShareDataDoneMethod) == null) {
          ScheduleProviderGrpc.getHandleTransmitShareDataDoneMethod = getHandleTransmitShareDataDoneMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.TransmitShareDataDoneReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandleTransmitShareDataDone"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.TransmitShareDataDoneReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("HandleTransmitShareDataDone"))
              .build();
        }
      }
    }
    return getHandleTransmitShareDataDoneMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq,
      com.google.protobuf.Empty> getPublishDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PublishData",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq,
      com.google.protobuf.Empty> getPublishDataMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq, com.google.protobuf.Empty> getPublishDataMethod;
    if ((getPublishDataMethod = ScheduleProviderGrpc.getPublishDataMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getPublishDataMethod = ScheduleProviderGrpc.getPublishDataMethod) == null) {
          ScheduleProviderGrpc.getPublishDataMethod = getPublishDataMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PublishData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("PublishData"))
              .build();
        }
      }
    }
    return getPublishDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq,
      com.google.protobuf.Empty> getPublishComputeResourceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PublishComputeResource",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq,
      com.google.protobuf.Empty> getPublishComputeResourceMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq, com.google.protobuf.Empty> getPublishComputeResourceMethod;
    if ((getPublishComputeResourceMethod = ScheduleProviderGrpc.getPublishComputeResourceMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getPublishComputeResourceMethod = ScheduleProviderGrpc.getPublishComputeResourceMethod) == null) {
          ScheduleProviderGrpc.getPublishComputeResourceMethod = getPublishComputeResourceMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PublishComputeResource"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("PublishComputeResource"))
              .build();
        }
      }
    }
    return getPublishComputeResourceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq,
      com.google.protobuf.Empty> getPublishAlgoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PublishAlgo",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq,
      com.google.protobuf.Empty> getPublishAlgoMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq, com.google.protobuf.Empty> getPublishAlgoMethod;
    if ((getPublishAlgoMethod = ScheduleProviderGrpc.getPublishAlgoMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getPublishAlgoMethod = ScheduleProviderGrpc.getPublishAlgoMethod) == null) {
          ScheduleProviderGrpc.getPublishAlgoMethod = getPublishAlgoMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PublishAlgo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("PublishAlgo"))
              .build();
        }
      }
    }
    return getPublishAlgoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq,
      com.google.protobuf.Empty> getCallContractMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CallContract",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq,
      com.google.protobuf.Empty> getCallContractMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq, com.google.protobuf.Empty> getCallContractMethod;
    if ((getCallContractMethod = ScheduleProviderGrpc.getCallContractMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getCallContractMethod = ScheduleProviderGrpc.getCallContractMethod) == null) {
          ScheduleProviderGrpc.getCallContractMethod = getCallContractMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CallContract"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("CallContract"))
              .build();
        }
      }
    }
    return getCallContractMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReq,
      com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReply> getFindMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Find",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReq.class,
      responseType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReq,
      com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReply> getFindMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReq, com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReply> getFindMethod;
    if ((getFindMethod = ScheduleProviderGrpc.getFindMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getFindMethod = ScheduleProviderGrpc.getFindMethod) == null) {
          ScheduleProviderGrpc.getFindMethod = getFindMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReq, com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Find"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReply.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("Find"))
              .build();
        }
      }
    }
    return getFindMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq,
      com.google.protobuf.Empty> getPeerLoginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PeerLogin",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq,
      com.google.protobuf.Empty> getPeerLoginMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq, com.google.protobuf.Empty> getPeerLoginMethod;
    if ((getPeerLoginMethod = ScheduleProviderGrpc.getPeerLoginMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getPeerLoginMethod = ScheduleProviderGrpc.getPeerLoginMethod) == null) {
          ScheduleProviderGrpc.getPeerLoginMethod = getPeerLoginMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PeerLogin"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("PeerLogin"))
              .build();
        }
      }
    }
    return getPeerLoginMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq,
      com.google.protobuf.Empty> getPeerLogoutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PeerLogout",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq,
      com.google.protobuf.Empty> getPeerLogoutMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq, com.google.protobuf.Empty> getPeerLogoutMethod;
    if ((getPeerLogoutMethod = ScheduleProviderGrpc.getPeerLogoutMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getPeerLogoutMethod = ScheduleProviderGrpc.getPeerLogoutMethod) == null) {
          ScheduleProviderGrpc.getPeerLogoutMethod = getPeerLogoutMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PeerLogout"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("PeerLogout"))
              .build();
        }
      }
    }
    return getPeerLogoutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq,
      com.google.protobuf.Empty> getPeerPublishDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PeerPublishData",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq,
      com.google.protobuf.Empty> getPeerPublishDataMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq, com.google.protobuf.Empty> getPeerPublishDataMethod;
    if ((getPeerPublishDataMethod = ScheduleProviderGrpc.getPeerPublishDataMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getPeerPublishDataMethod = ScheduleProviderGrpc.getPeerPublishDataMethod) == null) {
          ScheduleProviderGrpc.getPeerPublishDataMethod = getPeerPublishDataMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PeerPublishData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("PeerPublishData"))
              .build();
        }
      }
    }
    return getPeerPublishDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq,
      com.google.protobuf.Empty> getPeerPublishComputeResourceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PeerPublishComputeResource",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq,
      com.google.protobuf.Empty> getPeerPublishComputeResourceMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq, com.google.protobuf.Empty> getPeerPublishComputeResourceMethod;
    if ((getPeerPublishComputeResourceMethod = ScheduleProviderGrpc.getPeerPublishComputeResourceMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getPeerPublishComputeResourceMethod = ScheduleProviderGrpc.getPeerPublishComputeResourceMethod) == null) {
          ScheduleProviderGrpc.getPeerPublishComputeResourceMethod = getPeerPublishComputeResourceMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PeerPublishComputeResource"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("PeerPublishComputeResource"))
              .build();
        }
      }
    }
    return getPeerPublishComputeResourceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq,
      com.google.protobuf.Empty> getPeerPublishAlgoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PeerPublishAlgo",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq,
      com.google.protobuf.Empty> getPeerPublishAlgoMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq, com.google.protobuf.Empty> getPeerPublishAlgoMethod;
    if ((getPeerPublishAlgoMethod = ScheduleProviderGrpc.getPeerPublishAlgoMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getPeerPublishAlgoMethod = ScheduleProviderGrpc.getPeerPublishAlgoMethod) == null) {
          ScheduleProviderGrpc.getPeerPublishAlgoMethod = getPeerPublishAlgoMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PeerPublishAlgo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("PeerPublishAlgo"))
              .build();
        }
      }
    }
    return getPeerPublishAlgoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq,
      com.google.protobuf.Empty> getPeerCallContractMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PeerCallContract",
      requestType = com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq,
      com.google.protobuf.Empty> getPeerCallContractMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq, com.google.protobuf.Empty> getPeerCallContractMethod;
    if ((getPeerCallContractMethod = ScheduleProviderGrpc.getPeerCallContractMethod) == null) {
      synchronized (ScheduleProviderGrpc.class) {
        if ((getPeerCallContractMethod = ScheduleProviderGrpc.getPeerCallContractMethod) == null) {
          ScheduleProviderGrpc.getPeerCallContractMethod = getPeerCallContractMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PeerCallContract"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleProviderMethodDescriptorSupplier("PeerCallContract"))
              .build();
        }
      }
    }
    return getPeerCallContractMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ScheduleProviderStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ScheduleProviderStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ScheduleProviderStub>() {
        @java.lang.Override
        public ScheduleProviderStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ScheduleProviderStub(channel, callOptions);
        }
      };
    return ScheduleProviderStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ScheduleProviderBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ScheduleProviderBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ScheduleProviderBlockingStub>() {
        @java.lang.Override
        public ScheduleProviderBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ScheduleProviderBlockingStub(channel, callOptions);
        }
      };
    return ScheduleProviderBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ScheduleProviderFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ScheduleProviderFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ScheduleProviderFutureStub>() {
        @java.lang.Override
        public ScheduleProviderFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ScheduleProviderFutureStub(channel, callOptions);
        }
      };
    return ScheduleProviderFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ScheduleProviderImplBase implements io.grpc.BindableService {

    /**
     */
    public void handleEventRecord(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.EventRecordReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleEventRecordMethod(), responseObserver);
    }

    /**
     * <pre>
     * 处理源组织内数据节点的消息
     * </pre>
     */
    public void handleDataNodeReportStatus(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.DataNodeReportReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleDataNodeReportStatusMethod(), responseObserver);
    }

    /**
     * <pre>
     * 处理源组织内计算节点的消息
     * </pre>
     */
    public void handleComputeNodeReportStatus(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComputeNodeReportReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleComputeNodeReportStatusMethod(), responseObserver);
    }

    /**
     */
    public void handleTransmitShareDataDone(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.TransmitShareDataDoneReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleTransmitShareDataDoneMethod(), responseObserver);
    }

    /**
     * <pre>
     * 处理客户端的消息
     * </pre>
     */
    public void publishData(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPublishDataMethod(), responseObserver);
    }

    /**
     */
    public void publishComputeResource(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPublishComputeResourceMethod(), responseObserver);
    }

    /**
     */
    public void publishAlgo(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPublishAlgoMethod(), responseObserver);
    }

    /**
     */
    public void callContract(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCallContractMethod(), responseObserver);
    }

    /**
     */
    public void find(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReq request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindMethod(), responseObserver);
    }

    /**
     * <pre>
     *  处理源自其它调度节点的消息
     * </pre>
     */
    public void peerLogin(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPeerLoginMethod(), responseObserver);
    }

    /**
     */
    public void peerLogout(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPeerLogoutMethod(), responseObserver);
    }

    /**
     */
    public void peerPublishData(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPeerPublishDataMethod(), responseObserver);
    }

    /**
     */
    public void peerPublishComputeResource(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPeerPublishComputeResourceMethod(), responseObserver);
    }

    /**
     */
    public void peerPublishAlgo(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPeerPublishAlgoMethod(), responseObserver);
    }

    /**
     */
    public void peerCallContract(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPeerCallContractMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHandleEventRecordMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.EventRecordReq,
                com.google.protobuf.Empty>(
                  this, METHODID_HANDLE_EVENT_RECORD)))
          .addMethod(
            getHandleDataNodeReportStatusMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.DataNodeReportReq,
                com.google.protobuf.Empty>(
                  this, METHODID_HANDLE_DATA_NODE_REPORT_STATUS)))
          .addMethod(
            getHandleComputeNodeReportStatusMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComputeNodeReportReq,
                com.google.protobuf.Empty>(
                  this, METHODID_HANDLE_COMPUTE_NODE_REPORT_STATUS)))
          .addMethod(
            getHandleTransmitShareDataDoneMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.TransmitShareDataDoneReq,
                com.google.protobuf.Empty>(
                  this, METHODID_HANDLE_TRANSMIT_SHARE_DATA_DONE)))
          .addMethod(
            getPublishDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq,
                com.google.protobuf.Empty>(
                  this, METHODID_PUBLISH_DATA)))
          .addMethod(
            getPublishComputeResourceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq,
                com.google.protobuf.Empty>(
                  this, METHODID_PUBLISH_COMPUTE_RESOURCE)))
          .addMethod(
            getPublishAlgoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq,
                com.google.protobuf.Empty>(
                  this, METHODID_PUBLISH_ALGO)))
          .addMethod(
            getCallContractMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq,
                com.google.protobuf.Empty>(
                  this, METHODID_CALL_CONTRACT)))
          .addMethod(
            getFindMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReq,
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReply>(
                  this, METHODID_FIND)))
          .addMethod(
            getPeerLoginMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq,
                com.google.protobuf.Empty>(
                  this, METHODID_PEER_LOGIN)))
          .addMethod(
            getPeerLogoutMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq,
                com.google.protobuf.Empty>(
                  this, METHODID_PEER_LOGOUT)))
          .addMethod(
            getPeerPublishDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq,
                com.google.protobuf.Empty>(
                  this, METHODID_PEER_PUBLISH_DATA)))
          .addMethod(
            getPeerPublishComputeResourceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq,
                com.google.protobuf.Empty>(
                  this, METHODID_PEER_PUBLISH_COMPUTE_RESOURCE)))
          .addMethod(
            getPeerPublishAlgoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq,
                com.google.protobuf.Empty>(
                  this, METHODID_PEER_PUBLISH_ALGO)))
          .addMethod(
            getPeerCallContractMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq,
                com.google.protobuf.Empty>(
                  this, METHODID_PEER_CALL_CONTRACT)))
          .build();
    }
  }

  /**
   */
  public static final class ScheduleProviderStub extends io.grpc.stub.AbstractAsyncStub<ScheduleProviderStub> {
    private ScheduleProviderStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ScheduleProviderStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ScheduleProviderStub(channel, callOptions);
    }

    /**
     */
    public void handleEventRecord(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.EventRecordReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleEventRecordMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 处理源组织内数据节点的消息
     * </pre>
     */
    public void handleDataNodeReportStatus(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.DataNodeReportReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleDataNodeReportStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 处理源组织内计算节点的消息
     * </pre>
     */
    public void handleComputeNodeReportStatus(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComputeNodeReportReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleComputeNodeReportStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void handleTransmitShareDataDone(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.TransmitShareDataDoneReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleTransmitShareDataDoneMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 处理客户端的消息
     * </pre>
     */
    public void publishData(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPublishDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void publishComputeResource(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPublishComputeResourceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void publishAlgo(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPublishAlgoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void callContract(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCallContractMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void find(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReq request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *  处理源自其它调度节点的消息
     * </pre>
     */
    public void peerLogin(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPeerLoginMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void peerLogout(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPeerLogoutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void peerPublishData(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPeerPublishDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void peerPublishComputeResource(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPeerPublishComputeResourceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void peerPublishAlgo(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPeerPublishAlgoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void peerCallContract(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPeerCallContractMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ScheduleProviderBlockingStub extends io.grpc.stub.AbstractBlockingStub<ScheduleProviderBlockingStub> {
    private ScheduleProviderBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ScheduleProviderBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ScheduleProviderBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty handleEventRecord(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.EventRecordReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleEventRecordMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 处理源组织内数据节点的消息
     * </pre>
     */
    public com.google.protobuf.Empty handleDataNodeReportStatus(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.DataNodeReportReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleDataNodeReportStatusMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 处理源组织内计算节点的消息
     * </pre>
     */
    public com.google.protobuf.Empty handleComputeNodeReportStatus(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComputeNodeReportReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleComputeNodeReportStatusMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty handleTransmitShareDataDone(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.TransmitShareDataDoneReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleTransmitShareDataDoneMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 处理客户端的消息
     * </pre>
     */
    public com.google.protobuf.Empty publishData(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPublishDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty publishComputeResource(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPublishComputeResourceMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty publishAlgo(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPublishAlgoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty callContract(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCallContractMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReply find(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *  处理源自其它调度节点的消息
     * </pre>
     */
    public com.google.protobuf.Empty peerLogin(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPeerLoginMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty peerLogout(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPeerLogoutMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty peerPublishData(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPeerPublishDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty peerPublishComputeResource(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPeerPublishComputeResourceMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty peerPublishAlgo(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPeerPublishAlgoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty peerCallContract(com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPeerCallContractMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ScheduleProviderFutureStub extends io.grpc.stub.AbstractFutureStub<ScheduleProviderFutureStub> {
    private ScheduleProviderFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ScheduleProviderFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ScheduleProviderFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> handleEventRecord(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.EventRecordReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleEventRecordMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 处理源组织内数据节点的消息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> handleDataNodeReportStatus(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.DataNodeReportReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleDataNodeReportStatusMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 处理源组织内计算节点的消息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> handleComputeNodeReportStatus(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComputeNodeReportReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleComputeNodeReportStatusMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> handleTransmitShareDataDone(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.TransmitShareDataDoneReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleTransmitShareDataDoneMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 处理客户端的消息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> publishData(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPublishDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> publishComputeResource(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPublishComputeResourceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> publishAlgo(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPublishAlgoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> callContract(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCallContractMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReply> find(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     *  处理源自其它调度节点的消息
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> peerLogin(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPeerLoginMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> peerLogout(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPeerLogoutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> peerPublishData(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPeerPublishDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> peerPublishComputeResource(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPeerPublishComputeResourceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> peerPublishAlgo(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPeerPublishAlgoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> peerCallContract(
        com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPeerCallContractMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HANDLE_EVENT_RECORD = 0;
  private static final int METHODID_HANDLE_DATA_NODE_REPORT_STATUS = 1;
  private static final int METHODID_HANDLE_COMPUTE_NODE_REPORT_STATUS = 2;
  private static final int METHODID_HANDLE_TRANSMIT_SHARE_DATA_DONE = 3;
  private static final int METHODID_PUBLISH_DATA = 4;
  private static final int METHODID_PUBLISH_COMPUTE_RESOURCE = 5;
  private static final int METHODID_PUBLISH_ALGO = 6;
  private static final int METHODID_CALL_CONTRACT = 7;
  private static final int METHODID_FIND = 8;
  private static final int METHODID_PEER_LOGIN = 9;
  private static final int METHODID_PEER_LOGOUT = 10;
  private static final int METHODID_PEER_PUBLISH_DATA = 11;
  private static final int METHODID_PEER_PUBLISH_COMPUTE_RESOURCE = 12;
  private static final int METHODID_PEER_PUBLISH_ALGO = 13;
  private static final int METHODID_PEER_CALL_CONTRACT = 14;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ScheduleProviderImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ScheduleProviderImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HANDLE_EVENT_RECORD:
          serviceImpl.handleEventRecord((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.EventRecordReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_HANDLE_DATA_NODE_REPORT_STATUS:
          serviceImpl.handleDataNodeReportStatus((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.DataNodeReportReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_HANDLE_COMPUTE_NODE_REPORT_STATUS:
          serviceImpl.handleComputeNodeReportStatus((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComputeNodeReportReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_HANDLE_TRANSMIT_SHARE_DATA_DONE:
          serviceImpl.handleTransmitShareDataDone((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.TransmitShareDataDoneReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_PUBLISH_DATA:
          serviceImpl.publishData((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_PUBLISH_COMPUTE_RESOURCE:
          serviceImpl.publishComputeResource((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_PUBLISH_ALGO:
          serviceImpl.publishAlgo((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_CALL_CONTRACT:
          serviceImpl.callContract((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_FIND:
          serviceImpl.find((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReq) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.FindReply>) responseObserver);
          break;
        case METHODID_PEER_LOGIN:
          serviceImpl.peerLogin((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_PEER_LOGOUT:
          serviceImpl.peerLogout((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.ComeGoReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_PEER_PUBLISH_DATA:
          serviceImpl.peerPublishData((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishDataReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_PEER_PUBLISH_COMPUTE_RESOURCE:
          serviceImpl.peerPublishComputeResource((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishComputeResourceReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_PEER_PUBLISH_ALGO:
          serviceImpl.peerPublishAlgo((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.PublishAlgoReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_PEER_CALL_CONTRACT:
          serviceImpl.peerCallContract((com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.CallContractReq) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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

  private static abstract class ScheduleProviderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ScheduleProviderBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.platon.rosettanet.admin.grpc.ScheduleProviderRpcMessage.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ScheduleProvider");
    }
  }

  private static final class ScheduleProviderFileDescriptorSupplier
      extends ScheduleProviderBaseDescriptorSupplier {
    ScheduleProviderFileDescriptorSupplier() {}
  }

  private static final class ScheduleProviderMethodDescriptorSupplier
      extends ScheduleProviderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ScheduleProviderMethodDescriptorSupplier(String methodName) {
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
      synchronized (ScheduleProviderGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ScheduleProviderFileDescriptorSupplier())
              .addMethod(getHandleEventRecordMethod())
              .addMethod(getHandleDataNodeReportStatusMethod())
              .addMethod(getHandleComputeNodeReportStatusMethod())
              .addMethod(getHandleTransmitShareDataDoneMethod())
              .addMethod(getPublishDataMethod())
              .addMethod(getPublishComputeResourceMethod())
              .addMethod(getPublishAlgoMethod())
              .addMethod(getCallContractMethod())
              .addMethod(getFindMethod())
              .addMethod(getPeerLoginMethod())
              .addMethod(getPeerLogoutMethod())
              .addMethod(getPeerPublishDataMethod())
              .addMethod(getPeerPublishComputeResourceMethod())
              .addMethod(getPeerPublishAlgoMethod())
              .addMethod(getPeerCallContractMethod())
              .build();
        }
      }
    }
    return result;
  }
}
