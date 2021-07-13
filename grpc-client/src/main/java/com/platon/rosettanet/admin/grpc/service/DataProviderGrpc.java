package com.platon.rosettanet.admin.grpc.service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: fighter/data_svc.proto")
public final class DataProviderGrpc {

  private DataProviderGrpc() {}

  public static final String SERVICE_NAME = "datasvc.DataProvider";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.GetStatusReply> getGetStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStatus",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.GetStatusReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.GetStatusReply> getGetStatusMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.GetStatusReply> getGetStatusMethod;
    if ((getGetStatusMethod = DataProviderGrpc.getGetStatusMethod) == null) {
      synchronized (DataProviderGrpc.class) {
        if ((getGetStatusMethod = DataProviderGrpc.getGetStatusMethod) == null) {
          DataProviderGrpc.getGetStatusMethod = getGetStatusMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.GetStatusReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.GetStatusReply.getDefaultInstance()))
              .setSchemaDescriptor(new DataProviderMethodDescriptorSupplier("GetStatus"))
              .build();
        }
      }
    }
    return getGetStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.ListDataReply> getListDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListData",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.ListDataReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.ListDataReply> getListDataMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.ListDataReply> getListDataMethod;
    if ((getListDataMethod = DataProviderGrpc.getListDataMethod) == null) {
      synchronized (DataProviderGrpc.class) {
        if ((getListDataMethod = DataProviderGrpc.getListDataMethod) == null) {
          DataProviderGrpc.getListDataMethod = getListDataMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.ListDataReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.ListDataReply.getDefaultInstance()))
              .setSchemaDescriptor(new DataProviderMethodDescriptorSupplier("ListData"))
              .build();
        }
      }
    }
    return getListDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> getUploadDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadData",
      requestType = com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> getUploadDataMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> getUploadDataMethod;
    if ((getUploadDataMethod = DataProviderGrpc.getUploadDataMethod) == null) {
      synchronized (DataProviderGrpc.class) {
        if ((getUploadDataMethod = DataProviderGrpc.getUploadDataMethod) == null) {
          DataProviderGrpc.getUploadDataMethod = getUploadDataMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UploadData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply.getDefaultInstance()))
              .setSchemaDescriptor(new DataProviderMethodDescriptorSupplier("UploadData"))
              .build();
        }
      }
    }
    return getUploadDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> getBatchUploadMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "BatchUpload",
      requestType = com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> getBatchUploadMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> getBatchUploadMethod;
    if ((getBatchUploadMethod = DataProviderGrpc.getBatchUploadMethod) == null) {
      synchronized (DataProviderGrpc.class) {
        if ((getBatchUploadMethod = DataProviderGrpc.getBatchUploadMethod) == null) {
          DataProviderGrpc.getBatchUploadMethod = getBatchUploadMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "BatchUpload"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply.getDefaultInstance()))
              .setSchemaDescriptor(new DataProviderMethodDescriptorSupplier("BatchUpload"))
              .build();
        }
      }
    }
    return getBatchUploadMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadReply> getDownloadDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DownloadData",
      requestType = com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadReply> getDownloadDataMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadReply> getDownloadDataMethod;
    if ((getDownloadDataMethod = DataProviderGrpc.getDownloadDataMethod) == null) {
      synchronized (DataProviderGrpc.class) {
        if ((getDownloadDataMethod = DataProviderGrpc.getDownloadDataMethod) == null) {
          DataProviderGrpc.getDownloadDataMethod = getDownloadDataMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DownloadData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadReply.getDefaultInstance()))
              .setSchemaDescriptor(new DataProviderMethodDescriptorSupplier("DownloadData"))
              .build();
        }
      }
    }
    return getDownloadDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> getDeleteDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteData",
      requestType = com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> getDeleteDataMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> getDeleteDataMethod;
    if ((getDeleteDataMethod = DataProviderGrpc.getDeleteDataMethod) == null) {
      synchronized (DataProviderGrpc.class) {
        if ((getDeleteDataMethod = DataProviderGrpc.getDeleteDataMethod) == null) {
          DataProviderGrpc.getDeleteDataMethod = getDeleteDataMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply.getDefaultInstance()))
              .setSchemaDescriptor(new DataProviderMethodDescriptorSupplier("DeleteData"))
              .build();
        }
      }
    }
    return getDeleteDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataRequest,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataReply> getSendSharesDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendSharesData",
      requestType = com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataRequest.class,
      responseType = com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataRequest,
      com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataReply> getSendSharesDataMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataRequest, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataReply> getSendSharesDataMethod;
    if ((getSendSharesDataMethod = DataProviderGrpc.getSendSharesDataMethod) == null) {
      synchronized (DataProviderGrpc.class) {
        if ((getSendSharesDataMethod = DataProviderGrpc.getSendSharesDataMethod) == null) {
          DataProviderGrpc.getSendSharesDataMethod = getSendSharesDataMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataRequest, com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendSharesData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataReply.getDefaultInstance()))
              .setSchemaDescriptor(new DataProviderMethodDescriptorSupplier("SendSharesData"))
              .build();
        }
      }
    }
    return getSendSharesDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReq,
      com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReply> getHandleTaskReadyGoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandleTaskReadyGo",
      requestType = com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReq.class,
      responseType = com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReq,
      com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReply> getHandleTaskReadyGoMethod() {
    io.grpc.MethodDescriptor<com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReq, com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReply> getHandleTaskReadyGoMethod;
    if ((getHandleTaskReadyGoMethod = DataProviderGrpc.getHandleTaskReadyGoMethod) == null) {
      synchronized (DataProviderGrpc.class) {
        if ((getHandleTaskReadyGoMethod = DataProviderGrpc.getHandleTaskReadyGoMethod) == null) {
          DataProviderGrpc.getHandleTaskReadyGoMethod = getHandleTaskReadyGoMethod =
              io.grpc.MethodDescriptor.<com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReq, com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandleTaskReadyGo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReply.getDefaultInstance()))
              .setSchemaDescriptor(new DataProviderMethodDescriptorSupplier("HandleTaskReadyGo"))
              .build();
        }
      }
    }
    return getHandleTaskReadyGoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DataProviderStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataProviderStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataProviderStub>() {
        @java.lang.Override
        public DataProviderStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataProviderStub(channel, callOptions);
        }
      };
    return DataProviderStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DataProviderBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataProviderBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataProviderBlockingStub>() {
        @java.lang.Override
        public DataProviderBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataProviderBlockingStub(channel, callOptions);
        }
      };
    return DataProviderBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DataProviderFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataProviderFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataProviderFutureStub>() {
        @java.lang.Override
        public DataProviderFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataProviderFutureStub(channel, callOptions);
        }
      };
    return DataProviderFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class DataProviderImplBase implements io.grpc.BindableService {

    /**
     */
    public void getStatus(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.GetStatusReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStatusMethod(), responseObserver);
    }

    /**
     */
    public void listData(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.ListDataReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListDataMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest> uploadData(
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getUploadDataMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest> batchUpload(
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getBatchUploadMethod(), responseObserver);
    }

    /**
     */
    public void downloadData(com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDownloadDataMethod(), responseObserver);
    }

    /**
     */
    public void deleteData(com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteDataMethod(), responseObserver);
    }

    /**
     */
    public void sendSharesData(com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendSharesDataMethod(), responseObserver);
    }

    /**
     */
    public void handleTaskReadyGo(com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReq request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleTaskReadyGoMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetStatusMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.GetStatusReply>(
                  this, METHODID_GET_STATUS)))
          .addMethod(
            getListDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.ListDataReply>(
                  this, METHODID_LIST_DATA)))
          .addMethod(
            getUploadDataMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest,
                com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply>(
                  this, METHODID_UPLOAD_DATA)))
          .addMethod(
            getBatchUploadMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest,
                com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply>(
                  this, METHODID_BATCH_UPLOAD)))
          .addMethod(
            getDownloadDataMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest,
                com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadReply>(
                  this, METHODID_DOWNLOAD_DATA)))
          .addMethod(
            getDeleteDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest,
                com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply>(
                  this, METHODID_DELETE_DATA)))
          .addMethod(
            getSendSharesDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataRequest,
                com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataReply>(
                  this, METHODID_SEND_SHARES_DATA)))
          .addMethod(
            getHandleTaskReadyGoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReq,
                com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReply>(
                  this, METHODID_HANDLE_TASK_READY_GO)))
          .build();
    }
  }

  /**
   */
  public static final class DataProviderStub extends io.grpc.stub.AbstractAsyncStub<DataProviderStub> {
    private DataProviderStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataProviderStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataProviderStub(channel, callOptions);
    }

    /**
     */
    public void getStatus(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.GetStatusReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listData(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.ListDataReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest> uploadData(
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getUploadDataMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadRequest> batchUpload(
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getBatchUploadMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void downloadData(com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getDownloadDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteData(com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendSharesData(com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataRequest request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendSharesDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void handleTaskReadyGo(com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReq request,
        io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleTaskReadyGoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DataProviderBlockingStub extends io.grpc.stub.AbstractBlockingStub<DataProviderBlockingStub> {
    private DataProviderBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataProviderBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataProviderBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.GetStatusReply getStatus(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStatusMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.ListDataReply listData(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadReply> downloadData(
        com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getDownloadDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply deleteData(com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataReply sendSharesData(com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendSharesDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReply handleTaskReadyGo(com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleTaskReadyGoMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DataProviderFutureStub extends io.grpc.stub.AbstractFutureStub<DataProviderFutureStub> {
    private DataProviderFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataProviderFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataProviderFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.GetStatusReply> getStatus(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStatusMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.ListDataReply> listData(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply> deleteData(
        com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataReply> sendSharesData(
        com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendSharesDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReply> handleTaskReadyGo(
        com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleTaskReadyGoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_STATUS = 0;
  private static final int METHODID_LIST_DATA = 1;
  private static final int METHODID_DOWNLOAD_DATA = 2;
  private static final int METHODID_DELETE_DATA = 3;
  private static final int METHODID_SEND_SHARES_DATA = 4;
  private static final int METHODID_HANDLE_TASK_READY_GO = 5;
  private static final int METHODID_UPLOAD_DATA = 6;
  private static final int METHODID_BATCH_UPLOAD = 7;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DataProviderImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DataProviderImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_STATUS:
          serviceImpl.getStatus((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.GetStatusReply>) responseObserver);
          break;
        case METHODID_LIST_DATA:
          serviceImpl.listData((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.ListDataReply>) responseObserver);
          break;
        case METHODID_DOWNLOAD_DATA:
          serviceImpl.downloadData((com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadReply>) responseObserver);
          break;
        case METHODID_DELETE_DATA:
          serviceImpl.deleteData((com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.DownloadRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply>) responseObserver);
          break;
        case METHODID_SEND_SHARES_DATA:
          serviceImpl.sendSharesData((com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataRequest) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.SendSharesDataReply>) responseObserver);
          break;
        case METHODID_HANDLE_TASK_READY_GO:
          serviceImpl.handleTaskReadyGo((com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReq) request,
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.Common.TaskReadyGoReply>) responseObserver);
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
        case METHODID_UPLOAD_DATA:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.uploadData(
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply>) responseObserver);
        case METHODID_BATCH_UPLOAD:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.batchUpload(
              (io.grpc.stub.StreamObserver<com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.UploadReply>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class DataProviderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DataProviderBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.platon.rosettanet.admin.grpc.service.DataProviderRpcMessage.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DataProvider");
    }
  }

  private static final class DataProviderFileDescriptorSupplier
      extends DataProviderBaseDescriptorSupplier {
    DataProviderFileDescriptorSupplier() {}
  }

  private static final class DataProviderMethodDescriptorSupplier
      extends DataProviderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DataProviderMethodDescriptorSupplier(String methodName) {
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
      synchronized (DataProviderGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DataProviderFileDescriptorSupplier())
              .addMethod(getGetStatusMethod())
              .addMethod(getListDataMethod())
              .addMethod(getUploadDataMethod())
              .addMethod(getBatchUploadMethod())
              .addMethod(getDownloadDataMethod())
              .addMethod(getDeleteDataMethod())
              .addMethod(getSendSharesDataMethod())
              .addMethod(getHandleTaskReadyGoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
