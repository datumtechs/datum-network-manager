// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/api/annotations.proto

package com.platon.rosettanet.admin.grpc.service;

public final class AnnotationsProto {
  private AnnotationsProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
    registry.add(com.platon.rosettanet.admin.grpc.service.AnnotationsProto.http);
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public static final int HTTP_FIELD_NUMBER = 72295728;
  /**
   * <pre>
   * See `HttpRule`.
   * </pre>
   *
   * <code>extend .google.protobuf.MethodOptions { ... }</code>
   */
  public static final
    com.google.protobuf.GeneratedMessage.GeneratedExtension<
      com.google.protobuf.DescriptorProtos.MethodOptions,
      com.platon.rosettanet.admin.grpc.service.HttpRule> http = com.google.protobuf.GeneratedMessage
          .newFileScopedGeneratedExtension(
        com.platon.rosettanet.admin.grpc.service.HttpRule.class,
        com.platon.rosettanet.admin.grpc.service.HttpRule.getDefaultInstance());

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\034google/api/annotations.proto\022\ngoogle.a" +
      "pi\032\025google/api/http.proto\032 google/protob" +
      "uf/descriptor.proto:E\n\004http\022\036.google.pro" +
      "tobuf.MethodOptions\030\260\312\274\" \001(\0132\024.google.ap" +
      "i.HttpRuleB>\n(com.platon.rosettanet.admi" +
      "n.grpc.serviceB\020AnnotationsProtoP\001b\006prot" +
      "o3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.platon.rosettanet.admin.grpc.service.HttpProto.getDescriptor(),
          com.google.protobuf.DescriptorProtos.getDescriptor(),
        });
    http.internalInit(descriptor.getExtensions().get(0));
    com.platon.rosettanet.admin.grpc.service.HttpProto.getDescriptor();
    com.google.protobuf.DescriptorProtos.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}