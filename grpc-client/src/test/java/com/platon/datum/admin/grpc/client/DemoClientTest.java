package com.platon.datum.admin.grpc.client;

import com.platon.datum.admin.grpc.carrier.api.AuthRpcApi;
import com.platon.datum.admin.grpc.carrier.api.AuthServiceGrpc;
import com.platon.datum.admin.grpc.carrier.types.Common;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.Test;


/**
 * @Author liushuyu
 * @Date 2021/7/6 19:50
 * @Version
 * @Desc
 */
public class DemoClientTest {


    /**
     * 启动一个服务
     * @throws Exception
     */
//    @Before
//    public void startService() throws Exception {
//        /* The port on which the server should run */
//        int port = 50051;
//        //这个部分启动server
//        NettyServerBuilder.forPort(port)
//                .addService(new AuthServiceImpl().bindService())
//                .build()
//                .start();
//        System.out.println("Server started, listening on " + port);
//    }

    /**
     * 测试客户端
     */
    @Test
    public void test() {
        Channel channel = ManagedChannelBuilder.forAddress("192.168.21.164", 4444).usePlaintext().build();

        AuthRpcApi.ApplyIdentityJoinRequest joinRequest = AuthRpcApi.ApplyIdentityJoinRequest.newBuilder().build();
        Common.SimpleResponse responseCode = AuthServiceGrpc.newBlockingStub(channel).applyIdentityJoin(joinRequest);
        System.out.println("###############" + responseCode.getMsg());
        System.out.println("111111111");
    }


    /**
     * 服务端实现
     */
    static class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {

        @Override
        public void applyIdentityJoin(AuthRpcApi.ApplyIdentityJoinRequest request, StreamObserver<Common.SimpleResponse> responseObserver) {
            System.out.println(request);
            Common.SimpleResponse code = Common.SimpleResponse.newBuilder().setMsg("goooooooooooo").setStatus(0).build();
            //responseObserver.onNext(code);
            responseObserver.onCompleted();
        }
    }
}
