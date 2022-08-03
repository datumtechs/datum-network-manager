package com.platon.datum.admin.grpc.impl;

import com.platon.datum.admin.grpc.carrier.api.DIDServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@Slf4j
@GrpcService
@Service
public class VcGrpc extends DIDServiceGrpc.DIDServiceImplBase {


    /**
     * <pre>
     * 保存Vc信息
     * </pre>
     */

}
