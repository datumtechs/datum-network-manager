// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: lib/types/metadata.proto

package com.platon.rosettanet.admin.grpc.service;

public interface MetadataUsedQuoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:types.MetadataUsedQuo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * 元数据的使用方式 (0: 未定义; 1: 按照时间段来使用; 2: 按照次数来使用)
   * </pre>
   *
   * <code>.api.protobuf.MetadataUsageType usage_type = 1;</code>
   * @return The enum numeric value on the wire for usageType.
   */
  int getUsageTypeValue();
  /**
   * <pre>
   * 元数据的使用方式 (0: 未定义; 1: 按照时间段来使用; 2: 按照次数来使用)
   * </pre>
   *
   * <code>.api.protobuf.MetadataUsageType usage_type = 1;</code>
   * @return The usageType.
   */
  com.platon.rosettanet.admin.grpc.service.MetadataUsageType getUsageType();

  /**
   * <pre>
   * 是否已过期 (当 usage_type 为 1 时才需要的字段)
   * </pre>
   *
   * <code>bool expire = 2;</code>
   * @return The expire.
   */
  boolean getExpire();

  /**
   * <pre>
   * 已经使用的次数 (当 usage_type 为 1 时才需要的字段)
   * </pre>
   *
   * <code>uint32 used_times = 3;</code>
   * @return The usedTimes.
   */
  int getUsedTimes();
}
